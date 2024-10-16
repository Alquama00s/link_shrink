package com.linkshrink.authn.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.linkshrink.authn.Dto.ClientDTO;
import com.linkshrink.authn.configurations.PrivateClientDetails;
import com.linkshrink.authn.configurations.PrivateUserDetails;
import com.linkshrink.authn.entity.Client;
import com.linkshrink.authn.entity.User;
import com.linkshrink.authn.entity.builders.ClientBuilder;
import com.linkshrink.authn.exceptions.GenericKnownException;
import com.linkshrink.authn.repository.ClientRepository;
import com.linkshrink.authn.repository.RoleRepository;
import com.linkshrink.authn.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class ClientService {

    ClientRepository clientRepository;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    AuthenticationManager authenticationManager;
    JwtTokenService jwtTokenService;


    public ClientDTO generateClient() {
        var user = extractLoggedInUser();
        var cliRole = roleRepository.findById(3).orElseThrow();

        var client = new Client();
        client.setClientId(UUID.randomUUID().toString());
        client.setClientSecret(UUID.randomUUID() + UUID.randomUUID().toString());
        client.setUserId(user.getId());
        client.setRoles(List.of(cliRole));
        client.setAccessTokenValiditySec(5*60);
        client.setRefreshTokenValiditySec(5*60);
        client.setActive(true);

        var clientDTO = new ClientDTO(client.getClientId(), client.getClientSecret());

        client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
       clientRepository.save(client);

        return clientDTO;
    }

    public List<Client> getAllClients(){
        var user = extractLoggedInUser();
        var c = clientRepository.findByUserId(user.getId());
        log.info(c.toString());
        return c;
    }


    public String authenticate(ClientDTO credentials) throws JOSEException {
        var auth = authenticationManager.authenticate(credentials.getToken());
        if(!auth.isAuthenticated()) throw new GenericKnownException("Un authorized");
        var client = clientRepository.findByClientId(credentials.getClientId()).orElseThrow();
        return jwtTokenService.getToken(new PrivateClientDetails(client));
    }


    public String getEncryptedToken(ClientDTO credentials) throws JOSEException {
        var auth = authenticationManager.authenticate(credentials.getToken());
        if(!auth.isAuthenticated()) throw new GenericKnownException("Un authorized");
        var client = clientRepository.findByClientId(credentials.getClientId()).orElseThrow();
        return jwtTokenService.getEncryptedToken(new PrivateClientDetails(client));
    }


    public boolean deleteClient(String clientId){
        var user = extractLoggedInUser();
        var client = clientRepository.findByClientId(clientId).orElseThrow();
        if(client.getUserId()!=user.getId()) throw new GenericKnownException("could not delete", HttpStatus.UNAUTHORIZED);
        clientRepository.delete(client);
        return true;
    }

    private User extractLoggedInUser(){
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

}
