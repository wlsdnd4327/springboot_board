package org.koreait.services.post;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.dtos.post.PostForm;
import org.koreait.entities.board.Board;
import org.koreait.entities.post.Post;
import org.koreait.exceptions.post.PostNotExistsException;
import org.koreait.validators.post.PostValidator;
import org.koreait.services.board.BoardConfigInfoService;
import org.koreait.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostSaveService {

    private final PostValidator postValidator;
    private final BoardConfigInfoService configInfoService;
    private final PostRepository postRepository;

    private final HttpServletRequest request;

    public void save(PostForm postForm){
        postValidator.check(postForm);  //유효성 검사

        /**
         * 게시글 db 저장 처리 - 추가, 수정(id가 없으면 추가 , 있을 시 수정)
         * 1. 게시글 작성, 수정 권한체크(수정 -> 본인이 작성한 글 인지 체크)
         * 2. 게시글 저장, 수정 처리
         * 3. 회원정보는 게시글 등록시에만 저장
         */
        Long id = postForm.getId();
        Board board = configInfoService.get(postForm.getBId(), id == null ? "write" : "update");

        Post post = null;
        if(id == null){ //게시글 추가
            post = new ModelMapper().map(postForm, Post.class);
            post.setBoard(board);   //게시판 정보, 회원 정보 -> 게시글 등록시에만 추가
            post.setIp(request.getRemoteAddr());
            post.setUa(request.getHeader("User-Agent"));
        }else { //게시글 수정
            post = postRepository.findById(id).orElseThrow(PostNotExistsException::new);
        }

        post.setCategory(postForm.getCategory());
        post.setSubject(postForm.getSubject());
        post.setContent(postForm.getContent());
        post.setPoster(postForm.getPoster());

        post = postRepository.saveAndFlush(post);
        postForm.setId(post.getId());
    }
}
