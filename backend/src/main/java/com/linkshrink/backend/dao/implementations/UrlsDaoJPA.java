package com.linkshrink.backend.dao.implementations;

import com.linkshrink.backend.customException.KnownException;
import com.linkshrink.backend.dao.interfaces.UrlsDao;
import com.linkshrink.backend.entity.Url;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UrlsDaoJPA implements UrlsDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public long save(Url url)throws Exception {
        if(urlExist(url.getShortUrl())){
            throw new KnownException("short Url already exist");
        }
        entityManager.persist(url);
        return url.getId();
    }

    @Override
    @Transactional
    public int delete(Long id) {
        return entityManager.createQuery("delete from Url u where u.id = :id")
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public Url getUrl(String shortUrl) {
        try {
            return entityManager.createQuery("select u from Url u where u.shortUrl = :shortUrl", Url.class)
                    .setParameter("shortUrl",shortUrl)
                    .getSingleResult();
        }catch (NoResultException e){
            System.out.println("no urls found");
        }
      return null;
    }

}
