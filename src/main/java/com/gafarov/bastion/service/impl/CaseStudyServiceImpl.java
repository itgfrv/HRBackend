package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.casestudy.AttemptStatus;
import com.gafarov.bastion.entity.casestudy.CaseStudyAttempt;
import com.gafarov.bastion.entity.casestudy.File;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.casestudy.CaseStudyAttemptDto;
import com.gafarov.bastion.model.casestudy.CriteriaDto;
import com.gafarov.bastion.repository.UserRepository;
import com.gafarov.bastion.repository.casestudy.CaseStudyAttemptRepository;
import com.gafarov.bastion.repository.casestudy.CaseStudyMarkRepository;
import com.gafarov.bastion.repository.casestudy.CriteriaRepository;
import com.gafarov.bastion.repository.casestudy.FileRepository;
import com.gafarov.bastion.service.CaseStudyService;
import com.gafarov.bastion.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaseStudyServiceImpl implements CaseStudyService {
    private final S3Service s3Service;
    private final FileRepository fileRepository;
    private final CriteriaRepository criteriaRepository;
    private final CaseStudyAttemptRepository caseStudyAttemptRepository;
    private final CaseStudyMarkRepository caseStudyMarkRepository;
    private final UserRepository userRepository;

    @Override
    public void uploadFiles(MultipartFile[] files, User user, Integer attemptId) {
        CaseStudyAttempt attempt = caseStudyAttemptRepository.findByIdAndUserId(attemptId, user.getId()).orElseThrow();
        if (attempt.getStatus() == AttemptStatus.NOT_DONE) {
            for (MultipartFile file : files) {
                String fileName = attempt.getId() + "/" + file.getOriginalFilename();
                File fileEntity = new File();
                fileEntity.setFileName(file.getOriginalFilename());
                fileEntity.setCaseStudyAttempt(attempt);
                fileEntity.setFullPath(fileName);
                s3Service.uploadFile(file, fileName);
                fileRepository.save(fileEntity);
            }
            attempt.setStatus(AttemptStatus.DONE);
            caseStudyAttemptRepository.save(attempt);
        }
    }

    @Override
    public void addAttemptsToUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setActivity(Activity.CASE_STUDY);
        userRepository.save(user);
        CaseStudyAttempt attempt = new CaseStudyAttempt();
        attempt.setUserId(userId);
        attempt.setStatus(AttemptStatus.NOT_DONE);
        caseStudyAttemptRepository.save(attempt);
    }

    @Override
    public List<CaseStudyAttemptDto> getUserAttempts(Integer userId) {
        return caseStudyAttemptRepository.findAllByUserId(userId)
                .stream()
                .map(attempt -> {
                    return new CaseStudyAttemptDto(attempt.getId(), attempt.getStatus());
                }).toList();
    }

    public List<CriteriaDto> getCriteria() {
        return criteriaRepository.findAll().stream().map(c -> new CriteriaDto(c.getId(), c.getCriteria())).toList();
    }
    public List<File> getAttemptFiles(Integer attemptId) {
        return fileRepository.findAllByCaseStudyAttemptId(attemptId);
    }
    public CaseStudyAttempt getCaseStudyAttempt(Integer attemptId) {
        return caseStudyAttemptRepository.findById(attemptId).orElseThrow();
    }
}
