package com.gafarov.bastion.model.casestudy;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gafarov.bastion.entity.casestudy.AttemptStatus;

public record CaseStudyAttemptDto(int id, @JsonProperty("status") AttemptStatus status, Integer totalMarks,
                                  Integer maxMarks) {
}
