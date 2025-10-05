package com.qianlou.app.controller.agreement;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.qianlou.app.service.LeaseAgreementService;
import com.qianlou.app.vo.agreement.AgreementDetailVo;
import com.qianlou.app.vo.agreement.AgreementItemVo;
import com.pzj.lease.common.annotation.CheckPermission;
import com.pzj.lease.common.context.LoginUserContext;
import com.pzj.lease.common.result.Result;
import com.pzj.lease.model.entity.LeaseAgreement;
import com.pzj.lease.model.enums.LeaseStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/agreement")
@Tag(name = "租约信息")
public class LeaseAgreementController {

    @Resource
    private LeaseAgreementService leaseAgreementService;

    @Operation(summary = "获取个人租约基本信息列表")
    @GetMapping("/listItem")
    public Result<List<AgreementItemVo>> listItem() {
        //APP端使用手机号码登录，故username即为手机号码
        List<AgreementItemVo> list = leaseAgreementService.listAgreementItemByPhone(LoginUserContext.getLoginUser().getUsername());
        return Result.ok(list);
    }

    @Operation(summary = "根据id获取租约详细信息")
    @GetMapping("/getDetailById")
    public Result<AgreementDetailVo> getDetailById(@RequestParam Long id) {
        AgreementDetailVo agreementDetailVo = leaseAgreementService.getAgreementDetailById(id);
        return Result.ok(agreementDetailVo);
    }

    // @CheckPermission
    // @Operation(summary = "根据id更新租约状态", description = "用于确认租约和提前退租")
    // @PostMapping("/updateStatusById")
    // public Result updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus leaseStatus) {
    //     LambdaUpdateWrapper<LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
    //     updateWrapper.eq(LeaseAgreement::getId, id);
    //     updateWrapper.set(LeaseAgreement::getStatus, leaseStatus);
    //     leaseAgreementService.update(updateWrapper);
    //     return Result.ok();
    // }


    // @CheckPermission
    // @Operation(summary = "保存或更新租约", description = "用于续约")
    // @PostMapping("saveOrUpdate")
    // public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
    //     leaseAgreementService.saveOrUpdate(leaseAgreement);
    //     return Result.ok();
    // }
}