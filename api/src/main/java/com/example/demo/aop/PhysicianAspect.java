package com.example.demo.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.example.demo.utils.ResultTemplate;

/**
 * 医师控制器切面 - 演示四种通知类型
 * @Before: 前置通知 - 方法执行前
 * @AfterReturning: 后置通知 - 方法正常返回后
 * @AfterThrowing: 异常通知 - 方法抛出异常后
 * @After: 最终通知 - 方法执行后（无论成功还是异常）
 */
@Aspect
@Component
public class PhysicianAspect {
    
    // // 切入点：匹配 PhysicianController 的所有方法
    // @Pointcut("execution(* com.example.demo.controller.PhysicianController.*(..))")
    
    // 切入点：只匹配特定的 HTTP 请求方法，避免内部调用Service方法
    // fetchPositions 方法，而是 MyBatis 的懒加载，所以需要排除掉。每个懒加载查询都会触发 AOP 切面，导致重复记录日志
    // @annotation(org.springframework.web.bind.annotation.GetMapping) 表示匹配 GetMapping 注解
    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping) && " +
              "execution(* com.example.demo.controller.PhysicianController.*(..))")
    public void physicianControllerMethods() {}
    
    // 前置通知 - 方法执行前
    @Before("physicianControllerMethods()")
    public void before(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        Object[] args = joinPoint.getArgs();
        
        System.out.println("""
            =================== 前置通知 ===================
            """);
        System.out.println("类名: " + className);
        System.out.println("方法名: " + methodName);
        System.out.println("可传递参数数量: " + args.length);
        
        // 记录参数信息（隐藏敏感信息）
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                String argInfo = args[i].toString();
                // 隐藏密码等敏感信息
                // if (argInfo.toLowerCase().contains("password")) {
                //     argInfo = "[敏感信息已隐藏]";
                // }
                System.out.println("当前传递参数[" + i + "]: " + argInfo);
            }
        }
        System.out.println("时间: " + java.time.LocalDateTime.now());
    }
    
    // 后置通知 - 方法正常返回后
    @AfterReturning(pointcut = "physicianControllerMethods()", returning = "result")
    public void afterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        
        System.out.println("""
            =================== 后置通知 ===================
            """);
        System.out.println("方法名: " + methodName);
        System.out.println("执行结果: 成功");
        
        // 记录返回结果
        if (result instanceof ResultTemplate) {
            ResultTemplate resultTemplate = (ResultTemplate) result;
            System.out.println("返回状态: " + resultTemplate.getStatus());
            System.out.println("返回消息: " + resultTemplate.getMessage());
        } else {
            System.out.println("返回类型: " + result.getClass().getSimpleName());
        }
        
        System.out.println("时间: " + java.time.LocalDateTime.now());
    }
    
    // 异常通知 - 方法抛出异常后
    @AfterThrowing(pointcut = "physicianControllerMethods()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        
        System.out.println("""
            =================== 异常通知 ===================
            """);
        System.out.println("类名: " + className);
        System.out.println("方法名: " + methodName);
        System.out.println("异常类型: " + exception.getClass().getSimpleName());
        System.out.println("异常消息: " + exception.getMessage());
        System.out.println("时间: " + java.time.LocalDateTime.now());
        
        // 打印异常堆栈（可选）
        System.out.println("异常堆栈:");
        exception.printStackTrace();
    }
    
    // 最终通知 - 方法执行后（无论成功还是异常）
    @After("physicianControllerMethods()")
    public void after(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        
        System.out.println("""
            =================== 最终通知 ===================
            """);
        System.out.println("方法名: " + methodName);
        System.out.println("方法执行完毕（无论成功还是异常）");
        System.out.println("时间: " + java.time.LocalDateTime.now());
        System.out.println("===============================================");
    }
}
