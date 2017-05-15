package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SearchTestDriveDetailServiceImpl implements SearchTestDriveDetailService{
	
	@Override
	public PageInfoDto queryTestDriveDetail(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.DEALER_CODE,A.CUSTOMER_NO,A.CUSTOMER_NAME,A.CONTACTOR_PHONE,A.CONTACTOR_MOBILE,A.TEST_DRIVE_REGISTER,A.TEST_DRIVE_TYPE,A.PLACE,A.SCORE,A.SERIES_CODE,");
		sb.append(" A.TEST_DRIVE_FEEDBACK,B.SOLD_BY,B.GENDER,B.ARRIVE_TIME,C.SO_NO,C.VIN,C.STOCK_OUT_DATE,D.SERIES,E.SERIES_NAME AS SERIES_NAME_1,F.SERIES_NAME AS SERIES_NAME_2,G.USER_NAME,");
		sb.append(" CASE WHEN A.TEST_DRIVE_REGISTER IS NULL THEN '12781002' WHEN A.TEST_DRIVE_REGISTER IS NOT NULL THEN '12781001' ELSE '' END AS IS_REGISTER,");
		sb.append(" CASE WHEN A.TEST_DRIVE_FEEDBACK IS NULL THEN '12781002' WHEN A.TEST_DRIVE_FEEDBACK IS NOT NULL THEN '12781001' ELSE '' END AS IS_FEEDBACK,");
		sb.append(" CASE WHEN C.SO_NO IS NOT NULL THEN '12781001' WHEN C.SO_NO IS NULL THEN '12781002' ELSE '' END AS IS_STOCK_OUT,H.TEST_DRIVE_REMARK ");
		sb.append(" FROM TT_TEST_DRIVE A LEFT JOIN TM_POTENTIAL_CUSTOMER B ON A.CUSTOMER_NO = B.CUSTOMER_NO LEFT JOIN TT_SALES_ORDER C ON A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(" AND C.SO_STATUS = 13011035 LEFT JOIN TM_VEHICLE D ON C.VIN = D.VIN   LEFT JOIN TM_SERIES E ON A.SERIES_CODE = E.SERIES_CODE LEFT JOIN TM_SERIES F ON D.SERIES = F.SERIES_CODE");
		sb.append(" LEFT JOIN TM_USER G ON B.SOLD_BY = G.USER_ID   LEFT JOIN TM_POTENTIAL_CUSTOMER H ON A.CUSTOMER_NO = H.CUSTOMER_NO ");
		sb.append(" WHERE 1=1");
		Utility.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
		Utility.sqlToLike(sb, queryParam.get("contactorPhone"), "CONTACTOR_PHONE", "A");
		Utility.sqlToEquals(sb, queryParam.get("soldBy"), "SOLD_BY", "B");
		Utility.sqlToEquals(sb, queryParam.get("intentSeries"), "SERIES_CODE", "A");
		Utility.sqlToEquals(sb, queryParam.get("testDriveType"), "TEST_DRIVE_TYPE", "A");
		Utility.sqlToEquals(sb, queryParam.get("place"), "PLACE", "A");
		if(Utility.testString(queryParam.get("isStockOut")) && "12781001".equals(queryParam.get("isStockOut"))){
			sb.append(" AND C.SO_NO IS NOT NULL ");
		}else if(Utility.testString(queryParam.get("isStockOut")) && "12781002".equals(queryParam.get("isStockOut"))){
			sb.append(" AND C.SO_NO IS NULL ");
		}
		Utility.sqlToEquals(sb, queryParam.get("customerType"), "CUSTOMER_TYPE", "A");
		Utility.sqlToDate(sb, queryParam.get("testDriveRegisterBegin"), queryParam.get("testDriveRegisterEnd"), "TEST_DRIVE_REGISTER", "A");
		Utility.sqlToDate(sb, queryParam.get("arriveTimeBegin"), queryParam.get("arriveTimeEnd"), "ARRIVE_TIME", "B");
		Utility.sqlToDate(sb, queryParam.get("testDriveFeedbackBegin"), queryParam.get("testDriveFeedbackEnd"), "TEST_DRIVE_FEEDBACK", "A");
	
		System.err.println(sb.toString());
		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), null);
		return id;
	}
	
    @SuppressWarnings("unchecked")
	@Override
	public List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.DEALER_CODE,A.CUSTOMER_NO,A.CUSTOMER_NAME,A.CONTACTOR_PHONE,A.CONTACTOR_MOBILE,A.TEST_DRIVE_REGISTER,A.TEST_DRIVE_TYPE,A.PLACE,A.SCORE,A.SERIES_CODE,");
		sb.append(" A.TEST_DRIVE_FEEDBACK,B.SOLD_BY,B.GENDER,B.ARRIVE_TIME,C.SO_NO,C.VIN,C.STOCK_OUT_DATE,D.SERIES,E.SERIES_NAME AS SERIES_NAME_1,F.SERIES_NAME AS SERIES_NAME_2,G.USER_NAME,");
		sb.append(" CASE WHEN A.TEST_DRIVE_REGISTER IS NULL THEN '12781002' WHEN A.TEST_DRIVE_REGISTER IS NOT NULL THEN '12781001' ELSE '' END AS IS_REGISTER,");
		sb.append(" CASE WHEN A.TEST_DRIVE_FEEDBACK IS NULL THEN '12781002' WHEN A.TEST_DRIVE_FEEDBACK IS NOT NULL THEN '12781001' ELSE '' END AS IS_FEEDBACK,");
		sb.append(" CASE WHEN C.SO_NO IS NOT NULL THEN '12781001' WHEN C.SO_NO IS NULL THEN '12781002' ELSE '' END AS IS_STOCK_OUT ,H.TEST_DRIVE_REMARK");
		sb.append(" FROM TT_TEST_DRIVE A LEFT JOIN TM_POTENTIAL_CUSTOMER B ON A.CUSTOMER_NO = B.CUSTOMER_NO LEFT JOIN TT_SALES_ORDER C ON A.CUSTOMER_NO = C.CUSTOMER_NO ");
		sb.append(" AND C.SO_STATUS = 13011035 LEFT JOIN TM_VEHICLE D ON C.VIN = D.VIN   LEFT JOIN TM_SERIES E ON A.SERIES_CODE = E.SERIES_CODE LEFT JOIN TM_SERIES F ON D.SERIES = F.SERIES_CODE");
		sb.append(" LEFT JOIN TM_USER G ON B.SOLD_BY = G.USER_ID   LEFT JOIN TM_POTENTIAL_CUSTOMER H ON A.CUSTOMER_NO = H.CUSTOMER_NO");
		sb.append(" WHERE 1=1");
		Utility.sqlToLike(sb, queryParam.get("customerName"), "CUSTOMER_NAME", "A");
		Utility.sqlToLike(sb, queryParam.get("contactorPhone"), "CONTACTOR_PHONE", "A");
		Utility.sqlToEquals(sb, queryParam.get("soldBy"), "SOLD_BY", "B");
		Utility.sqlToEquals(sb, queryParam.get("intentSeries"), "SERIES_CODE", "A");
		Utility.sqlToEquals(sb, queryParam.get("testDriveType"), "TEST_DRIVE_TYPE", "A");
		Utility.sqlToEquals(sb, queryParam.get("place"), "PLACE", "A");
		if(Utility.testString(queryParam.get("isStockOut")) && "12781001".equals(queryParam.get("isStockOut"))){
			sb.append(" AND C.SO_NO IS NOT NULL ");
		}else if(Utility.testString(queryParam.get("isStockOut")) && "12781002".equals(queryParam.get("isStockOut"))){
			sb.append(" AND C.SO_NO IS NULL ");
		}
		Utility.sqlToEquals(sb, queryParam.get("customerType"), "CUSTOMER_TYPE", "A");
		Utility.sqlToDate(sb, queryParam.get("testDriveRegisterBegin"), queryParam.get("testDriveRegisterEnd"), "TEST_DRIVE_REGISTER", "A");
		Utility.sqlToDate(sb, queryParam.get("arriveTimeBegin"), queryParam.get("arriveTimeEnd"), "ARRIVE_TIME", "B");
		Utility.sqlToDate(sb, queryParam.get("testDriveFeedbackBegin"), queryParam.get("testDriveFeedbackEnd"), "TEST_DRIVE_FEEDBACK", "A");
	
		System.err.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
		return list;
	}
}
