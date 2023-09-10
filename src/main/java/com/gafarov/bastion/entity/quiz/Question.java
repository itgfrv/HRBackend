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
@Table(name = "question")
public class Question {
    @Id
    private Integer id;
    @Column(name = "img_src")
    private String imgSrc;
    @Column(length = 1000)
    private String question;
    @Enumerated(EnumType.STRING)
    @Column(name = "question_type")
    private QuestionType questionType;
    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;
    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private List<Answer> answers;
}
