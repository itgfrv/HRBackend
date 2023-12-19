package com.gafarov.bastion.controller;

import com.amazonaws.services.s3.model.*;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.service.AmazonClient;
import com.gafarov.bastion.service.impl.HWTaskService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/hw")
@CrossOrigin
@AllArgsConstructor
public class HWTaskController extends BaseController {

    @Autowired
    private final HWTaskService service;

    @PostMapping(value = "/load", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void loadFiles(@AuthenticationPrincipal User user, @RequestBody MultipartFile[] files) {
        service.saveToS3(user,files);
    }
    @GetMapping(value = "/{file}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ByteArrayResource download(@PathVariable String file){
        var data = service.downloadFile(file);
        final ByteArrayResource resource = new ByteArrayResource(data);
        return resource;
    }



}
