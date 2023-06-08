package org.koreait.test;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.commons.constants.Regex;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hibernate.validator.internal.util.Contracts.assertTrue;

@SpringBootTest
@TestPropertySource(locations="classpath:application-test.properties")
@Transactional
public class RegexTest {
    private String[] strings;
    private String memberPw = "aA!123456";
    
    @BeforeEach
    public void testSet(){
        strings=new String[] {
                "^[0-9]*$",
                "^[a-zA-Z]*$",
                "^[a-z]*$",
                "^[A-Z]*$",
                "^\\d$", //
                "^\\D$", // 숫자 이외
                "^01(?:0|1|[6-8])\\d{3,4}\\d{4}$", // 휴대전화 번호 양식
                "^[\\w]*$"
        };
    }

    @Test
    @DisplayName("숫자만 형식 통과")
    void exam01(){
        Pattern pattern = Pattern.compile(strings[0]);
        String str = "123424";

        Matcher matcher = pattern.matcher(str);
        assertTrue(matcher.matches(),"일치!");
    }

    @Test
    @DisplayName("영대소문자 형식")
    void exam02(){
        Pattern pattern = Pattern.compile(strings[1]);
        String str = "aaAAddDDff";

        Matcher matcher = pattern.matcher(str);
        assertTrue(matcher.matches(),"형식 일치");
    }

    @Test
    @DisplayName("전화번호 형식 통과")
    void exam03(){
        Pattern pattern = Pattern.compile(strings[6]);
        String str = "010-4766-1265";

        str = str.replaceAll("\\D","");
        System.out.println(str);
        Matcher matcher = pattern.matcher(str);
        assertTrue(matcher.matches(),"형식 일치");
        
    }

    @Test
    @DisplayName("영문자 포함 테스트")
    void exam04(){
        boolean isCaseCheck = false;

        Pattern lower = Pattern.compile(Regex.LOWER_CASE.getValue());
        Pattern upper = Pattern.compile(Regex.UPPER_CASE.getValue());
        Pattern ig = Pattern.compile(Regex.IGNORE_CASE.getValue());
        boolean result = false;

        if(!isCaseCheck) {
            Matcher matcher = ig.matcher(memberPw);
            result = matcher.find();
        }else {
            result = lower.matcher(memberPw).matches() && upper.matcher(memberPw).matches();
        }
        assertTrue(result,"불일치");
    }
}
