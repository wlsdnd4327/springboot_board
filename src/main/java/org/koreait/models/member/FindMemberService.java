package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.MemberPwFind;
import org.koreait.commons.MemberUtil;
import org.koreait.controllers.member.FindIdForm;
import org.koreait.controllers.member.FindPwForm;
import org.koreait.controllers.member.SendMailDto;
import org.koreait.entities.MemberEntity;
import org.koreait.repositories.MemberRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindMemberService implements MemberPwFind {

    private final MemberRepository repository;
    private final MemberUtil memberUtil;
    private final JavaMailSender mailSender;
    private MemberEntity member = null;

    /**
     * 회원 아이디 찾기
     * 1. 이름 입력 여부확인 -> X -> 이름을 입력해달라는 문구 출력
     * 2. 이메일 입력 여부확인 -> X -> 이메일을 입력해달라는 문구 출력
     * 3. 이름으로 찾은 회원정보의 이메일과 입력받은 이메일이 같은지 여부 확인 ->  같지 않으면, 등록된 이메일과 다르다.
     * 4. 같은경우 -> 회원 아이디 반환.
     * @param findIdForm
     * @return
     */

    public String findId(FindIdForm findIdForm) {
        String findMemberNm = findIdForm.getMemberNm();
        String findMemberEmail = findIdForm.getEmail();

        MemberEntity findData = repository.findByMemberNmAndEmail(findMemberNm, findMemberEmail);

        if(findData == null) { return null;}

        return findData.getMemberId();
    }

    public long findPw(String memberId){

        MemberEntity memberEntity = repository.findByMemberId(memberId);

        if(memberEntity == null) { throw new MemberDataNotExistsException();}

        return memberEntity.getMemberNo();
    }

    @Override
    public SendMailDto writeMail(String id) {

        MemberEntity member = repository.findByMemberId(id);

        if(member == null) {return null;}

        String email_login = memberUtil.getMember().getEmail();
        String email_db = member.getEmail();
        SendMailDto mailDto = new SendMailDto();

        if(email_db.equals(email_login)) {
            mailDto.setAddress(email_login);
            mailDto.setTitle("새 비밀번호 등록 안내 이메일 입니다.");
            mailDto.setMessage("안녕하세요, 새 비밀번호 등록 관련 안내 이메일 입니다."
            +"회원님의 비밀번호 설정을 위해 아래 url로 들어가 새 비밀번호를 등록해주세요."
            +"http://localhost:3000/member/setNewPw");
        }
        return mailDto;
    }

    @Override
    public void sendMail(SendMailDto mailDto) {
        System.out.println("====================");
        System.out.println("메일 전송");
        System.out.println("====================");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getAddress());
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        message.setFrom("day00@test.org");
        message.setReplyTo("day00@test.org");
        System.out.println("message : " + message);
        mailSender.send(message);
    }
}
