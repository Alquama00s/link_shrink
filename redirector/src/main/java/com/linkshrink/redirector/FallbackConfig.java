package com.linkshrink.redirector;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@ConfigurationProperties(prefix = "fallbacks")
public record FallbackConfig(String viewUrl) {
}
