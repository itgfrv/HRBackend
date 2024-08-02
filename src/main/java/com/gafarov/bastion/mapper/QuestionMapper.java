package com.gafarov.bastion.mapper;

import com.gafarov.bastion.entity.quiz.Question;
import com.gafarov.bastion.model.quiz.QuestionDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuestionMapper {
    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    QuestionDto mapQuestionToQuestionDto(Question question);
}
