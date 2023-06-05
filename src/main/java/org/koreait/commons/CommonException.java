package org.koreait.commons;

import org.springframework.http.HttpStatus;

import java.util.ResourceBundle;

/**
 * 예외 통제
 */

public class CommonException extends RuntimeException{
    protected static ResourceBundle bundleValidations;
    protected static ResourceBundle bundleErrors;
    protected HttpStatus httpStatus;

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

    public HttpStatus getStatus(){
        return httpStatus;
    }
}
