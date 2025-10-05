package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.ApartmentInfoMapper;
import com.qianlou.app.mapper.ViewAppointmentMapper;
import com.qianlou.app.service.ApartmentInfoService;
import com.qianlou.app.service.ViewAppointmentService;
import com.qianlou.app.vo.apartment.ApartmentItemVo;
import com.qianlou.app.vo.appointment.AppointmentDetailVo;
import com.qianlou.app.vo.appointment.AppointmentItemVo;
import com.qianlou.model.entity.ViewAppointment;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Resource
    private ViewAppointmentMapper viewAppointmentMapper;

    @Resource
    private ApartmentInfoMapper apartmentInfoMapper;

    @Resource
    private ApartmentInfoService apartmentInfoService;

    @Override
    public AppointmentDetailVo getDetailById(Long id) {

        ViewAppointment viewAppointment = viewAppointmentMapper.selectById(id);

        ApartmentItemVo apartmentItemVo = apartmentInfoMapper.selectApartmentItemVoById(viewAppointment.getApartmentId());

        AppointmentDetailVo appointmentDetailVo = new AppointmentDetailVo();
        BeanUtils.copyProperties(viewAppointment,appointmentDetailVo);

        appointmentDetailVo.setApartmentItemVo(apartmentItemVo);

        return appointmentDetailVo;
    }

    @Override
    public List<AppointmentItemVo> listAppointmentItemByUserId(Long userId) {
        return viewAppointmentMapper.listAppointmentItemByUserId(userId);
    }

    @Override
    public AppointmentDetailVo getAppointmentDetailVoById(Long id) {
        ViewAppointment viewAppointment = viewAppointmentMapper.selectById(id);
        if (viewAppointment == null) {
            return null;
        }
        ApartmentItemVo apartmentItemVo = apartmentInfoService.getApartmentItemVoById(viewAppointment.getApartmentId());
        AppointmentDetailVo agreementDetailVo = new AppointmentDetailVo();
        BeanUtils.copyProperties(viewAppointment, agreementDetailVo);
        agreementDetailVo.setApartmentItemVo(apartmentItemVo);
        return agreementDetailVo;
    }
}




