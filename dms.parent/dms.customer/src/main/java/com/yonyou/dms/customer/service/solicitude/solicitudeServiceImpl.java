package com.yonyou.dms.customer.service.solicitude;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.common.domains.DTO.customer.TermlyMaintainRemindDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.common.domains.PO.customer.OwnerMemoPO;
import com.yonyou.dms.common.domains.PO.customer.TermlyMaintainRemindPO;
import com.yonyou.dms.common.domains.PO.customer.VehicleMemoPO;
import com.yonyou.dms.common.domains.PO.stockmanage.CarownerPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class solicitudeServiceImpl implements solicitudeService {

	@Override
	public PageInfoDto querySolicitudeInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append(
				" select  e.RO_CREATE_DATE,e.RO_NO,e.ENGINE_NO ,sp.SALES_PART_NO, b.REMIND_ID,b.REMIND_WAY,a.DCRC_ADVISOR,b.IS_SUCCESS_REMIND,b.REMARK,a.VIP_NO,a.DISCOUNT_EXPIRE_DATE,a.IS_VALID,a.DELIVERER,a.DELIVERER_PHONE,a.DELIVERER_MOBILE,d.PHONE,d.MOBILE,d.ADDRESS,a.LICENSE,a.SALES_DATE,a.LAST_MAINTAIN_DATE,a.NEXT_MAINTAIN_DATE, ");
		sql.append(
				" a.NEXT_MAINTAIN_MILEAGE,d.OWNER_NAME,d.CONTACTOR_PHONE,d.CONTACTOR_MOBILE,d.CONTACTOR_NAME,d.CONTACTOR_ADDRESS,d.CONTACTOR_ZIP_CODE,a.LAST_MAINTENANCE_DATE,a.LAST_MAINTENANCE_MILEAGE,b.REMIND_DATE,b.REMIND_CONTENT,b.REMINDER,c.EMPLOYEE_NAME REMINDER_NAME,b.CUSTOMER_FEEDBACK,b.REMIND_FAIL_REASON,b.REMIND_STATUS,a.VIN,a.OWNER_NO,d.OWNER_PROPERTY,a.BRAND,a.SERIES,a.MODEL,a.SERVICE_ADVISOR,f.EMPLOYEE_NAME MAINTAIN_ADVISOR,a.LAST_MAINTAIN_MILEAGE,b.REMIND_DATE_S,b.REMIND_DATE_T,a.DEALER_CODE,db.dealer_shortname"
				+ " ,c.EMPLOYEE_NAME AS service_advisor_name from ");
		sql.append(" ( " + CommonConstants.VM_VEHICLE);

		sql.append(
				" ) a left join (select a.*,c.remind_date_s,c.remind_date_t from tt_termly_maintain_remind a inner join (select vin,max(remind_date) remind_date  from tt_termly_maintain_remind  where dealer_code in (select share_entity from ");
		sql.append(" ( " + CommonConstants.VM_ENTITY_SHARE_WITH + " ) vm " + "where dealer_code='");

		sql.append(FrameworkUtil.getLoginInfo().getDealerCode() + "' ");

		sql.append(" and biz_code = 'TT_ALL_REMIND')  and last_tag =" + DictCodeConstants.IS_YES);

		sql.append(Utility.getintCond(" ", "IS_SUCCESS_REMIND", queryParam.get("isSuccessRemind")));

		sql.append("  group by vin) b  on a.remind_date = b.remind_date  and a.vin = b.vin ");

		sql.append(
				" left join tt_termly_maintain_l_remind c  on a.dealer_code = c.dealer_code  and a.vin = c.vin) b  on a.vin = b.vin "
				
				+ " left join tm_employee c  on b.reminder = c.employee_no  and b.dealer_code = c.dealer_code left join " );
				
//				+ " LEFT JOIN tm_user tu  ON tu.DEALER_CODE=b.DEALER_CODE  ");
		sql.append("( " + CommonConstants.VM_OWNER + " ) d ");

		sql.append(
				" on a.dealer_code = d.dealer_code  and a.owner_no = d.owner_no  left join  (select  max(ro_create_date) ro_create_date, vin,ro_no,ENGINE_NO  from tt_repair_order where dealer_code in  (select  share_entity  from ");
		sql.append("(" + CommonConstants.VM_ENTITY_SHARE_WITH + " ) g " + "where dealer_code='");

		sql.append(FrameworkUtil.getLoginInfo().getDealerCode() + "' ");

		sql.append(
				" and biz_code = 'TT_REPAIR_ORDER')  group by vin) e  on a.vin = e.vin "
				
				+ " LEFT JOIN  TT_SALES_PART sp ON sp.DEALER_CODE =a.DEALER_CODE AND sp.VIN=e.VIN "
				
				+ " left join tm_employee f  on a.dealer_code = f.dealer_code  and a.maintain_advisor = f.employee_no ");

		sql.append(" and a.owner_no <> '888888888888' ");

		sql.append("  LEFT JOIN TM_DEALER_BASICINFO db ON a.dealer_code=db.dealer_code  ");
		

		sql.append(Utility.getDateCond("a", "SALES_DATE", queryParam.get("salesDateFrom"), queryParam.get("salesDate"))
				+ " ");
		sql.append(Utility.getDateCond("e", "RO_CREATE_DATE", queryParam.get("lastBegin"), queryParam.get("lastEnd"))
				+ " ");
		sql.append(Utility.getStrCond("a", "MAINTAIN_ADVISOR", queryParam.get("maintainAdvisor")) + " ");
		sql.append(Utility.getStrCond("a", "BRAND", queryParam.get("brand")) + " ");
		sql.append(Utility.getStrCond("a", "MODEL", queryParam.get("model")) + " ");
		sql.append(Utility.getStrCond("a", "SERIES", queryParam.get("series")) + " ");
		sql.append(Utility.getintCond("d", "OWNER_PROPERTY", queryParam.get("ownerProperty")) + " ");
		sql.append(Utility.getStrLikeCond("a", "LICENSE", queryParam.get("license")) + " ");
		sql.append(Utility.getStrLikeCond("d", "ADDRESS", queryParam.get("address")) + " ");
		sql.append(Utility.getStrLikeCond("d", "OWNER_NAME", queryParam.get("ownerName")) + " ");
		sql.append(Utility.getStrLikeCond("a", "DELIVERER", queryParam.get("deliverer")) + " ");
		sql.append(Utility.getDateCond("b", "REMIND_DATE", queryParam.get("remindDateBegin"),
				queryParam.get("remindDateEnd")) + " ");
		sql.append(Utility.getDateCond("a", "DISCOUNT_EXPIRE_DATE", queryParam.get("discountExpireDateBegin"),
				queryParam.get("discountExpireDateEnd")) + " ");
		sql.append(Utility.getStrLikeCond("a", "VIN", queryParam.get("vin")) + " ");
		sql.append(Utility.getintCond("b", "REMIND_WAY", queryParam.get("remindWay")) + " ");
		sql.append(Utility.getStrCond("a", "DCRC_ADVISOR", queryParam.get("dcrcadvisor")) + " ");
		sql.append(" where 1=1 GROUP BY a.vin ");
		this.setWhere(sql, queryParam, queryList);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	private void setWhere(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {

		if (!StringUtils.isNullOrEmpty(queryParam.get("iSValid"))) {
			sql.append(" AND a.IS_VALID like ? ");
			queryList.add("%" + queryParam.get("iSValid") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindStatus"))) {
			sql.append(" AND b.REMIND_STATUS like ? ");
			queryList.add("%" + queryParam.get("remindStatus") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastMaintainDate"))) {
			sql.append(" AND a.LAST_MAINTAIN_DATE >= ?");
			queryList.add(queryParam.get("lastMaintainDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastMaintainDateTo"))) {
			sql.append(" AND a.LAST_MAINTAIN_DATE <= ?");
			queryList.add(queryParam.get("lastMaintainDateTo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("deliverer"))) {
			sql.append(" AND a.DELIVERER like ? ");
			queryList.add("%" + queryParam.get("deliverer") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" AND a.VIN like ? ");
			queryList.add("%" + queryParam.get("vin") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindDateF"))) {
			sql.append(" AND a.REMIND_DATE_F >= ?");
			queryList.add(queryParam.get("remindDateF"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindDateFTo"))) {
			sql.append(" AND a.REMIND_DATE_F <= ?");
			queryList.add(queryParam.get("remindDateFTo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vipNo"))) {
			sql.append(" AND a.VIP_NO like ? ");
			queryList.add("%" + queryParam.get("vipNo") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("address"))) {
			sql.append(" AND d.ADDRESS like ? ");
			queryList.add("%" + queryParam.get("address") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			sql.append(" AND a.LICENSE like ? ");
			queryList.add("%" + queryParam.get("license") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("salesDateFrom"))) {
			sql.append(" AND a.SALES_DATE >= ?");
			queryList.add(queryParam.get("salesDateFrom"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("salesDate"))) {
			sql.append(" AND a.SALES_DATE <= ?");
			queryList.add(queryParam.get("salesDate"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("remindWay"))) {
			sql.append(" AND b.REMIND_WAY like ? ");
			queryList.add("%" + queryParam.get("remindWay") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("nextMaintainMileage"))) {
			sql.append(" AND a.NEXT_MAINTAIN_MILEAGE like ? ");
			queryList.add("%" + queryParam.get("nextMaintainMileage") + "%");
		}

		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and a.BREAND like ? ");
			queryList.add("%" + queryParam.get("brandCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
			sql.append(" and a.SERIES like ? ");
			queryList.add("%" + queryParam.get("seriesCode") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
			sql.append(" and a.MODEL like ? ");
			queryList.add("%" + queryParam.get("modelCode") + "%");
		}
		/*
		 * if
		 * (!StringUtils.isNullOrEmpty(queryParam.get("nextMaintainDateFrom")))
		 * { sql.append(" AND a.NEXT_MAINTAIN_DATE >= ?");
		 * queryList.add(queryParam.get("nextMaintainDateFrom")); } if
		 * (!StringUtils.isNullOrEmpty(queryParam.get("nextMaintainDate"))) {
		 * sql.append(" AND a.NEXT_MAINTAIN_DATE <= ?");
		 * queryList.add(queryParam.get("nextMaintainDate")); }
		 */
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealer_code"))) {
			sql.append(" and a.DEALER_CODE like ? ");
			queryList.add("%" + queryParam.get("dealer_code") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("discountExpireDateBegin"))) {
			sql.append(" AND a.DISCOUNT_EXPIRE_DATE >= ?");
			queryList.add(queryParam.get("discountExpireDateBegin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("discountExpireDateEnd"))) {
			sql.append(" AND a.DISCOUNT_EXPIRE_DATE <= ?");
			queryList.add(queryParam.get("discountExpireDateEnd"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerProperty"))) {
			sql.append(" and d.OWNER_PROPERTY like ? ");
			queryList.add("%" + queryParam.get("ownerProperty") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isSuccessRemind"))) {
			sql.append(" and b.IS_SUCCESS_REMIND like ? ");
			queryList.add("%" + queryParam.get("isSuccessRemind") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
			sql.append("  and d.OWNER_NAME like ? ");
			queryList.add("%" + queryParam.get("ownerName") + "%");
		}

	}

	/**
	 * 查询DCRC专员
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryApplicant(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("SELECT EMPLOYEE_NAME,DEALER_CODE,EMPLOYEE_NO,IS_DCRC_ADVISOR FROM TM_EMPLOYEE WHERE 1=1 ");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Map> exportSolicitudeInfo(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append(
				" select  e.RO_CREATE_DATE, b.REMIND_ID,b.REMIND_WAY,a.DCRC_ADVISOR,b.IS_SUCCESS_REMIND,b.REMARK,a.VIP_NO,a.DISCOUNT_EXPIRE_DATE,a.IS_VALID,a.DELIVERER,a.DELIVERER_PHONE,a.DELIVERER_MOBILE,d.PHONE,d.MOBILE,d.ADDRESS,a.LICENSE,a.SALES_DATE,a.LAST_MAINTAIN_DATE,a.NEXT_MAINTAIN_DATE, ");
		sql.append(
				" a.NEXT_MAINTAIN_MILEAGE,d.OWNER_NAME,d.CONTACTOR_PHONE,d.CONTACTOR_MOBILE,d.CONTACTOR_NAME,d.CONTACTOR_ADDRESS,d.CONTACTOR_ZIP_CODE,a.LAST_MAINTENANCE_DATE,a.LAST_MAINTENANCE_MILEAGE,b.REMIND_DATE,b.REMIND_CONTENT,b.REMINDER,c.EMPLOYEE_NAME REMINDER_NAME,b.CUSTOMER_FEEDBACK,b.REMIND_FAIL_REASON,b.REMIND_STATUS,a.VIN,a.OWNER_NO,d.OWNER_PROPERTY,a.BRAND,a.SERIES,a.MODEL,a.SERVICE_ADVISOR,f.EMPLOYEE_NAME MAINTAIN_ADVISOR,a.LAST_MAINTAIN_MILEAGE,b.REMIND_DATE_S,b.REMIND_DATE_T,a.DEALER_CODE,db.dealer_shortname from ");
		sql.append(" ( " + CommonConstants.VM_VEHICLE);
		sql.append(
				" ) a left join (select a.*,c.remind_date_s,c.remind_date_t from tt_termly_maintain_remind a inner join (select vin,max(remind_date) remind_date  from tt_termly_maintain_remind  where dealer_code in (select share_entity from ");
		sql.append(" ( " + CommonConstants.VM_ENTITY_SHARE_WITH + " ) vm " + "where dealer_code='");
		sql.append(FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sql.append(" and biz_code = 'TT_ALL_REMIND')  and last_tag = 12781001");

		sql.append(Utility.getintCond(" ", "is_success_remind", queryParam.get("isSuccessRemind")));

		sql.append("  group by vin) b  on a.remind_date = b.remind_date  and a.vin = b.vin ");
		sql.append(
				" left join tt_termly_maintain_l_remind c  on a.dealer_code = c.dealer_code  and a.vin = c.vin) b  on a.vin = b.vin  left join tm_employee c  on b.reminder = c.employee_no  and b.dealer_code = c.dealer_code  left join ");
		sql.append("( " + CommonConstants.VM_OWNER + " ) d ");
		sql.append(
				" on a.dealer_code = d.dealer_code  and a.owner_no = d.owner_no  left join  (select  max(ro_create_date) ro_create_date, vin  from tt_repair_order  where dealer_code in  (select  share_entity  from ");
		sql.append("(" + CommonConstants.VM_ENTITY_SHARE_WITH + " ) g " + "where dealer_code='");
		sql.append(FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		sql.append(
				" and biz_code = 'TT_REPAIR_ORDER')  group by vin) e  on a.vin = e.vin  left join tm_employee f  on a.dealer_code = f.dealer_code  and a.maintain_advisor = f.employee_no ");
		sql.append(" and a.owner_no <> '888888888888' ");
		sql.append("  LEFT JOIN TM_DEALER_BASICINFO db ON a.dealer_code=db.dealer_code  ");
		sql.append(Utility.getDateCond("a", "SALES_DATE", queryParam.get("salesDateFrom"), queryParam.get("salesDate"))
				+ " ");
		sql.append(Utility.getDateCond("e", "RO_CREATE_DATE", queryParam.get("lastBegin"), queryParam.get("lastEnd"))
				+ " ");
		sql.append(Utility.getStrCond("a", "MAINTAIN_ADVISOR", queryParam.get("maintainAdvisor")) + " ");
		sql.append(Utility.getStrCond("a", "BRAND", queryParam.get("brand")) + " ");
		sql.append(Utility.getStrCond("a", "MODEL", queryParam.get("model")) + " ");
		sql.append(Utility.getStrCond("a", "SERIES", queryParam.get("series")) + " ");
		sql.append(Utility.getintCond("d", "owner_property", queryParam.get("ownerProperty")) + " ");
		sql.append(Utility.getStrLikeCond("a", "license", queryParam.get("license")) + " ");
		sql.append(Utility.getStrLikeCond("d", "address", queryParam.get("address")) + " ");
		sql.append(Utility.getStrLikeCond("d", "owner_name", queryParam.get("ownerName")) + " ");
		sql.append(Utility.getStrLikeCond("a", "deliverer", queryParam.get("deliverer")) + " ");
		sql.append(Utility.getDateCond("b", "remind_date", queryParam.get("remindDateBegin"),
				queryParam.get("remindDateEnd")) + " ");
		sql.append(Utility.getDateCond("a", "discount_expire_date", queryParam.get("discountExpireDateBegin"),
				queryParam.get("discountExpireDateEnd")) + " ");
		sql.append(Utility.getStrLikeCond("a", "vin", queryParam.get("vin")) + " ");
		sql.append(Utility.getintCond("b", "remind_way", queryParam.get("remindWay")) + " ");
		sql.append(Utility.getStrCond("a", "dcrc_advisor", queryParam.get("dcrcadvisor")) + " ");
		sql.append(" where 1=1 ");
		this.setWhere(sql, queryParam, queryList);
		List<Map> list = DAOUtil.findAll(sql.toString(), queryList);
		for (Map map : list) {
			if (map.get("REMIND_WAY") != null && map.get("REMIND_WAY") != "") {
				if (Integer.parseInt(map.get("REMIND_WAY").toString()) == DictCodeConstants.DICT_REMIND_WAY_TEL) {
					map.put("REMIND_WAY", "电话");
				} else if (Integer
						.parseInt(map.get("REMIND_WAY").toString()) == DictCodeConstants.DICT_REMIND_WAY_MESSAGE) {
					map.put("REMIND_WAY", "短信");
				} else if (Integer
						.parseInt(map.get("REMIND_WAY").toString()) == DictCodeConstants.DICT_REMIND_WAY_MISSIVE) {
					map.put("REMIND_WAY", "书信");
				}
				if (map.get("VIP_NO") != null && map.get("VIP_NO") != "") {
					if (Integer.parseInt(map.get("VIP_NO").toString()) == DictCodeConstants.STATUS_IS_YES) {
						map.put("VIP_NO", "是");
					} else if (Integer.parseInt(map.get("VIP_NO").toString()) == DictCodeConstants.STATUS_IS_NOT) {
						map.put("VIP_NO", "否");
					}
				}
				if (map.get("REMIND_STATUS") != null && map.get("REMIND_STATUS") != "") {
					if (Integer.parseInt(map.get("REMIND_STATUS")
							.toString()) == DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_AGAIN) {
						map.put("REMIND_STATUS", "继续提醒");
					} else if (Integer.parseInt(map.get("REMIND_STATUS")
							.toString()) == DictCodeConstants.DICT_VEHICLE_REMINDER_STATUS_FAIL) {
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
				if (map.get("IS_SUCCESS_REMIND") != null && map.get("IS_SUCCESS_REMIND") != "") {
					if (Integer.parseInt(map.get("IS_SUCCESS_REMIND").toString()) == DictCodeConstants.STATUS_IS_YES) {
						map.put("IS_SUCCESS_REMIND", "是");
					} else if (Integer
							.parseInt(map.get("IS_SUCCESS_REMIND").toString()) == DictCodeConstants.STATUS_IS_NOT) {
						map.put("IS_SUCCESS_REMIND", "否");
					}
				}
				if (map.get("IS_CHECK_RADIO") != null && map.get("IS_CHECK_RADIO") != "") {
					if (Integer.parseInt(map.get("IS_CHECK_RADIO").toString()) == DictCodeConstants.STATUS_IS_YES) {
						map.put("IS_CHECK_RADIO", "是");
					} else if (Integer
							.parseInt(map.get("IS_CHECK_RADIO").toString()) == DictCodeConstants.STATUS_IS_NOT) {
						map.put("IS_CHECK_RADIO", "否");
					}
				}

			}

		}
		return list;
	}

	/**
	 * 新增提醒记录
	 * 
	 * @author Administrator
	 * @date 2017年3月29日
	 * @param vins
	 * @param ownerNo
	 * @param tmVehicleDTO
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.solicitude.solicitudeService#addeRemind(java.lang.String,
	 *      java.lang.String,
	 *      com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO)
	 */
	@Override
	public void addeRemind(String vins, String ownerNo, TermlyMaintainRemindDTO termlyMaintainRemindDTO)
			throws ServiceBizException {

		VehiclePO tmVehiclePO = VehiclePO.findByCompositeKeys(termlyMaintainRemindDTO.getVin(),
				FrameworkUtil.getLoginInfo().getDealerCode());// 修改
		TermlyMaintainRemindPO termlyMaintainRemindPO = new TermlyMaintainRemindPO();// 新增
		setvehiclePo(tmVehiclePO, termlyMaintainRemindDTO, termlyMaintainRemindPO);
	}

	/**
	 * 设置提醒表属性
	 * 
	 * @author Administrator
	 * @date 2017年3月28日
	 * @param tmVehiclePO
	 * @param tmVehicleDTO
	 * @param flag
	 */

	private void setvehiclePo(VehiclePO tmVehiclePO, TermlyMaintainRemindDTO termlyMaintainRemindDTO,
			TermlyMaintainRemindPO termlyMaintainRemindPO) {

		tmVehiclePO.setDouble("NEXT_MAINTAIN_MILEAGE", termlyMaintainRemindDTO.getNextmaintainmileage());// 下次保养里程
		tmVehiclePO.setDate("NEXT_MAINTAIN_DATE", termlyMaintainRemindDTO.getNextmaintainDate());// 预计下次保养日期
		tmVehiclePO.setDouble("CURRENT_MILEAGE", termlyMaintainRemindDTO.getCurrentmileage());// 当前实际里程
		tmVehiclePO.saveIt();

		termlyMaintainRemindPO.setString("VIN", termlyMaintainRemindDTO.getVin());
		termlyMaintainRemindPO.setString("OWNER_NO", termlyMaintainRemindDTO.getOwnerNo());
		termlyMaintainRemindPO.set("REMIND_DATE", new Timestamp(System.currentTimeMillis()));// 提醒时间
		termlyMaintainRemindPO.setString("LAST_TAG", DictCodeConstants.IS_YES);// 最新记录标识
		termlyMaintainRemindPO.setString("REMIND_CONTENT", termlyMaintainRemindDTO.getRemindContent());// 提醒内容
		termlyMaintainRemindPO.setString("CUSTOMER_FEEDBACK", termlyMaintainRemindDTO.getCustomerFeedback());// 客户反馈
		termlyMaintainRemindPO.setString("REMIND_WAY", termlyMaintainRemindDTO.getRemindWay());// 提醒方式
//		termlyMaintainRemindPO.setString("REMINDER", termlyMaintainRemindDTO.getReminder());// 提醒人
		termlyMaintainRemindPO.setString("REMIND_STATUS", termlyMaintainRemindDTO.getRemindStatus());// 提醒状态
		termlyMaintainRemindPO.setString("REMIND_FAIL_REASON", termlyMaintainRemindDTO.getRemindFailReason());// 提醒失败原因
		termlyMaintainRemindPO.setString("REMARK", termlyMaintainRemindDTO.getRemark()); // 备注
		termlyMaintainRemindPO.saveIt();

	}

	/**
	 * 根据车辆VIN号或者车牌号来查找
	 * 
	 * @param vin
	 * @return
	 * @throws ServiceBizException(non-Javadoc)
	 */

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> queryVehicelByid(String vin, String license) throws ServiceBizException {
		List<Map> list = queryBusinessType(vin, license);

		Map map = list.get(0);
		vin = "";
		license = "";
		if (map.get("VIN") != null && !map.get("VIN").equals("")) {
			vin = map.get("VIN").toString();
		}
		if (map.get("LICENSE") != null && !map.get("LICENSE").equals("")) {
			license = map.get("LICENSE").toString();
		}
		StringBuffer sql = new StringBuffer();
		// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT  A.*,ter.REMINDER,tu.USER_NAME FROM (" + CommonConstants.VM_VEHICLE + ") A "
				+ " LEFT JOIN tt_termly_maintain_remind ter  ON A.DEALER_CODE = ter.DEALER_CODE AND A.VIN = ter.VIN "
				+ " LEFT JOIN tm_user tu  ON tu.DEALER_CODE=ter.DEALER_CODE  AND tu.USER_id= ter.REMINDER "
				+ " WHERE A.VIN = '" +vin+"' "
				+ " AND A.LICENSE = '"+license+"'  ");
		System.err.println(sql.toString());

		List<Map> resultList = Base.findAll(sql.toString());

		return resultList;
	}

	/**
	 * TODO description
	 * 
	 * @author Administrator
	 * @date 2017年3月31日
	 * @param id
	 * @return 根据车辆VIN号或者车牌号来查找
	 */

	@SuppressWarnings("rawtypes")
	private List<Map> queryBusinessType(String vin, String license) {

		// String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String sb = new String(
				" SELECT ve.VIN,ve.DEALER_CODE,ve.LAST_MAINTENANCE_DATE,ve.LAST_MAINTAIN_DATE,ve.LAST_MAINTENANCE_MILEAGE,ve.LAST_MAINTAIN_MILEAGE,ve.CURRENT_MILEAGE,ve.LICENSE from tm_vehicle ve  where ve.vin = '"
						+ vin + "' AND ve.license= '" + license + "' ");
		System.err.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(vin);
		queryList.add(license);
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}

	/**
	 * @author Administrator
	 * @date 2017年3月31日
	 * @param id
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)根据车主编号进行回显
	 * @see com.yonyou.dms.customer.service.solicitude.solicitudeService#queryOwnerNOByid(java.lang.String)
	 */

	@SuppressWarnings({ "rawtypes" })
	@Override
	public List<Map> queryOwnerNOByid(String id) throws ServiceBizException {
		List<Map> list = queryQwnerBy(id);
		Map map = list.get(0);
		String ownerNo = "";
		if (map.get("OWNER_NO") != null && !map.get("OWNER_NO").equals("")) {
			ownerNo = map.get("OWNER_NO").toString();
		}
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT  A.*,ve.*,ve.ENGINE_NO,A.OWNER_NAME FROM (" + CommonConstants.VM_OWNER + ") A "

				+ " LEFT JOIN TM_OWNER_MEMO B ON B.DEALER_CODE = A.DEALER_CODE AND B.OWNER_NO = A.OWNER_NO "
				+ " LEFT JOIN ("+CommonConstants.VM_VEHICLE+") ve ON ve.DEALER_CODE= A.DEALER_CODE AND ve.OWNER_NO=A.OWNER_NO "
//				+ " LEFT JOIN TT_REPAIR_ORDER re ON re.VIN=ve.VIN AND re.DEALER_CODE = ve.DEALER_CODE "
//				+ " LEFT JOIN TT_BALANCE_ACCOUNTS ba ON ba.DEALER_CODE = re.DEALER_CODE AND ba.RO_NO =re.RO_NO "
				+ " WHERE A.DEALER_CODE = '" + dealerCode + "'  AND  A.OWNER_NO = '" + ownerNo + "' ");
		System.out.println(sql.toString());
		List<Map> resultList = Base.findAll(sql.toString());
		return resultList;
	}

	/**
	 * 根据车主编号进行回显 TODO description
	 * 
	 * @author Administrator
	 * @date 2017年3月31日
	 * @param id
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	private List<Map> queryQwnerBy(String id) {
//		String sb = new String(
//				"SELECT OWNER_NO,OWNER_NAME,OWNER_SPELL,GENDER,CT_CODE, CERTIFICATE_NO,PROVINCE,CITY,DISTRICT,ADDRESS,ZIP_CODE,INDUSTRY_FIRST,INDUSTRY_SECOND,TAX_NO,PRE_PAY,ARREARAGE_AMOUNT,CUS_RECEIVE_SORT FROM tm_owner WHERE owner_no ='"
//						+ id + "' ");
		String sb=new String(" SELECT  ow.OWNER_NO, ow.OWNER_NAME, ow.OWNER_SPELL, ow.GENDER, ow.CT_CODE, ow.CERTIFICATE_NO, ow.PROVINCE, ow.CITY, ow.DISTRICT, ow.ADDRESS, ow.ZIP_CODE, ow.INDUSTRY_FIRST," 
				+ " ow.INDUSTRY_SECOND, ow.TAX_NO, ow.PRE_PAY, ow.ARREARAGE_AMOUNT, ow.CUS_RECEIVE_SORT,ve.* "
				+ " FROM tm_owner ow LEFT JOIN tm_vehicle ve ON ow.dealer_code=ve.dealer_code AND ow.owner_no=ve.owner_no"
				+ " WHERE ow.owner_no ='"+id+"'	AND 1=1	 ");
		System.out.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}

	/**
	 * 根据VIN号,车主编号进行回显查询提醒记录
	 * 
	 * @author Administrator
	 * @date 2017年3月29日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.solicitude.solicitudeService#queryRemindre(java.util.Map)
	 */
	@SuppressWarnings({ "rawtypes" })
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
		sb.append(" (" + DictCodeConstants.DICT_REMINDER_TYPE_TERMLY_MAINTAIN
				+ ") AS REMIND_TYPE, REMIND_ID, OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT, CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK,"
				+ " REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS, VER,  A.LAST_NEXT_MAINTAIN_DATE AS LAST_NEXT_MAINTAIN_DATE, LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE,  0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM "
				+ " TT_TERMLY_MAINTAIN_REMIND A   WHERE A.DEALER_CODE IN  (SELECT  SHARE_ENTITY  FROM ("
				+ CommonConstants.VM_ENTITY_SHARE_WITH
				+ ") vm  WHERE DEALER_CODE = '2100000'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY = "
				+ DictCodeConstants.D_KEY + " " );
		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
//		sb.append("  UNION SELECT  (" + DictCodeConstants.DICT_REMINDER_TYPE_GUAR_ATTERM
//				+ ") AS REMIND_TYPE, REMIND_ID,  OWNER_NO, VIN, REMIND_DATE, REMIND_CONTENT,  CUSTOMER_FEEDBACK, REMIND_FAIL_REASON, REMARK, REMINDER, REMIND_WAY, LAST_TAG, REMIND_STATUS,"
//				+ " VER, '' AS LAST_NEXT_MAINTAIN_DATE, 0 AS LAST_NEXT_MAINTAIN_MILEAGE, A.DEALER_CODE, 0 AS RENEWAL_REMIND_STATUS, 0 AS RENEWAL_FAILED_REASON, '' AS RENEWAL_REMARK, '' AS RENEWAL_FAILED_DATE  FROM TT_REPAIR_EXPIRE_REMIND A   WHERE A.DEALER_CODE IN "
//				+ "  (SELECT  SHARE_ENTITY  FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH
//				+ ") vm6  WHERE DEALER_CODE = '2100000'  AND biz_code = 'TT_ALL_REMIND')  AND A.D_KEY ="
//				+ DictCodeConstants.D_KEY + " ");

		// if (vin != null && !vin.equals("")&&ownerNo!=null &&
		// !ownerNo.equals("")){
		// sb.append(" AND A.VIN = '"+vin+"' AND A.OWNER_NO = '"+ownerNo+"' " );
		// }
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

	/**
	 * 根据VIN号,车主编号进行回显查询提醒记录 TODO description
	 * 
	 * @author Administrator
	 * @date 2017年4月6日
	 * @param sb
	 * @param queryParam
	 * @param queryList
	 */
//	private void setWhereOwnerNo(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
//
//		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
//			sb.append(" AND A.VIN  like ? ");
//			queryList.add("%" + queryParam.get("vin") + "%");
//		}
//		if (!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))) {
//			sb.append(" AND A.OWNER_NO like ? ");
//			queryList.add("%" + queryParam.get("ownerNo") + "%");
//		}
//	}
	/**
	 * 根据提醒编号进行回显查询提醒记录信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryByRemindID(String remindId) throws ServiceBizException {
		TermlyMaintainRemindPO termlyMaintainRemindPO = TermlyMaintainRemindPO.findById(remindId);
		List<Map> list = new ArrayList<Map>();
		list.add(termlyMaintainRemindPO.toMap());
		return list;
	}
	
	/**
	 * 根据提醒编号進行修改提醒记录
	 */
	@Override
	public void modifyRemindID(String remindId, TermlyMaintainRemindDTO termlyMaintainRemindDTO)
			throws ServiceBizException {

		TermlyMaintainRemindPO termlyMaintainRemindPO = TermlyMaintainRemindPO.findById(remindId);
		termlyMaintainRemindPO.setString("CUSTOMER_FEEDBACK",termlyMaintainRemindDTO.getCustomerFeedback());
		termlyMaintainRemindPO.setString("REMARK", termlyMaintainRemindDTO.getRemark());
		termlyMaintainRemindPO.saveIt();
	
	}
	
	/**
	 * 根据VIN号,车牌号进行回显查询提醒记录 TODO description
	 * 
	 * @author Administrator
	 * @date 2017年4月2日
	 * @param vin
	 * @return
	 */

	@SuppressWarnings("rawtypes")
	private List<Map> queryReminderBy(String vin, String ownerNo) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

		String sb = new String(
				" SELECT db.DEALER_SHORTNAME, re.OWNER_NO, om.OWNER_NAME, ov.LICENSE, re.VIN,re.REMIND_ID, re.REMIND_DATE, re.REMIND_WAY, re.REMIND_CONTENT, "
						+ " re.CUSTOMER_FEEDBACK, re.REMINDER, re.REMIND_STATUS,"
						+ " re.REMIND_FAIL_REASON, re.REMARK, ie.RENEWAL_REMIND_STATUS, ie.RENEWAL_FAILED_REASON, ie.RENEWAL_REMARK , ie.RENEWAL_FAILED_DATE,"
						+ " ov.NEW_VEHICLE_DATE,ov.NEW_VEHICLE_MILEAGE, re.LAST_NEXT_MAINTAIN_DATE, re.LAST_NEXT_MAINTAIN_MILEAGE FROM tt_termly_maintain_remind re "
						+ " LEFT JOIN tm_owner om ON om.OWNER_NO=re.OWNER_NO AND om.`DEALER_CODE`=re.`DEALER_CODE`"
						+ " LEFT JOIN tm_vehicle ov ON ov.`VIN`=re.`VER` AND ov.`DEALER_CODE`=re.`DEALER_CODE`"
						+ " LEFT JOIN TM_DEALER_BASICINFO db  ON re.dealer_code=db.dealer_code "
						+ " LEFT JOIN tt_insurance_expire_remind ie ON ie.vin=re.`VIN` AND ie.`DEALER_CODE`=re.`DEALER_CODE`"
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
	 * 修改车主信息
	 * 
	 * @author Administrator
	 * @date 2017年3月29日
	 * @param vin
	 * @param tmVehicleDTO
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.solicitude.solicitudeService#updateOwner(java.lang.String,
	 *      com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO)
	 */
	@Override
	public void updateOwner(String ownerNo, OwnerDTO ownerDTO) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		CarownerPO ownerPO = CarownerPO.findByCompositeKeys(ownerDTO.getOwnerNo(),dealerCode);
		settOwnerPo(ownerPO, ownerDTO);
		ownerPO.saveIt();
		List<OwnerMemoPO> findBySQL = OwnerMemoPO.findBySQL("SELECT * FROM TM_OWNER_MEMO WHERE OWNER_NO = ? ", ownerNo);
		if (findBySQL.size() > 0) {
			OwnerMemoPO ownerMemoPO = findBySQL.get(0);
			ownerMemoPO.setString("MEMO_INFO", ownerDTO.getOwnerMemo());
			ownerMemoPO.saveIt();
		} else {
			OwnerMemoPO ownerMemoPO = new OwnerMemoPO();
			ownerMemoPO.setString("MEMO_INFO", ownerDTO.getOwnerMemo());
			ownerMemoPO.setString("OWNER_NO", ownerDTO.getOwnerNo());
			ownerMemoPO.saveIt();
		}
	}

	/**
	 * 设置车主属性
	 * 
	 * @author Administrator
	 * @date 2017年3月29日
	 * @param tmVehiclePO
	 * @param tmVehicleDTO
	 * @param b
	 */
	private void settOwnerPo(CarownerPO ownerPO, OwnerDTO ownerDTO) {

		ownerPO.setInteger("OWNER_PROPERTY", ownerDTO.getOwnerProperty());
		ownerPO.setString("OWNER_NAME", ownerDTO.getOwnerName());
		ownerPO.setString("OWNER_SPELL", ownerDTO.getOwnerSpell());
		ownerPO.setInteger("GENDER", ownerDTO.getGender());
		ownerPO.setInteger("CT_CODE", ownerDTO.getCtCode());
		ownerPO.setString("CERTIFICATE_NO", ownerDTO.getCertificateNo());
		ownerPO.setInteger("PROVINCE", ownerDTO.getProvince());
		ownerPO.setInteger("city", ownerDTO.getCity());
		ownerPO.setInteger("DISTRICT", ownerDTO.getDistrict());
		ownerPO.setString("ADDRESS", ownerDTO.getAddress());
		ownerPO.setString("ZIP_CODE", ownerDTO.getZipCode());
		ownerPO.setInteger("INDUSTRY_FIRST", ownerDTO.getIndustryFirst());
		ownerPO.setInteger("INDUSTRY_SECOND", ownerDTO.getIndustrySecond());
		ownerPO.setString("TAX_NO", ownerDTO.getTaxNo());
		ownerPO.setDouble("PRE_PAY", ownerDTO.getPrePay());
		ownerPO.setDouble("ARREARAGE_AMOUNT", ownerDTO.getArrearageAmount());
		ownerPO.setDouble("CUS_RECEIVE_SORT", ownerDTO.getCusReceiveSort());
		ownerPO.setString("CERTIFICATE_NO", ownerDTO.getCertificateNo());
		ownerPO.setString("SECOND_ADDRESS", ownerDTO.getSecondAddress());
		ownerPO.setString("PHONE", ownerDTO.getPhone());
		ownerPO.setString("E_MAIL", ownerDTO.getEMail());
		ownerPO.setDate("BIRTHDAY", ownerDTO.getBirthday());
		ownerPO.setString("MOBILE", ownerDTO.getMobile());
		ownerPO.setDouble("FAMILY_INCOME", ownerDTO.getFamilyIncome());
		ownerPO.setDouble("OWNER_MARRIAGE", ownerDTO.getOwnerMarriage());
		ownerPO.setDouble("EDU_LEVEL", ownerDTO.getEduLevel());
		ownerPO.setString("HOBBY", ownerDTO.getHobby());
		ownerPO.setString("CONTACTOR_NAME", ownerDTO.getContactorName());
		ownerPO.setInteger("CONTACTOR_GENDER", ownerDTO.getContactorGender());
		ownerPO.setString("CONTACTOR_PHONE", ownerDTO.getContactorPhone());
		ownerPO.setString("CONTACTOR_EMAIL", ownerDTO.getContactorEmail());
		ownerPO.setString("CONTACTOR_FAX", ownerDTO.getContactorFax());
		ownerPO.setString("CONTACTOR_MOBILE", ownerDTO.getContactorMobile());
		ownerPO.setInteger("CONTACTOR_PROVINCE", ownerDTO.getContactorProvince());
		ownerPO.setString("CONTACTOR_CITY", ownerDTO.getContactorCity());
		ownerPO.setString("CONTACTOR_DISTRICT", ownerDTO.getContactorDistrict());
		ownerPO.setString("CONTACTOR_ADDRESS", ownerDTO.getContactorAddress());
		ownerPO.setString("CONTACTOR_ZIP_CODE", ownerDTO.getContactorZipCode());
		ownerPO.setInteger("CONTACTOR_HOBBY_CONTACT", ownerDTO.getContactorHobbyContact());
		ownerPO.setInteger("CONTACTOR_VOCATION_TYPE", ownerDTO.getContactorVocationType());
		ownerPO.setInteger("CONTACTOR_POSITION", ownerDTO.getContactorPosition());
		ownerPO.setString("REMARK", ownerDTO.getRemark());

	}
	
	/**
	 * 修改车辆信息
	 * 
	 * @author Administrator
	 * @date 2017年3月29日
	 * @param vin
	 * @param tmVehicleDTO
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.solicitude.solicitudeService#updateOwner(java.lang.String,
	 *      com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO)
	 */
	@Override
	public void updateVehicle(String vin, TmVehicleDTO tmVehicleDTO) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		VehiclePO vehiclePO =VehiclePO.findByCompositeKeys(vin, dealerCode);
		setVehiclePo(vehiclePO, tmVehicleDTO);
		vehiclePO.saveIt();
		List<VehicleMemoPO> findBySQL = VehiclePO.findBySQL("SELECT * FROM TM_VEHICLE_MEMO WHERE VIN = ? ", vin);
		if (findBySQL.size() > 0) {
			VehicleMemoPO vehicleMemoPO = findBySQL.get(0);
			vehicleMemoPO.setString("MEMO_INFO", tmVehicleDTO.getMemoInfo());
			vehicleMemoPO.saveIt();
		} else {
			VehicleMemoPO vehicleMemoPO = new VehicleMemoPO();
			vehicleMemoPO.setString("MEMO_INFO", tmVehicleDTO.getMemoInfo());
			vehicleMemoPO.setString("VIN", tmVehicleDTO.getVin());
			vehicleMemoPO.saveIt();
		}
	}
	/**
	 * 设置车辆属性
	 * @param vehiclePO
	 * @param tmVehicleDTO
	 */
	private void setVehiclePo(VehiclePO vehiclePO, TmVehicleDTO tmVehicleDTO) {
		vehiclePO.setString("ENGINE_NO", tmVehicleDTO.getEngineNo());
		vehiclePO.setString("EXHAUST_QUANTITY", tmVehicleDTO.getExhaustQuantity());
		vehiclePO.setDate("PRODUCT_DATE", tmVehicleDTO.getProductDate());
		vehiclePO.setString("BRAND", tmVehicleDTO.getBrand());
		vehiclePO.setString("APACKAGE", tmVehicleDTO.getApAckAge());
		vehiclePO.setString("ENGINE_NO_OLD", tmVehicleDTO.getEngineNoOld());
		vehiclePO.setString("SHIFT_TYPE", tmVehicleDTO.getShifeType());
		vehiclePO.setInteger("FUEL_TYPE", tmVehicleDTO.getFuelType());
		vehiclePO.setInteger("BUSINESS_KIND", tmVehicleDTO.getBusinessKind());
		vehiclePO.setInteger("VEHICLE_PURPOSE", tmVehicleDTO.getVehiclePurpose());
		vehiclePO.setString("GEAR_BOX", tmVehicleDTO.getGearBox());
		vehiclePO.setString("KEY_NUMBER", tmVehicleDTO.getKeynumber());
		vehiclePO.setString("BRAND_MODEL", tmVehicleDTO.getBrandModel());
		vehiclePO.setString("INNER_COLOR", tmVehicleDTO.getInnerColor());

		vehiclePO.setString("INSURANCE_ADVISOR", tmVehicleDTO.getInsuranceAdvisor());
		vehiclePO.setString("MAINTAIN_ADVISOR", tmVehicleDTO.getMaintainAdvisor());
		vehiclePO.setString("PRODUCTING_AREA", tmVehicleDTO.getProductingArea());
		vehiclePO.setString("DCRC_ADVISOR", tmVehicleDTO.getDcrcAdvisor());

		vehiclePO.setString("VSN", tmVehicleDTO.getVsn());
		vehiclePO.setInteger("DISCHARGE_STANDARD", tmVehicleDTO.getDischargeStandrd());
		vehiclePO.setInteger("WAYS_TO_BUY", tmVehicleDTO.getWaysToBuy());
//		vehiclePO.setString("DEALER_SHORTNAME", tmVehicleDTO.getDealercode());
		// vehiclePO.setString("USER_NAME", tmVehicleDTO.get));
		vehiclePO.setString("SALES_MILEAGE", tmVehicleDTO.getSalesMileage());
		vehiclePO.setDate("LICENSE_DATE", tmVehicleDTO.getLicenseDate());
		vehiclePO.setInteger("IS_SELF_COMPANY", tmVehicleDTO.getIsSelfCompany());
		vehiclePO.setDate("BUSINESS_DATE", tmVehicleDTO.getBusinessDate());
		vehiclePO.setString("CONTRACT_NO", tmVehicleDTO.getContractNo());
		vehiclePO.setDate("CONTRACT_DATE", tmVehicleDTO.getContractDate());
		vehiclePO.setString("VIP_NO", tmVehicleDTO.getVipNo());
		vehiclePO.setString("DISCOUNT_MODE_CODE", tmVehicleDTO.getDiscountModeCode());
		vehiclePO.setString("DELIVERER", tmVehicleDTO.getDeliverer());
		vehiclePO.setString("DELIVERER_GENDER", tmVehicleDTO.getDelivererGender());
		vehiclePO.setString("DELIVERER_RELATION_TO_OWNER", tmVehicleDTO.getDelivererRelationToOwner());
		vehiclePO.setInteger("DELIVERER_HOBBY_CONTACT", tmVehicleDTO.getDelivererHobbyContact());
		vehiclePO.setString("DELIVERER_PHONE", tmVehicleDTO.getDelivererPhone());
		vehiclePO.setString("DELIVERER_MOBILE", tmVehicleDTO.getDelivererMobile());
		vehiclePO.setString("DELIVERER_CREDIT", tmVehicleDTO.getDelivererCredit());
		vehiclePO.setString("ZIP_CODE", tmVehicleDTO.getZipCode());
		vehiclePO.setDouble("IS_TRACE", tmVehicleDTO.getIstrace());
		vehiclePO.setDate("TRACE_TIME", tmVehicleDTO.getTraceTime());
		vehiclePO.setString("DELIVERER_ADDRESS", tmVehicleDTO.getDelivererAddress());
		vehiclePO.setString("DELIVERER_COMPANY", tmVehicleDTO.getDelivererCompany());
		vehiclePO.setString("REMARK", tmVehicleDTO.getRemark());
	}

	/**
	 * @author Administrator
	 * @date 2017年4月3日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.solicitude.solicitudeService#querySales(java.util.Map)
	 */

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> querySales(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append(" SELECT aa.*,C.OWNER_NAME,C.PHONE,C.MOBILE,B.BRAND,B.SERIES,B.MODEL from (SELECT '定期保养提醒' description,cast(D.REMIND_ID as char(14))  REMIND_ID, ");
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
		stringBuffer.append(" '' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,A.OWNER_NAME,A.DELIVERER_MOBILE PHONE,A.DELIVERER_PHONE MOBILE,A.BRAND,A.SERIES,A.MODEL FROM  TT_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TT_REPAIR_ORDER E ON A.RO_NO=E.RO_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN Tt_Technician_I TECH ON TECH.DEALER_CODE=E.DEALER_CODE ");
		stringBuffer.append(" AND TECH.RO_NO=E.RO_NO AND TECH.D_KEY=0 LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO ");
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
		stringBuffer.append(" A.TRANCER REMINDER,'' CUSTOMER_FEEDBACK,0 REMIND_STATUS,A.VIN,F.OWNER_NAME,E.CONTACTOR_PHONE PHONE,E.CONTACTOR_MOBILE MOBILE,H.BRAND,H.SERIES,H.MODEL FROM  TT_LOSS_VEHICLE_TRACE_TASK A ");
		stringBuffer.append(" LEFT JOIN TM_CUSTOMER E ON A.OWNER_NO=E.CUSTOMER_NO AND A.DEALER_CODE=E.DEALER_CODE AND E.D_KEY = 0 LEFT JOIN ("+ CommonConstants.VM_VEHICLE + ") H ON A.VIN = H.VIN  ");
		stringBuffer.append(" AND A.DEALER_CODE = H.DEALER_CODE LEFT JOIN ("+ CommonConstants.VM_OWNER +") F ON H.OWNER_NO=F.OWNER_NO AND A.DEALER_CODE=F.DEALER_CODE Left join(select distinct  Q.TRACE_TASK_ID,N.QUESTIONNAIRE_NAME ");
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
	
	/**
	 * 查询维修历史
	 */
	@Override
	public PageInfoDto queryMaintainhistory(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				"select A.VIN,B.CONSULTANT,A.DEALER_CODE, A.RO_NO, A.SALES_PART_NO, A.BOOKING_ORDER_NO, A.ESTIMATE_NO,");
		sql.append("   A.RO_TYPE, A.REPAIR_TYPE_CODE, A.OTHER_REPAIR_TYPE, A.VEHICLE_TOP_DESC, ");
		sql.append("    A.SEQUENCE_NO, A.PRIMARY_RO_NO, A.INSURATION_NO, A.INSURATION_CODE,");
		sql.append("    A.IS_CUSTOMER_IN_ASC, A.IS_SEASON_CHECK, A.OIL_REMAIN, A.IS_WASH, A.IS_TRACE,");
		sql.append("   A.TRACE_TIME, A.NO_TRACE_REASON, A.NEED_ROAD_TEST, A.RECOMMEND_EMP_NAME,");
		sql.append("     A.RECOMMEND_CUSTOMER_NAME, A.SERVICE_ADVISOR, A.SERVICE_ADVISOR_ASS, A.RO_STATUS,");
		sql.append("    A.RO_CREATE_DATE, A.END_TIME_SUPPOSED, A.CHIEF_TECHNICIAN, A.OWNER_NO, A.OWNER_NAME,");
		sql.append("    A.OWNER_PROPERTY, A.LICENSE, A.ENGINE_NO, A.BRAND, A.SERIES, A.MODEL, A.IN_MILEAGE,");
		sql.append("    A.OUT_MILEAGE, A.IS_CHANGE_ODOGRAPH, A.CHANGE_MILEAGE, A.TOTAL_CHANGE_MILEAGE,");
		sql.append("    A.TOTAL_MILEAGE, A.DELIVERER, A.DELIVERER_GENDER, A.DELIVERER_PHONE,");
		sql.append("    A.DELIVERER_MOBILE, A.FINISH_USER, A.COMPLETE_TAG, A.WAIT_INFO_TAG, A.WAIT_PART_TAG,");
		sql.append("    A.COMPLETE_TIME, A.FOR_BALANCE_TIME, A.DELIVERY_TAG, A.DELIVERY_DATE, A.LABOUR_PRICE");
		sql.append("    , A.LABOUR_AMOUNT, A.REPAIR_PART_AMOUNT, A.SALES_PART_AMOUNT, A.ADD_ITEM_AMOUNT,");
		sql.append("    A.OVER_ITEM_AMOUNT, A.REPAIR_AMOUNT, A.ESTIMATE_AMOUNT, A.BALANCE_AMOUNT,");
		sql.append("    A.RECEIVE_AMOUNT, A.SUB_OBB_AMOUNT, A.DERATE_AMOUNT, A.TRACE_TAG, A.REMARK,");
		sql.append("    A.TEST_DRIVER, A.PRINT_RO_TIME, A.RO_CHARGE_TYPE, A.PRINT_RP_TIME, A.IS_ACTIVITY,");
		sql.append(
				"    A.CUSTOMER_DESC, A.LOCK_USER, A.IS_CLOSE_RO, A.RO_SPLIT_STATUS,A.SO_NO,C.EMPLOYEE_NAME as service_advisor_name ,");
		sql.append("    TECH.TECHNICIAN AS TECHNICIAN_UNITE,TECH.LABOUR_NAME AS LABOUR_NAME_UNITE,db.DEALER_SHORTNAME ");
		sql.append(
				" FROM TT_REPAIR_ORDER A LEFT JOIN TT_SALES_PART B ON A.dealer_code=B.dealer_code AND A.RO_NO=B.RO_NO  ");
		sql.append(" LEFT JOIN TM_DEALER_BASICINFO db ON a.dealer_code=db.dealer_code  ");
		
		sql.append(" LEFT JOIN TM_EMPLOYEE c on A.dealer_code=C.dealer_code and A.SERVICE_ADVISOR=c.EMPLOYEE_NO ");
		sql.append(
				" LEFT JOIN TT_TECHNICIAN_I TECH ON TECH.dealer_code=A.dealer_code AND TECH.RO_NO=A.RO_NO AND TECH.D_KEY=("
						+ CommonConstants.D_KEY + ")");
		sql.append("	 WHERE A.dealer_code in (select share_entity FROM (" +CommonConstants.VM_ENTITY_SHARE_WITH+ ") vm ");
		sql.append(" where vm.dealer_code ='" + dealerCode + "' and vm.biz_code = 'TT_REPAIR_ORDER') ");
		sql.append(" AND " + Utility.getShareEntityCon(dealerCode, "TT_REPAIR_ORDER", "A"));
		sql.append(" AND A.D_KEY=(" + CommonConstants.D_KEY + ") ");
		
		
		sql.append(Utility.getLikeCond("A", "LICENSE",  queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));
		sql.append(Utility.getLikeCond("A", "OWNER_NAME",  queryParam.get("ownerName"), "AND"));
		sql.append(Utility.getLikeCond("A", "SO_NO",  queryParam.get("soNo"), "AND"));
		setWherer(sql, queryParam, queryList);
		sql.append("ORDER BY A.RO_NO ASC");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 查询维修历史
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWherer(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("roNO"))) {
			sql.append(" AND A.RO_NO like ?");
			queryList.add("%" + queryParam.get("roNO") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			sql.append(" OR A.LICENSE LIKE ? ");
			queryList.add("%" + queryParam.get("license") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("roStatus"))) {
			sql.append(" AND A.RO_STATUS like ? ");
			queryList.add("%" + queryParam.get("roStatus") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisor"))) {
			sql.append(" AND A.SERVICE_ADVISOR like ?");
			queryList.add("%"+queryParam.get("serviceAdvisor")+"%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
			sql.append(" AND A.BRAND like ?");
			queryList.add(queryParam.get("brand"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("status"))&&queryParam.get("status").equals("1")) {
			sql.append(" AND (A.RO_STATUS=("+DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR+") +  OR A.RO_STATUS= ("+DictCodeConstants.DICT_RO_STATUS_TYPE_FOR_BALANCE+")) ");
			queryList.add("%" + queryParam.get("status") + "%");
		}
	}

	/**
	 * 根据车牌号，工单号进行回显查询工单结算明细
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryRoNoByid(String id, String roNo,String salesPartNo) throws ServiceBizException {
		List<Map> list = queryroNoBy(id,roNo,salesPartNo);
		Map map = list.get(0);
		String ownerNo = "";
		roNo = "";
		salesPartNo = "";
		if (map.get("OWNER_NO") != null && !map.get("OWNER_NO").equals("")) {
			ownerNo = map.get("OWNER_NO").toString();
		}
		if (map.get("RO_NO") != null && !map.get("RO_NO").equals("")) {
			roNo = map.get("RO_NO").toString();
		}
		if (map.get("SALES_PART_NO") != null && !map.get("SALES_PART_NO").equals("")) {
			salesPartNo = map.get("SALES_PART_NO").toString();
		}
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT  A.*,ve.*,re.*,ba.*,re.RO_NO,re.REPAIR_TYPE_CODE,re.ENGINE_NO,A.OWNER_NAME FROM (" + CommonConstants.VM_OWNER + ") A "

				+ " LEFT JOIN TM_OWNER_MEMO B ON B.DEALER_CODE = A.DEALER_CODE AND B.OWNER_NO = A.OWNER_NO "
				+ " LEFT JOIN ("+CommonConstants.VM_VEHICLE+") ve ON ve.DEALER_CODE= A.DEALER_CODE AND ve.OWNER_NO=A.OWNER_NO "
				+ " LEFT JOIN TT_REPAIR_ORDER re ON re.VIN=ve.VIN AND re.DEALER_CODE = ve.DEALER_CODE "
				+ " LEFT JOIN TT_BALANCE_ACCOUNTS ba ON ba.DEALER_CODE = re.DEALER_CODE AND ba.RO_NO =re.RO_NO "
				+ " LEFT JOIN TT_SALES_PART sp ON sp.DEALER_CODE = re.DEALER_CODE AND sp.RO_NO = re.RO_NO "
				+ " WHERE A.DEALER_CODE = '" + dealerCode + "'  AND  A.OWNER_NO = '" + ownerNo + "' AND re.RO_NO= '"+roNo+"' AND sp.SALES_PART_NO='"+salesPartNo+"'  ");
		System.out.println(sql.toString());
		List<Map> resultList = Base.findAll(sql.toString());
		return resultList;
	}
	/**
	 * 根据车牌号，工单号进行回显查询工单结算明细
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List<Map> queryroNoBy(String id,String roNo,String salesPartNo) {
		String sb = new String(
				" SELECT ow.*,ve.*,ro.*,sp.* "
						+ " FROM ("+CommonConstants.VM_OWNER+") ow "
						+ " LEFT JOIN ("+CommonConstants.VM_VEHICLE+") ve ON ow.DEALER_CODE=ve.DEALER_CODE AND ow.OWNER_NO=ve.OWNER_NO "
						+ " LEFT JOIN TT_REPAIR_ORDER ro ON ro.DEALER_CODE = ve.DEALER_CODE AND ro.VIN =ve.VIN  "
						+ " LEFT JOIN TT_SALES_PART sp ON sp.DEALER_CODE = ro.DEALER_CODE AND sp.RO_NO = ro.RO_NO "
						+ " WHERE ow.owner_no ='"+id+"' AND ro.RO_NO='"+roNo+"' AND sp.SALES_PART_NO ='"+salesPartNo+"'	AND 1=1	 ");
		System.out.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		queryList.add(id);
		queryList.add(roNo);
		queryList.add(salesPartNo);
		List<Map> result = Base.findAll(sb.toString());
		return result;
	}

	/**
	 * 查询维修项目
	 * 
	 * @author Administrator
	 * @date 2017年4月5日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 * @throws SQLException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.solicitude.solicitudeService#queryMaintainHistory(java.util.Map)
	 */

	@Override
	public PageInfoDto queryMaintainHistory(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				" SELECT  B.REPAIR_TYPE_CODE AS TYPE_CODE , B.IN_MILEAGE, A.*,db.DEALER_SHORTNAME,ve.LICENSE,B.RO_TYPE,B.RO_CREATE_DATE,B.RO_STATUS,tu.`USER_NAME` FROM TT_RO_LABOUR A LEFT JOIN TM_DEALER_BASICINFO db ON A.dealer_code=db.dealer_code"
						+ "  LEFT JOIN TT_REPAIR_ORDER B  ON A.DEALER_CODE = B.DEALER_CODE  AND A.D_KEY = B.D_KEY  AND A.RO_NO = B.RO_NO "
						+ " LEFT JOIN ("+CommonConstants.VM_VEHICLE+") ve ON ve.DEALER_CODE = A.DEALER_CODE  AND ve.license=B.`LICENSE` "
						+ " LEFT JOIN tm_user tu  ON tu.DEALER_CODE=b.DEALER_CODE  "
						+ " WHERE ");
		sql.append(Utility.getShareEntityCon(dealerCode, "TT_REPAIR_ORDER", "A"));
		sql.append(Utility.getLikeCond("A", "LABOUR_CODE", queryParam.get("labourCode"), "AND"));
		sql.append(Utility.getLikeCond("A", "LABOUR_NAME", queryParam.get("labourName"), "AND"));
		sql.append("  AND B.REPAIR_TYPE_CODE NOT IN (SELECT  REPAIR_TYPE_CODE " + " FROM (");
		sql.append(CommonConstants.VM_REPAIR_TYPE + ") vmr  WHERE DEALER_CODE IN  (SELECT SHARE_ENTITY FROM  (");
		sql.append(CommonConstants.VM_ENTITY_SHARE_WITH + ") rr " + " WHERE DEALER_CODE = '" + dealerCode);
		sql.append("'  AND BIZ_CODE = 'UNIFIED_VIEW')  AND IS_PRE_SERVICE = (" + DictCodeConstants.DICT_IS_YES);
		sql.append("  ) ) GROUP BY A.ITEM_ID ");

		setWherew(sql, queryParam, queryList);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * TODO description
	 * 查询维修项目
	 * @author Administrator
	 * @date 2017年4月5日
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */

	private void setWherew(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {
		if (!StringUtils.isNullOrEmpty(queryParam.get("inMileageFrom"))) {
			sql.append(" AND B.IN_MILEAGE >= ?");
			queryList.add(queryParam.get("inMileageFrom"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("inMileageTo"))) {
			sql.append(" AND B.IN_MILEAGE <= ?");
			queryList.add(queryParam.get("inMileageTo"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sql.append(" AND B.VIN like ? ");
			queryList.add("%" + queryParam.get("vin") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))) {
			sql.append(" AND B.REPAIR_TYPE_CODE = ? ");
			queryList.add(queryParam.get("repairTypeCode"));
		}
	}

	/**
	 * 查询维修类型
	 * 
	 * @author Administrator
	 * @date 2017年4月5日
	 * @param queryParam
	 * @return
	 * @throws ServiceBizException
	 *             (non-Javadoc)
	 * @see com.yonyou.dms.customer.service.solicitude.solicitudeService#queryRepairType(java.util.Map)
	 */

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryRepairType(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" SELECT DEALER_CODE,REPAIR_TYPE_CODE FROM tt_repair_order WHERE 1=1  ");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}
	/**
	 * 查询维修配件
	 */
	@Override
	public PageInfoDto queryPart(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT A.*,db.dealer_shortname FROM TT_RO_REPAIR_PART A "
				+ " LEFT JOIN TT_REPAIR_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY AND A.RO_NO = B.RO_NO"
			    + " LEFT JOIN TM_DEALER_BASICINFO db ON a.dealer_code=db.dealer_code   "
				+ " WHERE "
				+  Utility.getShareEntityCon(dealerCode,"TT_REPAIR_ORDER", "A")
				+ " AND B.VIN = '"+queryParam.get("vin")+"'"
				+ " AND A.D_KEY = "+CommonConstants.D_KEY+"  " );
		
//		if (Utility.testString(queryParam.get("repairType"))) {}
			sql.append(" AND B.REPAIR_TYPE_CODE NOT IN ");
			sql.append("(select REPAIR_TYPE_CODE from ("+CommonConstants.VM_REPAIR_TYPE+") e where DEALER_CODE in (select SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") vm ");
			sql.append("where DEALER_CODE='"+dealerCode+"' AND BIZ_CODE = 'UNIFIED_VIEW') AND IS_PRE_SERVICE = ("+DictCodeConstants.IS_YES+") "
					+ "  ) GROUP BY A.PART_NO ");
		
		sql.append(Utility.getLikeCond("A", "PART_NO", queryParam.get("partNo"), "AND"));
		sql.append(Utility.getLikeCond("A", "PART_NAME", queryParam.get("PartName"), "AND"));
				
		setWheree(sql, queryParam, queryList);
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	/**
	 * 查询维修配件
	 * @param sql
	 * @param queryParam
	 * @param queryList
	 */
	private void setWheree(StringBuffer sql, Map<String, String> queryParam, List<Object> queryList) {
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("partNo"))) {
			sql.append(" AND A.PART_NO like ? ");
			queryList.add("%" + queryParam.get("partNo") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
			sql.append(" AND A.PART_NAME like ? ");
			queryList.add("%"+queryParam.get("partName")+"%");
		}
	}


	/**
	 * 查询保险报修
	* @author Administrator
	* @date 2017年4月7日
	* @param queryParam
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.customer.service.solicitude.solicitudeService#queryInsurance(java.util.Map)
	*/
		
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryInsurance(Map<String, String> queryParam,String vin) throws ServiceBizException {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" SELECT * FROM TT_SERVICE_INSURANCE WHERE VIN = '"+vin+"' ");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> resultList = DAOUtil.findAll(stringBuffer.toString(), queryList);
		return resultList;
	}
	
	/**
	 * 查询工单明细维修项目明细
	 */
	@Override
	public PageInfoDto queryMainProject(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM,"
				+ (DictCodeConstants.DICT_IS_NO)
				+ " AS IS_SELECTED,ITEM_ID, l.DEALER_CODE, l.RO_NO, l.CHARGE_PARTITION_CODE, l.TROUBLE_CAUSE, "
				+ "  l.TROUBLE_DESC, l.LOCAL_LABOUR_CODE, l.LOCAL_LABOUR_NAME, l.LABOUR_CODE,"
				+ "  l.LABOUR_PRICE, l.LABOUR_NAME, l.STD_LABOUR_HOUR, l.LABOUR_AMOUNT,C.OEM_LABOUR_HOUR AS OEM_LABOUR_HOUR,C.DOWN_TAG, "
				+ "  l.MANAGE_SORT_CODE, l.TECHNICIAN, l.WORKER_TYPE_CODE, l.REMARK, l.DISCOUNT,l.DISCOUNT AS DISCOUNT_COPY, "		
				+ "  l.INTER_RETURN, l.NEEDLESS_REPAIR, l.PRE_CHECK, l.CONSIGN_EXTERIOR, l.CREATED_AT, "
				+ "  l.ASSIGN_LABOUR_HOUR, l.ASSIGN_TAG, l.ACTIVITY_CODE,A.ACTIVITY_FIRST,l.MODEL_LABOUR_CODE,l.REPAIR_TYPE_CODE,l.PACKAGE_CODE,l.MAINTAIN_PACKAGE_CODE," 
				+ "  l.CLAIM_LABOUR, l.CLAIM_AMOUNT ,l.REASON ,l.STD_LABOUR_HOUR_SAVE, " 
				+ "  te.EMPLOYEE_NAME as TECHNICIAN_NAME, "		
				+ "  l.CARD_ID,m.CARD_CODE,l.SPRAY_AREA,RT.IS_INSURANCE,l.IS_TRIPLE_GUARANTEE,NO_WARRANTY_CONDITION," 
				+ "  l.POS_CODE,l.POS_NAME,l.APP_CODE,l.APP_NAME,l.WAR_LEVEL,l.INFIX_CODE,l.LABOUR_TYPE" 
				+ " FROM TT_RO_LABOUR l left join TM_MEMBER_CARD m on l.DEALER_CODE=m.DEALER_CODE and l.CARD_ID=m.CARD_ID "
				+ " LEFT JOIN TM_EMPLOYEE TE on te.DEALER_CODE = l.DEALER_CODE  and l.TECHNICIAN = te.EMPLOYEE_NO  "   
				+ " LEFT JOIN ("+CommonConstants.VM_REPAIR_ITEM+") C ON l.DEALER_CODE=C.DEALER_CODE AND l.LABOUR_CODE=C.LABOUR_CODE AND l.MODEL_LABOUR_CODE=C.MODEL_LABOUR_CODE "
				+ " LEFT JOIN TT_ACTIVITY A ON A.DEALER_CODE = l.DEALER_CODE AND A.ACTIVITY_CODE = l.ACTIVITY_CODE"
				+ " LEFT JOIN TM_REPAIR_TYPE RT ON l.DEALER_CODE = RT.DEALER_CODE AND l.REPAIR_TYPE_CODE = RT.REPAIR_TYPE_CODE"
				+ " WHERE "
//				+ " l.RO_NO='"+roNo+"' AND "
				+ " l.DEALER_CODE='"+dealerCode+"' AND l.D_KEY=" +CommonConstants.D_KEY); 
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	
	}
	/**
	 * 查询工单明细维修材料明细
	 */
	@Override
	public PageInfoDto queryMaintainMaterial(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM,A.FROM_TYPE,A.MAINTAIN_PACKAGE_CODE,"
				+ (DictCodeConstants.DICT_IS_NO)
				+ " AS IS_SELECTED,A.IS_DISCOUNT,A.INTER_RETURN,A.DEALER_CODE, A.RO_NO,A.ITEM_ID, A.STORAGE_CODE, A.IS_MAIN_PART, "
				+ " A.STORAGE_POSITION_CODE, A.PART_NO, A.PART_NAME, A.CREATED_AT, "
				+ " A.PART_QUANTITY, A.UNIT_CODE, A.PRICE_TYPE, A.PRICE_RATE, A.PART_COST_PRICE, A.PART_SALES_PRICE, "
				+ " A.PART_COST_AMOUNT, A.PART_SALES_AMOUNT, A.SENDER, A.RECEIVER, A.SEND_TIME, A.CHARGE_PARTITION_CODE, "
				+ " A.IS_FINISHED, A.BATCH_NO, A.OEM_LIMIT_PRICE, A.OUT_STOCK_NO,A.DISCOUNT,A.DISCOUNT AS DISCOUNT_COPY,A.NEEDLESS_REPAIR,A.PRE_CHECK, "
				+ " A.CONSIGN_EXTERIOR,A.ACTIVITY_CODE,l.ACTIVITY_FIRST,A.PART_BATCH_NO,A.MANAGE_SORT_CODE ,A.PRINT_RP_TIME,A.PRINT_BATCH_NO,A.REPAIR_TYPE_CODE, "
				+ " B.SALES_PRICE,B.CLAIM_PRICE,B.LATEST_PRICE,B.COST_PRICE,A.LABOUR_CODE,A.MODEL_LABOUR_CODE,A.PACKAGE_CODE, "
				+ " B.INSURANCE_PRICE,C.NODE_PRICE, A.REASON,A.CARD_ID,m.CARD_CODE,A.LABOUR_NAME,C.DOWN_TAG,RT.IS_INSURANCE, " 
				+ " A.POS_CODE,A.POS_NAME,A.PART_INFIX,A.WAR_LEVEL,A.PART_CATEGORY,C.PART_TYPE " 
				+ " FROM TT_RO_REPAIR_PART A "
				+ " LEFT JOIN TM_MEMBER_CARD m on A.DEALER_CODE=m.DEALER_CODE and A.CARD_ID=m.CARD_ID"
				+ " LEFT JOIN TM_PART_STOCK B ON A.DEALER_CODE = B.DEALER_CODE "
				+ " AND A.D_KEY = B.D_KEY AND A.STORAGE_CODE = B.STORAGE_CODE "
				+ " AND A.PART_NO = B.PART_NO  "
				+ " LEFT JOIN ("+CommonConstants.VM_PART_INFO+") C on  C.DEALER_CODE = A.DEALER_CODE "
				+ " AND C.D_KEY = A.D_KEY  "
				+ " AND C.PART_NO = A.PART_NO  "
				+ " LEFT JOIN TT_ACTIVITY l ON A.DEALER_CODE = l.DEALER_CODE AND A.ACTIVITY_CODE = l.ACTIVITY_CODE"
				+ " LEFT JOIN TT_RO_LABOUR RL ON A.DEALER_CODE = RL.DEALER_CODE AND A.RO_NO = RL.RO_NO AND A.ITEM_ID_LABOUR= RL.ITEM_ID"
				+ " LEFT JOIN TM_REPAIR_TYPE RT ON RL.DEALER_CODE = RT.DEALER_CODE AND RL.REPAIR_TYPE_CODE = RT.REPAIR_TYPE_CODE"
				+ " WHERE A.D_KEY = "+ CommonConstants.D_KEY
				+ " AND A.RO_NO='"+queryParam.get("roNo")+"'  AND A.DEALER_CODE ='"+dealerCode+"' "); 
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	/**
	 * 查询工单明细销售材料明细
	 */
	@Override
	public PageInfoDto querySellMaterial(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM," 
				+ (DictCodeConstants.DICT_IS_NO)
				+ " AS IS_SELECTED,	A.DISCOUNT AS DISCOUNT_COPY,A.ITEM_ID,A.DEALER_CODE,A.SALES_PART_NO,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_NO,A.PART_BATCH_NO,A.PART_NAME,A.PART_QUANTITY,A.BATCH_NO,A.DISCOUNT"
				+ "	,A.PRICE_TYPE,A.PRICE_RATE,A.OEM_LIMIT_PRICE,A.CHARGE_PARTITION_CODE,A.MANAGE_SORT_CODE,A.UNIT_CODE,A.PART_COST_PRICE,A.PART_SALES_PRICE,A.PART_COST_AMOUNT"
				+ "	,A.IS_FINISHED,A.FINISHED_DATE,A.SENDER,A.RECEIVER,A.SEND_TIME,A.D_KEY,A.UPDATED_AT,A.UPDATED_BY,A.CREATED_AT,A.CREATED_BY,A.VER,A.IS_DISCOUNT,A.DXP_DATE"
				+ "	,A.OTHER_PART_COST_PRICE,A.OTHER_PART_COST_AMOUNT,A.SALES_AMOUNT PART_SALES_AMOUNT,A.SALES_DISCOUNT,A.OLD_SALES_PART_NO,C.DOWN_TAG "
				+ " FROM TT_SALES_PART_ITEM A LEFT JOIN TT_SALES_PART B ON  "
				+ " A.SALES_PART_NO=B.SALES_PART_NO LEFT JOIN TM_PART_INFO C ON A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO "
				+ " where A.DEALER_CODE='"+dealerCode+"'  AND B.RO_NO='"+queryParam.get("roNo")+"' AND B.DEALER_CODE='"+dealerCode+"'"
				+ " AND A.D_KEY= " +CommonConstants.D_KEY
				+ " AND B.D_KEY=" +CommonConstants.D_KEY
				+ " ORDER BY A.ITEM_ID"); 
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 查询工单附加项目明细
	 */
	@Override
	public PageInfoDto queryAccessoryProject(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM," + (DictCodeConstants.DICT_IS_NO) + " AS IS_SELECTED, "
					+ "T1.DEALER_CODE, T1.RO_NO, T1.ITEM_ID, T1.ADD_ITEM_CODE, T1.ADD_ITEM_NAME, T1.ADD_ITEM_AMOUNT,T1.ACTIVITY_CODE,A.ACTIVITY_FIRST, "
					+ "T1.CHARGE_PARTITION_CODE,T1.REMARK,T1.DISCOUNT,T1.DISCOUNT AS DISCOUNT_COPY,T1.MANAGE_SORT_CODE,T1.CREATED_AT,T2.IS_DOWN "
					+ " FROM TT_RO_ADD_ITEM T1 LEFT JOIN TM_BALANCE_MODE_ADD_ITEM T2 ON T1.DEALER_CODE=T2.DEALER_CODE AND T1.ADD_ITEM_CODE=T2.ADD_ITEM_CODE "
					+ " LEFT JOIN TT_ACTIVITY A ON A.DEALER_CODE = T1.DEALER_CODE AND A.ACTIVITY_CODE = T1.ACTIVITY_CODE"
					+ " WHERE T1.RO_NO='"+queryParam.get("roNo")+"' AND T1.DEALER_CODE ='"+dealerCode+"' AND T1.D_KEY=" +CommonConstants.D_KEY ); 
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 查询工单辅料费明细
	 */
	@Override
	public PageInfoDto queryAccessoryManage(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM, "
				+ (DictCodeConstants.DICT_IS_NO)
				+ " AS IS_SELECTED,ITEM_ID, DEALER_CODE, RO_NO, MANAGE_SORT_CODE, OVER_ITEM_AMOUNT, " +
				"    LABOUR_RATE, REPAIR_PART_RATE, SALES_PART_RATE, ADD_ITEM_RATE, " +
				"    LABOUR_AMOUNT_RATE, OVERHEAD_EXPENSES_RATE, IS_MANAGING, DISCOUNT " +
				"  FROM TT_RO_MANAGE WHERE IS_MANAGING="+(DictCodeConstants.DICT_IS_NO)+" "
				+ "  AND RO_NO='"+queryParam.get("roNo")+"' AND DEALER_CODE='"+dealerCode+"'  AND D_KEY="+CommonConstants.D_KEY  ); 
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 查询工单管理费明细
	 */
	@Override
	public PageInfoDto queryManage(Map<String, String> queryParam,String roNo) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM, "
				+ (DictCodeConstants.DICT_IS_NO)
				+ " AS IS_SELECTED,ITEM_ID, DEALER_CODE, RO_NO, MANAGE_SORT_CODE, OVER_ITEM_AMOUNT, " +
				"    LABOUR_RATE, REPAIR_PART_RATE, SALES_PART_RATE, ADD_ITEM_RATE, " +
				"    LABOUR_AMOUNT_RATE, OVERHEAD_EXPENSES_RATE, IS_MANAGING, DISCOUNT " +
				"  FROM TT_RO_MANAGE WHERE IS_MANAGING="+(DictCodeConstants.DICT_IS_YES)+" "
				+ "  AND RO_NO='"+queryParam.get("roNo")+"' AND DEALER_CODE='"+dealerCode+"'  AND D_KEY="+CommonConstants.D_KEY  ); 
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 查询工单派工情况
	 */
	@Override
	public PageInfoDto queryAssign(Map<String, String> queryParam, String roNo)
			throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM,"
                + (DictCodeConstants.DICT_IS_NO)
                + " AS IS_SELECTED,ra.DEALER_CODE, ra.ITEM_ID,ra.RO_NO, ra.ASSIGN_ID, ra.TECHNICIAN, ra.ASSIGN_LABOUR_HOUR, ra.LABOUR_HOUR_CONVERT"
                + "  , ra.ASSIGN_TIME, ra.LABOUR_POSITION_CODE, ra.ITEM_START_TIME, ra.ITEM_END_TIME, "
                + "  ra.ESTIMATE_END_TIME, ra.FINISHED_TAG, ra.CHECKER, ra.LABOUR_FACTOR, ra.WORKER_TYPE_CODE"
                + "  FROM TT_RO_ASSIGN ra "
                + "  LEFT JOIN TT_RO_LABOUR l ON l.`DEALER_CODE`=ra.`DEALER_CODE` AND l.`RO_NO`=ra.`RO_NO` "
                + " WHERE  ra.RO_NO='"+roNo+"' AND ra.DEALER_CODE='"+dealerCode+"' AND ra.D_KEY=" +CommonConstants.D_KEY );
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 根据发动机号、车牌号、车主地址查询建议维修项目
	 */
	@Override
	public PageInfoDto QueryRepairProject(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT C.ADDRESS,B.LICENSE , A.RO_NO,A.IS_VALID,A.SUGGEST_MAINTAIN_LABOUR_ID,A.LABOUR_CODE,"
				+ " A.LABOUR_NAME,A.STD_LABOUR_HOUR,A.LABOUR_AMOUNT,A.REASON,A.SUGGEST_DATE,B.SERVICE_ADVISOR,A.VIN,B.ENGINE_NO,A.DEALER_CODE,"
				+ " EM.EMPLOYEE_NAME as SERVICE_ADVISOR_NAME  FROM TT_SUGGEST_MAINTAIN_LABOUR A "
				+ " LEFT JOIN TT_REPAIR_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.RO_NO  = B.RO_NO  AND B.D_KEY = " +CommonConstants.D_KEY 
				+ " LEFT JOIN ("+CommonConstants.VM_OWNER+")  C ON A.DEALER_CODE = C.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO "
				+ " LEFT JOIN TM_EMPLOYEE EM ON EM.DEALER_CODE = B.DEALER_CODE  AND EM.EMPLOYEE_NO=B.SERVICE_ADVISOR  "
				+ " WHERE A.DEALER_CODE in ( select SHARE_ENTITY FROM ("+CommonConstants.VM_ENTITY_SHARE_WITH+") vm  where DEALER_CODE ='"+dealerCode+"' AND biz_code ='TT_SUGGEST_MAINTAIN' )"
				+ " AND A.D_KEY = "+CommonConstants.D_KEY  );
		sql.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("C", "ADDRESS", queryParam.get("address"), "AND"));
		sql.append(Utility.getLikeCond("B", "ENGINE_NO", queryParam.get("engineNo"), "AND"));
		if (!StringUtils.isNull(queryParam.get("serviceAdvisor"))) {
			sql.append(" AND B.SERVICE_ADVISOR = '"+queryParam.get("serviceAdvisor")+"'");
		}
		if (!StringUtils.isNull(queryParam.get("ownerNo"))) {
			sql.append(" AND B.OWNER_NO = '"+queryParam.get("ownerNo")+"'");
		}
		sql.append(Utility.getDateCond("A", "SUGGEST_DATE", queryParam.get("suggestBeginDate"), queryParam.get("suggestEndDate")));
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}
	
	/**
	 * 根据发动机号、车牌号、车主地址查询建议维修配件
	 */
	@Override
	public PageInfoDto QueryRepairPart(Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		List<Object> queryList = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(" SELECT C.ADDRESS ,A.RO_NO,A.IS_VALID,A.SUGGEST_MAINTAIN_PART_ID,A.PART_NO,A.PART_NAME,A.SUGGEST_DATE,A.SALES_PRICE,"
				+ " A.QUANTITY,A.REASON,B.SERVICE_ADVISOR,A.VIN,B.LICENSE,B.ENGINE_NO ,A.DEALER_CODE ,EM.EMPLOYEE_NAME as SERVICE_ADVISOR_NAME  "
				+ " FROM TT_SUGGEST_MAINTAIN_PART A LEFT JOIN TT_REPAIR_ORDER B ON A.DEALER_CODE = B.DEALER_CODE AND A.RO_NO = B.RO_NO AND B.D_KEY = " +CommonConstants.D_KEY
				+ " LEFT JOIN ("+CommonConstants.VM_OWNER+") C ON A.DEALER_CODE = C.DEALER_CODE AND B.OWNER_NO = C.OWNER_NO "
				+ " LEFT JOIN TM_EMPLOYEE EM ON EM.DEALER_CODE = B.DEALER_CODE  AND EM.EMPLOYEE_NO=B.SERVICE_ADVISOR "
				+ " WHERE A.DEALER_CODE in ( select SHARE_ENTITY FROM ("+CommonConstants.VM_ENTITY_SHARE_WITH+") vm where DEALER_CODE ='" + dealerCode + "' and biz_code ='TT_SUGGEST_MAINTAIN' )"
				+ " AND A.D_KEY = "+CommonConstants.D_KEY );
		sql.append(Utility.getLikeCond("B", "LICENSE", queryParam.get("license"), "AND"));
		sql.append(Utility.getLikeCond("C", "ADDRESS", queryParam.get("address"), "AND"));
		sql.append(Utility.getLikeCond("B", "ENGINE_NO", queryParam.get("engineNo"), "AND"));
		
		if (!StringUtils.isNull(queryParam.get("serviceAdvisor"))) {
			sql.append(" AND B.SERVICE_ADVISOR = '"+queryParam.get("serviceAdvisor")+"'");
		}
		if (!StringUtils.isNull(queryParam.get("ownerNo"))) {
			sql.append(" AND B.OWNER_NO = '"+queryParam.get("ownerNo")+"'");
		}

		sql.append(Utility.getLikeCond("A", "VIN", queryParam.get("vin"), "AND"));
		sql.append(Utility.getDateCond("A", "SUGGEST_DATE", queryParam.get("suggestBeginDate"), queryParam.get("suggestEndDate")));
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryList);
		return pageInfoDto;
	}

	/**
	 * 查询客户投诉
	 */
	@Override
	public PageInfoDto queryComplaint(Map<String, String> queryParam)
			throws ServiceBizException, SQLException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append(" SELECT b.TECHNICIAN AS TECHNICIAN_NEW,A.VIN,A.COMPLAINT_NO,A.COMPLAINT_NAME,A.COMPLAINT_PHONE,A.COMPLAINT_TYPE,A.COMPLAINT_DATE,A.SERVICE_ADVISOR, ");
		stringBuffer.append(" A.COMPLAINT_MOBILE,A.COMPLAINT_GENDER,A.COMPLAINT_MAIN_TYPE,A.COMPLAINT_SUB_TYPE,A.CRC_COMPLAINT_NO,A.IS_GCR,A.CRC_COMPLAINT_SOURCE,A.TECHNICIAN, ");
		stringBuffer.append(" A.DEAL_STATUS,  A.COMPLAINT_END_DATE,A.DEPARTMENT,A.BE_COMPLAINT_EMP,A.CLOSE_DATE,A.COMPLAINT_RESULT,A.COMPLAINT_ORIGIN,A.IS_INTIME_DEAL,A.COMPLAINT_SUMMARY, ");
		stringBuffer.append(" A.COMPLAINT_REASON,A.CONSULTANT,A.OWNER_NAME,A.LICENSE,A.RESOLVENT,A.CLOSE_STATUS,IS_UPLOAD,A.DEALER_CODE,db.DEALER_SHORTNAME FROM TT_CUSTOMER_COMPLAINT a left join TT_TECHNICIAN_I b on a.DEALER_CODE=b.DEALER_CODE and a.ro_no=b.ro_no  ");
		stringBuffer.append(" LEFT JOIN TM_DEALER_BASICINFO db ON a.dealer_code=db.dealer_code   ");
		stringBuffer.append(" WHERE a.D_KEY = ("+ DictCodeConstants.D_KEY +") AND a.IS_VALID= ("+ DictCodeConstants.DICT_IS_YES +") AND A.DEALER_CODE in( ");
		stringBuffer.append(" select SHARE_ENTITY from ("+ CommonConstants.VM_ENTITY_SHARE_WITH +") h where h.DEALER_CODE = ?  ");
		queryList.add(FrameworkUtil.getLoginInfo().getDealerCode());
		stringBuffer.append(" and h.biz_code = 'TT_CUSTOMER_COMPLAINT' ) ");
		stringBuffer.append(Utility.getLikeCond("A", "OWNER_NAME", queryParam.get("OWNER_NAME"), "AND"));
		stringBuffer.append(Utility.getLikeCond("A", "LICENSE", queryParam.get("LICENSE"), "AND"));
		
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			stringBuffer.append(" AND A.VIN = ? ");
			queryList.add(queryParam.get("vin"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
			stringBuffer.append(" AND A.LICENSE = ? ");
			queryList.add(queryParam.get("license"));
		}
		System.out.print(stringBuffer.toString());
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(stringBuffer.toString(), queryList);
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
		sql.append(" SELECT A.* ,C.EMPLOYEE_NAME from TT_CUSTOMER_COMPLAINT A "
				+ " LEFT JOIN TM_USER B ON A.DEALER_CODE = B.DEALER_CODE "
				+ " AND (CASE WHEN A.CREATED_BY IS NULL THEN A.UPDATED_BY  ELSE A.CREATED_BY END)= B.USER_ID "
				+ " LEFT JOIN TM_EMPLOYEE C ON A.DEALER_CODE = C.DEALER_CODE AND B.EMPLOYEE_NO = C.EMPLOYEE_NO"
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
	public PageInfoDto queryDispose(Map<String, String> queryParam) throws ServiceBizException, SQLException {
		StringBuffer stringBuffer = new StringBuffer();
		List<Object> queryList = new ArrayList<Object>();
		stringBuffer.append("SELECT ccd.* FROM TT_CUSTOMER_COMPLAINT_DETAIL ccd LEFT JOIN TT_CUSTOMER_COMPLAINT cc"
				+ " ON ccd.DEALER_CODE = cc.DEALER_CODE  AND ccd.`COMPLAINT_NO` = cc.`COMPLAINT_NO` "
				+ " WHERE ccd.`COMPLAINT_NO` = '"+queryParam.get("complanintNo")+"'  AND 1 = 1 ");
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(stringBuffer.toString(), queryList);
		return pageInfoDto;
	}
}
