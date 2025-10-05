package com.pzj.lease.web.admin.service;

import com.pzj.lease.model.entity.ApartmentInfo;
import com.pzj.lease.model.enums.ReleaseStatus;
import com.pzj.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.pzj.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.pzj.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.pzj.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liubo
* @description 针对表【apartment_info(公寓信息表)】的数据库操作Service
* @createDate 2023-07-24 15:48:00
*/
public interface ApartmentInfoService extends IService<ApartmentInfo> {

    void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo);

    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> apartmentItemVoPage, ApartmentQueryVo queryVo);

    public ApartmentDetailVo detail(Long id);

    public void removeApartmentById(Long id);

    public void updateReleaseStatusById(Long id, ReleaseStatus status);

    public List<ApartmentInfo> listInfoByDistrict(Long id);
}
