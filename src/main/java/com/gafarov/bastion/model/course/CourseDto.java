package com.gafarov.bastion.model.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private int id;
    private String name;
    private String description;
    @JsonProperty("is_opened")
    private Boolean isOpened;
}
