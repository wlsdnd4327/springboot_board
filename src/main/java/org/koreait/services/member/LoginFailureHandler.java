package org.koreait.services.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.exceptions.member.LoginValidationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String memberId = request.getParameter("memberId");
        String memberPw = request.getParameter("memberPw");

        //세션에 담겨있는 로그인 에러 메시지 삭제(초기화)
        session.removeAttribute("memberId");
        session.removeAttribute("requiredMemberId");
        session.removeAttribute("requiredMemberPw");
        session.removeAttribute("global");

        session.setAttribute("memberId",memberId); // 로그인 템플릿 쪽에 메시지 표시 및 실패 시 로그인 아이디 값을 유지하기 위한 값.

        try{
            if(memberId == null || memberId.isBlank()){
                throw new LoginValidationException("requiredMemberId","NotBlank.joinForm.memberId");
            }
            if(memberPw == null || memberPw.isBlank()){
                throw new LoginValidationException("requiredMemberPw","NotBlank.joinForm.memberPw");
            }

            throw new LoginValidationException("global","Validation.login.fail");   //비밀번호 또는 아이디가 틀렸을때 발생하는 예외

        }catch(LoginValidationException e){
            session.setAttribute(e.getField(),e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/member/login");  //실패시 다시 로그인 페이지 이동
    }
}
