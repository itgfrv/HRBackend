package com.gafarov.bastion.entity.crossCheck;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cross_check_question")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrossCheckQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cross_check_question_seq")
    @SequenceGenerator(name = "cross_check_question_seq", sequenceName = "cross_check_question_id_seq", allocationSize = 1)
    private Integer id;

    @Column(length = 255)
    private String question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_check_id")
    private CrossCheck crossCheck;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CrossCheckEvaluation> evaluations;
}
