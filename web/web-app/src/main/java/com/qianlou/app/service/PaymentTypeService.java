package com.qianlou.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qianlou.model.entity.PaymentType;

import java.util.List;

public interface PaymentTypeService extends IService<PaymentType> {

    List<PaymentType> listByRoomId(Long id);
}
