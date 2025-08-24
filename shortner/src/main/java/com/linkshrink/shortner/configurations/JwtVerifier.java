package com.linkshrink.shortner.configurations;


import com.linkshrink.shortner.customException.KnownException;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.*;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.SignedJWT;
import jakarta.annotation.PostConstruct;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtVerifier {

    @Autowired
    private WebClient webClient;
    private JWKSource<SecurityContext> jwks;

    @Value("${auth.server:http://localhost:8082}")
    String authServerRoot;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    void init() {
        webClient.get()
                .uri(authServerRoot + "/oauth/protected/jwks")
                .retrieve()
                .bodyToMono(Map.class)
                .subscribe(this::setKeys);
    }

    void setKeys(Map keyMap) {

        List<JWK> jwkList = new ArrayList<>();
        var keyList = (List<Map<String, Object>>) keyMap.get("keys");
        for (var key : keyList) {
            try {
                jwkList.add(JWK.parse(key));
            } catch (Exception e) {
                log.error(e.toString());

            }
        }
        log.info("received {} keys parsed {} keys",keyList.size(),jwkList.size());
        jwks = new ImmutableJWKSet<>(new JWKSet(jwkList));
    }

    public SignedJWT getVerifiedJwt(String token) throws ParseException, JOSEException, KnownException {
        if(token.split("\\.").length>3){
            log.info("decrypting token");
            token = decryptToken(token);
        }
        var jwt = SignedJWT.parse(token);
        var keys = jwks.get(new JWKSelector(new JWKMatcher.Builder()
                .keyID(jwt.getHeader().getKeyID())
                .build()),null);

        if(keys.size()!=1){
            init();
            throw new AccessDeniedException("in valid token");
        }

        var key = (RSAKey) keys.get(0);

        if(!jwt.verify(new RSASSAVerifier(key))){
            throw new AccessDeniedException("in valid token");
        }
        return jwt;

    }

    public String decryptToken(String encryptedToken) throws KnownException {
        try{
            var jwe = JWEObject.parse(encryptedToken);
            var keySelector = new JWKMatcher.Builder()
                    .keyID(jwe.getHeader().getKeyID())
                    .build();
            var jwk =jwks.get(new JWKSelector(keySelector),null).get(0);
            jwe.decrypt(new RSADecrypter(jwk.toRSAKey()));
            return jwe.getPayload().toSignedJWT().serialize();
        }catch (ParseException ex){
            throw new KnownException("malformed token");
        }catch (JOSEException ex){
            throw new KnownException("unknown token");
        }
    }


}
