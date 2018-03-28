package com.acl.VO;

import com.google.common.collect.Maps;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 65766 on 2018/1/20.
 */
@Setter
@Getter
public class JsonData {

    private boolean ret;

    private String msg;

    private Object data;

    private JsonData(boolean ret) {
        this.ret = ret;
    }

    public static JsonData success() {
        return new JsonData(true);
    }

    public static JsonData success(String msg) {
        final JsonData result = new JsonData(true);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }

    public static JsonData success(Object data) {
        final JsonData result = new JsonData(true);
        result.setMsg(null);
        result.setData(data);
        return result;
    }

    public static JsonData success(String msg, Object data) {
        final JsonData result = new JsonData(true);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static JsonData fail(String msg) {
        final JsonData result = new JsonData(false);
        result.setMsg(msg);
        return result;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = Maps.newHashMap();
        map.put("ret", ret);
        map.put("msg", msg);
        map.put("data", data);
        return map;
    }


}
