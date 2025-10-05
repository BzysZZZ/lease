package com.pzj.lease.web.admin.custom.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

@Configuration
public class RocketMQConfiguration {
    
    @Value("${rocketmq.name-server}")
    private String nameServer;
    
    @Value("${rocketmq.producer.group}")
    private String producerGroup;
    
@Bean
public RocketMQTemplate rocketMQTemplate() {
    // 创建默认生产者并设置组名和NameServer地址
    DefaultMQProducer producer = new DefaultMQProducer(producerGroup);
    producer.setNamesrvAddr(nameServer);
    
    // 创建RocketMQTemplate并设置生产者
    RocketMQTemplate rocketMQTemplate = new RocketMQTemplate();
    rocketMQTemplate.setProducer(producer);
    return rocketMQTemplate;
}
}