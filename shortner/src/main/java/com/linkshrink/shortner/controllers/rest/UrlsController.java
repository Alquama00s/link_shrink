package com.linkshrink.shortner.controllers.rest;


import com.linkshrink.shortner.controllers.advice.ApiControllerAdvice;
import com.linkshrink.shortner.customException.KnownException;
import com.linkshrink.shortner.dao.interfaces.UrlsDao;
import com.linkshrink.shortner.entity.Url;
import com.linkshrink.shortner.util.generator.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Duration;

@RestController
@RequestMapping("/api/urls")
public class UrlsController extends ApiControllerAdvice{




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
    public Url create(@RequestBody Url url) throws Exception{

        if(url.getShortUrl()!=null&&urlDao.urlExist(url.getShortUrl())){

            throw new KnownException("Url already exist");
        }

        if(url.getShortUrl()==null){
            //generating random urls if collision occurs db wil throw error
            url.setShortUrl(urlGenerator.generateShortUrl());
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
    public Url get(@RequestParam(value = "short_url") String shortUrl){
        return urlDao.getUrl(shortUrl);
    }
}
