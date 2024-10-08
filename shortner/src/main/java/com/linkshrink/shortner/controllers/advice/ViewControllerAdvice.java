package com.linkshrink.shortner.controllers.advice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.view.RedirectView;



//@ControllerAdvice(basePackages = {"com.linkshrink.shortner.controllers.web"})
public class ViewControllerAdvice {

    @Value("${fallbacks.viewUrl}")
    private String FALLBACK_VIEW_URL;

    @ExceptionHandler(Exception.class)
    public RedirectView handleException(Exception ex){
        var redView = new RedirectView();
        redView.setUrl(FALLBACK_VIEW_URL);
        return redView;

    }
}
