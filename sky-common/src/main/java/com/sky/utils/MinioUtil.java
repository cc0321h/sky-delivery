package com.sky.utils;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;

import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@AllArgsConstructor
@Slf4j
public class MinioUtil {

    private String url;
    private String accessKey;
    private String secretKey;
    
    /**
     * 上传文件
     * @param inputStream 
     */
    public String upload(InputStream inputStream, String objectName) {
        log.info("上传文件");

        MinioClient minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();;
        String bucketName = new String("public");
        try {
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(inputStream, inputStream.available(), -1).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder path = new StringBuilder().append(url).append("/").append(bucketName).append("/").append(objectName);;
        return path.toString();
    }
    

}
