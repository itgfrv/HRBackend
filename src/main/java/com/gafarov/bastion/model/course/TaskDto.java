package com.gafarov.bastion.model.course;

import com.fasterxml.jackson.annotation.JsonProperty;
//import com.gafarov.bastion.entity.course.TaskType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private int id;
    //@JsonProperty("task_type")
    //private TaskType taskType;
    private String description;
    @JsonProperty("task_id")
    private int taskId;
}
