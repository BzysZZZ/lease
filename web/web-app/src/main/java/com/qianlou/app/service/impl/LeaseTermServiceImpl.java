package com.qianlou.app.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qianlou.app.mapper.LeaseTermMapper;
import com.qianlou.app.service.LeaseTermService;
import com.qianlou.model.entity.LeaseTerm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaseTermServiceImpl extends ServiceImpl<LeaseTermMapper, LeaseTerm>
    implements LeaseTermService {

    @Autowired
    private LeaseTermMapper leaseTermMapper;

    @Override
    public List<LeaseTerm> listByRoomId(Long id) {
        return leaseTermMapper.selectListByRoomId(id);
    }
}




