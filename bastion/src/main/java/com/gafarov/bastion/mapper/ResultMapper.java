package com.gafarov.bastion.mapper;

import com.gafarov.bastion.entity.Result;
import com.gafarov.bastion.model.ResultDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ResultMapper {
    ResultMapper INSTANCE = Mappers.getMapper(ResultMapper.class);

    ResultDto mapResultToResultDto(Result result);

}
