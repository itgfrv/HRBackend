package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.resume.ResumeAnswer;
import com.gafarov.bastion.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeAnswerRepository extends JpaRepository<ResumeAnswer, Integer> {
    List<ResumeAnswer> findAllByUserId(Integer userId);

    Optional<ResumeAnswer> findByUserIdAndQuestionId(Integer userId,Integer questionId);
}
