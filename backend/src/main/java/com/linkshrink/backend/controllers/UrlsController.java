package com.linkshrink.backend.controllers;


import com.linkshrink.backend.controllers.generic.GenericAPIController;
import com.linkshrink.backend.dao.interfaces.UrlsDao;
import com.linkshrink.backend.entity.Urls;
import com.linkshrink.backend.generaor.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.HashMap;

@RestController
@RequestMapping("/api/urls")
public class UrlsController extends GenericAPIController {


    private UrlGenerator urlGenerator;
    private UrlsDao urlDao;


    @Autowired
    public void setUrlDao(UrlsDao urlDao) {
        this.urlDao = urlDao;
    }

    @Autowired
    public UrlsController(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    @PostMapping("/create")
    public Urls create(@RequestBody HashMap<String,String> request){
        var resp = new HashMap<String,String>();
        Urls url;
        var now = new Timestamp(System.currentTimeMillis());
        if(request.get("expiry")==null){
            url = new Urls(request.get("url"),urlGenerator.generateShortUrl(),now);
        }else{
            url = new Urls(request.get("url"),urlGenerator.generateShortUrl(),now, Timestamp.from(now.toInstant().plus(Duration.parse(request.get("expiry")))));
        }
        urlDao.save(url);
        return  url;
    }
}
