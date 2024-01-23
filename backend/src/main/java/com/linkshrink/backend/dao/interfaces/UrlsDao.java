package com.linkshrink.backend.dao.interfaces;

import com.linkshrink.backend.entity.Url;

public interface UrlsDao {

    long save(Url url)throws Exception;

    int delete(Long id);

    Url getUrl(String shortUrl);

    default boolean urlExist(String shortUrl){
        return getUrl(shortUrl)!=null;
    }
}
