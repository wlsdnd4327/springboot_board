package org.koreait.dtos.admin.board;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardForm {

    private String mode;    //update -> 수정, 없으면 추가

    @NotBlank
    private String bId; //게시판 아이디

    @NotBlank
    private String bName;   //게시판명

    private boolean use;    //사용 여부

    private int rowsOfPage = 20;    //한 페이지당 게시글 수

    private boolean showViewList;   //게시글 하단 목록 노출

    private String category;

    private String listAccessRole = "ALL"; //목록 접근 권한

    private String viewAccessRole = "ALL"; //글보기 접근 권한

    private String writeAccessRole = "ALL"; //글쓰기 접근 권한

    private String commentAccessRole = "ALL"; //댓글 접근 권한

    private boolean useEditor;  //에디터

    private boolean useAttachFile;  //파일 첨부

    private boolean useAttachImage; //이미지 첨부

    private boolean useComment;   //댓글
}
