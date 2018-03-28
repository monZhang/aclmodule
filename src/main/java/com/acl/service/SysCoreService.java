package com.acl.service;

import com.acl.common.RequestHolder;
import com.acl.dao.SysAclMapper;
import com.acl.dao.SysRoleAclMapper;
import com.acl.dao.SysRoleUserMapper;
import com.acl.entity.SysAcl;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by 65766 on 2018/2/6.
 */
@Service
public class SysCoreService {

    @Resource
    private SysAclMapper sysAclMapper;
    @Resource
    private SysRoleUserMapper sysRoleUserMapper;
    @Resource
    private SysRoleAclMapper sysRoleAclMapper;


    //获取当前用户所有的acl
    public List<SysAcl> getCurrentUserAcl() {
        final Integer userId = RequestHolder.getUser().getId();
        if (isSuperAdmin(userId)){
            return sysAclMapper.getAll();
        }
        //根据userid获取所有角色id
        List<Integer> roleIdList = getRoleIdListByUserId(userId);
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        //根据所有角色Id获取所有权限点
        return getAclListByRoleIdList(roleIdList);
    }

    private boolean isSuperAdmin(Integer userId) {
        return true;
    }


    private List<SysAcl> getAclListByRoleIdList(List<Integer> roleIdList) {
        if (CollectionUtils.isEmpty(roleIdList)) {
            return Lists.newArrayList();
        }
        //获取所有权限点id
        List<Integer> aclIdLIst = sysRoleAclMapper.getAclIdByRoleIdList(roleIdList);
        if (CollectionUtils.isEmpty(aclIdLIst)) {
            return Lists.newArrayList();
        }
        //获取所有权限点对象
        return sysAclMapper.getAclByAclIdList(aclIdLIst);
    }

    private List<Integer> getRoleIdListByUserId(Integer userId) {
        return sysRoleUserMapper.getRoleIdList(userId);
    }

    //获取当前角色的所有权限
    public List<SysAcl> getRoleAcl(Integer roleId) {
        //获取当前角色所有权限id
        List<Integer> aclIdList = sysRoleAclMapper.getAclIdByRoleIdList(Lists.newArrayList(roleId));
        if (CollectionUtils.isEmpty(aclIdList)) {
            return Lists.newArrayList();
        }
        //根据所有权限id获取所有权限对象
        return sysAclMapper.getAclByAclIdList(aclIdList);
    }
}
