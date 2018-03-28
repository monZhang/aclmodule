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
 * Created by 65766 on 2018/2/3.
 */

@Getter
@Setter
@ToString
public class AclParam {

    private Integer id;

    @NotNull
    private Integer aclModuleId;

    @NotBlank
    private String name;

    @NotBlank
    private String url;

    private String code;

    @NotNull
    @Min(value = 1, message = "类型错误!!")
    @Max(value = 3, message = "类型错误!!")
    private Integer type;

    @NotNull
    @Min(value = 0, message = "状态非法!!")
    @Max(value = 2, message = "状态非法!!")
    private Integer status;

    private Integer seq;

    private String remark;


}
