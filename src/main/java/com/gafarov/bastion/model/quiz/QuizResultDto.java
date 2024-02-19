package com.gafarov.bastion.model.quiz;

import com.gafarov.bastion.entity.quiz.QuizType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDto {
    private QuizType type;
    private Integer userResult;
    private List<ResultDto> result;
    private Long duration;
}
