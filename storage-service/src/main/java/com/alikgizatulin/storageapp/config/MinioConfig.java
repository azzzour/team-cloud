package com.alikgizatulin.storageapp.config;

import io.minio.MinioClient;
import io.minio.admin.MinioAdminClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfig {

    @Value("${minio.endpoint}")
    private String minioEndpoint;

    @Value("${minio.access_key}")
    private String accessKey;

    @Value("${minio.secret_key}")
    private String secretKey;

    @Bean
    MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioEndpoint)
                .credentials(accessKey,secretKey)
                .build();
    }

    @Bean
    MinioAdminClient minioAdminClient() {
        return MinioAdminClient.builder()
                .endpoint(minioEndpoint)
                .credentials(accessKey,secretKey)
                .build();
    }
}
