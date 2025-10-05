package com.qianlou.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.app.vo.apartment.ApartmentItemVo;
import com.pzj.lease.model.entity.ApartmentInfo;

import java.math.BigDecimal;

public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {

    ApartmentItemVo selectApartmentItemVoById(Long apartmentId);

    ApartmentInfo selectApartmentById(Long id);

    BigDecimal selectMinRentByApartmentId(Long id);
}




