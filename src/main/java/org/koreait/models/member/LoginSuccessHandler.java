package org.koreait.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        /**
         * 1. 세션에 담겨있는 로그인 에러 메시지 삭제(초기화
         * 2. 로그인한 회원 정보 쉽게 확인할 수 있도록 세션 처리
         * 3. 로그인 성공 시 메인페이지로 이동
         */
        HttpSession session = request.getSession();
        session.removeAttribute("memberId");
        session.removeAttribute("RequiredMemberId");
        session.removeAttribute("RequiredMemberPw");
        session.removeAttribute("global");

        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        session.setAttribute("memberInfo",memberInfo);

        String url = request.getContextPath() + "/";
        response.sendRedirect(url);
    }
}
