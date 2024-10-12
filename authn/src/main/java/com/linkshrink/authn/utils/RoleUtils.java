package com.linkshrink.authn.utils;

import com.linkshrink.authn.entity.Role;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class RoleUtils {
    public static List<String> getSimpleRoles(List<Role> roles){
        if(roles==null)return Collections.emptyList();
        return roles.stream().map(Role::getName).toList();
    }

    public static List<Role> getRoles(Collection<String> roles){
        return roles.stream().map(i-> {
            var role = new Role();
            role.setName(i);
            return role;
        }).toList();
    }
}

