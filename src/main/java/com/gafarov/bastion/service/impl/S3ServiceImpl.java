package com.gafarov.bastion.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.gafarov.bastion.config.S3Config;
import com.gafarov.bastion.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class S3ServiceImpl implements S3Service {
    private AmazonS3 s3client;
    @Autowired
    private final S3Config configuration;

    public S3ServiceImpl(S3Config config) {
        this.configuration = config;
        initializeS3();
    }

    private void initializeS3() {
        this.s3client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                                this.configuration.getEndpointUrl(),
                                this.configuration.getRegion()
                        )
                )
                .withCredentials(
                        new AWSStaticCredentialsProvider(
                                new BasicAWSCredentials(
                                        this.configuration.getAccessKey(),
                                        this.configuration.getSecretKey()
                                )))
                .build();
    }

    @Override
    public void uploadFile(MultipartFile file, String filePath) {
        final ObjectMetadata md = new ObjectMetadata();
        final AccessControlList acl = new AccessControlList();
        final byte[] buffer;
        try {
            buffer = file.getInputStream().readAllBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final InputStream convertedInputStream = new ByteArrayInputStream(buffer);
        md.setContentLength(buffer.length);
        final PutObjectRequest req = new PutObjectRequest(this.configuration.getBucketName(), filePath, convertedInputStream, md);
        acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
        req.setAccessControlList(acl);
        System.out.println(configuration.getRegion());
        System.out.println(this.configuration.getSecretKey());
        this.s3client.putObject(req);
    }

    @Override
    public MultipartFile getFile(String filePath) {
        return null;
    }
}
