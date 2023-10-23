package org.koreait.services.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.koreait.dtos.member.MemberInfo;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        /**
         * 1. 세션에 담겨있는 로그인 에러 메시지 삭제(초기화)
         * 2. 로그인한 회원 정보 쉽게 확인할 수 있도록 세션 처리
         * 3. 쿠키 등록하여 아이디 저장 기능 구현
         * 4. 로그인 성공 시 메인페이지로 이동
         */

        //1. 세션에 담겨있는 로그인 에러 메시지 삭제(초기화)
        HttpSession session = request.getSession();
        session.removeAttribute("memberId");
        session.removeAttribute("requiredMemberId");
        session.removeAttribute("requiredMemberPw");
        session.removeAttribute("global");

        //2. 로그인한 회원 정보 쉽게 확인할 수 있도록 세션 처리
        MemberInfo memberInfo = (MemberInfo) authentication.getPrincipal();
        session.setAttribute("memberInfo",memberInfo);

        //3. 쿠키 등록하여 아이디 저장 기능 구현
        Cookie cookie = new Cookie("saveId",request.getParameter("memberId"));

        if (request.getParameter("saveId") == null) { // 쿠기 제거
            cookie.setMaxAge(0);
        } else { // 쿠키 등록
            cookie.setMaxAge(60 * 60 * 24 * 365);
        }

        response.addCookie(cookie);

        //4. 로그인 성공 시 메인페이지로 이동
        String redirectUrl = request.getParameter("redirectUrl");

        String url = request.getContextPath();
        if (redirectUrl == null) { // 로그인 후 이동 경로가 없다면 메인 경로(/)로 이동
            url += "/";
        } else { // 있다면 설정한 경로로 이동
            url += redirectUrl;
        }

        response.sendRedirect(url);
    }

}
