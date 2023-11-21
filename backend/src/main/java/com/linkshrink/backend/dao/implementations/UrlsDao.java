package com.linkshrink.backend.dao.implementations;

import com.linkshrink.backend.dao.interfaces.UrlsDaoInterface;
import com.linkshrink.backend.entity.Urls;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;

public class UrlsDao implements UrlsDaoInterface {

    @Autowired
    private EntityManager entityManager;

    @Override
    public int save(Urls url) {
        entityManager.persist(url);
        return url.getId();
    }

    @Override
    public int delete(int id) {
        return entityManager.createQuery("delete from urls u where u.id = :id")
                .setParameter("id",id)
                .executeUpdate();
    }
}
