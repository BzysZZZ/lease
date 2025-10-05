package com.qianlou.app.vo.apartment;


import com.qianlou.app.vo.graph.GraphVo;
import com.pzj.lease.model.entity.ApartmentInfo;
import com.pzj.lease.model.entity.LabelInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Schema(description = "App端公寓信息")
public class ApartmentItemVo extends ApartmentInfo {

    private List<LabelInfo> labelInfoList;

    private List<GraphVo> graphVoList;

    private BigDecimal minRent;
}
