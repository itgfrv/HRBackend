package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.UserResult;
import com.gafarov.bastion.entity.quiz.*;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.exception.ForbiddenException;
import com.gafarov.bastion.mapper.QuizMapper;
import com.gafarov.bastion.mapper.ResultMapper;
import com.gafarov.bastion.model.quiz.QuizAnswer;
import com.gafarov.bastion.model.quiz.QuizDto;
import com.gafarov.bastion.model.quiz.QuizResultDto;
import com.gafarov.bastion.model.quiz.ResultDto;
import com.gafarov.bastion.repository.*;
import com.gafarov.bastion.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserResultServiceImpl userResultService;
    private final UserResultRepository userResultRepository;
    private final ResultRepository repository;
    private final UserRepository userRepository;
    private final UserAnswerRepository userAnswerRepository;

    public QuizDto getQuiz(Integer quizId, User user) {
        if ((user.getActivity() == Activity.RESUME && quizId == 1) || (user.getActivity() == Activity.INTERVIEW && quizId == 2)) {
            Quiz quiz = quizRepository.findById(quizId).orElseThrow();
            userResultService.createUserResult(user, quiz);
            return QuizMapper.INSTANCE.mapQuizToQuizDto(quiz);
        } else {
            throw new ForbiddenException("Нельзя");
        }
    }

    public List<ResultDto> checkResult(List<QuizAnswer> answerList, User user, Integer quizId) {
        if ((user.getActivity() == Activity.RESUME && quizId == 1) || (user.getActivity() == Activity.INTERVIEW && quizId == 2)) {
            Quiz quiz = quizRepository.findById(quizId).orElseThrow();
            UserResult userResult = userResultRepository.findByUserIdAndQuizId(user.getId(), quiz.getId()).orElseThrow();
            Map<String, Integer> result = new LinkedHashMap<>();
            Map<String, Integer> maxResult = new LinkedHashMap<>();
            List<Question> questions = quiz.getQuestionList();
            for (Question question : questions) {
                maxResult.put(question.getQuestionType(), maxResult.getOrDefault(question.getQuestionType(), 0) + 1);
                Answer ans = question.getAnswers().stream().filter(Answer::isCorrect).toList().get(0);
                List<QuizAnswer> quizAnswers = answerList.stream().filter(a -> a.questionId().equals(question.getId())).toList();
                if (quizAnswers.size() != 0) {
                    UserAnswer userAnswer = new UserAnswer();
                    userAnswer.setQuestion(question);
                    userAnswer.setUserResult(userResult);
                    userAnswer.setAnswer(question.getAnswers().stream().filter(answer -> {
                        return answer.getId().equals(quizAnswers.get(0).answerId());
                    }).toList().get(0));
                    userAnswerRepository.save(userAnswer);
                    if (quizAnswers.get(0).answerId().equals(ans.getId())) {
                        result.put(question.getQuestionType(), result.getOrDefault(question.getQuestionType(), 0) + 1);
                    }
                }
            }
            var results = userResultService.saveResult(result, maxResult, user, quiz);
            if (quizId == 1) {
                user.setActivity(Activity.WAITING_INTERVIEW);
                userRepository.save(user);
            } else {
                user.setActivity(Activity.WAITING_RESULT);
                userRepository.save(user);
            }
            return results.stream().map(ResultMapper.INSTANCE::mapResultToResultDto).toList();
        } else {
            throw new ForbiddenException("Нельзя!");
        }
    }


    public List<QuizResultDto> getUserResult(Integer userId) {
        List<UserResult> userResults = userResultService.getUserResult(userId);
        List<QuizResultDto> resultDtos = new ArrayList<>();
        for (var userResult : userResults) {
            if (userResult.getEndTime() != null) {
                QuizResultDto res = new QuizResultDto();
                res.setUserResult(userResult.getId());
                List<Result> results = repository.findAllByUserResultId(userResult.getId());
                res.setResult(results
                        .stream()
                        .map(ResultMapper.INSTANCE::mapResultToResultDto).collect(Collectors.toList()));
                res.setType(userResult.getQuiz().getQuizType());
                Long duration = userResult.getEndTime().toInstant().toEpochMilli() - userResult.getStartTime().toInstant().toEpochMilli();
                res.setDuration(duration);
                resultDtos.add(res);
            }
        }
        return resultDtos;
    }

}
