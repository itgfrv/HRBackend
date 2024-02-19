package com.gafarov.bastion.entity.quiz;

import com.gafarov.bastion.entity.UserResult;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_answer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_answer_seq")
    @SequenceGenerator(name = "user_answer_seq", sequenceName = "user_answer_id_seq", allocationSize = 1)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_result_id")
    private UserResult userResult;
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

}
