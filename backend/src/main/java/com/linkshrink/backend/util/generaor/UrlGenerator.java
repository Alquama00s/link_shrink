package com.linkshrink.backend.util.generaor;

import com.linkshrink.backend.customException.KnownException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;


public abstract class UrlGenerator {



    protected EntityManager entityManager;


    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected abstract String generateShortUrl() throws Exception;

    public boolean urlExist(String url){
        long cnt = (long)entityManager.createQuery("select count(*) from Urls u where u.shortUrl = :url and u.generated=true")
                .setParameter("url",url)
                .getSingleResult();
        return cnt!=0;
    }


    @Transactional
    protected void deleteExpiredUrls(){
        entityManager.createQuery("delete from Urls where expiryAfter <= :now")
                .setParameter("now", new Timestamp(System.currentTimeMillis()))
                .executeUpdate();
    }


    public final String getShortUrl()throws Exception{
        deleteExpiredUrls();
        String url = generateShortUrl();
        if(urlExist(url)){
            throw new KnownException("Generated Url capacity reached");
        }
        return url;
    }


}
