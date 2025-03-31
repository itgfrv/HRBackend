package com.gafarov.bastion.service;

import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.model.casestudy.CaseStudyAttemptDto;
import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CaseStudyService {
    void uploadFiles(MultipartFile[] files, User user, Integer attemptId) throws MessagingException;
    Integer addAttemptsToUser(Integer userId);
    List<CaseStudyAttemptDto> getUserAttempts(Integer userId);
}
