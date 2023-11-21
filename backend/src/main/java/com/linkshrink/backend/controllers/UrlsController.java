package com.linkshrink.backend.controllers;


import com.linkshrink.backend.generaor.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/urls")
public class UrlsController extends GenericController{


    private UrlGenerator urlGenerator;

    @Autowired
    public UrlsController(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    @PostMapping("/create")
    public HashMap<String,String> create(@RequestBody HashMap<String,String> request){
        var resp = new HashMap<String,String>();
        resp.put("url",urlGenerator.generateShortUrl());
        return  resp;
    }
}
