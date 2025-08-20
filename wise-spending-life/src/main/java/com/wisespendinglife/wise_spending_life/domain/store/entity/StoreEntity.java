package com.wisespendinglife.wise_spending_life.domain.store.entity;
//TODO 유저 포인트랑 ManyToOne
//TODO 챌린지 완료 어떻게 한다고 했지.. -> 그냥 챌린지에서 쌓인 데이터 베이스 개수 읽기.
//TODO 스토어 카테고리 서비스에 박기 (참고 payment)

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.SQLDelete;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
        name = "store",
        uniqueConstraints = {
                //같은 이름이라도 카테고리별로는 중복 허용
              @UniqueConstraint(name = "uk_store_store_name", columnNames = {"storeName", "categoryId"})
        },
        indexes = { //중복 등록 방지를 위한 유니크 설정
                @Index(name = "idx_store_active_id", columnList = "isDeleted, id"),
                @Index(name = "idx_store_cat_active_id", columnList = "categoryId, isDeleted, id"),
                @Index(name = "idx_store_name", columnList = "storeName")
})
// 소프트 삭제만 수행(조회 필터는 제거: 기존 코드와 호환)
@SQLDelete(sql = "UPDATE store SET is_deleted = true WHERE id = ?")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String storeName; //상점 이름

    @Column(nullable = false, length = 255)
    private String logoUrl; //브랜드 로고

    @Column(nullable = false)
    private Boolean isDeleted; //삭제여부

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category category; //카테고리 아이디 FK

    //가게 이름/로고 변경 로직
    public void updateBasics(String newName, String newLogoUrl){
        setNameAndLogo(newName, newLogoUrl);
    }

    //카테고리 변경 로직
    public void changeCategory(Category newCategory){
        this.category = newCategory;
    }
    private void setNameAndLogo(String name, String logo){
        this.storeName = name == null ? null : name.trim();
        this.logoUrl = logo == null ? null : logo.trim();
    }

    @PrePersist
    private void prePersist() {
        if(isDeleted == null) isDeleted = false;
        if(storeName != null) storeName = storeName.trim();
        if(logoUrl != null) logoUrl = logoUrl.trim();
    }

    public void setIsDeleted(){
        this.isDeleted = true;
    }
}
