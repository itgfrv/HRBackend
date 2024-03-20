package com.gafarov.bastion.repository.casestudy;

import com.gafarov.bastion.entity.casestudy.CaseStudyAttempt;
import com.gafarov.bastion.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaseStudyAttemptRepository extends JpaRepository<CaseStudyAttempt, Integer> {

    List<CaseStudyAttempt> findAllByUserId(Integer userId);
    Optional<CaseStudyAttempt> findByIdAndUserId(Integer id,Integer userId);

}
