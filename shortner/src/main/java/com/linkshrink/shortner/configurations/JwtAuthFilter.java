package com.linkshrink.shortner.configurations;

import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    JwtVerifier jwtVerifier;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            var token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
            log.info("token: "+token);
            var jwt = jwtVerifier.getVerifiedJwt(token);
            var authorities = Optional.ofNullable(jwt.getJWTClaimsSet().getListClaim("authorities"))
                    .orElse(Collections.emptyList())
                    .stream().map(Object::toString).map(SimpleGrantedAuthority::new).toList();
            var subject = Optional.ofNullable(jwt.getJWTClaimsSet().getSubject())
                    .orElse("un specified");
            var pii = Optional.ofNullable(jwt.getJWTClaimsSet().getJSONObjectClaim("pii"))
                    .orElse(Map.of());
            var auth = new UsernamePasswordAuthenticationToken(subject,token,authorities);
            auth.setDetails(pii);
            SecurityContextHolder.getContext().setAuthentication(auth);

        } catch (Exception e) {
            log.info("un authenticated");
            log.info(e.toString());
        }
        filterChain.doFilter(request,response);
    }
}
