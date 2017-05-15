package com.yonyou.dms.customer.service.complaint;

import java.sql.SQLException;
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

@Service
public class displsalSearchServiceImpl implements displsalSearchService {

	// 查询客户投诉处理结果
	@Override
	public PageInfoDto queryCompaint(Map<String, String> queryParam) throws ServiceBizException, SQLException {

		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT aa.DEALER_CODE,aa.employee_name as PRINCIPAL, COMPLAINT_COUNT,"
				+ Utility.getChangeNull("", "ON_TIME_COUNT", 0) + "ON_TIME_COUNT,"
				+ Utility.getChangeNull("", "ON_TIME_COUNT", 0) + "*100 /COMPLAINT_COUNT ON_TIME_RATE,"
				+ Utility.getChangeNull("", "CONTENTMENT_COUNT", 0) + "CONTENTMENT_COUNT,"
				+ Utility.getChangeNull("", "CONTENTMENT_COUNT", 0) + " *100 /COMPLAINT_COUNT CONTENTMENT_RATE, "
				+ Utility.getChangeNull("", "REPEAT_COUNT", 0) + "REPEAT_COUNT,"
				+ Utility.getChangeNull("", "REPEAT_COUNT", 0) + " *100 /COMPLAINT_COUNT REPEAT_RATE"
				+ "  FROM  (SELECT  A.principal, " + " COUNT(A.COMPLAINT_NO) COMPLAINT_COUNT ,"
				+ " A.DEALER_CODE FROM  TT_CUSTOMER_COMPLAINT A WHERE A.D_KEY = " + CommonConstants.D_KEY
				+ " AND A.IS_VALID =" + DictCodeConstants.DICT_IS_YES);// 必须是有效的

		sql.append(Utility.getDateCond("A", "COMPLAINT_DATE", queryParam.get("complaintDate"),
				queryParam.get("complaintDateEnd")));
		sql.append(Utility.getDateCond("A", "COMPLAINT_END_DATE", queryParam.get("complaintEndDate"),
				queryParam.get("complaintEndDateEnd")));
		sql.append(Utility.getDateCond("A", "CLOSE_DATE", queryParam.get("closeDate"),
				queryParam.get("tracingDateEnd")));

		// 分母满意率
//		if (StringUtils.isNullOrEmpty(queryParam.get("complaintResult"))) {
//
//			sql.append(Utility.gerSpecialSql(
//					" AND LTRIM(RTRIM(CHAR(CHAR(A.COMPLAINT_RESULT),8))) IN(" + queryParam.get("complaintResult"),
//					" AND TRIM(TO_CHAR(A.COMPLAINT_RESULT)) IN(" + queryParam.get("complaintResult") + ")))"));
//		}

		sql.append("GROUP BY A.principal,A.DEALER_CODE) A1 LEFT JOIN (SELECT B.PRINCIPAL,COUNT(B.COMPLAINT_NO) ON_TIME_COUNT,B.DEALER_CODE"
						+ " FROM TT_CUSTOMER_COMPLAINT B WHERE B.D_KEY = " + CommonConstants.D_KEY + " AND B.IS_VALID ="
						+ DictCodeConstants.DICT_IS_YES + " AND B.IS_INTIME_DEAL="// 必须是有效地
						+ DictCodeConstants.DICT_IS_YES);

		sql.append(Utility.getDateCond("B", "COMPLAINT_DATE", queryParam.get("complaintDate"),
				queryParam.get("complaintDateEnd")));
		sql.append(Utility.getDateCond("B", "COMPLAINT_END_DATE", queryParam.get("complaintEndDate"),
				queryParam.get("complaintEndDateEnd")));
		sql.append(Utility.getDateCond("B", "CLOSE_DATE", queryParam.get("tracingDate"),
				queryParam.get("tracingDateEnd")));

		sql.append(
				"GROUP BY B.PRINCIPAL,B.DEALER_CODE) B1 ON A1.PRINCIPAL = B1.PRINCIPAL AND A1.DEALER_CODE = B1.DEALER_CODE "
						+ " LEFT JOIN (SELECT c.PRINCIPAL,COUNT(C.COMPLAINT_NO) CONTENTMENT_COUNT, C.DEALER_CODE FROM  TT_CUSTOMER_COMPLAINT C WHERE C.D_KEY = "
						+ CommonConstants.D_KEY + " AND C.IS_VALID =" + DictCodeConstants.DICT_IS_YES
						+ " and (C.COMPLAINT_RESULT=" + DictCodeConstants.DICT_COMPLAINT_RESULT_MORE_CONTENTED
						+ " or C.COMPLAINT_RESULT=" + DictCodeConstants.DICT_COMPLAINT_RESULT_CONTENTED
						+ "  OR C.COMPLAINT_RESULT=" + DictCodeConstants.DICT_COMPLAINT_RESULT_COMMON + ") ");

		// 分子该投诉人单子数量
//		if (complaintResult != null && !"".equals(complaintResult.trim())) {
//			String[] itemList = complaintResult.split(",");
//			String trueComplaintResult = "";
//			String flag = "1";
//			for (int i = 0; i < itemList.length; i++) {
//
//				if (itemList[i].trim().equals("'11351001'") || itemList[i].trim().equals("'11351002'")) {
//					if (trueComplaintResult != null && !"".equals(trueComplaintResult.trim())) {
//						trueComplaintResult = trueComplaintResult + ',' + itemList[i].trim();
//					} else {
//						trueComplaintResult = itemList[i].trim();
//					}
//					flag = "0";
//				}
//
//			}
//			if (flag.equals("1")) {
//				trueComplaintResult = "'0'";
//			}
//			if (trueComplaintResult != null && !"".equals(trueComplaintResult.trim())) {
//
//				sql.append(Utility.gerSpecialSql(
//						" AND LTRIM(RTRIM(CHAR(CHAR(C.COMPLAINT_RESULT),8))) IN(" + trueComplaintResult,
//						" AND TRIM(TO_CHAR(C.COMPLAINT_RESULT)) IN(" + trueComplaintResult + ")))"));
//			}
//		}

		sql.append(Utility.getDateCond("C", "COMPLAINT_DATE", queryParam.get("complaintDate"),
				queryParam.get("complaintDateEnd")));

		sql.append(Utility.getDateCond("C", "COMPLAINT_END_DATE", queryParam.get("complaintEndDate"),
				queryParam.get("complaintEndDateEnd")));

		sql.append(Utility.getDateCond("C", "CLOSE_DATE", queryParam.get("tracingDate"),
				queryParam.get("tracingDateEnd")));
		sql.append(" GROUP BY c.PRINCIPAL,C.DEALER_CODE) C1 "
				+ " ON A1.PRINCIPAL = C1.PRINCIPAL AND A1.DEALER_CODE = C1.DEALER_CODE LEFT JOIN (SELECT "
				+ " D.PRINCIPAL, COUNT(D.COMPLAINT_NO) REPEAT_COUNT, D.DEALER_CODE FROM "
				+ " TT_CUSTOMER_COMPLAINT D  WHERE D.D_KEY = " + CommonConstants.D_KEY + " AND D.IS_VALID ="
				+ DictCodeConstants.DICT_IS_YES+" AND D.REPEAT_DEAL_TIMES > 0  ");

		sql.append(Utility.getDateCond("D", "COMPLAINT_DATE", queryParam.get("complaintDate"),
				queryParam.get("complaintDateEnd")));
		sql.append(Utility.getDateCond("D", "COMPLAINT_END_DATE", queryParam.get("complaintEndDate"),
				queryParam.get("complaintEndDateEnd")));
		sql.append(Utility.getDateCond("D", "CLOSE_DATE", queryParam.get("tracingDate"),
				queryParam.get("tracingDateEnd")));
		sql.append("GROUP BY D.PRINCIPAL,D.DEALER_CODE) D1 "
				+ " ON A1.PRINCIPAL = D1.PRINCIPAL AND A1.DEALER_CODE = D1.DEALER_CODE "
				+ "inner join tm_employee aa ON a1.principal = aa.employee_no AND a1.DEALER_CODE = aa.DEALER_CODE WHERE A1.DEALER_CODE = '"+dealerCode + "'");

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 导出
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> exportComalaint(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT aa.DEALER_CODE,aa.employee_name as PRINCIPAL, COMPLAINT_COUNT,"
				+ Utility.getChangeNull("", "ON_TIME_COUNT", 0) + "ON_TIME_COUNT,"
				+ Utility.getChangeNull("", "ON_TIME_COUNT", 0) + "*100 /COMPLAINT_COUNT ON_TIME_RATE,"
				+ Utility.getChangeNull("", "CONTENTMENT_COUNT", 0) + "CONTENTMENT_COUNT,"
				+ Utility.getChangeNull("", "CONTENTMENT_COUNT", 0) + " *100 /COMPLAINT_COUNT CONTENTMENT_RATE, "
				+ Utility.getChangeNull("", "REPEAT_COUNT", 0) + "REPEAT_COUNT,"
				+ Utility.getChangeNull("", "REPEAT_COUNT", 0) + " *100 /COMPLAINT_COUNT REPEAT_RATE"
				+ "  FROM  (SELECT  A.principal, " + " COUNT(A.COMPLAINT_NO) COMPLAINT_COUNT ,"
				+ " A.DEALER_CODE FROM  TT_CUSTOMER_COMPLAINT A WHERE A.D_KEY = " + CommonConstants.D_KEY
				+ " AND A.IS_VALID =" + DictCodeConstants.DICT_IS_YES);// 必须是有效的

		sql.append(Utility.getDateCond("A", "COMPLAINT_DATE", queryParam.get("complaintDate"),
				queryParam.get("complaintDateEnd")));
		sql.append(Utility.getDateCond("A", "COMPLAINT_END_DATE", queryParam.get("complaintEndDate"),
				queryParam.get("complaintEndDateEnd")));
		sql.append(Utility.getDateCond("A", "CLOSE_DATE", queryParam.get("closeDate"),
				queryParam.get("tracingDateEnd")));

		// 分母满意率
//		if (StringUtils.isNullOrEmpty(queryParam.get("complaintResult"))) {
//
//			sql.append(Utility.gerSpecialSql(
//					" AND LTRIM(RTRIM(CHAR(CHAR(A.COMPLAINT_RESULT),8))) IN(" + queryParam.get("complaintResult"),
//					" AND TRIM(TO_CHAR(A.COMPLAINT_RESULT)) IN(" + queryParam.get("complaintResult") + ")))"));
//		}

		sql.append("GROUP BY A.principal,A.DEALER_CODE) A1 LEFT JOIN (SELECT B.PRINCIPAL,COUNT(B.COMPLAINT_NO) ON_TIME_COUNT,B.DEALER_CODE"
						+ " FROM TT_CUSTOMER_COMPLAINT B WHERE B.D_KEY = " + CommonConstants.D_KEY + " AND B.IS_VALID ="
						+ DictCodeConstants.DICT_IS_YES + " AND B.IS_INTIME_DEAL="// 必须是有效地
						+ DictCodeConstants.DICT_IS_YES);

		sql.append(Utility.getDateCond("B", "COMPLAINT_DATE", queryParam.get("complaintDate"),
				queryParam.get("complaintDateEnd")));
		sql.append(Utility.getDateCond("B", "COMPLAINT_END_DATE", queryParam.get("complaintEndDate"),
				queryParam.get("complaintEndDateEnd")));
		sql.append(Utility.getDateCond("B", "CLOSE_DATE", queryParam.get("tracingDate"),
				queryParam.get("tracingDateEnd")));

		sql.append(
				"GROUP BY B.PRINCIPAL,B.DEALER_CODE) B1 ON A1.PRINCIPAL = B1.PRINCIPAL AND A1.DEALER_CODE = B1.DEALER_CODE "
						+ " LEFT JOIN (SELECT c.PRINCIPAL,COUNT(C.COMPLAINT_NO) CONTENTMENT_COUNT, C.DEALER_CODE FROM  TT_CUSTOMER_COMPLAINT C WHERE C.D_KEY = "
						+ CommonConstants.D_KEY + " AND C.IS_VALID =" + DictCodeConstants.DICT_IS_YES
						+ " and (C.COMPLAINT_RESULT=" + DictCodeConstants.DICT_COMPLAINT_RESULT_MORE_CONTENTED
						+ " or C.COMPLAINT_RESULT=" + DictCodeConstants.DICT_COMPLAINT_RESULT_CONTENTED
						+ "  OR C.COMPLAINT_RESULT=" + DictCodeConstants.DICT_COMPLAINT_RESULT_COMMON + ") ");

		// 分子该投诉人单子数量
//		if (complaintResult != null && !"".equals(complaintResult.trim())) {
//			String[] itemList = complaintResult.split(",");
//			String trueComplaintResult = "";
//			String flag = "1";
//			for (int i = 0; i < itemList.length; i++) {
//
//				if (itemList[i].trim().equals("'11351001'") || itemList[i].trim().equals("'11351002'")) {
//					if (trueComplaintResult != null && !"".equals(trueComplaintResult.trim())) {
//						trueComplaintResult = trueComplaintResult + ',' + itemList[i].trim();
//					} else {
//						trueComplaintResult = itemList[i].trim();
//					}
//					flag = "0";
//				}
//
//			}
//			if (flag.equals("1")) {
//				trueComplaintResult = "'0'";
//			}
//			if (trueComplaintResult != null && !"".equals(trueComplaintResult.trim())) {
//
//				sql.append(Utility.gerSpecialSql(
//						" AND LTRIM(RTRIM(CHAR(CHAR(C.COMPLAINT_RESULT),8))) IN(" + trueComplaintResult,
//						" AND TRIM(TO_CHAR(C.COMPLAINT_RESULT)) IN(" + trueComplaintResult + ")))"));
//			}
//		}

		sql.append(Utility.getDateCond("C", "COMPLAINT_DATE", queryParam.get("complaintDate"),
				queryParam.get("complaintDateEnd")));

		sql.append(Utility.getDateCond("C", "COMPLAINT_END_DATE", queryParam.get("complaintEndDate"),
				queryParam.get("complaintEndDateEnd")));

		sql.append(Utility.getDateCond("C", "CLOSE_DATE", queryParam.get("tracingDate"),
				queryParam.get("tracingDateEnd")));
		sql.append(" GROUP BY c.PRINCIPAL,C.DEALER_CODE) C1 "
				+ " ON A1.PRINCIPAL = C1.PRINCIPAL AND A1.DEALER_CODE = C1.DEALER_CODE LEFT JOIN (SELECT "
				+ " D.PRINCIPAL, COUNT(D.COMPLAINT_NO) REPEAT_COUNT, D.DEALER_CODE FROM "
				+ " TT_CUSTOMER_COMPLAINT D  WHERE D.D_KEY = " + CommonConstants.D_KEY + " AND D.IS_VALID ="
				+ DictCodeConstants.DICT_IS_YES+" AND D.REPEAT_DEAL_TIMES > 0  ");

		sql.append(Utility.getDateCond("D", "COMPLAINT_DATE", queryParam.get("complaintDate"),
				queryParam.get("complaintDateEnd")));
		sql.append(Utility.getDateCond("D", "COMPLAINT_END_DATE", queryParam.get("complaintEndDate"),
				queryParam.get("complaintEndDateEnd")));
		sql.append(Utility.getDateCond("D", "CLOSE_DATE", queryParam.get("tracingDate"),
				queryParam.get("tracingDateEnd")));
		sql.append("GROUP BY D.PRINCIPAL,D.DEALER_CODE) D1 "
				+ " ON A1.PRINCIPAL = D1.PRINCIPAL AND A1.DEALER_CODE = D1.DEALER_CODE "
				+ "inner join tm_employee aa ON a1.principal = aa.employee_no AND a1.DEALER_CODE = aa.DEALER_CODE WHERE A1.DEALER_CODE = '"+dealerCode + "'");
		
		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		return list;
	}
}
