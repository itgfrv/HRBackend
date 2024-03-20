package com.gafarov.bastion.entity.casestudy;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "case_study_attempt")
public class CaseStudyAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "case_study_attempt_seq")
    @SequenceGenerator(name = "case_study_attempt_seq", sequenceName = "case_study_attempt_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttemptStatus status;
}