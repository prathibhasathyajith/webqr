package com.boc.webqr.util.interceptor;

import com.boc.webqr.bean.RequestBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Interceptor implements HandlerInterceptor {
    private final Logger logger = LoggerFactory.getLogger(Interceptor.class);


    @Autowired
    RequestBean requestBean;

    @Override
    public boolean preHandle
            (HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        RequestDispatcher requestDispatcher;

        try {
            if (!request.getRequestURI().substring(request.getContextPath().length()).equals("/api/v1/conf/server")) {
                System.out.println("---- Interceptor ----");
            }
        } catch (Exception e) {
            logger.error("Exception : ", e);
            requestDispatcher = request.getRequestDispatcher("/api/v1/request/timeout");
            requestDispatcher.forward(request, response);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion
            (HttpServletRequest request, HttpServletResponse response, Object
                    handler, Exception exception) throws Exception {
    }
}
