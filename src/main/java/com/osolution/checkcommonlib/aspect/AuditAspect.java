package com.osolution.checkcommonlib.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class AuditAspect {

    @Before("execution(* com..*..*(..))")
    public void logMethodCall(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("[AUDIT] Appel de : {} avec paramètres : {}", methodName, Arrays.toString(args));
    }

    @AfterReturning(pointcut = "execution(* com..*..*(..))", returning = "result")
    public void logMethodReturn(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().toShortString();
        log.info("[AUDIT] Résultat de : {} => {}", methodName, result);
    }

    @AfterThrowing(pointcut = "execution(* com..*..*(..))", throwing = "ex")
    public void logMethodException(JoinPoint joinPoint, Throwable ex) {
        String methodName = joinPoint.getSignature().toShortString();
        log.error("[AUDIT] Exception dans : {} => {}", methodName, ex.getMessage(), ex);
    }
}
