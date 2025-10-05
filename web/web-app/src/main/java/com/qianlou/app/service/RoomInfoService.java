package com.qianlou.app.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianlou.app.vo.room.RoomDetailVo;
import com.qianlou.app.vo.room.RoomItemVo;
import com.qianlou.app.vo.room.RoomQueryVo;
import com.qianlou.model.entity.RoomInfo;

public interface RoomInfoService extends IService<RoomInfo> {

    IPage<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> page, Long id);

    IPage<RoomItemVo> getRoomItemByQueryForPage(Page<RoomItemVo> page, RoomQueryVo queryVo);

    RoomDetailVo getRoomDetailById(Long id);
}
