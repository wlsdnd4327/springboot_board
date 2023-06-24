package org.koreait.controllers.admin.lecture;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.instructor.LectureForm;
import org.koreait.entities.lecture.LectureEntity;
import org.koreait.models.lecture.LectureInfoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/lecture")
@RequiredArgsConstructor
public class AdminLectureController {

    private final LectureListService lectureListService;
    private final LectureInfoService lectureInfoService;

    /**
     * 강의 목록
     */
    @GetMapping
    public String index(@ModelAttribute LectureSearch lectureSearch, Model model) {

        Page<LectureEntity> lectureEntity = lectureListService.gets(lectureSearch);
        model.addAttribute("items", lectureEntity.getContent());


        /**
        Page<LectureEntity> lectureEntities = lectureListService.gets(lectureSearch);
        model.addAttribute("items", lectureEntities.getContent());
        */
        return "admin/lecture/index";
    }

    /**
     * 강의 수정

    public String update(@PathVariable Long lectureNo, Model model){

        LectureEntity lectureEntity = lectureInfoService.get(lectureNo);
        LectureForm lectureForm = new ModelMapper().map(lectureEntity, LectureForm.class);
        LectureForm.setMode("update");

        model.addAttribute("lectureForm", lectureForm);

        return "admin/lecture/config";

    }

     */

}