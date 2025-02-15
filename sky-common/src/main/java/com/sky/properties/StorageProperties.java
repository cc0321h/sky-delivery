package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.s3")
@Data
public class StorageProperties {

    private String url;
    private String accessKey;
    private String secretKey;

}
