package com.qianlou.app.controller.appointment;


import com.qianlou.app.service.ViewAppointmentService;
import com.qianlou.app.vo.appointment.AppointmentDetailVo;
import com.qianlou.app.vo.appointment.AppointmentItemVo;
import com.pzj.lease.common.annotation.CheckPermission;
import com.pzj.lease.common.context.LoginUserContext;
import com.pzj.lease.common.result.Result;
import com.pzj.lease.model.entity.ViewAppointment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "看房预约信息")
@RestController
@RequestMapping("/app/appointment")
public class ViewAppointmentController {

    @Resource
    private ViewAppointmentService viewAppointmentService;

    @CheckPermission
    @Operation(summary = "保存或更新看房预约")
    @PostMapping("/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody ViewAppointment viewAppointment) {
        viewAppointment.setUserId(LoginUserContext.getLoginUser().getUserId());
        viewAppointmentService.saveOrUpdate(viewAppointment);
        return Result.ok();
    }

    @Operation(summary = "查询个人预约看房列表")
    @GetMapping("/listItem")
    public Result<List<AppointmentItemVo>> listItem() {
        List<AppointmentItemVo> list = viewAppointmentService.listAppointmentItemByUserId(LoginUserContext.getLoginUser().getUserId());
        return Result.ok(list);
    }


    @GetMapping("/getDetailById")
    @Operation(summary = "根据ID查询预约详情信息")
    public Result<AppointmentDetailVo> getDetailById(Long id) {
        AppointmentDetailVo detail = viewAppointmentService.getAppointmentDetailVoById(id);
        return Result.ok(detail);
    }

}

