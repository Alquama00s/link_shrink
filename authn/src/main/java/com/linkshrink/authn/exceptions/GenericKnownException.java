package com.linkshrink.authn.exceptions;

import com.linkshrink.authn.Dto.request.ApiErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class GenericKnownException extends RuntimeException {
    public GenericKnownException(String message) {
        super(message);
        httpStatus=HttpStatus.BAD_REQUEST;
    }

    public GenericKnownException(String message,HttpStatus httpStatus) {
        super(message);
        this.httpStatus=httpStatus;
    }

    private HttpStatus httpStatus;


    public ApiErrorResponse getResponse(){
      return ApiErrorResponse.builder()
              .message(getMessage())
              .build();
    }

}
