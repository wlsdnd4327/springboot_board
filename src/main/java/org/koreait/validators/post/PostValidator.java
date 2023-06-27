package org.koreait.validators.post;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.validators.RequiredValidator;
import org.koreait.commons.validators.Validator;
import org.koreait.dtos.post.PostForm;
import org.koreait.exceptions.post.PostValidationException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostValidator implements Validator<PostForm>, RequiredValidator {

    @Override
    public void check(PostForm postForm) {
        requiredCheck(postForm.getBId(), new PostValidationException("NotBlank.postForm.bId"));
        requiredCheck(postForm.getGid(), new PostValidationException("NotBlank.postForm.gid"));
        requiredCheck(postForm.getSubject(), new PostValidationException("NotBlank.postForm.subject"));
        requiredCheck(postForm.getContent(), new PostValidationException("NotBlank.postForm.content"));
    }
}
