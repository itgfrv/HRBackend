package com.gafarov.bastion.repository.crossCheck;

import com.gafarov.bastion.entity.crossCheck.CrossCheckRule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrossCheckRuleRepository extends JpaRepository<CrossCheckRule, Integer> {
}
