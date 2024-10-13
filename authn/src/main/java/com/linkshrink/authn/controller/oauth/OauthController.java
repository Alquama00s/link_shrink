package com.linkshrink.authn.controller.oauth;


import com.linkshrink.authn.Dto.TokenDto;
import com.linkshrink.authn.Dto.response.ClientDTO;
import com.linkshrink.authn.service.JwtTokenService;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.KeySourceException;
import com.nimbusds.jose.jwk.JWKMatcher;
import com.nimbusds.jose.jwk.JWKSelector;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    JWKSource<SecurityContext> jwkSource;

    @Autowired
    JwtTokenService jwtTokenService;

    HashMap<String, Object> publicKeys;

    @PostConstruct
    public void init() throws KeySourceException {
        publicKeys = new HashMap<>();
        var matcher = new JWKMatcher.Builder()
                .build();
        var keys = jwkSource.get(new JWKSelector(matcher), null);
        for (var i : keys) {
            var publicParams = new HashMap<String, String>((Map<? extends String, ? extends String>) i.toPublicJWK().getRequiredParams());
            publicParams.put("kid", i.getKeyID());
            publicKeys.put(i.getKeyID(), publicParams);
        }
    }


    @GetMapping("/jwks")
    public Map<String, Object> getAvailableKeys() throws KeySourceException {
        return Map.of("keys", publicKeys.values());
    }


    @PostMapping("/introspect")
    public Map<String, Object> introspect(@RequestBody Map<String, Object> request) {
        String token = request.get("token").toString();
        return Map.of(
                "token", token,
                "verified", jwtTokenService.introspect(token)
        );
    }

    @PostMapping("/revoke")
    public Map<String, Object> revoke(@RequestBody Map<String, Object> request) throws ParseException {
        String token = request.get("token").toString();
        jwtTokenService.revoke(token);
        return Map.of(
                "token", token,
                "revoked", true
        );
    }

    @PostMapping("/token")
    public TokenDto getToken(@RequestBody ClientDTO clientDTO){

    }


}
