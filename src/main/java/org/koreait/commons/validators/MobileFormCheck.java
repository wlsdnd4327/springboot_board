package org.koreait.commons.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface MobileFormCheck {

    default boolean checkForm(String mobile){

        mobile=mobile.replaceAll("\\D","");

        String regex = "^01(0|1|[6-8])\\d{3,4}\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(mobile);

        return matcher.matches();
    }
}
