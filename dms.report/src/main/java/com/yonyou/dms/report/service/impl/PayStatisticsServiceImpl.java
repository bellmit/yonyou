package com.yonyou.dms.report.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class PayStatisticsServiceImpl implements PayStatisticsService {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map> exportPayStatistics(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		StringBuffer sql = new StringBuffer("");
		List<Object> queryList = new ArrayList<Object>();

		sql.append(
				" SELECT a.* ,p.PAY_TYPE_NAME FROM (SELECT a.dealer_code,a.d_key,c.user_name,A.RECEIVE_NO,A.RECEIVE_DATE,A.TRANSACTOR,A.REMARK,A.RECEIVE_AMOUNT,A.PAY_TYPE_CODE,A.GATHERING_TYPE,B.BEST_CONTACT_TYPE,A.RECORDER,d.vin,d.so_no,d.BUSINESS_TYPE, ");

		sql.append(
				" CASE WHEN (B.CUSTOMER_NAME IS NOT NULL) AND (B.CUSTOMER_NAME <> '') THEN B.CUSTOMER_NAME ELSE e.CUSTOMER_NAME END CUSTOMER_NAME,COALESCE(COALESCE(b.CUSTOMER_NAME,e.customer_name),bk.cust_name) AS CUS_NAME, ");

		sql.append(
				" CASE WHEN (a.CUSTOMER_NO IS NOT NULL) AND (a.CUSTOMER_NO <> '') THEN a.CUSTOMER_NO ELSE e.CUSTOMER_CODE END CUSTOMER_NO FROM TT_CUSTOMER_GATHERING A  ");

		sql.append(
				" LEFT JOIN TM_POTENTIAL_CUSTOMER B ON A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.CUSTOMER_NO = B.CUSTOMER_NO LEFT JOIN TM_USER C ON A.DEALER_CODE = C.DEALER_CODE AND A.RECORDER = C.USER_ID LEFT JOIN TT_SALES_ORDER d ON a.so_no = d.so_no AND A.DEALER_CODE = d.DEALER_CODE  ");

		sql.append(
				" LEFT JOIN (SELECT DEALER_CODE,OWNER_NO AS CUSTOMER_NO,OWNER_NAME AS CUST_NAME FROM TM_OWNER) bk ON bK.DEALER_CODE = A.DEALER_CODE AND bK.CUSTOMER_NO = A.CUSTOMER_NO  ");

		sql.append(
				"  LEFT JOIN TM_PART_CUSTOMER e ON d.CONSIGNEE_CODE = e.CUSTOMER_CODE AND e.DEALER_CODE = a.DEALER_CODE) a ");

		sql.append("LEFT JOIN tm_pay_type p ON p.DEALER_CODE= A.dealer_code AND p.PAY_TYPE_CODE=A.PAY_TYPE_CODE");

		sql.append(" WHERE 1 = 1  ");

		this.setWhere(sql, queryParam, queryList);

		// sql.append(" AND A.DEALER_CODE = 2100000 AND A.D_KEY = 0 ");

		sql.append(" AND A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");

		sql.append(" AND A.D_KEY = " + CommonConstants.D_KEY);

		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		for (Map map : list) {
			if (map.get("GATHERING_TYPE") != null && map.get("GATHERING_TYPE") != "") {
				if (map.get("GATHERING_TYPE") == DictCodeConstants.DICT_GATHERING_TYPE_CASH) {
					map.put("GATHERING_TYPE", "定金");
				} else if (map.get("GATHERING_TYPE") == DictCodeConstants.DICT_GATHERING_TYPE_BEFOREHAND) {
					map.put("GATHERING_TYPE", "预收款");
				} else if (map.get("GATHERING_TYPE") == DictCodeConstants.DICT_GATHERING_TYPE_BEFOREHAND) {
					map.put("GATHERING_TYPE", "代收款");
				} else if (map.get("GATHERING_TYPE") == DictCodeConstants.DICT_GATHERING_TYPE_CAR_BALANCE) {
					map.put("GATHERING_TYPE", "购车余款");
				} else if (map.get("GATHERING_TYPE") == DictCodeConstants.DICT_GATHERING_TYPE_CAR_ALL) {
					map.put("GATHERING_TYPE", "购车全款");
				}
			}
			if (map.get("PAY_TYPE_CODE") != null && map.get("PAY_TYPE_CODE") != "") {
				if (map.get("PAY_TYPE_CODE") == DictCodeConstants.DICT_SC_PAY_TYPE_CASH) {
					map.put("PAY_TYPE_CODE", "现金");
				} else if (map.get("PAY_TYPE_CODE") == DictCodeConstants.DICT_SC_PAY_TYPE_CHECK) {
					map.put("PAY_TYPE_CODE", "支票");
				} else if (map.get("PAY_TYPE_CODE") == DictCodeConstants.DICT_SC_PAY_TYPE_TRANSFER_ACCOUNTS) {
					map.put("PAY_TYPE_CODE", "转账");
				} else if (map.get("PAY_TYPE_CODE") == DictCodeConstants.DICT_SC_PAY_TYPE_OTHERS) {
					map.put("PAY_TYPE_CODE", "其他");
				} else if (map.get("PAY_TYPE_CODE") == DictCodeConstants.DICT_SC_PAY_TYPE_NEW_CAR_PAY) {
					map.put("PAY_TYPE_CODE", "作为新车款垫付");
				}
			}

		}
		return list;
	}

	@Override
	public PageInfoDto queryPayStatisticsInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		StringBuffer sql = new StringBuffer("");
		List<Object> queryList = new ArrayList<Object>();

		sql.append(
				" SELECT a.* ,p.PAY_TYPE_NAME FROM (SELECT a.dealer_code,a.d_key,C.USER_NAME,A.RECEIVE_NO,A.RECEIVE_DATE,A.TRANSACTOR,A.REMARK,A.RECEIVE_AMOUNT,A.PAY_TYPE_CODE,A.GATHERING_TYPE,B.BEST_CONTACT_TYPE,A.RECORDER,d.vin,d.so_no,d.BUSINESS_TYPE, ");

		sql.append(
				" CASE WHEN (B.CUSTOMER_NAME IS NOT NULL) AND (B.CUSTOMER_NAME <> '') THEN B.CUSTOMER_NAME ELSE e.CUSTOMER_NAME END CUSTOMER_NAME,COALESCE(COALESCE(b.CUSTOMER_NAME,e.customer_name),bk.cust_name) AS CUS_NAME, ");

		sql.append(
				" CASE WHEN (a.CUSTOMER_NO IS NOT NULL) AND (a.CUSTOMER_NO <> '') THEN a.CUSTOMER_NO ELSE e.CUSTOMER_CODE END CUSTOMER_NO FROM TT_CUSTOMER_GATHERING A  ");

		sql.append(
				" LEFT JOIN TM_POTENTIAL_CUSTOMER B ON A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.CUSTOMER_NO = B.CUSTOMER_NO LEFT JOIN TM_USER C ON A.DEALER_CODE = C.DEALER_CODE AND A.RECORDER = C.USER_ID LEFT JOIN TT_SALES_ORDER d ON a.so_no = d.so_no AND A.DEALER_CODE = d.DEALER_CODE  ");

		sql.append(
				" LEFT JOIN (SELECT DEALER_CODE,OWNER_NO AS CUSTOMER_NO,OWNER_NAME AS CUST_NAME FROM TM_OWNER) bk ON bK.DEALER_CODE = A.DEALER_CODE AND bK.CUSTOMER_NO = A.CUSTOMER_NO  ");

		sql.append(
				"  LEFT JOIN TM_PART_CUSTOMER e ON d.CONSIGNEE_CODE = e.CUSTOMER_CODE AND e.DEALER_CODE = a.DEALER_CODE) a ");

		sql.append(" LEFT JOIN tm_pay_type p ON p.DEALER_CODE= a.dealer_code AND p.PAY_TYPE_CODE=a.PAY_TYPE_CODE");

		sql.append("  LEFT JOIN TM_USER C ON A.DEALER_CODE = C.DEALER_CODE AND A.RECORDER = C.USER_NAME  ");

		sql.append(" WHERE 1 = 1  ");
		
//		System.err.println(sql+"::::::::::");

		this.setWhere(sql, queryParam, queryList);

		// sql.append(" AND A.DEALER_CODE = 2100000 AND A.D_KEY = 0 ");

		sql.append(" AND A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");

		sql.append(" AND A.D_KEY = " + CommonConstants.D_KEY);

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 查询条件设置
	 * 
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWhere(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {
		if (!StringUtils.isNullOrEmpty(queryParam.get("transactor"))) {
			sql.append(" AND A.TRANSACTOR like ?");
			queryList.add("%"+queryParam.get("transactor")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("userName"))) {
			sql.append(" AND a.USER_NAME like ? ");
			queryList.add("%"+queryParam.get("userName")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
			sql.append(" AND a.CUSTOMER_NAME like ?");
			queryList.add("%"+queryParam.get("customerName")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("gatheringType"))) {
			sql.append(" AND A.GATHERING_TYPE like ? ");// 收款类型
			queryList.add("%"+queryParam.get("gatheringType")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("businessType"))) {
			sql.append(" AND a.BUSINESS_TYPE like ? ");
			queryList.add("%"+queryParam.get("businessType")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("payTypeName"))) {
			sql.append(" AND p.PAY_TYPE_NAME like ? ");// 收款方式
			queryList.add(queryParam.get("payTypeName"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("receiveDateFrom"))) {
			sql.append(" AND  A.RECEIVE_DATE>= ?");
			queryList.add(queryParam.get("receiveDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("receiveDateTo"))) {
			sql.append(" AND A.RECEIVE_DATE <= ?");
			queryList.add(queryParam.get("receiveDateTo"));
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querypayType(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(
				" SELECT PY.PAY_TYPE_CODE,PY.PAY_TYPE_NAME,PY.DEALER_CODE FROM TM_PAY_TYPE PY WHERE 1=1 ");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}
}
