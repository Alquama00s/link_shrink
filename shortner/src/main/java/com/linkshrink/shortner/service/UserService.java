package com.linkshrink.shortner.service;

import com.linkshrink.shortner.configurations.JwtVerifier;
import com.linkshrink.shortner.dto.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class UserService {

    JwtVerifier jwtVerifier;

    public User getLoggedInUser(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var pii = (Map<String,Object>) auth.getDetails();
        var user = new User();
        user.setName(pii.getOrDefault("user.name","N/A").toString());
        user.setEmail(pii.getOrDefault("user.username","N/A").toString());
        user.setId(Integer.parseInt(pii.getOrDefault("user.id",0).toString()));
        user.setAuthorities(auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList());
        return user;
    }

}
