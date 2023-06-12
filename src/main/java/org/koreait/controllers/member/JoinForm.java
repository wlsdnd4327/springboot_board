package org.koreait.controllers.member;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class JoinForm {

    @NotBlank
    @Size(min=6, max=20)
    private String memberId; // 회원아이디

    @NotBlank
    @Size(min=8)
    private String memberPw; // 회원비밀번호

    @NotBlank
    private String memberPwRe; // 회원비밀번호 확인

    @NotBlank
    private String memberNm; // 회원명

    @NotBlank @Email
    private String email; // 이메일
    
    private String mobile; // 연락처

    private boolean[] agrees; // 회원 약관 동의
}
