package com.linkshrink.redirector.configurations;

import com.linkshrink.redirector.client.AuthnClient;
import com.linkshrink.redirector.redis.Client;
import com.linkshrink.redirector.utils.token.TokenManager;
import com.linkshrink.redirector.utils.token.TokenManagerBuilder;
import io.lettuce.core.RedisURI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Bean
    public TokenManager tokenManager(AuthnClient authnClient){
        String authClientId = System.getProperty("REDIRECTOR_AUTH_CLIENTID","redirector");
        String authClientSecret = System.getProperty("REDIRECTOR_AUTH_CLIENTSECRET","redirectorpassword");
        return new TokenManagerBuilder()
                .clientId(authClientId)
                .clientSecret(authClientSecret)
                .expiryDuration(Duration.ofMinutes(3))
                .jwtAuthenticator(authnClient)
                .build();

    }

}
