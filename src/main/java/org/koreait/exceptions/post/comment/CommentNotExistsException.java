package org.koreait.exceptions.post.comment;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class CommentNotExistsException extends CommonException {
    public CommentNotExistsException() {
        super(bundleValidations.getString("Validation.comment.notExists"), HttpStatus.BAD_REQUEST);
    }
}
