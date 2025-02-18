package com.gafarov.bastion.service.crossCheck;

import com.gafarov.bastion.entity.crossCheck.*;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.crossCheck.*;
import com.gafarov.bastion.repository.crossCheck.*;
import com.gafarov.bastion.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
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
        var roles = crossCheck.getRules().stream().map(CrossCheckRule::getEvaluatorRole).distinct().toList();
        for (var role : roles) {
            var users = userService.findUsersByRole(Role.valueOf(role));
            for (var user : users) {
                var crossCheckAttempt = new CrossCheckAttempt();
                crossCheckAttempt.setEvaluator(user);
                crossCheckAttempt.setSession(savedSession);
                crossCheckAttempt.setStatus(CrossCheckAttemptStatus.STARTED);
                attemptRepository.save(crossCheckAttempt);
            }
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
                        session.getAttempts().stream()
                                             .map(a->a.getStatus().toString())
                                             .allMatch("COMPLETED"::equals)?"COMPLETED":"STARTED",
                        new ArrayList<>()
                        ))
                .sorted(Comparator.comparing(CrossCheckSessionDto::getId).reversed())
                .collect(Collectors.toList());
    }


    public List<CrossCheckSessionDto> getUserSessions(User user) {
        var attempts = attemptRepository.findByEvaluatorId(user.getId()).stream().sorted(Comparator.comparing(CrossCheckAttempt::getId).reversed());

        return attempts
                .map(a -> new CrossCheckSessionDto(
                        a.getId(),
                        a.getSession().getDate(),
                        a.getSession().getDescription(),
                        a.getStatus().toString(),
                        new ArrayList<>()
                ))
                .collect(Collectors.toList());
    }

    public void saveEvaluation(Integer attemptId, List<UserScore> userScores) {
        var attempt = attemptRepository.findById(attemptId).orElseThrow();

        for (UserScore userScore : userScores) {
            var evaluatedUser = userService.findUserById(userScore.getUserId());

            for (ScoreEntry entry : userScore.getScores()) {
                var evaluation = new CrossCheckEvaluation();
                evaluation.setAttempt(attempt);
                evaluation.setEvaluated(evaluatedUser);
                evaluation.setQuestion(questionRepository.findById(entry.getQuestionId()).orElseThrow());
                evaluation.setMark(entry.getScore());
                evaluation.setComment(entry.getComment());
                evaluationRepository.save(evaluation);
            }
        }
        attempt.setStatus(CrossCheckAttemptStatus.COMPLETED);
        attemptRepository.save(attempt);
    }

    public CrossCheckAttemptDetailsDto getAttemptDetails(Integer attemptId) {
        var attempt = attemptRepository.findById(attemptId).orElseThrow();
        var users = userService.findUsersByRole(Role.EMPLOYEE).stream()
                .filter(u -> !Objects.equals(attempt.getEvaluator().getId(), u.getId()))
                .map(user -> new UserDto(user.getId(), user.getFirstname(), user.getLastname(), user.getRole().toString()))
                .toList();
        var questions = questionRepository.findByCrossCheckId(attempt.getSession().getCrossCheck().getId()).stream().map(question -> new QuestionDto(question.getId(), question.getQuestion())).toList();
        return new CrossCheckAttemptDetailsDto(attempt.getId(), users, questions, attempt.getStatus().toString());
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
    public List<EvaluatorDto> getSessionEvaluations(Integer sessionId) {
        var session = sessionRepository.findById(sessionId).orElseThrow();
        var attempts = session.getAttempts();

        return attempts.stream().map(attempt -> {
            var evaluatorDto = new UserDto(
                    attempt.getEvaluator().getId(),
                    attempt.getEvaluator().getFirstname(),
                    attempt.getEvaluator().getLastname(),
                    attempt.getEvaluator().getRole().toString()
            );

            var evaluations = evaluationRepository.findByAttemptId(attempt.getId());
            var evaluationsDto = evaluations.stream()
                    .collect(Collectors.groupingBy(CrossCheckEvaluation::getEvaluated))
                    .entrySet().stream()
                    .map(entry -> {
                        var evaluatedUser = entry.getKey();
                        var evaluatedDto = new UserDto(
                                evaluatedUser.getId(),
                                evaluatedUser.getFirstname(),
                                evaluatedUser.getLastname(),
                                evaluatedUser.getRole().toString()
                        );

                        var marks = entry.getValue().stream().map(evaluation -> new MarkDto(
                                new QuestionDto(evaluation.getQuestion().getId(), evaluation.getQuestion().getQuestion()),
                                evaluation.getMark()
                        )).toList();

                        return new EvaluationDto(evaluatedDto, marks);
                    }).toList();

            return new EvaluatorDto(evaluatorDto, evaluationsDto);
        }).toList();
    }

    public List<EvaluationDto> getSessionEvaluationsAvg(Integer sessionId) {
        var evaluations = evaluationRepository.findBySessionId(sessionId).stream()
                .collect(Collectors.groupingBy(CrossCheckEvaluation::getEvaluated));
        List<EvaluationDto> evaluationDtos = new ArrayList<>();
        for(var e: evaluations.entrySet()){
            var evaluatedDto = new UserDto(
                    e.getKey().getId(),
                    e.getKey().getFirstname(),
                    e.getKey().getLastname(),
                    e.getKey().getRole().toString()
            );
            List<MarkDto> marks = new ArrayList<>();
            var questionMap = e.getValue().stream()
                    .collect(Collectors.groupingBy(CrossCheckEvaluation::getQuestion));
            for(var q: questionMap.entrySet()){
                var questionDto = new QuestionDto(q.getKey().getId(), q.getKey().getQuestion());
                var avgM = q.getValue().stream().mapToInt(qmarks->qmarks.getMark()).average().getAsDouble();
                marks.add(new MarkDto(questionDto, avgM));
            }

            evaluationDtos.add(new EvaluationDto(evaluatedDto, marks));
        }
        return evaluationDtos;


    }

}
