package com.wisespendinglife.wise_spending_life.domain.store.entity;

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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; //카테고리 아이디 FK

    @Column(nullable = false)
    private Boolean isDeleted; //삭제여부
}
