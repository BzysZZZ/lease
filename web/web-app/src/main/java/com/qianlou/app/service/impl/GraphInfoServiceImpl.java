package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.GraphInfoMapper;
import com.qianlou.app.service.GraphInfoService;
import com.pzj.lease.model.entity.GraphInfo;
import org.springframework.stereotype.Service;

@Service
public class GraphInfoServiceImpl extends ServiceImpl<GraphInfoMapper, GraphInfo>
    implements GraphInfoService {

}




