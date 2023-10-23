package org.koreait.services.post;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.post.Post;
import org.koreait.exceptions.post.PostNotExistsException;
import org.koreait.repositories.PostRepository;
import org.koreait.services.flie.FileDeleteService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDeleteService {

    private final PostRepository postRepository;
    private final FileDeleteService fileDeleteService;
    /**
     * 게시글 삭제
     * @param id
     */
    public void delete(Long id){
        Post post = postRepository.findById(id).orElseThrow(PostNotExistsException::new);
        String gid = post.getGid();

        postRepository.deleteById(id);
        postRepository.flush();

        /** 게시글 파일 삭제 처리 */
        fileDeleteService.delete(gid);
    }

}
