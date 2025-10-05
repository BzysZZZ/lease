package com.qianlou.app.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qianlou.app.vo.history.HistoryItemVo;
import com.pzj.lease.model.entity.BrowsingHistory;

public interface BrowsingHistoryService extends IService<BrowsingHistory> {

    void saveBrowsingHistory(Long userId, Long id);

    IPage<HistoryItemVo> pageHistoryItemByUserId(IPage<HistoryItemVo> page, Long userId);

}
