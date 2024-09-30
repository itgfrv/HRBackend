package com.gafarov.bastion.model.newcasestudy;

import com.gafarov.bastion.entity.casestudy.CaseStudyAttempt;
import com.gafarov.bastion.entity.casestudy.CaseStudyMark;
import com.gafarov.bastion.entity.casestudy.File;

import java.util.List;
import java.util.stream.Collectors;

public class CaseStudyAttemptMapper {

    // Преобразование CaseStudyAttempt в DTO
    public static CaseStudyAttemptDTO toDto(CaseStudyAttempt attempt) {
        CaseStudyAttemptDTO dto = new CaseStudyAttemptDTO();
        dto.setId(attempt.getId());
        dto.setUserId(attempt.getUserId());
        dto.setStatus(attempt.getStatus());

        // Преобразуем список файлов в DTO
        List<FileDTO> fileDTOs = attempt.getFiles().stream()
                .map(CaseStudyAttemptMapper::toFileDto)
                .collect(Collectors.toList());
        dto.setFiles(fileDTOs);

        // Преобразуем список оценок в DTO
        List<CaseStudyMarkDTO> markDTOs = attempt.getMarks().stream()
                .map(CaseStudyAttemptMapper::toMarkDto)
                .collect(Collectors.toList());
        dto.setMarks(markDTOs);

        return dto;
    }

    // Преобразование File в FileDTO
    public static FileDTO toFileDto(File file) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileName(file.getFileName());
        fileDTO.setFullPath(file.getFullPath());
        return fileDTO;
    }

    // Преобразование CaseStudyMark в CaseStudyMarkDTO
    public static CaseStudyMarkDTO toMarkDto(CaseStudyMark mark) {
        CaseStudyMarkDTO markDTO = new CaseStudyMarkDTO();
        markDTO.setMark(mark.getMark());
        markDTO.setComment(mark.getComment());
        return markDTO;
    }

    // Преобразование CaseStudyAttemptDTO в CaseStudyAttempt
    public static CaseStudyAttempt toEntity(CaseStudyAttemptDTO dto) {
        CaseStudyAttempt attempt = new CaseStudyAttempt();
        attempt.setId(dto.getId());
        attempt.setUserId(dto.getUserId());
        attempt.setStatus(dto.getStatus());

        // Преобразуем DTO файлов в сущности
        List<File> files = dto.getFiles().stream()
                .map(CaseStudyAttemptMapper::toFileEntity)
                .collect(Collectors.toList());
        attempt.setFiles(files);

        // Преобразуем DTO оценок в сущности
        List<CaseStudyMark> marks = dto.getMarks().stream()
                .map(CaseStudyAttemptMapper::toMarkEntity)
                .collect(Collectors.toList());
        attempt.setMarks(marks);

        return attempt;
    }

    // Преобразование FileDTO в File
    public static File toFileEntity(FileDTO fileDTO) {
        File file = new File();
        file.setFileName(fileDTO.getFileName());
        file.setFullPath(fileDTO.getFullPath());
        return file;
    }

    // Преобразование CaseStudyMarkDTO в CaseStudyMark
    public static CaseStudyMark toMarkEntity(CaseStudyMarkDTO markDTO) {
        CaseStudyMark mark = new CaseStudyMark();
        mark.setMark(markDTO.getMark());
        return mark;
    }
}