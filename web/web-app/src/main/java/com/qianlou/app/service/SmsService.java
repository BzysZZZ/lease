package com.qianlou.app.service;

public interface SmsService {

    void sendCode(String phone, String code);
}
