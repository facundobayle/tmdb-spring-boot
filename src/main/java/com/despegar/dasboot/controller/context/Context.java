package com.despegar.dasboot.controller.context;

public class Context {
    private static final ThreadLocal<RequestContext> state = new ThreadLocal<>();

    public static void setRequestContext(RequestContext requestContext) {
        state.set(requestContext);
    }

    public static RequestContext getRequestContext() {
        return state.get();
    }
}
