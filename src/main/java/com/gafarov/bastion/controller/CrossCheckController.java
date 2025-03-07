package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.crossCheck.*;
import com.gafarov.bastion.service.crossCheck.CrossCheckService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cross-check")
@RequiredArgsConstructor
public class CrossCheckController extends BaseController {
    private final CrossCheckService crossCheckService;

    @PostMapping("/session/{crossCheckId}")
    public void addCrossCheckSession(@PathVariable Integer crossCheckId, @RequestBody DescriptionRequest description) {
        crossCheckService.addCrossCheckSession(crossCheckId, description.getDescription());
    }

    @GetMapping("/sessions/{crossCheckId}")
    public List<CrossCheckSessionDto> getSessions(@PathVariable Integer crossCheckId) {
        return crossCheckService.getSessions(crossCheckId);
    }

    @GetMapping("/sessions/details/{sessionId}")
    public List<EvaluatorDto> getSessionDetails(@PathVariable Integer sessionId) {
        return crossCheckService.getSessionEvaluations(sessionId);
    }

    @GetMapping("/sessions/details/{sessionId}/avg")
    public List<EvaluationDto> getSessionDetailsAvg(
            @PathVariable Integer sessionId,
            @RequestParam(required = false, defaultValue = "1") double weight) {
        return crossCheckService.getSessionEvaluationsAvg(sessionId, weight);
    }

    @GetMapping("/attempt/result/{attemptId}")
    public ResponseEntity<CrossCheckAttemptResultDto> getCompletedAttemptDetails(@PathVariable Integer attemptId) {
        return ResponseEntity.ok(crossCheckService.getCompletedAttemptDetails(attemptId));
    }

    @GetMapping("/attempts")
    public List<CrossCheckSessionDto> getUserSessions(@AuthenticationPrincipal User user) {
        return crossCheckService.getUserSessions(user);
    }

    @GetMapping("/attempts/{attemptId}")
    public ResponseEntity<CrossCheckAttemptDetailsDto> getAttemptDetails(@PathVariable Integer attemptId) {
        return ResponseEntity.ok(crossCheckService.getAttemptDetails(attemptId));
    }

    @PostMapping("/attempts/evaluate")
    public ResponseEntity<Void> saveEvaluation(@RequestParam Integer attemptId, @RequestBody List<UserScore> userScores) throws MessagingException {
        crossCheckService.saveEvaluation(attemptId, userScores);
        return ResponseEntity.ok().build();
    }
}