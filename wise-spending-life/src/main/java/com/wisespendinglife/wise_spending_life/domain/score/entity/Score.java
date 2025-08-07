package com.wisespendinglife.wise_spending_life.domain.score.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Score {

    @Id
    private Long id;

    /**
     * TODO: User 테이블과 연결해야 함. (단방향)
     *
     * User 도메인 작업 완료되면 아래 주석 해제하면 됨
     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id", nullable = false)
//    private User user;

    private Integer score;
}
