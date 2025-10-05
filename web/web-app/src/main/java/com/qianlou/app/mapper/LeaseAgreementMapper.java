package com.qianlou.app.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.app.vo.agreement.AgreementItemVo;
import com.qianlou.model.entity.LeaseAgreement;

import java.util.List;

public interface LeaseAgreementMapper extends BaseMapper<LeaseAgreement> {

    List<AgreementItemVo> listItem(String phone);

    List<AgreementItemVo> listAgreementItemByPhone(String phone);

}




