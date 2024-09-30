package com.gafarov.bastion.entity.casestudy;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "file")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    @SequenceGenerator(name = "file_seq", sequenceName = "file_id_seq", allocationSize = 1)
    private Integer id;

    @Column(name = "file_name", nullable = false)
    private String fileName;

    @ManyToOne
    @JoinColumn(name = "case_study_attempt_id", nullable = false)
    @JsonIgnore
    private CaseStudyAttempt caseStudyAttempt;

    @Column(name = "full_file_path", nullable = false)
    private String fullPath;
}