package org.koreait.models.member;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class WrongInfoException extends CommonException {
    public WrongInfoException(String code, HttpStatus httpStatus) {
        super(bundleValidations.getString(code), httpStatus);
    }
}
