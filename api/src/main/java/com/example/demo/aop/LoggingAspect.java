package com.example.demo.aop;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(com.example.springbootproject1.controller..*) || within(com.example.springbootproject1.service..*)")
    public void controllerAndServicePackage() {}

    private Object maskSensitive(Object arg) {
        if (arg == null) {
            return null;
        }

        String s = String.valueOf(arg).toLowerCase();
        if (s.contains("password") || s.contains("passwd")) {
            return "***";
        }

        if (arg instanceof Map<?, ?> m) {
            Map<Object, Object> masked = new HashMap<>();
            m.forEach((k, v) -> {
                if (k != null && String.valueOf(k).toLowerCase().contains("password")) {
                    masked.put(k, "***");
                } else {
                    masked.put(k, v);
                }
            });
            return masked;
        }

        return arg;
    }

    @Around("controllerAndServicePackage()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Signature signature = joinPoint.getSignature();
        String className = signature.getDeclaringTypeName();
        String methodName = signature.getName();

        Object[] args = joinPoint.getArgs();
        String argString = Arrays.stream(args)
            .map(this::maskSensitive)
            .map(String::valueOf)
            .collect(Collectors.joining(", "));

        log.info("进入 {}类.{}方法({})", className, methodName, argString);

        try {
            Object result = joinPoint.proceed();
            long cost = System.currentTimeMillis() - start;
            log.info("执行完成 {}类.{}方法 completed in {} ms", className, methodName, cost);
            return result;
        } catch (Throwable ex) {
            long cost = System.currentTimeMillis() - start;
            log.error("错误 {}类.{}方法 failed in {} ms: {}", className, methodName, cost, ex.getMessage(), ex);
            throw ex;
        }
    }
}


