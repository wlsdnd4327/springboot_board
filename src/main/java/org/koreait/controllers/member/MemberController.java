package org.koreait.controllers.member;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.commons.CommonFunc;
import org.koreait.models.member.MemberSaveService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController{

    private final MemberSaveService saveService;
    private final JoinValidator validator;

    @GetMapping("/join")
    public String join(Model model){
        commonProcess(model, "회원가입");
        JoinForm joinForm = new JoinForm();
        model.addAttribute("joinForm",joinForm);

        model.addAttribute("addCss","style2");
        return "member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model){
        commonProcess(model, "회원가입");

        validator.validate(joinForm,errors);

        if(errors.hasErrors()) {return "member/join";}

        saveService.save(joinForm);

        model.addAttribute("joinForm",joinForm);
        model.addAttribute("addCss","style2");

        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpSession session,
                        @CookieValue(required = false) String saveId, Model model){
        if(saveId != null){
            loginForm.setMemberId(saveId);
            loginForm.setSaveId(true);
        }

        String memberId = (String) session.getAttribute("memberId");
        if(memberId != null){
            loginForm.setMemberId(memberId);
        }

        commonProcess(model,"로그인");
        return "member/login";
    }

    public void commonProcess(Model model, String title) {
        model.addAttribute("title",title);
    }
}
