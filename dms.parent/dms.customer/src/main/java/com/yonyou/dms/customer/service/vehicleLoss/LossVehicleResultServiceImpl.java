
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : LossVehicleResultServiceImpl.java
*
* @Author : sqh
*
* @Date : 2017年3月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月31日    sqh    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.vehicleLoss;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO description
* @author sqh
* @date 2017年3月31日
*/
@Service
public class LossVehicleResultServiceImpl implements LossVehicleResultService{

    @Override
    public PageInfoDto queryLossVehicleResult(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" select A.*,Us.user_name inputerName,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME,B.COLOR,B.APACKAGE,B.EXHAUST_QUANTITY,B.VEHICLE_PURPOSE,B.BUSINESS_KIND,Coalesce(B.IS_SELF_COMPANY,12781002) IS_SELF_COMPANY,db.dealer_shortname,u.user_name, ");
        sb.append(" B.SALES_DATE,B.MILEAGE,B.FIRST_IN_DATE,B.NEXT_MAINTAIN_DATE,B.NEXT_MAINTAIN_MILEAGE,B.DELIVERER,B.DELIVERER_GENDER,B.DELIVERER_PHONE,B.DELIVERER_MOBILE,");
        sb.append(" B.DELIVERER_ADDRESS,B.LAST_MAINTAIN_DATE,B.LAST_MAINTAIN_MILEAGE,C.OWNER_NAME,C.GENDER,C.PROVINCE,C.CITY,C.DISTRICT,C.ADDRESS,C.ZIP_CODE,C.PHONE,C.MOBILE,C.BIRTHDAY,C.E_MAIL,C.CONTACTOR_NAME,");
        sb.append(" C.CONTACTOR_GENDER,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.CONTACTOR_ADDRESS,C.OWNER_PROPERTY from tt_loss_vehicle_trace_task A left join ");
        sb.append("("+ CommonConstants.VM_VEHICLE +") B ");
        sb.append(" ON (A.VIN = B.VIN AND A.DEALER_CODE = B.DEALER_CODE) LEFT JOIN ");
        sb.append("("+ CommonConstants.VM_OWNER +") C ");
        sb.append(" ON (A.OWNER_NO = C.OWNER_NO AND A.DEALER_CODE = C.DEALER_CODE) ");
        sb.append(" LEFT JOIN TM_DEALER_BASICINFO db ON B.DEALER_CODE=db.dealer_code  ");
        sb.append(" LEFT JOIN tm_user U ON U.USER_ID = A.TRANCER ");
        sb.append(" LEFT JOIN tm_user Us ON Us.USER_ID = A.INPUTER ");
        sb.append(" LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
        sb.append(" where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
            sb.append(" AND  A.DEALER_CODE = ? ");
            queryList.add(queryParam.get("dealer_code"));
        }
        sb.append(" and A.TRACE_TYPE = ");
        sb.append(CommonConstants.DICT_DCRC_TRACE_TYPE_LOSS_VEHICLE_ALARM);
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and  A.VIN like ? ");
            queryList.add("%"+ queryParam.get("vin") +"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sb.append(" and A.license like ? ");
            queryList.add(queryParam.get("license"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("trancer"))) {
            sb.append(" AND  A.TRANCER = ? ");
            queryList.add(queryParam.get("trancer"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("inputer"))) {
            sb.append(" and A.INPUTER = ? ");
            queryList.add(queryParam.get("inputer"));
        }
      
        if (!StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
            sb.append(" and A.TRACE_STATUS = ? ");
            queryList.add(queryParam.get("traceStatus"));
        }else{
            sb.append(" and A.TRACE_STATUS IN ( ");
            sb.append(DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END+",");
            sb.append(DictCodeConstants.DICT_TRACING_STATUS_FAIL_END+" ) ");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
            sb.append(" AND  B.BRAND = ? ");
            queryList.add(queryParam.get("brandId"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
            sb.append(" AND  B.SERIES = ? ");
            queryList.add(queryParam.get("seriesCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
            sb.append(" AND  B.MODEL = ? ");
            queryList.add(queryParam.get("modelCode"));
        }
        Utility.sqlToDate(sb,queryParam.get("InputBeginDate"), queryParam.get("InputEndDate"), "A.INPUT_DATE", "INPUT_DATE");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
        return pageInfoDto;
    }

	@Override
	public List<Map> exportLossVehicleResult(Map<String, String> queryParam) throws ServiceBizException {
	      StringBuffer sb = new StringBuffer();
	        List<Object> queryList = new ArrayList<Object>();
	        sb.append(" select A.*,Us.user_name inputerName,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME,B.COLOR,B.APACKAGE,B.EXHAUST_QUANTITY,B.VEHICLE_PURPOSE,B.BUSINESS_KIND,Coalesce(B.IS_SELF_COMPANY,12781002) IS_SELF_COMPANY,db.dealer_shortname,u.user_name, ");
	        sb.append(" B.SALES_DATE,B.MILEAGE,B.FIRST_IN_DATE,B.NEXT_MAINTAIN_DATE,B.NEXT_MAINTAIN_MILEAGE,B.DELIVERER,B.DELIVERER_GENDER,B.DELIVERER_PHONE,B.DELIVERER_MOBILE,");
	        sb.append(" B.DELIVERER_ADDRESS,B.LAST_MAINTAIN_DATE,B.LAST_MAINTAIN_MILEAGE,C.OWNER_NAME,C.GENDER,C.PROVINCE,C.CITY,C.DISTRICT,C.ADDRESS,C.ZIP_CODE,C.PHONE,C.MOBILE,C.BIRTHDAY,C.E_MAIL,C.CONTACTOR_NAME,");
	        sb.append(" C.CONTACTOR_GENDER,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,C.CONTACTOR_ADDRESS,C.OWNER_PROPERTY from tt_loss_vehicle_trace_task A left join ");
	        sb.append("("+ CommonConstants.VM_VEHICLE +") B ");
	        sb.append(" ON (A.VIN = B.VIN AND A.DEALER_CODE = B.DEALER_CODE) LEFT JOIN ");
	        sb.append("("+ CommonConstants.VM_OWNER +") C ");
	        sb.append(" ON (A.OWNER_NO = C.OWNER_NO AND A.DEALER_CODE = C.DEALER_CODE) ");
	        sb.append(" LEFT JOIN TM_DEALER_BASICINFO db ON B.DEALER_CODE=db.dealer_code  ");
	        sb.append(" LEFT JOIN tm_user U ON U.USER_ID = A.TRANCER ");
	        sb.append(" LEFT JOIN tm_user Us ON Us.USER_ID = A.INPUTER ");
	        sb.append(" LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
	        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
	        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
	        sb.append(" where 1=1 ");
	        if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
	            sb.append(" AND  A.DEALER_CODE = ? ");
	            queryList.add(queryParam.get("dealer_code"));
	        }
	        sb.append(" and A.TRACE_TYPE = ");
	        sb.append(CommonConstants.DICT_DCRC_TRACE_TYPE_LOSS_VEHICLE_ALARM);
	        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
	            sb.append(" and  A.VIN like ? ");
	            queryList.add("%"+ queryParam.get("vin") +"%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
	            sb.append(" and A.license like ? ");
	            queryList.add(queryParam.get("license"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("trancer"))) {
	            sb.append(" AND  A.TRANCER = ? ");
	            queryList.add(queryParam.get("trancer"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("inputer"))) {
	            sb.append(" and A.INPUTER = ? ");
	            queryList.add(queryParam.get("inputer"));
	        }
	      
	        if (!StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
	            sb.append(" and A.TRACE_STATUS = ? ");
	            queryList.add(queryParam.get("traceStatus"));
	        }else{
	            sb.append(" and A.TRACE_STATUS IN ( ");
	            sb.append(DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END+",");
	            sb.append(DictCodeConstants.DICT_TRACING_STATUS_FAIL_END+" ) ");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("brandId"))) {
	            sb.append(" AND  B.BRAND = ? ");
	            queryList.add(queryParam.get("brandId"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
	            sb.append(" AND  B.SERIES = ? ");
	            queryList.add(queryParam.get("seriesCode"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
	            sb.append(" AND  B.MODEL = ? ");
	            queryList.add(queryParam.get("modelCode"));
	        }
	        Utility.sqlToDate(sb,queryParam.get("InputBeginDate"), queryParam.get("InputEndDate"), "A.INPUT_DATE", "INPUT_DATE");
	        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
			return resultList;
	}

}
