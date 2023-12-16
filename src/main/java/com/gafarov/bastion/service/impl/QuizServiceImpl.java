package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.UserResult;
import com.gafarov.bastion.entity.quiz.Answer;
import com.gafarov.bastion.entity.quiz.Question;
import com.gafarov.bastion.entity.quiz.QuestionType;
import com.gafarov.bastion.entity.quiz.Quiz;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.mapper.QuizMapper;
import com.gafarov.bastion.mapper.ResultMapper;
import com.gafarov.bastion.model.quiz.QuizAnswer;
import com.gafarov.bastion.model.quiz.QuizDto;
import com.gafarov.bastion.model.quiz.QuizResultDto;
import com.gafarov.bastion.model.quiz.ResultDto;
import com.gafarov.bastion.repository.QuizRepository;
import com.gafarov.bastion.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final UserResultServiceImpl userResultService;
    private final UserServiceImpl userService;

    public QuizDto getQuiz(Integer quizId, User user) {
        Quiz quiz = quizRepository.findById(quizId).orElseThrow();
        userResultService.createUserResult(user, quiz);
        return QuizMapper.INSTANCE.mapQuizToQuizDto(quiz);
    }

    public List<ResultDto> checkResult(List<QuizAnswer> answerList, User user, Integer quizId) {
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
        var results = userResultService.saveResult(result, maxResult, user, quiz);
        return results.stream().map(ResultMapper.INSTANCE::mapResultToResultDto).toList();
    }


    public List<QuizResultDto> getUserResult(Integer userId) {
        List<UserResult> userResults = userResultService.getUserResult(userId);
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
