package com.sky.utils;

import org.springframework.web.multipart.MultipartFile;

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
     * @param file 
     */
    public String upload(MultipartFile file) {
        log.info("上传文件");

        MinioClient minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();;
        String bucketName = new String("public");
        String originalFilename = file.getOriginalFilename();
        
        try {
            minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            minioClient.putObject(
                PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(originalFilename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build()  
            );

            return minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                    .bucket(bucketName)
                    .object(originalFilename)
                    .method(Method.GET)
                    .build()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    

}
