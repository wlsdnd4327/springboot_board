package org.koreait.repositories;

import org.koreait.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository extends JpaRepository<MemberEntity,Long>,
        QuerydslPredicateExecutor<MemberEntity> {

}
