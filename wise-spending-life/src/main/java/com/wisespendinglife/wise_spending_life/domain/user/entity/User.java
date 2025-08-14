package com.wisespendinglife.wise_spending_life.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "Users")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id; //아이디

    @Column(nullable = false)
    private String name; //이름

    private String profileImgUrl; //프로필이미지 url

    private String gender; //성별

    private String location; //거주지

    private String email; //이메일

    private String phoneNumber; //전화번호

    @Column(nullable = false)
    private Long age; //나이

    private Long baseAmount; //기준금액

    private Long point;

    @CreatedDate
    @Column(nullable = false) //자동화함
    private LocalDateTime createdAt; //레코드 생성 날짜

    @LastModifiedDate
    @Column(nullable = false) //자동화함
    private LocalDateTime updatedAt; //래코드 수정 날짜

    @Column(nullable = false)
    private Boolean isDeleted; //삭제여부

    public void updateName(String name){
        this.name = name;
    }
    public void updateProfileImgUrl(String profileImgUrl){
        this.profileImgUrl = profileImgUrl;
    }
    public void updateGender(String gender){
        this.gender = gender;
    }
    public void updateLocation(String location){
        this.location = location;
    }
    public void updateEmail(String email){
        this.email = email;
    }
    public void updatePhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }
    public void updateAge(Long age){
        this.age = age;
    }
    public void updatePoint(Long point){ this.point = point; }
    public void updateBaseAmount(Long baseAmount){
        this.baseAmount = baseAmount;
    }
    public void updateIsDeleted(Boolean isDeleted){
        this.isDeleted = isDeleted;
    }

}
