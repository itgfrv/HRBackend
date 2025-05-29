package com.gafarov.bastion.model.newcasestudy;

import com.gafarov.bastion.entity.casestudy.AttemptStatus;
import lombok.Data;

import java.util.List;

@Data
public class CaseStudyAttemptDTO {
    private Integer id;
    private Integer userId;
    private AttemptStatus status;
    private String link1;
    private String link2;
    private List<FileDTO> files;
    private List<CaseStudyMarkDTO> marks;
}