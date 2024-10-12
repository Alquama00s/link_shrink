package com.linkshrink.authn.service;


import com.linkshrink.authn.Dto.users.UsernamePassword;
import com.linkshrink.authn.entity.User;
import com.linkshrink.authn.exceptions.GenericKnownException;
import com.linkshrink.authn.repository.RoleRepository;
import com.linkshrink.authn.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;

    public User registerUser(User user){
        var existingUser = userRepository.findByEmail(user.getEmail());
        if(existingUser.isPresent()) throw new GenericKnownException("Email already exist");
        user.setPwd(passwordEncoder.encode(user.getPassword()));
        var defaultRole = roleRepository.findById(1).orElseThrow();
        user.setRoles(List.of(defaultRole));
        user.setActive(true);
        return userRepository.save(user);
    }

    public boolean authenticate(UsernamePassword credentials){
        var auth = authenticationManager.authenticate(credentials.getToken());
        return auth.isAuthenticated();
    }


}
