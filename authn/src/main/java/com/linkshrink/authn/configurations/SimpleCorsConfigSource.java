package com.linkshrink.authn.configurations;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

public class SimpleCorsConfigSource implements CorsConfigurationSource {

    public SimpleCorsConfigSource(CorsConfiguration corsConfiguration){
        this.corsConfiguration = corsConfiguration;
    }

    private final CorsConfiguration corsConfiguration;

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        return corsConfiguration;
    }
}
