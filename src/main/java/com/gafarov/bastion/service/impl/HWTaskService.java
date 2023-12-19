package com.gafarov.bastion.service.impl;

import com.amazonaws.services.s3.model.*;
import com.amazonaws.util.IOUtils;
import com.gafarov.bastion.entity.user.User;
import com.gafarov.bastion.service.AmazonClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class HWTaskService {
    @Autowired
    private final AmazonClient amazon;

    public void saveToS3(User user, MultipartFile[] files) {
        if (files.length == 0) return;
        Arrays.stream(files)
                .forEach(multipartFile -> {
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
        final PutObjectRequest req = new PutObjectRequest(this.amazon.getBucketName(), originalImagePath, convertedInputStream, md);
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        req.setAccessControlList(acl);
        this.amazon.getS3client().putObject(req);
    }

    @Async
    public byte[] downloadFile(final String keyName) {
        byte[] content = null;
        final GetObjectRequest request = new GetObjectRequest(this.amazon.getBucketName(), keyName);
        final S3Object s3Object = amazon.getS3client().getObject(request);
        final S3ObjectInputStream stream = s3Object.getObjectContent();
        try {
            content = IOUtils.toByteArray(stream);
            s3Object.close();
        } catch (final IOException ex) {
        }
        return content;
    }

}
