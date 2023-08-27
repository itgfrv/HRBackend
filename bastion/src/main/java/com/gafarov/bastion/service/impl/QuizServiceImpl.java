package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.UserResult;
import com.gafarov.bastion.entity.quiz.Answer;
import com.gafarov.bastion.entity.quiz.Question;
import com.gafarov.bastion.entity.quiz.QuestionType;
import com.gafarov.bastion.entity.quiz.Quiz;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.mapper.QuizMapper;
import com.gafarov.bastion.model.QuizAnswer;
import com.gafarov.bastion.model.QuizDto;
import com.gafarov.bastion.model.ResultDto;
import com.gafarov.bastion.repository.QuizRepository;
import com.gafarov.bastion.repository.UserResultRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.*;

@Service
@AllArgsConstructor
public class QuizServiceImpl {
    private final QuizRepository quizRepository;
    private final UserResultRepository userResultRepository;

    public QuizDto getQuiz(Integer quizId, User user) {
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
        List<ResultDto> resultDtos = new ArrayList<>();
        for(var questionType : maxResult.keySet()){
            ResultDto res = new ResultDto();
            res.setQuestionType(questionType);
            res.setMaxScore(maxResult.get(questionType));
            res.setCurScore(result.getOrDefault(questionType,0));
            resultDtos.add(res);
        }
        return resultDtos;
    }

}
