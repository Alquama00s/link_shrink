package com.linkshrink.shortner.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

@Configuration
public class Configs {

    @Bean
    public SecurityFilterChain defaultChain(HttpSecurity http,JwtVerifier jwtVerifier) throws Exception {

        http.cors(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(r->
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
    public WebClient webClient(){
        return WebClient.builder()
                .build();
    }

}
