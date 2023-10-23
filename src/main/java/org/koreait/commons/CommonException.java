package org.koreait.commons;

import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

/**
 * 공통 예외
 */

public class CommonException extends RuntimeException{

    protected static ResourceBundle bundleValidations;

    protected static ResourceBundle bundleErrors;

    protected HttpStatus httpStatus;    //상태코드

    static{
        bundleValidations = ResourceBundle.getBundle("messages.validations");
        bundleErrors= ResourceBundle.getBundle("messages.errors");
    }

    public CommonException(String message){
        super(message);
        httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public CommonException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus(){  //상태 값 조회
        return httpStatus;
    }
}
