package com.qianlou.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qianlou.app.vo.room.RoomItemVo;
import com.qianlou.app.vo.room.RoomQueryVo;
import com.qianlou.model.entity.RoomInfo;

import java.math.BigDecimal;

public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    IPage<RoomItemVo> getRoomItemByQueryForPage(IPage<RoomItemVo> page, RoomQueryVo queryVo);

    RoomInfo selectRoomById(Long id);

    IPage<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> page, Long id);


    BigDecimal selectMinRentByApartmentId(Long id);
}