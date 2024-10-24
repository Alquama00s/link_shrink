package com.linkshrink.shortner.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "fallbacks")
public record FallBacks(String expiryDuration) {
}
