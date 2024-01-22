package com.linkshrink.backend.dao;

import com.linkshrink.backend.dao.implementations.UrlsDaoJPA;
import com.linkshrink.backend.entity.Url;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UrlsDaoJPATest {
    @Autowired
    private UrlsDaoJPA urlsDaoJPA;


    @Test
    @DisplayName(value = "url objects are saved in database")
    public void saveTest(){
        Url url = new Url();
        url.setLongUrl("https://cloud.google.com/sql/docs/postgres/users");
        url.setShortUrl("aaaaaaa");
        urlsDaoJPA.save(url);
        System.out.println(url.getId());

        Assertions.assertNotNull(urlsDaoJPA.getUrl("aaaaaaa"));

    }
}
