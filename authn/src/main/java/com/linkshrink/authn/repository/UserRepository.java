package com.linkshrink.authn.repository;

import com.linkshrink.authn.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Integer> {

    Optional<User> findByEmail(String email);

}
