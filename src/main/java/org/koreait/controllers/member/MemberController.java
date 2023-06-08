package org.koreait.controllers.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.models.member.MemberSaveService;
import org.koreait.repositories.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberSaveService saveService;
    private final JoinValidator validator;

    @GetMapping("/join")
    public String join(Model model){
        JoinForm joinForm = new JoinForm();
        model.addAttribute("joinForm",joinForm);

        model.addAttribute("addCss","style2");
        return "member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model){

        validator.validate(joinForm,errors);

        if(errors.hasErrors()) {return "member/join";}

        saveService.save(joinForm);

        model.addAttribute("joinForm",joinForm);
        model.addAttribute("addCss","style2");

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(){
        return "member/login";
    }
}
