package com.gafarov.bastion.controller;

import com.gafarov.bastion.model.crossCheck.CrossCheckAttemptDetailsDto;
import com.gafarov.bastion.model.crossCheck.CrossCheckAttemptDto;
import com.gafarov.bastion.model.crossCheck.CrossCheckAttemptResultDto;
import com.gafarov.bastion.model.crossCheck.CrossCheckSessionDto;
import com.gafarov.bastion.service.crossCheck.CrossCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cross-check")
@RequiredArgsConstructor
public class CrossCheckController {
    private final CrossCheckService crossCheckService;

    @PostMapping("/session")
    public ResponseEntity<CrossCheckSessionDto> addCrossCheckSession(@RequestParam Integer crossCheckId, @RequestParam String description) {
        return null;// ResponseEntity.ok(crossCheckService.addCrossCheckSession(crossCheckId, description));
    }

    @GetMapping("/attempts/{userId}")
    public ResponseEntity<List<CrossCheckAttemptDto>> getUserAttempts(@PathVariable Integer userId) {
        return ResponseEntity.ok(crossCheckService.getUserAttempts(userId));
    }

    @GetMapping("/sessions/{crossCheckId}")
    public ResponseEntity<List<CrossCheckSessionDto>> getSessions(@PathVariable Integer crossCheckId) {
        return ResponseEntity.ok(crossCheckService.getSessions(crossCheckId));
    }

    @PostMapping("/attempt/start/{attemptId}")
    public ResponseEntity<CrossCheckAttemptDto> startAttempt(@PathVariable Integer attemptId) {
        return ResponseEntity.ok(crossCheckService.startAttempt(attemptId));
    }

    @PostMapping("/evaluation")
    public ResponseEntity<Void> saveEvaluation(@RequestParam Integer attemptId, @RequestParam Integer evaluatedId, @RequestBody Map<Integer, Integer> questionMarks, @RequestBody Map<Integer, String> comments) {
        crossCheckService.saveEvaluation(attemptId, evaluatedId, questionMarks, comments);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/attempt/details/{attemptId}")
    public ResponseEntity<CrossCheckAttemptDetailsDto> getAttemptDetails(@PathVariable Integer attemptId) {
        return ResponseEntity.ok(crossCheckService.getAttemptDetails(attemptId));
    }

    @GetMapping("/attempt/result/{attemptId}")
    public ResponseEntity<CrossCheckAttemptResultDto> getCompletedAttemptDetails(@PathVariable Integer attemptId) {
        return ResponseEntity.ok(crossCheckService.getCompletedAttemptDetails(attemptId));
    }
}