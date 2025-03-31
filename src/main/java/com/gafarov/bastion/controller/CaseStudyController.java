package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.exception.ForbiddenException;
import com.gafarov.bastion.model.casestudy.CaseStudyAttemptDto;
import com.gafarov.bastion.model.casestudy.CaseStudyMarkDto;
import com.gafarov.bastion.model.casestudy.CriteriaDto;
import com.gafarov.bastion.model.casestudy.EvaluationDTO;
import com.gafarov.bastion.model.newcasestudy.CaseStudyAttemptDTO;
import com.gafarov.bastion.model.newcasestudy.CaseStudyAttemptMapper;
import com.gafarov.bastion.service.impl.CaseStudyServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/case-study")
@RequiredArgsConstructor
public class CaseStudyController {

    private final CaseStudyServiceImpl service;

    @PostMapping(value = "/load/{attemptId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Отправка файлов тестового задания"
    )
    public void loadFiles(
            @AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile[] files,
            @PathVariable Integer attemptId
    ) throws MessagingException {
        if (user.getActivity() != Activity.CASE_STUDY) {
            throw new ForbiddenException("forbidden");
        }
        service.uploadFiles(files, user, attemptId);
    }

    @GetMapping(value = "/attempts")
    @Operation(
            summary = "Получить список своих попыток"
    )
    public List<CaseStudyAttemptDto> getAttempts(@AuthenticationPrincipal User user) {
        var list = service.getUserAttempts(user.getId());
        list.sort(Comparator.comparingInt(CaseStudyAttemptDto::id));
        return list;
    }

    @GetMapping(value = "/attempts/{userId}")
    @Operation(
            summary = "Получить список попыток конкретного пользователя"
    )
    public List<CaseStudyAttemptDto> getUserAttempts(@AuthenticationPrincipal User user, @PathVariable Integer userId) {
        if (user.getRole() != Role.ADMIN) {
            throw new ForbiddenException("forbidden");
        }
        var list = service.getUserAttempts(userId);
        list.sort(Comparator.comparingInt(CaseStudyAttemptDto::id));
        return list;
    }

    @PostMapping(value = "/attempts/add/{userId}")
    @Operation(
            summary = "Добавить пользователю попытку"
    )
    public int addAttemptsToUser(@AuthenticationPrincipal User user, @PathVariable Integer userId) {
        if (user.getRole() != Role.ADMIN) {
            throw new ForbiddenException("forbidden");
        }
       return service.addAttemptsToUser(userId);
    }

    @PostMapping(value = "/attempts")
    @Operation(
            summary = "WIP сохранить результаты проверки работы "
    )
    public void saveAttemptResult(List<CaseStudyMarkDto> marks) {

    }

    @GetMapping("/criteria")
    @Operation(
            summary = "Получить список критериев для проверки"
    )
    public List<CriteriaDto> getCriteria() {
        return service.getCriteria();
    }

    @GetMapping("/attempt/{id}")
    public CaseStudyAttemptDTO getCaseStudyAttemptDTO(@AuthenticationPrincipal User user, @PathVariable Integer id) {
        return CaseStudyAttemptMapper.toDto(service.getCaseStudyAttempt(id, user));
    }

    @PostMapping("/attempt/{attemptId}")
    public void evaluateCaseStudy(@PathVariable Integer attemptId, @RequestBody List<EvaluationDTO> evaluations) {
        service.saveEvaluations(attemptId, evaluations);
    }
}