package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.UserResult;
import com.gafarov.bastion.entity.quiz.UserAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswer,Integer> {
    List<UserAnswer> findAllByUserResultId(Integer userResultId);
}
