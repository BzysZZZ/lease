package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.*;
import com.qianlou.app.service.LeaseAgreementService;
import com.qianlou.app.vo.agreement.AgreementDetailVo;
import com.qianlou.app.vo.agreement.AgreementItemVo;
import com.qianlou.app.vo.graph.GraphVo;
import com.qianlou.model.entity.*;
import com.qianlou.model.enums.ItemType;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Resource
    private LeaseAgreementMapper leaseAgreementMapper;

    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;

    @Resource
    private GraphInfoMapper graphInfoMapper;

    @Resource
    private RoomInfoMapper roomInfoMapper;

    @Resource
    private PaymentTypeMapper paymentTypeMapper;

    @Resource
    private LeaseTermMapper leaseTermMapper;

    @Override
    public List<AgreementItemVo> listAgreementItemByPhone(String phone) {
        return leaseAgreementMapper.listAgreementItemByPhone(phone);
    }

    @Override
    public AgreementDetailVo getAgreementDetailById(Long id) {
        //1.查询租约信息
        LeaseAgreement leaseAgreement = leaseAgreementMapper.selectById(id);
        if (leaseAgreement == null) {
            return null;
        }
        //2.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectApartmentById(leaseAgreement.getApartmentId());

        //3.查询房间信息
        RoomInfo roomInfo = roomInfoMapper.selectRoomById(leaseAgreement.getRoomId());

        //4.查询公寓图片
        List<GraphVo> apartmentGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT, leaseAgreement.getApartmentId());

        //5.查询房间图片
        List<GraphVo> roomGraphVoList = graphInfoMapper.selectListByItemTypeAndId(ItemType.ROOM, leaseAgreement.getRoomId());

        //6.查询租期信息
        LeaseTerm leaseTerm = leaseTermMapper.selectLeaseTermById(leaseAgreement.getLeaseTermId());

        //7.查询支付方式
        PaymentType paymentType = paymentTypeMapper.selectPaymentTypeById(leaseAgreement.getPaymentTypeId());

        AgreementDetailVo agreementDetailVo = new AgreementDetailVo();
        BeanUtils.copyProperties(leaseAgreement, agreementDetailVo);
        agreementDetailVo.setApartmentName(apartmentInfo.getName());
        agreementDetailVo.setRoomNumber(roomInfo.getRoomNumber());
        agreementDetailVo.setApartmentGraphVoList(apartmentGraphVoList);
        agreementDetailVo.setRoomGraphVoList(roomGraphVoList);
        agreementDetailVo.setPaymentTypeName(paymentType.getName());
        agreementDetailVo.setLeaseTermMonthCount(leaseTerm.getMonthCount());
        agreementDetailVo.setLeaseTermUnit(leaseTerm.getUnit());
        return agreementDetailVo;
    }
}




