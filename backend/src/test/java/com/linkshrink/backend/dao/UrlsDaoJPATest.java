package com.linkshrink.backend.dao;

import com.linkshrink.backend.customException.KnownException;
import com.linkshrink.backend.dao.implementations.UrlsDaoJPA;
import com.linkshrink.backend.entity.Url;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
public class UrlsDaoJPATest {
    @Autowired
    private UrlsDaoJPA urlsDaoJPA;

    @Autowired
    private JdbcTemplate jdbc;

    @BeforeEach
    public void setUpMockData()throws Exception{
        String[] shortUrl={"alfa-beta","delta","gamma"};
        for(var s: shortUrl){
            Url url = new Url();
            url.setLongUrl("https://cloud.google.com/sql/docs/postgres/users");
            url.setShortUrl(s);
            urlsDaoJPA.save(url);
        }
    }

    @AfterEach
    public void deleteMockData(){
        jdbc.execute("delete from urls");
    }

    @Test
    @DisplayName(value = "url objects are saved in database and are retrieved")
    public void saveTest()throws Exception{
        Url url = new Url();
        url.setLongUrl("https://cloud.google.com/sql/docs/postgres/users");
        url.setShortUrl("aaaaaaa");
        urlsDaoJPA.save(url);
        Assertions.assertInstanceOf(Long.class,url.getId());
        Assertions.assertNotNull(urlsDaoJPA.getUrl("aaaaaaa"));

    }

    @Test
    @DisplayName(value = "url objects are being retrieved and deleted from db")
    public void deleteTest(){
        Url delta = urlsDaoJPA.getUrl("delta");
        Assertions.assertNotNull(delta);
        urlsDaoJPA.delete(delta.getId());
        delta = urlsDaoJPA.getUrl("delta");
        Assertions.assertNull(delta);
    }

    @Test
    @DisplayName(value = "url objects with duplicate short urls are not being saved")
    public void duplicateUrlTest(){
        Url url = new Url();
        url.setLongUrl("https://cloud.google.com/sql/docs/postgres/users");
        url.setShortUrl("delta");
        Assertions.assertThrows(KnownException.class,()->urlsDaoJPA.save(url));
    }
    @Test
    @DisplayName(value = "url objects with malformed short urls are not being saved")
    public void malformedShortUrlTest(){
        Url url = new Url();
        url.setLongUrl("https://cloud.google.com/sql/docs/postgres/users");
        url.setShortUrl("delta/");
        Assertions.assertThrows(ConstraintViolationException.class,()->urlsDaoJPA.save(url));
        url.setShortUrl("delta@");
        Assertions.assertThrows(ConstraintViolationException.class,()->urlsDaoJPA.save(url));
        url.setShortUrl("delta#");
        Assertions.assertThrows(ConstraintViolationException.class,()->urlsDaoJPA.save(url));

    }

}
