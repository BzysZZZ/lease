package com.pzj.lease.web.admin.consumer;


import com.fasterxml.jackson.databind.ser.Serializers;
import com.pzj.lease.web.admin.controller.user.UserInfoController;
import com.pzj.lease.web.admin.dto.FileUploadMessage;
import com.pzj.lease.web.admin.service.FileService;
import io.minio.errors.*;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
@RocketMQMessageListener(topic = "file-upload-topic",consumerGroup = "file-upload-consumer-group")
public class FileUploadConsumer implements RocketMQListener<FileUploadMessage> {
    @Autowired
    private FileService fileService;

    private static final Logger log = LoggerFactory.getLogger(FileUploadConsumer.class);

    @Override
    public void onMessage(FileUploadMessage fileUploadMessage){
        System.out.println("接收到文件上传任务,taskID:"+fileUploadMessage.getTackID());

        //1.从消息中还原文件
        byte[] fileBytes= Base64.getDecoder().decode(fileUploadMessage.getFileContent());
        //为了能传给原来的upload方法，需要把byte[]包装成一个MultipartFile
        //Spring有一个现成的实现，MockMultipartFile
        try {
            MultipartFile multipartFile = new MockMultipartFile(
                    fileUploadMessage.getFileName(),
                    fileUploadMessage.getFileName(),
                    fileUploadMessage.getContentType(),
                    new ByteArrayInputStream(fileBytes)
            );
            //2.调用原来的upload方法
            String fileUrl=fileService.upload(multipartFile);

        } catch (Exception e){
            log.error("任务处理失败,taskID:{}", fileUploadMessage.getTackID(), e);
        }
    }
}
