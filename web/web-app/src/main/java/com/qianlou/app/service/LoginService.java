package com.qianlou.app.service;


import com.qianlou.app.vo.user.LoginVo;
import com.qianlou.app.vo.user.UserInfoVo;

public interface LoginService {

    String login(LoginVo loginVo);

    UserInfoVo getUserInfoById(Long userId);

    void getSMSCode(String phone);

}
