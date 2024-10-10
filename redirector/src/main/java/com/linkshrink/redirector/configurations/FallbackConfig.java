package com.linkshrink.redirector.configurations;


import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "fallbacks")
public record FallbackConfig(String viewUrl) {
}


