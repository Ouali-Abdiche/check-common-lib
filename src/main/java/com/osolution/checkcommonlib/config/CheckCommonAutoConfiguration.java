package com.osolution.checkcommonlib.config;

import com.osolution.checkcommonlib.aspect.AuditAspect;
import com.osolution.checkcommonlib.aspect.ExecutionTimeAspect;
import com.osolution.checkcommonlib.aspect.RetryAspect;
import com.osolution.checkcommonlib.properties.CheckCommonProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnClass(name = "org.aspectj.lang.ProceedingJoinPoint")
@EnableConfigurationProperties(CheckCommonProperties.class)
public class CheckCommonAutoConfiguration {

    @Bean
    @ConditionalOnProperty(prefix = "check-common", name = "audit-enabled", havingValue = "true")
    public AuditAspect auditAspect() {
        return new AuditAspect();
    }

    @Bean
    @ConditionalOnProperty(prefix = "check-common", name = "log-execution-time", havingValue = "true")
    public ExecutionTimeAspect executionTimeAspect() {
        return new ExecutionTimeAspect();
    }

    @Bean
    @ConditionalOnProperty(prefix = "check-common.retry", name = "enabled", havingValue = "true")
    public RetryAspect retryAspect() {
        return new RetryAspect();
    }
}
