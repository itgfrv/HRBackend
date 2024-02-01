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
            @AuthenticationPrincipal User user
    ) {
        if (user.getRole() != Role.ADMIN) throw new ForbiddenException("only for admin");
        Pageable pageable = PageRequest.of(page, size);
        return formService.getPaginationForm(pageable, filterParam);
    }

    @GetMapping("/{id}")
    public FullFormDto getUserForm(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        if (user.getRole() != Role.ADMIN) throw new ForbiddenException("only for admin");
        return formService.getUserInfo(id);
    }

    @GetMapping("/personal")
    public PersonalInfo getPersonalInfo(@AuthenticationPrincipal User user) {
        return new PersonalInfo(user.getId(), user.getEmail(), user.getFirstname(), user.getLastname(), user.getRole(), user.getUserStatus(), user.getActivity());
    }
    @GetMapping("/task-info")
    public TaskInfo getTaskInfo(@AuthenticationPrincipal User user) {
        Activity a = user.getActivity();
        TaskInfo t = null;
        switch (a) {
            case REGISTERED -> {
                t = new TaskInfo(true, false, false);
            }
            case RESUME -> {
                t = new TaskInfo(false, true, false);
            }
            case WAITING_INTERVIEW, WAITING_RESULT -> {
                t = new TaskInfo(false, false, false);
            }
            case INTERVIEW -> {
                t = new TaskInfo(false, false, true);
            }
        }
        System.out.println(t);
        return t;
    }

    private record TaskInfo(
            @JsonProperty("resume")
            boolean resume,
            @JsonProperty("demo")
            boolean demoTest,
            @JsonProperty("interview")
            boolean interview
    ) {
    }
}
