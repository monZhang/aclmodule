package com.acl.service;

import com.acl.common.RequestHolder;
import com.acl.dao.SysAclModuleMapper;
import com.acl.entity.SysAclModule;
import com.acl.exception.ParamException;
import com.acl.exception.PermissionException;
import com.acl.params.AclModuleParam;
import com.acl.utils.BeanValidator;
import com.acl.utils.IpUtil;
import com.acl.utils.LevelUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class SysAclModultService {

    @Resource
    private SysAclModuleMapper sysAclModuleMapper;

    /**
     * 新增部门
     *
     * @param param
     */
    public void saveAclModule(AclModuleParam param) {
        BeanValidator.check(param);
        //判断部门名称是否重复
        if (checkExist(param.getName(), param.getParentId(), param.getId())) {
            throw new PermissionException("同一部门下名称不能重复!!!");
        }
        SysAclModule sysDepart = SysAclModule.builder().name(param.getName()).parentId(param.getParentId()).
                seq(param.getSeq()).remark(param.getRemark()).build();
        sysDepart.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        sysDepart.setOperator(RequestHolder.getUser().getUsername());
        sysDepart.setOperatorTime(new Date());
        sysDepart.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        sysAclModuleMapper.insertSelective(sysDepart);
    }

    //判断同级部门下是否存在相同名称的节点
    private boolean checkExist(String name, Integer parentId, Integer id) {
        return sysAclModuleMapper.countByNameParentIdAclModuleId(name, parentId, id) > 0;
    }

    //获得父节点level
    private String getLevel(Integer parentId) {
        SysAclModule sysDepart = sysAclModuleMapper.selectByPrimaryKey(parentId);
        if (sysDepart == null) {
            return null;
        } else {
            return sysDepart.getLevel();
        }
    }

    /**
     * 修改部门
     *
     * @param param
     */
    public void updateAclModule(AclModuleParam param) {
        BeanValidator.check(param);

        final SysAclModule before = sysAclModuleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新部门不存在");

        if (checkExist(param.getName(), param.getParentId(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysAclModule after = SysAclModule.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).
                seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));

        after.setOperator(RequestHolder.getUser().getUsername());
        after.setOperatorTime(new Date());
        after.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        updateWithChild(before, after);
    }

    //更新子部门
    private void updateWithChild(SysAclModule before, SysAclModule after) {

        String newPrefix = before.getLevel();
        String oldPrefix = after.getLevel();
        if (!oldPrefix.equals(newPrefix)) {
            List<SysAclModule> allChildNode = sysAclModuleMapper.getAllChildNode(oldPrefix);
            if (!CollectionUtils.isEmpty(allChildNode)) {
                for (SysAclModule dept : allChildNode) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldPrefix) == 0) {
                        level = newPrefix + level.substring(oldPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysAclModuleMapper.batchUpdateLevel(allChildNode);
            }
        }
        sysAclModuleMapper.updateByPrimaryKeySelective(after);
    }

    //删除方法
    public void deleteAclModule(Integer id) {

    }
}
