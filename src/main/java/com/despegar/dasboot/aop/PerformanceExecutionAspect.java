package com.despegar.dasboot.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PerformanceExecutionAspect {
    private static final Logger logger = LoggerFactory.getLogger(PerformanceExecutionAspect.class);

    @Around("@annotation(performance)")
    public Object trackExecutionTime(ProceedingJoinPoint joinPoint, Performance performance)
            throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long finish = System.nanoTime();
        String name = joinPoint.getSignature().toShortString();

        logger.info(
                String.format("Call to %s took %s ms", name, (finish - start)/1000000));

        return proceed;
    }

}
