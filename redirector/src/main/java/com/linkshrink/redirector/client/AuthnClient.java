package com.linkshrink.redirector.client;

import com.linkshrink.redirector.dto.ClientCredentials;
import com.linkshrink.redirector.dto.Token;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "authn",url = "${urls.authn:http://localhost:8082}",path = "/oauth")
public interface AuthnClient {

    @PostMapping("/token")
    public Token authenticate(@RequestBody ClientCredentials clientCredentials);

}
