package com.linkshrink.authn.exceptions;

import com.linkshrink.authn.Dto.request.ApiErrorResponse;

public class GenericKnownException extends RuntimeException {
    public GenericKnownException(String message) {
        super(message);
    }

    public ApiErrorResponse getResponse(){
      return ApiErrorResponse.builder()
              .message(getMessage())
              .build();
    }

}
