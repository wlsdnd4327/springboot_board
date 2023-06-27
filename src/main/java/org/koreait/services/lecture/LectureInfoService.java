package org.koreait.services.lecture;


import lombok.RequiredArgsConstructor;
import org.koreait.exceptions.lecture.LectureNotFoundException;
import org.koreait.entities.lecture.LectureEntity;
import org.koreait.repositories.LectureEntityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureInfoService {
    private final LectureEntityRepository repository;
/**
    private final LectureEntityRepository repository;

    public LectureForm get(Long lectureNo){
        LectureEntity lectureEntity = repository.findById(lectureNo).orElseThrow(LectureDuplicateException::new);
        LectureForm lectureForm = LectureForm.builder()
                .category(lectureEntity.getCategory().toString().toLowerCase())
                .instructor(lectureEntity.getInstructor())
                .title(lectureEntity.getTitle())
                .startDt(lectureEntity.getStartDt().toString())
                .endDt(lectureEntity.getEndDt().toString())
                .capacity(lectureEntity.getCapacity())
                .price(lectureEntity.getPrice().toString())
                .content(lectureEntity.getContent())
                .build();

        return lectureForm;
    }

    public LectureForm get(String title){
        if(title == null || title.isBlank()){
            throw new LectureDuplicateException();
        }
        LectureEntity lectureEntity = repository.findByTitle(title);
        if(lectureEntity == null){
            throw  new LectureDuplicateException();
        }

        LectureForm lectureForm = LectureForm.builder()
                .category(lectureEntity.getCategory().toString().toString().toLowerCase())
                .instructor(lectureEntity.getInstructor())
                .title(lectureEntity.getTitle())
                .startDt(lectureEntity.getStartDt().toString())
                .endDt(lectureEntity.getEndDt().toString())
                .capacity(lectureEntity.getCapacity())
                .price(lectureEntity.getPrice().toString())
                .content(lectureEntity.getContent())
                .build();

        return lectureForm;
    }

 */


    private final LectureEntityRepository lectureEntityRepository;

    public LectureEntity get(Long lectureNo){
        LectureEntity data = repository.findById(lectureNo).orElseThrow(LectureNotFoundException::new);

        return data;
    }





}

