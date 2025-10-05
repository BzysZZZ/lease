package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.qianlou.app.mapper.UserInfoMapper;
import com.qianlou.app.service.LoginService;
import com.qianlou.app.service.SmsService;
import com.qianlou.app.service.UserInfoService;
import com.qianlou.app.vo.user.LoginVo;
import com.qianlou.app.vo.user.UserInfoVo;
import com.qianlou.common.constant.RedisConstant;
import com.qianlou.common.exception.LeaseException;
import com.qianlou.common.result.ResultCodeEnum;
import com.qianlou.common.utils.JwtUtil;
import com.qianlou.common.utils.VerifyCodeUtil;
import com.qianlou.model.entity.UserInfo;
import com.qianlou.model.enums.BaseStatus;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private SmsService smsService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserInfoService userInfoService;


    @Override
    public String login(LoginVo loginVo) {
        //1.判断手机号码和验证码是否为空
        if (!StringUtils.hasText(loginVo.getPhone())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }
        if (!StringUtils.hasText(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EMPTY);
        }
        //2.校验验证码
//        String key = RedisConstant.APP_LOGIN_PREFIX + loginVo.getPhone();
//        String code = stringRedisTemplate.opsForValue().get(key);
        String code = "123456";
        if (code == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_EXPIRED);
        }
        if (!code.equals(loginVo.getCode())) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_CODE_ERROR);
        }
        //3.判断用户是否存在,不存在则注册（创建用户）
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getPhone, loginVo.getPhone());
        UserInfo userInfo = userInfoService.getOne(queryWrapper);
        if (userInfo == null) {
            userInfo = new UserInfo();
            userInfo.setPhone(loginVo.getPhone());
            userInfo.setStatus(BaseStatus.ENABLE);
            userInfo.setNickname("用户-"+loginVo.getPhone().substring(5));
            userInfoService.save(userInfo);
        }
        //4.判断用户是否被禁
        if (userInfo.getStatus().equals(BaseStatus.DISABLE)) {
            throw new LeaseException(ResultCodeEnum.APP_ACCOUNT_DISABLED_ERROR);
        }
        //5.创建并返回TOKEN
        return JwtUtil.createToken(userInfo.getId(), loginVo.getPhone());
    }

    @Override
    public UserInfoVo getUserInfoById(Long userId) {
        UserInfo userInfo = userInfoMapper.selectById(userId);
        return new UserInfoVo(userInfo.getNickname(), userInfo.getAvatarUrl());
    }

    @Override
    public void getSMSCode(String phone) {
        //1. 检查手机号码是否为空
        if (!StringUtils.hasText(phone)) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_PHONE_EMPTY);
        }
        //2. 检查Redis中是否已经存在该手机号码的key
        String key = RedisConstant.APP_LOGIN_PREFIX + phone;
        boolean hasKey = Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
        if (hasKey) {
            //若存在，则检查其存在的时间
            Long expire = stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);
            if (RedisConstant.APP_LOGIN_CODE_TTL_SEC - expire < RedisConstant.APP_LOGIN_CODE_RESEND_TIME_SEC) {
                //若存在时间不足一分钟，响应发送过于频繁
                throw new LeaseException(ResultCodeEnum.APP_SEND_SMS_TOO_OFTEN);
            }
        }
        //3.发送短信，并将验证码存入Redis
        String verifyCode = VerifyCodeUtil.getVerifyCode(6);
        smsService.sendCode(phone, verifyCode);
        stringRedisTemplate.opsForValue().set(key, verifyCode, RedisConstant.APP_LOGIN_CODE_TTL_SEC, TimeUnit.SECONDS);
    }
}
