package org.koreait.repositories;

import org.koreait.entities.lecture.LectureEntity;
import org.koreait.entities.lecture.QLectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.time.LocalDate;

import java.util.List;

public interface LectureEntityRepository extends JpaRepository<LectureEntity, Long>, QuerydslPredicateExecutor<LectureEntity> {


    LectureEntity findByTitle(String title);


    default boolean exists(String title) {
        QLectureEntity lectureEntity = QLectureEntity.lectureEntity;

        return exists(lectureEntity.title.eq(title));
    }


    public default LocalDate toLocalDate(String date){
        if(date != null && !date.isBlank()){
            return LocalDate.parse(date);
        }
        return null;
    }



}
