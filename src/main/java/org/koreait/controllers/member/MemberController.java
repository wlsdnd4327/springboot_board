package org.koreait.controllers.member;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/join")
    public String join(Model model){
        JoinForm joinForm = new JoinForm();
        model.addAttribute("joinForm",joinForm);

        model.addAttribute("addCss","style2");
        return "member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model){
        model.addAttribute("addCss","style2");
        return "member/join";
    }
}
