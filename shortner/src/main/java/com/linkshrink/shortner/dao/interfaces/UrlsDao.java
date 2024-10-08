package com.linkshrink.shortner.dao.interfaces;

import com.linkshrink.shortner.entity.Url;

public interface UrlsDao {

    long save(Url url)throws Exception;

    int delete(Long id);

    Url getUrl(String shortUrl);

    default boolean urlExist(String shortUrl){
        return getUrl(shortUrl)!=null;
    }
}
