package com.gafarov.bastion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gafarov.bastion.entity.quiz.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    @JsonProperty("question_type")
    private QuestionType questionType;
    @JsonProperty("current_result")
    private Integer result;
    @JsonProperty("max_result")
    private Integer maxResult;
}
