package com.linkshrink.authn.service;

import com.linkshrink.authn.entity.Role;
import com.linkshrink.authn.repository.RoleRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Transactional
public class RoleService {

    RoleRepository roleRepository;

    public List<Role> getroles(){
        return StreamSupport.stream(roleRepository.findAll().spliterator(),false)
                .toList();
    }

    public Role createRole(String role){
        role="ROLE_"+role.toUpperCase();
        return createAuthority(role);
    }

    public Role createAuthority(String authority){
        authority=authority.toUpperCase();
        var existingRole = roleRepository.findByName(authority);
        if(existingRole.isPresent())return existingRole.get();
        var roleOb = new Role();
        roleOb.setName(authority);
        return roleRepository.save(roleOb);
    }

    public void createRoles(String ...roles){
        for(var r: roles){
            createRole(r);
        }
    }
    public void createAuthority(String ...authorities){
        for(var r: authorities){
            createAuthority(r);
        }
    }



}
