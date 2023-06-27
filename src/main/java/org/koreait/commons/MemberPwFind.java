package org.koreait.commons;

import org.koreait.dtos.member.SendMailDto;

public interface MemberPwFind {
    /**
     * 회원 비밀번호 찾기 설계
     * 사용자로부터 아이디를 입력 받음.
     * 아이디 존재 여부 확인 후 db에 있으면 pass, 없으면 회원정보가 없다고 오류 메시지 출력.
     * 메일 전송 서비스 구현
     *  - 메일 생성.(url 넘기고 해당 url에 들어갈 경우, 새로운 비밀번호 생성 및 변경 가능하도록 구현)
     *  - 메일 보내기.
     * 입력받은 값으로 회원 비밀번호 변경.
     */
    public SendMailDto writeMail(String id);
    public void sendMail(SendMailDto mailDto);
}
