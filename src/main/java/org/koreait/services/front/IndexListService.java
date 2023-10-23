package org.koreait.services.front;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.koreait.dtos.front.IndexForm;
import org.koreait.entities.post.Post;
import org.koreait.entities.post.QPost;
import org.koreait.repositories.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class IndexListService {

    private final PostRepository postRepository;

    public Page<Post> gets(IndexForm indexForm){
        QPost qPost = QPost.post;
        BooleanBuilder andBuilder = new BooleanBuilder();
        int limit = indexForm.getLimit();
        int page = indexForm.getPage();

        Pageable pageable = PageRequest.of(page, limit, Sort.by(desc("isNotice"), desc("createdAt")));
        Page<Post> data = postRepository.findAll(andBuilder, pageable);
        return data;
    }
}
