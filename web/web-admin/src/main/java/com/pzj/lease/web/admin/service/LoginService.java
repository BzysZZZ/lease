package com.pzj.lease.web.admin.service;

import com.pzj.lease.web.admin.vo.login.CaptchaVo;
import com.pzj.lease.web.admin.vo.login.LoginVo;
import com.pzj.lease.web.admin.vo.system.user.SystemUserInfoVo;

public interface LoginService {

    CaptchaVo getCaptcha();

    String login(LoginVo loginVo);

    SystemUserInfoVo getLoginUserInfo(Long userId);
}
