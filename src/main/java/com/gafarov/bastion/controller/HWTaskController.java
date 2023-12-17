package com.gafarov.bastion.controller;

import com.amazonaws.services.s3.model.*;
import com.gafarov.bastion.service.AmazonClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/hw")
public class HWTaskController extends BaseController{
    private final AmazonClient amazon;

    public HWTaskController(AmazonClient amazon) {
        this.amazon = amazon;
    }

    @PostMapping("/load")
    public void loadFiles(@RequestBody List<MultipartFile> files) {
        files.forEach(multipartFile -> {
            try {
                this.saveToS3(multipartFile.getInputStream(), multipartFile.getOriginalFilename());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    private void saveToS3(final InputStream inputStream, final String originalImagePath) throws IOException {
        final ObjectMetadata md = new ObjectMetadata();
        final AccessControlList acl = new AccessControlList();
        final byte[] buffer = inputStream.readAllBytes();
        final InputStream convertedInputStream = new ByteArrayInputStream(buffer);
        md.setContentLength(buffer.length);
        md.setContentType("audio/mp3");

        final PutObjectRequest req = new PutObjectRequest(this.amazon.getBucketName(), originalImagePath, convertedInputStream, md);
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        req.setAccessControlList(acl);
        this.amazon.getS3client().putObject(req);
    }

}
