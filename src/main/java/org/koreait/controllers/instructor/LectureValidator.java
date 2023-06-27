package org.koreait.controllers.instructor;

import lombok.RequiredArgsConstructor;
import org.koreait.repositories.LectureEntityRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
@RequiredArgsConstructor
public class LectureValidator implements Validator {

    private final LectureEntityRepository lectureEntityRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return LectureForm.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        LectureForm lectureForm = (LectureForm) target;
        String mode = lectureForm.getMode();
        String category = lectureForm.getCategory();
        String title = lectureForm.getTitle();
        String startDt = lectureForm.getStartDt();
        String endDt = lectureForm.getEndDt();
        Integer capacity = lectureForm.getCapacity();
        String price = lectureForm.getPrice();
        String content = lectureForm.getContent();

        //1. 카테고리가 blank면, 카테고리 입력해달리는 에러 코드 지정
        if(category != null && !category.isBlank() && category.trim().equalsIgnoreCase("blank")){
            errors.rejectValue("category","Validation.notChoiceCategory");
        }

        //2.강의 제목 중복 여부
        if(mode != null && !mode.equals("update") && title != null && !title.isBlank() && lectureEntityRepository.exists(title)){
            errors.rejectValue("title","Validation.duplicate.title");
        }

        //3.시작일이 종료일보다 이전인지 확인
        if(startDt != null && endDt != null){
            int cnt = startDt.compareTo(endDt);
            if(cnt > 0){
                errors.rejectValue("startDt","Validation.checkDt");
            }
        }

    }

}
