package com.qianlou.app.interceptor;

import com.qianlou.common.context.LoginUser;
import com.qianlou.common.context.LoginUserContext;
import com.qianlou.common.exception.LeaseException;
import com.qianlou.common.result.ResultCodeEnum;
import com.qianlou.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String token = request.getHeader("access_token");
        if (token == null) {
            throw new LeaseException(ResultCodeEnum.APP_LOGIN_AUTH);
        }
        Claims claims = JwtUtil.parseToken(token);
        Long userId = claims.get("userId", Long.class);
        String username = claims.get("username", String.class);
        LoginUserContext.setLoginUser(new LoginUser(userId, username));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserContext.clear();
    }
}