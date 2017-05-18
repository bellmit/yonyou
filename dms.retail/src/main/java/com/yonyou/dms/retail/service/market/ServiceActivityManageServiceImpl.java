
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
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtActivityLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPO;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
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
        double lab=0,par=0,sub=0;
        
        for (Map map : labourList) {
            lab += Double.parseDouble(map.get("LABOUR_AMOUNT_DISCOUNT").toString());
        }
        for (Map map : partList) {
            par += Double.parseDouble(map.get("PART_SALES_AMOUNT_DISCOUNT").toString());
        }
        for (Map map : subjoinList) {
            sub += Double.parseDouble(map.get("RECEIVE_AMOUNT").toString());
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
                activityPo.set("ACTIVITY_TYPE",dto.getActivityKind());
            }
            try {
                if (!StringUtils.isNullOrEmpty(dto.getBeginDate())) {
                    activityPo.set("BEGIN_DATE",Utility.getTimeStamp(dto.getBeginDate().toString().substring(0, 10) + " 00:00:00"));
                }
                if (!StringUtils.isNullOrEmpty(dto.getEndDate())) {
                    activityPo.set("END_DATE",Utility.getTimeStamp(dto.getEndDate().toString().substring(0, 10) + " 23:59:59"));
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                activityPo.set("MAINTAIN_BEGIN_DAY",dto.getMaintainBeginDay());
            }
            if (!StringUtils.isNullOrEmpty(dto.getMaintainEndDay())) {
                activityPo.set("MAINTAIN_END_DAY",dto.getMaintainEndDay());
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
        }
        if ("U".equals(dto.getFag())) {
            activityPo = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getActivityCode());
            activityPo.set("ACTIVITY_NAME",dto.getActivityName());
            activityPo.set("IS_PART_ACTIVITY",dto.getIsPartActivity());
            activityPo.set("IS_REPEAT_ATTEND",dto.getIsRepeatAttend());
            activityPo.set("VEHICLE_PURPOSE",dto.getVehiclePurpose());
            
            activityPo.set("Activity_Property",dto.getActivityProperty());
            if (!StringUtils.isNullOrEmpty(dto.getActivityKind())) {
                activityPo.set("ACTIVITY_TYPE",dto.getActivityKind());
            }
            try {
                if (!StringUtils.isNullOrEmpty(dto.getBeginDate())) {
                activityPo.set("BEGIN_DATE",Utility.getTimeStamp(dto.getBeginDate().toString().substring(0, 10) + " 00:00:00"));
                }
                if (!StringUtils.isNullOrEmpty(dto.getEndDate())) {
                    activityPo.set("END_DATE",Utility.getTimeStamp(dto.getEndDate().toString().substring(0, 10) + " 23:59:59"));
                }
            } catch (Exception e) {
                e.printStackTrace();
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
                activityPo.set("MEMBERLEVEL_ALLOWED",dto.getMemberLevelAllowed());
            } else {
                activityPo.set("MEMBERLEVEL_ALLOWED","");
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
        }
        if ("D".equals(dto.getFag())) {
            TtActivityPO.delete("activity_code=?", dto.getActivityCode());
            TtActivityPartPO.delete("activity_code=?", dto.getActivityCode());
            TtActivityLabourPO.delete("activity_code=?", dto.getActivityCode());

            //更新主表的更新时间
            TtActivityPO poDms = TtActivityPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),dto.getActivityCode());
            poDms.saveIt();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String,Object> querys(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("select a.* FROM ("+CommonConstants.VT_ACTIVITY+") a WHERE a.DEALER_CODE=? ");
//        sql.append(" a.DEALER_CODE, a.ACTIVITY_CODE, a.ACTIVITY_NAME, a.ACTIVITY_TYPE,a.ACTIVITY_KIND, a.BEGIN_DATE,");
//        sql.append(" a.PARTS_IS_MODIFY,'' AS LABOUR_CODE,'' AS PART_CODE,a.END_DATE, a.RELEASE_DATE, a.RELEASE_TAG, a.LABOUR_AMOUNT, a.ADD_ITEM_AMOUNT, ");
//        sql.append(" a.FROM_ENTITY, a.DOWN_TAG, a.DOWN_TIMESTAMP, a.GLOBAL_ACTIVITY_CODE, a.ACTIVITY_TITLE, a.ATTACHMENT_URL, ");
//        sql.append(" a.REPAIR_PART_AMOUNT, a.ACTIVITY_AMOUNT, a.IS_VALID,a.VER,a.ACTIVITY_FIRST,a.OCCUR_CREDIT,");
//        sql.append(" select a.* FROM ("+CommonConstants.VT_ACTIVITY+") a WHERE a.DEALER_CODE=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(id)) {
           sql.append(" and a.ACTIVITY_CODE like ?");
           list.add("%"+id+"%");
        }
        sql.append(" order by a.CREATED_AT desc ");
        return DAOUtil.findFirst(sql.toString(), list);
    }

}
