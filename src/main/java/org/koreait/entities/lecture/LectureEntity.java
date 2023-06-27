package org.koreait.entities.lecture;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.koreait.commons.constants.Category;
import org.koreait.commons.constants.LectureStatus;
import org.koreait.entities.BaseEntity;

import java.time.LocalDate;

@Entity @Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class LectureEntity extends BaseEntity {


    @Id
    @GeneratedValue
    private Long lectureNo; //강의고유번호

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LectureStatus lectureStatus = LectureStatus.READY; //강의 상태

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category; //카테고리 구분

    @Column(length = 40, nullable = false)
    private String instructor; //강사명

    @Column(length = 70, nullable = false, unique = true)
    private String title; //강의제목

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate startDt; //시작일

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private LocalDate endDt; //종료일

    @Column(nullable = false)
    private Integer capacity; //수용인원

    @Column(nullable = false)
    private Integer numOfStu=0; //신청인원

    @Column(nullable = false)
    private Long price; //수강료

    @Column(nullable = false)
    private String content; //강의소개
}
