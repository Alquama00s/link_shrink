package com.linkshrink.backend.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.Duration;


@Entity
@Table(name = "urls")
public class Urls {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "short_url")
    private String shortUrl;

    @Column(name = "created_on")
    private Timestamp creationTime;


    @Column(name = "expiry_after")
    private Duration interval;


    public Urls(String longUrl, String shortUrl, Timestamp creationTime, Duration interval) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Duration getInterval() {
        return interval;
    }

    public void setInterval(Duration interval) {
        this.interval = interval;
    }

    
}
