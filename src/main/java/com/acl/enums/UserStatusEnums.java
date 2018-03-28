package com.acl.enums;

import lombok.Getter;

/**
 * Created by 65766 on 2018/1/28.
 */
@Getter
public enum UserStatusEnums {

    //正常0  冻结1 删除 2
    AVAILABLE(1, "正常"), DISABLED(0, "冻结"), DELETE(2, "删除");

    private Integer code;

    private String msg;

    UserStatusEnums(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
