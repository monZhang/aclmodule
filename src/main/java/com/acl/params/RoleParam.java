package com.acl.params;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by 65766 on 2018/2/3.
 */
@Getter
@Setter
@ToString
public class RoleParam {

    private Integer id;

    @NotBlank
    @Length(min = 2,max = 10,message = "角色名称2到十个字!!!")
    private String name;

    @NotNull
    @Min(value = 0,message = "角色状态非法!!!")
    @Max(value = 1,message = "角色状态非法!!!")
    private Integer status;

    private Integer type;

    private Integer seq;

    @Length(max = 100,message = "备注最多一百个字!!!")
    private String remark;


}
