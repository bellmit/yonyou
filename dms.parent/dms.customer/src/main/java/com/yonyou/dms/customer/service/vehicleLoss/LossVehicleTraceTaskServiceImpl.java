
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : LossVehicleTraceTaskServiceImpl.java
*
* @Author : sqh
*
* @Date : 2017年3月29日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年3月29日    sqh    1.0
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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* TODO 
* @author sqh
* @date 2017年3月29日
*/
@Service
public class LossVehicleTraceTaskServiceImpl implements LossVehicleTraceTaskService{

    @Override
    public PageInfoDto queryLossVehicleTraceTask(Map<String, String> queryParam) throws ServiceBizException {

        StringBuffer sb = new StringBuffer();
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" select distinct A.TRACE_STATUS,A.TRACE_TYPE,A.TASK_REMARK,A.TRACE_ITEM_ID,A.INPUTER,A.INPUT_DATE,A.CLOSE_DATE,A.VER,db.dealer_shortname,u.user_name, ");
        sb.append(" C.OWNER_NO,C.OWNER_NAME,C.GENDER,C.PHONE,C.MOBILE,C.E_MAIL,C.ADDRESS,B.LICENSE,B.VIN,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME, ");
        sb.append(" B.DELIVERER,B.SALES_DATE,DELIVERER_PHONE,DELIVERER_MOBILE,DELIVERER_ADDRESS,Coalesce(A.TRACE_TAG,12781002) TRACE_TAG,A.TRANCER, d.is_return_factory IS_RETURN_FACTORY, ");
        sb.append(" A.DEALER_CODE FROM TT_LOSS_VEHICLE_TRACE_TASK A left join ");
        sb.append("("+ CommonConstants.VM_VEHICLE +") B ");
        sb.append(" ON A.DEALER_CODE = B.DEALER_CODE AND A.VIN = B.VIN LEFT JOIN ");
        sb.append("("+ CommonConstants.VM_OWNER +") C ");
        sb.append(" ON A.DEALER_CODE = C.DEALER_CODE AND A.OWNER_NO = C.OWNER_NO  left join TT_LOSS_VEHICLE_TRACE e on e.DEALER_CODE = a.DEALER_CODE and e.item_id=a.loss_vehicle_trace_item_id ");
        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
        sb.append("  LEFT JOIN tm_user U ON U.USER_ID = A.TRANCER ");
        sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
        sb.append(" left join ( select VL.REMIND_ID,vl.DEALER_CODE,vl.vin,vl.is_return_factory from TT_VEHICLE_LOSS_REMIND vl ");
        sb.append(" INNER JOIN ( select max(f.remind_id) AS REMIND_ID from TT_VEHICLE_LOSS_REMIND f where 1=1 group by f.DEALER_CODE,f.vin )  S2 ON S2.REMIND_ID=VL.REMIND_ID ");
        sb.append(" where 1=1 ) d on a.DEALER_CODE = d.DEALER_CODE and e.vin=d.vin where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
            sb.append(" AND  A.DEALER_CODE = ? ");
            queryList.add(queryParam.get("dealer_code"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" AND  B.VIN like ? ");
            queryList.add("%"+ queryParam.get("vin") +"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
            sb.append(" AND  C.OWNER_NO = ? ");
            queryList.add(queryParam.get("ownerNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sb.append(" AND  C.OWNER_NAME = ? ");
            queryList.add(queryParam.get("ownerName"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("trancer"))) {
            sb.append(" AND  A.TRANCER = ? ");
            queryList.add(queryParam.get("trancer"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sb.append(" AND  B.LICENSE = ? ");
            queryList.add(queryParam.get("license"));
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
        if (!StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
            sb.append(" AND  A.TRACE_STATUS = ? ");
            queryList.add(queryParam.get("traceStatus"));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
        
        return pageInfoDto;
    }
    
    @Override
    public List<Map> exportLossVehicleTraceTask(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" select distinct A.TRACE_STATUS,A.TRACE_TYPE,A.TASK_REMARK,A.TRACE_ITEM_ID,A.INPUTER,A.INPUT_DATE,A.CLOSE_DATE,A.VER,db.dealer_shortname,u.user_name, ");
        sb.append(" C.OWNER_NO,C.OWNER_NAME,C.GENDER,C.PHONE,C.MOBILE,C.E_MAIL,C.ADDRESS,B.LICENSE,B.VIN,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME, ");
        sb.append(" B.DELIVERER,B.SALES_DATE,DELIVERER_PHONE,DELIVERER_MOBILE,DELIVERER_ADDRESS,Coalesce(A.TRACE_TAG,12781002) TRACE_TAG,A.TRANCER, d.is_return_factory IS_RETURN_FACTORY, ");
        sb.append(" A.DEALER_CODE FROM TT_LOSS_VEHICLE_TRACE_TASK A left join ");
        sb.append("("+ CommonConstants.VM_VEHICLE +") B ");
        sb.append(" ON A.DEALER_CODE = B.DEALER_CODE AND A.VIN = B.VIN LEFT JOIN ");
        sb.append("("+ CommonConstants.VM_OWNER +") C ");
        sb.append(" ON A.DEALER_CODE = C.DEALER_CODE AND A.OWNER_NO = C.OWNER_NO  left join TT_LOSS_VEHICLE_TRACE e on e.DEALER_CODE = a.DEALER_CODE and e.item_id=a.loss_vehicle_trace_item_id ");
        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
        sb.append("  LEFT JOIN tm_user U ON U.USER_ID = A.TRANCER ");
        sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
        sb.append(" left join ( select VL.REMIND_ID,vl.DEALER_CODE,vl.vin,vl.is_return_factory from TT_VEHICLE_LOSS_REMIND vl ");
        sb.append(" INNER JOIN ( select max(f.remind_id) AS REMIND_ID from TT_VEHICLE_LOSS_REMIND f where 1=1 group by f.DEALER_CODE,f.vin )  S2 ON S2.REMIND_ID=VL.REMIND_ID ");
        sb.append(" where 1=1 ) d on a.DEALER_CODE = d.DEALER_CODE and e.vin=d.vin where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
            sb.append(" AND  A.DEALER_CODE = ? ");
            queryList.add(queryParam.get("dealer_code"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" AND  B.VIN like ? ");
            queryList.add("%"+ queryParam.get("vin") +"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
            sb.append(" AND  C.OWNER_NO = ? ");
            queryList.add(queryParam.get("ownerNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sb.append(" AND  C.OWNER_NAME = ? ");
            queryList.add(queryParam.get("ownerName"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("trancer"))) {
            sb.append(" AND  A.TRANCER = ? ");
            queryList.add(queryParam.get("trancer"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sb.append(" AND  B.LICENSE = ? ");
            queryList.add(queryParam.get("license"));
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
        if (!StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
            sb.append(" AND  A.TRACE_STATUS = ? ");
            queryList.add(queryParam.get("traceStatus"));
        }
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
		return resultList;
    }

    @SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append(" SELECT aa.*,C.OWNER_NAME,C.PHONE,C.MOBILE,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME from (SELECT '定期保养提醒' description,cast(D.REMIND_ID as char(14))  REMIND_ID, ");
		stringBuffer.append(" D.DEALER_CODE,D.OWNER_NO,D.REMIND_DATE,D.REMIND_CONTENT,D.REMINDER,D.CUSTOMER_FEEDBACK,D.REMIND_STATUS,D.VIN ");
		stringBuffer.append(" FROM  TT_VEHICLE_LOSS_REMIND D where D.D_KEY = 0 ");
			stringBuffer.append(" AND D.DEALER_CODE  = ?  ");
			queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (D.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or D.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "REMIND_DATE", null);
		stringBuffer.append(" ) aa left join ("+ CommonConstants.VM_VEHICLE + ") B on B.DEALER_CODE = aa.DEALER_CODE and B.VIN = aa.VIN ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" left join ("+ CommonConstants.VM_OWNER +") C");
		stringBuffer.append(" on aa.DEALER_CODE = c.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO ");
		stringBuffer.append(" union all SELECT '客户生日提醒' description,cast(B.REMIND_ID as char(14))  REMIND_ID, ");
		stringBuffer.append(" A.DEALER_CODE,B.OWNER_NO,B.REMIND_DATE,B.REMIND_CONTENT,B.REMINDER,B.CUSTOMER_FEEDBACK,B.REMIND_STATUS,B.VIN,A.CUSTOMER_NAME OWNER_NAME,A.CONTACTOR_PHONE PHONE, ");
		stringBuffer.append(" A.CONTACTOR_MOBILE MOBILE,C.BRAND,tb.brand_name,C.SERIES,ts.series_name,C.MODEL,tm.MODEL_NAME FROM TM_POTENTIAL_CUSTOMER A LEFT JOIN TT_OWNER_BIRTHDAY_REMIND B ON A.DEALER_CODE = B.DEALER_CODE ");
		
		stringBuffer.append(" AND A.CUSTOMER_NO = B.OWNER_NO left join ("+ CommonConstants.VM_VEHICLE + ") C on B.DEALER_CODE = a.DEALER_CODE and C.VIN = B.VIN  ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON C.DEALER_CODE=tb.DEALER_CODE AND C.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON C.DEALER_CODE = ts.DEALER_CODE  AND C.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON C.DEALER_CODE = tm.DEALER_CODE  AND C.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" WHERE A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (B.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or B.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		stringBuffer.append(" AND A.D_KEY=0 AND A.CUSTOMER_TYPE=10181001 ");
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "REMIND_DATE", null);
		
		
		stringBuffer.append(" union all SELECT '销售回访结果' description,cast(A.TRACE_TASK_ID as char(14))  REMIND_ID,A.DEALER_CODE,A.CUSTOMER_NO OWNER_NO ,A.INPUT_DATE REMIND_DATE,QU.QUESTIONNAIRE_NAME REMIND_CONTENT,");
		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.CUSTOMER_NAME OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE, ");
		stringBuffer.append(" A.BRAND,tb.brand_name,A.SERIES,ts.series_name,A.MODEL,tm.MODEL_NAME FROM  TT_SALES_TRACE_TASK A LEFT JOIN TM_CUSTOMER E ON A.CUSTOMER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND A.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND A.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND A.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE ");
		stringBuffer.append(" Left join (select distinct Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME from ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N inner join TT_SALES_TRACE_TASK_QUESTION Q on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE) ");
		stringBuffer.append(" QU on QU.TRACE_TASK_ID=A.TRACE_TASK_ID WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (A.CUSTOMER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", null);
		stringBuffer.append(" union all SELECT '回访结果' description,A.RO_NO REMIND_ID,A.DEALER_CODE,H.OWNER_NO,A.INPUT_DATE REMIND_DATE,A.REMARK REMIND_CONTENT,A.TRANCER REMINDER, ");
		stringBuffer.append(" '' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.OWNER_NAME,A.DELIVERER_MOBILE PHONE,A.DELIVERER_PHONE MOBILE,A.BRAND,tb.brand_name,A.SERIES,ts.series_name,A.MODEL,tm.MODEL_NAME FROM  TT_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TT_REPAIR_ORDER E ON A.RO_NO=E.RO_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN Tt_Technician_I TECH ON TECH.DEALER_CODE=E.DEALER_CODE AND TECH.RO_NO=E.RO_NO AND TECH.D_KEY=0 ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND A.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND A.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND A.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO ");
		stringBuffer.append(" AND A.DEALER_CODE=F.DEALER_CODE WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (H.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", "A");
		stringBuffer.append(" union all SELECT '流失报警回访结果' description,cast(A.TRACE_ITEM_ID as char(14))  REMIND_ID,A.DEALER_CODE,A.OWNER_NO ,A.INPUT_DATE REMIND_DATE,QU.QUESTIONNAIRE_NAME REMIND_CONTENT, ");
		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,F.OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE,H.BRAND,tb.brand_name,H.SERIES,ts.series_name,H.MODEL,tm.MODEL_NAME FROM  TT_LOSS_VEHICLE_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TM_CUSTOMER E ON A.OWNER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN  AND A.DEALER_CODE = H.DEALER_CODE ");
		stringBuffer.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON H.DEALER_CODE=tb.DEALER_CODE AND H.BRAND=tb.BRAND_CODE ");
		stringBuffer.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON H.DEALER_CODE = ts.DEALER_CODE  AND H.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
		stringBuffer.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON H.DEALER_CODE = tm.DEALER_CODE  AND H.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
		stringBuffer.append(" LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE Left join(select distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME ");
		stringBuffer.append(" from ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N inner join TT_LOSS_VHCL_TRCE_TASK_QN Q on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE ) QU   on QU.TRACE_TASK_ID=A.TRACE_ITEM_ID ");
		stringBuffer.append(" WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (A.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", null);

		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		
		return resultList;
	}

	@Override
	public List<Map> querySalesOrderTrackTask(Map<String, String> queryParam) throws ServiceBizException {
		 StringBuffer sb = new StringBuffer();
	        List<Object> queryList = new ArrayList<Object>();
	        sb.append(" select distinct * from((select A.DEALER_CODE,B.SO_NO,B.CUSTOMER_NO as PO_CUSTOMER_NO, B.BUSINESS_TYPE,CASE WHEN (A.TRACE_STATUS= " );
	        sb.append("("+ DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END +")) OR ( A.TRACE_STATUS= ("+ DictCodeConstants.DICT_TRACING_STATUS_SUCCEED_END +")) THEN ("+ DictCodeConstants.DICT_IS_YES +") ");
	        sb.append(" ELSE ("+ DictCodeConstants.DICT_IS_NO +") END AS IS_SELECTED,A.VER,A.CUSTOMER_NAME,A.CUSTOMER_NO,A.VIN,A.BRAND,A.SERIES,A.MODEL,tb.brand_name,ts.series_name,tm.MODEL_NAME,A.TRANCER,A.TRACE_TASK_ID,A.INPUT_DATE,A.TRACE_STATUS,A.TASK_REMARK,C.GENDER,C.PROVINCE,C.CITY,");
	        sb.append(" C.DISTRICT,C.ADDRESS,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE ,VE.SALES_DATE AS STOCK_OUT_DATE ,E.QUESTIONNAIRE_NAME,F.ORG_CODE,F.ORG_NAME,C.CUSTOMER_TYPE,C.E_MAIL,C.SOLD_BY,C.FAX,C.CERTIFICATE_NO,C.EDUCATION_LEVEL,C.HOBBY,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.ORGAN_TYPE,C.ORGAN_TYPE_CODE,C.VOCATION_TYPE,C.POSITION_NAME,C.AGE_STAGE,C.BUY_PURPOSE,C.CHOICE_REASON,C.BUY_REASON,C.CUS_SOURCE,C.FAMILY_INCOME ");
	        sb.append(" ,B.CONFIRMED_DATE,OWNER_MARRIAGE,CONTACTOR_NAME from TT_SALES_TRACE_TASK A inner join TT_SALES_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.SO_NO = B.SO_NO ");
	        sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON A.DEALER_CODE=tb.DEALER_CODE AND A.BRAND=tb.BRAND_CODE ");
	        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON A.DEALER_CODE = ts.DEALER_CODE  AND A.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
	        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON A.DEALER_CODE = tm.DEALER_CODE  AND A.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
	        sb.append(" inner join TM_CUSTOMER C ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_NO = C.CUSTOMER_NO inner join ("+ CommonConstants.VM_VEHICLE + ") ve  on A.DEALER_CODE = ve.DEALER_CODE AND A.CUSTOMER_NO = ve.CUSTOMER_NO AND A.VIN=VE.VIN ");
	        sb.append(" left join TT_SALES_TRACE_TASK_QUESTION D ON A.DEALER_CODE = D.DEALER_CODE AND A.TRACE_TASK_ID = D.TRACE_TASK_ID left JOIN ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") E ON A.DEALER_CODE = E.DEALER_CODE AND D.QUESTIONNAIRE_CODE = E.QUESTIONNAIRE_CODE ");
	        sb.append(" inner join (SELECT A.ORG_CODE,A.ORG_NAME,A.DEALER_CODE,B.USER_ID FROM TM_ORGANIZATION A ,TM_USER B  WHERE A.DEALER_CODE=B.DEALER_CODE AND A.ORG_CODE=B.ORG_CODE AND A.DEALER_CODE= ? ");
	        queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
	        sb.append(" ) F ON C.SOLD_BY = F.USER_ID AND C.DEALER_CODE=F.DEALER_CODE WHERE A.DEALER_CODE = ? ");
	        queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
	        if (!StringUtils.isNullOrEmpty(queryParam.get("TRACE_STATUS")) && !StringUtils.isNullOrEmpty(queryParam.get("IS_OPERATOR_MSG")) && queryParam.get("IS_OPERATOR_MSG").equals(DictCodeConstants.DICT_IS_YES)) {
	        	sb.append(" AND (A.TRACE_STATUS = ("+ DictCodeConstants.DICT_TRACING_STATUS_NO +") or A.TRACE_STATUS = ("+ DictCodeConstants.DICT_TRACING_STATUS_CONTINUE +")) ");
			}else{
				if (!StringUtils.isNullOrEmpty(queryParam.get("TRACE_STATUS"))){
					sb.append(" AND A.TRACE_STATUS = ? ");
					queryList.add(queryParam.get("TRACE_STATUS"));
				}
			}
//	        sb.append(Utility.getLikeCond("A", "CUSTOMER_NAME", queryParam.get("CUSTOMER_NAME"), "AND"));
//	        sb.append(Utility.getLikeCond("A", "CUSTOMER_NO", queryParam.get("CUSTOMER_NO"), "AND"));
	        sb.append(Utility.getLikeCond("A", "VIN", queryParam.get("VIN"), "AND"));
	        sb.append(Utility.getLikeCond("A", "TRANCER", queryParam.get("TRANCER"), "AND"));
//	        sb.append(Utility.getLikeCond("E", "QUESTIONNAIRE_NAME", queryParam.get("QUESTIONNAIRE_NAME"), "AND"));
//	        sb.append(Utility.getDateCond("VE", "SALES_DATE", queryParam.get("SALES_DATE"), queryParam.get("SALES_DATE")));
//	        sb.append(Utility.getDateCond("B", "CONFIRMED_DATE", queryParam.get("BEGIN_CONFIRMED_DATE"), queryParam.get("END_CONFIRMED_DATE")));
	        sb.append(" )) B ");
	        
	        @SuppressWarnings("rawtypes")
			List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
			return resultList;
	}

	@Override
	public List<Map> queryLossVehicleTraceTask1(Map<String, String> queryParam) throws ServiceBizException {
		 StringBuffer sb = new StringBuffer();
	        List<Object> queryList = new ArrayList<Object>();
	        sb.append(" select distinct A.TRACE_STATUS,A.TRACE_TYPE,A.TASK_REMARK,A.TRACE_ITEM_ID,A.INPUTER,A.INPUT_DATE,A.CLOSE_DATE,A.VER,db.dealer_shortname,u.user_name, ");
	        sb.append(" C.OWNER_NO,C.OWNER_NAME,C.GENDER,C.PHONE,C.MOBILE,C.E_MAIL,C.ADDRESS,B.LICENSE,B.VIN,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME, ");
	        sb.append(" B.DELIVERER,B.SALES_DATE,DELIVERER_PHONE,DELIVERER_MOBILE,DELIVERER_ADDRESS,Coalesce(A.TRACE_TAG,12781002) TRACE_TAG,A.TRANCER, Coalesce(d.is_return_factory,12781002) IS_RETURN_FACTORY, ");
	        sb.append(" A.DEALER_CODE FROM TT_LOSS_VEHICLE_TRACE_TASK A left join ");
	        sb.append("("+ CommonConstants.VM_VEHICLE +") B ");
	        sb.append(" ON A.DEALER_CODE = B.DEALER_CODE AND A.VIN = B.VIN LEFT JOIN ");
	        sb.append("("+ CommonConstants.VM_OWNER +") C ");
	        sb.append(" ON A.DEALER_CODE = C.DEALER_CODE AND A.OWNER_NO = C.OWNER_NO  left join TT_LOSS_VEHICLE_TRACE e on e.DEALER_CODE = a.DEALER_CODE and e.item_id=a.loss_vehicle_trace_item_id ");
	        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
	        sb.append("  LEFT JOIN tm_user U ON U.USER_ID = A.TRANCER ");
	        sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
	        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code");
	        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code");
	        sb.append(" left join ( select VL.REMIND_ID,vl.DEALER_CODE,vl.vin,vl.is_return_factory from TT_VEHICLE_LOSS_REMIND vl ");
	        sb.append(" INNER JOIN ( select max(f.remind_id) AS REMIND_ID from TT_VEHICLE_LOSS_REMIND f where 1=1 group by f.DEALER_CODE,f.vin )  S2 ON S2.REMIND_ID=VL.REMIND_ID ");
	        sb.append(" where 1=1 ) d on a.DEALER_CODE = d.DEALER_CODE and e.vin=d.vin where 1=1 ");
	        if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
	            sb.append(" AND  A.DEALER_CODE = ? ");
	            queryList.add(queryParam.get("dealer_code"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
	            sb.append(" AND  B.VIN like ? ");
	            queryList.add("%"+ queryParam.get("vin") +"%");
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
	            sb.append(" AND  C.OWNER_NO = ? ");
	            queryList.add(queryParam.get("ownerNo"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
	            sb.append(" AND  C.OWNER_NAME = ? ");
	            queryList.add(queryParam.get("ownerName"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("trancer"))) {
	            sb.append(" AND  A.TRANCER = ? ");
	            queryList.add(queryParam.get("trancer"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
	            sb.append(" AND  B.LICENSE = ? ");
	            queryList.add(queryParam.get("license"));
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
	        if (!StringUtils.isNullOrEmpty(queryParam.get("traceStatus"))) {
	            sb.append(" AND  A.TRACE_STATUS = ? ");
	            queryList.add(queryParam.get("traceStatus"));
	        }
	        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
			return resultList;
	}

	@Override
	public List<Map> queryLossVehicleTraceTaskByVin(String vin) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" select distinct A.TRACE_STATUS,A.TRACE_TYPE,A.TASK_REMARK,A.TRACE_ITEM_ID,A.INPUTER,A.INPUT_DATE,A.CLOSE_DATE,A.VER,db.dealer_shortname,u.user_name, ");
        sb.append(" C.OWNER_NO,C.OWNER_NAME,C.GENDER,C.PHONE,C.MOBILE,C.E_MAIL,C.ADDRESS,B.LICENSE,B.VIN,B.BRAND,tb.brand_name,B.SERIES,ts.series_name,B.MODEL,tm.MODEL_NAME, ");
        sb.append(" B.DELIVERER,B.SALES_DATE,DELIVERER_PHONE,DELIVERER_MOBILE,DELIVERER_ADDRESS,Coalesce(A.TRACE_TAG,12781002) TRACE_TAG,A.TRANCER, d.is_return_factory IS_RETURN_FACTORY, ");
        sb.append(" A.DEALER_CODE FROM TT_LOSS_VEHICLE_TRACE_TASK A left join ");
        sb.append("("+ CommonConstants.VM_VEHICLE +") B ");
        sb.append(" ON A.DEALER_CODE = B.DEALER_CODE AND A.VIN = B.VIN LEFT JOIN ");
        sb.append("("+ CommonConstants.VM_OWNER +") C ");
        sb.append(" ON A.DEALER_CODE = C.DEALER_CODE AND A.OWNER_NO = C.OWNER_NO  left join TT_LOSS_VEHICLE_TRACE e on e.DEALER_CODE = a.DEALER_CODE and e.item_id=a.loss_vehicle_trace_item_id ");
        sb.append("  LEFT JOIN TM_DEALER_BASICINFO db ON B.dealer_code=db.dealer_code  ");
        sb.append("  LEFT JOIN tm_user U ON U.USER_ID = A.TRANCER ");
        sb.append("  LEFT JOIN ( SELECT mb.brand_code,mb.brand_name,mb.dealer_code FROM tm_brand mb ) tb ON B.DEALER_CODE=tb.DEALER_CODE AND B.BRAND=tb.BRAND_CODE ");
        sb.append(" LEFT JOIN (SELECT s.series_code,s.series_name,s.dealer_code,s.BRAND_CODE FROM tm_series s) ts  ON B.DEALER_CODE = ts.DEALER_CODE  AND B.SERIES = ts.SERIES_CODE  AND ts.brand_code=tb.brand_code ");
        sb.append(" LEFT JOIN  (SELECT  m.DEALER_CODE, m.MODEL_NAME, m.MODEL_CODE, m.SERIES_CODE  FROM tm_model m) tm  ON B.DEALER_CODE = tm.DEALER_CODE  AND B.MODEL = TM.MODEL_CODE  AND ts.brand_code=tb.brand_code AND tm.series_code=ts.series_code ");
        sb.append(" left join ( select VL.REMIND_ID,vl.DEALER_CODE,vl.vin,vl.is_return_factory from TT_VEHICLE_LOSS_REMIND vl ");
        sb.append(" INNER JOIN ( select max(f.remind_id) AS REMIND_ID from TT_VEHICLE_LOSS_REMIND f where 1=1 group by f.DEALER_CODE,f.vin )  S2 ON S2.REMIND_ID=VL.REMIND_ID ");
        sb.append(" where 1=1 ) d on a.DEALER_CODE = d.DEALER_CODE and e.vin=d.vin where 1=1 ");
        if (!StringUtils.isNullOrEmpty(FrameworkUtil.getLoginInfo().getDealerCode())) {
            sb.append(" AND  A.DEALER_CODE = ? ");
            queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
        }
        if (!StringUtils.isNullOrEmpty(vin)) {
            sb.append(" AND  B.VIN like ? ");
            queryList.add("%"+ vin +"%");
        }
        System.out.print(sb);
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
		return resultList;
	}

	@Override
	public PageInfoDto QueryLossAlarmTraceTaskLog(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" select TRACE_TASK_LOG_ID,DEALER_CODE,TRACE_TASK_ID,TRACE_LOG_DATE,TRACE_LOG_DESC from tt_loss_vehicle_trace_task_log where TRACE_TASK_ID = ? ");
        queryList.add(queryParam.get("traceItemId"));
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}
	
}
