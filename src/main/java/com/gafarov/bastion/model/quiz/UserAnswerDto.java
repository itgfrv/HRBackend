package com.gafarov.bastion.model.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerDto {
    private QuestionDto question;
    private Integer userAnswerId;
    private Integer correctAnswerId;
}
