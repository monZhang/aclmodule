package com.acl.filter;


import com.acl.common.RequestHolder;
import com.acl.entity.SysUser;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        final SysUser user = (SysUser) request.getSession().getAttribute("user");
        if (user == null) {
            String url = "/signin.jsp";
            response.sendRedirect(url);
            return;
        } else {
            RequestHolder.addRequest(request);
            RequestHolder.addUser(user);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
