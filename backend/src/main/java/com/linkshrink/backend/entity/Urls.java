package com.linkshrink.backend.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.linkshrink.backend.util.deserializer.TimestampIntervalDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;


@Entity
@Table(name = "urls")
public class Urls {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "long_url")
    @URL
    private String longUrl;

    @Column(name = "short_url")
    @Pattern(regexp = "^[a-zA-Z0-9-]+$",message = "Short Url must only contain alphanumeric character and `-`")
    private String shortUrl;

    @Column(name = "created_on")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp creationTime;


    @Column(name = "expiry_after")
    @JsonDeserialize(using = TimestampIntervalDeserializer.class)
    private Timestamp expiryAfter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    public Timestamp getExpiryAfter() {
        return expiryAfter;
    }

    public void setExpiryAfter(Timestamp expiryAfter) {
        this.expiryAfter = expiryAfter;
    }

    
}
