package com.linkshrink.authn.controller;

import com.linkshrink.authn.Dto.ClientDTO;
import com.linkshrink.authn.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    ClientService clientService;

    @GetMapping("/register")
    public ClientDTO createNewClient(){
        return clientService.generateClient();
    }

}
