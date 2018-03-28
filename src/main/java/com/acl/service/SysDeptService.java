package com.acl.service;

import com.acl.common.RequestHolder;
import com.acl.dao.SysDepartMapper;
import com.acl.entity.SysDepart;
import com.acl.exception.ParamException;
import com.acl.exception.PermissionException;
import com.acl.params.DeptParam;
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

/**
 * Created by 65766 on 2018/1/21.
 */
@Service
@Transactional
public class SysDeptService {

    @Resource
    SysDepartMapper sysDepartMapper;

    /**
     * 新增部门
     *
     * @param param
     */
    public void saveDept(DeptParam param) {
        BeanValidator.check(param);
        //判断部门名称是否重复
        if (checkExist(param.getName(), param.getParentId(), param.getId())) {
            throw new PermissionException("同一部门下名称不能重复!!!");
        }
        SysDepart sysDepart = SysDepart.builder().name(param.getName()).parentId(param.getParentId()).
                remark(param.getRemark()).seq(param.getSeq()).build();
        sysDepart.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));
        sysDepart.setOperator(RequestHolder.getUser().getUsername());
        sysDepart.setOperatorTime(new Date());
        sysDepart.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        sysDepartMapper.insertSelective(sysDepart);
    }

    //判断同级部门下是否存在相同名称的节点
    private boolean checkExist(String name, Integer parentId, Integer id) {
        return sysDepartMapper.countByNameParentIdDepartId(name, parentId, id) > 0;
    }

    //获得父节点level
    private String getLevel(Integer parentId) {
        SysDepart sysDepart = sysDepartMapper.selectByPrimaryKey(parentId);
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
    public void updateDept(DeptParam param) {
        BeanValidator.check(param);

        final SysDepart before = sysDepartMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新部门不存在");

        if (checkExist(param.getName(), param.getParentId(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }
        SysDepart after = SysDepart.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId()).
                seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));

        after.setOperator(RequestHolder.getUser().getUsername());
        after.setOperatorTime(new Date());
        after.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        updateWithChild(before, after);
    }

    //更新子部门
    private void updateWithChild(SysDepart before, SysDepart after) {

        String newPrefix = before.getLevel();
        String oldPrefix = after.getLevel();
        if (!oldPrefix.equals(newPrefix)) {
            List<SysDepart> allChildNode = sysDepartMapper.getAllChildNode(oldPrefix);
            if (!CollectionUtils.isEmpty(allChildNode)) {
                for (SysDepart dept : allChildNode) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldPrefix) == 0) {
                        level = newPrefix + level.substring(oldPrefix.length());
                        dept.setLevel(level);
                    }
                }
                sysDepartMapper.batchUpdateLevel(allChildNode);
            }
        }
        sysDepartMapper.updateByPrimaryKeySelective(after);
    }

    //删除方法
    public void deleteDept(Integer id) {

    }

}
