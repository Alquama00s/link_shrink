package com.linkshrink.redirector.utils.token;

import com.linkshrink.redirector.dto.ClientCredentials;
import java.time.Duration;

public class TokenManagerBuilder {

    TokenManager tokenManager = new TokenManager();

    ClientCredentials clientCredentials= new ClientCredentials();
    Duration expiryDuration;
    JWTAuthenticator jwtAuthenticator;

    public TokenManagerBuilder clientId(String clientId){
        clientCredentials.setClientId(clientId);
        return  this;
    }

    public TokenManagerBuilder clientSecret(String clientSecret){
        clientCredentials.setClientSecret(clientSecret);
        return this;
    }

    public TokenManagerBuilder jwtAuthenticator(JWTAuthenticator jwtAuthenticator){
        this.jwtAuthenticator=jwtAuthenticator;
        return this;
    }

    public TokenManagerBuilder expiryDuration(Duration expiryDuration){
        this.expiryDuration = expiryDuration;
        return this;
    }

    public TokenManager build(){
        tokenManager.setExpiryDuration(expiryDuration);
        tokenManager.setClientCredentials(clientCredentials);
        tokenManager.setJwtAuthenticator(jwtAuthenticator);
        return tokenManager;
    }

}
