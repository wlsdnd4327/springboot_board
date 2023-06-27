package org.koreait.exceptions.board;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class BoardConfigNotExistException extends CommonException {
    public BoardConfigNotExistException() {
        super(bundleValidations.getString("Validation.board.notExists"), HttpStatus.BAD_REQUEST);
    }
}
