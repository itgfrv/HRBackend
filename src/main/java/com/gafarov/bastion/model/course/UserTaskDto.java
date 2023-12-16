package com.gafarov.bastion.model.course;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTaskDto {
    private TaskDto task;
    @JsonProperty("is_opened")
    private boolean isOpened;
}
