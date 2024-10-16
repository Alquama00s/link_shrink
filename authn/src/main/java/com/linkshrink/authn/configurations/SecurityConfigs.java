package com.linkshrink.authn.configurations;


import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Configuration
public class SecurityConfigs {

    @Bean
    public SecurityFilterChain defaultChain(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable);
        http.csrf(CsrfConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(r->r
                .requestMatchers(
                        "/api/user/register",
                        "/error",
                        "/api/user/login",
                        "/oauth/**"
                ).permitAll()
                .anyRequest().authenticated()
        );
//        http.exceptionHandling(ehc->
//                ehc.accessDeniedHandler((request, response, accessDeniedException) ->response.getWriter().write("Access denied"))
//                );
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(ApplicationContext context,PasswordEncoder passwordEncoder) throws Exception {
        var userDetailBeans = context.getBeansOfType(UserDetailsService.class);
        var aps = new ArrayList<>(context.getBeansOfType(AuthenticationProvider.class).values());
        aps.addAll(userDetailBeans.values().stream().map(i->{
            var ap = new DaoAuthenticationProvider(passwordEncoder);
            ap.setUserDetailsService(i);
            return ap;
        }).toList());
        log.info(userDetailBeans.size()+"");
        return new ProviderManager(aps);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        var defaultKeyList = new ArrayList<>(IntStream.range(0, 3).mapToObj(i -> (JWK) generateRsaKey()).toList());
//        defaultKeyList.add(generateRsaKey("user-auth"));
        JWKSet jwkSet = new JWKSet(defaultKeyList);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static RSAKey generateRsaKey(String keyId){
        RSAKey rsaKey;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            var keyPair = keyPairGenerator.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(keyId)
                    .build();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return rsaKey;
    }
    private static RSAKey generateRsaKey() {
        return generateRsaKey(UUID.randomUUID().toString());
    }



}
