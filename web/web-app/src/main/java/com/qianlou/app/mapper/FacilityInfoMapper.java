package com.qianlou.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.model.entity.FacilityInfo;

import java.util.List;

public interface FacilityInfoMapper extends BaseMapper<FacilityInfo> {

    List<FacilityInfo> selectListByRoomId(Long id);

    List<FacilityInfo> selectListByApartmentId(Long id);
}




