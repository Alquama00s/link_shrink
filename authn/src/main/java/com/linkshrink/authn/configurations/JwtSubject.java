package com.linkshrink.authn.configurations;

import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.UUID;

public interface JwtSubject extends UserDetails {


    default JWTClaimsSet.Builder getClaimBuilder(){
        return new JWTClaimsSet.Builder()
                .subject(getUsername())
                .jwtID(UUID.randomUUID().toString())
                .claim("authorities",getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .issuer("LinkShrink");
    }

}
