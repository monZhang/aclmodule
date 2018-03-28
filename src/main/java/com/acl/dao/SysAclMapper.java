package com.acl.dao;

import com.acl.params.PageParam;
import com.acl.entity.SysAcl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAcl record);

    int insertSelective(SysAcl record);

    SysAcl selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAcl record);

    int updateByPrimaryKey(SysAcl record);

    int selectCountByAclModuleId(@Param("aclModuleId") Integer aclModuleId);

    List<SysAcl> AclPage(@Param("aclModuleId") Integer aclModuleId,@Param("param") PageParam param);

    int countByNameParentIdDepartId(@Param("name") String name,@Param("aclModuleId") Integer aclModuleId,@Param("id") Integer id);

    List<SysAcl> getAll();

    List<SysAcl> getAclByAclIdList(List<Integer> aclIdLIst);
}