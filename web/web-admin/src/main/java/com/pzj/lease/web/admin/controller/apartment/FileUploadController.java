package com.pzj.lease.web.admin.controller.apartment;


import com.pzj.lease.common.result.Result;
import com.pzj.lease.common.result.ResultCodeEnum;
import com.pzj.lease.web.admin.dto.FileUploadMessage;
import com.pzj.lease.web.admin.service.FileService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;


@Tag(name = "文件管理")
@RequestMapping("/admin/file")
@RestController
public class FileUploadController {

    /*
    @Autowired
    private FileService fileService;

    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
            String url=fileService.upload(file);
            return Result.ok(url);
    }
    */

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    @PostMapping("upload")
    public Result<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        if(file.isEmpty()){
            return Result.fail(ResultCodeEnum.UPLOAD_FILE_NULL.getCode(),ResultCodeEnum.UPLOAD_FILE_NULL.getMessage());
        }
        //1.生成一个唯一的任务ID，方便后续查询状态
        String taskID= UUID.randomUUID().toString();

        //2.创建一个消息对象用来传递文件消息
        FileUploadMessage message = new FileUploadMessage();
        message.setTackID(taskID);
        message.setFileName(file.getOriginalFilename());
        message.setFileSize(file.getSize());
        message.setContentType(file.getContentType());
        message.setFileContent(Base64.getEncoder().encodeToString(file.getBytes()));

        //3.发送消息到RocketMQ的file-upload-topic
        rocketMQTemplate.convertAndSend("file-upload-topic", message);
        return Result.ok("文件上传任务已受理,taskID:"+taskID+"，请稍后查询结果");
    }

}
