package org.koreait.models.member;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.MemberEntity;
import org.koreait.repositories.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWithdrawalService {

    private final MemberRepository repository;

    /**
     * 회원 탈퇴 기능
     * 마이페이지 안에 만들기
     * 입력 받은 회원 비밀번호가 로그인한 회원 비밀번호와 일치하다면, 삭제 아니면, 삭제 실패 반환.
     * 삭제 시 세션도 만료할 것.
     * @param memberId
     */
    public void withdrawal(String memberId){
        MemberEntity memberEntity = repository.findByMemberId(memberId);

        if(memberEntity == null) return;

        repository.delete(memberEntity);
        repository.flush();
    }


}