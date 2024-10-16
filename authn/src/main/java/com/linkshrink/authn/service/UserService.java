package com.linkshrink.authn.service;


import com.linkshrink.authn.Dto.request.UsernamePassword;
import com.linkshrink.authn.configurations.PrivateUserDetails;
import com.linkshrink.authn.entity.User;
import com.linkshrink.authn.exceptions.GenericKnownException;
import com.linkshrink.authn.repository.RoleRepository;
import com.linkshrink.authn.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtTokenService jwtTokenService;

    public User registerUser(User user){
        var existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent()) throw new GenericKnownException("Email already exist");
        user.setPwd(passwordEncoder.encode(user.getPassword()));
        var defaultRole = roleRepository.findById(1).orElseThrow();
        user.setRoles(List.of(defaultRole));
        user.setActive(true);
        return userRepository.save(user);
    }

    public String authenticate(UsernamePassword credentials) throws JOSEException {
        var auth = authenticationManager.authenticate(credentials.getToken());
        if(!auth.isAuthenticated()) throw new GenericKnownException("Un authorized");
        var user = userRepository.findByEmail(credentials.getEmail()).orElseThrow();
        return jwtTokenService.getToken(new PrivateUserDetails(user));
    }

    public User getUser(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }


}
