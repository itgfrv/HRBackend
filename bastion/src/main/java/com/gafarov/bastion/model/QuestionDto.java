package com.gafarov.bastion.model;

import com.gafarov.bastion.entity.quiz.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

    private Integer id;
    private String imgSrc;
    private String question;

    private QuestionType questionType;
    private List<AnswerDto> answers;
}
