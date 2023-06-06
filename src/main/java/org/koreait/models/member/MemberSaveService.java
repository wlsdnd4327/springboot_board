package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.member.JoinForm;
import org.koreait.entities.MemberEntity;
import org.koreait.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSaveService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * 회원 정보 저장 기능
     * @param joinForm
     */
    public void save(JoinForm joinForm){

        MemberEntity member = MemberEntity.builder()
                .memberId(joinForm.getMemberId())
                .memberPw(passwordEncoder.encode(joinForm.getMemberPw()))
                .memberNm(joinForm.getMemberNm())
                .email(joinForm.getEmail())
                .mobile(joinForm.getMobile())
                .agree(joinForm.isAgree())
                .build();

        memberRepository.saveAndFlush(member);
    }
}
