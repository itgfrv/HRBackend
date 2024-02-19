package com.gafarov.bastion.controller;

import com.gafarov.bastion.service.impl.HWTaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;


@RestController
@RequestMapping("/hw")

@AllArgsConstructor
public class HWTaskController extends BaseController {

    @Autowired
    private final HWTaskService service;

    @PostMapping(value = "/load", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loadFiles(@RequestParam("file") MultipartFile[] files) {
        Arrays.stream(files).forEach(fil -> {
            System.out.println(fil.getOriginalFilename());
        });
        //service.saveToS3(user,files);
    }

    @GetMapping(value = "/{file}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ByteArrayResource download(@PathVariable String file) {
        var data = service.downloadFile(file);
        final ByteArrayResource resource = new ByteArrayResource(data);
        return resource;
    }


}
