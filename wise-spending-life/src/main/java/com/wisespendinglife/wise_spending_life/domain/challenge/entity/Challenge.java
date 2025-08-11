package com.wisespendinglife.wise_spending_life.domain.challenge.entity;

import com.wisespendinglife.wise_spending_life.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "challenge")
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private String challengeName;
    @Enumerated(EnumType.STRING)
    private ChallengeType challengeType;
    private Long challengeDays;
    private LocalDate startAt;
    private LocalDate endAt;
    private LocalDate createdAt;
    private String characterImageUrl;
    private Boolean isCompleted;
    private Boolean isDeleted;

    @OneToMany(mappedBy = "challenge", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallengeCategory> challengeCategories = new ArrayList<>();

    public void addChallengeCategory(ChallengeCategory challengeCategory) {
        this.challengeCategories.add(challengeCategory);
        challengeCategory.setChallengeCategory(this);
    }

    @Builder
    public Challenge(UserEntity user, String challengeName, ChallengeType challengeType,
                     Long challengeDays, LocalDate startAt, LocalDate endAt, LocalDate createdAt,
                     String characterImageUrl, Boolean isCompleted, Boolean isDeleted) {
        this.user = user;
        this.challengeName = challengeName;
        this.challengeType = challengeType;
        this.challengeDays = challengeDays;
        this.startAt = startAt;
        this.endAt = endAt;
        this.createdAt = createdAt;
        this.characterImageUrl = characterImageUrl;
        this.isCompleted = isCompleted;
        this.isDeleted = isDeleted;
    }

    public void setIsCompleted() {
        this.isCompleted = true;
    }

    public void setIsDeleted(){
        this.isDeleted = true;
    }
}
