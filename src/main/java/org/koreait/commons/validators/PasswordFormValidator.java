package org.koreait.commons.validators;

import org.koreait.commons.constants.Regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface PasswordFormValidator {
    /**
     * 비밀번호 형식 특수성 체크
     * 1. 영대소문자 하나 이상 포함 - isCaseCheck(true : 대소문자 각각 1자이상 , false : 대소문자 구분 X)
     * 2. 숫자 포함
     * 3. 특수문자 하나 이상 포함 !@#$%^&*()_+
     */

    default boolean alphabetCheck(String memberPw, boolean isCaseCheck){
        Pattern lower = Pattern.compile(Regex.LOWER_CASE.getValue());
        Pattern upper = Pattern.compile(Regex.UPPER_CASE.getValue());
        Pattern ig = Pattern.compile(Regex.IGNORE_CASE.getValue());
        boolean result = false;

        if(!isCaseCheck) {
            Matcher matcher = ig.matcher(memberPw);
            result = matcher.find();
        }else {
            result = lower.matcher(memberPw).find() && upper.matcher(memberPw).find();
        }

        return result;
    }

    default boolean numberCheck(String memberPw){
        Pattern pattern = Pattern.compile(Regex.NUM.getValue());
        Matcher matcher = pattern.matcher(memberPw);
        return matcher.find();
    }

    default boolean specialCheck(String memberPw){
        Pattern pattern = Pattern.compile(Regex.SPECIAL.getValue());
        Matcher matcher = pattern.matcher(memberPw);
        return matcher.find();
    }

    default boolean pwNpwRecheck(String memberPw, String memberPwRe){
        return memberPw.equals(memberPwRe);
    }
}
