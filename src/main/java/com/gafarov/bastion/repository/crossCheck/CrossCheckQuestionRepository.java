package com.gafarov.bastion.repository.crossCheck;

import com.gafarov.bastion.entity.crossCheck.CrossCheckQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CrossCheckQuestionRepository extends JpaRepository<CrossCheckQuestion, Integer> {
    List<CrossCheckQuestion> findByCrossCheckId(Integer id);
}
