package com.linkshrink.backend.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Entity
@Table(name = "urls")
public class Urls {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "created_on")
    private Timestamp creationTime;


    @Column(name = "expiry_after")
    private Timestamp interval;


    public Urls(String longUrl, String shortUrl, Timestamp creationTime, Timestamp interval) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.creationTime = creationTime;
        this.interval = interval;
    }

    public Urls(String longUrl, String shortUrl, Timestamp creationTime) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.creationTime = creationTime;
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

    public Timestamp getInterval() {
        return interval;
    }

    public void setInterval(Timestamp interval) {
        this.interval = interval;
    }

    
}
