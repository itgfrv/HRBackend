package com.gafarov.bastion.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gafarov.bastion.model.quiz.QuizResultDto;
import com.gafarov.bastion.model.resume.ResumeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullFormDto {
    @JsonProperty("user_info")
    private FormDto user;
    @JsonProperty("resume")
    private ResumeDto resumeDto;
    @JsonProperty("quiz_result")
    private List<QuizResultDto> quizResult;
}
