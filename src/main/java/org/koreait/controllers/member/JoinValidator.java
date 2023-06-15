package org.koreait.controllers.member;


import lombok.RequiredArgsConstructor;
import org.koreait.commons.validators.MobileFormValidator;
import org.koreait.commons.validators.PasswordFormValidator;
import org.koreait.repositories.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor
public class JoinValidator implements Validator, MobileFormValidator, PasswordFormValidator {

    private final MemberRepository repository;

    @Override
    public boolean supports(Class<?> clazz) {
        return JoinForm.class.isAssignableFrom(clazz);
    }

    /**
     *  1. 회원 아이디 중복 체크
     *  2. 비밀번호 - 비밀번호 확인 일치 여부 체크
     *  3. 비밀번호 복잡성 일치 여부 체크(영대소문자, 숫자, 특수문자 1자 이상)
     *  4. 휴대전화번호 양식 숫자만 남도록 통일화 + 휴대전화 번호 양식 체크
     *  5. 회원 약관 동의 체크 여부 확인.
     *
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
        boolean[] agrees = joinForm.getAgrees();

        System.out.println(agrees + "👉");

        //1. 아이디 중복 체크
        if(!memberId.isBlank() && memberId != null && repository.isExist(memberId)){
            errors.rejectValue("memberId","Validation.duplicate.memberId");
        }

        if(!memberPw.isBlank() && memberPw!=null){

            //2. 비밀번호 - 비밀번호 확인 동일 여부 체크
            if(!memberPwRe.isBlank() && memberPwRe != null && !memberPw.equals(memberPwRe)) {
                errors.rejectValue("memberPwRe", "Validation.discord.password");
            }

            //3. 비밀번호 - 형식 특수성 일치 여부 체크 -> 정규 표현식
            if(!alphabetCheck(memberPw,true) || !numberCheck(memberPw) || !specialCheck(memberPw)){
                errors.rejectValue("memberPw","Validation.complexity.password");
            }
        }

        //4. 휴대전화 번호 양식 일치 여부 체크
        if(!mobile.isBlank() && mobile != null) {
            if (!checkForm(mobile)) {
                errors.rejectValue("mobile", "Validation.discord.mobile");
            }

            mobile = mobile.replaceAll("\\D","");
            joinForm.setMobile(mobile);
        }

        //5. 약관 동의 여부 체크
        /**
         * 체크하면 true, 아니면 null 반환.
         */
        if((agrees != null && agrees.length > 0)) {
            for (boolean agree : agrees) {
                if (!agree) {
                    errors.rejectValue("agrees","Validation.disAgree");
                    break;
                }
            }
        }else if(agrees.length ==0 && agrees != null){
            errors.rejectValue("agrees","Validation.disAgree");
        }
    }
}
