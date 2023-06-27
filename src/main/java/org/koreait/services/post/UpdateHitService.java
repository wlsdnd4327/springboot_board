package org.koreait.services.post;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.post.Post;
import org.koreait.entities.post.PostView;
import org.koreait.repositories.PostRepository;
import org.koreait.repositories.PostViewRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * 조회수 업데이트
 */
@Service
@RequiredArgsConstructor
public class UpdateHitService {

    private final PostViewRepository postViewRepository;
    private final PostRepository postRepository;
    private final HttpServletRequest request;

    public void update(Long id){
        try{
            PostView postView = new PostView();
            postView.setId(id);
            postView.setUid("" + getUid());
            postViewRepository.saveAndFlush(postView);
        }catch (Exception e){
            long cnt = postViewRepository.getHit(id);
            Post post = postRepository.findById(id).orElse(null);
            if(post != null){
                post.setHit((int)cnt);
                postRepository.flush();
            }
        }
    }

    private int getUid(){
        String ip = request.getRemoteAddr();
        String ua = request.getHeader("User-Agent");

        return Objects.hash(ip, ua);
    }
}
