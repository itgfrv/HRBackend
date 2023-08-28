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
@Table(name = "quiz")
public class Quiz {
    @Id
    private Integer id;
    @Enumerated(EnumType.STRING)
    @Column(name = "quiz_type")
    private QuizType quizType;
    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<Question> questionList;
}
