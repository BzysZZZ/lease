package com.qianlou.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.model.entity.PaymentType;

import java.util.List;

public interface PaymentTypeMapper extends BaseMapper<PaymentType> {

    List<PaymentType> selectListByRoomId(Long id);

    PaymentType selectPaymentTypeById(Long paymentTypeId);

}




