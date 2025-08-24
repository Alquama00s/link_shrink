package com.linkshrink.shortner.controllers.rest;


import com.linkshrink.shortner.controllers.advice.ApiControllerAdvice;
import com.linkshrink.shortner.customException.KnownException;
import com.linkshrink.shortner.dao.interfaces.UrlsDao;
import com.linkshrink.shortner.entity.Url;
import com.linkshrink.shortner.service.UrlsService;
import com.linkshrink.shortner.util.generator.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@CrossOrigin(originPatterns = "${cors.origins:http://localhost*}")
@RestController
@RequestMapping("/api/urls")
public class UrlsController extends ApiControllerAdvice{

    @Autowired
    private UrlsService urlsService;


    @PostMapping("/create")
    public Url create(@RequestBody Url url) throws Exception{
        return  urlsService.createUrl(url);
    }

    @GetMapping("/get")
    public Url get(@RequestParam(value = "short_url") String shortUrl) throws KnownException {
        return urlsService.getUrl(shortUrl);
    }

    @GetMapping("/")
    public Map<String,Object> getAllUrls(){
        return Map.of("urls",urlsService.getAllUrl());
    }
}
