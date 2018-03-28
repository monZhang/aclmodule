package com.acl.common;

import com.acl.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;

@Setter
@Getter
public class RequestHolder {

    private static final ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();
    private static final ThreadLocal<SysUser> user = new ThreadLocal<>();



    public static void addRequest(HttpServletRequest req) {
        request.set(req);
    }

    public static HttpServletRequest getRequest() {
        return request.get();
    }

    public static SysUser getUser() {
        return user.get();
    }

    public static void remove() {
        request.remove();
        user.remove();
    }

    public static void addUser(SysUser u) {
        user.set(u);
    }


}
