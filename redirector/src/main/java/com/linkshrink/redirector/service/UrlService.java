package com.linkshrink.redirector.service;


import com.linkshrink.redirector.FallbackConfig;
import com.linkshrink.redirector.client.ShortnerClient;
import com.linkshrink.redirector.entity.Url;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    ShortnerClient shortnerClient;

//    @Cacheable(cacheNames = "urls",key = "#shortUrl")
    public Url getUrl(String shortUrl){
        return shortnerClient.get(shortUrl);
    }



}
