package com.qianlou.app.vo.appointment;


import com.qianlou.app.vo.apartment.ApartmentItemVo;
import com.pzj.lease.model.entity.ViewAppointment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "APP端预约看房详情")
public class AppointmentDetailVo extends ViewAppointment {

    @Schema(description = "公寓基本信息")
    private ApartmentItemVo apartmentItemVo;
}
