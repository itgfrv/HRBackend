package com.gafarov.bastion.repository.course;

import com.gafarov.bastion.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Integer> {
    @Override
    Optional<Course> findById(Integer integer);
}
