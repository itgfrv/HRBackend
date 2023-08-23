package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.Resume;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.mapper.ResumeMapper;
import com.gafarov.bastion.model.ResumeDto;
import com.gafarov.bastion.repository.ResumeRepository;
import com.gafarov.bastion.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ResumeServiceImpl {
    private final ResumeRepository repository;
    private final UserRepository userRepository;

    public ResumeDto getPersonalResume(int id) {
        Optional<Resume> resume = repository.findByUserId(id);
        if(resume.isPresent()){
            return ResumeMapper.INSTANCE.mapResumeToResumeDto(resume.get());
        }
        return new ResumeDto("","","","","","","","","","","","");
    }

    public ResumeDto sendResume(ResumeDto resumeDto, User user) {
        var dto = updateResume(resumeDto, user);

        user.setActivity(Activity.RESUME);
        userRepository.save(user);

        return dto;
    }

    public ResumeDto updateResume(ResumeDto resumeDto, User user) {
        Resume r = ResumeMapper.INSTANCE.mapResumeDtoToResume(resumeDto);
        r.setUser(user);
        r.setId(user.getId());
        var savedResume = repository.save(r);
        return ResumeMapper.INSTANCE.mapResumeToResumeDto(savedResume);
    }
}
