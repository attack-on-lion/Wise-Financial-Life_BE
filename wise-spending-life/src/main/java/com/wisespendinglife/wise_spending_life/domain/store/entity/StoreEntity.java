package com.wisespendinglife.wise_spending_life.domain.store.entity;
//TODO 유저 포인트랑 ManyToOne
//TODO 챌린지 완료 어떻게 한다고 했지.. -> 그냥 챌린지에서 쌓인 데이터 베이스 개수 읽기.
//TODO 스토어 카테고리 서비스에 박기 (참고 payment)

import com.wisespendinglife.wise_spending_life.domain.category.entity.Category;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Store")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private String storeName; //상점 이름

    @Column(nullable = false)
    private String logoURL; //브랜드 로고

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; //카테고리 아이디 FK

    @Column(nullable = false)
    private Boolean isDeleted; //삭제여부

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;
}
