package org.koreait.exceptions.member;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class MemberDataNotExistsException extends CommonException {

    public MemberDataNotExistsException() {
        super(bundleValidations.getString("Validation.member.notExists"),HttpStatus.BAD_REQUEST);
    }
}
