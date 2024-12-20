package com.linkshrink.authn.configurations;

import com.linkshrink.authn.Dto.ClientDTO;
import com.linkshrink.authn.entity.Client;
import com.linkshrink.authn.entity.Role;
import com.linkshrink.authn.entity.User;
import com.linkshrink.authn.repository.ClientRepository;
import com.linkshrink.authn.repository.RoleRepository;
import com.linkshrink.authn.repository.UserRepository;
import com.linkshrink.authn.service.ClientService;
import com.linkshrink.authn.service.RoleService;
import com.linkshrink.authn.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Component
@Slf4j
public class Initialization implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ClientService clientService;

    @Value("${init.admin.email:admin@linkshrink.com}")
    private String ADMIN_EMAIL;

    @Value("${init.admin.name:admin}")
    private String ADMIN_NAME;

    @Value("${init.admin.password:admin}")
    private String ADMIN_PASSWORD;

    @Value("${init.clients:redirector:redirectorpassword}")
    private String CLIENTS;

    @Override
    public void run(String... args) throws Exception {
        initRoles();
        createAdmin();
        createAdminClients();
    }
    private void initRoles(){
        roleService.createRoles(
                "USER",
                "ADMIN",
                "CLIENT"
        );
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
            userService.registerAdmin(admin);
        }
    }

    /*
    * these clients are required for inter ms comms
    * */
    private void createAdminClients(){
        var clients = CLIENTS.split(",");
        for(var c : clients){
            var clientIdPassword = c.split(":");
            System.out.println(Arrays.toString(clientIdPassword));
            var clientDto = new ClientDTO(clientIdPassword[0],clientIdPassword[1]);
            clientService.registerAdminClient(clientDto);
        }
    }



}
