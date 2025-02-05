package com.gafarov.bastion.service.crossCheck;

import com.gafarov.bastion.entity.crossCheck.*;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.model.crossCheck.*;
import com.gafarov.bastion.repository.crossCheck.*;
import com.gafarov.bastion.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CrossCheckService {
    private final CrossCheckRepository crossCheckRepository;
    private final CrossCheckAttemptRepository attemptRepository;
    private final CrossCheckSessionRepository sessionRepository;
    private final CrossCheckEvaluationRepository evaluationRepository;
    private final CrossCheckQuestionRepository questionRepository;
    private final UserServiceImpl userService;

    public void addCrossCheckSession(Integer crossCheckId, String description) {
        var crossCheck = crossCheckRepository.findById(crossCheckId).orElseThrow();
        var crossCheckSession = new CrossCheckSession();
        crossCheckSession.setCrossCheck(crossCheck);
        crossCheckSession.setDate(LocalDateTime.now());
        crossCheckSession.setDescription(description);
        var savedSession = sessionRepository.save(crossCheckSession);
        var role = crossCheck.getRules().stream().map(CrossCheckRule::getEvaluatorRole).distinct().toList().get(0);
        var users = userService.findUsersByRole(Role.valueOf(role));
        for (var user : users) {
            var crossCheckAttempt = new CrossCheckAttempt();
            crossCheckAttempt.setEvaluator(user);
            crossCheckAttempt.setSession(savedSession);
            crossCheckAttempt.setStatus(CrossCheckAttemptStatus.STARTED);
            attemptRepository.save(crossCheckAttempt);
        }
    }

    public List<CrossCheckAttemptDto> getUserAttempts(Integer userId) {
        var attempts = attemptRepository.findByEvaluatorId(userId);
        return attempts.stream()
                .map(attempt -> new CrossCheckAttemptDto(attempt.getId(), attempt.getEvaluator().getId(), attempt.getStatus().toString()))
                .collect(Collectors.toList());
    }

    public List<CrossCheckSessionDto> getSessions(Integer crossCheckId) {
        var sessions = sessionRepository.findByCrossCheckId(crossCheckId);
        return sessions.stream()
                .map(session -> new CrossCheckSessionDto(
                        session.getId(),
                        session.getDate(),
                        session.getDescription(),
                        new ArrayList<>()
                ))
                .collect(Collectors.toList());
    }

    public CrossCheckAttemptDto startAttempt(Integer attemptId) {
        var attempt = attemptRepository.findById(attemptId).orElseThrow();
        attempt.setStatus(CrossCheckAttemptStatus.STARTED);
        var updatedAttempt = attemptRepository.save(attempt);
        return new CrossCheckAttemptDto(updatedAttempt.getId(), updatedAttempt.getEvaluator().getId(), updatedAttempt.getStatus().toString());
    }

    public void saveEvaluation(Integer attemptId, Integer evaluatedId, Map<Integer, Integer> questionMarks, Map<Integer, String> comments) {
        var attempt = attemptRepository.findById(attemptId).orElseThrow();
        for (var entry : questionMarks.entrySet()) {
            var evaluation = new CrossCheckEvaluation();
            evaluation.setAttempt(attempt);
            evaluation.setEvaluated(userService.findUserById(evaluatedId));
            evaluation.setQuestion(questionRepository.findById(entry.getKey()).orElseThrow());
            evaluation.setMark(entry.getValue());
            evaluation.setComment(comments.get(entry.getKey()));
            evaluationRepository.save(evaluation);
        }
    }

    public CrossCheckAttemptDetailsDto getAttemptDetails(Integer attemptId) {
        var attempt = attemptRepository.findById(attemptId).orElseThrow();
        var users = userService.findUsersByRole(Role.EMPLOYEE);
        var questions = questionRepository.findByCrossCheckId(attempt.getSession().getCrossCheck().getId());
        return null;//new CrossCheckAttemptDetailsDto(attempt.getId(), users, questions);
    }

    public CrossCheckAttemptResultDto getCompletedAttemptDetails(Integer attemptId) {
        var attempt = attemptRepository.findById(attemptId).orElseThrow();
        if (attempt.getStatus() != CrossCheckAttemptStatus.COMPLETED) {
            throw new IllegalStateException("Attempt is not completed yet");
        }
        var evaluations = evaluationRepository.findByAttemptId(attemptId);
        List<EvaluationResultDto> results = evaluations.stream()
                .map(evaluation -> new EvaluationResultDto(
                        evaluation.getEvaluated().getId(),
                        evaluation.getQuestion().getId(),
                        evaluation.getMark(),
                        evaluation.getComment()
                ))
                .collect(Collectors.toList());

        return new CrossCheckAttemptResultDto(attempt.getId(), attempt.getEvaluator().getId(), results);
    }
}
