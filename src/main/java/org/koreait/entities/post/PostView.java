package org.koreait.entities.post;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;

@Entity @Data
@IdClass(PostViewId.class)
public class PostView {

    @Id
    private Long id;    //게시글 번호

    @Id
    @Column(length = 40)
    private String uid; //ip + ua + 회원번호


}
