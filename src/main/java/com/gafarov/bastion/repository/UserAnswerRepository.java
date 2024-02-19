package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.quiz.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAnswerRepository extends JpaRepository<UserAnswer,Integer> {
}
