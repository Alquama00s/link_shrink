package com.linkshrink.redirector.utils.token;

import com.linkshrink.redirector.dto.ClientCredentials;
import com.linkshrink.redirector.dto.Token;
import lombok.Builder;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;

public class TokenManager {

    private Token token;

    private Instant expiry;

    @Setter
    private ClientCredentials clientCredentials;
    @Setter
    private Duration expiryDuration;
    @Setter
    private JWTAuthenticator jwtAuthenticator;

    public Token getToken() {
        if (expiry.isAfter(new Timestamp(System.currentTimeMillis()).toInstant())) {
            return token;
        }
        token = jwtAuthenticator.authenticate(clientCredentials);
        expiry = new Timestamp(System.currentTimeMillis()).toInstant().plus(expiryDuration);
        return token;
    }

}
