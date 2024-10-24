package com.linkshrink.shortner.dto;

import lombok.Data;

import java.util.List;

@Data
public class User {

    String name;
    int id;
    String email;
    List<String> authorities;
}
