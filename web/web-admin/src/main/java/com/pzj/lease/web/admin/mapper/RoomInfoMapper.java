package com.pzj.lease.web.admin.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.model.entity.RoomInfo;
import com.pzj.lease.web.admin.vo.room.RoomItemVo;
import com.pzj.lease.web.admin.vo.room.RoomQueryVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.pzj.lease.web.admin.vo.room.RoomSubmitVo;

/**
* @author liubo
* @description 针对表【room_info(房间信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.pzj.lease.model.RoomInfo
*/
public interface RoomInfoMapper extends BaseMapper<RoomInfo> {

    IPage<RoomItemVo> getPageItem(Page<RoomItemVo> page, RoomQueryVo queryVo);
}




