package com.linkshrink.authn.service;


import com.linkshrink.authn.Dto.response.ClientDTO;
import com.linkshrink.authn.entity.Client;
import com.linkshrink.authn.repository.ClientRepository;
import com.linkshrink.authn.repository.RoleRepository;
import com.linkshrink.authn.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ClientService {

    ClientRepository clientRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;

    public ClientDTO generateClient() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findByEmail(auth.getName()).orElseThrow();
        var cliRole = roleRepository.findById(3).orElseThrow();
        var client = Client.builder()
                .clientId(UUID.randomUUID().toString())
                .secret(UUID.randomUUID().toString() + UUID.randomUUID().toString())
                .user(user)
                .roles(List.of(cliRole))
                .accessTokenValiditySec(5*60)
                .refreshTokenValiditySec(5*60)
                .isActive(true)
                .build();
        client.setClientSecret(passwordEncoder.encode(client.getSecret()));
        var savedClient = clientRepository.save(client);

        return new ClientDTO(savedClient.getClientId(), savedClient.getClientSecret());

    }

}
