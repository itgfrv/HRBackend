package com.gafarov.bastion.mapper;

import com.gafarov.bastion.entity.quiz.Result;
import com.gafarov.bastion.model.quiz.ResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResultMapper {
    ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);

    ResultDto mapResultToResultDto(Result result);

}
