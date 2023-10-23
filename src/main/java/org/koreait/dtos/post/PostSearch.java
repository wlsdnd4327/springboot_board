package org.koreait.dtos.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 게시글 검색 처리
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearch {

    private int page = 0;

    private int limit = 20; //한페이지당 최대 글 수

    private String sopt;    //검색 조건

    private String skey;    //검색 키워드

    private String bid; // 게시판 아이디
}
