package com.gafarov.bastion.model.resume;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ResumeDto(
        @JsonProperty("questions")
        List<ResumeQuestionDto> resumeQuestionDtoList,
        @JsonProperty("answers")
        List<ResumeAnswerDto> resumeAnswerDtoList
) {
}
