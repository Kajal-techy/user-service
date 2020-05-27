package com.userservice.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {
    @Around("execution(* com.userservice.controller.UserController.*(..)) || execution(* com.userservice.service.UserServiceImpl.*(..))")
    public Object around(ProceedingJoinPoint jointpoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object response = jointpoint.proceed();
        long timeTaken = System.currentTimeMillis() - startTime;
        log.info("Existing from {}.{} with return value {} and total time: {}", jointpoint.getTarget().getClass().getSimpleName(), jointpoint.getSignature().getName(), response, timeTaken);
        return response;
    }

    @AfterThrowing (pointcut = "execution(* com.userservice.controller.UserController.*(..)) || execution(* com.userservice.service.UserServiceImpl.*(..))", throwing = "ex")
    public void printException(JoinPoint jointpoint, Exception ex) throws Throwable
    {
        log.info("Existing from {}.{} with exception {} ", jointpoint.getTarget().getClass().getSimpleName(), jointpoint.getSignature().getName(), ex.toString());
    }
}
