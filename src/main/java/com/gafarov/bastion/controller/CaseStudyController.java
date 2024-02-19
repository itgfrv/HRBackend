package com.gafarov.bastion.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@RestController
@RequestMapping("/case-study")
public class CaseStudyController {
    @PostMapping(value = "/load", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loadFiles(@RequestParam("file") MultipartFile[] files) {

        Arrays.stream(files).forEach(fil -> {
            System.out.println(fil.getOriginalFilename());
        });
    }
}
