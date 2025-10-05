package com.pzj.lease.web.admin.controller.system;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.common.result.Result;
import com.pzj.lease.model.entity.SystemPost;
import com.pzj.lease.model.entity.SystemUser;
import com.pzj.lease.model.enums.BaseStatus;
import com.pzj.lease.model.enums.SystemUserType;
import com.pzj.lease.web.admin.service.SystemPostService;
import com.pzj.lease.web.admin.service.SystemUserService;
import com.pzj.lease.web.admin.vo.system.user.SystemUserItemVo;
import com.pzj.lease.web.admin.vo.system.user.SystemUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kotlin.jvm.internal.Lambda;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.web.bind.annotation.*;


@Tag(name = "后台用户信息管理")
@RestController
@RequestMapping("/admin/system/user")
public class SystemUserController {

    @Autowired
    SystemUserService systemUserService;

    @Autowired
    SystemPostService systemPostService;

    @Operation(summary = "根据条件分页查询后台用户列表")
    @GetMapping("page")
    public Result<IPage<SystemUserItemVo>> page(@RequestParam long current, @RequestParam long size, SystemUserQueryVo queryVo) {
        Page<SystemUser> page = new Page<>(current, size);
        IPage<SystemUserItemVo> systemUserItemVoPage=systemUserService.getUserPage(page,queryVo);
        return Result.ok(systemUserItemVoPage);
    }

    @Operation(summary = "根据ID查询后台用户信息")
    @GetMapping("getById")
    public Result<SystemUserItemVo> getById(@RequestParam Long id) {
        SystemUser systemUser = systemUserService.getById(id);
        LambdaQueryWrapper<SystemPost> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemPost::getId, systemUser.getPostId());
        SystemPost systemPost = systemPostService.getOne(queryWrapper);
        SystemUserItemVo systemUserItemVo = new SystemUserItemVo();
        BeanUtils.copyProperties(systemUser, systemUserItemVo);
        systemUserItemVo.setPostName(systemPost.getName());
        return Result.ok(systemUserItemVo);
    }

    @Operation(summary = "保存或更新后台用户信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody SystemUser systemUser) {
        if(systemUser.getPassword()!=null){
            systemUser.setPassword(DigestUtils.md5Hex(systemUser.getPassword()));
        };
        systemUserService.saveOrUpdate(systemUser);
        return Result.ok();
    }

    @Operation(summary = "判断后台用户名是否可用")
    @GetMapping("isUserNameAvailable")
    public Result<Boolean> isUsernameExists(@RequestParam String username) {
        LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemUser::getUsername, username);
        Boolean isAvailable= Boolean.TRUE;
        SystemUser systemUser = systemUserService.getOne(queryWrapper);
        if(systemUser!=null){
            isAvailable=(systemUser.getStatus()== BaseStatus.ENABLE);
        }
        return Result.ok(isAvailable);
    }

    @DeleteMapping("deleteById")
    @Operation(summary = "根据ID删除后台用户信息")
    public Result removeById(@RequestParam Long id) {
        systemUserService.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "根据ID修改后台用户状态")
    @PostMapping("updateStatusByUserId")
    public Result updateStatusByUserId(@RequestParam Long id, @RequestParam BaseStatus status){
        LambdaUpdateWrapper<SystemUser> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(SystemUser::getId,id);
        updateWrapper.eq(SystemUser::getStatus,status);
        systemUserService.update(updateWrapper);
        return Result.ok();
    }
}
