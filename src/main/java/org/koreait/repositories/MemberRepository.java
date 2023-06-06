package org.koreait.repositories;

import org.koreait.entities.MemberEntity;
import org.koreait.entities.QMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<MemberEntity,Long>,
        QuerydslPredicateExecutor<MemberEntity> {

    MemberEntity findByMemberId(String memberId); // 아이디로 멤버 조회

    // 회원 아이디 중복체크
    default boolean isExist(String memberId){
        QMemberEntity member = QMemberEntity.memberEntity;
        return exists(member.memberId.eq(memberId));
    }

}
