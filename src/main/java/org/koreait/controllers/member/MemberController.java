package org.koreait.controllers.member;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.koreait.dtos.member.FindIdForm;
import org.koreait.dtos.member.JoinForm;
import org.koreait.dtos.member.LoginForm;
import org.koreait.dtos.member.PwResetForm;
import org.koreait.services.member.FindMemberService;
import org.koreait.services.member.MemberSaveService;
import org.koreait.services.member.MemberUpdateService;
import org.koreait.services.member.MemberWithdrawalService;
import org.koreait.validators.member.JoinValidator;
import org.koreait.validators.member.PwValidator;
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
    private final PwValidator pwValidator;

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
    public String login(@ModelAttribute LoginForm loginForm, HttpServletRequest request,
                        @CookieValue(required = false) String saveId, Model model, @Valid Errors errors) {

        HttpSession session = request.getSession();
        String referer = request.getHeader("Referer");

        if (!referer.contains("/member/login")) {
            session.removeAttribute("memberId");
            session.removeAttribute("requiredMemberId");
            session.removeAttribute("requiredMemberPw");
            session.removeAttribute("global");
        }

        if(errors.hasErrors()){
            return "member/login";
        }

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

    //마이페이지
    @GetMapping("/mypage")
    public String mypage(Model model){
        commonProcess(model,"마이페이지");
        return "member/mypage";
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
    public String findPwPs(String memberId, Model model, HttpSession session) {
        commonProcess(model, "비밀번호 찾기");
        try {
            long memberNo = findMemberService.findPw(memberId);
            session.setAttribute("memberNo", memberNo);
            return "redirect:/member/resetPw";
        } catch (Exception e) {
            e.printStackTrace();
            String script = String.format("Swal.fire('회원을 찾을 수 없습니다!!', '', 'error').then(function(){history.go(-1);})");
            model.addAttribute("script", script);
            model.addAttribute("memberId",memberId);
            return "commons/sweet_script";
        }
    }

    // 회원 비번 설정하기
    @GetMapping("/resetPw")
    public String resetPw(@ModelAttribute PwResetForm pwResetForm, Model model) {
        commonProcess(model, "새 비밀번호 설정");
        return "member/resetPw";
    }


    @PostMapping("/resetPw")
    public String resetPwPs(@ModelAttribute @Valid PwResetForm pwResetForm, Errors errors, Model model,
                            HttpSession session) {

        Long memberNo = (Long)session.getAttribute("memberNo");

        if (memberNo == null) {
            throw new RuntimeException("잘못된 접근입니다.");
        }

        String memberPw = pwResetForm.getMemberPw();

        try{

            pwValidator.validate(pwResetForm,errors);

            if(errors.hasErrors()){
                return "member/resetPw";
            }

            updateService.updatePw(memberNo,memberPw);
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/member/findPw";
        }

        String script = "Swal.fire('수정 완료!', '', 'success').then(function(){location.href='/member/login';})";

        model.addAttribute("script",script);

        return "commons/sweet_script";
    }


    // 회원 탈퇴
    @GetMapping("/withdrawal")
    public String withdrawal(Model model){
        commonProcess(model,"회원 탈퇴");
        return "member/withdrawal";
    }

    @PostMapping("/withdrawal")
    public String withdrawalPs(Model model, String memberPw, String withdrawalMsg, HttpServletResponse response, HttpServletRequest request){
        HttpSession httpSession = request.getSession();
        commonProcess(model, "회원 탈퇴");

        if(!withdrawalMsg.isBlank() && withdrawalMsg != null) {

            withdrawalService.withdrawal(memberPw);

            String script = "Swal.fire('회원 탈퇴가 완료되었습니다.', '', 'success').then(function(){location.href='/member/login';})";

            model.addAttribute("script",script);

            httpSession.removeAttribute("memberInfo");

            httpSession.invalidate();

            /* 아이디 저장 쿠키 삭제 S */
            Cookie cookie = new Cookie("saveId", "");
            cookie.setMaxAge(0);
            cookie.setPath(request.getContextPath() + "/member");
            response.addCookie(cookie);
            /* 아이디 저장 쿠키 삭제 E */

            return "commons/sweet_script";

        }else {

            String script = "Swal.fire('확인 메시지 입력 바랍니다.', '', 'error').then(function(){history.go(-1);})";

            model.addAttribute("script",script);

            return "commons/sweet_script";
        }
    }

    private void commonProcess(Model model, String title) {
        model.addAttribute("title",title);
    }
}