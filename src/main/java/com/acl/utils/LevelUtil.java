package com.acl.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by 65766 on 2018/1/21.
 */
public class LevelUtil {

    private final static String SEPARATOR = ".";
    public  final static String ROOT = "0";

    public static String calculateLevel(String parentLevel, Integer parentId) {
        if (StringUtils.isBlank(parentLevel)) {
            return ROOT;
        } else {
            return StringUtils.join(parentLevel,SEPARATOR, parentId );
        }
    }

}
