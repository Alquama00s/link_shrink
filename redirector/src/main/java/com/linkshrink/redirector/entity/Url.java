package com.linkshrink.redirector.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;



@Data
public class Url{

    private long id;
    private String longUrl;
    private String shortUrl;
    private Timestamp expiryAfter;

}
