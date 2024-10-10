package com.linkshrink.redirector.configurations;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

@ConfigurationProperties(prefix = "spring.redis")
public record RedisConfig(
        @DefaultValue("localhost") String host,
        @DefaultValue("6379") int port,
        @DefaultValue("60") int timeoutSec,
        @DefaultValue("") String password){

}