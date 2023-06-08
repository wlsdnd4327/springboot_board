package org.koreait.models.member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class LoginFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        /**
         * 1. 세션에 담긴 로그인 에러 메세지 삭제 - 이전 로그인 시 저장된 내용 초기화.
         * 2. 로그인한 회원 정보 쉽게 확인하도록 세션 처리
         * 3. 로그인 성공시 메인페이지
         */

        HttpSession session = request.getSession();
    }
}
