package com.gafarov.bastion.model.resume;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeAnswerDto {
    @JsonProperty("question_id")
    private int questionId;
    @JsonProperty("answer_body")
    private String answer;
}
