package com.acl.params;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

public class PageParam {

    @Getter
    @Setter
    @Min(value = 0, message = "页码非法!")
    private Integer pageNo = 1;

    @Getter
    @Setter
    @Min(value = 0, message = "页面数量非法!")
    private Integer pageSize = 10;

    @Setter
    private Integer offSet;

    public Integer getOffSet() {
        return (pageNo - 1) * pageSize;
    }
}
