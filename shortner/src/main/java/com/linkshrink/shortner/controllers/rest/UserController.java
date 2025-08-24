package com.linkshrink.shortner.controllers.rest;


import com.linkshrink.shortner.dto.User;
import com.linkshrink.shortner.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(originPatterns = "${cors.origins:http://localhost*}")
@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    UserService userService;

    @GetMapping("/profile")
    public User getProfile(){
        return userService.getLoggedInUser();
    }
}
