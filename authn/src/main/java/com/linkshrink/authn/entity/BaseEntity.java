package com.linkshrink.authn.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@Data
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    private boolean isActive;

    @JsonIgnore
    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdOn;

    @JsonIgnore
    @LastModifiedDate
    @Column(insertable = false)
    LocalDateTime updatedOn;

    @JsonIgnore
    @CreatedBy
    @Column(updatable = false)
    Integer createdBy;

    @JsonIgnore
    @LastModifiedBy
    @Column(insertable = false)
    Integer updatedBy;


}
