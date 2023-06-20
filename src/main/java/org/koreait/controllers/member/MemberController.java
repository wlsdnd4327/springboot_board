package org.koreait.controllers.member;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.models.member.FindMemberService;
import org.koreait.models.member.MemberSaveService;
import org.koreait.models.member.MemberUpdateService;
import org.koreait.models.member.MemberWithdrawalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final FindMemberService findMemberService;
    private final MemberSaveService saveService;
    private final MemberUpdateService updateService;
    private final MemberWithdrawalService withdrawalService;
    private final JoinValidator joinValidator;


    // 회원가입
    @GetMapping("/join")
    public String join(Model model) {

        commonProcess(model, "회원가입");

        JoinForm joinForm = new JoinForm();

        model.addAttribute("joinForm", joinForm);

        return "member/join";
    }

    @PostMapping("/join")
    public String joinPs(@Valid JoinForm joinForm, Errors errors, Model model) {

        commonProcess(model, "회원가입");

        joinValidator.validate(joinForm, errors);

        if (errors.hasErrors()) {
            return "member/join";
        }

        saveService.save(joinForm);

        model.addAttribute("joinForm", joinForm);

        return "redirect:/member/login";
    }

    //로그인
    @GetMapping("/login")
    public String login(@ModelAttribute LoginForm loginForm, HttpSession session,
                        @CookieValue(required = false) String saveId, Model model) {

        if (saveId != null) {
            loginForm.setMemberId(saveId);
            loginForm.setSaveId(true);
        }

        String memberId = (String) session.getAttribute("memberId");
        if (memberId != null) {
            loginForm.setMemberId(memberId);
        }

        commonProcess(model, "로그인");
        return "member/login";
    }

    //회원 아이디 찾기
    @GetMapping("/findId")
    public String findId(@ModelAttribute FindIdForm findIdForm, Model model) {
        commonProcess(model, "아이디 찾기");
        return "member/findid";
    }

    @PostMapping("/findId")
    public String findIdPs(@Valid FindIdForm findIdForm, Errors errors, Model model) {

        if (errors.hasErrors()) {
            return "member/findid";
        }

        String memberId = findMemberService.findId(findIdForm);

        if (memberId == null) {
            errors.reject("Validation.member.notExists");
        }

        model.addAttribute("findIdData", memberId);

        commonProcess(model, "아이디 찾기");

        return "member/findid";
    }

    //회원 비번 찾기
    @GetMapping("/findPw")
    public String findPw(Model model) {
        commonProcess(model, "비밀번호 찾기");
        return "member/findpw";
    }

    @PostMapping("/findPw")
    public String findPwPs(String memberId, Model model) {
        commonProcess(model, "비밀번호 찾기");
        try {
            long memberNo = findMemberService.findPw(memberId);
            return "redirect:/member/resetPw/" + memberNo;
        } catch (Exception e) {
            e.printStackTrace();
            String script = String.format("Swal.fire('회원을 찾을 수 없습니다.', '', 'error').then(function(){history.go(-1);})");
            model.addAttribute("script", script);
            model.addAttribute("memberId",memberId);
            return "common/sweet_script";
        }
    }

    // 회원 비번 설정하기
    @GetMapping("/resetPw/{no}")
    public String resetPw(@PathVariable long no, @ModelAttribute JoinForm joinForm, Model model) {
        commonProcess(model, "새 비밀번호 설정");
        return "member/resetPw";
    }

    @PostMapping("/resetPw/{no}")
    public String resetPwPs(@PathVariable long no,@ModelAttribute JoinForm joinForm, Errors errors, Model model) {
        String memberPw = joinForm.getMemberPw();
        try{
            joinValidator.validate(joinForm,errors);

            if(errors.hasErrors()){
                return "member/resetPw";
            }

            updateService.updatePw(no,memberPw);
        }catch(Exception e){
            return "member/findpw";
        }

        String script = "Swal.fire('수정 완료!', '', 'success').then(function(){location.href='/member/login';})";

        model.addAttribute("script",script);

        return "common/sweet_script";
    }


    // 회원 탈퇴
    @GetMapping("/withdrawal")
    public String withdrawal(Model model){
        commonProcess(model,"회원 탈퇴");
        return "member/withdrawal";
    }

    @PostMapping("/withdrawal")
    public String withdrawalPs(Model model, HttpSession httpSession, String memberPw, String withdrawalMsg){
        MessageDto message = null;

        if(!withdrawalMsg.isBlank() && withdrawalMsg != null) {

            withdrawalService.withdrawal(memberPw);

            message = new MessageDto("회원 탈퇴가 완료되었습니다.", "/member/login", RequestMethod.GET, null);

            httpSession.removeAttribute("memberInfo");

            httpSession.invalidate();

            return showMsgAndRedirect(message, model);

        }else {

            message = new MessageDto("확인 메시지 입력 바랍니다.","/member/withdrawal",RequestMethod.GET,null);

            commonProcess(model, "회원 탈퇴");

            return "member/withdrawal";
        }
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("addCss","style2");
        model.addAttribute("title",title);
    }

    private String showMsgAndRedirect(MessageDto params, Model model){
        model.addAttribute("params",params);
        return "common/messageRedirect";
    }
}
