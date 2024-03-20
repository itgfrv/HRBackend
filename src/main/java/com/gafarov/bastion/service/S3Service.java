package com.gafarov.bastion.service;

import org.springframework.web.multipart.MultipartFile;

public interface S3Service {
    void uploadFile(MultipartFile file, String filePath);
    MultipartFile getFile(String filePath);
}
