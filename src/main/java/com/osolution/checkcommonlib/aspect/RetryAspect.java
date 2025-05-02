package com.osolution.checkcommonlib.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class RetryAspect {

    @Around("@annotation(retryOnFailure)")
    public Object handleRetry(ProceedingJoinPoint joinPoint, com.osolution.checkcommonlib.annotation.RetryOnFailure retryOnFailure) throws Throwable {
        int maxAttempts = retryOnFailure.maxAttempts();
        long delayMs = retryOnFailure.delayMs();
        Class<? extends Throwable>[] includeExceptions = retryOnFailure.include();
        Class<? extends Throwable>[] excludeExceptions = retryOnFailure.exclude();

        int attempt = 0;
        while (attempt < maxAttempts) {
            try {
                return joinPoint.proceed();
            } catch (Throwable ex) {
                if (shouldRetry(ex, includeExceptions, excludeExceptions)) {
                    attempt++;
                    if (attempt >= maxAttempts) {
                        throw ex;
                    }
                    Thread.sleep(delayMs);
                } else {
                    throw ex;
                }
            }
        }

        throw new IllegalStateException("Le maximum de tentatives a été atteint sans succès.");
    }

    private boolean shouldRetry(Throwable ex, Class<? extends Throwable>[] includeExceptions, Class<? extends Throwable>[] excludeExceptions) {
        boolean shouldInclude = includeExceptions.length == 0 || Arrays.stream(includeExceptions)
                .anyMatch(clazz -> clazz.isInstance(ex));

        boolean shouldExclude = Arrays.stream(excludeExceptions)
                .anyMatch(clazz -> clazz.isInstance(ex));

        return shouldInclude && !shouldExclude;
    }
}