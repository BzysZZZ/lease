package com.qianlou.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.app.vo.attr.AttrValueVo;
import com.pzj.lease.model.entity.AttrValue;

import java.util.List;

public interface AttrValueMapper extends BaseMapper<AttrValue> {

    List<AttrValueVo> selectListByRoomId(Long id);
}




