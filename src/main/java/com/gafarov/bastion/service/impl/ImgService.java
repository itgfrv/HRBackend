package com.gafarov.bastion.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class ImgService {

    public byte[] getImageObject(String file) throws IOException {
        File fi = new File("~/HRBackend/img/" + file);
        return Files.readAllBytes(fi.toPath());
    }
}
