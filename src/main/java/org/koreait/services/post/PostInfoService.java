package org.koreait.services.post;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.post.Post;
import org.koreait.exceptions.post.PostNotExistsException;
import org.koreait.services.board.BoardConfigInfoService;
import org.koreait.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostInfoService {

    private final PostRepository postRepository;
    private final BoardConfigInfoService boardConfigInfoService;

    public Post get(Long id){
        return get(id, "view");
    }

    public Post get(Long id, String location){
        Post post = postRepository.findById(id).orElseThrow(PostNotExistsException::new);

        //게시판 설정 조회 + 접근 권한 체크
        boardConfigInfoService.get(post.getBoard().getBId(), "view");

        return post;
    }
}
