package com.gafarov.bastion.repository;

import com.gafarov.bastion.entity.Resume;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResumeRepository extends JpaRepository<Resume, Integer> {

    Optional<Resume> findByUserId(Integer id);

}
