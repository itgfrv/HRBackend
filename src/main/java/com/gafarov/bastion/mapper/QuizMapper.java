package com.gafarov.bastion.mapper;

import com.gafarov.bastion.entity.quiz.Quiz;
import com.gafarov.bastion.model.quiz.QuizDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface QuizMapper {
    QuizMapper INSTANCE = Mappers.getMapper(QuizMapper.class);

    QuizDto mapQuizToQuizDto(Quiz quiz);

}
