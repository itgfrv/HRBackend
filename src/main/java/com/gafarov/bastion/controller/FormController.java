package com.gafarov.bastion.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.exception.ForbiddenException;
import com.gafarov.bastion.model.FormDto;
import com.gafarov.bastion.model.FullFormDto;
import com.gafarov.bastion.model.PersonalInfo;
import com.gafarov.bastion.service.impl.FormServiceImpl;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/form")
@AllArgsConstructor

public class FormController extends BaseController {
    private final FormServiceImpl formService;

    @GetMapping
    public List<FormDto> getUsersForm(
            @RequestParam(required = false, defaultValue = "0") @Min(0) int page,
            @RequestParam(required = false, defaultValue = "200") @Min(1) int size,
            @RequestParam(required = false, name = "filter_param") Activity filterParam,
            @RequestParam(name = "role") Role role,
            @AuthenticationPrincipal User user
    ) {
        if (user.getRole() != Role.ADMIN) throw new ForbiddenException("only for admin");
        Pageable pageable = PageRequest.of(page, size);
        return formService.getPaginationForm(pageable, filterParam, role);
    }

    @GetMapping("/{id}")
    public FullFormDto getUserForm(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        if (user.getRole() != Role.ADMIN) throw new ForbiddenException("only for admin");
        return formService.getUserInfo(id);
    }

    @GetMapping("/personal")
    public PersonalInfo getPersonalInfo(@AuthenticationPrincipal User user) {
        return new PersonalInfo(user.getId(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getRole(), user.getUserStatus(), user.getActivity(), user.getCreatedDate(), user.getLastActivityDate());
    }

    @GetMapping("/task-info")
    public TaskInfo getTaskInfo(@AuthenticationPrincipal User user) {
        Activity a = user.getActivity();
        TaskInfo t = null;
        switch (a) {
            case REGISTERED -> {
                t = new TaskInfo(true, false, false, false, false, false, false);
            }
            case RESUME -> {
                t = new TaskInfo(false, true, true, false, false, false, false);
            }
            case WAITING_INTERVIEW -> {
                t = new TaskInfo(false, true, false, true, false, false, false);
            }
            case WAITING_RESULT -> {
                t = new TaskInfo(false, true, false, true, false, true, false);
            }
            case INTERVIEW -> {
                t = new TaskInfo(false, true, false, true, true, false, false);
            }
            case CASE_STUDY -> {
                t = new TaskInfo(false, true, false, true, false, true, true);
            }
        }
        return t;
    }

    private record TaskInfo(
            @JsonProperty("resume")
            boolean resume,
            @JsonProperty("is_resume_done")
            boolean resumeDone,
            @JsonProperty("demo")
            boolean demoTest,
            @JsonProperty("is_demo_done")
            boolean demoDone,
            @JsonProperty("interview")
            boolean interview,
            @JsonProperty("is_interview_done")
            boolean interviewDone,
            boolean caseStudy
    ) {
    }
}
