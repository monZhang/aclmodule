package com.acl.service;

import com.acl.params.PageParam;
import com.acl.VO.PageResult;
import com.acl.common.RequestHolder;
import com.acl.dao.SysAclMapper;
import com.acl.entity.SysAcl;
import com.acl.exception.PermissionException;
import com.acl.params.AclParam;
import com.acl.utils.BeanValidator;
import com.acl.utils.IpUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by 65766 on 2018/2/3.
 */
@Service
@Transactional
public class SysAclService {

    @Resource
    private SysAclMapper sysAclMapper;

    /**
     * 保存权限
     *
     * @param param
     */
    public void saveAcl(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getAclModuleId(), param.getId())) {
            throw new PermissionException("同一权限模块下名称不能重复!!!");
        }
        SysAcl sysAcl = SysAcl.builder().code(param.getCode()).aclModuleId(param.getAclModuleId()).name(param.getName()).url(param.getUrl()).type(param.getType()).
                status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();

        sysAcl.setOperator(RequestHolder.getUser().getUsername());
        sysAcl.setOperatorTime(new Date());
        sysAcl.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        sysAclMapper.insertSelective(sysAcl);
    }

    private String generateCode() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(new Date()) + "-" + (Math.random() * 100);
    }


    /**
     * 修改权限
     *
     * @param param
     */
    public void updateAcl(AclParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getAclModuleId(), param.getId())) {
            throw new PermissionException("同一权限模块下名称不能重复!!!");
        }
        final SysAcl sysUser = sysAclMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(sysUser, "待修改的权限不存在!!!");
        SysAcl sysAcl = SysAcl.builder().id(param.getId()).aclModuleId(param.getAclModuleId()).name(param.getName()).url(param.getUrl()).type(param.getType()).
                status(param.getStatus()).seq(param.getSeq()).remark(param.getRemark()).build();
        sysAcl.setOperator(RequestHolder.getUser().getUsername());
        sysAcl.setOperatorTime(new Date());
        sysAcl.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        sysAclMapper.updateByPrimaryKeySelective(sysAcl);
    }


    //判断同级权限模块下是否存在相同名称的节点
    private boolean checkExist(String name, Integer aclModuleId, Integer id) {
        return sysAclMapper.countByNameParentIdDepartId(name, aclModuleId, id) > 0;
    }

    //分页查询
    public PageResult aclPage(Integer aclModuleId, PageParam param) {
        int totalCount = sysAclMapper.selectCountByAclModuleId(aclModuleId);
        if (totalCount > 0) {
            List<SysAcl> data = sysAclMapper.AclPage(aclModuleId, param);
            return PageResult.<SysAcl>builder().data(data).total(totalCount).build();
        }
        return PageResult.<SysAcl>builder().build();
    }


}
