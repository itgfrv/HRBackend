package com.gafarov.bastion.model.casestudy;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CaseStudyMarkDto(
        @JsonProperty("criteria_id")
        int criteriaId,
        int mark,
        String comment
) {
}
