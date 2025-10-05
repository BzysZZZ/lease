package com.pzj.lease.web.admin.mapper;

import com.pzj.lease.model.entity.PaymentType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author liubo
 * @description 针对表【payment_type(支付方式表)】的数据库操作Mapper
 * @createDate 2023-07-24 15:48:00
 * @Entity com.pzj.lease.model.PaymentType
 */
public interface PaymentTypeMapper extends BaseMapper<PaymentType> {

    List<PaymentType> getPaymentTpyeById(Long id);
}




