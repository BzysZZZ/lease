package com.pzj.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.model.entity.ViewAppointment;
import com.pzj.lease.model.enums.AppointmentStatus;
import com.pzj.lease.web.admin.mapper.ViewAppointmentMapper;
import com.pzj.lease.web.admin.service.ViewAppointmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzj.lease.web.admin.vo.appointment.AppointmentQueryVo;
import com.pzj.lease.web.admin.vo.appointment.AppointmentVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【view_appointment(预约看房信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ViewAppointmentServiceImpl extends ServiceImpl<ViewAppointmentMapper, ViewAppointment>
        implements ViewAppointmentService {

    @Autowired
    private ViewAppointmentMapper viewAppointmentMapper;

    @Override
    public void updateStatusById(Long id, AppointmentStatus status){
        LambdaUpdateWrapper <ViewAppointment> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        lambdaUpdateWrapper.eq(ViewAppointment::getId,id).set(ViewAppointment::getAppointmentStatus,status);
        super.update(lambdaUpdateWrapper);
    }

    @Override
    public IPage<AppointmentVo> getPageByQuery(Page<AppointmentVo> appointmentVoIPage, AppointmentQueryVo queryVo){
        return viewAppointmentMapper.getPageByQuery(appointmentVoIPage,queryVo);
    }

}




