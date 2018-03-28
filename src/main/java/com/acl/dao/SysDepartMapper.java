package com.acl.dao;

import com.acl.entity.SysDepart;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysDepartMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysDepart record);

    int insertSelective(SysDepart record);

    SysDepart selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysDepart record);

    int updateByPrimaryKey(SysDepart record);

    List<SysDepart> selectAll();

    List<SysDepart> getAllChildNode(@Param("level")String level);

    int countByNameParentIdDepartId(@Param("name")String name,@Param("parentId") Integer parentId,@Param("id") Integer departId);

    void batchUpdateLevel(List<SysDepart> allChildNode);
}