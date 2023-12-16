package com.gafarov.bastion.entity.course;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserTask {
    @EmbeddedId
    private UserTaskId userTaskId;
    private Boolean isOpened;
    private Boolean isDone;
}
