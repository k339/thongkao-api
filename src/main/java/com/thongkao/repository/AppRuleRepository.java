package com.thongkao.repository;

import com.thongkao.entity.AppRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppRuleRepository extends JpaRepository<AppRule, Long> {
}
