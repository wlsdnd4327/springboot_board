package org.koreait.dtos.lecture;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class LectureForm {

    private String mode; // update : 수정, 없으면 추가

    private Long lectureNo; //강고유번호

    @NotBlank
    private String category; // 카테고리

    @NotBlank
    private String instructor;// 강사명

    @NotBlank
    private String title; //강의명

    @NotBlank
    private String startDt; //시작일

    @NotBlank
    private String endDt; //종료일

    @NotNull
    @Min(1)
    private Integer capacity; // 정원

    @NotBlank
    private String price; // 수강료

    @NotBlank
    @Size(min = 10, max = 200)
    private String content; // 강의설명

}
