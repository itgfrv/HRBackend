package com.gafarov.bastion.repository.crossCheck;

import com.gafarov.bastion.entity.crossCheck.CrossCheckEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CrossCheckEvaluationRepository extends JpaRepository<CrossCheckEvaluation, Integer> {
    List<CrossCheckEvaluation> findByAttemptId(Integer attemptId);

    @Query("""
        SELECT e FROM CrossCheckEvaluation e
        JOIN e.attempt a
        WHERE a.session.id = :sessionId
    """)
    List<CrossCheckEvaluation> findBySessionId(@Param("sessionId") Integer sessionId);
}
