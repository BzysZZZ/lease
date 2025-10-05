package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.PaymentTypeMapper;
import com.qianlou.app.service.PaymentTypeService;
import com.qianlou.model.entity.PaymentType;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentTypeServiceImpl extends ServiceImpl<PaymentTypeMapper, PaymentType>
    implements PaymentTypeService {

    @Resource
    private PaymentTypeMapper paymentTypeMapper;

    @Override
    public List<PaymentType> listByRoomId(Long id) {
        return paymentTypeMapper.selectListByRoomId(id);
    }
}




