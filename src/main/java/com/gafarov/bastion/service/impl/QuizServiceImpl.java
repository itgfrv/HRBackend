package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.Result;
import com.gafarov.bastion.entity.UserResult;
import com.gafarov.bastion.entity.quiz.Answer;
import com.gafarov.bastion.entity.quiz.Question;
import com.gafarov.bastion.entity.quiz.QuestionType;
import com.gafarov.bastion.entity.quiz.Quiz;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.entity.user.UserStatus;
import com.gafarov.bastion.exception.ForbiddenException;
import com.gafarov.bastion.mapper.QuizMapper;
import com.gafarov.bastion.mapper.ResultMapper;
import com.gafarov.bastion.model.QuizAnswer;
import com.gafarov.bastion.model.QuizDto;
import com.gafarov.bastion.model.QuizResultDto;
import com.gafarov.bastion.model.ResultDto;
import com.gafarov.bastion.repository.QuizRepository;
import com.gafarov.bastion.repository.ResultRepository;
import com.gafarov.bastion.repository.UserRepository;
import com.gafarov.bastion.repository.UserResultRepository;
import com.gafarov.bastion.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserResultRepository userResultRepository;
    private final ResultRepository repository;
    private final UserRepository userRepository;

    public QuizDto getQuiz(Integer quizId, User user) {
        if ((quizId == 1 && user.getActivity() == Activity.RESUME) || (quizId == 2 && user.getActivity() == Activity.INTERVIEW)) {
            Quiz q = quizRepository.findById(quizId).orElseThrow();
            Optional<UserResult> userResult = userResultRepository.findByUserIdAndQuizId(user.getId(), quizId);
            if (userResult.isEmpty()) {
                UserResult ur = new UserResult();
                ur.setQuiz(q);
                ur.setUser(user);
                ur.setStartTime(OffsetDateTime.now());
                userResultRepository.save(ur);
            }
            return QuizMapper.INSTANCE.mapQuizToQuizDto(q);
        } else throw new ForbiddenException("u cant write this test");
    }

    public List<ResultDto> checkResult(List<QuizAnswer> answerList, User user, Integer quizId) {
        if ((quizId == 1 && user.getActivity() == Activity.RESUME) || (quizId == 2 && user.getUserStatus() == UserStatus.INTERVIEW)) {

            Quiz quiz = quizRepository.findById(quizId).orElseThrow();

            Map<QuestionType, Integer> result = new HashMap<>();
            Map<QuestionType, Integer> maxResult = new HashMap<>();

            List<Question> questions = quiz.getQuestionList();
            for (Question question : questions) {
                maxResult.put(question.getQuestionType(), maxResult.getOrDefault(question.getQuestionType(), 0) + 1);
                Answer ans = question.getAnswers().stream().filter(Answer::isCorrect).toList().get(0);
                List<QuizAnswer> quizAnswers = answerList.stream().filter(a -> a.questionId().equals(question.getId())).toList();
                if (quizAnswers.size() != 0) {
                    if (quizAnswers.get(0).answerId().equals(ans.getId())) {
                        result.put(question.getQuestionType(), result.getOrDefault(question.getQuestionType(), 0) + 1);
                    }
                }
            }
            UserResult userResult = userResultRepository.findByUserIdAndQuizId(user.getId(), quizId).orElseThrow();
            List<Result> results = new ArrayList<>();
            for (var questionType : maxResult.keySet()) {
                Result res = new Result();
                res.setResult(result.getOrDefault(questionType, 0));
                res.setMaxResult(maxResult.get(questionType));
                res.setQuestionType(questionType);
                res.setUserResult(userResult);
                var savedRes = repository.save(res);
                results.add(savedRes);
            }
            userResult.setEndTime(OffsetDateTime.now());
            userResultRepository.save(userResult);
            if (quizId == 1) {
                user.setActivity(Activity.WAITING_INTERVIEW);
                userRepository.save(user);
            }
            if (quizId == 2) {
                user.setActivity(Activity.WAITING_RESULT);
                userRepository.save(user);
            }
            return results.stream().map(ResultMapper.INSTANCE::mapResultToResultDto).toList();
        } else throw new ForbiddenException("u cant send test");
    }

    public List<QuizResultDto> getUserResult(Integer userId) {
        List<UserResult> userResults = userResultRepository.findAllByUserId(userId);
        List<QuizResultDto> resultDtos = new ArrayList<>();
        for (var userResult : userResults) {
            if (userResult.getEndTime() != null) {
                QuizResultDto res = new QuizResultDto();
                res.setResult(userResult.getResults().stream().map(ResultMapper.INSTANCE::mapResultToResultDto).collect(Collectors.toList()));
                res.setType(userResult.getQuiz().getQuizType());
                Long duration = userResult.getEndTime().toInstant().toEpochMilli() - userResult.getStartTime().toInstant().toEpochMilli();
                res.setDuration(duration);
                resultDtos.add(res);
            }
        }
        return resultDtos;
    }

}
