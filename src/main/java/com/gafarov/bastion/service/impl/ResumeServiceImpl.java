package com.gafarov.bastion.service.impl;


import com.gafarov.bastion.entity.resume.ResumeAnswer;
import com.gafarov.bastion.entity.resume.ResumeQuestion;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.resume.ResumeAnswerDto;
import com.gafarov.bastion.model.resume.ResumeDto;
import com.gafarov.bastion.model.resume.ResumeQuestionDto;
import com.gafarov.bastion.repository.ResumeAnswerRepository;
import com.gafarov.bastion.repository.ResumeQuestionRepository;
import com.gafarov.bastion.repository.UserRepository;
import com.gafarov.bastion.service.ResumeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeQuestionRepository questionRepository;
    private final ResumeAnswerRepository answerRepository;
    private final UserRepository userRepository;

    public ResumeDto getResume(User user) {
        List<ResumeQuestion> questions = questionRepository.findAll().stream().sorted(Comparator.comparingInt(ResumeQuestion::getId)).toList();
        List<ResumeQuestionDto> dto = questions.stream()
                .map(q -> new ResumeQuestionDto(q.getId(), q.getQuestion()))
                .toList();
        List<ResumeAnswer> answers = answerRepository.findAllByUserId(user.getId());
        List<ResumeAnswerDto> answerDtos = answers.stream()
                .map(a -> new ResumeAnswerDto(a.getQuestion().getId(), a.getAnswer()))
                .toList();
        return new ResumeDto(dto, answerDtos);
    }

    public ResumeDto updateResume(User user, List<ResumeAnswerDto> answers) {
        for (var ans : answers) {
            var resumeAnswer = answerRepository
                    .findByUserIdAndQuestionId(user.getId(), ans.getQuestionId())
                    .orElseGet(ResumeAnswer::new);
            resumeAnswer.setAnswer(ans.getAnswer());
            resumeAnswer.setUser(user);
            resumeAnswer.setQuestion(questionRepository.findById(ans.getQuestionId()).orElseThrow());
            answerRepository.save(resumeAnswer);
        }
        return getResume(user);
    }

    public ResumeDto sendResume(User user, List<ResumeAnswerDto> answers) {
        updateResume(user, answers);
        user.setActivity(Activity.RESUME);
        user.setLastActivityDate(LocalDateTime.now());
        user.setViewed(false);
        userRepository.save(user);
        return getResume(user);
    }

}
