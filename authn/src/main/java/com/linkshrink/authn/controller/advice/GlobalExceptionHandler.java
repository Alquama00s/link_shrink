package com.linkshrink.authn.controller.advice;

import com.linkshrink.authn.Dto.request.ApiErrorResponse;
import com.linkshrink.authn.exceptions.GenericKnownException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleKnownException(MethodArgumentNotValidException ex){
        Map<String,String> detail = new HashMap<>();
        for(var f: ex.getBindingResult().getFieldErrors()){
            detail.put(f.getField(),f.getDefaultMessage());
        }
        return ApiErrorResponse.builder().message("invalid request").details(detail).build();
    }

    @ExceptionHandler(HttpMessageConversionException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrorResponse handleKnownException(HttpMessageConversionException ex){
        return ApiErrorResponse.builder().message("invalid request").build();
    }

    @ExceptionHandler(GenericKnownException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleKnownException(GenericKnownException ex){
        return new ResponseEntity<>(ex.getResponse(),ex.getHttpStatus());
    }

    @ExceptionHandler(DisabledException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiErrorResponse handleKnownException(DisabledException ex){
        return ApiErrorResponse.builder().message(ex.getMessage()).build();
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiErrorResponse handleException(Exception ex){
        log.error(ex.toString());
        return ApiErrorResponse.builder().message("something went wrong").build();
    }

}
