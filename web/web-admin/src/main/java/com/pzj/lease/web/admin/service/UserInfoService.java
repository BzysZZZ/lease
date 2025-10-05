package com.pzj.lease.web.admin.service;

import com.pzj.lease.model.entity.CursorPageVo;
import com.pzj.lease.model.entity.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pzj.lease.web.admin.vo.user.UserInfoQueryVo;

/**
* @author liubo
* @description 针对表【user_info(用户信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface UserInfoService extends IService<UserInfo> {

    CursorPageVo<UserInfo> cursorPage(long size, long lastId, UserInfoQueryVo queryVo);
}
