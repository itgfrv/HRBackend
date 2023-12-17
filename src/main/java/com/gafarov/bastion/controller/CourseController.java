package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.course.TaskType;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.course.TaskDto;
import com.gafarov.bastion.model.course.UserTaskDto;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/course")
@CrossOrigin
public class CourseController extends BaseController {
    @PostMapping("/add/{id}")
    @Operation(
            summary = "Добавить пользователю курс",
            description = "Должен быть jwt токен пользователя,в урле указывается id курса"
    )
    public void addCourse(@AuthenticationPrincipal User user, @RequestParam Integer id) {

    }

    @GetMapping("/{id}/tasks")
    @Operation(
            summary = "Получить таски пользователя по курсу",
            description = "Должен быть jwt токен пользователя,в урле указывается id курса"
    )
    public List<UserTaskDto> getTask(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        List<UserTaskDto> list = new ArrayList<>();
        TaskDto one = new TaskDto(1, TaskType.RESUME, "Анкета", 1);
        list.add(new UserTaskDto(one, true));
        TaskDto two = new TaskDto(2, TaskType.QUIZ, "Демо-тест инженер проектировщик", 1);
        TaskDto three = new TaskDto(3, TaskType.QUIZ, "Интервью-тест инженер проектировщик", 2);
        TaskDto four = new TaskDto(4, TaskType.HW, "Домашнее задание", 1);
        list.add(new UserTaskDto(two, true));
        list.add(new UserTaskDto(three, true));
        list.add(new UserTaskDto(four, true));
        return list;
    }
}
