package com.gafarov.bastion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gafarov.bastion.entity.quiz.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Integer id;
    @JsonProperty("quiz_type")
    private QuizType quizType;
    @JsonProperty("questions")
    private List<QuestionDto> questionList;
}
