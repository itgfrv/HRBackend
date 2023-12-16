package com.gafarov.bastion.service.impl.course;

import com.gafarov.bastion.entity.course.Course;
import com.gafarov.bastion.entity.course.Task;
import com.gafarov.bastion.entity.course.UserTask;
import com.gafarov.bastion.entity.course.UserTaskId;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.course.UserTaskDto;
import com.gafarov.bastion.repository.course.CourseRepository;
import com.gafarov.bastion.repository.course.UserTaskRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Data
public class CourseService {

    private final CourseRepository courseRepository;
    private final UserTaskRepository userTaskRepository;

    public void getCourses() {
        List<Course> courses = courseRepository.findAll();
    }

    public void getUserCourseTasks(User user, int courseId) {
        List<UserTaskDto> userTaskDtos = new ArrayList<>();
        Course course = courseRepository.getReferenceById(courseId);
        List<Task> tasks = course.getTasks();
        for (Task task : tasks) {
            UserTaskId id = new UserTaskId(user,task);
            UserTask ut = userTaskRepository.getReferenceById(id);

        }
    }
}
