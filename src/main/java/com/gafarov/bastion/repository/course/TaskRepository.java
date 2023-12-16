package com.gafarov.bastion.repository.course;

import com.gafarov.bastion.entity.course.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
