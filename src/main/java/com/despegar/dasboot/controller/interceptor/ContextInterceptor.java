package com.despegar.dasboot.controller.interceptor;

import com.despegar.dasboot.controller.Headers;
import com.despegar.dasboot.controller.context.Context;
import com.despegar.dasboot.controller.context.RequestContext;
import org.jboss.logging.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ContextInterceptor implements HandlerInterceptor {
    private static final String HOSTNAME = "hostname";
    private static final String UOW = "uow";
    private static final String X_MOVIE = "xmovie";

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String uow = Optional.ofNullable(request.getHeader(Headers.UOW))
                        .orElseGet(() -> UUID.randomUUID().toString());
        setContext(request, uow);
        String hostname = InetAddress.getLocalHost().getHostName();

        MDC.put(UOW, uow);
        MDC.put(HOSTNAME, hostname);

        return true;
    }

    private void setContext(HttpServletRequest request, String uow) {
        Map<String, String> customHeaders =
                Collections.list(request.getHeaderNames()).stream()
                        .filter(h -> h.equals(X_MOVIE))
                        .collect(Collectors.toMap(Function.identity(), request::getHeader));

        customHeaders.put(Headers.UOW, uow);
        RequestContext requestContext = new RequestContext(customHeaders);
        Context.setRequestContext(requestContext);
    }
}
