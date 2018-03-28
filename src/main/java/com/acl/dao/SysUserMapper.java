package com.acl.dao;

import com.acl.params.PageParam;
import com.acl.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    int checkMail(@Param("email") String email,@Param("userId") Integer userId);

    int checkPhone(@Param("phone") String phone, @Param("userId") Integer userId);

    SysUser selectByKeyWord(@Param("userName") String userName);

    int selectCountByDeptId(@Param("deptId") Integer deptId);

    List<SysUser> userPage(@Param("deptId") Integer deptId,@Param("param") PageParam param);
}