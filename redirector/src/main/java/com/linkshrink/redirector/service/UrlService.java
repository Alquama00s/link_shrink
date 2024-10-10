package com.linkshrink.redirector.service;


import com.linkshrink.redirector.client.ShortnerClient;
import com.linkshrink.redirector.entity.Url;
import com.linkshrink.redirector.redis.Cached;
import com.linkshrink.redirector.redis.CachedRaw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    @Autowired
    ShortnerClient shortnerClient;

    @Cached(prefix = "url::")
    public Url getUrl(String shortUrl){
        return shortnerClient.get(shortUrl);
    }

    @CachedRaw(prefix = "raw::url::")
    public String getLongUrl(String shortUrl){
        return shortnerClient.get(shortUrl).getLongUrl();
    }



}
