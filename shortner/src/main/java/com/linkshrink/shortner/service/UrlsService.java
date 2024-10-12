package com.linkshrink.shortner.service;


import com.linkshrink.shortner.customException.KnownException;
import com.linkshrink.shortner.dao.interfaces.UrlsDao;
import com.linkshrink.shortner.entity.Url;
import com.linkshrink.shortner.util.generator.UrlGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;

@Service
public class UrlsService {

    @Autowired
    private UrlsDao urlDao;
    @Autowired
    private UrlGenerator urlGenerator;
    @Value("${fallbacks.expiryDuration}")
    private String defaultExpiryInterval;

    public Url getUrl(String shortUrl) throws KnownException {
        var res = urlDao.getUrl(shortUrl);
        if(res.getExpiryAfter().after(new Timestamp(System.currentTimeMillis()))){
            urlDao.delete(res.getId());
            throw new KnownException("Url expired");
        }
        return res;
    }

    public Url createUrl(Url url) throws Exception {
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
        return url;
    }


}
