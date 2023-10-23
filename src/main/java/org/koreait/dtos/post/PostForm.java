package org.koreait.dtos.post;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.entities.file.FileEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostForm {
    private Long id; // 게시글 번호

    @NotBlank
    private String bId; //게시판 아이디

    @NotBlank
    private String gid = UUID.randomUUID().toString();  //그룹아이디(없으면 자동 생성)

    private String poster; // 작성자

    private String category; // 게시판 분류

    @NotBlank
    private String subject; // 제목

    @NotBlank
    private String content; // 내용

    private int hit;    //조회수

    private LocalDateTime createdAt;    //작성일시

    private boolean isNotice;   //공지글 여부 (1이면 공지글 0이면 일반글)

    private List<FileEntity> attachEditors;
    private List<FileEntity> attachFiles;

}
