package com.linkshrink.backend.dao.implementations;

import com.linkshrink.backend.dao.interfaces.UrlsDao;
import com.linkshrink.backend.entity.Url;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UrlsDaoJPA implements UrlsDao {

    @Autowired
    private EntityManager entityManager;

    @Override
    @Transactional
    public long save(Url url) {
        entityManager.persist(url);
        return url.getId();
    }

    @Override
    @Transactional
    public int delete(int id) {
        return entityManager.createQuery("delete from Urls u where u.id = :id")
                .setParameter("id",id)
                .executeUpdate();
    }

    @Override
    public Url getUrl(String shortUrl) {
        return entityManager.createQuery("select u from Urls u where u.shortUrl = :shortUrl", Url.class)
                .setParameter("shortUrl",shortUrl)
                .getSingleResult();
    }

}
