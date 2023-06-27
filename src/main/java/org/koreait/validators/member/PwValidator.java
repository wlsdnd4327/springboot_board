package org.koreait.validators.member;


import org.koreait.commons.validators.PasswordFormValidator;
import org.koreait.dtos.member.PwResetForm;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PwValidator implements Validator,PasswordFormValidator {

    @Override
    public boolean supports(Class<?> clazz) {
        return PwResetForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PwResetForm pwResetForm = (PwResetForm) target;

        String memberPw = pwResetForm.getMemberPw();
        String memberPwRe = pwResetForm.getMemberPwRe();

        if(memberPw!=null && !memberPw.isBlank()){
            //2. 비밀번호 - 비밀번호 확인 동일 여부 체크
            if (memberPwRe != null && !memberPwRe.isBlank() ) {
                if(!pwNpwRecheck(memberPw,memberPwRe)) {
                    errors.rejectValue("memberPwRe", "Validation.discord.password");
                }
            }

            //3. 비밀번호 - 형식 특수성 일치 여부 체크 -> 정규 표현식
            if (!alphabetCheck(memberPw, true) || !numberCheck(memberPw) || !specialCheck(memberPw)) {
                errors.rejectValue("memberPw", "Validation.complexity.password");
            }
        }
    }
}
