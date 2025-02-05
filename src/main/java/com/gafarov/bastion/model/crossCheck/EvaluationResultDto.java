package com.gafarov.bastion.model.crossCheck;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationResultDto {
    private Integer evaluatedId;
    private Integer questionId;
    private Integer mark;
    private String comment;
}
