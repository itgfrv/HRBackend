package com.gafarov.bastion.service.impl;

import com.gafarov.bastion.entity.casestudy.*;
import com.gafarov.bastion.entity.user.Activity;
import com.gafarov.bastion.entity.user.Role;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.casestudy.CaseStudyAttemptDto;
import com.gafarov.bastion.model.casestudy.CriteriaDto;
import com.gafarov.bastion.model.casestudy.EvaluationDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
            attempt.setStatus(AttemptStatus.SEND);
            caseStudyAttemptRepository.save(attempt);
        }
    }

    @Override
    public Integer addAttemptsToUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow();
        user.setActivity(Activity.CASE_STUDY);
        userRepository.save(user);
        CaseStudyAttempt attempt = new CaseStudyAttempt();
        attempt.setUserId(userId);
        attempt.setStatus(AttemptStatus.NOT_DONE);
        var a = caseStudyAttemptRepository.save(attempt);
        return a.getId();
    }

    @Override
    public List<CaseStudyAttemptDto> getUserAttempts(Integer userId) {
        return new ArrayList<>(
                caseStudyAttemptRepository.findAllByUserId(userId)
                        .stream()
                        .map(attempt -> new CaseStudyAttemptDto(attempt.getId(), attempt.getStatus()))
                        .toList()
        );
    }

    public List<CriteriaDto> getCriteria() {
        return criteriaRepository.findAll().stream().map(c -> new CriteriaDto(c.getId(), c.getCriteria())).toList();
    }

    public List<File> getAttemptFiles(Integer attemptId) {
        return fileRepository.findAllByCaseStudyAttemptId(attemptId);
    }

    public CaseStudyAttempt getCaseStudyAttempt(Integer attemptId, User user) {
        var attempt = caseStudyAttemptRepository.findById(attemptId).orElseThrow();
        if (user.getRole() == Role.ADMIN || Objects.equals(user.getId(), attempt.getUserId())) {
            return attempt;
        } else {
            throw new RuntimeException();
        }
    }

    public void saveEvaluations(Integer caseStudyAttemptId, List<EvaluationDTO> evaluations) {
        CaseStudyAttempt caseStudyAttempt = caseStudyAttemptRepository.findById(caseStudyAttemptId)
                .orElseThrow(() -> new IllegalArgumentException("CaseStudyAttempt not found with id: " + caseStudyAttemptId));
        if (caseStudyAttempt.getStatus() != AttemptStatus.SEND)
            return;
        for (EvaluationDTO evaluation : evaluations) {
            Criteria criteria = criteriaRepository.findById(evaluation.getCriteriaId())
                    .orElseThrow(() -> new IllegalArgumentException("Criteria not found with id: " + evaluation.getCriteriaId()));
            CaseStudyMark mark = new CaseStudyMark();
            mark.setCriteria(criteria);
            mark.setCaseStudyAttempt(caseStudyAttempt);
            mark.setMark(evaluation.getMark());
            mark.setComment(evaluation.getComment());
            caseStudyMarkRepository.save(mark);
        }
        caseStudyAttempt.setStatus(AttemptStatus.CHECKED);
        caseStudyAttemptRepository.save(caseStudyAttempt);
    }
}
