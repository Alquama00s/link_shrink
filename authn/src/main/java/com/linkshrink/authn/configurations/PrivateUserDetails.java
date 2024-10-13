package com.linkshrink.authn.configurations;

import com.linkshrink.authn.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
@AllArgsConstructor
public class PrivateUserDetails implements UserDetails {
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


}
