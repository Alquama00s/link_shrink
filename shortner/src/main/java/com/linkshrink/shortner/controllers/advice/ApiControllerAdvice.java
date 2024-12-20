package com.linkshrink.shortner.controllers.advice;

import com.linkshrink.shortner.customException.KnownException;
import com.linkshrink.shortner.entity.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
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


    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleKnownException(ConstraintViolationException ex){
        var cons = ex.getConstraintViolations().iterator().next();
        return new ApiErrorResponse(cons.getMessage());
    }


    @ExceptionHandler(KnownException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleKnownException(KnownException ex){
        return new ApiErrorResponse(ex.getMessage());
    }


    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrorResponse handleDatabaseException(DataAccessException ex){
        return new ApiErrorResponse("Resource mismatch");
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleRuntimeException(RuntimeException ex){
        if(ex.getCause()==null)return handleException(ex);
        return new ApiErrorResponse(ex.getCause().getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleException(Exception ex){
       return new ApiErrorResponse("Unexpected error");
    }

}
