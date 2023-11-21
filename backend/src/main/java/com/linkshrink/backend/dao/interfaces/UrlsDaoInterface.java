package com.linkshrink.backend.dao.interfaces;

import com.linkshrink.backend.entity.Urls;

public interface UrlsDaoInterface {

    int save(Urls url);

    int delete(int id);
}
