package com.linkshrink.shortner.controllers.web;


import com.linkshrink.shortner.controllers.advice.ViewControllerAdvice;
import com.linkshrink.shortner.dao.interfaces.UrlsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

//@RestController
//@RequestMapping("")
public class RedirectController extends ViewControllerAdvice {


    private UrlsDao urlDao;


    @Autowired
    public void setUrlDao(UrlsDao urlDao) {
        this.urlDao = urlDao;
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl){
        return new RedirectView(urlDao.getUrl(shortUrl).getLongUrl());
    }
}
