package com.example.restaurant.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public class CreaDtUpdtDtBase {

    @Column(name = "CREA_DT", updatable = false)
    private LocalDateTime creaDt;

    @Column(name = "UPDT_DT", insertable = false)
    private LocalDateTime updtDt;

    @PrePersist
    public void onPrePersist() {
        this.creaDt = LocalDateTime.now(ZoneId.of("UTC"));
    }

    @PreUpdate
    public void onPreUpdate() {
        this.updtDt = LocalDateTime.now(ZoneId.of("UTC"));
    }

}
