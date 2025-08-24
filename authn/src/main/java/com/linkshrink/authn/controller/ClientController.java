package com.linkshrink.authn.controller;

import com.linkshrink.authn.Dto.ClientDTO;
import com.linkshrink.authn.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(originPatterns = "${cors.origins:http://localhost*}")
@RestController
@RequestMapping("/api/client")
@AllArgsConstructor
public class ClientController {

    ClientService clientService;

    @GetMapping("/register")
    public ClientDTO createNewClient(){
        return clientService.generateClient();
    }

    @GetMapping("/clients")
    public Map<String,Object> getClients(){
        return Map.of("clients",clientService.getAllClients());
    }

    @DeleteMapping("/{clientId}")
    public Map<String,Object> deleteClient(@PathVariable String clientId){
        return Map.of("status",clientService.deleteClient(clientId),
        "message","deleted"
        );
    }



}
