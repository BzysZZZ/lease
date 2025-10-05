package com.pzj.lease.web.admin.interceptor;

import com.pzj.lease.common.exception.LeaseException;
import com.pzj.lease.common.login.LoginUser;
import com.pzj.lease.common.login.LoginUserHolder;
import com.pzj.lease.common.result.ResultCodeEnum;
import com.pzj.lease.common.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.rmi.dgc.Lease;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //请求可以获取用户请求的所有信息，token一般放在请求头
        //响应可以对响应信息进行修改
        //handler使我们所拦截的方法，可以通过反射获取方法的信息
        //返回值如果是true就会放行，否则处理到此为止
        long start = System.currentTimeMillis();
        log.debug("Token校验开始 - 时间戳：{}ms，请求路径：{}", start, request.getRequestURI());
        String token = request.getHeader("access_token");
        System.out.println("token:"+token);
        if(token==null){
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        }

        long parseStart = System.currentTimeMillis();
        Claims claims = JwtUtils.parseToken(token);
        long parseEnd = System.currentTimeMillis();
        log.debug("Token解析耗时：{}ms，解析结果 - userId：{}，userName：{}",
                (parseEnd - parseStart),
                claims.get("userId", Long.class),
                claims.get("userName", String.class));

        Long userId = claims.get("userId", Long.class);
        String userName = claims.get("userName", String.class);

        LoginUserHolder.setLoginUser(new LoginUser(userId,userName));

        long end = System.currentTimeMillis();
        log.debug("Token校验结束 - 时间戳：{}ms，总耗时：{}ms", end, (end - start));

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        LoginUserHolder.clear();
    }
}
