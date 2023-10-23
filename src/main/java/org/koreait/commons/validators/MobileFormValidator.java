package org.koreait.commons.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface MobileFormValidator {

    default boolean checkForm(String mobile){

        // 1. 형식의 통일화 - 숫자가 아닌 문자 전부 제거
        mobile = mobile.replaceAll("\\D","");

        // 2. 패턴 생성 체크
        String regex = "^01(0|1|[6-8])\\d{3,4}\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);

        return matcher.matches();
    }
}
