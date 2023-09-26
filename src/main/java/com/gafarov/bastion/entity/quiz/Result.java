package com.gafarov.bastion.entity.quiz;

import com.gafarov.bastion.entity.UserResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
    @SequenceGenerator(name = "result_seq", sequenceName = "result_id_seq", allocationSize = 1)
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;
    @Column(name = "max_result")
    private Integer maxResult;

    private Integer result;
    @ManyToOne
    @JoinColumn(name = "user_result_id")
    private UserResult userResult;
}
