package com.gafarov.bastion.model.newcasestudy;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CaseStudyMarkDTO {
    @JsonProperty("criteria_id")
    private int criteriaId;
    private int mark;
    private String comment;
}