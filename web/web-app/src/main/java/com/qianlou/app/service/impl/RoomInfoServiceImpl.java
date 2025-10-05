package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.*;
import com.qianlou.app.service.ApartmentInfoService;
import com.qianlou.app.service.BrowsingHistoryService;
import com.qianlou.app.service.RoomInfoService;
import com.qianlou.app.vo.apartment.ApartmentItemVo;
import com.qianlou.app.vo.attr.AttrValueVo;
import com.qianlou.app.vo.fee.FeeValueVo;
import com.qianlou.app.vo.graph.GraphVo;
import com.qianlou.app.vo.room.RoomDetailVo;
import com.qianlou.app.vo.room.RoomItemVo;
import com.qianlou.app.vo.room.RoomQueryVo;
import com.qianlou.common.context.LoginUserContext;
import com.qianlou.model.entity.*;
import com.qianlou.model.enums.ItemType;
import com.qianlou.model.enums.LeaseStatus;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Resource
    private RoomInfoMapper roomInfoMapper;

    @Resource
    private GraphInfoMapper graphInfoMapper;

    @Resource
    private AttrValueMapper attrValueMapper;

    @Resource
    private FacilityInfoMapper facilityInfoMapper;

    @Resource

    private LabelInfoMapper labelInfoMapper;
    @Resource

    private PaymentTypeMapper paymentTypeMapper;
    @Resource

    private LeaseTermMapper leaseTermMapper;

    @Resource
    private FeeValueMapper feeValueMapper;

    @Resource
    private LeaseAgreementMapper leaseAgreementMapper;

    @Resource
    private BrowsingHistoryService browsingHistoryService;

    @Resource
    private ApartmentInfoService apartmentInfoService;

    @Override
    public IPage<RoomItemVo> pageItemByApartmentId(Page<RoomItemVo> page, Long id) {
        return roomInfoMapper.pageItemByApartmentId(page, id);
    }

    @Override
    public IPage<RoomItemVo> getRoomItemByQueryForPage(Page<RoomItemVo> page, RoomQueryVo queryVo) {
        return roomInfoMapper.getRoomItemByQueryForPage(page, queryVo);
    }

    @Override
    public RoomDetailVo getRoomDetailById(Long id) {
        //1.查询RoomInfo
        RoomInfo roomInfo = roomInfoMapper.selectRoomById(id);
        if (roomInfo == null) {
            return null;
        }

        //2.查询所属公寓信息
        ApartmentItemVo apartmentItemVo = apartmentInfoService.selectApartmentItemVoById(roomInfo.getApartmentId());

        //3.查询graphInfoList
        List<GraphVo> graphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, id);

        //4.查询attrValueList
        List<AttrValueVo> attrvalueVoList = attrValueMapper.selectListByRoomId(id);

        //5.查询facilityInfoList
        List<FacilityInfo> facilityInfoList = facilityInfoMapper.selectListByRoomId(id);

        //6.查询labelInfoList
        List<LabelInfo> labelInfoList = labelInfoMapper.selectListByRoomId(id);

        //7.查询paymentTypeList
        List<PaymentType> paymentTypeList = paymentTypeMapper.selectListByRoomId(id);

        //8.查询leaseTermList
        List<LeaseTerm> leaseTermList = leaseTermMapper.selectListByRoomId(id);

        //9.查询费用项目信息
        List<FeeValueVo> feeValueVoList = feeValueMapper.selectListByApartmentId(roomInfo.getApartmentId());

        //10.查询房间入住状态
        LambdaQueryWrapper<LeaseAgreement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(LeaseAgreement::getRoomId, roomInfo.getId());
        queryWrapper.in(LeaseAgreement::getStatus, LeaseStatus.SIGNED, LeaseStatus.WITHDRAWING);
        Long singedCount = leaseAgreementMapper.selectCount(queryWrapper);

        RoomDetailVo appRoomDetailVo = new RoomDetailVo();
        BeanUtils.copyProperties(roomInfo, appRoomDetailVo);
        appRoomDetailVo.setIsDelete(roomInfo.getIsDeleted() == 1);
        appRoomDetailVo.setIsCheckIn(singedCount > 0);

        appRoomDetailVo.setApartmentItemVo(apartmentItemVo);
        appRoomDetailVo.setGraphVoList(graphVoList);
        appRoomDetailVo.setAttrValueVoList(attrvalueVoList);
        appRoomDetailVo.setFacilityInfoList(facilityInfoList);
        appRoomDetailVo.setLabelInfoList(labelInfoList);
        appRoomDetailVo.setPaymentTypeList(paymentTypeList);
        appRoomDetailVo.setFeeValueVoList(feeValueVoList);
        appRoomDetailVo.setLeaseTermList(leaseTermList);

        browsingHistoryService.saveBrowsingHistory(LoginUserContext.getLoginUser().getUserId(), id);
        return appRoomDetailVo;
    }
}




