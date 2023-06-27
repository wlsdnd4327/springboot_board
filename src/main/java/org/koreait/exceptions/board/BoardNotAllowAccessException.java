package org.koreait.exceptions.board;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardNotAllowAccessException extends CommonException {
    public BoardNotAllowAccessException() {
        super(bundleValidations.getString("Validation.board.NotAllowAccess"), HttpStatus.UNAUTHORIZED);
    }
}
