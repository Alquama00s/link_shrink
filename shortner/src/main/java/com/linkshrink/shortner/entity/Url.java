package com.linkshrink.shortner.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.linkshrink.shortner.util.customValidators.ValidateShortUrl;
import com.linkshrink.shortner.util.deserializer.TimestampIntervalDeserializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.URL;

import java.sql.Timestamp;


@Data
@Entity
@EqualsAndHashCode(callSuper = false)
@Table(name = "urls")
@ValidateShortUrl
public class Url extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @URL
    @NotNull
    private String longUrl;

    private String shortUrl;

    @NotNull
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean generated = false;


    @Column(name = "expiry_after")
    @JsonDeserialize(using = TimestampIntervalDeserializer.class)
    private Timestamp expiryAfter;

}
