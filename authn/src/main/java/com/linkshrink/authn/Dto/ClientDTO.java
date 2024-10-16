package com.linkshrink.authn.Dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
@AllArgsConstructor
public class ClientDTO {

    private String clientId;
    private String clientSecret;

    @JsonIgnore
    public UsernamePasswordAuthenticationToken getToken(){
        return new UsernamePasswordAuthenticationToken(clientId,clientSecret);
    }
}
