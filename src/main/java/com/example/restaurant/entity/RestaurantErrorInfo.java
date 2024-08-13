package com.example.restaurant.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Comment;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@ToString
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "TBL_RESTAURANT_ERROR_L")
public class RestaurantErrorInfo implements Persistable<Long> {

    @Id
    @Column(name="NO") @Comment("번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column(name="ERR_RESTAURANT_ID") @Comment("에러 점포 아이디")
    private String errRestaurantId;

    @Column(name="ERR_MSG", columnDefinition = "text") @Comment("에러 메세지")
    private String errMsg;

    @Column(name = "CREA_DT", updatable = false)
    private LocalDateTime creaDt;

    @PrePersist
    public void onPrePersist() {
        this.creaDt = LocalDateTime.now(ZoneId.of("UTC"));
    }


    @Override
    public Long getId() {
        return no;
    }

    @Override
    public boolean isNew() {
        return creaDt == null;
    }

    public static RestaurantErrorInfo createBy(Exception e, String errRestaurantId) {
        return RestaurantErrorInfo.builder()
                .errMsg(e.getMessage())
                .errRestaurantId(errRestaurantId)
                .build();
    }
}
