package com.linkshrink.authn.repository;

import com.linkshrink.authn.entity.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client,Integer> {

    Optional<Client> findByClientId(String clientId);

}
