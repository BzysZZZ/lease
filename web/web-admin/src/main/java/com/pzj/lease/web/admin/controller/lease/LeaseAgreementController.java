package com.pzj.lease.web.admin.controller.lease;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.common.result.Result;
import com.pzj.lease.model.entity.*;
import com.pzj.lease.model.enums.LeaseStatus;
import com.pzj.lease.web.admin.service.*;
import com.pzj.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.pzj.lease.web.admin.vo.agreement.AgreementVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Tag(name = "租约管理")
@RestController
@RequestMapping("/admin/agreement")
public class LeaseAgreementController {

    @Autowired
    LeaseAgreementService leaseAgreementService;

    @Autowired
    ApartmentInfoService apartmentInfoService;

    @Autowired
    RoomInfoService  roomInfoService;

    @Autowired
    PaymentTypeService paymentTypeService;

    @Autowired
    LeaseTermService leaseTermService;

    @Operation(summary = "保存或修改租约信息")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdate(@RequestBody LeaseAgreement leaseAgreement) {
        leaseAgreementService.saveOrUpdateLease(leaseAgreement);
        return Result.ok();
    }

    @Operation(summary = "根据条件分页查询租约列表")
    @GetMapping("page")
    public Result<IPage<AgreementVo>> page(@RequestParam long current, @RequestParam long size, AgreementQueryVo queryVo) {
        Page<AgreementVo> voPage=new Page<>(current,size);
        IPage<AgreementVo> agreementVoIPage=leaseAgreementService.getPage(voPage,queryVo);
        return Result.ok(agreementVoIPage);
    }

    @Operation(summary = "根据id查询租约信息")
    @GetMapping(name = "getById")
    public Result<AgreementVo> getById(@RequestParam Long id) {
        AgreementVo agreementVo=new AgreementVo();
        LeaseAgreement leaseAgreement = leaseAgreementService.getById(id);
        BeanUtils.copyProperties(leaseAgreement,agreementVo);
        ApartmentInfo apartmentInfo = apartmentInfoService.getById(id);
        RoomInfo roomInfo = roomInfoService.getById(leaseAgreement.getRoomId());
        PaymentType paymentType = paymentTypeService.getById(leaseAgreement.getPaymentTypeId());
        LeaseTerm leaseTerm = leaseTermService.getById(leaseAgreement.getLeaseTermId());
        agreementVo.setLeaseTerm(leaseTerm);
        agreementVo.setPaymentType(paymentType);
        agreementVo.setRoomInfo(roomInfo);
        agreementVo.setApartmentInfo(apartmentInfo);
        return Result.ok(agreementVo);
    }

    @Operation(summary = "根据id删除租约信息")
    @DeleteMapping("removeById")
    public Result removeById(@RequestParam Long id) {
        leaseAgreementService.removeById(id);
        return Result.ok();
    }

    @Operation(summary = "根据id更新租约状态")
    @PostMapping("updateStatusById")
    public Result updateStatusById(@RequestParam Long id, @RequestParam LeaseStatus status) {
        LambdaUpdateWrapper <LeaseAgreement> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(LeaseAgreement::getId,id);
        updateWrapper.set(LeaseAgreement::getStatus,status);
        leaseAgreementService.update(updateWrapper);
        return Result.ok();
    }

}

