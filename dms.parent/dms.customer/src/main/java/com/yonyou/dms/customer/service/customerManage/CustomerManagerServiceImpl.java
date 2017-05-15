
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CustomerManagerServiceImpl.java
*
* @Author : dingchaoyu
*
* @Date : 2017年4月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月27日    dingchaoyu    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.customer.VehicleMemoPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TtServiceInsurancePO;
import com.yonyou.dms.customer.domains.DTO.customerManage.CustomerManageDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.CommonErrorConstant;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author dingchaoyu
* @date 2017年4月27日
*/
@SuppressWarnings("rawtypes")
@Service
public class CustomerManagerServiceImpl implements CustomerManagerService{

    @Override
    public PageInfoDto queryByFixspell(Map<String, String> map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT distinct A.DEALER_CODE,A.OWNER_NO, A.OWNER_PROPERTY, A.OWNER_NAME, A.ADDRESS,A.MEMBER_NO,A.CT_CODE,A.CERTIFICATE_NO, ");
        sql.append(" case when (A.OWNER_PROPERTY = ?) and  (A.DECISION_MARKER_PHONE is not null)  then DECISION_MARKER_PHONE ");
        sql.append(" when (A.OWNER_PROPERTY = ?) and (A.PHONE is not null) then PHONE else null end PHONE,");
        sql.append(" case when (A.OWNER_PROPERTY =?) and (A.DECISION_MARKER_MOBILE is not null)  then DECISION_MARKER_MOBILE ");
        sql.append(" when (A.OWNER_PROPERTY =?) and (A.MOBILE is not null) then MOBILE else null end MOBILE, ");
        sql.append(" A.CONTACTOR_NAME, A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.ZIP_CODE,A.CREATED_BY, ");
        sql.append(" COALESCE(A.ARREARAGE_AMOUNT,0) as ARREARAGE_AMOUNT,");
        sql.append(" case when B.MEMBER_STATUS=? then 12781001 else 12781002 end MEMBER_STATUS");
        sql.append(" FROM ("+CommonConstants.VM_OWNER+") A LEFT JOIN ("+CommonConstants.VM_MEMBER+") B ON B.CUSTOMER_NO=A.OWNER_NO AND A.DEALER_CODE=B.DEALER_CODE and B.MEMBER_STATUS=?");
        sql.append(" AND A.CERTIFICATE_NO = B.CERTIFICATE_NO   WHERE 1=1 ");
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL);
        list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&!"-1".equals(map.get("OWNER_PROPERTY"))) {
            sql.append(" AND A.OWNER_PROPERTY like ? ");
            list.add("%"+map.get("OWNER_PROPERTY")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NO"))) {
            sql.append(" and A.OWNER_NO like ?");
            list.add("%"+map.get("OWNER_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NAME"))) {
            sql.append(" AND A.OWNER_NAME like ? ");
            list.add("%"+map.get("OWNER_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_SPELL"))) {
            sql.append(" AND A.OWNER_SPELL like ?");
            list.add("%"+map.get("OWNER_SPELL")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("MEMBER_STATUS"))&&map.get("MEMBER_STATUS").equals(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL)) {
            sql.append(" AND B.MEMBER_STATUS = ? ");
            list.add(map.get("MEMBER_STATUS"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("MEMBER_STATUS"))&&map.get("MEMBER_STATUS").equals(DictCodeConstants.DICT_MEMBER_STATUS_QUIT)) {
            sql.append(" AND (B.MEMBER_STATUS IS NULL OR B.MEMBER_STATUS<>?)");
            list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&map.get("OWNER_PROPERTY").equals(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY)) {
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" and A.DECISION_MARKER_PHONE like ? ");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" and A.DECISION_MARKER_MOBILE like ? ");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }else if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&map.get("OWNER_PROPERTY").equals(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL)) {
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" and A.PHONE like ? ");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" and A.MOBILE like ? ");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }else{
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" AND ( A.DECISION_MARKER_PHONE LIKE ? OR A.PHONE LIKE ? ) ");
                list.add("%"+map.get("PHONE")+"%");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" AND ( A.DECISION_MARKER_MOBILE LIKE ? OR A.MOBILE LIKE ?) ");
                list.add("%"+map.get("MOBILE")+"%");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }
        if (!StringUtils.isNullOrEmpty(map.get("BEGIN_TIME"))&&!StringUtils.isNullOrEmpty(map.get("END_TIME"))) {
            Utility.getDateCond("A", "FOUND_DATE", map.get("BEGIN_TIME").toString(), map.get("END_TIME").toString());
        }
        sql.append(" ORDER BY A.OWNER_NO DESC ");
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryByNoOrSpellForPageQuery(Map<String, String> map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT A.FOUND_DATE,A.WX_UNBUNDLING_DATE,C.IS_WX_CONNECT,C.MOBILE_PHONE, ");
        sql.append(" A.NO_VALID_REASON,A.KEY_NUMBER,A.DEALER_CODE,A.VIN,A.AVCHORED,A.VSN,A.DISCHARGE_STANDARD,A.EXHAUST_QUANTITY,A.OWNER_NO,");
        sql.append(" A.IS_BINDING,A.BINDING_DATE,A.UNIT_ATTACHCODE ,LICENSE, ENGINE_NO, BRAND, SERIES, MODEL, DELIVERER, DELIVERER_PHONE,");
        sql.append(" DELIVERER_MOBILE,LAST_MAINTAIN_DATE,SERVICE_ADVISOR,CONSULTANT,A.CREATED_BY ,COALESCE(A.ARREARAGE_AMOUNT,0)  as ARREARAGE_AMOUNT,A.PRODUCTING_AREA,");
        sql.append(" B.OWNER_PROPERTY,B.OWNER_NAME,B.ADDRESS,B.MEMBER_NO,B.CONTACTOR_NAME,B.CONTACTOR_PHONE,B.CONTACTOR_MOBILE,B.ZIP_CODE, ");
        sql.append(" case when (B.DECISION_MARKER_PHONE is null) and (B.PHONE is not null) then PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is  null) then B.DECISION_MARKER_PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is not null) and (B.DECISION_MARKER_PHONE=B.PHONE) then PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is not null) and (B.DECISION_MARKER_PHONE<>B.PHONE)then trim(PHONE)||' '||trim(B.DECISION_MARKER_PHONE) ");
        sql.append(" else null end PHONE,case");
        sql.append(" when (B.DECISION_MARKER_MOBILE is null) and (B.MOBILE is not null) then MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is  null) then B.DECISION_MARKER_MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is not null) and (B.DECISION_MARKER_MOBILE=B.MOBILE) then MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is not null) and (B.DECISION_MARKER_MOBILE<>B.MOBILE) then trim(MOBILE)||' '||trim(B.DECISION_MARKER_MOBILE) ");
        sql.append(" else null end MOBILE FROM (SELECT * FROM ("+CommonConstants.VM_OWNER+") B WHERE 1 = 1  AND B.DEALER_CODE =?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&!"-1".equals(map.get("OWNER_PROPERTY"))) {
            sql.append(" AND B.OWNER_PROPERTY like ? ");
            list.add("%"+map.get("OWNER_PROPERTY")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NO"))) {
            sql.append(" and B.OWNER_NO like ?");
            list.add("%"+map.get("OWNER_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NAME"))) {
            sql.append(" AND B.OWNER_NAME like ? ");
            list.add("%"+map.get("OWNER_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_SPELL"))) {
            sql.append(" AND B.OWNER_SPELL like ?");
            list.add("%"+map.get("OWNER_SPELL")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("MEMBER_STATUS"))) {
            sql.append(" AND B.OWNER_PROPERTY like ? ");
            list.add("%"+map.get("MEMBER_STATUS")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&map.get("OWNER_PROPERTY").equals(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY)) {
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" and B.DECISION_MARKER_PHONE like ? ");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" and B.DECISION_MARKER_MOBILE like ? ");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }else if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&map.get("OWNER_PROPERTY").equals(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL)) {
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" and B.PHONE like ? ");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" and B.MOBILE like ? ");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }else{
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" AND ( B.DECISION_MARKER_PHONE LIKE ? OR A.PHONE LIKE ? ) ");
                list.add("%"+map.get("PHONE")+"%");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" AND ( B.DECISION_MARKER_MOBILE LIKE ? OR A.MOBILE LIKE ?) ");
                list.add("%"+map.get("MOBILE")+"%");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }
        if (!StringUtils.isNullOrEmpty(map.get("BEGIN_TIMEs"))&&!StringUtils.isNullOrEmpty(map.get("END_TIMEs"))) {
            Utility.getDateCond("B", "FOUND_DATE", map.get("BEGIN_TIMEs").toString(), map.get("END_TIMEs").toString());
        }
        sql.append(" ORDER BY OWNER_NO DESC ) B");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VEHICLE+") A ON B.OWNER_NO = A.OWNER_NO AND B.DEALER_CODE = A.DEALER_CODE ");
        sql.append(" LEFT JOIN TM_WX_HISTORY  C ON A.VIN = C.VIN AND A.DEALER_CODE = C.DEALER_CODE AND C.IS_WX_CONNECT=12781001 ");
        sql.append("WHERE A.VIN IS NOT NULL ");
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public List<Map> queryByFixspellExport(Map<String, String> map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT distinct A.DEALER_CODE,A.OWNER_NO, A.OWNER_PROPERTY, A.OWNER_NAME, A.ADDRESS,A.MEMBER_NO,A.CT_CODE,A.CERTIFICATE_NO, ");
        sql.append(" case when (A.OWNER_PROPERTY = ?) and  (A.DECISION_MARKER_PHONE is not null)  then DECISION_MARKER_PHONE ");
        sql.append(" when (A.OWNER_PROPERTY = ?) and (A.PHONE is not null) then PHONE else null end PHONE,");
        sql.append(" case when (A.OWNER_PROPERTY =?) and (A.DECISION_MARKER_MOBILE is not null)  then DECISION_MARKER_MOBILE ");
        sql.append(" when (A.OWNER_PROPERTY =?) and (A.MOBILE is not null) then MOBILE else null end MOBILE, ");
        sql.append(" A.CONTACTOR_NAME, A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.ZIP_CODE,A.CREATED_BY, ");
        sql.append(" COALESCE(A.ARREARAGE_AMOUNT,0) as ARREARAGE_AMOUNT,");
        sql.append(" case when B.MEMBER_STATUS=? then 12781001 else 12781002 end MEMBER_STATUS");
        sql.append(" FROM ("+CommonConstants.VM_OWNER+") A LEFT JOIN ("+CommonConstants.VM_MEMBER+") B ON B.CUSTOMER_NO=A.OWNER_NO AND A.DEALER_CODE=B.DEALER_CODE and B.MEMBER_STATUS=?");
        sql.append(" AND A.CERTIFICATE_NO = B.CERTIFICATE_NO   WHERE 1=1 ");
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL);
        list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&!"-1".equals(map.get("OWNER_PROPERTY"))) {
            sql.append(" AND A.OWNER_PROPERTY like ? ");
            list.add("%"+map.get("OWNER_PROPERTY")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NO"))) {
            sql.append(" and A.OWNER_NO like ?");
            list.add("%"+map.get("OWNER_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NAME"))) {
            sql.append(" AND A.OWNER_NAME like ? ");
            list.add("%"+map.get("OWNER_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_SPELL"))) {
            sql.append(" AND A.OWNER_SPELL like ?");
            list.add("%"+map.get("OWNER_SPELL")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("MEMBER_STATUS"))&&map.get("MEMBER_STATUS").equals(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL)) {
            sql.append(" AND B.MEMBER_STATUS = ? ");
            list.add(map.get("MEMBER_STATUS"));
        }
        if (!StringUtils.isNullOrEmpty(map.get("MEMBER_STATUS"))&&map.get("MEMBER_STATUS").equals(DictCodeConstants.DICT_MEMBER_STATUS_QUIT)) {
            sql.append(" AND (B.MEMBER_STATUS IS NULL OR B.MEMBER_STATUS<>?)");
            list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&map.get("OWNER_PROPERTY").equals(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY)) {
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" and A.DECISION_MARKER_PHONE like ? ");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" and A.DECISION_MARKER_MOBILE like ? ");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }else if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&map.get("OWNER_PROPERTY").equals(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL)) {
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" and A.PHONE like ? ");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" and A.MOBILE like ? ");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }else{
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" AND ( A.DECISION_MARKER_PHONE LIKE ? OR A.PHONE LIKE ? ) ");
                list.add("%"+map.get("PHONE")+"%");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" AND ( A.DECISION_MARKER_MOBILE LIKE ? OR A.MOBILE LIKE ?) ");
                list.add("%"+map.get("MOBILE")+"%");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }
        if (!StringUtils.isNullOrEmpty(map.get("BEGIN_TIME"))&&!StringUtils.isNullOrEmpty(map.get("END_TIME"))) {
            Utility.getDateCond("A", "FOUND_DATE", map.get("BEGIN_TIME").toString(), map.get("END_TIME").toString());
        }
        sql.append(" ORDER BY A.OWNER_NO DESC ");
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public List<Map> queryByNoOrSpellForPageQueryExport(Map<String, String> map) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT A.FOUND_DATE,A.WX_UNBUNDLING_DATE,C.IS_WX_CONNECT,C.MOBILE_PHONE, ");
        sql.append(" A.NO_VALID_REASON,A.KEY_NUMBER,A.DEALER_CODE,A.VIN,A.AVCHORED,A.VSN,A.DISCHARGE_STANDARD,A.EXHAUST_QUANTITY,A.OWNER_NO,");
        sql.append(" A.IS_BINDING,A.BINDING_DATE,A.UNIT_ATTACHCODE ,LICENSE, ENGINE_NO, BRAND, SERIES, MODEL, DELIVERER, DELIVERER_PHONE,");
        sql.append(" DELIVERER_MOBILE,LAST_MAINTAIN_DATE,SERVICE_ADVISOR,CONSULTANT,A.CREATED_BY ,COALESCE(A.ARREARAGE_AMOUNT,0)  as ARREARAGE_AMOUNT,A.PRODUCTING_AREA,");
        sql.append(" B.OWNER_PROPERTY,B.OWNER_NAME,B.ADDRESS,B.MEMBER_NO,B.CONTACTOR_NAME,B.CONTACTOR_PHONE,B.CONTACTOR_MOBILE,B.ZIP_CODE, ");
        sql.append(" case when (B.DECISION_MARKER_PHONE is null) and (B.PHONE is not null) then PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is  null) then B.DECISION_MARKER_PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is not null) and (B.DECISION_MARKER_PHONE=B.PHONE) then PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is not null) and (B.DECISION_MARKER_PHONE<>B.PHONE)then trim(PHONE)||' '||trim(B.DECISION_MARKER_PHONE) ");
        sql.append(" else null end PHONE,case");
        sql.append(" when (B.DECISION_MARKER_MOBILE is null) and (B.MOBILE is not null) then MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is  null) then B.DECISION_MARKER_MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is not null) and (B.DECISION_MARKER_MOBILE=B.MOBILE) then MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is not null) and (B.DECISION_MARKER_MOBILE<>B.MOBILE) then trim(MOBILE)||' '||trim(B.DECISION_MARKER_MOBILE) ");
        sql.append(" else null end MOBILE FROM (SELECT * FROM ("+CommonConstants.VM_OWNER+") B WHERE 1 = 1  AND B.DEALER_CODE =?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&!"-1".equals(map.get("OWNER_PROPERTY"))) {
            sql.append(" AND B.OWNER_PROPERTY like ? ");
            list.add("%"+map.get("OWNER_PROPERTY")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NO"))) {
            sql.append(" and B.OWNER_NO like ?");
            list.add("%"+map.get("OWNER_NO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_NAME"))) {
            sql.append(" AND B.OWNER_NAME like ? ");
            list.add("%"+map.get("OWNER_NAME")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_SPELL"))) {
            sql.append(" AND B.OWNER_SPELL like ?");
            list.add("%"+map.get("OWNER_SPELL")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("MEMBER_STATUS"))) {
            sql.append(" AND B.OWNER_PROPERTY like ? ");
            list.add("%"+map.get("MEMBER_STATUS")+"%");
        }
        if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&map.get("OWNER_PROPERTY").equals(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY)) {
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" and B.DECISION_MARKER_PHONE like ? ");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" and B.DECISION_MARKER_MOBILE like ? ");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }else if (!StringUtils.isNullOrEmpty(map.get("OWNER_PROPERTY"))&&map.get("OWNER_PROPERTY").equals(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL)) {
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" and B.PHONE like ? ");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" and B.MOBILE like ? ");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }else{
            if (!StringUtils.isNullOrEmpty(map.get("PHONE"))) {
                sql.append(" AND ( B.DECISION_MARKER_PHONE LIKE ? OR A.PHONE LIKE ? ) ");
                list.add("%"+map.get("PHONE")+"%");
                list.add("%"+map.get("PHONE")+"%");
            }
            if (!StringUtils.isNullOrEmpty(map.get("MOBILE"))) {
                sql.append(" AND ( B.DECISION_MARKER_MOBILE LIKE ? OR A.MOBILE LIKE ?) ");
                list.add("%"+map.get("MOBILE")+"%");
                list.add("%"+map.get("MOBILE")+"%");
            }
        }
        if (!StringUtils.isNullOrEmpty(map.get("BEGIN_TIMEs"))&&!StringUtils.isNullOrEmpty(map.get("END_TIMEs"))) {
            Utility.getDateCond("B", "FOUND_DATE", map.get("BEGIN_TIMEs").toString(), map.get("END_TIMEs").toString());
        }
        sql.append(" ORDER BY OWNER_NO DESC ) B");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VEHICLE+") A ON B.OWNER_NO = A.OWNER_NO AND B.DEALER_CODE = A.DEALER_CODE ");
        sql.append(" LEFT JOIN TM_WX_HISTORY  C ON A.VIN = C.VIN AND A.DEALER_CODE = C.DEALER_CODE AND C.IS_WX_CONNECT=12781001 ");
        sql.append("WHERE A.VIN IS NOT NULL ");
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryByFixspells(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT distinct A.DEALER_CODE,A.OWNER_NO, A.OWNER_PROPERTY, A.OWNER_NAME, A.ADDRESS,A.MEMBER_NO,A.CT_CODE,A.CERTIFICATE_NO, ");
        sql.append(" case when (A.OWNER_PROPERTY = ?) and  (A.DECISION_MARKER_PHONE is not null)  then DECISION_MARKER_PHONE ");
        sql.append(" when (A.OWNER_PROPERTY = ?) and (A.PHONE is not null) then PHONE else null end PHONE,");
        sql.append(" case when (A.OWNER_PROPERTY =?) and (A.DECISION_MARKER_MOBILE is not null)  then DECISION_MARKER_MOBILE ");
        sql.append(" when (A.OWNER_PROPERTY =?) and (A.MOBILE is not null) then MOBILE else null end MOBILE, ");
        sql.append(" A.CONTACTOR_NAME, A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.ZIP_CODE,A.CREATED_BY, ");
        sql.append(" COALESCE(A.ARREARAGE_AMOUNT,0) as ARREARAGE_AMOUNT,");
        sql.append(" case when B.MEMBER_STATUS=? then 12781001 else 12781002 end MEMBER_STATUS");
        sql.append(" FROM ("+CommonConstants.VM_OWNER+") A LEFT JOIN ("+CommonConstants.VM_MEMBER+") B ON B.CUSTOMER_NO=A.OWNER_NO AND A.DEALER_CODE=B.DEALER_CODE and B.MEMBER_STATUS=?");
        sql.append(" AND A.CERTIFICATE_NO = B.CERTIFICATE_NO   WHERE 1=1 ");
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL);
        list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        if (!StringUtils.isNullOrEmpty(id)) {
            sql.append(" and A.OWNER_NO like ?");
            list.add("%"+id+"%");
        } 
        sql.append(" ORDER BY A.OWNER_NO DESC ");
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public PageInfoDto queryByNoOrSpellForPageQuerys(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT A.FOUND_DATE,A.WX_UNBUNDLING_DATE,C.IS_WX_CONNECT,C.MOBILE_PHONE, ");
        sql.append(" A.NO_VALID_REASON,A.KEY_NUMBER,A.DEALER_CODE,A.VIN,A.AVCHORED,A.VSN,A.DISCHARGE_STANDARD,A.EXHAUST_QUANTITY,A.OWNER_NO,");
        sql.append(" A.IS_BINDING,A.BINDING_DATE,A.UNIT_ATTACHCODE ,LICENSE, ENGINE_NO, BRAND, SERIES, MODEL, DELIVERER, DELIVERER_PHONE,");
        sql.append(" DELIVERER_MOBILE,LAST_MAINTAIN_DATE,SERVICE_ADVISOR,CONSULTANT,A.CREATED_BY ,COALESCE(A.ARREARAGE_AMOUNT,0)  as ARREARAGE_AMOUNT,A.PRODUCTING_AREA,");
        sql.append(" B.OWNER_PROPERTY,B.OWNER_NAME,B.ADDRESS,B.MEMBER_NO,B.CONTACTOR_NAME,B.CONTACTOR_PHONE,B.CONTACTOR_MOBILE,B.ZIP_CODE, ");
        sql.append(" case when (B.DECISION_MARKER_PHONE is null) and (B.PHONE is not null) then PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is  null) then B.DECISION_MARKER_PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is not null) and (B.DECISION_MARKER_PHONE=B.PHONE) then PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is not null) and (B.DECISION_MARKER_PHONE<>B.PHONE)then trim(PHONE)||' '||trim(B.DECISION_MARKER_PHONE) ");
        sql.append(" else null end PHONE,case");
        sql.append(" when (B.DECISION_MARKER_MOBILE is null) and (B.MOBILE is not null) then MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is  null) then B.DECISION_MARKER_MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is not null) and (B.DECISION_MARKER_MOBILE=B.MOBILE) then MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is not null) and (B.DECISION_MARKER_MOBILE<>B.MOBILE) then trim(MOBILE)||' '||trim(B.DECISION_MARKER_MOBILE) ");
        sql.append(" else null end MOBILE FROM (SELECT * FROM ("+CommonConstants.VM_OWNER+") B WHERE 1 = 1  AND B.DEALER_CODE =?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(id)) {
            sql.append(" and B.OWNER_NO like ?");
            list.add("%"+id+"%");
        }
        sql.append(" ORDER BY OWNER_NO DESC ) B");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VEHICLE+") A ON B.OWNER_NO = A.OWNER_NO AND B.DEALER_CODE = A.DEALER_CODE ");
        sql.append(" LEFT JOIN TM_WX_HISTORY  C ON A.VIN = C.VIN AND A.DEALER_CODE = C.DEALER_CODE AND C.IS_WX_CONNECT=12781001 ");
        sql.append("WHERE A.VIN IS NOT NULL ");
        return DAOUtil.pageQuery(sql.toString(), list);
    }

    @Override
    public void addCustomerManageles(String id,CustomerManageDTO dto) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        String sql = "SELECT A.dealer_code,A.VIN FROM ("+CommonConstants.VM_VEHICLE+") A WHERE A.VIN=? and A.dealer_code=?";
        list.add(id);
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        List<Map> all = DAOUtil.findAll(sql, list);
        if (all.size()>0) {
            throw new ServiceBizException("VIN重复,新增车辆失败！");
        }
        VehiclePO po = new VehiclePO();
        po.set("GEAR_BOX",dto.getGearBox());
        po.set("INNER_COLOR",dto.getInnerColor());
        po.set("IS_VALID",dto.getIsValid());
        if (!StringUtils.isNullOrEmpty(dto.getIsValid())&&dto.getIsValid().equals(DictCodeConstants.DICT_IS_NO)) {
            po.set("ADJUSTER",CommonConstants.SESSION_EMPNO);
            po.set("ADJUST_DATE",new Date());
        }
        po.set("OWNER_NO",dto.getOwnerNo());
        po.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        po.set("VIN",id);
        po.set("LICENSE",dto.getLicense());
        po.set("ENGINE_NO",dto.getEngineNo());
        po.set("BRAND",dto.getBrand());
        po.set("SERIES",dto.getSeries());
        po.set("MODEL",dto.getModel());
        if (!StringUtils.isNullOrEmpty(dto.getModel())) {
            String s = "select * from tm_model where model=?";
            List<Object> a = new ArrayList<>();
            a.add(dto.getModel());
            List<Map> ss = DAOUtil.findAll(s, a);
            if (ss!=null&&ss.size()>0) {
                po.set("FUEL_TYPE",ss.get(0).get("FUEL_TYPE"));
                po.set("EXHAUST_QUANTITY",ss.get(0).get("EXHAUST_QUANTITY"));
            }
        }
        po.set("MODEL_YEAR",dto.getYearModel());
        po.set("COLOR",dto.getColors());
        po.set("PRODUCT_DATE",dto.getProductDate());
        po.set("HANDBOOK_NO",dto.getHandBookNo());
        po.set("SHIFT_TYPE",dto.getShifeType());
        po.set("IS_BINDING",dto.getIsBinding());
        po.set("BINDING_DATE",dto.getBindingDate());
        if (!StringUtils.isNullOrEmpty(dto.getFuelType())) {
            po.set("FUEL_TYPE",dto.getFuelType());
        }
        if (!StringUtils.isNullOrEmpty(dto.getVehiclePurpose())) {
            po.set("VEHICLE_PURPOSE",dto.getVehiclePurpose());
        }
        if (!StringUtils.isNullOrEmpty(dto.getBusinessKind())) {
            po.set("BUSINESS_KIND",dto.getBusinessKind());
            po.set("BUSINESS_DATE",dto.getBusinessDate());
            po.set("ENGINE_NO_OLD",dto.getEngineNoOld());
            po.set("CHANGE_ENGINE_DESC",dto.getChangeEngineDesc());
            po.set("SALES_AGENT_NAME",FrameworkUtil.getLoginInfo().getDealerName());
            po.set("CONSULTANT",dto.getConsultant());
            po.set("IS_SELF_COMPANY",dto.getIsSelfCompany());
            po.set("SALES_DATE",dto.getSalesDate());
            po.set("SALES_MILEAGE",dto.getSalesMileage());
            po.set("WRT_BEGIN_DATE",dto.getWrtBeginDate());
            po.set("WRT_BEGIN_MILEAGE",dto.getWrtBeginNileage());
            po.set("WRT_END_DATE",dto.getWrtEndinDate());
            po.set("WRT_END_MILEAGE",dto.getWrtEndNileage());
            po.set("LICENSE_DATE",dto.getLicenseDate());
        }
        po.set("MILEAGE",dto.getMileage());
        po.set("TOTAL_CHANGE_MILEAGE",dto.getTotalChangeMileage());
        po.set("CHANGE_DATE",dto.getChangeDate());
        po.set("ADD_EQUIPMENT",dto.getAddequipment());
        po.set("FIRST_IN_DATE",dto.getFirstInDate());
        po.set("KEY_NUMBER",dto.getKeynumber());
        po.set("NEXT_MAINTAIN_DATE",dto.getNerVehicleDate());
        po.set("DAILY_AVERAGE_MILEAGE",dto.getDailyAverageMileage());
        po.set("LAST_INSPECT_DATE",dto.getLastInspectDate());
        po.set("NEXT_INSPECT_DATE",dto.getNextInspectDate());
        po.set("DELIVERER",dto.getDeliverer());
        if (!StringUtils.isNullOrEmpty(dto.getDelivererGender())) {
            po.set("DELIVERER_GENDER",dto.getDelivererGender());
            po.set("DELIVERER_PHONE",dto.getDelivererPhone());
            po.set("DELIVERER_MOBILE",dto.getDelivererMobile());
        }
        if (!StringUtils.isNullOrEmpty(dto.getDelivererHobbyContact())) {
            po.set("DELIVERER_HOBBY_CONTACT",dto.getDelivererHobbyContact());
            po.set("DELIVERER_RELATION_TO_OWNER",dto.getDelivererRelationToOwner());
            po.set("DELIVERER_COMPANY",dto.getDelivererCompany());
        }
        po.set("REMARK",dto.getRemark());
        po.set("DELIVERER_CREDIT",dto.getDelivererCredit());
        po.set("DELIVERER_ADDRESS",dto.getDelivererAddress());
        po.set("ZIP_CODE",dto.getContactorZipCode());
        po.set("APACKAGE",dto.getApAckAge());
        po.set("FOUND_DATE",new Date());
        po.set("CHIEF_TECHNICIAN",dto.getChieftechnician());
        po.set("LAST_MAINTAIN_DATE",dto.getLastMaintainDate());
        po.set("LAST_MAINTAIN_MILEAGE",dto.getLastMaintainMileage());
        po.set("PRE_PAY",dto.getPrePay());
        po.set("ARREARAGE_AMOUNT",dto.getArrearageAmount());
        po.set("PREPARE_JOB",dto.getPrepareJob());
        po.set("CONTRACT_NO",dto.getContractNo());
        po.set("ATTENTION_INFO",dto.getAttentionInfo());
        po.set("VIP_NO",dto.getVipNo());
        po.set("CONTRACT_DATE",dto.getContractDate());
        po.set("DISCOUNT_EXPIRE_DATE",dto.getDiscountexpireDate());
        po.set("DISCOUNT_MODE_CODE",dto.getDiscountModeCode());
        po.set("CAPITAL_ASSERTS_MANAGE_NO",dto.getCapitalAssertsManageNo());
        po.set("IS_TRACE",dto.getIstrace());
        po.set("UNIT_ATTACHCODE",dto.getUnitAttachcode());
        po.set("LAST_MAINTENANCE_DATE",dto.getLastMaintenanceDate());
        po.set("IS_UPLOAD",DictCodeConstants.DICT_IS_NO);
        po.set("IS_UPLOAD_GROUP",DictCodeConstants.DICT_IS_NO);
        if (!StringUtils.isNullOrEmpty(dto.getTraceTime())) {
            po.set("TRACE_TIME",dto.getTraceTime());
        }
        po.set("SERVICE_ADVISOR",dto.getServiceAdvisor());
        po.set("BRAND_MODEL",dto.getBrandModel());
        po.set("NO_VALID_REASON",dto.getNoValidReason());
        if (!StringUtils.isNullOrEmpty(dto.getInsuranceAdvisor())) {
            po.set("INSURANCE_ADVISOR",dto.getInsuranceAdvisor());
        }
        if (!StringUtils.isNullOrEmpty(dto.getMaintainAdvisor())) {
            po.set("MAINTAIN_ADVISOR",dto.getMaintainAdvisor());
        }
        if (!StringUtils.isNullOrEmpty(dto.getDcrcAdvisor())) {
            po.set("DCRC_ADVISOR",dto.getDcrcAdvisor());
        }
        if (!StringUtils.isNullOrEmpty(dto.getProductingArea())) {
            po.set("PRODUCTING_AREA",dto.getProductingArea());
        }
        if (!StringUtils.isNullOrEmpty(dto.getVSN())) {
            po.set("VSN",dto.getVSN());
        }
        if (!StringUtils.isNullOrEmpty(dto.getDischargeStandrd())) {
            po.set("DISCHARGE_STANDARD",dto.getDischargeStandrd());
        }
        po.set("AVCHORED",dto.getAvchored());
        if (!StringUtils.isNullOrEmpty(dto.getWaysToBuy())) {
            po.set("WAYS_TO_BUY",dto.getWaysToBuy());
        }
        if (!StringUtils.isNullOrEmpty(dto.getReplaceIntentModel())) {
            po.set("REPLACE_INTENT_MODEL",dto.getReplaceIntentModel());
        }
        if (!StringUtils.isNullOrEmpty(dto.getReplaceDate())) {
            po.set("REPLACE_DATE",dto.getReplaceDate());
        }
        if (!StringUtils.isNullOrEmpty(dto.getRebuyIntentModel())) {
            po.set("REBUY_INTENT_MODEL",dto.getRebuyIntentModel());
        }
        if (!StringUtils.isNullOrEmpty(dto.getRebuyDate())) {
            po.set("REBUY_DATE",dto.getRebuyDate());
       }
        po.saveIt();
        List<Map> subEntityList = Utility.getSubEntityList(po.getString("DEALER_CODE"), "TM_VEHICLE");
        if (subEntityList!=null) {
            if (subEntityList != null && subEntityList.size() > 0) {
                for (Map entityPO : subEntityList) {
                    TmVehicleSubclassPO poSub = new TmVehicleSubclassPO();
                    poSub.setString("MAIN_ENTITY", po.getString("DEALER_CODE"));
                    poSub.setString("DEALER_CODE", entityPO.get("CHILD_ENTITY"));
                    poSub.setString("OWNER_NO", po.getString("OWNER_NO"));
                    poSub.setString("VIN", po.getString("VIN"));

                    // comg begin 20110513 //二级网点业务-车辆子表Insert
                    poSub.setString("CONSULTANT", po.getString("CONSULTANT"));
                    poSub.setInteger("IS_SELF_COMPANY", po.getInteger("IS_SELF_COMPANY"));
                    poSub.setDate("FIRST_IN_DATE", po.getDate("FIRST_IN_DATE"));
                    poSub.setString("CHIEF_TECHNICIAN", po.getString("CHIEF_TECHNICIAN"));
                    poSub.setString("SERVICE_ADVISOR", po.getString("SERVICE_ADVISOR"));
                    poSub.setString("INSURANCE_ADVISOR", po.getString("INSURANCE_ADVISOR"));
                    poSub.setString("MAINTAIN_ADVISOR", po.getString("MAINTAIN_ADVISOR"));
                    poSub.setDate("LAST_MAINTAIN_DATE", po.getDate("LAST_MAINTAIN_DATE"));
                    poSub.setDouble("LAST_MAINTAIN_MILEAGE", po.getDouble("LAST_MAINTAIN_MILEAGE"));
                    poSub.setDate("LAST_MAINTENANCE_DATE", po.getDate("LAST_MAINTENANCE_DATE"));
                    poSub.setDouble("LAST_MAINTENANCE_MILEAGE", po.getDouble("LAST_MAINTENANCE_MILEAGE"));
                    poSub.setDouble("PRE_PAY", po.getDouble("PRE_PAY"));
                    poSub.setDouble("ARREARAGE_AMOUNT", po.getDouble("ARREARAGE_AMOUNT"));
                    poSub.setDate("DISCOUNT_EXPIRE_DATE", po.getDate("DISCOUNT_EXPIRE_DATE"));
                    poSub.setString("DISCOUNT_MODE_CODE", po.getString("DISCOUNT_MODE_CODE"));
                    poSub.setInteger("IS_SELF_COMPANY_INSURANCE",
                                     po.getInteger("IS_SELF_COMPANY_INSURANCE"));
                    poSub.setDate("ADJUST_DATE", po.getDate("ADJUST_DATE"));
                    poSub.setString("ADJUSTER", po.getString("ADJUSTER"));
                    poSub.setInteger("IS_VALID", po.getInteger("IS_VALID"));
                    poSub.setInteger("NO_VALID_REASON", po.getInteger("NO_VALID_REASON"));
                    // comg end 20110513 二级经销商服务
                    poSub.saveIt();
                }  
            }
        }
        
        //车辆备注
        
        VehicleMemoPO tmVehicleMemoPO = new VehicleMemoPO();
        tmVehicleMemoPO.set("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        tmVehicleMemoPO.set("Vin",dto.getVin());
        tmVehicleMemoPO.set("MEMO_INFO",dto.getMemoInfo());
        tmVehicleMemoPO.saveIt();
    }

    @Override
    public List<Map> queryByFixspellExports(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT distinct A.DEALER_CODE,A.OWNER_NO, A.OWNER_PROPERTY, A.OWNER_NAME, A.ADDRESS,A.MEMBER_NO,A.CT_CODE,A.CERTIFICATE_NO, ");
        sql.append(" case when (A.OWNER_PROPERTY = ?) and  (A.DECISION_MARKER_PHONE is not null)  then DECISION_MARKER_PHONE ");
        sql.append(" when (A.OWNER_PROPERTY = ?) and (A.PHONE is not null) then PHONE else null end PHONE,");
        sql.append(" case when (A.OWNER_PROPERTY =?) and (A.DECISION_MARKER_MOBILE is not null)  then DECISION_MARKER_MOBILE ");
        sql.append(" when (A.OWNER_PROPERTY =?) and (A.MOBILE is not null) then MOBILE else null end MOBILE, ");
        sql.append(" A.CONTACTOR_NAME, A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.ZIP_CODE,A.CREATED_BY, ");
        sql.append(" COALESCE(A.ARREARAGE_AMOUNT,0) as ARREARAGE_AMOUNT,");
        sql.append(" case when B.MEMBER_STATUS=? then 12781001 else 12781002 end MEMBER_STATUS");
        sql.append(" FROM ("+CommonConstants.VM_OWNER+") A LEFT JOIN ("+CommonConstants.VM_MEMBER+") B ON B.CUSTOMER_NO=A.OWNER_NO AND A.DEALER_CODE=B.DEALER_CODE and B.MEMBER_STATUS=?");
        sql.append(" AND A.CERTIFICATE_NO = B.CERTIFICATE_NO   WHERE 1=1 ");
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_COMPANY);
        list.add(DictCodeConstants.DICT_OWNER_PROPERTY_PERSONAL);
        list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        list.add(DictCodeConstants.DICT_MEMBER_STATUS_NORMAL);
        if (!StringUtils.isNullOrEmpty(id)) {
            sql.append(" and A.OWNER_NO like ?");
            list.add("%"+id+"%");
        } 
        sql.append(" ORDER BY A.OWNER_NO DESC ");
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public List<Map> queryByNoOrSpellForPageQueryExports(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT A.FOUND_DATE,A.WX_UNBUNDLING_DATE,C.IS_WX_CONNECT,C.MOBILE_PHONE, ");
        sql.append(" A.NO_VALID_REASON,A.KEY_NUMBER,A.DEALER_CODE,A.VIN,A.AVCHORED,A.VSN,A.DISCHARGE_STANDARD,A.EXHAUST_QUANTITY,A.OWNER_NO,");
        sql.append(" A.IS_BINDING,A.BINDING_DATE,A.UNIT_ATTACHCODE ,LICENSE, ENGINE_NO, BRAND, SERIES, MODEL, DELIVERER, DELIVERER_PHONE,");
        sql.append(" DELIVERER_MOBILE,LAST_MAINTAIN_DATE,SERVICE_ADVISOR,CONSULTANT,A.CREATED_BY ,COALESCE(A.ARREARAGE_AMOUNT,0)  as ARREARAGE_AMOUNT,A.PRODUCTING_AREA,");
        sql.append(" B.OWNER_PROPERTY,B.OWNER_NAME,B.ADDRESS,B.MEMBER_NO,B.CONTACTOR_NAME,B.CONTACTOR_PHONE,B.CONTACTOR_MOBILE,B.ZIP_CODE, ");
        sql.append(" case when (B.DECISION_MARKER_PHONE is null) and (B.PHONE is not null) then PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is  null) then B.DECISION_MARKER_PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is not null) and (B.DECISION_MARKER_PHONE=B.PHONE) then PHONE ");
        sql.append(" when (B.DECISION_MARKER_PHONE is not null) and (B.PHONE is not null) and (B.DECISION_MARKER_PHONE<>B.PHONE)then trim(PHONE)||' '||trim(B.DECISION_MARKER_PHONE) ");
        sql.append(" else null end PHONE,case");
        sql.append(" when (B.DECISION_MARKER_MOBILE is null) and (B.MOBILE is not null) then MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is  null) then B.DECISION_MARKER_MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is not null) and (B.DECISION_MARKER_MOBILE=B.MOBILE) then MOBILE ");
        sql.append(" when (B.DECISION_MARKER_MOBILE is not null) and (B.MOBILE is not null) and (B.DECISION_MARKER_MOBILE<>B.MOBILE) then trim(MOBILE)||' '||trim(B.DECISION_MARKER_MOBILE) ");
        sql.append(" else null end MOBILE FROM (SELECT * FROM ("+CommonConstants.VM_OWNER+") B WHERE 1 = 1  AND B.DEALER_CODE =?");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        if (!StringUtils.isNullOrEmpty(id)) {
            sql.append(" and B.OWNER_NO like ?");
            list.add("%"+id+"%");
        }
        sql.append(" ORDER BY OWNER_NO DESC ) B");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VEHICLE+") A ON B.OWNER_NO = A.OWNER_NO AND B.DEALER_CODE = A.DEALER_CODE ");
        sql.append(" LEFT JOIN TM_WX_HISTORY  C ON A.VIN = C.VIN AND A.DEALER_CODE = C.DEALER_CODE AND C.IS_WX_CONNECT=12781001 ");
        sql.append("WHERE A.VIN IS NOT NULL ");
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public void queryRepairOrderByDateStand(String id) throws ServiceBizException {
        List<Object> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer("SELECT (CASE WHEN (SELECT COUNT(B.RO_NO) FROM TT_RO_LABOUR B  WHERE  ");
        sql.append(" A.DEALER_CODE=B.DEALER_CODE and A.RO_NO=B.RO_NO and B.CHARGE_PARTITION_CODE='S')>0 or ");
        sql.append(" (SELECT COUNT(C.RO_NO) FROM TT_RO_ADD_ITEM C WHERE A.DEALER_CODE=C.DEALER_CODE and ");
        sql.append(" A.RO_NO=C.RO_NO and C.CHARGE_PARTITION_CODE='S')>0 or ");
        sql.append(" (SELECT COUNT(D.RO_NO) FROM TT_RO_REPAIR_PART D WHERE A.DEALER_CODE=D.DEALER_CODE and ");
        sql.append(" A.RO_NO=D.RO_NO and D.CHARGE_PARTITION_CODE='S')>0 THEN "+DictCodeConstants.DICT_IS_YES);
        sql.append(" ELSE "+DictCodeConstants.DICT_IS_NO+ " END) AS HAVE_CLAIM_TAG,");
        sql.append(" A.DEALER_CODE,A.RO_NO,A.RO_TYPE,A.RO_CREATE_DATE, A.REPAIR_TYPE_CODE,A.TEST_DRIVER,");
        sql.append(" A.SERVICE_ADVISOR, A.SERVICE_ADVISOR_ASS,A.RO_STATUS,A.LOCK_USER,A.CHIEF_TECHNICIAN,");
        sql.append(" A.IN_MILEAGE,A.OUT_MILEAGE,A.DELIVERER, (CASE WHEN ");
        sql.append(" (SELECT count(CC.RO_NO) FROM TT_RO_LABOUR  CC where CC.DEALER_CODE = A.DEALER_CODE AND CC.RO_NO = A.RO_NO ");
        sql.append(" AND CC.REPAIR_TYPE_CODE IN ('BPWX','ZHWX','QTWX','BJWX','PQWX'))>0 or ");
        sql.append(" (SELECT count(AA.RO_NO) FROM TT_RO_REPAIR_PART AA where AA.DEALER_CODE = A.DEALER_CODE AND AA.RO_NO = A.RO_NO");
        sql.append(" AND AA.REPAIR_TYPE_CODE IN ('BPWX','ZHWX','QTWX','BJWX','PQWX'))>0 ");
        sql.append(" THEN  "+DictCodeConstants.DICT_IS_NO + " ELSE "+ DictCodeConstants.DICT_IS_YES + " END) as MACHINE_RO_NO ,");
        sql.append(" A.DELIVERER_PHONE,A.DELIVERER_MOBILE,");
        sql.append(" A.INSURATION_CODE,A.ESTIMATE_NO,A.OWNER_NO, A.OWNER_NAME, A.OWNER_PROPERTY, A.LICENSE, ");
        sql.append(" A.VIN,A.ENGINE_NO, A.BRAND, A.SERIES, A.MODEL, A.END_TIME_SUPPOSED, A.FOR_BALANCE_TIME, ");
        sql.append(" A.DELIVERY_DATE,A.COMPLETE_TIME, A.RECOMMEND_CUSTOMER_NAME, A.REMARK,A.REMARK1, ");
        sql.append(" A.LABOUR_AMOUNT, A.REPAIR_PART_AMOUNT,A.SALES_PART_AMOUNT,A.ADD_ITEM_AMOUNT,");
        sql.append(" A.OVER_ITEM_AMOUNT,A.REPAIR_AMOUNT,A.BALANCE_AMOUNT,A.RECEIVE_AMOUNT, '' as SALES_DATE,");
        sql.append(" BA.BALANCE_CLOSE,BA.BALANCE_CLOSE_TIME, A.DELIVERY_TAG, BA.PAY_OFF,BA.BALANCE_HANDLER,");
        sql.append(" BA.PRINT_BALANCE_TIME,");
        sql.append(" CASE A.QUOTE_END_ACCURATE WHEN 12781001 THEN 12781001 ELSE 12781002 END AS QUOTE_END_ACCURATE,");
        sql.append(" CASE A.IS_SYSTEM_QUOTE WHEN 12781001 THEN 12781001 ELSE 12781002 END AS FIRST_QUOTE_END_ACCURATE,");
        sql.append(" Coalesce(A.CUSTOMER_PRE_CHECK,12781002) AS CUSTOMER_PRE_CHECK,");
        sql.append(" Coalesce(A.CHECKED_END,12781002) AS CHECKED_END,Coalesce(A.EXPLAINED_BALANCE_ACCOUNTS,12781002) AS EXPLAINED_BALANCE_ACCOUNTS, BA.BALANCE_TIME,");
        sql.append(" ' ' as E_MAIL, ' ' as ADDRESS, ' ' as ZIP_CODE, ' ' as CONTACTOR_NAME, ' ' as CONTACTOR_ADDRESS, ' ' as CONTACTOR_ZIP_CODE,");
        sql.append(" ' ' as CONTACTOR_EMAIL,A.COMPLETE_TAG,A.WAIT_INFO_TAG,A.WAIT_PART_TAG, ' ' as PROVINCE, ' ' as CITY, ' ' as DISTRICT, ' ' as VIP_NO,");
        sql.append(" TECH.TECHNICIAN AS TECHNICIAN_UNITE,TECH.LABOUR_NAME AS LABOUR_NAME_UNITE FROM TT_REPAIR_ORDER A ");
        sql.append(" LEFT JOIN TT_BALANCE_ACCOUNTS BA ON A.DEALER_CODE = BA.DEALER_CODE AND BA.RO_NO = A.RO_NO and a.D_KEY = ba.D_KEY AND BA.IS_RED = ");
        sql.append(DictCodeConstants.DICT_IS_NO);
        sql.append(" LEFT JOIN TT_TECHNICIAN_I TECH ON TECH.DEALER_CODE=A.DEALER_CODE AND TECH.RO_NO=A.RO_NO AND TECH.D_KEY= ");
        sql.append(CommonConstants.D_KEY);
        sql.append(" WHERE   A.DEALER_CODE=? AND A.D_KEY=? ");
        list.add(FrameworkUtil.getLoginInfo().getDealerCode());
        list.add(CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(id)) {
            sql.append(" and A.VIN like ?");
            list.add("%"+id+"%");
        }
        List<Map> findAll = DAOUtil.findAll(sql.toString(), list);
        if (findAll.size()>0&&findAll!=null) {
            throw new ServiceBizException("该车辆有维修记录，不能删除！");
        }
    }

    @Override
    public void performExecute(String id) throws ServiceBizException {
        /*
         * 如果此车辆的客户编号为空的才能进行删除，销售出库的车辆不能删除
         */
        Map map = null;
        String s = "select * from tm_vehicle where vin='"+id+"'";
        List<Map> list = Utility.getVehicleSubclassList(FrameworkUtil.getLoginInfo().getDealerCode(), DAOUtil.findAll(s, null));
        if(list!=null && list.size()>0){
            for (int i = 0; i < list.size(); i++)
            {
                map = list.get(i);
            }
        }
        if(CommonConstants.DEALER_DEFAULT_OWNER_NO.equals(map.get("OWNER_NO"))) {
            String a = "select * from tm_vs_stock where vin='"+id+"'";
            List<Map> all = DAOUtil.findAll(a, null);
            if(all!=null && all.size()>0){
                if(all.get(0).get("STOCK_OUT_TYPE")!=null){
                    if(!String.valueOf(DictCodeConstants.DICT_STOCK_OUT_TYPE_OTHER).equals(all.get(0).get("STOCK_OUT_TYPE").toString())){
                        throw new ServiceBizException(CommonErrorConstant.MSG_ONLY_ALOW_DELETE_4S_VEHICLE);
                    }   
                }else{
                    throw new ServiceBizException("不能删除未出库的4S店车辆!");
                }
            }
        }
      //非本站销售的车辆
        if(map.get("CUSTOMER_NO")==null || "".equals(map.get("CUSTOMER_NO"))){
            TmVehicleSubclassPO.delete("VIN=?", map.get("VIN"));//二级网点业务
            VehiclePO.delete("VIN=?", map.get("VIN"));
            TtServiceInsurancePO.delete("VIN=?", map.get("VIN"));
        }else{
            throw new ServiceBizException("此车辆是销售出库产生的，不能删除!");
        }
    }
}
