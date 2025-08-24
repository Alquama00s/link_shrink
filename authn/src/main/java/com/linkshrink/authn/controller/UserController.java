package com.linkshrink.authn.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.linkshrink.authn.Dto.TokenDto;
import com.linkshrink.authn.Dto.request.UsernamePassword;
import com.linkshrink.authn.entity.Role;
import com.linkshrink.authn.entity.User;
import com.linkshrink.authn.service.ClientService;
import com.linkshrink.authn.service.UserService;
import com.linkshrink.authn.validations.groups.RequestDTO;
import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    UserService userService;


    @PostMapping("/register")
    public User createUser(@RequestBody @Validated(RequestDTO.class) User user){
       return userService.registerUser(user);
    }

    @PostMapping("/login")
    public TokenDto authenticate(@RequestBody @Validated UsernamePassword credentials) throws JOSEException {
        return credentials.isUnEncrypted()?
                new TokenDto(userService.authenticate(credentials)):
                new TokenDto(userService.getEncryptedToken(credentials));
    }

    @GetMapping("/profile")
    public User getProfile(){
        return userService.getUser();
    }

    @GetMapping("/resource")
    public Map<String,String> getProtectedResource(){
        return Map.of("protected","yes ok");
    }

    @PostMapping("/role/add")
    public User addRole(@RequestBody Role role){
        return userService.giveRole(role.getId());
    }

    @PostMapping("/role/remove")
    public User removeRole(@RequestBody Role role){
        return userService.removeRole(role.getId());
    }

}
