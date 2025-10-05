package com.qianlou.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzj.lease.model.entity.LabelInfo;

import java.util.List;

public interface LabelInfoMapper extends BaseMapper<LabelInfo> {

    List<LabelInfo> selectListByRoomId(Long id);

    List<LabelInfo> selectListByApartmentId(Long id);
}




