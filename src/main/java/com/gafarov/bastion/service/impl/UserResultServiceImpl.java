package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.UserResult;
import com.gafarov.bastion.entity.quiz.QuestionType;
import com.gafarov.bastion.entity.quiz.Quiz;
import com.gafarov.bastion.entity.quiz.Result;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.repository.ResultRepository;
import com.gafarov.bastion.repository.UserResultRepository;
import com.gafarov.bastion.service.UserResultService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserResultServiceImpl implements UserResultService {
    private final ResultRepository resultRepository;
    private final UserResultRepository userResultRepository;

    public void createUserResult(User user, Quiz quiz) {
        Optional<UserResult> userResult = userResultRepository.findByUserIdAndQuizId(user.getId(), quiz.getId());
        if (userResult.isEmpty()) {
            UserResult ur = new UserResult();
            ur.setQuiz(quiz);
            ur.setUser(user);
            ur.setStartTime(OffsetDateTime.now());
            userResultRepository.save(ur);
        }
    }

    public List<UserResult> getUserResult(Integer userId) {
        return userResultRepository.findAllByUserId(userId);
    }

    public List<Result> saveResult(
            Map<String, Integer> result,
            Map<String, Integer> maxResult,
            User user,
            Quiz quiz
    ) {
        UserResult userResult = userResultRepository.findByUserIdAndQuizId(user.getId(), quiz.getId()).orElseThrow();
        List<Result> results = new ArrayList<>();
        for (var questionType : maxResult.keySet()) {
            Result res = new Result();
            res.setResult(result.getOrDefault(questionType, 0));
            res.setMaxResult(maxResult.get(questionType));
            res.setQuestionType(questionType);
            res.setUserResult(userResult);
            var savedRes = resultRepository.save(res);
            results.add(savedRes);
        }
        userResult.setEndTime(OffsetDateTime.now());
        userResultRepository.save(userResult);
        return results;
    }
}
