package com.acl.common;


import com.acl.VO.JsonData;
import com.acl.exception.ParamException;
import com.acl.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 65766 on 2018/1/20.
 */
@Slf4j
public class GlobalExceptionResolver implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        String url = httpServletRequest.getRequestURL().toString();
        String systemException = "system error !";
        ModelAndView modelAndView;

        if (url.endsWith(".json")) {
            if (e instanceof PermissionException || e instanceof ParamException) {
                log.info("json预期异常rul={} 错误信息:", url, e);
                final JsonData result = JsonData.fail(e.getMessage());
                modelAndView = new ModelAndView("jsonView", result.toMap());
                return modelAndView;
            } else {
                log.info("json系统异常url={} 错误信息为:", url, e);
                final JsonData result = JsonData.fail(systemException);
                modelAndView = new ModelAndView("jsonView", result.toMap());
                return modelAndView;
            }
        } else if (url.endsWith(".page")) {
            log.info("page异常url={} 错误信息为:", url, e);
            final JsonData result = JsonData.fail(systemException);
            modelAndView = new ModelAndView("exception", result.toMap());
            return modelAndView;
        } else {
            log.info("其他异常url={} 错误信息为:", url, e);
            final JsonData result = JsonData.fail(e.getMessage());
            modelAndView = new ModelAndView("exception", result.toMap());
            return modelAndView;
        }
    }

}
