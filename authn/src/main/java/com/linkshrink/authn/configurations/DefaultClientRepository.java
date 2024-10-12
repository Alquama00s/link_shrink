package com.linkshrink.authn.configurations;

import com.linkshrink.authn.exceptions.GenericKnownException;
import com.linkshrink.authn.repository.ClientRepository;
import com.linkshrink.authn.utils.ClientUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class DefaultClientRepository implements RegisteredClientRepository {

    ClientRepository clientRepository;

    @Override
    public void save(RegisteredClient registeredClient) {
        log.info("not implemented");
    }

    @Override
    public RegisteredClient findById(String id) {
        log.info("find by id called");
        var client = clientRepository.findByClientId(id);
        if(client.isEmpty()) throw new GenericKnownException("");
        return ClientUtils.registeredClient(client.get()) ;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = clientRepository.findByClientId(clientId);
        if(client.isEmpty()) throw new GenericKnownException("Client not found");
        return ClientUtils.registeredClient(client.get()) ;
    }
}
