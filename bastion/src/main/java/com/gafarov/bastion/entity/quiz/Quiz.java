package com.gafarov.bastion.entity.quiz;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Quiz {
    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    private QuizType quizType;
    @OneToMany
    private List<Question> questionList;
}
