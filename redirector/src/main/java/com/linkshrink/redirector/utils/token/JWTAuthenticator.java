package com.linkshrink.redirector.utils.token;

import com.linkshrink.redirector.dto.ClientCredentials;
import com.linkshrink.redirector.dto.Token;
import org.springframework.web.bind.annotation.RequestBody;

public interface JWTAuthenticator {
    public Token authenticate(@RequestBody ClientCredentials clientCredentials);
}
