package com.pzj.lease.web.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pzj.lease.common.exception.LeaseException;
import com.pzj.lease.common.result.ResultCodeEnum;
import com.pzj.lease.model.entity.*;
import com.pzj.lease.model.enums.ItemType;
import com.pzj.lease.model.enums.ReleaseStatus;
import com.pzj.lease.web.admin.mapper.*;
import com.pzj.lease.web.admin.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pzj.lease.web.admin.vo.apartment.ApartmentDetailVo;
import com.pzj.lease.web.admin.vo.apartment.ApartmentItemVo;
import com.pzj.lease.web.admin.vo.apartment.ApartmentQueryVo;
import com.pzj.lease.web.admin.vo.apartment.ApartmentSubmitVo;
import com.pzj.lease.web.admin.vo.fee.FeeValueVo;
import com.pzj.lease.web.admin.vo.graph.GraphVo;
import io.minio.messages.Item;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liubo
 * @description 针对表【apartment_info(公寓信息表)】的数据库操作Service实现
 * @createDate 2023-07-24 15:48:00
 */
@Service
public class ApartmentInfoServiceImpl extends ServiceImpl<ApartmentInfoMapper, ApartmentInfo>
        implements ApartmentInfoService {

    //通用Mapper中没有批量插入的方法而通用Service中有，因此注入Service
    @Autowired
    private GraphInfoService graphInfoService;

    @Autowired
    private ApartmentFacilityService apartmentFacilityService;

    @Autowired
    private ApartmentLabelService apartmentLabelService;

    @Autowired
    private ApartmentFeeValueService apartmentFeeValueService;

    @Autowired
    private ApartmentInfoMapper apartmentInfoMapper;

    @Autowired
    private FacilityInfoMapper facilityInfoMapper;

    @Autowired
    private LabelInfoMapper labelInfoMapper;

    @Autowired
    private GraphInfoMapper graphInfoMapper;

    @Autowired
    private FeeValueMapper feeValueMapper;

    @Autowired
    private RoomInfoMapper roomInfoMapper;

    @Override
    public void saveOrUpdateApartment(ApartmentSubmitVo apartmentSubmitVo) {
        boolean isUpdate=apartmentSubmitVo.getId()!=null;
        super.saveOrUpdate(apartmentSubmitVo);  //apartmentSubmitVo继承了ApartmentInfo对象，直接传可以处理Info对象里的信息，
        //这里要注意先处理公寓信息实体，因为其他表依赖于公寓ID，要先插入公寓基本信息创建一个公寓ID。
        if(isUpdate){
            //1.删除图片列表--操作图片信息表：①所属对象类型是公寓②所属对象id是公寓id
            LambdaQueryWrapper<GraphInfo> graphQueryWrapper = new LambdaQueryWrapper<>();
            graphQueryWrapper.eq(GraphInfo::getItemType,ItemType.APARTMENT);
            graphQueryWrapper.eq(GraphInfo::getItemId,apartmentSubmitVo.getId());
            graphInfoService.remove(graphQueryWrapper);

            //2.删除配套列表
            LambdaQueryWrapper<ApartmentFacility> apartmentQueryWrapper = new LambdaQueryWrapper<>();
            apartmentQueryWrapper.eq(ApartmentFacility::getApartmentId,apartmentSubmitVo.getId());
            apartmentFacilityService.remove(apartmentQueryWrapper);

            //3.删除标签列表
            LambdaQueryWrapper<ApartmentLabel> apartmentLabelQueryWrapper = new LambdaQueryWrapper<>();
            apartmentLabelQueryWrapper.eq(ApartmentLabel::getApartmentId,apartmentSubmitVo.getId());
            apartmentLabelService.remove(apartmentLabelQueryWrapper);

            //4.删除杂费列表
            LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueQueryWrapper = new LambdaQueryWrapper<>();
            apartmentFeeValueQueryWrapper.eq(ApartmentFeeValue::getApartmentId,apartmentSubmitVo.getId());
            apartmentFeeValueService.remove(apartmentFeeValueQueryWrapper);
        }
        //1.插入图片列表
        List<GraphVo> graphVoList = apartmentSubmitVo.getGraphVoList();
        if(!CollectionUtils.isEmpty(graphVoList)){
            ArrayList<GraphInfo> graphInfos = new ArrayList<>();
            for(GraphVo graphVo:graphVoList){
                GraphInfo graphInfo = new GraphInfo();
                graphInfo.setItemType(ItemType.APARTMENT);
                graphInfo.setItemId(apartmentSubmitVo.getId());
                graphInfo.setName(graphInfo.getName());
                graphInfo.setUrl(graphInfo.getUrl());
                graphInfos.add(graphInfo);
            }
            graphInfoService.saveBatch(graphInfos);
        }

        //2.插入配套列表
        List<Long> facilityInfoIds = apartmentSubmitVo.getFacilityInfoIds();
        if(!CollectionUtils.isEmpty(facilityInfoIds)){
            ArrayList<ApartmentFacility>  apartmentFacilities = new ArrayList<>();
            for(Long facilityId:facilityInfoIds){
                ApartmentFacility apartmentFacility=new ApartmentFacility();
                apartmentFacility.setApartmentId(apartmentSubmitVo.getId());
                apartmentFacility.setFacilityId(facilityId);
                apartmentFacilities.add(apartmentFacility);
            }
            apartmentFacilityService.saveBatch(apartmentFacilities);
        }
        
        //3.插入标签列表
        List<Long> labelIds = apartmentSubmitVo.getLabelIds();
        if(!CollectionUtils.isEmpty(labelIds)){
            ArrayList<ApartmentLabel> apartmentLabels = new ArrayList<>();
            for(Long labelId:labelIds){
                ApartmentLabel apartmentLabel = new ApartmentLabel();
                apartmentLabel.setApartmentId(apartmentSubmitVo.getId());
                apartmentLabel.setLabelId(labelId);
                apartmentLabels.add(apartmentLabel);
            }
            apartmentLabelService.saveBatch(apartmentLabels);
        }
        
        //4.插入杂费列表
        List<Long> feeValueIds = apartmentSubmitVo.getFeeValueIds();
        if(!CollectionUtils.isEmpty(feeValueIds)){
            ArrayList<ApartmentFeeValue> apartmentFeeValues = new ArrayList<>();
            for(Long feeValueId:feeValueIds){
                ApartmentFeeValue apartmentFeeValue = new ApartmentFeeValue();
                apartmentFeeValue.setApartmentId(apartmentSubmitVo.getId());
                apartmentFeeValue.setFeeValueId(feeValueId);
                apartmentFeeValues.add(apartmentFeeValue);
            }
            apartmentFeeValueService.saveBatch(apartmentFeeValues);
        }


        //而剩下的公寓配套，杂费，标签图片不会处理。
        //修改信息时前端传入的是修改之后的图片列表而不是修改了哪些，因此一般要先查出来原本的图片，然后对修改后的图片进行对比，
        // 或者将所有图片都删了一次性插入，新增和修改的逻辑不一样，因此需要区分。

    }

    @Override
    public IPage<ApartmentItemVo> pageItem(Page<ApartmentItemVo> apartmentItemVoPage, ApartmentQueryVo queryVo){
        return apartmentInfoMapper.pageItem(apartmentItemVoPage,queryVo);
    }

    @Override
    public ApartmentDetailVo detail(Long id){
        //1.查询公寓信息
        ApartmentInfo apartmentInfo = apartmentInfoMapper.selectById(id);

        //2.查询图片列表
        /*
        LambdaQueryWrapper<GraphInfo> graphInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getId,id);
        graphInfoLambdaQueryWrapper.eq(GraphInfo::getItemType,ItemType.APARTMENT);
        List<GraphInfo> graphInfoList = graphInfoService.list(graphInfoLambdaQueryWrapper);
        List<GraphVo> graphVoList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(graphInfoList)){
            for(GraphInfo graphInfo:graphInfoList){
                GraphVo graphVo = new GraphVo();
                graphVo.setName(graphInfo.getName());
                graphVo.setUrl(graphInfo.getUrl());
                graphVoList.add(graphVo);
            }
        }
        */
        List<GraphVo> graphVoList= graphInfoMapper.selectListByItemTypeAndId(ItemType.APARTMENT,id);

        //3.查询配套列表
        List<FacilityInfo> facilityInfoList=facilityInfoMapper.getFacilityInfoById(id);

        //4.查询标签列表
        List<LabelInfo> labelInfoList=labelInfoMapper.getLabelInfoById(id);

        //5.查询杂费列表
        List<FeeValueVo> feeValueVoList=feeValueMapper.getFeeValueById(id);

        //6.组装
        ApartmentDetailVo apartmentDetailVo = new ApartmentDetailVo();
        BeanUtils.copyProperties(apartmentInfo,apartmentDetailVo);
        apartmentDetailVo.setGraphVoList(graphVoList);
        apartmentDetailVo.setFacilityInfoList(facilityInfoList);
        apartmentDetailVo.setLabelInfoList(labelInfoList);
        apartmentDetailVo.setFeeValueVoList(feeValueVoList);

        return apartmentDetailVo;

    }

    public void removeApartmentById(Long id){
        LambdaQueryWrapper<RoomInfo> roomInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        roomInfoLambdaQueryWrapper.eq(RoomInfo::getApartmentId, id);
        Long cnt=roomInfoMapper.selectCount(roomInfoLambdaQueryWrapper);

        if(cnt>0){
            //终止删除，并响应提示信息
            throw new LeaseException(ResultCodeEnum.ADMIN_APARTMENT_DELETE_ERROR);
        }

        super.removeById(id);

        LambdaQueryWrapper<GraphInfo> graphInfoQueryWrapper = new LambdaQueryWrapper<>();
        graphInfoQueryWrapper.eq(GraphInfo::getItemType,ItemType.APARTMENT);
        graphInfoQueryWrapper.eq(GraphInfo::getItemId,id);
        graphInfoService.remove(graphInfoQueryWrapper);

        LambdaQueryWrapper<ApartmentFacility> apartmentFacilityQueryWrapper = new LambdaQueryWrapper<>();
        apartmentFacilityQueryWrapper.eq(ApartmentFacility::getApartmentId,id);
        apartmentFacilityService.remove(apartmentFacilityQueryWrapper);

        LambdaQueryWrapper<ApartmentLabel> apartmentLabelQueryWrapper = new LambdaQueryWrapper<>();
        apartmentLabelQueryWrapper.eq(ApartmentLabel::getApartmentId,id);
        apartmentLabelService.remove(apartmentLabelQueryWrapper);

        LambdaQueryWrapper<ApartmentFeeValue> apartmentFeeValueQueryWrapper = new LambdaQueryWrapper<>();
        apartmentFeeValueQueryWrapper.eq(ApartmentFeeValue::getApartmentId,id);
        apartmentFeeValueService.remove(apartmentFeeValueQueryWrapper);
    }

    public void updateReleaseStatusById(Long id, ReleaseStatus status){
        UpdateWrapper<ApartmentInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("is_release", status);  // 只更新这一个字段
        super.update(updateWrapper);
    }

    public List<ApartmentInfo> listInfoByDistrict(Long id){
        LambdaQueryWrapper<ApartmentInfo> apartmentInfoLambdaQueryWrapper = new LambdaQueryWrapper<>();
        apartmentInfoLambdaQueryWrapper.eq(ApartmentInfo::getDistrictId,id);
        return super.list(apartmentInfoLambdaQueryWrapper);
    }


}




