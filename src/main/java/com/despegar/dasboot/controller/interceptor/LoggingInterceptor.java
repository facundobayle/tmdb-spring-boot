package com.despegar.dasboot.controller.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.stream.Collectors;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String headersStr = getHeaders(request);
        logger.info("Init {} with headers: {}", request.getRequestURL(), headersStr);
        return true;
    }

    private String getHeaders(HttpServletRequest request) {
        return Collections.list(request.getHeaderNames()).stream()
                .map(header -> header + ": " + request.getHeader(header))
                .collect(Collectors.joining(", "));
    }
}
