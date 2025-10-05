package com.qianlou.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.app.vo.graph.GraphVo;
import com.pzj.lease.model.entity.GraphInfo;
import com.pzj.lease.model.enums.ItemType;

import java.util.List;

public interface GraphInfoMapper extends BaseMapper<GraphInfo> {

    List<GraphVo> selectListByItemTypeAndId(ItemType itemType, Long id);
}




