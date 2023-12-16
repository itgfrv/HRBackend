package com.gafarov.bastion.repository.course;

import com.gafarov.bastion.entity.course.UserCourse;
import com.gafarov.bastion.entity.course.UserCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseRepository extends JpaRepository<UserCourse, UserCourseId> {
}
