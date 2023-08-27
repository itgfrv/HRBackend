package com.gafarov.bastion.model;

import com.gafarov.bastion.entity.quiz.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDto {
    private QuestionType questionType;
    private Integer curScore;
    private Integer maxScore;
}
