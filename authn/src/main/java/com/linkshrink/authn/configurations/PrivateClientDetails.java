package com.linkshrink.authn.configurations;

import com.linkshrink.authn.entity.Client;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
@Getter
public class PrivateClientDetails implements JwtSubject {

    private Client client;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return client.getSimpleRoles().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return client.getClientSecret();
    }

    @Override
    public String getUsername() {
        return client.getClientId();
    }

    @Override
    public boolean isEnabled() {
        return client.isActive();
    }

    @Override
    public JWTClaimsSet.Builder getClaimBuilder() {
        return JwtSubject.super.getClaimBuilder()
                .claim("userId",client.getUserId())
                .expirationTime(new Date(new Date().getTime() + client.getAccessTokenValiditySec() * 1000L));
    }
}
