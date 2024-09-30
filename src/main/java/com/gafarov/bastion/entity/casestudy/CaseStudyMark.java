package com.gafarov.bastion.entity.casestudy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "case_study_mark")
public class CaseStudyMark {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "case_study_mark_seq")
    @SequenceGenerator(name = "case_study_mark_seq", sequenceName = "case_study_mark_id_seq", allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "criteria_id", nullable = false)
    private Criteria criteria;

    @ManyToOne
    @JoinColumn(name = "case_study_attempt_id", nullable = false)
    @JsonIgnore
    private CaseStudyAttempt caseStudyAttempt;

    @Column(name = "mark", nullable = false)
    private int mark;
    @Column(name = "comment", nullable = false)
    private String comment;
}