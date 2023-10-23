package org.koreait.entities.post;

import jakarta.persistence.*;
import lombok.*;
import org.koreait.entities.BaseEntity;
import org.koreait.entities.member.MemberEntity;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntity {
    @Id @GeneratedValue
    private Long id;    //댓글 번호

    @Column(length = 40, nullable = false)
    private String poster;  //작성자

    @Lob
    @Column(nullable = false)
    private String content; //내용

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="userNo")
    private MemberEntity member;  //작성회원

    @ToString.Exclude
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="postId")
    private Post post;
}
