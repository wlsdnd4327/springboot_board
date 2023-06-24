package org.koreait.models.lecture;


import lombok.RequiredArgsConstructor;
import org.koreait.commons.constants.Category;
import org.koreait.controllers.instructor.LectureNotFoundException;
import org.koreait.controllers.instructor.LectureForm;
import org.koreait.entities.lecture.LectureEntity;
import org.koreait.repositories.LectureEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * 강의 추가, 수정
 * -수정 시 강의명이 있을 때만 수정
 */
@Service
@RequiredArgsConstructor
public class LectureSaveService {

    private final LectureEntityRepository lectureEntityRepository;

    public void save(LectureForm lectureForm, Errors errors) {

        if (lectureForm.getLectureNo() != null) {
            update(lectureForm.getLectureNo(), lectureForm, errors);
            return;
        }
        if (errors.hasErrors()) {
            return;
        }
        LectureEntity lectureEntity = new LectureEntity();
        lectureEntity.setCategory(Enum.valueOf(Category.class, lectureForm.getCategory().toUpperCase()));
        lectureEntity.setInstructor(lectureForm.getInstructor());
        lectureEntity.setTitle(lectureForm.getTitle());
        lectureEntity.setStartDt(lectureEntityRepository.toLocalDate(lectureForm.getStartDt()));
        lectureEntity.setEndDt(lectureEntityRepository.toLocalDate(lectureForm.getEndDt()));
        lectureEntity.setCapacity(lectureForm.getCapacity());
        lectureEntity.setPrice(Long.valueOf(lectureForm.getPrice()));
        lectureEntity.setContent(lectureForm.getContent());
        lectureEntityRepository.save(lectureEntity);
    }


    public void update(Long lectureNo, LectureForm lectureForm, Errors errors){
        if(errors.hasErrors()) {
            return;
        }
        LectureEntity lectureEntity = lectureEntityRepository.findById(lectureNo).orElseThrow(()->new LectureNotFoundException());
        lectureEntity.setCategory(Enum.valueOf(Category.class, lectureForm.getCategory().toUpperCase()));
        lectureEntity.setInstructor(lectureForm.getInstructor());
        lectureEntity.setTitle(lectureForm.getTitle());
        lectureEntity.setStartDt(lectureEntityRepository.toLocalDate(lectureForm.getStartDt()));
        lectureEntity.setEndDt(lectureEntityRepository.toLocalDate(lectureForm.getEndDt()));
        lectureEntity.setCapacity(lectureForm.getCapacity());
        lectureEntity.setPrice(Long.valueOf(lectureForm.getPrice()));
        lectureEntity.setContent(lectureForm.getContent());

        lectureEntityRepository.flush();
    }


    /** 최근 강의

    public void save(LectureForm lectureForm){
        save(lectureForm, null);
    }

    public void save(LectureForm lectureForm, Errors errors){

        if(errors != null && errors.hasErrors()){
            return;
        }


          //강의 조회 -> 없으면 엔티티 생성
          //신규 등록 시 중복 여부 체크

        Long lectureNo = lectureForm.getLectureNo();
        LectureEntity lectureEntity = lectureEntityRepository.findById(lectureNo).orElseGet(LectureEntity::new);

        String mode = lectureForm.getMode();
        if ((mode == null && !mode.equals("update")) && lectureEntity.getLectureNo() != null) {
            throw new LectureDuplicateException();
        }

        lectureEntity.setCategory(Enum.valueOf(Category.class, lectureForm.getCategory().toUpperCase()));
        lectureEntity.setInstructor(lectureForm.getInstructor());
        lectureEntity.setTitle(lectureForm.getTitle());
        lectureEntity.setStartDt(lectureEntityRepository.toLocalDate(lectureForm.getStartDt()));
        lectureEntity.setEndDt(lectureEntityRepository.toLocalDate(lectureForm.getEndDt()));
        lectureEntity.setCapacity(lectureForm.getCapacity());
        lectureEntity.setPrice(Long.valueOf(lectureForm.getPrice()));
        lectureEntity.setContent(lectureForm.getContent());

        lectureEntityRepository.saveAndFlush(lectureEntity);
    }

    */
}
