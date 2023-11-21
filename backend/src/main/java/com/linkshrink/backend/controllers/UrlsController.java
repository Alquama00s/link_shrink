package com.linkshrink.backend.controllers;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/urls")
public class UrlsController {

    @PostMapping("/create")
    public HashMap<String,String> create(@RequestBody HashMap<String,String> request){

    }
}
