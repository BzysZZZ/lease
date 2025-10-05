package com.pzj.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.model.entity.*;
import com.pzj.lease.model.enums.ItemType;
import com.pzj.lease.model.enums.ReleaseStatus;
import com.pzj.lease.web.admin.mapper.*;
import com.pzj.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzj.lease.web.admin.vo.attr.AttrValueVo;
import com.pzj.lease.web.admin.vo.graph.GraphVo;
import com.pzj.lease.web.admin.vo.room.RoomDetailVo;
import com.pzj.lease.web.admin.vo.room.RoomItemVo;
import com.pzj.lease.web.admin.vo.room.RoomQueryVo;
import com.pzj.lease.web.admin.vo.room.RoomSubmitVo;
import org.springdoc.core.parsers.ReturnTypeParser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【room_info(房间信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class RoomInfoServiceImpl extends ServiceImpl<RoomInfoMapper, RoomInfo>
        implements RoomInfoService {

    @Autowired
    private RoomLeaseTermService roomLeaseTermService;

    @Autowired
    private RoomPaymentTypeService roomPaymentTypeService;

    @Autowired
    private RoomAttrValueService roomAttrValueService;

    @Autowired
    private RoomLabelService roomLabelService;

    @Autowired
    private RoomFacilityService roomFacilityService;

    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private RoomInfoMapper roomInfoMapper;
    @Autowired
    private ApartmentInfoService apartmentInfoService;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private AttrValueMapper attrValueMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private PaymentTypeMapper paymentTypeMapper;

    @Autowired
    private LeaseTermMapper leaseTermMapper;



    public void saveOrUpdateRoom(RoomSubmitVo roomSubmitVo){
        //1.新建还是更新，更新要先删除
        Boolean isUpdate=(roomSubmitVo.getId()==null);
        //2.房间基本信息表
        super.saveOrUpdate(roomSubmitVo);
        if(isUpdate){
            //3.租期
            LambdaQueryWrapper<RoomLeaseTerm> roomLeaseTermLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId,roomSubmitVo.getId());
            roomLeaseTermService.remove(roomLeaseTermLambdaQueryWrapper);

            //4.支付方式
            LambdaQueryWrapper<RoomPaymentType> roomPaymentTypeLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId,roomSubmitVo.getId());
            roomPaymentTypeService.remove(roomPaymentTypeLambdaQueryWrapper);

            //5.属性值
            LambdaQueryWrapper<RoomAttrValue> roomAttrValueLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roomAttrValueLambdaQueryWrapper.eq(RoomAttrValue::getRoomId,roomSubmitVo.getId());
            roomAttrValueService.remove(roomAttrValueLambdaQueryWrapper);

            //6.标签
            LambdaQueryWrapper<RoomLabel> roomLabelLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roomLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId,roomSubmitVo.getId());
            roomLabelService.remove(roomLabelLambdaQueryWrapper);

            //7.配套
            LambdaQueryWrapper<RoomFacility> roomFacilityLambdaQueryWrapper = new LambdaQueryWrapper<>();
            roomFacilityLambdaQueryWrapper.eq(RoomFacility::getRoomId,roomSubmitVo.getId());
            roomFacilityService.remove(roomFacilityLambdaQueryWrapper);

            //8.图片
            LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType, ItemType.ROOM);
            graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemId,roomSubmitVo.getId());
            graphInfoService.remove(graphInfoLambdaQueryWrapper);

        }

        //3.租期
        List<Long> leaseTermIds = roomSubmitVo.getLeaseTermIds();
        List<RoomLeaseTerm> roomLeaseTerms = new ArrayList<>();
        if(!CollectionUtils.isEmpty(leaseTermIds)){
            for(long LeaseTermId:leaseTermIds){
                RoomLeaseTerm roomLeaseTerm = new RoomLeaseTerm();
                roomLeaseTerm.setId(LeaseTermId);
                roomLeaseTerm.setRoomId(roomSubmitVo.getId());
                roomLeaseTerms.add(roomLeaseTerm);
            }
        }
        roomLeaseTermService.saveOrUpdateBatch(roomLeaseTerms);

        //4.支付方式
        List<Long> paymentTypeIds = roomSubmitVo.getPaymentTypeIds();
        List<RoomPaymentType> roomPaymentTypeList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(paymentTypeIds)){
            for(long PaymentTypeId:paymentTypeIds){
                RoomPaymentType roomPaymentType = new RoomPaymentType();
                roomPaymentType.setId(PaymentTypeId);
                roomPaymentType.setRoomId(roomSubmitVo.getId());
                roomPaymentTypeList.add(roomPaymentType);
            }
        }
        roomPaymentTypeService.saveOrUpdateBatch(roomPaymentTypeList);

        //5.属性值
        List<Long> attrValueIds = roomSubmitVo.getAttrValueIds();
        List<RoomAttrValue> roomAttrValueList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(attrValueIds)){
            for(long AttrValueId:attrValueIds){
                RoomAttrValue roomAttrValue = new RoomAttrValue();
                roomAttrValue.setId(AttrValueId);
                roomAttrValue.setRoomId(roomSubmitVo.getId());
                roomAttrValueList.add(roomAttrValue);
            }
        }
        roomAttrValueService.saveOrUpdateBatch(roomAttrValueList);

        //6.标签
        List<Long> labelInfoIds = roomSubmitVo.getLabelInfoIds();
        ArrayList<RoomLabel> roomLabels = new ArrayList<>();
        if(!CollectionUtils.isEmpty(labelInfoIds)){
            for(long LabelInfoId:labelInfoIds){
                RoomLabel roomLabel = new RoomLabel();
                roomLabel.setId(LabelInfoId);
                roomLabel.setRoomId(roomSubmitVo.getId());
                roomLabels.add(roomLabel);
            }
        }
        roomLabelService.saveOrUpdateBatch(roomLabels);

        //7.配套
        List<Long> facilityInfoIds = roomSubmitVo.getFacilityInfoIds();
        List<RoomFacility> roomFacilityList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(facilityInfoIds)){
            for(long FacilityInfoId:facilityInfoIds){
                RoomFacility roomFacility = new RoomFacility();
                roomFacility.setId(FacilityInfoId);
                roomFacility.setRoomId(roomSubmitVo.getId());
                roomFacilityList.add(roomFacility);
            }
        }
        roomFacilityService.saveOrUpdateBatch(roomFacilityList);

        //8.图片
        List<GraphVo> graphVoList = roomSubmitVo.getGraphVoList();
        List<GraphInfo> graphInfoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(graphVoList)){
            for(GraphVo graphVo:graphVoList){
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setUrl(graphVo.getUrl());
                graphInfo.setName(graphVo.getName());
                graphInfo.setItemId(roomSubmitVo.getId());
                graphInfo.setItemType(ItemType.ROOM);
                graphInfoList.add(graphInfo);
            }
        }
        graphInfoService.saveOrUpdateBatch(graphInfoList);
    }

    @Override
    public IPage<RoomItemVo> getPageItem(Page<RoomItemVo> page, RoomQueryVo queryVo){
        return roomInfoMapper.getPageItem(page,queryVo);
    }

    @Override
    public RoomDetailVo getDetailById(Long id){
        RoomDetailVo roomDetailVo=new RoomDetailVo();
        RoomInfo roomInfo=roomInfoMapper.selectById(id);

        //1.所属公寓信息
        LambdaQueryWrapper<ApartmentInfo> apartmentInfoLambdaQueryWrapper=new LambdaQueryWrapper<>();
        apartmentInfoLambdaQueryWrapper.eq(ApartmentInfo::getId,roomInfo.getApartmentId());
        ApartmentInfo apartmentInfo = apartmentInfoService.getOne(apartmentInfoLambdaQueryWrapper);

        //2.图片列表
        List<GraphVo> graphVoList=graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM,id);

        //3.属性信息列表
        List<AttrValueVo> attrValueVoList =attrValueMapper.seletListByRoomId(id);

        //4.配套信息列表
        List<FacilityInfo> facilityInfoById = facilityInfoMapper.getFacilityInfoById(apartmentInfo.getId());

        //5.标签信息列表
        List<LabelInfo> labelInfoById = labelInfoMapper.getLabelInfoById(apartmentInfo.getId());

        //6.支付方式列表
        List<PaymentType> paymentTypes=paymentTypeMapper.getPaymentTpyeById(id);

        //7.可选租期列表
        List<LeaseTerm> leaseTerms=leaseTermMapper.getLeaseTermById(id);

        BeanUtils.copyProperties(roomInfo,roomDetailVo);
        roomDetailVo.setApartmentInfo(apartmentInfo);
        roomDetailVo.setGraphVoList(graphVoList);
        roomDetailVo.setAttrValueVoList(attrValueVoList);
        roomDetailVo.setFacilityInfoList(facilityInfoById);
        roomDetailVo.setLabelInfoList(labelInfoById);
        roomDetailVo.setPaymentTypeList(paymentTypes);
        roomDetailVo.setLeaseTermList(leaseTerms);
        return roomDetailVo;
    }

    public void removeRoomById(Long id){
        super.removeById(id);

        LambdaQueryWrapper<RoomLeaseTerm>  roomLeaseTermLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roomLeaseTermLambdaQueryWrapper.eq(RoomLeaseTerm::getRoomId,id);
        roomLeaseTermService.remove(roomLeaseTermLambdaQueryWrapper);

        LambdaQueryWrapper<RoomPaymentType>  roomPaymentTypeLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roomPaymentTypeLambdaQueryWrapper.eq(RoomPaymentType::getRoomId,id);
        roomPaymentTypeService.remove(roomPaymentTypeLambdaQueryWrapper);

        LambdaQueryWrapper<RoomAttrValue>  roomAttrValueLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roomAttrValueLambdaQueryWrapper.eq(RoomAttrValue::getRoomId,id);
        roomAttrValueService.remove(roomAttrValueLambdaQueryWrapper);

        LambdaQueryWrapper<RoomLabel>  roomLabelLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roomLabelLambdaQueryWrapper.eq(RoomLabel::getRoomId,id);
        roomLabelService.remove(roomLabelLambdaQueryWrapper);

        LambdaQueryWrapper<RoomFacility>  roomFacilityLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roomFacilityLambdaQueryWrapper.eq(RoomFacility::getRoomId,id);
        roomFacilityService.remove(roomFacilityLambdaQueryWrapper);

        LambdaQueryWrapper<GraphInfo>  graphInfoLambdaQueryWrapper=new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getId,id);
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType,ItemType.ROOM);
        graphInfoService.remove(graphInfoLambdaQueryWrapper);
    }

    public void updateReleaseStatusById(Long id, ReleaseStatus status){
        LambdaUpdateWrapper<RoomInfo> roomInfoLambdaUpdateWrapper=new LambdaUpdateWrapper<>();
        roomInfoLambdaUpdateWrapper.eq(RoomInfo::getId,id).set(RoomInfo::getIsRelease,status);
        super.update(roomInfoLambdaUpdateWrapper);
    }
    public List<RoomInfo> listBasicByApartmentId(Long id){
        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper=new LambdaQueryWrapper<>();
        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId,id);
        return roomInfoMapper.selectList(roomInfoLambdaQueryWrapper);
    }

}




