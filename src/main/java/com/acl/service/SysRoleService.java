package com.acl.service;

import com.acl.common.RequestHolder;
import com.acl.dao.SysRoleMapper;
import com.acl.entity.SysRole;
import com.acl.exception.PermissionException;
import com.acl.params.RoleParam;
import com.acl.utils.BeanValidator;
import com.acl.utils.IpUtil;
import com.google.common.base.Preconditions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by 65766 on 2018/2/3.
 */
@Service
@Transactional
public class SysRoleService {

    @Resource
    private SysRoleMapper sysRoleMapper;

    /**
     * 保存角色
     *
     * @param param
     */
    public void saveAcl(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())) {
            throw new PermissionException("同一角色类型下名称不能重复!!!");
        }
        SysRole sysRole = SysRole.builder().name(param.getName()).status(param.getStatus()).
                type(param.getType()).seq(param.getSeq()).remark(param.getRemark()).build();
        sysRole.setOperator(RequestHolder.getUser().getUsername());
        sysRole.setOperatorTime(new Date());
        sysRole.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        sysRoleMapper.insertSelective(sysRole);
    }


    /**
     * 修改角色
     *
     * @param param
     */
    public void updateAcl(RoleParam param) {
        BeanValidator.check(param);
        if (checkExist(param.getName(), param.getId())) {
            throw new PermissionException("同一角色模块下名称不能重复!!!");
        }
        final SysRole sysUser = sysRoleMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(sysUser, "待修改的角色不存在!!!");
        SysRole sysRole = SysRole.builder().id(param.getId()).name(param.getName()).status(param.getStatus()).
                type(param.getType()).seq(param.getSeq()).remark(param.getRemark()).build();
        sysRole.setOperator(RequestHolder.getUser().getUsername());
        sysRole.setOperatorTime(new Date());
        sysRole.setOperatorIp(IpUtil.getUserIP(RequestHolder.getRequest()));
        sysRoleMapper.updateByPrimaryKeySelective(sysRole);
    }


    //判断同级权限模块下是否存在相同名称的节点
    private boolean checkExist(String name,Integer id) {
        return sysRoleMapper.countByNameAndId(name, id) > 0;
    }

    //分页查询
    public List<SysRole> getAllRole() {
        List<SysRole> data = sysRoleMapper.getAllRole();
        return data;
    }


}
