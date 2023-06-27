package org.koreait.models.post;

import org.koreait.commons.CommonException;
import org.springframework.http.HttpStatus;

public class PostNotExistsException extends CommonException {
    public PostNotExistsException() {
        super(bundleValidations.getString("Validation.post.notExists"), HttpStatus.BAD_REQUEST);
    }
}
