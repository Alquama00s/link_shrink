package com.linkshrink.authn.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.linkshrink.authn.utils.RoleUtils;
import com.linkshrink.authn.validations.groups.PersistRequestDTO;
import com.linkshrink.authn.validations.groups.RequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {

    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Email(groups = PersistRequestDTO.class)
    private String email;

    @Size(min = 5,max = 100)
    @NotBlank(groups = PersistRequestDTO.class )
    private String name;


    @NotBlank
    @JsonIgnore
    private String pwd;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    @Size(min = 5,max = 20,groups = RequestDTO.class)
    @NotBlank(groups = RequestDTO.class)
    private String password;

    @JsonIgnore
    private boolean isActive ;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_map_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles;

    @JsonProperty("roles")
    public List<String> getSimpleRoles(){
        return RoleUtils.getSimpleRoles(roles);
    }

}
