package org.koreait.entities.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.entities.BaseEntity;
import org.koreait.entities.board.Board;

import java.util.UUID;

@Entity @Data @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(indexes = {@Index(name = "idx_post_category", columnList = "category DESC"), @Index(name = "idx_post_createAt", columnList = "createdAt DESC")})
public class Post extends BaseEntity {
    @Id @GeneratedValue
    private Long id;    //게시글 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bId")
    private Board board;

    @Column(length = 65, nullable = false)
    private String gid = UUID.randomUUID().toString(); //그룹아이디

    @Column(length = 40, nullable = false)
    private String poster;  //작성자

    @Column(length = 60)
    private String category;    //게시판 분류

    @Column(nullable = false)
    private String subject; //제목

    @Lob
    @Column(nullable = false)
    private String content; //내용

    private int hit;//조회수

    @Column(length = 125)
    private String ua;  //user-agent : 브라우저 정보

    @Column(length = 20)
    private String ip;  //작성자 ip 주소

    private int commentCnt; // 댓글수
}
