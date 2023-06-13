package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.member.FindIdForm;
import org.koreait.entities.MemberEntity;
import org.koreait.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class FindMemberService {

    private final MemberRepository repository;

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

        MemberEntity findData = repository.findByMemberNm(findMemberNm);

        if(findData == null) {throw new MemberDataNotExistsException();}

        if(!findMemberEmail.equals(findData.getEmail())){
            throw new WrongInfoException("Validation.wrong.email",HttpStatus.BAD_REQUEST);
        }

        return findData.getMemberId();
    }
}
