package com.gafarov.bastion.mapper;

import com.gafarov.bastion.entity.Resume;
import com.gafarov.bastion.model.ResumeDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeMapper INSTANCE = Mappers.getMapper(ResumeMapper.class);

    ResumeDto mapResumeToResumeDto(Resume resume);

    Resume mapResumeDtoToResume(ResumeDto dto);

}
