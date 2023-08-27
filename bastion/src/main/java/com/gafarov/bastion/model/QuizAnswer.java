package com.gafarov.bastion.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record QuizAnswer(
        @JsonProperty("question_id")
        Integer questionId,
        @JsonProperty("answer_id")
        Integer answerId
) {
}
