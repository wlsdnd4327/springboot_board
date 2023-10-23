package org.koreait.services.post.comment;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.post.Comment;
import org.koreait.entities.post.Post;
import org.koreait.entities.post.QComment;
import org.koreait.exceptions.post.comment.CommentNotExistsException;
import org.koreait.repositories.CommentRepository;
import org.koreait.repositories.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDeleteService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    /**
     * 댓글 삭제
     */
    public void delete(Long id){
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotExistsException::new);
        Post post = comment.getPost();
        Long postId = post.getId();
        commentRepository.delete(comment);
        commentRepository.flush();

        // 댓글 카운트 업데이트
        QComment qComment = QComment.comment;

        int cnt = (int)commentRepository.count(qComment.post.id.eq(postId));
        post.setCommentCnt(cnt);
        postRepository.flush();
    }
}
