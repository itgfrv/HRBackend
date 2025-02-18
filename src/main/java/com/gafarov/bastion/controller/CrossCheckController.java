package com.gafarov.bastion.controller;

import com.gafarov.bastion.entity.crossCheck.CrossCheckEvaluation;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.crossCheck.*;
import com.gafarov.bastion.service.crossCheck.CrossCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cross-check")
@RequiredArgsConstructor
public class CrossCheckController {
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
    public List<EvaluationDto> getSessionDetailsAvg(@PathVariable Integer sessionId) {
        return crossCheckService.getSessionEvaluationsAvg(sessionId);
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
    public ResponseEntity<Void> saveEvaluation(@RequestParam Integer attemptId, @RequestBody List<UserScore> userScores) {
        crossCheckService.saveEvaluation(attemptId, userScores);
        return ResponseEntity.ok().build();
    }
}