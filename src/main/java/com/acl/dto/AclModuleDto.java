package com.acl.dto;

import com.acl.entity.SysAcl;
import com.acl.entity.SysAclModule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Setter
@Getter
public class AclModuleDto extends SysAclModule {

    private List<AclModuleDto> aclModuleList;

    private List<AclDto> aclList;

    public static AclModuleDto adapt(SysAclModule sysAclModule) {
        AclModuleDto aclModuleDto = new AclModuleDto();
        BeanUtils.copyProperties(sysAclModule, aclModuleDto);
        return aclModuleDto;
    }

}
