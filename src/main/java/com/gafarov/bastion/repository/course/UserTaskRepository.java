package com.gafarov.bastion.repository.course;

import com.gafarov.bastion.entity.course.UserTask;
import com.gafarov.bastion.entity.course.UserTaskId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTaskRepository extends JpaRepository<UserTask, UserTaskId> {
}
