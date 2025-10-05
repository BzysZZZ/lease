package com.qianlou.app.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qianlou.model.entity.LeaseTerm;

import java.util.List;

public interface LeaseTermService extends IService<LeaseTerm> {

    List<LeaseTerm> listByRoomId(Long id);
}
