package org.koreait.controllers.member;


import lombok.RequiredArgsConstructor;
import org.koreait.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class MemberValidator implements Validator {

    private final MemberRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }

    /**
     *  1. 회원 아이디 중복 체크
     *  2. 비밀번호 - 비밀번호 확인 일치 여부 체크
     *  3. 비밀번호 형식 특수성 일치 여부 체크
     *  4. 휴대전화 번호 양식 체크
     *  5. 회원 약관 동의 체크 여부 확인.
     * @param target the object that is to be validated
     * @param errors contextual state about the validation process
     */
    @Override
    public void validate(Object target, Errors errors) {
        if(target == null){errors.reject("Validation.notExist");}

        JoinForm joinForm = (JoinForm) target;
        String memberId = joinForm.getMemberId();
        String memberPw = joinForm.getMemberPw();
        String memberPwRe = joinForm.getMemberPwRe();
        String mobile = joinForm.getMobile();
        boolean agree = joinForm.isAgree();

        //1. 아이디 중복 체크
        if(!memberId.isBlank() && memberId != null && repository.isExist(memberId)){
            errors.rejectValue(memberId,"Validation.duplicate.memberId");
        }

        //2. 비밀번호 - 비밀번호 확인 동일 여부 체크
        if(!memberPw.isBlank() && memberPw!=null && !memberPwRe.isBlank() && memberPwRe != null
        && memberPw != memberPwRe){
            errors.rejectValue(memberPwRe, "Validation.discord.password");
        }
        
        //3. 비밀번호 - 형식 특수성 일치 여부 체크 -> 정규 표현식
        

    }
}
