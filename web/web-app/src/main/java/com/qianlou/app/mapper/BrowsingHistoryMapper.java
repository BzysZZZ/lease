package com.qianlou.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qianlou.app.vo.history.HistoryItemVo;
import com.pzj.lease.model.entity.BrowsingHistory;

public interface BrowsingHistoryMapper extends BaseMapper<BrowsingHistory> {

    IPage<HistoryItemVo> pageItem(IPage<HistoryItemVo> page, Long userId);

    IPage<HistoryItemVo> pageHistoryItemByUserId(IPage<HistoryItemVo> page, Long userId);

}




