package com.linkshrink.authn.service;


import com.linkshrink.authn.configurations.PrivateClientDetails;
import com.linkshrink.authn.configurations.PrivateUserDetails;
import com.linkshrink.authn.entity.Client;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JwtTokenService {

    @Autowired
    JWKSource<SecurityContext> jwks;
    Random random = new SecureRandom();
    HashMap<String,Date> revokedTokens = new HashMap<>();

    public String getToken(PrivateUserDetails userDetails) throws JOSEException {

        var jwk = getRandomKey();
        var signer = new RSASSASigner(jwk);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(userDetails.getUsername())
                .jwtID(UUID.randomUUID().toString())
                .claim("authorities",userDetails.getUser().getSimpleRoles())
                .issuer("LinkShrink")
                .expirationTime(new Date(new Date().getTime() + 60 * 1000 * 5))
                .build();

        JWSHeader jwsHeader =  new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(jwk.getKeyID())
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();

    }

    public String getToken(PrivateClientDetails client) throws JOSEException {
        var jwk = getRandomKey();
        var signer = new RSASSASigner(jwk);
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(client.getClient().getClientId())
                .jwtID(UUID.randomUUID().toString())
                .claim("authorities",client.getClient().getSimpleRoles())
                .claim("userId",client.getClient().getUserId())
                .issuer("LinkShrink")
                .expirationTime(new Date(new Date().getTime() + client.getClient().getAccessTokenValiditySec() * 1000L))
                .build();

        JWSHeader jwsHeader =  new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(jwk.getKeyID())
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();

    }

    public boolean introspect(String token){
        try{
            var jwt = SignedJWT.parse(token);
            var matcher = new JWKMatcher.Builder()
                    .keyID(jwt.getHeader().getKeyID())
                    .build();

            var key = (RSAKey)jwks.get(new JWKSelector(matcher),null).get(0);

            var verifier = new RSASSAVerifier(key.toRSAPublicKey());

            return !revokedTokens.containsKey(jwt.getJWTClaimsSet().getJWTID()) && jwt.verify(verifier) && !isExpired(jwt);

        } catch (Exception e) {
            log.warn(e.toString());
        }
        return false;
    }

    public void revoke(String token) throws ParseException {
        JWT jwt = SignedJWT.parse(token);
        revokedTokens.put(jwt.getJWTClaimsSet().getJWTID(),jwt.getJWTClaimsSet().getExpirationTime());
    }

    @Scheduled(fixedRate = 5,timeUnit = TimeUnit.MINUTES)
    private void cleanExpiredRevokedTokens(){
        revokedTokens.entrySet().removeIf(i->isExpired(i.getValue()));
        log.info("revoked token size: {}", revokedTokens.size());
    }

    private boolean isExpired(JWT jwt){
        try {
            return isExpired(jwt.getJWTClaimsSet().getExpirationTime());
        } catch (ParseException e) {
            log.error(e.toString());
        }
        return false;
    }

    private boolean isExpired(Date date){
        return new Date().after(date);
    }

    private RSAKey getRandomKey() throws KeySourceException {
        var matcher = new JWKMatcher.Builder()
                .build();
        var keyList = jwks.get(new JWKSelector(matcher),null);
        return (RSAKey)keyList
                .get(random.nextInt(keyList.size()));
    }

}
