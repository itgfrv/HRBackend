package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.UserResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserResultRepository extends JpaRepository<UserResult, Integer> {
    Optional<UserResult> findByUserIdAndQuizId(Integer userId, Integer quizId);

    List<UserResult> findAllByUserId(Integer userId);
}
