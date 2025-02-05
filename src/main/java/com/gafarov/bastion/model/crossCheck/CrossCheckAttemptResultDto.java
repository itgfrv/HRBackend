package com.gafarov.bastion.model.crossCheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrossCheckAttemptResultDto {
    private Integer attemptId;
    private Integer evaluatorId;
    private List<EvaluationResultDto> results;
}