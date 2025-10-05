package com.qianlou.app.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qianlou.model.entity.LeaseTerm;

import java.util.List;

public interface LeaseTermMapper extends BaseMapper<LeaseTerm> {

    List<LeaseTerm> selectListByRoomId(Long id);

    LeaseTerm selectLeaseTermById(Long leaseTermId);
}




