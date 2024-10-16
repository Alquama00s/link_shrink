package com.linkshrink.authn.configurations;

import com.linkshrink.authn.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class ClientDetailsService implements UserDetailsService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       var client = clientRepository.findByClientId(username).orElseThrow(()->new UsernameNotFoundException("Client not found"));

        return new PrivateClientDetails(client);
    }
}
