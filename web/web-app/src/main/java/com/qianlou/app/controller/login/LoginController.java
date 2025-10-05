package com.qianlou.app.controller.login;


import com.qianlou.app.service.LoginService;
import com.qianlou.app.vo.user.LoginVo;
import com.qianlou.app.vo.user.UserInfoVo;
import com.qianlou.common.annotation.CheckPermission;
import com.qianlou.common.context.LoginUserContext;
import com.qianlou.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "登录管理")
@RequestMapping("/app/")
public class LoginController {

    @Resource
    private LoginService loginService;

    @CheckPermission
    @GetMapping("/login/getCode")
    @Operation(summary = "获取短信验证码")
    public Result getCode(@RequestParam String phone) {
        if (1==1){
            return Result.ok();
        }
        loginService.getSMSCode(phone);
        return Result.ok();
    }

    @PostMapping("/login")
    @Operation(summary = "登录")
    public Result<String> login(@RequestBody LoginVo loginVo) {
        return Result.ok(loginService.login(loginVo));
    }

    @GetMapping("/info")
    @Operation(summary = "获取登录用户信息")
    public Result<UserInfoVo> info() {
        UserInfoVo userInfoVo = loginService.getUserInfoById(LoginUserContext.getLoginUser().getUserId());
        return Result.ok(userInfoVo);
    }
}
