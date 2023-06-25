package org.koreait.tests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.controllers.admin.BoardForm;
import org.koreait.controllers.boards.PostForm;
import org.koreait.entities.Board;
import org.koreait.models.post.PostValidationException;
import org.koreait.models.post.PostSaveService;
import org.koreait.models.board.config.BoardConfigInfoService;
import org.koreait.models.board.config.BoardConfigSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DisplayName("게시글 등록, 수정 테스트")
@Transactional
public class BoardSaveTests {

    @Autowired
    private PostSaveService saveService;

    @Autowired
    private BoardConfigSaveService boardConfigSaveService;

    @Autowired
    private BoardConfigInfoService boardConfigInfoService;

    private Board board;

    @BeforeEach
    @Transactional
    void init(){
        //게시판 설정 추가
        BoardForm boardForm = new BoardForm();
        boardForm.setBId("freetalk");
        boardForm.setBName("자유게시판");
        boardConfigSaveService.save(boardForm);
        board = boardConfigInfoService.get(boardForm.getBId(), true);
    }

    private PostForm getPostForm(){
       return PostForm.builder()
               .bId(board.getBId())
               .gid(UUID.randomUUID().toString())
               .poster("작성자")
               .subject("제목")
               .content("내용")
               .category(board.getCategories() == null ? null : board.getCategories()[0])
               .build();
    }

    @Test
    @DisplayName("게시글 등록 성공시 예외 없음")
    void registerSuccessTest(){
        assertDoesNotThrow(() -> {
            saveService.save(getPostForm());
        });
    }

    @Test
    @DisplayName("필수 검증 항목 - bId, gid, subject, content, PostValidationException 발생")
//  @WithMockUser -> 회원 가입시 입력
    void requiredFieldsTest(){
        assertAll(
                //bId = null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getPostForm();
                    postForm.setBId(null);
                    saveService.save(postForm);
                }),
                //bId = 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getPostForm();
                    postForm.setBId(" ");
                    saveService.save(postForm);
                }),
                //gid = null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getPostForm();
                    postForm.setGid(null);
                    saveService.save(postForm);
                }),
                //gid = 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getPostForm();
                    postForm.setGid(" ");
                    saveService.save(postForm);
                }),
                //subject = null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getPostForm();
                    postForm.setSubject(null);
                    saveService.save(postForm);
                }),
                //subject = 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getPostForm();
                    postForm.setSubject(" ");
                    saveService.save(postForm);
                }),
                //content = null
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getPostForm();
                    postForm.setContent(null);
                    saveService.save(postForm);
                }),
                //content = 공백
                () -> assertThrows(PostValidationException.class, () -> {
                    PostForm postForm = getPostForm();
                    postForm.setContent(" ");
                    saveService.save(postForm);
                })
        );
    }
}
