package com.linkshrink.authn.repository;

import com.linkshrink.authn.entity.Client;
import com.linkshrink.authn.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client,Integer> {

    Optional<Client> findByClientId(String clientId);

    List<Client> findByUserId(int userId);

}
