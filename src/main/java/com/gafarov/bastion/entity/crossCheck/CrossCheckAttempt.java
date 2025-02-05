package com.gafarov.bastion.entity.crossCheck;

import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "cross_check_attempt")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrossCheckAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cross_check_attempt_seq")
    @SequenceGenerator(name = "cross_check_attempt_seq", sequenceName = "cross_check_attempt_id_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cross_check_session_id")
    private CrossCheckSession session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_id")
    private User evaluator;
    @Enumerated(EnumType.STRING)
    private CrossCheckAttemptStatus status;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CrossCheckEvaluation> evaluations;
}
