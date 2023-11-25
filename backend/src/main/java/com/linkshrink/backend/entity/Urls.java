package com.linkshrink.backend.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.linkshrink.backend.customException.KnownException;
import com.linkshrink.backend.util.deserializer.TimestampIntervalDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;
import org.springframework.beans.factory.annotation.Value;

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
    @Pattern(regexp = "^[a-zA-Z0-9-@]+$",message = "Short Url must only contain alphanumeric character and `-`")
    private String shortUrl;

    @Column(name = "generated")
    @NotNull
    private boolean generated = false;

    @Column(name = "created_on")
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp creationTime;


    @Column(name = "expiry_after")
    @JsonDeserialize(using = TimestampIntervalDeserializer.class)
    private Timestamp expiryAfter;



    @PrePersist
    void modifyGeneratedUrl() throws KnownException {
        if(!shortUrl.matches("^[a-zA-Z0-9-]+$")){
            throw new KnownException("Short Url must only contain alphanumeric character and `-`");
        }
        if(generated){
            shortUrl="@"+shortUrl;
        }
    }

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

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean generated) {
        this.generated = generated;
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
