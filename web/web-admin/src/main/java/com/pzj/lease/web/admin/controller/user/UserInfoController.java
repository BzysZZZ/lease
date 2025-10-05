package com.pzj.lease.web.admin.controller.user;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.common.result.Result;
import com.pzj.lease.model.entity.CursorPageVo;
import com.pzj.lease.model.entity.UserInfo;
import com.pzj.lease.model.enums.BaseStatus;
import com.pzj.lease.web.admin.interceptor.AuthenticationInterceptor;
import com.pzj.lease.web.admin.service.UserInfoService;
import com.pzj.lease.web.admin.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import kotlin.jvm.internal.Lambda;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Tag(name = "用户信息管理")
@RestController
@RequestMapping("/admin/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;

    private static final Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @Operation(summary = "分页查询用户信息")
    @GetMapping("page")
    public Result<IPage<UserInfo>> pageUserInfo(@RequestParam long current, @RequestParam long size, UserInfoQueryVo queryVo) {

        // 记录接口整体开始时间
        long totalStart = System.currentTimeMillis();
        log.debug("分页查询用户信息开始 - 时间戳：{}ms，参数：current={}, size={}, queryVo={}",
                totalStart, current, size, queryVo);

        // 分页查询环节：记录耗时
        long queryStart = System.currentTimeMillis();
        Page<UserInfo> userInfoPage = new Page<>(current, size);
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(queryVo.getPhone()!=null,UserInfo::getPhone,queryVo.getPhone());
        queryWrapper.eq(queryVo.getStatus()!=null,UserInfo::getStatus,queryVo.getStatus());
        Page<UserInfo> infoPage = userInfoService.page(userInfoPage, queryWrapper);

        long queryEnd = System.currentTimeMillis();
        log.debug("数据库分页查询耗时：{}ms，查询结果总数：{}", (queryEnd - queryStart), infoPage.getTotal());

        // 记录接口整体结束时间
        long totalEnd = System.currentTimeMillis();
        log.debug("分页查询用户信息结束 - 时间戳：{}ms，接口总耗时：{}ms", totalEnd, (totalEnd - totalStart));


        return Result.ok(infoPage);
    }


    @Operation(summary = "游标分页查询用户信息")
    @Parameters({
            @Parameter(name="size",description = "每页条数",required = true,example = "50"),
            @Parameter(name="lastId",description = "上一页最后一条ID(初始0)",required = true,example = "0"),
            @Parameter(name = "phone",description = "用户手机号（筛选条件可选)"),
            @Parameter(name="status",description = "用户状态（0禁用1正常）")
    })
    @GetMapping("/cursor-page")
    public Result<CursorPageVo<UserInfo>> cursorPage(@RequestParam long size, @RequestParam long lastId, UserInfoQueryVo queryVo) {
        CursorPageVo<UserInfo> cursorPageVo=userInfoService.cursorPage(size,lastId,queryVo);
        return Result.ok(cursorPageVo);
    }

    @Operation(summary = "根据用户id更新账号状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam BaseStatus status) {
        LambdaUpdateWrapper<UserInfo> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(UserInfo::getId,id).set(UserInfo::getStatus,status);
        userInfoService.update(updateWrapper);
        return Result.ok();
    }
}
