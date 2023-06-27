package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.MemberEntity;
import org.koreait.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void updatePw(long memberNo, String memberPw){
        MemberEntity member = repository.findById(memberNo).orElseThrow(MemberDataNotExistsException::new);

        member.setMemberPw(passwordEncoder.encode(memberPw));

        repository.saveAndFlush(member);
    }
}
