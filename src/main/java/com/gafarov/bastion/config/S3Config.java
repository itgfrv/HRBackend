package com.gafarov.bastion.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "amazon")
@EnableConfigurationProperties
@Data
public class S3Config {

    private String endpointUrl;

    private String bucketName;

    private String accessKey;

    private String secretKey;

    private String region;

}
