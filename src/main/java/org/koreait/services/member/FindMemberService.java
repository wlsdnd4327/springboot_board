package org.koreait.services.member;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberPwFind;
import org.koreait.commons.utils.MemberUtil;
import org.koreait.dtos.member.FindIdForm;
import org.koreait.entities.member.MemberEntity;
import org.koreait.exceptions.member.MemberDataNotExistsException;
import org.koreait.repositories.MemberRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindMemberService implements MemberPwFind {

    private final MemberRepository repository;
    private final MemberUtil memberUtil;
    private final JavaMailSender mailSender;
    private MemberEntity member = null;

    /**
     * 회원 아이디 찾기
     * 1. 이름 입력 여부확인 -> X -> 이름을 입력해달라는 문구 출력
     * 2. 이메일 입력 여부확인 -> X -> 이메일을 입력해달라는 문구 출력
     * 3. 이름으로 찾은 회원정보의 이메일과 입력받은 이메일이 같은지 여부 확인 ->  같지 않으면, 등록된 이메일과 다르다.
     * 4. 같은경우 -> 회원 아이디 반환.
     * @param findIdForm
     * @return
     */

    public String findId(FindIdForm findIdForm) {
        String findMemberNm = findIdForm.getMemberNm();
        String findMemberEmail = findIdForm.getEmail();

        MemberEntity findData = repository.findByMemberNmAndEmail(findMemberNm, findMemberEmail);

        if(findData == null) { return null;}

        return findData.getMemberId();
    }

    public long findPw(String memberId){

        MemberEntity memberEntity = repository.findByMemberId(memberId);

        if(memberEntity == null) { throw new MemberDataNotExistsException();}

        return memberEntity.getMemberNo();
    }
}
