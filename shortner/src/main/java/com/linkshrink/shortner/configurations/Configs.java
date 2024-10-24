package com.linkshrink.shortner.configurations;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
public class Configs {

    @Bean
    public SecurityFilterChain defaultChain(HttpSecurity http, JwtVerifier jwtVerifier, SimpleCorsConfigSource simpleCorsConfigSource) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(simpleCorsConfigSource))
                .authorizeHttpRequests(r ->
                                r.requestMatchers("/error").permitAll()
                                        .requestMatchers("/api/status/admin")
                                        .hasRole("admin".toUpperCase())
                                        .requestMatchers("/api/status/enc")
                                        .hasAuthority("encrypted".toUpperCase())
//                        .requestMatchers("/api/status/encAdmin")
//                        .access((i,j)->new AuthorizationDecision(i.get()
//                                .getAuthorities()
//                                .containsAll(Arrays.asList(new "ADMIN","ENCRYPTED")))))
                                        .anyRequest().authenticated()
                );

        http.addFilterBefore(new JwtAuthFilter(jwtVerifier), AuthorizationFilter.class);

        return http.build();
    }


    @Bean
    public SimpleCorsConfigSource corsConfiguration(@Value("${frontend.uri:http://localhost:4200}") String frontendUri) {
        log.info("frontend uri: {}", frontendUri);
        var cc = new CorsConfiguration();
        cc.addAllowedOrigin(frontendUri);
        cc.addAllowedMethod("*");
        cc.setMaxAge(3600L);
        cc.addAllowedHeader("*");
        return new SimpleCorsConfigSource(cc);
    }

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .build();
    }

}
