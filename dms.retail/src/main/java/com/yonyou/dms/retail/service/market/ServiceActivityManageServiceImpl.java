
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : ServiceActivityManageServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年5月16日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月16日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.retail.service.market;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtActivityAddPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityVehiclePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.retail.domains.DTO.market.ServiceActivityManageDTO;

/**
* TODO description
* @author dingchaoyu
* @date 2017年5月16日
*/
@SuppressWarnings("rawtypes")
@Service
public class ServiceActivityManageServiceImpl implements ServiceActivityManageService{

    @Override
    public PageInfoDto query(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT '' AS IS_SELECT,IS_RECALL_ACTIVITY,IS_REPEAT_ATTEND, IS_PART_ACTIVITY,");
        sql.append(" DEALER_CODE, ACTIVITY_CODE, ACTIVITY_NAME, ACTIVITY_TYPE,ACTIVITY_KIND, BEGIN_DATE,");
        sql.append(" PARTS_IS_MODIFY,'' AS LABOUR_CODE,'' AS PART_CODE,END_DATE, RELEASE_DATE, RELEASE_TAG, LABOUR_AMOUNT, ADD_ITEM_AMOUNT, ");
        sql.append(" FROM_ENTITY, DOWN_TAG, DOWN_TIMESTAMP, GLOBAL_ACTIVITY_CODE, ACTIVITY_TITLE, ATTACHMENT_URL, ");
        sql.append(" REPAIR_PART_AMOUNT, ACTIVITY_AMOUNT, IS_VALID,VER,ACTIVITY_FIRST,OCCUR_CREDIT,");
        sql.append(" VEHICLE_PURPOSE, ACTIVITY_PROPERTY FROM ("+CommonConstants.VT_ACTIVITY+")  WHERE DEALER_CODE=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(map.get("activityCode"))) {
           sql.append(" and ACTIVITY_CODE like ?");
           list.add("%"+map.get("activityCode")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("activityName"))) {
            sql.append(" and ACTIVITY_NAME like ?");
            list.add("%"+map.get("activityName")+"%");
         }
        Utility.getDateCond("", "BEGIN_DATE", ((StringUtils.isNullOrEmpty(map.get("beginDateFrom")))?"" : map.get("beginDateFrom").toString().substring(0, 10)), 
                            ((StringUtils.isNullOrEmpty(map.get("beginDateTo"))?"" : map.get("beginDateTo").toString().substring(0, 10))));
        Utility.getDateCond("", "END_DATE", ((StringUtils.isNullOrEmpty(map.get("endDateFrom")))?"" : map.get("endDateFrom").toString().substring(0, 10)), 
                            ((StringUtils.isNullOrEmpty(map.get("endDateTo"))?"" : map.get("endDateTo").toString().substring(0, 10))));
        sql.append(" AND IS_VALID = 12781001");
        if (!StringUtils.isNullOrEmpty(map.get("releaseTag"))) {
            sql.append(" AND RELEASE_TAG =? ");
            list.add(map.get("releaseTag"));
        }
        sql.append(" order by CREATED_AT desc ");
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public String save(ServiceActivityManageDTO dto) throws ServiceBizException {
        List<Map> labourList = dto.getLabourList();
        List<Map> partList = dto.getPartList();
        List<Map> subjoinList = dto.getSubjoinList();
        List<Map> modelList = dto.getModelList();
        List<Map> vehicleList = dto.getVehicleList();
        double lab=0,par=0,sub=0;
        if (labourList != null && !labourList.isEmpty()) {
            for (Map map : labourList) {
                if (!StringUtils.isNullOrEmpty(map.get("LABOUR_AMOUNT_DISCOUNT"))) {
                    lab += Double.parseDouble(map.get("LABOUR_AMOUNT_DISCOUNT").toString());
                }
                TtActivityLabourPO itemPo = null;
                if ("A".equals(dto.getFag())) {
                    itemPo = new TtActivityLabourPO();
                    itemPo.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    itemPo.set("ACTIVITY_CODE",dto.getActivityCode());
                    itemPo.set("MODEL_LABOUR_CODE",map.get("MODEL_LABOUR_CODE"));
                    itemPo.set("LABOUR_CODE",map.get("LABOUR_CODE"));
                    itemPo.set("LABOUR_NAME",map.get("LABOUR_NAME"));
                    itemPo.set("REPAIR_TYPE_CODE",map.get("REPAIR_TYPE_CODE"));
                    if(!StringUtils.isNullOrEmpty(map.get("STD_LABOUR_HOUR"))){
                        itemPo.set("STD_LABOUR_HOUR",map.get("STD_LABOUR_HOUR"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("ASSIGN_LABOUR_HOUR"))){
                        itemPo.set("ASSIGN_LABOUR_HOUR",map.get("ASSIGN_LABOUR_HOUR"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("LABOUR_PRICE"))){
                        itemPo.set("LABOUR_PRICE",map.get("LABOUR_PRICE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("LABOUR_AMOUNT"))){
                        itemPo.set("LABOUR_AMOUNT",map.get("LABOUR_AMOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("DISCOUNT"))){
                        itemPo.set("DISCOUNT",map.get("DISCOUNT"));
                    }
                    itemPo.set("CHARGE_PARTITION_CODE",map.get("CHARGE_PARTITION_CODE"));
                    itemPo.saveIt();
                }else {
                    itemPo = TtActivityLabourPO.findById(map.get("ITEM_ID"));
                    itemPo.set("ACTIVITY_CODE",dto.getActivityCode());
                    itemPo.set("MODEL_LABOUR_CODE",map.get("MODEL_LABOUR_CODE"));
                    itemPo.set("LABOUR_CODE",map.get("LABOUR_CODE"));
                    itemPo.set("LABOUR_NAME",map.get("LABOUR_NAME"));
                    itemPo.set("REPAIR_TYPE_CODE",map.get("REPAIR_TYPE_CODE"));
                    if(!StringUtils.isNullOrEmpty(map.get("STD_LABOUR_HOUR"))){
                        itemPo.set("STD_LABOUR_HOUR",map.get("STD_LABOUR_HOUR"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("ASSIGN_LABOUR_HOUR"))){
                        itemPo.set("ASSIGN_LABOUR_HOUR",map.get("ASSIGN_LABOUR_HOUR"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("LABOUR_PRICE"))){
                        itemPo.set("LABOUR_PRICE",map.get("LABOUR_PRICE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("LABOUR_AMOUNT"))){
                        itemPo.set("LABOUR_AMOUNT",map.get("LABOUR_AMOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("DISCOUNT"))){
                        itemPo.set("DISCOUNT",map.get("DISCOUNT"));
                    }
                    itemPo.set("CHARGE_PARTITION_CODE",map.get("CHARGE_PARTITION_CODE"));
                    itemPo.saveIt();
                }
            }
        }
        if (partList != null && !partList.isEmpty()) {
            for (Map map : partList) {
                if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT_DISCOUNT"))) {
                    par += Double.parseDouble(map.get("PART_SALES_AMOUNT_DISCOUNT").toString());
                }
                TtActivityPartPO partPo = null;
                if("A".equals(dto.getFag())){
                    partPo = new TtActivityPartPO();
                    partPo.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    partPo.set("ACTIVITY_CODE",dto.getActivityCode());
                    partPo.set("PART_NO",map.get("PART_NO"));
                    partPo.set("PART_NAME",map.get("PART_NAME"));
                    partPo.set("LABOUR_CODE",map.get("LABOUR_CODE"));
                    if(!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT"))){
                        partPo.set("PART_SALES_AMOUNT",map.get("PART_SALES_AMOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("PART_COST_AMOUNT"))){
                        partPo.set("PART_COST_AMOUNT",map.get("PART_COST_AMOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("PART_SALES_PRICE"))){
                        partPo.set("PART_SALES_PRICE",map.get("PART_SALES_PRICE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("PART_COST_PRICE"))){
                        partPo.set("PART_COST_PRICE",map.get("PART_COST_PRICE"));
                    }
                    partPo.set("UNIT_NAME",map.get("UNIT_CODE"));
                    if(!StringUtils.isNullOrEmpty(map.get("PART_QUANTITY"))){
                        partPo.set("PART_QUANTITY",map.get("PART_QUANTITY"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("IS_MAIN_PART"))){
                        partPo.set("IS_MAIN_PART",map.get("IS_MAIN_PART"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("DISCOUNT"))){
                        partPo.set("DISCOUNT",map.get("DISCOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))){
                        partPo.set("STORAGE_CODE",map.get("STORAGE_CODE"));
                    }
                    partPo.set("CHARGE_PARTITION_CODE",map.get("CHARGE_PARTITION_CODE"));
                    partPo.saveIt();
                }else {
                    partPo = TtActivityPartPO.findById(map.get("ITEM_ID"));
                    partPo.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    partPo.set("ACTIVITY_CODE",dto.getActivityCode());
                    partPo.set("PART_NO",map.get("PART_NO"));
                    partPo.set("PART_NAME",map.get("PART_NAME"));
                    partPo.set("LABOUR_CODE",map.get("LABOUR_CODE"));
                    if(!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT"))){
                        partPo.set("PART_SALES_AMOUNT",map.get("PART_SALES_AMOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("PART_COST_AMOUNT"))){
                        partPo.set("PART_COST_AMOUNT",map.get("PART_COST_AMOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("PART_SALES_PRICE"))){
                        partPo.set("PART_SALES_PRICE",map.get("PART_SALES_PRICE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("PART_COST_PRICE"))){
                        partPo.set("PART_COST_PRICE",map.get("PART_COST_PRICE"));
                    }
                    partPo.set("UNIT_NAME",map.get("UNIT_CODE"));
                    if(!StringUtils.isNullOrEmpty(map.get("PART_QUANTITY"))){
                        partPo.set("PART_QUANTITY",map.get("PART_QUANTITY"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("IS_MAIN_PART"))){
                        partPo.set("IS_MAIN_PART",map.get("IS_MAIN_PART"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("DISCOUNT"))){
                        partPo.set("DISCOUNT",map.get("DISCOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("STORAGE_CODE"))){
                        partPo.set("STORAGE_CODE",map.get("STORAGE_CODE"));
                    }
                    partPo.set("CHARGE_PARTITION_CODE",map.get("CHARGE_PARTITION_CODE"));
                    partPo.saveIt();
                }
            }
        }
        if (subjoinList != null && !subjoinList.isEmpty()) {
            for (Map map : subjoinList) {
                if (!StringUtils.isNullOrEmpty(map.get("RECEIVE_AMOUNT"))) {
                    sub += Double.parseDouble(map.get("RECEIVE_AMOUNT").toString());
                }
                TtActivityAddPO itemPo = null;
                if ("A".equals(dto.getFag())) {
                    itemPo = new TtActivityAddPO();
                    itemPo.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    itemPo.set("ACTIVITY_CODE",dto.getActivityCode());
                    itemPo.set("ADD_ITEM_CODE",map.get("ADD_ITEM_CODE"));
                    itemPo.set("ADD_ITEM_NAME",map.get("ADD_ITEM_NAME"));
                    
                    if(!StringUtils.isNullOrEmpty(map.get("ADD_ITEM_AMOUNT"))){
                        itemPo.set("ADD_ITEM_AMOUNT",map.get("ADD_ITEM_AMOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("DISCOUNT"))){
                        itemPo.set("DISCOUNT",map.get("DISCOUNT"));
                    }
                    itemPo.set("CHARGE_PARTITION_CODE",map.get("CHARGE_PARTITION_CODE"));
                    itemPo.saveIt();
                }else {
                    itemPo = TtActivityAddPO.findByCompositeKeys(map.get("ITEM_ID"),FrameworkUtil.getLoginInfo().getDealerCode());
                    itemPo.set("ACTIVITY_CODE",dto.getActivityCode());
                    itemPo.set("ADD_ITEM_CODE",map.get("ADD_ITEM_CODE"));
                    itemPo.set("ADD_ITEM_NAME",map.get("ADD_ITEM_NAME"));
                    
                    if(!StringUtils.isNullOrEmpty(map.get("ADD_ITEM_AMOUNT"))){
                        itemPo.set("ADD_ITEM_AMOUNT",map.get("ADD_ITEM_AMOUNT"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("DISCOUNT"))){
                        itemPo.set("DISCOUNT",map.get("DISCOUNT"));
                    }
                    itemPo.set("CHARGE_PARTITION_CODE",map.get("CHARGE_PARTITION_CODE"));
                    itemPo.saveIt();
                }
            }
        }
        if (modelList != null && !modelList.isEmpty()) {
            for (Map map : modelList) {
                if("A".equals(dto.getFag())){
                    TtActivityModelPO am = new TtActivityModelPO(); 
                    am.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    am.set("D_KEY",CommonConstants.D_KEY);
                    if(!StringUtils.isNullOrEmpty(map.get("ACTIVITY_CODE"))){
                        am.set("ACTIVITY_CODE",map.get("ACTIVITY_CODE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("MODEL_CODE"))){
                        am.set("MODEL_CODE",map.get("MODEL_CODE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("MANUFACTURE_DATE_BEGIN"))){
                        am.set("MANUFACTURE_DATE_BEGIN",map.get("MANUFACTURE_DATE_BEGIN"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("MANUFACTURE_DATE_END"))){
                        am.set("MANUFACTURE_DATE_END",map.get("MANUFACTURE_DATE_END"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("BEGIN_VIN"))){
                        am.set("BEGIN_VIN",map.get("BEGIN_VIN"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("END_VIN"))){
                        am.set("END_VIN",map.get("END_VIN"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("SERIES_CODE"))){
                        am.set("SERIES_CODE",map.get("SERIES_CODE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("CONFIG_CODE"))){
                        am.set("CONFIG_CODE",map.get("CONFIG_CODE"));
                    }
                    am.saveIt();
                }else {
                    TtActivityModelPO am= TtActivityModelPO.findByCompositeKeys(map.get("ITEM_ID"),FrameworkUtil.getLoginInfo().getDealerCode());
                    am.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    am.set("D_KEY",CommonConstants.D_KEY);
                    if(!StringUtils.isNullOrEmpty(map.get("ACTIVITY_CODE"))){
                        am.set("ACTIVITY_CODE",map.get("ACTIVITY_CODE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("MODEL_CODE"))){
                        am.set("MODEL_CODE",map.get("MODEL_CODE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("MANUFACTURE_DATE_BEGIN"))){
                        am.set("MANUFACTURE_DATE_BEGIN",map.get("MANUFACTURE_DATE_BEGIN"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("MANUFACTURE_DATE_END"))){
                        am.set("MANUFACTURE_DATE_END",map.get("MANUFACTURE_DATE_END"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("BEGIN_VIN"))){
                        am.set("BEGIN_VIN",map.get("BEGIN_VIN"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("END_VIN"))){
                        am.set("END_VIN",map.get("END_VIN"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("SERIES_CODE"))){
                        am.set("SERIES_CODE",map.get("SERIES_CODE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("CONFIG_CODE"))){
                        am.set("CONFIG_CODE",map.get("CONFIG_CODE"));
                    }
                    am.saveIt();
                }
            }
        }
        if (vehicleList != null && !vehicleList.isEmpty()) {
            for (Map map : vehicleList) {
                if("A".equals(dto.getFag())){
                    TtActivityVehiclePO av=new TtActivityVehiclePO();
                    av.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
                    av.set("D_KEY",CommonConstants.D_KEY);
                    if(!StringUtils.isNullOrEmpty(map.get("ACTIVITY_CODE"))){
                        av.set("ACTIVITY_CODE",map.get("ACTIVITY_CODE"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("VIN"))){
                        av.set("VIN",map.get("VIN"));
                    }
                    if(!StringUtils.isNullOrEmpty(map.get("DUTY_ENTITY"))){
                        av.set("DUTY_ENTITY",map.get("DUTY_ENTITY"));
                    }
                    av.saveIt();
                    
                }
            }
        }
        TtActivityPO activityPo = null;
        if ("A".equals(dto.getFag())) {
            activityPo = new TtActivityPO();
            activityPo.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
            activityPo.set("ACTIVITY_CODE",dto.getActivityCode());
            activityPo.set("ACTIVITY_NAME",dto.getActivityName());
            activityPo.set("IS_PART_ACTIVITY",dto.getIsPartActivity());
            activityPo.set("IS_REPEAT_ATTEND",dto.getIsRepeatAttend());
            activityPo.set("VEHICLE_PURPOSE",dto.getVehiclePurpose());
            
            activityPo.set("ACTIVITY_PROPERTY",dto.getActivityProperty()); //added by liufeilu in 20130520
            if (!StringUtils.isNullOrEmpty(dto.getActivityKind())) {
                activityPo.set("ACTIVITY_KIND",dto.getActivityKind());
            }
            if (!StringUtils.isNullOrEmpty(dto.getBeginDate())) {
                activityPo.set("BEGIN_DATE",dto.getBeginDate());
            }
            if (!StringUtils.isNullOrEmpty(dto.getEndDate())) {
                activityPo.set("END_DATE",dto.getEndDate());
            }
            activityPo.set("RELEASE_TAG","12891001");
            activityPo.set("LABOUR_AMOUNT",lab);
            activityPo.set("ADD_ITEM_AMOUNT",sub);
            activityPo.set("REPAIR_PART_AMOUNT",par);
            if (!StringUtils.isNullOrEmpty(dto.getActivityAmount())) {
                activityPo.set("ACTIVITY_AMOUNT",dto.getActivityAmount());
            }
            if (!StringUtils.isNullOrEmpty(dto.getActivityFirst())) {
                activityPo.set("ACTIVITY_FIRST",dto.getActivityFirst());
            } else {
                activityPo.set("ACTIVITY_FIRST",Integer.valueOf(DictCodeConstants.DICT_IS_NO));
            }
            if (!StringUtils.isNullOrEmpty(dto.getCreditTimes())) {
                activityPo.set("CREDIT_TIMES",dto.getCreditTimes());
            }
            // 新增字段
            if (!StringUtils.isNullOrEmpty(dto.getBrand())) {
                activityPo.set("BRAND",dto.getBrand());
            }
            if (!StringUtils.isNullOrEmpty(dto.getSeries())) {
                activityPo.set("SERIES",dto.getSeries());
            }
            if (!StringUtils.isNullOrEmpty(dto.getModel())) {
                activityPo.set("MODEL",dto.getModel());
            }
            if (!StringUtils.isNullOrEmpty(dto.getApAckAge())) {
                activityPo.set("CONFIG_CODE",dto.getApAckAge());
            }
            if (!StringUtils.isNullOrEmpty(dto.getSalesDateBegin())) {
                activityPo.set("SALES_DATE_BEGIN",dto.getSalesDateBegin());
            }
            if (!StringUtils.isNullOrEmpty(dto.getSalesendDate())) {
                activityPo.set("SALES_DATE_END",dto.getSalesendDate());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMileageBegin())) {
                activityPo.set("MILEAGE_BEGIN",dto.getMileageBegin());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMileageEnd())) {
                activityPo.set("MILEAGE_END",dto.getMileageEnd());
            }
            if (!StringUtils.isNullOrEmpty(dto.getBusinessType())) {
                activityPo.set("BUSINESS_TYPE",dto.getBusinessType());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMemberLevelAllowed())) {
                activityPo.set("MEMBER_LEVEL_ALLOWED",dto.getMemberLevelAllowed());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMaintainBeginDay())) {
                activityPo.set("MAINTAIN_BEGIN_DATE",dto.getMaintainBeginDay());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMaintainEndDay())) {
                activityPo.set("MAINTAIN_END_DATE",dto.getMaintainEndDay());
            }
            if (!StringUtils.isNullOrEmpty(dto.getModelYear())) {
                activityPo.set("MODEL_YEAR",dto.getModelYear());
            }
            if(!StringUtils.isNullOrEmpty(dto.getPartsIsModify())){
                activityPo.set("PARTS_IS_MODIFY",dto.getPartsIsModify());
            }
            if(!StringUtils.isNullOrEmpty(dto.getActivityKind())){
                activityPo.set("ACTIVITY_KIND",dto.getActivityKind());
            }
            if (!StringUtils.isNullOrEmpty(dto.getGlobalActivityCode())) {
                activityPo.set("GLOBAL_ACTIVITY_CODE",dto.getGlobalActivityCode());
            }
            if (!StringUtils.isNullOrEmpty(dto.getActivityTitle())) {
                activityPo.set("ACTIVITY_TITLE",dto.getActivityTitle());
            }
            activityPo.saveIt();
        }else {
            activityPo = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getActivityCode());
            activityPo.set("ACTIVITY_NAME",dto.getActivityName());
            activityPo.set("IS_PART_ACTIVITY",dto.getIsPartActivity());
            activityPo.set("IS_REPEAT_ATTEND",dto.getIsRepeatAttend());
            activityPo.set("VEHICLE_PURPOSE",dto.getVehiclePurpose());
            
            activityPo.set("Activity_Property",dto.getActivityProperty());
            if (!StringUtils.isNullOrEmpty(dto.getActivityKind())) {
                activityPo.set("ACTIVITY_KIND",dto.getActivityKind());
            }
            if (!StringUtils.isNullOrEmpty(dto.getBeginDate())) {
                activityPo.set("BEGIN_DATE",dto.getBeginDate());
            }
            if (!StringUtils.isNullOrEmpty(dto.getEndDate())) {
                activityPo.set("END_DATE",dto.getEndDate());
            }
            activityPo.set("RELEASE_TAG","12891001");
            activityPo.set("LABOUR_AMOUNT",lab);
            activityPo.set("ADD_ITEM_AMOUNT",sub);
            activityPo.set("REPAIR_PART_AMOUNT",par);
            if (!StringUtils.isNullOrEmpty(dto.getActivityAmount())) {
                activityPo.set("ACTIVITY_AMOUNT",dto.getActivityAmount());
            }
            if (!StringUtils.isNullOrEmpty(dto.getActivityFirst())) {
                activityPo.set("ACTIVITY_FIRST",dto.getActivityFirst());
            }
            if (!StringUtils.isNullOrEmpty(dto.getCreditTimes())) {
                activityPo.set("CREDIT_TIMES",dto.getCreditTimes());
            } else {
                activityPo.set("CREDIT_TIMES",0.00);
            }
            // 新增字段
            if (!StringUtils.isNullOrEmpty(dto.getBrand())) {
                activityPo.set("BRAND",dto.getBrand());
            }
            if (!StringUtils.isNullOrEmpty(dto.getSeries())) {
                activityPo.set("SERIES",dto.getSeries());
            }
            if (!StringUtils.isNullOrEmpty(dto.getModel())) {
                activityPo.set("MODEL",dto.getModel());
            }
            if (!StringUtils.isNullOrEmpty(dto.getApAckAge())) {
                activityPo.set("CONFIG_CODE",dto.getApAckAge());
            }
            if (!StringUtils.isNullOrEmpty(dto.getSalesDateBegin())) {
                activityPo.set("SALES_DATE_BEGIN",dto.getSalesDateBegin());
            }
            if (!StringUtils.isNullOrEmpty(dto.getSalesendDate())) {
                activityPo.set("SALES_DATE_END",dto.getSalesendDate());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMileageBegin())) {
                activityPo.set("MILEAGE_BEGIN",dto.getMileageBegin());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMileageEnd())) {
                activityPo.set("MILEAGE_END",dto.getMileageEnd());
            }
            if (!StringUtils.isNullOrEmpty(dto.getBusinessType())) {
                activityPo.set("BUSINESS_TYPE",dto.getBusinessType());
            } else {
                activityPo.set("BUSINESS_TYPE",0);
            }
            if (!StringUtils.isNullOrEmpty(dto.getMemberLevelAllowed())) {
                activityPo.set("MEMBER_LEVEL_ALLOWED",dto.getMemberLevelAllowed());
            } else {
                activityPo.set("MEMBER_LEVEL_ALLOWED","");
            }

            if (!StringUtils.isNullOrEmpty(dto.getMaintainBeginDay())) {
                activityPo.set("MAINTAIN_BEGIN_DAY",dto.getMaintainBeginDay());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMaintainEndDay())) {
                activityPo.set("MAINTAIN_END_DAY",dto.getMaintainEndDay());
            }
            if (!StringUtils.isNullOrEmpty(dto.getModelYear())) {
                activityPo.set("MODEL_YEAR",dto.getModelYear());
            }
            else{
                activityPo.set("MODEL_YEAR","");
            }
            if(!StringUtils.isNullOrEmpty(dto.getPartsIsModify())){
                activityPo.set("PARTS_IS_MODIFY",dto.getPartsIsModify());
            }
            if(!StringUtils.isNullOrEmpty(dto.getActivityKind())){
                activityPo.set("ACTIVITY_KIND",dto.getActivityKind());
            }
            if (!StringUtils.isNullOrEmpty(dto.getGlobalActivityCode())) {
                activityPo.set("GLOBAL_ACTIVITY_CODE",dto.getGlobalActivityCode());
            }
            if (!StringUtils.isNullOrEmpty(dto.getActivityTitle())) {
                activityPo.set("ACTIVITY_TITLE",dto.getActivityTitle());
            }
            activityPo.saveIt();
            if (!StringUtils.isNullOrEmpty(dto.getAdd())) {
                String add = dto.getAdd();
                List<Map> add2 = JSONUtil.jsonToList(add, Map.class);
                for (Map map2 : add2) {
                    TtActivityLabourPO.delete("ITEM_ID=?", map2.get("itemId"));
                }
                //更新主表信息
                TtActivityPO poDms = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getActivityCode());
                poDms.saveIt();
            } 
            if (!StringUtils.isNullOrEmpty(dto.getSub())) {
                String sub2 = dto.getSub();
                List<Map> sub3 = JSONUtil.jsonToList(sub2, Map.class);
                for (Map map2 : sub3) {
                    TtActivityAddPO.delete("ITEM_ID=?", map2.get("itemId"));
                }
                //更新主表信息
                TtActivityPO poDms = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getActivityCode());
                poDms.saveIt();
            }
            if (!StringUtils.isNullOrEmpty(dto.getPart())) {
                String part = dto.getPart();
                List<Map> part2 = JSONUtil.jsonToList(part, Map.class);
                for (Map map2 : part2) {
                    TtActivityPartPO.delete("ITEM_ID=?", map2.get("itemId"));
                }
                //更新主表更新时间
                TtActivityPO poDms = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getActivityCode());
                poDms.saveIt();
            }  
            if (!StringUtils.isNullOrEmpty(dto.getMod())) {
                String mod = dto.getMod();
                List<Map> mod2 = JSONUtil.jsonToList(mod, Map.class);
                for (Map map2 : mod2) {
                    TtActivityModelPO.delete("ITEM_ID=?", map2.get("itemId"));
                }
                //更新主表更新时间
                TtActivityPO poDms = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getActivityCode());
                poDms.saveIt();
            }
            if (!StringUtils.isNullOrEmpty(dto.getVehic())) {
                String vehic = dto.getVehic();
                List<Map> vehic2 = JSONUtil.jsonToList(vehic, Map.class);
                for (Map map2 : vehic2) {
                    TtActivityVehiclePO.delete("ITEM_ID=?", map2.get("itemId"));
                }
//              更新主表更新时间
                TtActivityPO poDms = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getActivityCode());
                poDms.saveIt();
            }
        }
        return "1";
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> querys(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select a.* FROM ("+CommonConstants.VT_ACTIVITY+") a WHERE a.DEALER_CODE=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(id)) {
           sql.append(" and a.ACTIVITY_CODE like ?");
           list.add("%"+id+"%");
        }
        sql.append(" order by a.CREATED_AT desc ");
        return DAOUtil.findFirst(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryVehicle(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        String sql = "SELECT a.* from ("+CommonConstants.VT_ACTIVITY_VEHICLE+") a ";
        if (!StringUtils.isNullOrEmpty(map.get("activityCode"))) {
            sql = sql + " AND a.Activity_Code = ? ";
            list.add(map.get("activityCode"));
        }
        return DAOUtil.pageQuery(sql, list);
    }

    @Override
    public PageInfoDto queryModel(Map map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        String sql = "SELECT * FROM Tt_Activity_Model ";
        if (!StringUtils.isNullOrEmpty(map.get("activityCode"))) {
            sql = sql + " AND Activity_Code = ? ";
            list.add(map.get("activityCode"));
        }
        return DAOUtil.pageQuery(sql, list);
    }

    @Override
    public PageInfoDto selectpart(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        PageInfoDto pageQuery = null;
        if (!StringUtils.isNullOrEmpty(id)) {
            String sql = "select * from Tt_Activity_Part where activity_code = ? ";
            list.add(id);
            pageQuery = DAOUtil.pageQuery(sql, list);
        }
        return pageQuery;
    }

    @Override
    public PageInfoDto selectlabour(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        PageInfoDto pageQuery = null;
        if (!StringUtils.isNullOrEmpty(id)) {
            String sql = "select * from Tt_Activity_Labour where activity_code = ? ";
            list.add(id);
            pageQuery = DAOUtil.pageQuery(sql, list);
        }
        return pageQuery;
    }

    @Override
    public PageInfoDto selectsubjoin(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        PageInfoDto pageQuery = null;
        if (!StringUtils.isNullOrEmpty(id)) {
            String sql = "select * from Tt_Activity_Add where activity_code = ? ";
            list.add(id);
            pageQuery = DAOUtil.pageQuery(sql, list);
        }
        return pageQuery;
    }

    @Override
    public void UpdateActivityReleaseTag(String id,String ids) throws ServiceBizException {
        TtActivityPO activityPo = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),id);
        if (ids.equals("1")) {
            activityPo.set("RELEASE_TAG","12891002");
            activityPo.set("RELEASE_DATE",new Date(System.currentTimeMillis()));
            activityPo.set("IS_VALID","12781001");
        }
        if (ids.equals("2")) {
            activityPo.set("IS_VALID","12781002");
        }
        activityPo.saveIt();
    }

}
