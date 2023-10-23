package org.koreait.dtos.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {

    private Long id;    //댓글 번호

    @NotNull
    private Long postId; // 게시글 번호

    private String poster;  //작성자

    @NotBlank
    private String content; //내용
}
