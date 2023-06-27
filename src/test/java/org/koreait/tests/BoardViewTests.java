package org.koreait.tests;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.koreait.dtos.admin.board.BoardForm;
import org.koreait.dtos.post.PostForm;
import org.koreait.entities.board.Board;
import org.koreait.services.board.BoardConfigInfoService;
import org.koreait.services.board.BoardConfigSaveService;
import org.koreait.exceptions.board.BoardNotAllowAccessException;
import org.koreait.services.post.PostInfoService;
import org.koreait.exceptions.post.PostNotExistsException;
import org.koreait.services.post.PostSaveService;
import org.koreait.repositories.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class BoardViewTests {

    private Board board;

    private Long id;    //게시글 번호

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private PostSaveService saveService;

    @Autowired
    private BoardConfigSaveService boardConfigSaveService;

    @Autowired
    private BoardConfigInfoService boardConfigInfoService;

    @Autowired
    private PostInfoService infoService;

    private PostForm postForm;

    private String bId = "freetalk";    //bId가 항상 필요하기 때문에 고정

    private Board getBoard(){ //매번 게시판 설정 새로 생성
        Board board = boardConfigInfoService.get(bId, true);
        return board;
    }

    //초기 테스트 전 게시글, 게시판 추가 후 테스트
    @BeforeEach
    void init(){
        //게시판 설정 추가
        BoardForm boardForm = new BoardForm();
        boardForm.setBId(bId);
        boardForm.setBName("자유게시판");
        boardForm.setUse(true);
        boardConfigSaveService.save(boardForm);
        board = getBoard();

        //테스트용 게시글 추가
        postForm = PostForm.builder()
                .bId(board.getBId())
                .gid(UUID.randomUUID().toString())
                .poster("작성자")
                .subject("제목")
                .content("내용")
                .category(board.getCategories() == null ? null : board.getCategories()[0])
                .build();

        saveService.save(postForm);
        id = postForm.getId();  //게시글 번호 조회
    }

    @Test
    @DisplayName("게시글 조회 성공시 예외 없음")
    void getPostSuccessTest(){
        assertDoesNotThrow(() ->{
           infoService.get(id);
        });
    }

    @Test
    @DisplayName("등록되지 않은 게시글 이면 PostNotExistsException 발생")
    void getPostNotExistsTest(){
        assertThrows(PostNotExistsException.class, () -> {
           infoService.get(id + 10);    //없는 게시글을 넣음
        });
    }

    @Test
    @DisplayName("게시판 사용 여부가 false 이면 접근불가 - BoardNotAllowAccessException 발생")
    void accessAuthCheck1(){    //게시판 설정 추가에서 boardForm.setUse(true); 추가시 테스트 해결
        assertThrows(BoardNotAllowAccessException.class, () -> {
            Board board = getBoard();
            board.setUse(false);
            boardRepository.saveAndFlush(board);
            infoService.get(id);
        });
    }

}
