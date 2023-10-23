package org.koreait.services.post;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.koreait.dtos.post.PostSearch;
import org.koreait.entities.post.Post;
import org.koreait.entities.post.QPost;
import org.koreait.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class PostListService {

    private final PostRepository postRepository;

    public Page<Post> gets(PostSearch postSearch){
        QPost qPost = QPost.post;
        BooleanBuilder andBuilder = new BooleanBuilder();

        String bId = postSearch.getBid();
        if (bId != null && !bId.isBlank()) {
            andBuilder.and(qPost.board.bId.eq(bId));
        }

        int page = postSearch.getPage();
        int limit = postSearch.getLimit();

        //검색 조건 처리
        String sopt = postSearch.getSopt();
        String skey = postSearch.getSkey();
        if(sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()){
            skey = skey.trim();
            sopt = sopt.trim();
            if(sopt.equals("all")){ //전체 검색
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(qPost.poster.contains(skey)).or(qPost.subject.contains(skey));
                andBuilder.and(orBuilder);
            } else if(sopt.equals("poster")){    //작성자 검색
                andBuilder.and(qPost.poster.contains(skey));
            } else if(sopt.equals("subject")){    //제목 검색
                andBuilder.and(qPost.subject.contains(skey));
            }
        }

        Pageable pageable = PageRequest.of(page, limit, Sort.by(desc("isNotice"), desc("createdAt")));
        Page<Post> data = postRepository.findAll(andBuilder, pageable);
        return data;
    }

    //페이징 처리
    public Page<Post> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, 20, Sort.by(sorts));
        return postRepository.findAll(pageable);
    }
}
