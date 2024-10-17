package com.linkshrink.authn.controller;


import com.linkshrink.authn.entity.Role;
import com.linkshrink.authn.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/role")
@AllArgsConstructor
public class RoleController {

    RoleService roleService;

    @GetMapping("")
    public Map<String,Object> getAllRoles(){
        return Map.of("roles",roleService.getroles());
    }

    @PostMapping("")
    public Role saveNewRole(@RequestBody Role role){
        return roleService.createRole(role.getName());
    }

    @PostMapping("/authority")
    public Role saveNewAuthority(@RequestBody Role role) {
        return roleService.createAuthority(role.getName());
    }

}
