package com.acl.params;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by 65766 on 2018/1/28.
 */
@Setter
@Getter
@ToString
public class UserParam {

    private Integer id;

    @NotBlank
    @Length(min = 2, max = 20, message = "名称2-20个字")
    private String username;

    @NotBlank
    private String telephone;

    @NotBlank
   // @Min(value = 2, message = "邮箱最少五个字")
    private String mail;

    @NotNull
    @Min(value = 0, message = "状态不正确")
    @Max(value = 2, message = "状态不正确")
    private Integer status;

    @NotNull
    private Integer deptId;

    @Length(max = 200, message = "最多200个字")
    private String remark;

}
