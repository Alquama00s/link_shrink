package com.linkshrink.authn.Dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientDTO {

    private String clientId;
    private String clientSecret;

}
