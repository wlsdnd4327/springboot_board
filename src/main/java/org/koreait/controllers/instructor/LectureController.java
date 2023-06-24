package org.koreait.controllers.instructor;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonException;
import org.koreait.entities.lecture.LectureEntity;
import org.koreait.models.lecture.LectureInfoService;
import org.koreait.models.lecture.LectureSaveService;
import org.koreait.repositories.LectureEntityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/instructor")
@RequiredArgsConstructor
public class LectureController {

    private final LectureSaveService lectureSaveService;
    private final LectureInfoService lectureInfoService;
    private final LectureValidator lectureValidator;


    /**
     * 강의 등록 폼
     */
    @RequestMapping("/addLecture")
    public String addLecture(@ModelAttribute LectureForm lectureForm, Model model){

        return "front/instructor/addLecture";
    }


    /**
     * 강의 수정
     */
    @GetMapping("/{lectureNo}/update")
    public String update(@PathVariable Long lectureNo, Model model){

        LectureEntity lectureEntity = lectureInfoService.get(lectureNo);
        LectureForm lectureForm = new ModelMapper().map(lectureEntity, LectureForm.class);
        lectureForm.setMode("update");

        model.addAttribute("lectureForm", lectureForm);

        return "front/instructor/addLecture";

    }

    /**
     * 강의 등록 저장
     */
    @PostMapping("/addLecture")
    public String addLecturePs(@Valid  LectureForm lectureForm, Errors errors, Model model){

        lectureValidator.validate(lectureForm, errors);
        lectureSaveService.save(lectureForm, errors);
        String url = "/instructor/save";

        if(lectureForm.getLectureNo() != null){
            model.addAttribute("mode", "update");
            url += "?mode=update";
        }
        if(errors.hasErrors()){
            return "front/instructor/addLecture";
        }

        return "redirect:"+url;
    }

    /**
     * 강의 등록 성공 -> 페이지 이동
     * @param mode
     * @param model
     * @return
     */
    @GetMapping("/save")
    public String saveSuccess(String mode, Model model){

        model.addAttribute("mode", mode);

        return "/front/instructor/saveSuccess";
    }

}