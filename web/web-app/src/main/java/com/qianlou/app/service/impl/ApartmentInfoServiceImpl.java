package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.*;
import com.qianlou.app.service.ApartmentInfoService;
import com.qianlou.app.vo.apartment.ApartmentDetailVo;
import com.qianlou.app.vo.apartment.ApartmentItemVo;
import com.qianlou.app.vo.graph.GraphVo;
import com.qianlou.model.entity.ApartmentInfo;
import com.qianlou.model.entity.FacilityInfo;
import com.qianlou.model.entity.LabelInfo;
import com.qianlou.model.enums.ItemType;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;

    @Resource
    private GraphInfoMapper graphInfoMapper;

    @Resource
    private LabelInfoMapper labelInfoMapper;

    @Resource
    private FacilityInfoMapper facilityInfoMapper;

    @Resource
    private RoomInfoMapper roomInfoMapper;


    @Override
    public ApartmentItemVo selectApartmentItemVoById(Long id) {
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(id);

        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);

        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);

        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        ApartmentItemVo apartmentItemVo = new ApartmentItemVo();

        BeanUtils.copyProperties(apartmentInfo, apartmentItemVo);
        apartmentItemVo.setGraphVoList(graphVoList);
        apartmentItemVo.setLabelInfoList(labelInfoList);
        apartmentItemVo.setMinRent(minRent);

        return apartmentItemVo;
    }

    @Override
    public ApartmentDetailVo getApartmentDetailById(Long id) {
        //1.查询ApartmentInfo
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(id);
        if (apartmentInfo == null) {
            return null;
        }
        //2.查询GraphInfo
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, id);

        //3.查询LabelInfo
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByApartmentId(id);

        //4.查询FacilityInfo
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByApartmentId(id);

        //5.查询公寓最低房租
        BigDecimal minRent = roomInfoMapper.selectMinRentByApartmentId(id);

        ApartmentDetailVo appApartmentDetailVo = new ApartmentDetailVo();

        BeanUtils.copyProperties(apartmentInfo, appApartmentDetailVo);
        appApartmentDetailVo.setIsDelete(apartmentInfo.getIsDeleted() == 1);
        appApartmentDetailVo.setGraphVoList(graphVoList);
        appApartmentDetailVo.setLabelInfoList(labelInfoList);
        appApartmentDetailVo.setFacilityInfoList(facilityInfoList);
        appApartmentDetailVo.setMinRent(minRent);
        return appApartmentDetailVo;
    }

    @Override
    public ApartmentItemVo getApartmentItemVoById(Long apartmentId) {
        return apartmentInfoMapper.selectApartmentItemVoById(apartmentId);
    }
}




