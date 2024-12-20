package com.linkshrink.shortner.util.generator;

import com.linkshrink.shortner.customException.KnownException;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;


public abstract class UrlGenerator {

    public abstract String generateShortUrl(Long randomSeed) throws Exception;
    public abstract String generateShortUrl();


}
