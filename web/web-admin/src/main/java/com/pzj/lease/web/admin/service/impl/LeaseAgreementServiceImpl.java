package com.pzj.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.model.entity.LeaseAgreement;
import com.pzj.lease.web.admin.mapper.LeaseAgreementMapper;
import com.pzj.lease.web.admin.service.LeaseAgreementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzj.lease.web.admin.vo.agreement.AgreementQueryVo;
import com.pzj.lease.web.admin.vo.agreement.AgreementVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author liubo
 * @description 针对表【lease_agreement(租约信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class LeaseAgreementServiceImpl extends ServiceImpl<LeaseAgreementMapper, LeaseAgreement>
        implements LeaseAgreementService {

    @Autowired
    private LeaseAgreementMapper leaseAgreementMapper;

    public void saveOrUpdateLease(LeaseAgreement leaseAgreement){
        super.saveOrUpdate(leaseAgreement);
    }

    public IPage<AgreementVo> getPage(Page<AgreementVo> voPage, AgreementQueryVo queryVo){
        return leaseAgreementMapper.getPage(voPage,queryVo);
    }
}




