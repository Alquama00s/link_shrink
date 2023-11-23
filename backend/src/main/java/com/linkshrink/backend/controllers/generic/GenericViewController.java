package com.linkshrink.backend.controllers.generic;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

public class GenericViewController {

    @Value("${fallbacks.viewUrl}")
    private String FALLBACK_VIEW_URL;

    @ExceptionHandler(Exception.class)
    public RedirectView handleException(Exception ex){
        var redView = new RedirectView();

        redView.setUrl(FALLBACK_VIEW_URL);

        return redView;

    }
}
