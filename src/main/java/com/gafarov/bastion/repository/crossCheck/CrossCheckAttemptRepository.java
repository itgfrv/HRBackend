package com.gafarov.bastion.repository.crossCheck;

import com.gafarov.bastion.entity.crossCheck.CrossCheckAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrossCheckAttemptRepository extends JpaRepository<CrossCheckAttempt, Integer> {
    List<CrossCheckAttempt> findByEvaluatorId(Integer userId);
}
