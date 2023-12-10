package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.controller.OldResumeDto;
import com.gafarov.bastion.entity.resume.ResumeAnswer;
import com.gafarov.bastion.entity.resume.ResumeQuestion;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.ResumeAnswerDto;
import com.gafarov.bastion.model.ResumeDto;
import com.gafarov.bastion.model.ResumeQuestionDto;
import com.gafarov.bastion.repository.ResumeAnswerRepository;
import com.gafarov.bastion.repository.ResumeQuestionRepository;
import com.gafarov.bastion.repository.UserRepository;
import com.gafarov.bastion.service.ResumeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeQuestionRepository questionRepository;
    private final ResumeAnswerRepository answerRepository;
    private final UserRepository userRepository;

    public ResumeDto getResume(User user) {
        List<ResumeQuestion> questions = questionRepository.findAll();
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
        return getResume(user);
    }

    public OldResumeDto getOld(User user) {
        List<ResumeAnswer> answers = answerRepository.findAllByUserId(user.getId());
        OldResumeDto resumeDto = new OldResumeDto();
        for (ResumeAnswer ra : answers) {
            Integer id = ra.getQuestion().getId();
            switch (id) {
                case 1 -> resumeDto.setPhoneNumber(ra.getAnswer());
                case 2 -> resumeDto.setEmail(ra.getAnswer());
                case 4 -> resumeDto.setMilitaryDuty(ra.getAnswer());
                case 5 -> resumeDto.setEducation(ra.getAnswer());
                case 6 -> resumeDto.setMetroStation(ra.getAnswer());
                case 10 -> resumeDto.setGoodQualities(ra.getAnswer());
                case 11 -> resumeDto.setBadQualities(ra.getAnswer());
                case 12 -> resumeDto.setBadHabits(ra.getAnswer());
                case 13 -> resumeDto.setReasonsForWorking(ra.getAnswer());
                case 14 -> resumeDto.setGoodJobQualities(ra.getAnswer());
                case 16 -> resumeDto.setBusyness(ra.getAnswer());
                case 15 -> resumeDto.setResumeSrc(ra.getAnswer());

            }
        }
        return resumeDto;
    }
    public OldResumeDto postOld(User user, OldResumeDto resumeDto){
        putOld(user,resumeDto);
        return getOld(user);
    }
    public OldResumeDto putOld(User user, OldResumeDto resumeDto){
        for(int i = 1;i<17;i++){
            var ra = answerRepository
                    .findByUserIdAndQuestionId(user.getId(), i)
                    .orElseGet(ResumeAnswer::new);
            String text;
            switch (i) {
                case 1 -> text=resumeDto.getPhoneNumber();
                case 2 -> text=resumeDto.getEmail();
                case 4 -> text=resumeDto.getMilitaryDuty();
                case 5 -> text=resumeDto.getEducation();
                case 6 -> text=resumeDto.getMetroStation();
                case 10 -> text=resumeDto.getGoodQualities();
                case 11 -> text=resumeDto.getBadQualities();
                case 12 -> text=resumeDto.getBadHabits();
                case 13 -> text=resumeDto.getReasonsForWorking();
                case 14 -> text=resumeDto.getGoodJobQualities();
                case 16 -> text=resumeDto.getBusyness();
                case 15 -> text=resumeDto.getResumeSrc();
                default -> text="";
            }
            ra.setQuestion(questionRepository.getReferenceById(i));
            ra.setAnswer(text);
            ra.setUser(user);
            answerRepository.save(ra);
        }
        return getOld(user);
    }
}
