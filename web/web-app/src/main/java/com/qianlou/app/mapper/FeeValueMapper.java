package com.qianlou.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.app.vo.fee.FeeValueVo;
import com.qianlou.model.entity.FeeValue;

import java.util.List;

public interface FeeValueMapper extends BaseMapper<FeeValue> {

    List<FeeValueVo> selectListByApartmentId(Long apartmentId);
}




