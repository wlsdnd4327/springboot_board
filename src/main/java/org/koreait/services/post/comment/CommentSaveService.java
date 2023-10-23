package org.koreait.services.post.comment;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.utils.MemberUtil;
import org.koreait.dtos.post.CommentForm;
import org.koreait.entities.member.MemberEntity;
import org.koreait.entities.post.Comment;
import org.koreait.entities.post.Post;
import org.koreait.entities.post.QComment;
import org.koreait.exceptions.post.comment.CommentNotExistsException;
import org.koreait.exceptions.post.PostNotExistsException;
import org.koreait.repositories.CommentRepository;
import org.koreait.repositories.PostRepository;
import org.springframework.stereotype.Service;

/**
 * 댓글 저장 및 수정
 *
 * 댓글 등록시(수정시는 다음 엔티티는 추가 하지 않음 - 변경되면 안되는 값)
 * 1. 댓글 저장시 등록횐 회원 엔티티 추가
 * 2. 댓글에 연결된 게시글 엔티티 추가
 */
@Service
@RequiredArgsConstructor
public class CommentSaveService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberUtil memberUtil;

    public void save(CommentForm form) {
        Long postId = form.getPostId();
        Post post = postRepository.findById(postId).orElseThrow(PostNotExistsException::new);

        Long id = form.getId();
        Comment comment = null;
        if (id != null) {
            comment = commentRepository.findById(id).orElseThrow(CommentNotExistsException::new);
        } else { // 추가
            MemberEntity member = memberUtil.getEntity();
            comment = new Comment();
            comment.setPost(post);
            comment.setMember(member);
            comment.setPoster(member.getMemberNm());
        }

        comment.setContent(form.getContent());

        commentRepository.saveAndFlush(comment);
        form.setId(comment.getId());

        // 댓글 카운트 업데이트
        QComment qComment = QComment.comment;

        int cnt = (int)commentRepository.count(qComment.post.id.eq(postId));
        post.setCommentCnt(cnt);
        postRepository.flush();
    }

    public void update(Long id, String content) {
        Comment comment = commentRepository.findById(id).orElse(null);
        if (comment == null) return;

        comment.setContent(content);
        commentRepository.flush();
    }
}
