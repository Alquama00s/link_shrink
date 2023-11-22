package com.linkshrink.backend.dao.interfaces;

import com.linkshrink.backend.entity.Urls;

public interface UrlsDao {

    long save(Urls url);

    int delete(int id);
}
