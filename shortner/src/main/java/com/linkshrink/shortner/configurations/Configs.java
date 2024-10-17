package com.linkshrink.shortner.configurations;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.web.reactive.function.client.WebClient;

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
