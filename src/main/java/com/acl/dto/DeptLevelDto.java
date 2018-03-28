package com.acl.dto;

import com.acl.entity.SysDepart;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Created by 65766 on 2018/1/21.
 */
@Setter
@Getter
public class DeptLevelDto extends SysDepart {

    private List<DeptLevelDto> deptList;

    public static DeptLevelDto adapt(SysDepart sysDepart) {
        DeptLevelDto deptLevelDto = new DeptLevelDto();
        BeanUtils.copyProperties(sysDepart, deptLevelDto);
        return deptLevelDto;
    }

}
