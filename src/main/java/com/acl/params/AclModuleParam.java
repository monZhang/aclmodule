package com.acl.params;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ToString
public class AclModuleParam {

    private Integer id;

    @NotBlank
    @Length(min=2,message = "部门名称最少两个字")
    private String name;

    private Integer parentId;

    private Integer seq;

    @Length(max =150 ,message = "备注不能超过150个字")
    private String remark;

}
