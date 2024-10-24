package com.linkshrink.authn.configurations;

import com.linkshrink.authn.entity.User;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.Map;


@Getter
@AllArgsConstructor
public class PrivateUserDetails implements JwtSubject {
    private User user;


    @Override
    public String getPassword() {
        return user.getPwd();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getSimpleRoles().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive();
    }

    @Override
    public JWTClaimsSet.Builder getClaimBuilder() {
        return JwtSubject.super.getClaimBuilder()
                .expirationTime(new Date(new Date().getTime() + 60 * 1000 * 5))
                ;
    }

    @Override
    public Map<String, Object> getSecurePayload() {
        return Map.of(
                "user.username",user.getEmail(),
                "user.name",user.getName(),
                "user.id",user.getId()
        );
    }
}
