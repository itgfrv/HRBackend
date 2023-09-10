package com.gafarov.bastion.entity;

import com.gafarov.bastion.entity.quiz.Quiz;
import com.gafarov.bastion.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "user_result")
public class UserResult {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_result_seq")
    @SequenceGenerator(name = "user_result_seq", sequenceName = "user_result_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @Column(name = "start_time")
    private OffsetDateTime startTime;
    @Column(name = "end_time")
    private OffsetDateTime endTime;
    @OneToMany
    private List<Result> results;
}
