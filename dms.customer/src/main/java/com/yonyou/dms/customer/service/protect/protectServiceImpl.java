package com.yonyou.dms.customer.service.protect;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.RepairExpireRemindDTO;
import com.yonyou.dms.common.domains.PO.customer.RepairExpireRemindPO;
import com.yonyou.dms.common.domains.PO.customer.TermlyMaintainRemindPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class protectServiceImpl implements protectService {

	@Override
	public PageInfoDto queryProtectInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				"SELECT  B.DEALER_CODE,D.IS_RETURN_FACTORY as is_back,CASE WHEN d.is_return_factory is null then null "
						+ " else  tro.ro_create_date end as back_time,  tro.RO_NO,B.ENGINE_NO,"
						+ " c.phone,c.mobile,m.BOOKING_ORDER_NO,m.BOOKING_COME_TIME, "
						+ " B.DELIVERER,B.DELIVERER_PHONE,B.DELIVERER_MOBILE,B.SALES_DATE, B.INSURANCE_BEGIN_DATE,"
						+ " B.INSURANCE_END_DATE,F.INSURATION_SHORT_NAME,C.OWNER_NAME, "
						+ " C.CONTACTOR_PHONE, C.CONTACTOR_MOBILE, C.CONTACTOR_NAME, "
						+ " C.CONTACTOR_ADDRESS, C.CONTACTOR_ZIP_CODE, D.REMIND_DATE, "
						+ " D.REMINDER, G.EMPLOYEE_NAME AS REMINDER_NAME,"
						+ " D.CUSTOMER_FEEDBACK,D.REMIND_CONTENT, D.REMIND_FAIL_REASON, D.REMIND_STATUS,"
						+ " B.VIN, B.OWNER_NO,C.OWNER_PROPERTY,B.BRAND, B.SERIES, B.LICENSE, "
						+ " B.WRT_BEGIN_DATE,B.WRT_END_DATE,B.WRT_BEGIN_MILEAGE,B.WRT_END_MILEAGE, C.ADDRESS, "
						+ " B.MODEL,B.SERVICE_ADVISOR,B.MILEAGE,B.LAST_MAINTAIN_MILEAGE,db.dealer_shortname FROM " 
						+ " ("+CommonConstants.VM_VEHICLE+") B left outer join "  
						+ " ("+CommonConstants.VM_OWNER+") C on B.DEALER_CODE = C.DEALER_CODE " + " AND B.OWNER_NO=C.OWNER_NO left outer join "
						+ "(select a.* from "
						+ " TT_REPAIR_EXPIRE_REMIND a,(select vin,max(REMIND_DATE) AS REMIND_DATE from TT_REPAIR_EXPIRE_REMIND where DEALER_CODE in ("
						+ "select SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+")  vm where DEALER_CODE='"
						+ dealerCode + "' and BIZ_CODE = 'TT_ALL_REMIND')" + " and LAST_TAG="
						+ DictCodeConstants.DICT_IS_YES + " and D_KEY=" + CommonConstants.D_KEY + " GROUP BY VIN"
						+ ") b where a.REMIND_DATE=b.REMIND_DATE AND a.vin=b.vin) D on B.VIN = D.VIN "
						+ "  LEFT JOIN TM_DEALER_BASICINFO db ON b.dealer_code=db.dealer_code  "
						+ " left outer join TM_EMPLOYEE G on D.REMINDER=G.EMPLOYEE_NO AND D.DEALER_CODE=G.DEALER_CODE"
						+ " left outer join ("+CommonConstants.VM_INSURANCE+") "
						+ " F on B.INSURATION_code=F.INSURATION_code AND  F.DEALER_CODE = '" + dealerCode + "'"
						+ " LEFT JOIN" + "("
						+ "SELECT MAX(BOOKING_ORDER_NO)  AS BOOKING_ORDER_NO, MAX(BOOKING_COME_TIME) AS BOOKING_COME_TIME,BOOKING_ORDER_STATUS,vin,DEALER_CODE"
						+ "    FROM TT_BOOKING_ORDER WHERE " + "        (BOOKING_ORDER_STATUS="
						+  DictCodeConstants.DICT_BOS_NOT_ENTER + "" + "         OR BOOKING_ORDER_STATUS="
						+  DictCodeConstants.DICT_BOS_DELAY_ENTER + "" + "         OR BOOKING_ORDER_STATUS IS NULL)"
						+ "    GROUP BY DEALER_CODE,vin,BOOKING_ORDER_STATUS) m ON m.vin=b.vin and m.DEALER_CODE=b.DEALER_CODE "
						+ " left join tt_repair_order tro on tro.DEALER_CODE=b.DEALER_CODE and d.ro_no=tro.ro_no "
						+ " WHERE B.DEALER_CODE = '" + dealerCode + "' ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
			sql.append(" AND B.BRAND='" + queryParam.get("brand") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" AND B.VIN LIKE " + "'%" + queryParam.get("vin") + "%'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
			sql.append(" AND B.MODEL='" + queryParam.get("model") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
			sql.append(" AND B.SERIES='" + queryParam.get("series") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty"))
				&& !queryParam.get("ownerProperty").trim().equals("-1")) {
			sql.append(" AND C.OWNER_PROPERTY=" + queryParam.get("ownerProperty"));
		}
//		if ((queryParam.get("remindStatus") == null || queryParam.get("remindStatus").equals(""))
//				&& queryParam.get("IsOperatorMsg").equals(DictCodeConstants.DICT_IS_YES)) {
//			sql.append(" AND (D.REMIND_STATUS = " + DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_AGAIN
//					+ " or D.REMIND_STATUS is null or D.REMIND_STATUS =0 )");
//		} else {
//			if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
//				sql.append(" AND D.REMIND_STATUS = " + queryParam.get("remindStatus"));
//			}
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isFlag"))) {
			if (queryParam.get("isFlag").equals("12781002")) {
				sql.append(sql.append(" AND B.IS_VALID=" + DictCodeConstants.DICT_IS_NO + " "));
			} else {
				sql.append(sql.append(" AND (B.IS_VALID=" + DictCodeConstants.DICT_IS_YES
						+ " or B.IS_VALID=0 or B.IS_VALID IS NULL)"));
			}
		}
		sql.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("C", "ADDRESS", queryParam.get("address"), "AND"));
		sql.append(Utility.getDateCond("B", "WRT_BEGIN_DATE", queryParam.get("wrtBeginDateBegin"),
				queryParam.get("wrtBeginDateEnd")));
		sql.append(Utility.getDateCond("B", "WRT_END_DATE", queryParam.get("wrtEndDateBegin"),
				queryParam.get("wrtEndDateEnd")));
		sql.append(Utility.getDateCond("B", "SALES_DATE", queryParam.get("firstSalesDate"),
				queryParam.get("lastSalesDate")));
		sql.append(Utility.getDateCond("D", "REMIND_DATE", queryParam.get("remindDateBegin"),
				queryParam.get("remindDateEnd")));
		if (!StringUtils.isNullOrEmpty(queryParam.get("isBack")) && queryParam.get("isBack").equals("12781001")) {
			sql.append(" and D.IS_RETURN_FACTORY=12781001 ");
			sql.append(Utility.getDateCond("tro", "RO_CREATE_DATE", queryParam.get("comeBegin"),
					queryParam.get("comeEnd")));
		}
		this.setWhere(sql, queryParam, queryList);

		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 设置查询条件
	 * 
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWhere(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {

//		if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateFrom"))) {
//			sql.append(" AND B.SALES_DATE >= ?");
//			queryList.add(queryParam.get("salesDateFrom"));
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateTo"))) {
//			sql.append(" AND B.SALES_DATE <= ?");
//			queryList.add(queryParam.get("salesDateTo"));
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and B.BRAND like ? ");
			queryList.add("%" + queryParam.get("brandCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
			sql.append(" and B.SERIES like ? ");
			queryList.add("%" + queryParam.get("seriesCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
			sql.append(" and B.MODEL like ? ");
			queryList.add("%" + queryParam.get("modelCode") + "%");
		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceBeginDateFrom"))) {
//			sql.append(" AND B.INSURANCE_BEGIN_DATE <= ?");
//			queryList.add(queryParam.get("insuranceBeginDateFrom"));
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceBeginDateTo"))) {
//			sql.append(" AND B.INSURANCE_BEGIN_DATE >= ?");
//			queryList.add(queryParam.get("insuranceBeginDateTo"));
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceEndDateFrom"))) {
//			sql.append(" AND B.INSURANCE_END_DATE <= ?");
//			queryList.add(queryParam.get("insuranceEndDateFrom"));
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("insuranceEndDateFrom"))) {
//			sql.append(" AND B.INSURANCE_END_DATE >= ?");
//			queryList.add(queryParam.get("insuranceEndDateFrom"));
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			sql.append(" AND B.LICENSE like ? ");
			queryList.add("%" + queryParam.get("license") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindDateFrom"))) {
			sql.append(" AND D.REMIND_DATE <= ?");
			queryList.add(queryParam.get("remindDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindDateTo"))) {
			sql.append(" AND D.REMIND_DATE >= ?");
			queryList.add(queryParam.get("remindDateTo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("iSValid"))) {
			sql.append(" AND IS_VALID like ? ");
			queryList.add("%" + queryParam.get("iSValid") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sql.append(" AND D.REMIND_STATUS like ? ");
			queryList.add("%" + queryParam.get("remindStatus") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" AND B.VIN like ? ");
			queryList.add("%" + queryParam.get("vin") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sql.append(" AND D.REMIND_STATUS like ? ");
			queryList.add("%" + queryParam.get("remindStatus") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("contactorAddress"))) {
			sql.append(" AND C.CONTACTOR_ADDRESS like ? ");
			queryList.add("%" + queryParam.get("contactorAddress") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isReturnFactory"))) {
			sql.append(" and D.IS_RETURN_FACTORY like ? ");
			queryList.add("%" + queryParam.get("isReturnFactory") + "%");
		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("roCreateDateFrom"))) {
//			sql.append(" AND tro.RO_CREATE_DATE <= ?");
//			queryList.add(queryParam.get("roCreateDateFrom"));
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("roCreateDateTo"))) {
//			sql.append(" AND tro.RO_CREATE_DATE >= ?");
//			queryList.add(queryParam.get("roCreateDateTo"));
//		}
		
	}

	/**
	 * 导出
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> exportProtectInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				"SELECT  B.DEALER_CODE,D.IS_RETURN_FACTORY as is_back,CASE WHEN d.is_return_factory is null then null "
						+ " else  tro.ro_create_date end as back_time, "
						+ " c.phone,c.mobile,m.BOOKING_ORDER_NO,m.BOOKING_COME_TIME, "
						+ " B.DELIVERER,B.DELIVERER_PHONE,B.DELIVERER_MOBILE,B.SALES_DATE, B.INSURANCE_BEGIN_DATE,"
						+ " B.INSURANCE_END_DATE,F.INSURATION_SHORT_NAME,C.OWNER_NAME, "
						+ " C.CONTACTOR_PHONE, C.CONTACTOR_MOBILE, C.CONTACTOR_NAME, "
						+ " C.CONTACTOR_ADDRESS, C.CONTACTOR_ZIP_CODE, D.REMIND_DATE, "
						+ " D.REMINDER, G.EMPLOYEE_NAME AS REMINDER_NAME,"
						+ " D.CUSTOMER_FEEDBACK,D.REMIND_CONTENT, D.REMIND_FAIL_REASON, D.REMIND_STATUS,"
						+ " B.VIN, B.OWNER_NO,C.OWNER_PROPERTY,B.BRAND, B.SERIES, B.LICENSE, "
						+ " B.WRT_BEGIN_DATE,B.WRT_END_DATE,B.WRT_BEGIN_MILEAGE,B.WRT_END_MILEAGE, C.ADDRESS, "
						+ " B.MODEL,B.SERVICE_ADVISOR,B.MILEAGE,B.LAST_MAINTAIN_MILEAGE,db.dealer_shortname FROM " 
						+ " ("+CommonConstants.VM_VEHICLE+") B left outer join "  
						+ " ("+CommonConstants.VM_OWNER+") C on B.DEALER_CODE = C.DEALER_CODE " + " AND B.OWNER_NO=C.OWNER_NO left outer join "
						+ "(select a.* from "
						+ " TT_REPAIR_EXPIRE_REMIND a,(select vin,max(REMIND_DATE) AS REMIND_DATE from TT_REPAIR_EXPIRE_REMIND where DEALER_CODE in ("
						+ "select SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+")  vm where DEALER_CODE='"
						+ dealerCode + "' and BIZ_CODE = 'TT_ALL_REMIND')" + " and LAST_TAG="
						+ DictCodeConstants.DICT_IS_YES + " and D_KEY=" + CommonConstants.D_KEY + " GROUP BY VIN"
						+ ") b where a.REMIND_DATE=b.REMIND_DATE AND a.vin=b.vin) D on B.VIN = D.VIN "
						+ "  LEFT JOIN TM_DEALER_BASICINFO db ON b.dealer_code=db.dealer_code  "
						+ " left outer join TM_EMPLOYEE G on D.REMINDER=G.EMPLOYEE_NO AND D.DEALER_CODE=G.DEALER_CODE"
						+ " left outer join ("+CommonConstants.VM_INSURANCE+") "
						+ " F on B.INSURATION_code=F.INSURATION_code AND  F.DEALER_CODE = '" + dealerCode + "'"
						+ " LEFT JOIN" + "("
						+ "SELECT MAX(BOOKING_ORDER_NO)  AS BOOKING_ORDER_NO, MAX(BOOKING_COME_TIME) AS BOOKING_COME_TIME,BOOKING_ORDER_STATUS,vin,DEALER_CODE"
						+ "    FROM TT_BOOKING_ORDER WHERE " + "        (BOOKING_ORDER_STATUS="
						+  DictCodeConstants.DICT_BOS_NOT_ENTER + "" + "         OR BOOKING_ORDER_STATUS="
						+  DictCodeConstants.DICT_BOS_DELAY_ENTER + "" + "         OR BOOKING_ORDER_STATUS IS NULL)"
						+ "    GROUP BY DEALER_CODE,vin,BOOKING_ORDER_STATUS) m ON m.vin=b.vin and m.DEALER_CODE=b.DEALER_CODE "
						+ " left join tt_repair_order tro on tro.DEALER_CODE=b.DEALER_CODE and d.ro_no=tro.ro_no "
						+ " WHERE B.DEALER_CODE = '" + dealerCode + "' ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
			sql.append(" AND B.BRAND='" + queryParam.get("brand") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" AND B.VIN LIKE " + "'%" + queryParam.get("vin") + "%'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("model"))) {
			sql.append(" AND B.MODEL='" + queryParam.get("model") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("series"))) {
			sql.append(" AND B.SERIES='" + queryParam.get("series") + "'");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty"))
				&& !queryParam.get("ownerProperty").trim().equals("-1")) {
			sql.append(" AND C.OWNER_PROPERTY=" + queryParam.get("ownerProperty"));
		}
//		if ((queryParam.get("remindStatus") == null || queryParam.get("remindStatus").equals(""))
//				&& queryParam.get("IsOperatorMsg").equals(DictCodeConstants.DICT_IS_YES)) {
//			sql.append(" AND (D.REMIND_STATUS = " + DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_AGAIN
//					+ " or D.REMIND_STATUS is null or D.REMIND_STATUS =0 )");
//		} else {
//			if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
//				sql.append(" AND D.REMIND_STATUS = " + queryParam.get("remindStatus"));
//			}
//		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isFlag"))) {
			if (queryParam.get("isFlag").equals("12781002")) {
				sql.append(sql.append(" AND B.IS_VALID=" + DictCodeConstants.DICT_IS_NO + " "));
			} else {
				sql.append(sql.append(" AND (B.IS_VALID=" + DictCodeConstants.DICT_IS_YES
						+ " or B.IS_VALID=0 or B.IS_VALID IS NULL)"));
			}
		}
		sql.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("C", "ADDRESS", queryParam.get("address"), "AND"));
		sql.append(Utility.getDateCond("B", "WRT_BEGIN_DATE", queryParam.get("wrtBeginDateBegin"),
				queryParam.get("wrtBeginDateEnd")));
		sql.append(Utility.getDateCond("B", "WRT_END_DATE", queryParam.get("wrtEndDateBegin"),
				queryParam.get("wrtEndDateEnd")));
		sql.append(Utility.getDateCond("B", "SALES_DATE", queryParam.get("firstSalesDate"),
				queryParam.get("lastSalesDate")));
		sql.append(Utility.getDateCond("D", "REMIND_DATE", queryParam.get("remindDateBegin"),
				queryParam.get("remindDateEnd")));
		if (!StringUtils.isNullOrEmpty(queryParam.get("isBack")) && queryParam.get("isBack").equals("12781001")) {
			sql.append(" and D.IS_RETURN_FACTORY=12781001 ");
			sql.append(Utility.getDateCond("tro", "RO_CREATE_DATE", queryParam.get("comeBegin"),
					queryParam.get("comeEnd")));
		}
		this.setWhere(sql, queryParam, queryList);
		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		for (Map map : list) {
			if (map.get("REMIND_STATUS") != null && map.get("REMIND_STATUS") != "") {
				if (Integer.parseInt(
						map.get("REMIND_STATUS").toString()) == DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_AGAIN) {
					map.put("REMIND_STATUS", "继续提醒");
				} else if (Integer.parseInt(
						map.get("REMIND_STATUS").toString()) == DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_FAIL) {
					map.put("REMIND_STATUS", "失败结束提醒");
				} else if (Integer.parseInt(map.get("REMIND_STATUS")
						.toString()) == DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_SUCCESS) {
					map.put("REMIND_STATUS", "成功结束提醒");
				}
			}
			if (map.get("IS_VALID") != null && map.get("IS_VALID") != "") {
				if (Integer.parseInt(map.get("IS_VALID").toString()) == DictCodeConstants.STATUS_IS_YES) {
					map.put("IS_VALID", "是");
				} else if (Integer.parseInt(map.get("IS_VALID").toString()) == DictCodeConstants.STATUS_IS_NOT) {
					map.put("IS_VALID", "否");
				}
			}

		}
		return list;
	}

	/**
	 * 新增提醒记录
	 */
	@Override
	public void addeRemind(String vins, String ownerNo, RepairExpireRemindDTO repairExpireRemindDTO)
			throws ServiceBizException {
		RepairExpireRemindPO repairExpireRemindPO = new RepairExpireRemindPO();// 新增
		setvehiclePo(repairExpireRemindDTO, repairExpireRemindPO);
	}
	
	private void setvehiclePo(RepairExpireRemindDTO repairExpireRemindDTO,
			RepairExpireRemindPO repairExpireRemindPO) {

		repairExpireRemindPO.setString("VIN", repairExpireRemindDTO.getVin());
		repairExpireRemindPO.setString("OWNER_NO", repairExpireRemindDTO.getOwnerNo());
		repairExpireRemindPO.set("REMIND_DATE", new Timestamp(System.currentTimeMillis()));// 提醒时间
		repairExpireRemindPO.setString("LAST_TAG", DictCodeConstants.IS_YES);// 最新记录标识
		repairExpireRemindPO.setString("REMIND_CONTENT", repairExpireRemindDTO.getRemindContent());// 提醒内容
		repairExpireRemindPO.setString("CUSTOMER_FEEDBACK", repairExpireRemindDTO.getCustomerFeedback());// 客户反馈
		repairExpireRemindPO.setString("REMIND_WAY", repairExpireRemindDTO.getRemindWay());// 提醒方式
		repairExpireRemindPO.setString("REMIND_STATUS", repairExpireRemindDTO.getRemindStatus());// 提醒状态
		repairExpireRemindPO.setString("REMARK", repairExpireRemindDTO.getRemark()); // 备注
		repairExpireRemindPO.saveIt();

	}

	/**
	 * 分页查询提醒记录
	 */
	@Override
	public PageInfoDto queryRemindre(String vin, String ownerNo) throws ServiceBizException {
		List<Object> queryList = new ArrayList<>();
		List<Map> list = queryReminderBy(vin, ownerNo);
		Map map = list.get(0);
		vin = "";
		ownerNo = "";
		if (map.get("VIN") != null && !map.get("VIN").equals("")) {
			vin = map.get("VIN").toString();
		}
		if (map.get("OWNER_NO") != null && !map.get("OWNER_NO").equals("")) {
			ownerNo = map.get("OWNER_NO").toString();
		}
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT A.*,V.NEW_VEHICLE_DATE, V.NEW_VEHICLE_MILEAGE, V.LICENSE, F.OWNER_NAME,db.DEALER_SHORTNAME,tu.USER_NAME , CASE WHEN E.DEALER_CODE != '"
						+ dealerCode + "'  THEN ''  ELSE E.EMPLOYEE_NAME  END EMPLOYEE_NAME FROM " + " ( SELECT " );
//						+ "( DictCodeConstants.DICT_REMINDER_TYPE_VERYCAR_ATTERM "
//						+ " ) AS REMIND_TYPE,REMIND_ID, OWNER_NO,VIN,REMIND_DATE,REMIND_CONTENT,CUSTOMER_FEEDBACK,REMIND_FAIL_REASON,REMARK,REMINDER,REMIND_WAY,"
//						+ " LAST_TAG, REMIND_STATUS, VER,'' AS LAST_NEXT_MAINTAIN_DATE,0 AS LAST_NEXT_MAINTAIN_MILEAGE,A.DEALER_CODE,0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_VEHICLE_REMIND A  WHERE A.DEALER_CODE IN "
//						+ " (SELECT  SHARE_ENTITY  FROM ( " + CommonConstants.VM_ENTITY_SHARE_WITH
//						+ ") vm2 WHERE DEALER_CODE = '2100000'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
//						+ DictCodeConstants.D_KEY + "  ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append(" UNION SELECT ( " + DictCodeConstants.DICT_REMINDER_TYPE_INSURANCE_ATTERM
//				+ ") AS REMIND_TYPE,REMIND_ID,OWNER_NO,VIN,REMIND_DATE,REMIND_CONTENT,CUSTOMER_FEEDBACK,REMIND_FAIL_REASON,REMARK,REMINDER, REMIND_WAY,LAST_TAG,REMIND_STATUS, "
//				+ " VER,'' AS LAST_NEXT_MAINTAIN_DATE,0 AS LAST_NEXT_MAINTAIN_MILEAGE,A.DEALER_CODE,A.RENEWAL_REMIND_STATUS,A.RENEWAL_FAILED_REASON,A.RENEWAL_REMARK, A.RENEWAL_FAILED_DATE AS RENEWAL_FAILED_DATE  FROM TT_INSURANCE_EXPIRE_REMIND A  WHERE A.DEALER_CODE IN "
//				+ " (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm1  WHERE DEALER_CODE = '" + dealerCode + "' AND biz_code = 'TT_ALL_REMIND') "
//				+ "  AND A.D_KEY = " + DictCodeConstants.D_KEY + " ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append(" UNION SELECT (" + DictCodeConstants.DICT_REMINDER_TYPE_NEW_CAR + ") AS REMIND_TYPE,"
//				+ " REMIND_ID, OWNER_NO,VIN, REMIND_DATE,REMIND_CONTENT,CUSTOMER_FEEDBACK,REMIND_FAIL_REASON,REMARK,REMINDER, REMIND_WAY,LAST_TAG,REMIND_STATUS,VER,'' AS LAST_NEXT_MAINTAIN_DATE,0 AS LAST_NEXT_MAINTAIN_MILEAGE,A.DEALER_CODE, "
//				+ " 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_NEWVEHICLE_REMIND A  WHERE A.DEALER_CODE IN  (SELECT  SHARE_ENTITY "
//				+ " FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH + ") vm3  WHERE DEALER_CODE = '" + dealerCode
//				+ "'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = " + DictCodeConstants.D_KEY + " ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append(" UNION SELECT (" + DictCodeConstants.DICT_REMINDER_TYPE_OWNER_BIRTHDAY
//				+ ") AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER, "
//				+ " '' AS LAST_NEXT_MAINTAIN_DATE, 0 AS LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON,'' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_OWNER_BIRTHDAY_REMIND A  WHERE A.DEALER_CODE IN "
//				+ " (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm4  WHERE DEALER_CODE = '" + dealerCode + "'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
//				+ DictCodeConstants.D_KEY + " ");

		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append(" UNION SELECT (" + DictCodeConstants.DICT_REMINDER_TYPE_CUSTOMER_LOSE
//				+ ") AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON,  REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER, '' AS LAST_NEXT_MAINTAIN_DATE, "
//				+ " 0 AS LAST_NEXT_MAINTAIN_MILEAGE,  A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS,  0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_VEHICLE_LOSS_REMIND A  WHERE A.DEALER_CODE IN "
//				+ " (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm5  WHERE DEALER_CODE = '" + dealerCode + "'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
//				+ DictCodeConstants.D_KEY + " UNION SELECT ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append(" (" + DictCodeConstants.DICT_REMINDER_TYPE_TERMLY_MAINTAIN
//				+ ") AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK,"
//				+ " REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER,  A.LAST_NEXT_MAINTAIN_DATE AS LAST_NEXT_MAINTAIN_DATE, LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE,  0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM "
//				+ " TT_TERMLY_MAINTAIN_REMIND A   WHERE A.DEALER_CODE IN  (SELECT  SHARE_ENTITY  FROM ("
//				+ CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm  WHERE DEALER_CODE = '2100000'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
//				+ DictCodeConstants.D_KEY + " " );
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
		sb.append("  (" + DictCodeConstants.DICT_REMINDER_TYPE_GUAR_ATTERM
				+ ") AS REMIND_TYPE, REMIND_ID,  OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT,  CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS,"
				+ " VER, '' AS LAST_NEXT_MAINTAIN_DATE, 0 AS LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_REPAIR_EXPIRE_REMIND A   WHERE A.DEALER_CODE IN "
				+ "  (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
				+ ") vm6  WHERE DEALER_CODE = '2100000'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY ="
				+ DictCodeConstants.D_KEY + " ");

		if (vin != null && !vin.equals("") && ownerNo != null && !ownerNo.equals("")) {
			sb.append(" AND A.VIN = '" + vin + "' AND A.OWNER_NO = '" + ownerNo + "' ");
		}
//		sb.append("  UNION SELECT (" + DictCodeConstants.DICT_REMINDER_TYPE_YEAR_CHECK
//				+ ") AS REMIND_TYPE,  REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS,"
//				+ " VER, '' AS LAST_NEXT_MAINTAIN_DATE, 0 AS LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON,  '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_CHECK_EXPIRE_REMIND A WHERE A.DEALER_CODE IN "
//				+ " (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm7 WHERE DEALER_CODE = '" + dealerCode + "'   AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY ="
//				+ DictCodeConstants.D_KEY + " ");
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
		sb.append(" ) A LEFT JOIN TM_DEALER_BASICINFO db  ON A.dealer_code = db.dealer_code "
				+ " LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") V  ON V.VIN = A.VIN  AND V.DEALER_CODE = '"
				+ dealerCode + "' " + " LEFT JOIN (" + CommonConstants.VM_OWNER + ") F  ON F.DEALER_CODE = '"
				+ dealerCode + "'  AND V.OWNER_NO = F.OWNER_NO "
				+ " LEFT JOIN TM_EMPLOYEE E  ON E.DEALER_CODE = A.DEALER_CODE  AND E.EMPLOYEE_NO = A.REMINDER "
				+ " LEFT JOIN tm_user tu  ON A.DEALER_CODE = tu.DEALER_CODE ");
		sb.append("  WHERE 1=1 AND A.VIN='"+vin+"' AND A.owner_no ='"+ownerNo+"' "  );
		sb.append(" GROUP BY A.REMIND_ID ");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), queryList);
		return pageInfoDto;
	}
	
	@SuppressWarnings("rawtypes")
	private List<Map> queryReminderBy(String vin, String ownerNo) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

		String sb = new String(
				" SELECT db.DEALER_SHORTNAME, re.OWNER_NO, om.OWNER_NAME, ov.LICENSE, re.VIN,re.REMIND_ID, re.REMIND_DATE, re.REMIND_WAY, re.REMIND_CONTENT, "
						+ " re.CUSTOMER_FEEDBACK, re.REMINDER, re.REMIND_STATUS,"
						+ " re.REMIND_FAIL_REASON, re.REMARK,"
						+ " ov.NEW_VEHICLE_DATE,ov.NEW_VEHICLE_MILEAGE, re.LAST_NEXT_MAINTAIN_DATE, re.LAST_NEXT_MAINTAIN_MILEAGE FROM tt_termly_maintain_remind re "
						+ " LEFT JOIN tm_owner om ON om.OWNER_NO=re.OWNER_NO AND om.`DEALER_CODE`=re.`DEALER_CODE`"
						+ " LEFT JOIN tm_vehicle ov ON ov.`VIN`=re.`VER` AND ov.`DEALER_CODE`=re.`DEALER_CODE`"
						+ " LEFT JOIN TM_DEALER_BASICINFO db  ON re.dealer_code=db.dealer_code "
						+ " LEFT JOIN TT_REPAIR_EXPIRE_REMIND ie ON ie.vin=re.`VIN` AND ie.`DEALER_CODE`=re.`DEALER_CODE`"
						+ " WHERE re.vin='" + vin + "' AND 1=1  AND re.DEALER_CODE = '" + dealerCode
						+ "' AND re.OWNER_NO ='" + ownerNo + "' ");
		System.out.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(vin);
		queryList.add(ownerNo);
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}

	/**
	 * 提醒记录回显查询提醒ID
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryByRemindID(String remindId) throws ServiceBizException {
		RepairExpireRemindPO repairExpireRemindPO = RepairExpireRemindPO.findById(remindId);
		List<Map> list = new ArrayList<Map>();
		list.add(repairExpireRemindPO.toMap());
		return list;
	}

	/**
	 * 修改提醒记录
	 */
	@Override
	public void modifyRemindID(String remindId, RepairExpireRemindDTO repairExpireRemindDTO)
			throws ServiceBizException {
		RepairExpireRemindPO repairExpireRemindPO = RepairExpireRemindPO.findById(remindId);
		repairExpireRemindPO.setString("CUSTOMER_FEEDBACK",repairExpireRemindDTO.getCustomerFeedback());
		repairExpireRemindPO.setString("REMARK", repairExpireRemindDTO.getRemark());
		repairExpireRemindPO.saveIt();
	}

	/**
	 * 销售回访DCRC回访信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

		stringBuffer.append(" SELECT aa.*,C.OWNER_NAME,C.PHONE,C.MOBILE,B.BRAND,B.SERIES,B.MODEL from (SELECT '定期保养提醒' description,cast(D.REMIND_ID as char(14))  REMIND_ID, ");
		stringBuffer.append(" D.DEALER_CODE,D.OWNER_NO,D.REMIND_DATE,D.REMIND_CONTENT,D.REMINDER,D.CUSTOMER_FEEDBACK,D.REMIND_STATUS,D.VIN ");
		stringBuffer.append(" FROM  TT_VEHICLE_LOSS_REMIND D where D.D_KEY = 0 ");
		stringBuffer.append(" AND D.DEALER_CODE  = '"+dealerCode+"'  ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (D.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or D.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		stringBuffer.append(
				Utility.getDateCond("", "REMIND_DATE", queryParam.get("remindDateFrom"), queryParam.get("remindDateTo"))
						+  " union all SELECT '保修到期提醒' description, "
						+ "cast(D.REMIND_ID as char(14))  REMIND_ID,D.DEALER_CODE,D.OWNER_NO,"
						+ "D.REMIND_DATE,D.REMIND_CONTENT,D.REMINDER,D.CUSTOMER_FEEDBACK,"
						+ "D.REMIND_STATUS,D.VIN FROM  TT_REPAIR_EXPIRE_REMIND D "
						+ "where D.D_KEY = 0 AND D.DEALER_CODE  = '" + dealerCode + "' ");
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append( " AND (D.OWNER_NO = '" + queryParam.get("ownerNo") + "' or D.VIN = '" + queryParam.get("vin") + "')");
		}
		
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "REMIND_DATE", null);
		stringBuffer.append(" ) aa left join ("+ CommonConstants.VM_VEHICLE + ") B on B.DEALER_CODE = aa.DEALER_CODE and B.VIN = aa.VIN left join ");
		stringBuffer.append(" ("+ CommonConstants.VM_OWNER +") C");
		stringBuffer.append(" on aa.DEALER_CODE = c.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO ");
		stringBuffer.append(" union all SELECT '客户生日提醒' description,cast(B.REMIND_ID as char(14))  REMIND_ID, ");
		stringBuffer.append(" A.DEALER_CODE,B.OWNER_NO,B.REMIND_DATE,B.REMIND_CONTENT,B.REMINDER,B.CUSTOMER_FEEDBACK,B.REMIND_STATUS,B.VIN,A.CUSTOMER_NAME OWNER_NAME,A.CONTACTOR_PHONE PHONE, ");
		stringBuffer.append(" A.CONTACTOR_MOBILE MOBILE,C.BRAND,C.SERIES,C.MODEL FROM TM_POTENTIAL_CUSTOMER A LEFT JOIN TT_OWNER_BIRTHDAY_REMIND B ON A.DEALER_CODE = B.DEALER_CODE ");
		
		stringBuffer.append(" AND A.CUSTOMER_NO = B.OWNER_NO left join ("+ CommonConstants.VM_VEHICLE + ") C on B.DEALER_CODE = a.DEALER_CODE and C.VIN = B.VIN WHERE A.DEALER_CODE= ? ");
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
		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.CUSTOMER_NAME OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE,A.BRAND, ");
		stringBuffer.append(" A.SERIES,A.MODEL FROM  TT_SALES_TRACE_TASK A LEFT JOIN TM_CUSTOMER E ON A.CUSTOMER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 ");
		stringBuffer.append(" LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE ");
		stringBuffer.append(" Left join (select distinct Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME from ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N inner join TT_SALES_TRACE_TASK_QUESTION Q on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE) ");
		stringBuffer.append(" QU on QU.TRACE_TASK_ID=A.TRACE_TASK_ID WHERE  A.D_KEY = 0 AND A.DEALER_CODE= '"+dealerCode+"' ");
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (A.CUSTOMER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", null);
		stringBuffer.append(" union all SELECT '回访结果' description,A.RO_NO REMIND_ID,A.DEALER_CODE,H.OWNER_NO,A.INPUT_DATE REMIND_DATE,A.REMARK REMIND_CONTENT,A.TRANCER REMINDER, ");
		stringBuffer.append(" '' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.OWNER_NAME,A.DELIVERER_MOBILE PHONE,A.DELIVERER_PHONE MOBILE,A.BRAND,A.SERIES,A.MODEL FROM  TT_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TT_REPAIR_ORDER E ON A.RO_NO=E.RO_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN Tt_Technician_I TECH ON TECH.DEALER_CODE=E.DEALER_CODE ");
		stringBuffer.append(" AND TECH.RO_NO=E.RO_NO AND TECH.D_KEY=0 LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO ");
		stringBuffer.append(" AND A.DEALER_CODE=F.DEALER_CODE WHERE  A.D_KEY = 0 AND A.DEALER_CODE= '"+dealerCode+"' ");

		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
			stringBuffer.append(" AND (H.OWNER_NO = ? ");
			queryList.add(queryParam.get("ownerNo"));
			stringBuffer.append(" or A.VIN = ? )  ");
			queryList.add(queryParam.get("ownerNo"));
		}
//		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", "A");
//		stringBuffer.append(" union all SELECT '流失报警回访结果' description,cast(A.TRACE_ITEM_ID as char(14))  REMIND_ID,A.DEALER_CODE,A.OWNER_NO ,A.INPUT_DATE REMIND_DATE,QU.QUESTIONNAIRE_NAME REMIND_CONTENT, ");
//		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,F.OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE,H.BRAND,H.SERIES,H.MODEL FROM  TT_LOSS_VEHICLE_TRACE_TASK A ");
//		stringBuffer.append(" LEFT JOIN TM_CUSTOMER E ON A.OWNER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN  ");
//		stringBuffer.append(" AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE Left join(select distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME ");
//		stringBuffer.append(" from ("+ CommonConstants.VT_TRACE_QUESTIONNAIRE + ") N inner join TT_LOSS_VHCL_TRCE_TASK_QN Q on Q.QUESTIONNAIRE_CODE=N.QUESTIONNAIRE_CODE ) QU   on QU.TRACE_TASK_ID=A.TRACE_ITEM_ID ");
//		stringBuffer.append(" WHERE  A.D_KEY = 0 AND A.DEALER_CODE= ? ");
//		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
//		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
//			stringBuffer.append(" AND (A.OWNER_NO = ? ");
//			queryList.add(queryParam.get("ownerNo"));
//			stringBuffer.append(" or A.VIN = ? )  ");
//			queryList.add(queryParam.get("ownerNo"));
//		}
//		Utility.sqlToDate(stringBuffer,queryParam.get("startDate"), queryParam.get("endDate"), "INPUT_DATE", null);
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}

}
