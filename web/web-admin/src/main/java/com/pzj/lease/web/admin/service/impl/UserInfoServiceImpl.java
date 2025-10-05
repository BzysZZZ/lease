package com.pzj.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzj.lease.model.entity.CursorPageVo;
import com.pzj.lease.model.entity.UserInfo;
import com.pzj.lease.web.admin.service.UserInfoService;
import com.pzj.lease.web.admin.mapper.UserInfoMapper;
import com.pzj.lease.web.admin.vo.user.UserInfoQueryVo;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liubo
* @description 针对表【user_info(用户信息表)】的数据库操作Service实现
* @createDate 2023-07-24 15:48:00
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

    @Autowired
    private UserInfoMapper userInfoMapper;

    public CursorPageVo<UserInfo> cursorPage(long size, long lastId, UserInfoQueryVo queryVo){
        LambdaQueryWrapper<UserInfo> lqw=new LambdaQueryWrapper<>();
        lqw.eq(UserInfo::getIsDeleted,0)
                .gt(lastId>0,UserInfo::getId,lastId)
                .like(queryVo.getPhone()!=null,UserInfo::getPhone,queryVo.getPhone())
                .eq(queryVo.getStatus()!=null,UserInfo::getStatus,queryVo.getStatus())
                .orderByAsc(UserInfo::getId)
                .last("LIMIT "+size);

        List<UserInfo> userInfoList=userInfoMapper.selectList(lqw);
        return new CursorPageVo<>(userInfoList,size);
    }


}




