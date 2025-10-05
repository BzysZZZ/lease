package com.pzj.lease.common.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SuppressWarnings("all")
@Data
@ConfigurationProperties(prefix = "aliyun.sms")
public class AliyunSMSProperties {

    private String accessKeyId;

    private String accessKeySecret;

    private String endpoint;
}