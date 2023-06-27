package org.koreait.controllers.admin.lecture;


import lombok.Data;

@Data
public class LectureSearch {


    private int page =1;
    private int limit = 20;
    private String sopt; //검색조건
    private String skey; // 검색키워드

}
