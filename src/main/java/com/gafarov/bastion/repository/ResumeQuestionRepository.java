package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.resume.ResumeQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeQuestionRepository extends JpaRepository<ResumeQuestion,Integer> {
}
