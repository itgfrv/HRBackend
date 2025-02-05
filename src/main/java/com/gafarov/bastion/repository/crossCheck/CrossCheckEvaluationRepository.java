package com.gafarov.bastion.repository.crossCheck;

import com.gafarov.bastion.entity.crossCheck.CrossCheckEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrossCheckEvaluationRepository extends JpaRepository<CrossCheckEvaluation, Integer> {
    List<CrossCheckEvaluation> findByAttemptId(Integer attemptId);
}
