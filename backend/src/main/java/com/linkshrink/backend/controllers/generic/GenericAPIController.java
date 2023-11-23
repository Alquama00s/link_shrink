package com.linkshrink.backend.controllers.generic;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


public class GenericAPIController {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String,String> handleException(Exception ex){
         var mp =new HashMap<String,String>();
         mp.put("error",ex.getMessage());
         return mp;
    }
}
