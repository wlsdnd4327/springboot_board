package org.koreait.exceptions.member;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class LoginValidationException extends CommonException {

    private String field;

    public LoginValidationException(String code){
        super(bundleValidations.getString(code), HttpStatus.BAD_REQUEST);
    }

    public LoginValidationException(String field, String code){
        this(code);
        this.field = field;
    }

    public String getField(){
        return field;
    }
}
