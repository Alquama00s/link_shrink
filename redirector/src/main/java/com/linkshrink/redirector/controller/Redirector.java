package com.linkshrink.redirector.controller;

import com.linkshrink.redirector.client.ShortnerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class Redirector {

    @Autowired
    ShortnerClient shortnerClient;

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl){
        return new RedirectView(shortnerClient.get(shortUrl).getLongUrl());
    }
}
