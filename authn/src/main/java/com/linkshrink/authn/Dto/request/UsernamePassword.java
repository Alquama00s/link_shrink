package com.linkshrink.authn.Dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Data
public class UsernamePassword {

    @Email
    private String email;
    @Size(min = 5, max = 20)
    @NotBlank
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private boolean unEncrypted;

    public UsernamePasswordAuthenticationToken getToken(){
        return new UsernamePasswordAuthenticationToken(email,password);
    }

}
