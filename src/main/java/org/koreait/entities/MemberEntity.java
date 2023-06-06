package org.koreait.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@Table(indexes={
        @Index(name="idx_member_memberNm",columnList = "memberNm"),
        @Index(name="idx_member_email",columnList = "email")
})
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue
    private long memberNo; // 회원번호

    @Column(length=45, nullable = false, unique = true)
    private String memberId; // 회원아이디

    @Column(length=65, nullable=false)
    private String memberPw; // 회원비밀번호

    @Column(length=35, nullable = false)
    private String memberNm; // 회원명

    @Column(length=25)
    private String email; // 이메일

    @Column(length=11)
    private String mobile; // 모바일

    @Column(nullable = false)
    private boolean agree; // 회원 약관 동의
}
