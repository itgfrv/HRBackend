package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.exception.ForbiddenException;
import com.gafarov.bastion.model.ResumeDto;
import com.gafarov.bastion.service.impl.ResumeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/resume")
@AllArgsConstructor
@CrossOrigin
public class ResumeController extends BaseController {
    private final ResumeServiceImpl service;

    @GetMapping
    public ResumeDto getPersonalResume(@AuthenticationPrincipal User user) {
        return service.getResume(user.getId());
    }

    @PostMapping
    public ResumeDto sendResume(
            @AuthenticationPrincipal User user,
            @RequestBody ResumeDto resumeDto
    ) {
        if (user.getActivity() != Activity.REGISTERED) {
            throw new ForbiddenException("cant save already saved resume");
        }
        return service.sendResume(resumeDto, user);
    }

    @PutMapping
    public ResumeDto updateResume(
            @AuthenticationPrincipal User user,
            @RequestBody ResumeDto resumeDto
    ) {
        if (user.getActivity() != Activity.REGISTERED) {
            throw new ForbiddenException("Cant change saved resume");
        }
        return service.updateResume(resumeDto, user);
    }
   /* @GetMapping("/questions")
    public List<ResumeQuestionDto> getResumeQuestions(){
        return null;
    }*/
}
