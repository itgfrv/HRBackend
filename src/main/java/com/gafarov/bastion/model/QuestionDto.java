package com.gafarov.bastion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gafarov.bastion.entity.quiz.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private Integer id;
    @JsonProperty("img_src")
    private String imgSrc;
    private String question;
    @JsonProperty("question_type")

    private QuestionType questionType;
    private List<AnswerDto> answers;
}
