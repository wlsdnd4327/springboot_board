package org.koreait.repositories;

import org.koreait.entities.SiteConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SiteConfigRepository extends JpaRepository<SiteConfigEntity,String>,
        QuerydslPredicateExecutor<SiteConfigEntity> {
}
