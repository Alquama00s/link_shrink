package com.linkshrink.authn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.linkshrink.authn.utils.RoleUtils;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.sql.Timestamp;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "clients")
public class Client extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String clientId;


    @NotNull
    @JsonIgnore
    private String clientSecret;

    @JsonIgnore
    private int userId;

    @JsonIgnore
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId",insertable = false,updatable = false)
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

    @JsonProperty(value = "scopes",access = JsonProperty.Access.READ_ONLY)
    public List<String> getSimpleRoles(){
        return RoleUtils.getSimpleRoles(roles);
    }
}
