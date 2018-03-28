package com.acl.controller;

import com.acl.entity.SysUser;
import com.acl.enums.UserStatusEnums;
import com.acl.service.SysUserService;
import com.acl.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 65766 on 2018/1/28.
 */
@Controller
public class UserController {
    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/login.page")
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String ret = (String) request.getAttribute("ret");
        String erroMsg = "";
        if (StringUtils.isBlank(userName)) {
            erroMsg = "用户名为空!";
        } else if (StringUtils.isBlank(password)) {
            erroMsg = "密码为空!";
        } else {
            SysUser user = sysUserService.selectByKeyWord(userName);
            if (user == null) {
                erroMsg = "用户不存在!";
            } else if (!user.getPassword().equals(MD5Util.encrypt(password))) {
                erroMsg = "用户名或密码错误!";
            } else if (user.getStatus() != UserStatusEnums.AVAILABLE.getCode()) {
                erroMsg = "用户状态不可用!";
            } else {
                request.getSession().setAttribute("user", user);
                if (StringUtils.isBlank(ret)) {

                    response.sendRedirect("/admin/index.page");
                    return ;
                } else {
                    response.sendRedirect(ret);
                    return ;
                }
            }
        }
        if (StringUtils.isNotBlank(ret)) {
            request.setAttribute("ret", ret);
        }
        request.setAttribute("username", userName);
        request.setAttribute("error", erroMsg);
        request.getRequestDispatcher("signin.jsp").forward(request, response);
    }

    @RequestMapping("/logout.page")
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/signin.jsp");
    }

}
