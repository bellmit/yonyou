package com.yonyou.dms.customer.service.complaint;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPlanPO;
import com.yonyou.dms.customer.domains.PO.customerManage.CustomerComplaintPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class searchComplaintServiceImpl implements searchComplaintService {

	// 分页查询
	@Override
	public PageInfoDto queryCompaint(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("SELECT b.TECHNICIAN AS TECHNICIAN_NEW,A.DEALER_CODE,A.VIN,A.COMPLAINT_NO, A.COMPLAINT_NAME, "
				+ "CASE WHEN A.COMPLAINT_MOBILE='' THEN A.COMPLAINT_PHONE ELSE A.COMPLAINT_MOBILE END AS COMPLAINT_PHONE,"
				+ "A.COMPLAINT_MOBILE,A.COMPLAINT_TYPE, A.COMPLAINT_DATE,  A.SERVICE_ADVISOR, "
				+ "A.COMPLAINT_GENDER,A.COMPLAINT_MAIN_TYPE,A.COMPLAINT_SUB_TYPE, A.CRC_COMPLAINT_NO, A.IS_GCR, A.CRC_COMPLAINT_SOURCE, A.TECHNICIAN, "
				+ " A.DEAL_STATUS,  A.COMPLAINT_END_DATE, A.DEPARTMENT,A.BE_COMPLAINT_EMP, A.CLOSE_DATE, A.COMPLAINT_RESULT, A.PRINCIPAL,A.COMPLAINT_ORIGIN, "
				+ " A.IS_INTIME_DEAL,A.COMPLAINT_SUMMARY, A.COMPLAINT_REASON,A.CONSULTANT, A.OWNER_NAME, A.LICENSE,  "
				+ " (SELECT count(COMPLAINT_DETAIL_ID)FROM TT_CUSTOMER_COMPLAINT_DETAIL d where a.DEALER_CODE = d.DEALER_CODE and a.COMPLAINT_NO = d.COMPLAINT_NO and d.IS_UPLOAD="
				+ DictCodeConstants.DICT_IS_NO + " ) AS NOT_UPLOAD_COUNT, " + " A.RESOLVENT, "
				+ " A.CLOSE_STATUS,IS_UPLOAD,(SELECT count(COMPLAINT_DETAIL_ID)FROM TT_CUSTOMER_COMPLAINT_DETAIL c where a.DEALER_CODE = c.DEALER_CODE and a.COMPLAINT_NO = c.COMPLAINT_NO )AS DETAIL_COUNT,db.dealer_shortname,TU.user_name,U.user_name as ss,"
				+ " Us.user_name as sse,ot.ORG_NAME as dept,A.ENGINE_NO,RO.RO_NO,sp.SALES_PART_NO  FROM "
				+ " TT_CUSTOMER_COMPLAINT a left join TT_TECHNICIAN_I b on a.DEALER_CODE=b.DEALER_CODE and a.ro_no=b.ro_no"
				+ " LEFT JOIN TM_DEALER_BASICINFO db ON a.dealer_code=db.dealer_code"
				+ " LEFT JOIN TM_USER TU ON TU.DEALER_CODE=A.DEALER_CODE AND TU.USER_NAME=A.PRINCIPAL "
				+ " LEFT JOIN TM_USER U ON U.DEALER_CODE=A.DEALER_CODE AND U.USER_NAME=A.SERVICE_ADVISOR "
				+ " LEFT JOIN TM_USER Us ON Us.DEALER_CODE=A.DEALER_CODE AND Us.USER_NAME=A.BE_COMPLAINT_EMP "
				+ " LEFT JOIN tm_organization ot ON ot.DEALER_CODE=A.DEALER_CODE AND ot.ORG_CODE=A.DEPARTMENT "
				+ " LEFT JOIN TT_REPAIR_ORDER RO ON RO.DEALER_CODE=A.DEALER_CODE AND RO.RO_NO=A.RO_NO "
				+ " LEFT JOIN  TT_SALES_PART sp ON sp.DEALER_CODE =A.DEALER_CODE AND sp.RO_NO=A.RO_NO "
				+ " WHERE a.D_KEY = " + CommonConstants.D_KEY + " AND a.IS_VALID=" + DictCodeConstants.DICT_IS_YES
				+ " AND A.DEALER_CODE ='" + dealerCode + "'  ");
		sql.append(Utility.getLikeCond("A", "COMPLAINT_NO",queryParam.get("complanintNo"), "AND") );
		sql.append(Utility.getLikeCond("A", "OWNER_NAME",queryParam.get("ownerName"), "AND") );
		sql.append(Utility.getLikeCond("A", "LICENSE",queryParam.get("license"), "AND"));

		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintType"))) {
			sql.append(" AND A.COMPLAINT_TYPE=" + queryParam.get("complaintType") + " " );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealStatus"))) {
			sql.append( " AND A.DEAL_STATUS=" + queryParam.get("dealStatus") + " ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintSerious"))) {
			sql.append(" AND A.COMPLAINT_SERIOUS=" + queryParam.get("complaintSerious") + " ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("priority"))) {
			sql.append(" AND A.PRIORITY=" + queryParam.get("priority") + " ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintDatefrom"))&&!StringUtils.isNullOrEmpty(queryParam.get("complaintDateto"))) {
			sql.append(Utility.getDateCond("A", "COMPLAINT_DATE", queryParam.get("complaintDatefrom"),queryParam.get("complaintDateto")));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintEndDateFrom"))&&!StringUtils.isNullOrEmpty(queryParam.get("complaintEndDateTo"))) {
			sql.append(Utility.getDateCond("A", "COMPLAINT_END_DATE", queryParam.get("complaintEndDateFrom"),queryParam.get("complaintEndDateTo")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("closeDateFrom"))&&!StringUtils.isNullOrEmpty(queryParam.get("closeDateTo"))) {
			sql.append(Utility.getDateCond("A", "CLOSE_DATE", queryParam.get("closeDateFrom"),queryParam.get("closeDateTo")));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintOrigin"))) {
			sql.append(" AND A.COMPLAINT_ORIGIN=" +queryParam.get("complaintOrigin") + " ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("principal"))) {
			sql.append(" AND A.PRINCIPAL='" + queryParam.get("principal") + "' ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("isIntimeDeal"))) {
			sql.append(" AND A.IS_INTIME_DEAL=" + queryParam.get("isIntimeDeal") + " ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("department"))) {
			sql.append(" AND A.DEPARTMENT='" + queryParam.get("department") + "' ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("closeStatus"))) {
			sql.append(" AND A.CLOSE_STATUS=" +queryParam.get("closeStatus"));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("beComplaintEmp"))) {
			sql.append(" AND A.BE_COMPLAINT_EMP='" + queryParam.get("beComplaintEmp") + "' ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintResult"))) {
			sql.append(" AND A.COMPLAINT_RESULT=" + queryParam.get("complaintResult") + " ");
		}

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 根据投诉编号回显查询投诉历史
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querycomplaintNoById(String id) throws ServiceBizException {
		List<Map> list = querycomplaintBy(id);
		Map map = list.get(0);
		String complaintNo = "";
		if (map.get("COMPLAINT_NO") != null && !map.get("COMPLAINT_NO").equals("")) {
			complaintNo = map.get("COMPLAINT_NO").toString();
		}
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT A.* ,C.EMPLOYEE_NAME,TU.USER_NAME,ot.ORG_NAME from TT_CUSTOMER_COMPLAINT A "
				+ " LEFT JOIN TM_USER B ON A.DEALER_CODE = B.DEALER_CODE "
				+ " AND (CASE WHEN A.CREATED_BY IS NULL THEN A.UPDATED_BY  ELSE A.CREATED_BY END)= B.USER_ID "
				+ " LEFT JOIN TM_EMPLOYEE C ON A.DEALER_CODE = C.DEALER_CODE AND B.EMPLOYEE_NO = C.EMPLOYEE_NO"
				+ " LEFT JOIN TM_USER TU ON TU.DEALER_CODE=A.DEALER_CODE AND TU.USER_CODE=A.PRINCIPAL "
				+ " LEFT JOIN tm_organization ot ON ot.DEALER_CODE=A.DEALER_CODE AND ot.ORG_CODE=A.DEPARTMENT "
				+ " WHERE A.COMPLAINT_NO = '"+complaintNo+"' AND A.DEALER_CODE = '"
				+  dealerCode + "' ");
		System.out.println(sql.toString());
		List<Map> resultList = DAOUtil.findAll(sql.toString(),null);
		return resultList;
	}
	
	/**
	 * 根据投诉编号回显查询投诉历史
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> querycomplaintBy(String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String sb = new String("SELECT  cc.COMPLAINT_NO,cc.DEALER_CODE FROM TT_CUSTOMER_COMPLAINT cc  LEFT JOIN tm_vehicle ve  ON cc.DEALER_CODE = ve.DEALER_CODE "
				+ "  AND cc.VIN = ve.VIN  LEFT JOIN TT_REPAIR_ORDER ro  ON ro.DEALER_CODE = cc.DEALER_CODE   AND ro.`RO_NO` = cc.`RO_NO`"
				+ " LEFT JOIN tt_customer_complaint_detail ccd ON ccd.DEALER_CODE = cc.DEALER_CODE  AND ccd.`COMPLAINT_NO` = cc.`COMPLAINT_NO`  WHERE cc.`COMPLAINT_NO` = '"+id+"'"
				+ " AND 1 = 1 AND cc.DEALER_CODE='"+dealerCode+"'  ");
		System.out.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		List<Map> result = DAOUtil.findAll(sb.toString(),null);
		return result;
	}
	
	/**
	 * 查询投诉处理明细
	 */
	@Override
	public PageInfoDto queryDispose(String complaintNo,Map<String, String> queryParam) throws ServiceBizException, SQLException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append("SELECT ccd.* FROM TT_CUSTOMER_COMPLAINT_DETAIL ccd LEFT JOIN TT_CUSTOMER_COMPLAINT cc"
				+ " ON ccd.DEALER_CODE = cc.DEALER_CODE  AND ccd.`COMPLAINT_NO` = cc.`COMPLAINT_NO` "
				+ " WHERE ccd.`COMPLAINT_NO` = '"+complaintNo+"'  AND 1 = 1 ");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(stringBuffer.toString(), queryList);
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
		sql.append("SELECT b.TECHNICIAN AS TECHNICIAN_NEW,A.DEALER_CODE,A.VIN,A.COMPLAINT_NO, A.COMPLAINT_NAME, "
				+ "CASE WHEN A.COMPLAINT_MOBILE='' THEN A.COMPLAINT_PHONE ELSE A.COMPLAINT_MOBILE END AS COMPLAINT_PHONE,"
				+ "A.COMPLAINT_MOBILE,A.COMPLAINT_TYPE, A.COMPLAINT_DATE,  A.SERVICE_ADVISOR, "
				+ "A.COMPLAINT_GENDER,A.COMPLAINT_MAIN_TYPE,A.COMPLAINT_SUB_TYPE, A.CRC_COMPLAINT_NO, A.IS_GCR, A.CRC_COMPLAINT_SOURCE, A.TECHNICIAN, "
				+ " A.DEAL_STATUS,  A.COMPLAINT_END_DATE, A.DEPARTMENT,A.BE_COMPLAINT_EMP, A.CLOSE_DATE, A.COMPLAINT_RESULT, A.PRINCIPAL,A.COMPLAINT_ORIGIN, "
				+ " A.IS_INTIME_DEAL,A.COMPLAINT_SUMMARY, A.COMPLAINT_REASON,A.CONSULTANT, A.OWNER_NAME, A.LICENSE,  "
				+ " (SELECT count(COMPLAINT_DETAIL_ID)FROM TT_CUSTOMER_COMPLAINT_DETAIL d where a.DEALER_CODE = d.DEALER_CODE and a.COMPLAINT_NO = d.COMPLAINT_NO and d.IS_UPLOAD="
				+ DictCodeConstants.DICT_IS_NO + " ) AS NOT_UPLOAD_COUNT, " + " A.RESOLVENT, "
				+ " A.CLOSE_STATUS,IS_UPLOAD,(SELECT count(COMPLAINT_DETAIL_ID)FROM TT_CUSTOMER_COMPLAINT_DETAIL c where a.DEALER_CODE = c.DEALER_CODE and a.COMPLAINT_NO = c.COMPLAINT_NO )AS DETAIL_COUNT,db.dealer_shortname,TU.user_name,U.user_name as ss,"
				+ " Us.user_name as sse,ot.ORG_NAME as dept,A.ENGINE_NO  FROM "
				+ " TT_CUSTOMER_COMPLAINT a left join TT_TECHNICIAN_I b on a.DEALER_CODE=b.DEALER_CODE and a.ro_no=b.ro_no"
				+ " LEFT JOIN TM_DEALER_BASICINFO db ON a.dealer_code=db.dealer_code"
				+ " LEFT JOIN TM_USER TU ON TU.DEALER_CODE=A.DEALER_CODE AND TU.USER_CODE=A.PRINCIPAL "
				+ " LEFT JOIN TM_USER U ON U.DEALER_CODE=A.DEALER_CODE AND U.USER_CODE=A.SERVICE_ADVISOR "
				+ " LEFT JOIN TM_USER Us ON Us.DEALER_CODE=A.DEALER_CODE AND Us.USER_CODE=A.BE_COMPLAINT_EMP "
				+ " LEFT JOIN tm_organization ot ON ot.DEALER_CODE=A.DEALER_CODE AND ot.ORG_CODE=A.DEPARTMENT "
				+ " LEFT JOIN TT_REPAIR_ORDER RO ON RO.DEALER_CODE=A.DEALER_CODE AND RO.RO_NO=A.RO_NO "
				+ " LEFT JOIN  TT_SALES_PART sp ON sp.DEALER_CODE =A.DEALER_CODE AND sp.RO_NO=A.RO_NO "
				+ " WHERE a.D_KEY = " + CommonConstants.D_KEY + " AND a.IS_VALID=" + DictCodeConstants.DICT_IS_YES
				+ " AND A.DEALER_CODE ='" + dealerCode + "'  ");
		sql.append(Utility.getLikeCond("A", "COMPLAINT_NO",queryParam.get("complanintNo"), "AND") );
		sql.append(Utility.getLikeCond("A", "OWNER_NAME",queryParam.get("ownerName"), "AND") );
		sql.append(Utility.getLikeCond("A", "LICENSE",queryParam.get("license"), "AND"));

		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintType"))) {
			sql.append(" AND A.COMPLAINT_TYPE=" + queryParam.get("complaintType") + " " );
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealStatus"))) {
			sql.append( " AND A.DEAL_STATUS=" + queryParam.get("dealStatus") + " ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintSerious"))) {
			sql.append(" AND A.COMPLAINT_SERIOUS=" + queryParam.get("complaintSerious") + " ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("priority"))) {
			sql.append(" AND A.PRIORITY=" + queryParam.get("priority") + " ");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintDatefrom"))&&!StringUtils.isNullOrEmpty(queryParam.get("complaintDateto"))) {
			sql.append(Utility.getDateCond("A", "COMPLAINT_DATE", queryParam.get("complaintDatefrom"),queryParam.get("complaintDateto")));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintEndDateFrom"))&&!StringUtils.isNullOrEmpty(queryParam.get("complaintEndDateTo"))) {
			sql.append(Utility.getDateCond("A", "COMPLAINT_END_DATE", queryParam.get("complaintEndDateFrom"),queryParam.get("complaintEndDateTo")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("closeDateFrom"))&&!StringUtils.isNullOrEmpty(queryParam.get("closeDateTo"))) {
			sql.append(Utility.getDateCond("A", "CLOSE_DATE", queryParam.get("closeDateFrom"),queryParam.get("closeDateTo")));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintOrigin"))) {
			sql.append(" AND A.COMPLAINT_ORIGIN=" +queryParam.get("complaintOrigin") + " ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("principal"))) {
			sql.append(" AND A.PRINCIPAL='" + queryParam.get("principal") + "' ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("isIntimeDeal"))) {
			sql.append(" AND A.IS_INTIME_DEAL=" + queryParam.get("isIntimeDeal") + " ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("department"))) {
			sql.append(" AND A.DEPARTMENT='" + queryParam.get("department") + "' ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("closeStatus"))) {
			sql.append(" AND A.CLOSE_STATUS=" +queryParam.get("closeStatus"));
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("beComplaintEmp"))) {
			sql.append(" AND A.BE_COMPLAINT_EMP='" + queryParam.get("beComplaintEmp") + "' ");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("complaintResult"))) {
			sql.append(" AND A.COMPLAINT_RESULT=" + queryParam.get("complaintResult") + " ");
		}
		
		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		for (Map map : list) {
			if (map.get("COMPLAINT_TYPE") != null && map.get("COMPLAINT_TYPE") != "") {
				if (Integer.parseInt(
						map.get("COMPLAINT_TYPE").toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_SERVICE_ATTITUDE) {
					map.put("COMPLAINT_TYPE", "服务人员服务态度");
				} else if (Integer.parseInt(
						map.get("COMPLAINT_TYPE").toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_PART_QUALITY) {
					map.put("COMPLAINT_TYPE", "供应配件的质量问题");
				} else if (Integer.parseInt(map.get("COMPLAINT_TYPE")
						.toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_PART_SUPPLY) {
					map.put("COMPLAINT_TYPE", "配件供应不及时");
				} else if (Integer.parseInt(map.get("COMPLAINT_TYPE")
						.toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_REPAIR_TECHNIC) {
					map.put("COMPLAINT_TYPE", "维修技术");
				} else if (Integer.parseInt(map.get("COMPLAINT_TYPE")
						.toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_NOT_FORMER_PART) {
					map.put("COMPLAINT_TYPE", "非原厂配件");
				} else if (Integer.parseInt(map.get("COMPLAINT_TYPE")
						.toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_REPAIR_TIME_LONGER) {
					map.put("COMPLAINT_TYPE", "维修时间长");
				} else if (Integer.parseInt(map.get("COMPLAINT_TYPE")
						.toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_PART_PRICE_HIGH) {
					map.put("COMPLAINT_TYPE", "配件价格过高/收费不合理");
				} else if (Integer.parseInt(map.get("COMPLAINT_TYPE")
						.toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_LOCAL_SERVICE) {
					map.put("COMPLAINT_TYPE", "现场服务及时性");
				} else if (Integer.parseInt(map.get("COMPLAINT_TYPE")
						.toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_GIVE_CAR_ON_TIME) {
					map.put("COMPLAINT_TYPE", "没有及时交车");
				} else if (Integer.parseInt(map.get("COMPLAINT_TYPE")
						.toString()) == DictCodeConstants.DICT_COMPLAINT_TYPE_NO_RESPONSION_HOT_LINE) {
					map.put("COMPLAINT_TYPE", "24小时热线无人接听");
				}
			}
			if (map.get("DEAL_STATUS") != null && map.get("DEAL_STATUS") != "") {
				if (map.get("DEAL_STATUS") == DictCodeConstants.DICT_COMPLAINT_DEAL_STATUS_NOT_DEAL) {
					map.put("DEAL_STATUS", "是");
				} else if (map.get("DEAL_STATUS") == DictCodeConstants.DICT_COMPLAINT_DEAL_STATUS_DEALED) {
					map.put("DEAL_STATUS", "否");
				}
			}
			
			if (map.get("COMPLAINT_RESULT") != null && map.get("COMPLAINT_RESULT") != "") {
				if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_MORE_CONTENTED) {
					map.put("COMPLAINT_RESULT", "完全满意");
				} else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_DISCONTENTED) {
					map.put("COMPLAINT_RESULT", "有点不满意");
				} else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_CONTENTED) {
					map.put("COMPLAINT_RESULT", "非常满意");
				} else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_COMMON) {
					map.put("COMPLAINT_RESULT", "比较满意");
				} else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_TOTALLY_DISCONTENTED) {
					map.put("COMPLAINT_RESULT", "非常不满意");
				} 
			}
			
			if (map.get("COMPLAINT_ORIGIN") != null && map.get("COMPLAINT_ORIGIN") != "") {
				if (Integer.parseInt(map.get("COMPLAINT_ORIGIN").toString()) == DictCodeConstants.DICT_COMPLAINT_ORIGIN_OEM) {
					map.put("COMPLAINT_ORIGIN", "OEM下发");
				} else if (Integer.parseInt(map.get("COMPLAINT_ORIGIN").toString()) == DictCodeConstants.DICT_COMPLAINT_ORIGIN_INNER) {
					map.put("COMPLAINT_ORIGIN", " 内部投诉");
				} else if (Integer.parseInt(map.get("COMPLAINT_ORIGIN").toString()) == DictCodeConstants.DICT_COMPLAINT_ORIGIN_CUSTOMER) {
					map.put("COMPLAINT_ORIGIN", "客户投诉");
				} else if (Integer.parseInt(map.get("COMPLAINT_ORIGIN").toString()) == DictCodeConstants.DICT_COMPLAINT_ORIGIN_TEL) {
					map.put("COMPLAINT_ORIGIN", "电访投诉");
				} 
			}
			
			if (map.get("IS_INTIME_DEAL") != null && map.get("IS_INTIME_DEAL") != "") {
				if (map.get("IS_INTIME_DEAL") == DictCodeConstants.DICT_IS_YES) {
					map.put("IS_INTIME_DEAL", "是");
				} else if (map.get("COMPLAINT_ORIGIN") == DictCodeConstants.DICT_IS_NO) {
					map.put("IS_INTIME_DEAL", " 否");
				} 
			}

		}
		return list;
	}

	/**
	 * 删除客户投诉
	 */
	@Override
	public void deletePlanById(String complaintNo) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		CustomerComplaintPO customerComplaintPO = CustomerComplaintPO.findByCompositeKeys(dealerCode, complaintNo);
		customerComplaintPO.delete();
	}

}
