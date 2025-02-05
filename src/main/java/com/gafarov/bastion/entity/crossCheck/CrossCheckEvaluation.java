package com.gafarov.bastion.entity.crossCheck;

import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cross_check_evaluation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrossCheckEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cross_check_evaluation_seq")
    @SequenceGenerator(name = "cross_check_evaluation_seq", sequenceName = "cross_check_evaluation_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluated_id")
    private User evaluated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private CrossCheckQuestion question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attempt_id")
    private CrossCheckAttempt attempt;

    private Integer mark;

    @Column(length = 255)
    private String comment;
}
