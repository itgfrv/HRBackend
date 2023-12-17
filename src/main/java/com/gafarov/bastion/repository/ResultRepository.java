package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.quiz.Result;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResultRepository extends JpaRepository<Result, Integer> {
    List<Result> findAllByUserResultId(Integer id);
}
