package com.linkshrink.authn.controller;


import com.linkshrink.authn.Dto.users.UsernamePassword;
import com.linkshrink.authn.entity.User;
import com.linkshrink.authn.service.UserService;
import com.linkshrink.authn.validations.groups.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
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
    public boolean authenticate(@RequestBody @Validated UsernamePassword credentials){
        return userService.authenticate(credentials);
    }

    @GetMapping("/resource")
    public Map<String,String> getProtectedResource(){
        return Map.of("protected","yes ok");
    }

}
