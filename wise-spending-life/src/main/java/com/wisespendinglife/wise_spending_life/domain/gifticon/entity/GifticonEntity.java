package com.wisespendinglife.wise_spending_life.domain.gifticon.entity;

import com.wisespendinglife.wise_spending_life.domain.store.entity.StoreEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "gifticon")
@EntityListeners(AuditingEntityListener.class)
@Builder

public class GifticonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //아이디

    @Column(nullable = false)
    private String name; //이름

    @Column(nullable = false)
    private Long price; //가격

    @Column(nullable = false, length = 500)
    private String imageUrl; //이미지 URL이

    @Column(nullable = false)
    private Boolean isDeleted; //삭제 여부

    @Column(nullable = false)
    private Boolean isRecommend; //상품 추천 여부

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; //생성 시간

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "storeId", nullable = false)
    private StoreEntity store; //스토어 아이디 FK

    @PrePersist
    void prePersist() {
        if (isDeleted == null)   isDeleted = false;
        if (isRecommend == null) isRecommend = false;
    }

    public void updateName(String name){
        this.name = name;
    }
    public void updatePrice(Long price){
        this.price = price;
    }
    public void updateImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    public void updateIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }
    public void updateIsRecommend(Boolean isRecommend){
        this.isRecommend = isRecommend;
    }
    public void updateCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }
    public void updateStore(StoreEntity store){this.store = store;}

}
