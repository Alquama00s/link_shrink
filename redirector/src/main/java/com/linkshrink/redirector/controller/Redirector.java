package com.linkshrink.redirector.controller;

import com.linkshrink.redirector.configurations.FallbackConfig;
import com.linkshrink.redirector.service.UrlService;
import com.linkshrink.redirector.utils.token.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Slf4j
@RestController
public class Redirector {

    @Autowired
    UrlService urlService;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    FallbackConfig fallbackConfig;

    @GetMapping("/{shortUrl}")
    public RedirectView redirect(@PathVariable String shortUrl) {
        return new RedirectView(urlService.getLongUrl(shortUrl));
    }


    @ExceptionHandler(Exception.class)
    public RedirectView handleException(Exception ex) {
        log.error(ex.getMessage());
        for (int i = 0; i < Math.min(10, ex.getStackTrace().length); i++) {
            log.error(ex.getStackTrace()[i].toString());
        }
        var red = new RedirectView(fallbackConfig.viewUrl());
        red.setStatusCode(HttpStatusCode.valueOf(307));
        return red;
    }
}
