package com.gafarov.bastion.service.impl;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gafarov.bastion.config.S3Config;
import com.gafarov.bastion.service.S3Service;
import org.springframework.web.multipart.MultipartFile;


public class S3ServiceImpl implements S3Service {
    private AmazonS3 s3client;

    private final S3Config configuration;

    public S3ServiceImpl(S3Config config) {
        this.configuration = config;
        initializeS3();
    }

    private void initializeS3() {
        this.s3client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(
                        new com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration(
                                "https://s3.timeweb.com",
                                "ru-1"
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
    public void saveFile(MultipartFile file, String filePath) {
        
    }

    @Override
    public MultipartFile getFile(String filePath) {
        return null;
    }
}
