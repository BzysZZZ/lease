package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.CityInfoMapper;
import com.qianlou.app.service.CityInfoService;
import com.qianlou.model.entity.CityInfo;
import org.springframework.stereotype.Service;

@Service
public class CityInfoServiceImpl extends ServiceImpl<CityInfoMapper, CityInfo>
    implements CityInfoService {

}




