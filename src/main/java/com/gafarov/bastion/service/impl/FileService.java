package com.gafarov.bastion.service.impl;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {
    @Value("${file.directory}")
    private String rootDirectory;

    @PostConstruct
    private void initializeStorage() {
        Path rootPath = Paths.get(rootDirectory);
        if (!Files.exists(rootPath)) {
            try {
                Files.createDirectories(rootPath);
            } catch (IOException e) {
                throw new RuntimeException("Не удалось создать корневую директорию для хранения файлов", e);
            }
        }
    }

    public void uploadFile(MultipartFile file, String filePath) {
        Path fullPath = Paths.get(rootDirectory, filePath);
        try {
            Files.createDirectories(fullPath.getParent());
            file.transferTo(fullPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Не удалось сохранить файл: " + filePath, e);
        }
    }

    public File getFile(String filePath) {
        Path fullPath = Paths.get(rootDirectory, filePath);
        File file = fullPath.toFile();
        if (!file.exists()) {
            throw new RuntimeException("Файл не найден: " + filePath);
        }

        return file;
    }
}
