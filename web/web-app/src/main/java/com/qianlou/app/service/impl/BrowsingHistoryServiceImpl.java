package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.BrowsingHistoryMapper;
import com.qianlou.app.service.BrowsingHistoryService;
import com.qianlou.app.vo.history.HistoryItemVo;
import com.pzj.lease.model.entity.BrowsingHistory;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class BrowsingHistoryServiceImpl extends ServiceImpl<BrowsingHistoryMapper, BrowsingHistory>
        implements BrowsingHistoryService {

    @Resource
    private BrowsingHistoryMapper browsingHistoryMapper;

    @Override
    @Async
    public void saveBrowsingHistory(Long userId, Long roomId) {
        log.info("保存浏览历史");
        BrowsingHistory browsingHistory = new BrowsingHistory();
        browsingHistory.setUserId(userId);
        browsingHistory.setRoomId(roomId);
        browsingHistory.setBrowseTime(new Date());

        LambdaQueryWrapper<BrowsingHistory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(BrowsingHistory::getUserId, userId);
        queryWrapper.eq(BrowsingHistory::getRoomId, roomId);
        Long count = browsingHistoryMapper.selectCount(queryWrapper);
        if (count > 0) {
            LambdaUpdateWrapper<BrowsingHistory> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.eq(BrowsingHistory::getUserId, userId);
            updateWrapper.eq(BrowsingHistory::getRoomId, roomId);
            browsingHistoryMapper.update(browsingHistory, updateWrapper);
        } else {
            this.save(browsingHistory);
        }
    }

    @Override
    public IPage<HistoryItemVo> pageHistoryItemByUserId(IPage<HistoryItemVo> page, Long userId) {
        return browsingHistoryMapper.pageItem(page, userId);
    }
}




