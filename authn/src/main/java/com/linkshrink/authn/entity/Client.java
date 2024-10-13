package com.linkshrink.authn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.linkshrink.authn.utils.RoleUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@Builder
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String clientId;

    @Transient
    @JsonProperty(value = "clientSecret",access = JsonProperty.Access.READ_ONLY)
    private String secret;

    @NotNull
    @JsonIgnore
    private String clientSecret;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_map_client",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private List<Role> roles;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int accessTokenValiditySec;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private int refreshTokenValiditySec;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Timestamp expiresOn;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean isActive;

    @JsonProperty(value = "scopes",access = JsonProperty.Access.READ_ONLY)
    public List<String> getSimpleRoles(){
        return RoleUtils.getSimpleRoles(roles);
    }
}
