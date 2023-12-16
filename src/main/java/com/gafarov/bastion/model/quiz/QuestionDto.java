package com.gafarov.bastion.model.quiz;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gafarov.bastion.entity.quiz.QuestionType;
import com.gafarov.bastion.model.quiz.AnswerDto;
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
