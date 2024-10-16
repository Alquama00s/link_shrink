package com.linkshrink.authn.configurations;

import com.linkshrink.authn.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@AllArgsConstructor
@Getter
public class PrivateClientDetails implements UserDetails {

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
}
