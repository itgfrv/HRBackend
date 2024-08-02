package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.quiz.QuizAnswer;
import com.gafarov.bastion.model.quiz.QuizDto;
import com.gafarov.bastion.model.quiz.ResultDto;
import com.gafarov.bastion.model.quiz.UserAnswerDto;
import com.gafarov.bastion.service.impl.QuizServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor

public class QuizController extends BaseController {
    private final QuizServiceImpl quizService;

    @GetMapping("/{id}")
    public QuizDto getQuiz(
            @PathVariable Integer id,
            @AuthenticationPrincipal User user
    ) {
        return quizService.getQuiz(id, user);
    }

    @PostMapping("/{id}")
    public List<ResultDto> sendResult(
            @PathVariable Integer id,
            @RequestBody List<QuizAnswer> answers,
            @AuthenticationPrincipal User user
    ) {
        return quizService.checkResult(answers, user, id);
    }
    @GetMapping("/result/{userResultId}")
    public List<UserAnswerDto> getUserResult(@PathVariable Integer userResultId) {
        return quizService.getUserResultByUserResultId(userResultId);
    }
}
