package com.linkshrink.authn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.linkshrink.authn.utils.RoleUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Size(min=5,max = 20)
    private String clientId;


    @NotNull
    @JsonIgnore
    private String clientSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_map_client",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    private int accessTokenValiditySec;
    private int refreshTokenValiditySec;
    private Timestamp expiresOn;
    private boolean isActive;

    @JsonProperty("roles")
    public List<String> getSimpleRoles(){
        return RoleUtils.getSimpleRoles(roles);
    }
}
