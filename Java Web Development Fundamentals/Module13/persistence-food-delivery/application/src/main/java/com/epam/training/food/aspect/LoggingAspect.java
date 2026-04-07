package com.epam.training.food.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("@annotation(EnableArgumentLogging)")
    public void logArguments(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        LOGGER.info("Method name: [{}], parameter(s): {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "@annotation(EnableReturnValueLogging)", returning = "result")
    public void logReturnValue(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Method name: [{}], return value: {}", methodName, result);
    }

    @Around("@annotation(EnableExecutionTimeLogging)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.nanoTime();
        Object proceed = joinPoint.proceed();
        long executionTime = System.nanoTime() - start;
        String methodName = joinPoint.getSignature().getName();
        LOGGER.info("Method name: [{}], execution time = {}µs", methodName, (executionTime / 1000.0));
        return (proceed);
    }
}