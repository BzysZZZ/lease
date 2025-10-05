package com.qianlou.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.app.vo.appointment.AppointmentItemVo;
import com.qianlou.model.entity.ViewAppointment;

import java.util.List;

public interface ViewAppointmentMapper extends BaseMapper<ViewAppointment> {

    List<AppointmentItemVo> listAppointmentItemByUserId(Long userId);

}




