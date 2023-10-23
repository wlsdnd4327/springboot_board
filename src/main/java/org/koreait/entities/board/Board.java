package org.koreait.entities.board;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.Role;
import org.koreait.entities.BaseEntity;

@Entity @Data @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends BaseEntity {

    @Id
    @Column(length = 30)
    private String bId; //게시판아이디

    @Column(length = 60, nullable = false)
    private String bName;   //게시판명

    @Column(name = "isUse")
    private boolean use;    //게시판 노출 여부

    private int rowsOfPage = 20;    //한 페이지당 게시글 수

    private boolean showViewList;   //게시글 하단 목록 노출

    @Lob
    private String category;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role listAccessRole = Role.ALL; //목록 접근 권한

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role viewAccessRole = Role.ALL; //글보기 접근 권한

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role writeAccessRole = Role.ALL; //글쓰기 접근 권한

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Role commentAccessRole = Role.ALL; //댓글 접근 권한

    private boolean useEditor;  //에디터

    private boolean useAttachFile;  //파일 첨부

    private boolean useAttachImage; //이미지 첨부

    private boolean useComment; //댓글

    /**
     * 게시판 분류 목록 나열
     * @return
     */
    public String[] getCategories(){
        if(category == null){
            return null;
        }
        String[] categories = category.replaceAll("\\r", "").trim().split("\\n");
        return categories;
    }
}
