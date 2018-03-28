package com.acl.dto;

import com.acl.entity.SysAcl;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

/**
 * Created by 65766 on 2018/2/6.
 */
@Getter
@Setter
public class AclDto extends SysAcl {

    //是否选中
    private boolean checked = false;
    //是否有权限分配
    private boolean hasAcl = false;

    public static AclDto adapt(SysAcl sysAcl) {
        final AclDto aclDto = new AclDto();
        BeanUtils.copyProperties(sysAcl, aclDto);
        return aclDto;
    }


}
