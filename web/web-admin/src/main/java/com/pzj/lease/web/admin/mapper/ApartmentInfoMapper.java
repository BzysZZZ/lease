package com.pzj.lease.web.admin.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.model.entity.ApartmentInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pzj.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.pzj.lease.web.admin.vo.apartment.ApartmentQueryVo;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Mapper
* @createDate 2023-07-24 15:48:00
* @Entity com.pzj.lease.model.ApartmentInfo
*/
public interface ApartmentInfoMapper extends BaseMapper<ApartmentInfo> {
    IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> apartmentItemVoPage, ApartmentQueryVo queryVo);

}




