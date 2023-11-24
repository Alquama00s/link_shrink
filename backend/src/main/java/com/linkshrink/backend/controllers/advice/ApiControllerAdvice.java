package com.linkshrink.backend.controllers.advice;

import com.linkshrink.backend.entity.ApiErrorResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


//@ControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    public ApiControllerAdvice() {
        System.out.println("adv created");
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleDatabaseException(DataAccessException ex){
        return new ApiErrorResponse("Resource not found");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(Exception ex){
       return new ApiErrorResponse("Unexpected error");
    }

}
