package com.sky.config; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sky.properties.StorageProperties;
import com.sky.utils.MinioUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MinioClientConfiguration {

    @Bean
    public MinioUtil minioUtil(@Autowired StorageProperties storageProperties) {
        log.info("≥ı ºªØMinioClient");
        return new MinioUtil(storageProperties.getUrl(), 
                storageProperties.getAccessKey(), 
                storageProperties.getSecretKey());
    }
    
}
