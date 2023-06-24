package org.koreait.controllers.admin.lecture;


import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.koreait.entities.lecture.LectureEntity;
import org.koreait.entities.lecture.QLectureEntity;
import org.koreait.repositories.LectureEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LectureListService {

    private final LectureEntityRepository lectureEntityRepository;

    public Page<LectureEntity> gets(LectureSearch lectureSearch){

        QLectureEntity lectureEntity = QLectureEntity.lectureEntity;

        BooleanBuilder andBuilder = new BooleanBuilder();
        int page = lectureSearch.getPage();
        int limit = lectureSearch.getLimit();
        page = page < 1 ? 1:page;
        limit = limit < 1? 20:limit;


        /**
         * 검색 조건 처리
         */

        String sopt = lectureSearch.getSopt();
        String skey = lectureSearch.getSkey();
        if(sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {

            sopt = sopt.trim();
            skey = sopt.trim();

            if (sopt.equals("all")) { //통합검색 - category, instructor, title

                BooleanBuilder orBuilder = new BooleanBuilder();
                //orBuilder.or(lectureEntity.category(Enum.valueOf(Category)))
                orBuilder.or(lectureEntity.instructor.contains(skey))
                        .or(lectureEntity.title.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("instructor")) {
                andBuilder.and(lectureEntity.instructor.contains(skey));
            } else if (sopt.equals("title")) {
                andBuilder.and(lectureEntity.title.contains(skey));
            }
        }


        Pageable pageable = PageRequest.of(page -1, limit, Sort.by(Sort.Order.desc("startDt")));
        Page<LectureEntity> lecturentity = lectureEntityRepository.findAll(andBuilder, pageable);

        return lecturentity;
    }
}
