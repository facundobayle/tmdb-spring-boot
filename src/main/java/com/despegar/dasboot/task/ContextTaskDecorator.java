package com.despegar.dasboot.task;

import com.despegar.dasboot.controller.context.Context;
import com.despegar.dasboot.controller.context.RequestContext;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class ContextTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        RequestContext context = Context.getRequestContext();
        Map<String, String> mdcMap = MDC.getCopyOfContextMap();

        return () -> {
            try {
                Context.setRequestContext(context);
                MDC.setContextMap(mdcMap);
                runnable.run();
            } finally {
                Context.clear();
                MDC.clear();
            }
        };
    }
}
