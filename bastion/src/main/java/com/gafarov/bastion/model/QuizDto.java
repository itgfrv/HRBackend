package com.gafarov.bastion.model;

import com.gafarov.bastion.entity.quiz.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Integer id;
    private List<QuestionDto> questionList;
    private QuizType quizType;
}
