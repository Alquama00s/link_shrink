package com.linkshrink.authn.Dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Builder
@Data
public class ApiErrorResponse {
    private String message;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Map<String,String> details;

}
