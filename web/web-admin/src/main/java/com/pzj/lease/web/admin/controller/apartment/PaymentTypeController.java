package com.pzj.lease.web.admin.controller.apartment;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pzj.lease.common.result.Result;
import com.pzj.lease.model.entity.PaymentType;
import com.pzj.lease.web.admin.service.PaymentTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;


@Tag(name = "支付方式管理")
@RequestMapping("/admin/payment")
@RestController
public class PaymentTypeController {

    @Autowired
    PaymentTypeService paymentTypeService;

    @Operation(summary = "查询全部支付方式列表")
    @GetMapping("list")
    public Result<List<PaymentType>> listPaymentType() {
        List<PaymentType> paymentTypeList = paymentTypeService.list();
        return Result.ok(paymentTypeList);
    }

    @Operation(summary = "保存或更新支付方式")
    @PostMapping("saveOrUpdate")
    public Result saveOrUpdatePaymentType(@RequestBody PaymentType paymentType) {
        //@RequsetBody注解会将请求体中的JSON串反序列化为对象
        paymentTypeService.saveOrUpdate(paymentType);
        return Result.ok();
    }

    @Operation(summary = "根据ID删除支付方式")
    @DeleteMapping("deleteById")
    public Result deletePaymentById(@RequestParam Long id) {
        paymentTypeService.removeById(id);
        return Result.ok();
    }

}















