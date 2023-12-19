package com.gafarov.bastion.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.gafarov.bastion.config.S3Config;
import org.springframework.stereotype.Service;

@Service
public class AmazonClient {
    private AmazonS3 s3client;

    private final S3Config configuration;

    public AmazonClient(final S3Config configuration) {
        this.configuration = configuration;
        this.initializeAmazon();
    }

    private void initializeAmazon() {
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
                                        this.getAccessKeyId(),
                                        this.getSecretAccessKey())))
                .build();
    }

    public final AmazonS3 getS3client() {
        return this.s3client;
    }

    public final String getBucketName() {
        return this.configuration.getBucketName();
    }

    public final String getAccessKeyId() {
        return this.configuration.getAccessKey();
    }

    public final String getSecretAccessKey() {
        return this.configuration.getSecretKey();
    }

    public final String getRegion() {
        return this.configuration.getRegion();
    }
}
