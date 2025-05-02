package com.osolution.checkcommonlib.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "check-common")
@Getter
@Setter
public class CheckCommonProperties {

    private boolean auditEnabled = false;
    private boolean logExecutionTime = false;
}
