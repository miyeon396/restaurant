package com.example.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class CreateBase {

    @Column(name = "CREA_DT", updatable = false)
    private LocalDateTime creaDt;

    @PrePersist
    public void onPrePersist() {
        this.creaDt = LocalDateTime.now(ZoneId.of("UTC"));
    }

}
