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
public class Question {
    @Id
    private Integer id;
    private String imgSrc;
    private String question;
    @Enumerated(EnumType.STRING)
    private QuestionType questionType;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @OneToMany
    private List<Answer> answers;
}
