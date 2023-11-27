package com.linkshrink.backend.controllers.rest;


import com.linkshrink.backend.controllers.advice.ApiControllerAdvice;
import com.linkshrink.backend.customException.KnownException;
import com.linkshrink.backend.dao.interfaces.UrlsDao;
import com.linkshrink.backend.entity.Urls;
import com.linkshrink.backend.util.generaor.UrlGenerator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;

@RestController
@RequestMapping("/api/urls")
public class UrlsController extends ApiControllerAdvice {




    private UrlGenerator urlGenerator;
    private UrlsDao urlDao;

    @Value("${fallbacks.expiryDuration}")
    private String defaultExpiryInterval;

    @Autowired
    public void setUrlDao(UrlsDao urlDao) {
        this.urlDao = urlDao;
    }


    @Autowired
    public void setUrlGenerator(UrlGenerator urlGenerator) {
        this.urlGenerator = urlGenerator;
    }

    @PostMapping("/create")
    public Urls create(@RequestBody Urls url) throws Exception{

        if(url.getShortUrl()!=null&&urlGenerator.urlExist(url.getShortUrl())){

            throw new KnownException("Url already exist");
        }

        if(url.getShortUrl()==null){
            url.setShortUrl(urlGenerator.getShortUrl());
            var expAfter=Timestamp
                    .from(new Timestamp(System.currentTimeMillis())
                            .toInstant()
                            .plus(Duration.parse(defaultExpiryInterval)));
            url.setExpiryAfter(expAfter);
            url.setGenerated(true);
        }
        urlDao.save(url);
        return  url;
    }

    @GetMapping("/get")
    public Urls get(@RequestParam(value = "short_url") String shortUrl){
        return urlDao.getUrl(shortUrl);
    }
}
