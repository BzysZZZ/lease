package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.UserInfoMapper;
import com.qianlou.app.service.UserInfoService;
import com.qianlou.model.entity.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {

}




