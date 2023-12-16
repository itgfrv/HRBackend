package com.gafarov.bastion.entity.course;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private Integer id;
    @Column(name = "task_type")
    private TaskType taskType;
    private String description;
    private Integer taskId;
    @Column(name = "is_open_on_start")
    private Boolean isOpenedOnStart;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
