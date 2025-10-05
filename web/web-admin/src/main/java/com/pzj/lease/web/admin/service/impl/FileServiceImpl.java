package com.pzj.lease.web.admin.service.impl;

import com.google.j2objc.annotations.AutoreleasePool;
import com.pzj.lease.common.minio.MinioProperties;
import com.pzj.lease.web.admin.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    /*

    @Autowired
    private MinioClient minioClient;

    @Autowired
    MinioProperties minioProperties;


    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
            if(!bucketExists){
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
                minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minioProperties.getBucketName()).config("""
                {
                  "Statement":[{
                    "Action":"s3:GetObject",
                    "Effect":"Allow",
                    "Principal":"*",
                    "Resource":"arn:aws:s3:::%s/*"
                  }],
                  "Version":"2012-10-17"
                }
                """.formatted(minioProperties.getBucketName())).build());
            }
            String fileName = new SimpleDateFormat("yyyyMMdd").format(new Date())+"/"+ UUID.randomUUID()+"-"+file.getOriginalFilename();
            minioClient.putObject(PutObjectArgs.builder().bucket(minioProperties.getBucketName()).object(fileName).stream(file.getInputStream(),file.getSize(),-1).contentType(file.getContentType()).build());
            return minioProperties.getEndpoint()+"/"+minioProperties.getBucketName()+fileName;
    }

     */

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioProperties minioProperties;

    @PostConstruct
    public void initBucket() throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        boolean bucketExists=minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build());
        if(!bucketExists){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucketName()).build());
            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket(minioProperties.getBucketName()).config("""
                {
                  "Statement":[{
                    "Action":"s3:GetObject",
                    "Effect":"Allow",
                    "Principal":"*",
                    "Resource":"arn:aws:s3:::%s/*"
                  }],
                  "Version":"2012-10-17"
                }
                """.formatted(minioProperties.getBucketName())).build());
        }
    }

    @Override
    public String upload(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String fileName=new SimpleDateFormat("yyyyMMdd").format(new Date())+"/"+UUID.randomUUID()+"-"+file.getOriginalFilename();
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .object(fileName)
                        .stream(file.getInputStream(),file.getSize(),-1)
                        .contentType(file.getContentType())
                        .build()
        );
        return minioProperties.getEndpoint()+"/"+minioProperties.getBucketName()+"/"+fileName;
    }
}
