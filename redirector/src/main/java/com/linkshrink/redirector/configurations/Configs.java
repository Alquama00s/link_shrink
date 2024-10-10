package com.linkshrink.redirector.configurations;

import com.linkshrink.redirector.redis.Client;
import io.lettuce.core.RedisURI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
@Configuration
public class Configs {

    @Bean
    public Client client(RedisConfig redisConfig){
        log.info("using redis properties: {}", redisConfig.toString());
        var builder = RedisURI.builder()
                .withHost(redisConfig.host())
                .withPort(redisConfig.port())
                .withTimeout(Duration.of(redisConfig.timeoutSec(), ChronoUnit.SECONDS));
        if(!redisConfig.password().isEmpty()){
            builder.withPassword(redisConfig.password().toCharArray());
        }
        return Client.build(builder.build());
    }

}
