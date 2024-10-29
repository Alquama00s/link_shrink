package com.linkshrink.authn.configurations;

import com.linkshrink.authn.Dto.ClientDTO;
import com.linkshrink.authn.entity.Client;
import com.linkshrink.authn.entity.Role;
import com.linkshrink.authn.entity.User;
import com.linkshrink.authn.repository.ClientRepository;
import com.linkshrink.authn.repository.RoleRepository;
import com.linkshrink.authn.repository.UserRepository;
import com.linkshrink.authn.service.ClientService;
import com.linkshrink.authn.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


@Component
@AllArgsConstructor
@Slf4j
public class Initialization implements CommandLineRunner {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private ClientRepository clientRepository;
    private RoleRepository roleRepository;

    @Value("${init.admin.email:admin@linkshrink.com}")
    private final String ADMIN_EMAIL;

    @Value("${init.admin.name:admin}")
    private final String ADMIN_NAME;

    @Value("${init.admin.password:admin}")
    private final String ADMIN_PASSWORD;

    @Value("${init.clients:'redirector:redirectorpassword'}")
    private final String CLIENTS;

    @Override
    public void run(String... args) throws Exception {

    }

    private void createAdmin(){
        if(userRepository.findByEmail(ADMIN_EMAIL).isEmpty()){
            var admin = new User();
            admin.setEmail(ADMIN_EMAIL);
            admin.setName(ADMIN_NAME);
            if(ADMIN_PASSWORD.equals("admin")){
                var pwd = UUID.randomUUID().toString();
                log.info("admin password: {}", pwd);
                admin.setPassword(pwd);
            }else{
                admin.setPassword(ADMIN_PASSWORD);
            }
            admin.setPwd(passwordEncoder.encode(admin.getPassword()));
            var adminRole = roleRepository.findByName("ROLE_ADMIN");
            if(adminRole.isEmpty()){
                var role = new Role();
                role.setName("ROLE_ADMIN");
                adminRole = java.util.Optional.of(roleRepository.save(role));
            }
            admin.setRoles(List.of(adminRole.orElseThrow()));
            admin.setActive(true);
            userRepository.save(admin);
        }
    }

    /*
    * these clients are required for inter ms comms
    * */
    private void createAdminClients(){
        var clients = CLIENTS.split(",");
        for(var c : clients){
            var clientIdPassword = c.split(":");
            var user = userRepository.findByEmail(ADMIN_EMAIL).orElseThrow();
            var cliRole = roleRepository.findById(3).orElseThrow();
            var client = new Client();
            client.setClientId(clientIdPassword[0]);
            client.setClientSecret(clientIdPassword[1]);
            client.setUserId(user.getId());
            client.setRoles(List.of(cliRole));
            client.setAccessTokenValiditySec(5*60);
            client.setRefreshTokenValiditySec(5*60);
            client.setActive(true);
            client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
            clientRepository.save(client);
        }
    }



}
