package com.qianlou.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qianlou.app.vo.agreement.AgreementDetailVo;
import com.qianlou.app.vo.agreement.AgreementItemVo;
import com.qianlou.model.entity.LeaseAgreement;

import java.util.List;

public interface LeaseAgreementService extends IService<LeaseAgreement> {

    List<AgreementItemVo> listAgreementItemByPhone(String username);

    AgreementDetailVo getAgreementDetailById(Long id);
}
