package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.controllers.member.FindForm;
import org.koreait.entities.MemberEntity;
import org.koreait.repositories.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FindMemberService {

    private final MemberRepository repository;

    private MemberEntity member = null;

    public String findId(FindForm findForm) {
        return null;
    }
}
