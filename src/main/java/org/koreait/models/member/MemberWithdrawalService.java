package org.koreait.models.member;

import lombok.RequiredArgsConstructor;
import org.koreait.entities.MemberEntity;
import org.koreait.repositories.MemberRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberWithdrawalService {

    private final MemberRepository repository;

    public void withdrawal(String memberId){
        MemberEntity memberEntity = repository.findByMemberId(memberId);

        if(memberEntity == null) return;

        repository.delete(memberEntity);
        repository.flush();
    }


}