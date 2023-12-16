package com.gafarov.bastion.repository.course;

import com.gafarov.bastion.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer> {
}
