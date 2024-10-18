package com.linkshrink.authn.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.linkshrink.authn.Dto.request.UsernamePassword;
import com.linkshrink.authn.configurations.PrivateUserDetails;
import com.linkshrink.authn.entity.Role;
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
        var defaultRole = roleRepository.findByName("ROLE_USER");
        if(defaultRole.isEmpty()){
            var role = new Role();
            role.setName("ROLE_USER");
            defaultRole = java.util.Optional.of(roleRepository.save(role));
        }
        user.setRoles(List.of(defaultRole.orElseThrow()));
        user.setActive(true);
        return userRepository.save(user);
    }

    public String authenticate(UsernamePassword credentials) throws JOSEException {
        var auth = authenticationManager.authenticate(credentials.getToken());
        if(!auth.isAuthenticated()) throw new GenericKnownException("Un authorized");
        var user = userRepository.findByEmail(credentials.getEmail()).orElseThrow();
        return jwtTokenService.getToken(new PrivateUserDetails(user));
    }

    public String getEncryptedToken(UsernamePassword credentials) throws JOSEException {
        var auth = authenticationManager.authenticate(credentials.getToken());
        if(!auth.isAuthenticated()) throw new GenericKnownException("Un authorized");
        var user = userRepository.findByEmail(credentials.getEmail()).orElseThrow();
        return jwtTokenService.getEncryptedToken(new PrivateUserDetails(user));
    }

    public User getUser(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    public User giveRole(int roleId){
        var user = getUser();
        if(user.getRoles().stream().map(Role::getId).anyMatch(i->i==roleId)){
            return user;
        }
        var role = roleRepository.findById(roleId);
        if(role.isEmpty()) throw new GenericKnownException("invalid role id");
        user.getRoles().add(role.get());
        return userRepository.save(user);
    }

    public User removeRole(int roleId){
        var user = getUser();
        if(!user.getRoles().stream().map(Role::getId).anyMatch(i->i==roleId)){
            return user;
        }
        user.getRoles().removeIf(i->i.getId()==roleId);
        return userRepository.save(user);
    }




}
