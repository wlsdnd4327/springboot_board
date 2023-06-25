package org.koreait.repositories;

import org.koreait.entities.PostView;
import org.koreait.entities.PostViewId;
import org.koreait.entities.QPostView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PostViewRepository extends JpaRepository<PostView, PostViewId>, QuerydslPredicateExecutor<PostView> {

    /**
     * 게시글 조회수 조회
     * @param id
     * @return
     */
    default long getHit(Long id){
        QPostView postView = QPostView.postView;
        return count(postView.id.eq(id));
    }

}
