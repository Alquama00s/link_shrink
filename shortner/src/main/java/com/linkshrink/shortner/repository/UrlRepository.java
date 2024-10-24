package com.linkshrink.shortner.repository;

import com.linkshrink.shortner.entity.Url;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UrlRepository extends CrudRepository<Url,Integer> {

    List<Url> findByCreatedBy(int userId);
}
