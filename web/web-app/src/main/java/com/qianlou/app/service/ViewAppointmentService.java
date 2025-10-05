package com.qianlou.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qianlou.app.vo.appointment.AppointmentDetailVo;
import com.qianlou.app.vo.appointment.AppointmentItemVo;
import com.qianlou.model.entity.ViewAppointment;

import java.util.List;

public interface ViewAppointmentService extends IService<ViewAppointment> {

    AppointmentDetailVo getDetailById(Long id);

    List<AppointmentItemVo> listAppointmentItemByUserId(Long userId);

    AppointmentDetailVo getAppointmentDetailVoById(Long id);

}
