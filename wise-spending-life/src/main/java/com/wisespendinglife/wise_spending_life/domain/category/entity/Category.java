package com.wisespendinglife.wise_spending_life.domain.category.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryType type;


}

