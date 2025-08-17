package com.wisespendinglife.wise_spending_life.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
public class S3Config {


    @Bean
    public S3Client s3Client(@Value("${s3.region:${aws.region:${AWS_REGION:${AWS_DEFAULT_REGION:}}}}") String region) {
        return S3Client.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner(@Value("${s3.region:${aws.region:${AWS_REGION:${AWS_DEFAULT_REGION:}}}}") String region) {
        return S3Presigner.builder()
                .region(software.amazon.awssdk.regions.Region.of(region))
                .build();
    }
}
