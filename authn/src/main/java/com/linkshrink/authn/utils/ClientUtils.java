package com.linkshrink.authn.utils;

import com.linkshrink.authn.entity.Client;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.sql.Timestamp;

public final class ClientUtils {

    public static RegisteredClient registeredClient(Client client){
        return RegisteredClient.withId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .clientSecretExpiresAt(client.getExpiresOn().toInstant())
                .scopes(i->i.addAll(client.getSimpleRoles()))
                .build();
    }

    public static Client client(RegisteredClient registeredClient){
        var c = new Client();
        c.setClientId(registeredClient.getClientId());
        c.setClientSecret(registeredClient.getClientSecret());
        c.setActive(true);
        c.setExpiresOn(Timestamp.from(registeredClient.getClientSecretExpiresAt()));
        c.setRoles(RoleUtils.getRoles(registeredClient.getScopes()));
        return c;
    }
}
