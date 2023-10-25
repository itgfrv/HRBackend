package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.ResumeAnswerDto;
import com.gafarov.bastion.model.ResumeDto;
import com.gafarov.bastion.service.ResumeService;
import com.gafarov.bastion.service.impl.ResumeServiceImpl;
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
        return service.updateResume(user,answers);
    }

    @PostMapping("/send")
    public ResumeDto sendResume(@AuthenticationPrincipal User user,@RequestBody List<ResumeAnswerDto> answers) {
        return service.sendResume(user, answers);
    }
}