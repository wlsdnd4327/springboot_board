package org.koreait.models.post;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

/**
 * 게시판 유효성 검사 관련 예외
 */
public class PostValidationException extends CommonException {

    public PostValidationException(String code) {
        super(bundleValidations.getString(code), HttpStatus.BAD_REQUEST);
    }
}
