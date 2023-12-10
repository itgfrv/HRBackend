package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.ResumeAnswerDto;
import com.gafarov.bastion.model.ResumeDto;
import com.gafarov.bastion.service.impl.ResumeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resume")
@AllArgsConstructor
@CrossOrigin
public class ResumeController extends BaseController {
    private final ResumeServiceImpl service;

    @GetMapping("/questions")
    public ResumeDto getResumeQuestions(@AuthenticationPrincipal User user) {
        return service.getResume(user);
    }

    @PutMapping("/update")
    public ResumeDto updateResume(@AuthenticationPrincipal User user,
                                  @RequestBody List<ResumeAnswerDto> answers
    ) {
        return service.updateResume(user, answers);
    }

    @PostMapping("/send")

    public ResumeDto sendResume(@AuthenticationPrincipal User user, @RequestBody List<ResumeAnswerDto> answers) {
        return service.sendResume(user, answers);
    }

    @GetMapping
    @Operation(
            summary = "Deprecated",
            description = "Старая версия получения анкеты, будет удалена"
    )
    public OldResumeDto getResume(@AuthenticationPrincipal User user) {

        return service.getOld(user);
    }

    @PostMapping
    @Operation(
            summary = "Deprecated",
            description = "Старая версия отправки анкеты, будет удалена"
    )
    public OldResumeDto postResume(@AuthenticationPrincipal User user, @RequestBody OldResumeDto resumeDto) {
        return service.postOld(user, resumeDto);
    }

    @PutMapping
    @Operation(
            summary = "Deprecated",
            description = "Старая версия обновления анкеты, будет удалена"
    )
    public OldResumeDto putResume(@AuthenticationPrincipal User user, @RequestBody OldResumeDto resumeDto) {
        return service.putOld(user, resumeDto);
    }

}