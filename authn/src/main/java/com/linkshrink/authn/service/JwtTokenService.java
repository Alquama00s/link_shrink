package com.linkshrink.authn.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkshrink.authn.configurations.JwtSubject;
import com.linkshrink.authn.configurations.PrivateClientDetails;
import com.linkshrink.authn.configurations.PrivateUserDetails;
import com.linkshrink.authn.entity.Client;
import com.linkshrink.authn.exceptions.GenericKnownException;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JwtTokenService {

    @Autowired
    JWKSource<SecurityContext> jwks;
    Random random = new SecureRandom();
    HashMap<String,Date> revokedTokens = new HashMap<>();

    public String getToken(JwtSubject subject) throws JOSEException {

        var jwk = getRandomKey();
        var signer = new RSASSASigner(jwk);
        JWTClaimsSet claimsSet = subject.getClaimBuilder()
                .build();

        JWSHeader jwsHeader =  new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(jwk.getKeyID())
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();

    }

    public String getEncryptedToken(JwtSubject subject) throws JOSEException {
        // make a jwt with personally identifiable info
        var signingjwk = getRandomKey();
        var signer = new RSASSASigner(signingjwk);
        var authorities = new ArrayList<>(subject.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        authorities.add("encrypted".toUpperCase());
        JWTClaimsSet claimsSet = subject.getClaimBuilder()
                .claim("authorities",authorities)
                .claim("pii",subject.getSecurePayload())
                .build();

        JWSHeader jwsHeader =  new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(signingjwk.getKeyID())
                .build();

        SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
        signedJWT.sign(signer);

        // encrypt the jwt
        var jwk = getRandomKey();
        var payload = new Payload(signedJWT);

        JWEHeader jweHeader =  new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256,EncryptionMethod.A256GCM)
                .keyID(jwk.getKeyID())
                .contentType("JWT")
                .audience(List.of("LinkShrink"))
                .customParam("exp",claimsSet.getExpirationTime().toInstant().getEpochSecond())
                .build();

        JWEObject jweObject = new JWEObject(jweHeader, payload);
        jweObject.encrypt(new RSAEncrypter(jwk));

        return jweObject.serialize();

    }

    public String decryptToken(String encryptedToken){
        try{
            var jwe = JWEObject.parse(encryptedToken);
            var keySelector = new JWKMatcher.Builder()
                    .keyID(jwe.getHeader().getKeyID())
                    .build();
            var jwk =jwks.get(new JWKSelector(keySelector),null).get(0);
            jwe.decrypt(new RSADecrypter(jwk.toRSAKey()));
            return jwe.getPayload().toSignedJWT().serialize();
        }catch (ParseException ex){
            throw new GenericKnownException("malformed token");
        }catch (JOSEException ex){
            throw new GenericKnownException("unknown token");
        }
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
