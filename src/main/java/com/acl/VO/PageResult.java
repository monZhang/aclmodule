package com.acl.VO;

import com.google.common.collect.Lists;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
public class PageResult<T> {

    List<T> data = Lists.newArrayList();

    Integer total = 0;

}
