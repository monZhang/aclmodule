package com.acl.dao;

import com.acl.entity.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    int countByNameParentIdAclModuleId(@Param("name") String name, @Param("parentId") Integer parentId, @Param("id") Integer id);

    void batchUpdateLevel(List<SysAclModule> allChildNode);

    List<SysAclModule> getAllChildNode(@Param("level") String level);

    List<SysAclModule> selectAll();
}