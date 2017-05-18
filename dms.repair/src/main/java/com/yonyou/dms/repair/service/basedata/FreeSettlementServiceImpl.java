package com.yonyou.dms.repair.service.basedata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.mapper.Mapper.Null;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmGiftCertificateItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPayobjAmountStatisticsPO;
import com.yonyou.dms.common.domains.PO.basedata.TmRepairTypePO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleSubclassPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAccountsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAddItemPayobjPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceLabourPayobjPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceOtherCostPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceRepairPartPayobjPO;
import com.yonyou.dms.common.domains.PO.basedata.TtMemberLabourFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoAssignPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSuggestMaintainLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSuggestMaintainPartPO;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VehiclePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.BalanceDTO;
import com.yonyou.dms.repair.domains.PO.balance.BalancePayobjPO;

@Service
@SuppressWarnings({ "rawtypes", "unused" })
public class FreeSettlementServiceImpl implements FreeSettlementService {

	private static final Logger logger = LoggerFactory.getLogger(FreeSettlementServiceImpl.class);

	@Autowired
	private CommonNoService commonNoService;

	@Override
	public PageInfoDto searchRepairOrder(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append(
				"select '12781001' AS COLOR_FLAG, A.VIN,B.CONSULTANT,A.DEALER_CODE, A.RO_NO, A.SALES_PART_NO, A.BOOKING_ORDER_NO, A.ESTIMATE_NO,"
						+ "   A.RO_TYPE, A.REPAIR_TYPE_CODE, A.OTHER_REPAIR_TYPE, A.VEHICLE_TOP_DESC, "
						+ "    A.SEQUENCE_NO, A.PRIMARY_RO_NO, A.INSURATION_NO, A.INSURATION_CODE,"
						+ "    A.IS_CUSTOMER_IN_ASC, A.IS_SEASON_CHECK, A.OIL_REMAIN, A.IS_WASH, A.IS_TRACE,"
						+ "   A.TRACE_TIME, A.NO_TRACE_REASON, A.NEED_ROAD_TEST, A.RECOMMEND_EMP_NAME,"
						+ "     A.RECOMMEND_CUSTOMER_NAME, A.SERVICE_ADVISOR, A.SERVICE_ADVISOR_ASS,te.EMPLOYEE_NAME,A.RO_STATUS,"
						+ "    A.RO_CREATE_DATE, A.END_TIME_SUPPOSED, A.CHIEF_TECHNICIAN, A.OWNER_NO, A.OWNER_NAME,"
						+ "    A.OWNER_PROPERTY, A.LICENSE,  A.ENGINE_NO, A.BRAND, A.SERIES, A.MODEL, A.IN_MILEAGE,"
						+ "    A.OUT_MILEAGE, A.IS_CHANGE_ODOGRAPH, A.CHANGE_MILEAGE, A.TOTAL_CHANGE_MILEAGE,"
						+ "    A.TOTAL_MILEAGE, A.DELIVERER, A.DELIVERER_GENDER, A.DELIVERER_PHONE,"
						+ "    A.DELIVERER_MOBILE, A.FINISH_USER, A.COMPLETE_TAG, A.WAIT_INFO_TAG, A.WAIT_PART_TAG,"
						+ "    A.COMPLETE_TIME, A.FOR_BALANCE_TIME, A.DELIVERY_TAG, A.DELIVERY_DATE, A.LABOUR_PRICE"
						+ "    , A.LABOUR_AMOUNT, A.REPAIR_PART_AMOUNT, A.SALES_PART_AMOUNT, A.ADD_ITEM_AMOUNT,"
						+ "    A.OVER_ITEM_AMOUNT, A.REPAIR_AMOUNT, A.ESTIMATE_AMOUNT, A.BALANCE_AMOUNT,"
						+ "    A.RECEIVE_AMOUNT, A.SUB_OBB_AMOUNT, A.DERATE_AMOUNT, A.TRACE_TAG, A.REMARK,"
						+ "    A.TEST_DRIVER, A.PRINT_RO_TIME, A.RO_CHARGE_TYPE, A.PRINT_RP_TIME, A.IS_ACTIVITY,"
						+ "    A.CUSTOMER_DESC, A.LOCK_USER, A.IS_CLOSE_RO, A.RO_SPLIT_STATUS,A.SO_NO,A.VER,A.IS_MAINTAIN,"
						+ "    A.IS_LARGESS_MAINTAIN,C.SALES_DATE,A.IS_QS, A.SCHEME_STATUS ,US.USER_NAME, "
						+ "    A.REMARK1, A.RO_TROUBLE_DESC"
						+ " FROM TT_REPAIR_ORDER A LEFT JOIN TT_SALES_PART B ON A.DEALER_CODE=B.DEALER_CODE AND A.RO_NO=B.RO_NO "
						+ " LEFT JOIN tm_employee te ON te.DEALER_CODE = A.DEALER_CODE AND A.SERVICE_ADVISOR_ASS = te.EMPLOYEE_NO "
						+ " LEFT JOIN TM_VEHICLE C ON A.DEALER_CODE = C.DEALER_CODE AND A.VIN = C.VIN "
						+ " LEFT JOIN TM_USER US ON A.LOCK_USER = US.USER_ID AND A.DEALER_CODE = US.DEALER_CODE WHERE A.DEALER_CODE ='"
						+ dealerCode + "' " + " AND A.D_KEY=" + CommonConstants.D_KEY + " ");
		String roNo = queryParam.get("roNo");
		String license = queryParam.get("license");
		String vin = queryParam.get("vin");
		String ownerName = queryParam.get("ownerName");

		sql.append(Utility.getLikeCond("A", "LICENSE", license, "AND"));
		sql.append(Utility.getLikeCond("A", "VIN", vin, "AND"));
		if (roNo != null && !"".equals(roNo.trim())) {
			sql.append(" AND ( A.RO_NO LIKE '%" + roNo + "%'   or  A.LICENSE LIKE '%" + roNo + "%' ) ");
		}
		sql.append(Utility.getLikeCond("A", "OWNER_NAME", ownerName, "AND"));

		System.err.println(sql.toString());
		List<Object> params = new ArrayList<Object>();
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	@Override
	public List<Map> queryRoLabourByRoNO(String id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder();
		sb.append("select 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM,"
				+ CommonConstants.DICT_IS_NO
				+ " AS IS_SELECTED,ITEM_ID, l.DEALER_CODE, l.RO_NO, l.CHARGE_PARTITION_CODE, l.TROUBLE_CAUSE, "
				+ "    l.TROUBLE_DESC, l.LOCAL_LABOUR_CODE, l.LOCAL_LABOUR_NAME, l.LABOUR_CODE,"
				+ "    l.LABOUR_PRICE, l.LABOUR_NAME, l.STD_LABOUR_HOUR, l.LABOUR_AMOUNT,l.LABOUR_AMOUNT AS CALC_REAL_RECEIVE_AMOUNT,C.OEM_LABOUR_HOUR AS OEM_LABOUR_HOUR,C.DOWN_TAG, "
				+ "    l.MANAGE_SORT_CODE, l.TECHNICIAN, l.WORKER_TYPE_CODE, l.REMARK, l.DISCOUNT,l.DISCOUNT AS DISCOUNT_COPY, "
				+ "    l.INTER_RETURN, l.NEEDLESS_REPAIR, l.PRE_CHECK, l.CONSIGN_EXTERIOR, l.CREATED_AT, "
				+ "    l.ASSIGN_LABOUR_HOUR, l.ASSIGN_TAG, l.ACTIVITY_CODE,A.ACTIVITY_FIRST,l.MODEL_LABOUR_CODE,l.REPAIR_TYPE_CODE,l.PACKAGE_CODE,l.MAINTAIN_PACKAGE_CODE,"
				+ " 	l.CLAIM_LABOUR, l.CLAIM_AMOUNT ,l.REASON" + "    ,l.STD_LABOUR_HOUR_SAVE "
				+ " ,te.EMPLOYEE_NAME as TECHNICIAN_NAME "
				+ ",l.CARD_ID,m.CARD_CODE,l.SPRAY_AREA,RT.IS_INSURANCE,l.IS_TRIPLE_GUARANTEE,NO_WARRANTY_CONDITION,"
				+ " l.POS_CODE,l.POS_NAME,l.APP_CODE,l.APP_NAME,l.WAR_LEVEL,l.INFIX_CODE,l.LABOUR_TYPE"
				+ " from TT_RO_LABOUR l left join TM_MEMBER_CARD m on l.DEALER_CODE=m.DEALER_CODE and l.CARD_ID=m.CARD_ID "
				+ " LEFT JOIN TM_EMPLOYEE TE on te.DEALER_CODE = l.DEALER_CODE  and l.TECHNICIAN = te.EMPLOYEE_NO  "
				+ " LEFT JOIN (" + CommonConstants.VM_REPAIR_ITEM
				+ ") C ON l.DEALER_CODE=C.DEALER_CODE AND l.LABOUR_CODE=C.LABOUR_CODE AND l.MODEL_LABOUR_CODE=C.MODEL_LABOUR_CODE "
				+ " LEFT JOIN TT_ACTIVITY A ON A.DEALER_CODE = l.DEALER_CODE AND A.ACTIVITY_CODE = l.ACTIVITY_CODE"
				+ " LEFT JOIN TM_REPAIR_TYPE RT ON l.DEALER_CODE = RT.DEALER_CODE AND l.REPAIR_TYPE_CODE = RT.REPAIR_TYPE_CODE"
				+ " WHERE " + "l.RO_NO='" + id + "' AND l.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode()
				+ "' AND l.D_KEY="
				// add by Bill Tang 2010-10-27 end
				+ CommonConstants.D_KEY + "");

		System.err.println("tab1: " + sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		return list;
	}

	@Override
	public List<Map> queryRoRepairPartByRoNO(String id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM,A.FROM_TYPE,A.MAINTAIN_PACKAGE_CODE,"
						+ CommonConstants.DICT_IS_NO
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
						+ "LEFT JOIN TM_MEMBER_CARD m on A.DEALER_CODE=m.DEALER_CODE and A.CARD_ID=m.CARD_ID"
						+ " LEFT JOIN TM_PART_STOCK B ON A.DEALER_CODE = B.DEALER_CODE "
						+ " AND A.D_KEY = B.D_KEY AND A.STORAGE_CODE = B.STORAGE_CODE " + " AND A.PART_NO = B.PART_NO  "
						+ "  left join (" + CommonConstants.VM_PART_INFO + ") C on  C.DEALER_CODE = A.DEALER_CODE "
						+ " AND C.D_KEY = A.D_KEY  " + " AND C.PART_NO = A.PART_NO  "
						+ " LEFT JOIN TT_ACTIVITY l ON A.DEALER_CODE = l.DEALER_CODE AND A.ACTIVITY_CODE = l.ACTIVITY_CODE"
						+ " LEFT JOIN TT_RO_LABOUR RL ON A.DEALER_CODE = RL.DEALER_CODE AND A.RO_NO = RL.RO_NO AND A.ITEM_ID_LABOUR= RL.ITEM_ID"
						+ " LEFT JOIN TM_REPAIR_TYPE RT ON RL.DEALER_CODE = RT.DEALER_CODE AND RL.REPAIR_TYPE_CODE = RT.REPAIR_TYPE_CODE"
						+ " WHERE A.D_KEY = " + CommonConstants.D_KEY + " AND A.RO_NO='" + id
						+ "'  AND A.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
		System.err.println("tab2: " + sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		return list;
	}

	@Override
	public List<Map> queryRoAddItemByRoNO(String id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT 1 SEL_FLAG,1 IS_DXP,'' SOURCE_NO,'' DXP_UPDATE_STATUS,'' SOURCE_ITEM,"
				+ CommonConstants.DICT_IS_NO + " AS IS_SELECTED, "
				+ "T1.DEALER_CODE, T1.RO_NO, T1.ITEM_ID, T1.ADD_ITEM_CODE, T1.ADD_ITEM_NAME, T1.ADD_ITEM_AMOUNT,T1.ACTIVITY_CODE,A.ACTIVITY_FIRST, "
				+ "T1.CHARGE_PARTITION_CODE,T1.REMARK,T1.DISCOUNT,T1.DISCOUNT AS DISCOUNT_COPY,T1.MANAGE_SORT_CODE,T1.CREATED_AT,T2.IS_DOWN "
				+ " FROM TT_RO_ADD_ITEM T1 LEFT JOIN TM_BALANCE_MODE_ADD_ITEM T2 ON T1.DEALER_CODE=T2.DEALER_CODE AND T1.ADD_ITEM_CODE=T2.ADD_ITEM_CODE "
				+ " LEFT JOIN TT_ACTIVITY A ON A.DEALER_CODE = T1.DEALER_CODE AND A.ACTIVITY_CODE = T1.ACTIVITY_CODE"
				+ " WHERE T1.RO_NO='" + id + "' AND T1.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode()
				+ "' AND T1.D_KEY=" + CommonConstants.D_KEY + " ");
		System.err.println("tab4: " + sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		return list;
	}

	@Override
	public PageInfoDto queryForSettlement(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("SELECT A.*  FROM TT_SALES_PART A LEFT JOIN TT_REPAIR_ORDER C"
				+ " ON A.DEALER_CODE=C.DEALER_CODE AND A.RO_NO=C.RO_NO AND " + "C.RO_STATUS = '"
				+ CommonConstants.DICT_RO_STATUS_TYPE_FOR_BALANCE + "' AND" + " C.D_KEY = '" + CommonConstants.D_KEY
				+ "' WHERE A.D_KEY ='" + CommonConstants.D_KEY + "' AND" + " (A.BALANCE_STATUS = '"
				+ CommonConstants.DICT_IS_NO + "' OR " + "A.BALANCE_STATUS IS NULL) AND A.DEALER_CODE='" + dealerCode
				+ "' AND" + " NOT EXISTS (SELECT ITEM_ID FROM TT_SALES_PART_ITEM "
				+ "B WHERE A.SALES_PART_NO = B.SALES_PART_NO AND A.DEALER_CODE ="
				+ " B.DEALER_CODE AND B.IS_FINISHED = '" + CommonConstants.DICT_IS_NO + "' AND "
				+ "EXISTS (SELECT ITEM_ID FROM TT_SALES_PART_ITEM B WHERE A.SALES_PART_NO "
				+ "= B.SALES_PART_NO AND A.DEALER_CODE = B.DEALER_CODE AND " + "B.IS_FINISHED = '"
				+ CommonConstants.DICT_IS_YES + "' )) ");

		System.err.println(sql.toString());
		List<Object> params = new ArrayList<Object>();
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	@Override
	public List<Map> querySalesPartList(String id) throws ServiceBizException {
		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT " + CommonConstants.DICT_IS_NO + " AS IS_SELECTED,A.DISCOUNT AS DISCOUNT_COPY,"
				+ " A.ITEM_ID,A.DEALER_CODE,A.SALES_PART_NO,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.PART_NO,A.PART_BATCH_NO,A.PART_NAME,A.PART_QUANTITY,A.BATCH_NO,A.DISCOUNT"
				+ "	,A.PRICE_TYPE,A.PRICE_RATE,A.OEM_LIMIT_PRICE,A.CHARGE_PARTITION_CODE,A.MANAGE_SORT_CODE,A.UNIT_CODE,A.PART_COST_PRICE,A.PART_SALES_PRICE,A.PART_COST_AMOUNT"
				+ "	,A.IS_FINISHED,A.FINISHED_DATE,A.SENDER,A.RECEIVER,A.SEND_TIME,A.D_KEY,A.UPDATED_AT,A.UPDATED_BY,A.CREATED_AT,A.CREATED_BY,A.VER,A.IS_DISCOUNT,A.DXP_DATE"
				+ "	,A.OTHER_PART_COST_PRICE,A.OTHER_PART_COST_AMOUNT,A.SALES_AMOUNT PART_SALES_AMOUNT,A.SALES_DISCOUNT,A.OLD_SALES_PART_NO FROM TT_SALES_PART_ITEM A WHERE A.D_KEY = "
				+ CommonConstants.D_KEY + " AND A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode()
				+ "' AND A.SALES_PART_NO = '" + id + "' ");
		System.err.println("tab3: " + sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(), null);
		return list;
	}

	@Override
	public PageInfoDto queryOwnerAndCustomer(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String license = queryParam.get("license");
		String customerType = queryParam.get("customerType");
		String customerCode = queryParam.get("customerCode");
		String customerName = queryParam.get("customerName");
		if (StringUtils.isNullOrEmpty(license)) {
			sql.append("select distinct * from " + " (select " + CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_OWNER
					+ " AS PRE_PAY_CUS_TYPE,O.DEALER_CODE, O.OWNER_NO as CUSTOMER_CODE, OWNER_NAME as CUSTOMER_NAME, ADDRESS,O.ZIP_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,O.PRE_PAY,O.ARREARAGE_AMOUNT,CUS_RECEIVE_SORT from TM_OWNER O "
					+ " LEFT JOIN TM_VEHICLE V ON V.DEALER_CODE=O.DEALER_CODE AND O.OWNER_NO=V.OWNER_NO "
					+ " where O.DEALER_CODE = '" + dealerCode + "'");
			sql.append("union " + " select " + CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_CUSTOMER
					+ " AS PRE_PAY_CUS_TYPE,DEALER_CODE ,CUSTOMER_CODE, CUSTOMER_NAME, ADDRESS,ZIP_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,PRE_PAY,ARREARAGE_AMOUNT,CUS_RECEIVE_SORT from TM_PART_CUSTOMER"
					+ " where DEALER_CODE = '" + dealerCode + "'");
		} else {
			sql.append(" select * from( ");
			sql.append(" select distinct * from " + " (select " + CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_OWNER
					+ " AS PRE_PAY_CUS_TYPE,O.DEALER_CODE, O.OWNER_NO as CUSTOMER_CODE, OWNER_NAME as CUSTOMER_NAME, ADDRESS,O.ZIP_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,O.PRE_PAY,O.ARREARAGE_AMOUNT,CUS_RECEIVE_SORT,V.LICENSE from TM_OWNER O "
					+ " LEFT JOIN TM_VEHICLE V ON V.DEALER_CODE=O.DEALER_CODE AND O.OWNER_NO=V.OWNER_NO where O.DEALER_CODE = '"
					+ dealerCode + "' ");
			sql.append(Utility.getLikeCond("V", "LICENSE", license, "AND"));
			if (customerType != null && customerType.equals(CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_CUSTOMER)) {
				sql.append(" and 1=2 ");
			}
			sql.append(" union " + " select " + CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_CUSTOMER
					+ " AS PRE_PAY_CUS_TYPE,DEALER_CODE,CUSTOMER_CODE, CUSTOMER_NAME, ADDRESS,ZIP_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,PRE_PAY,ARREARAGE_AMOUNT,CUS_RECEIVE_SORT,'' as LICENSE from TM_PART_CUSTOMER"
					+ " where DEALER_CODE = '" + dealerCode + "' ");
			if (customerType != null && customerType.equals(CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_OWNER)) {
				sql.append(" and 1=2 ");
			} else if (!StringUtils.isNullOrEmpty(license)) {
				sql.append(" and 1=2 ");
			}
			if (customerType != null && customerType.equals(CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_INSURANCE)) {
				sql.append(" and 1=2 ");
			}
			sql.append(" union " + " select " + CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_INSURANCE
					+ " AS PRE_PAY_CUS_TYPE,DEALER_CODE,CUSTOMER_CODE, CUSTOMER_NAME, ADDRESS,ZIP_CODE,CONTACTOR_NAME,CONTACTOR_PHONE,CONTACTOR_MOBILE,PRE_PAY,ARREARAGE_AMOUNT,CUS_RECEIVE_SORT,'' as LICENSE from TM_PART_CUSTOMER"
					+ " where DEALER_CODE = '" + dealerCode
					+ "'and CUSTOMER_TYPE_CODE = (select CUSTOMER_TYPE_CODE from TM_CUSTOMER_TYPE where DEALER_CODE='"
					+ dealerCode + "'and CUSTOMER_TYPE_NAME LIKE '%保险%')  ");
			if (customerType != null && customerType.equals(CommonConstants.DICT_PRE_PAY_CUSTOMER_TYPE_OWNER)) {
				sql.append(" and 1=2 ");
			} else if (!StringUtils.isNullOrEmpty(license)) {
				sql.append(" and 1=2 ");
			}
			sql.append(" ) a where 1=1 ");

		}
		sql.append(" ) A  where 1 =1 ");
		Utility.sqlToEquals(sql, customerType, "PRE_PAY_CUS_TYPE", "A");
		sql.append(Utility.getLikeCond("A", "CUSTOMER_CODE", customerCode, "AND"));
		sql.append(Utility.getLikeCond("A", "CUSTOMER_NAME", customerName, "AND"));
		System.err.println(sql.toString());
		List<Object> params = new ArrayList<Object>();
		PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;
	}

	@Override
	public List<Map> queryOtherCost() throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		sql.append("  SELECT * FROM TM_OTHER_COST where dealer_code in " + "(SELECT A.SHARE_ENTITY FROM ("
				+ CommonConstants.VM_ENTITY_SHARE_WITH + ") A WHERE DEALER_CODE='" + dealerCode + "' AND "
				+ "BIZ_CODE = 'UNIFIED_VIEW')");
		logger.debug("其他成本:        " + sql.toString());
		List<Object> params = new ArrayList<Object>();
		List<Map> list = DAOUtil.findAll(sql.toString(), params);
		return list;
	}

	@Override
	public void addBalanceAccounts(BalanceDTO balanceDTO) throws ServiceBizException {
		String roNo = balanceDTO.getRoNo();
		String salesPartNo = balanceDTO.getSalesPartNo();
		String gcsSysRepairNo = null;
		if (!StringUtils.isNullOrEmpty(roNo)) {
			List<Object> params = new ArrayList<Object>();
			params.add(FrameworkUtil.getLoginInfo().getDealerCode());
			params.add(CommonConstants.D_KEY);
			params.add(CommonConstants.DICT_IS_YES);
			params.add(roNo);
			List<Map> list = DAOUtil.findAll(
					"SELECT * FROM TT_BALANCE_ACCOUNTS A WHERE A.DEALER_CODE= ? AND A.D_KEY = ? AND IS_RED <> ? AND A.RO_NO = ?",
					params);
			if (list.size() > 0) {
				throw new ServiceBizException("单据已结算，不能重复结算");
			}
		}

		if (!StringUtils.isNullOrEmpty(salesPartNo)) {
			List<Object> params = new ArrayList<Object>();
			params.add(FrameworkUtil.getLoginInfo().getDealerCode());
			params.add(CommonConstants.D_KEY);
			params.add(CommonConstants.DICT_IS_YES);
			params.add(salesPartNo);
			List<Map> list = DAOUtil.findAll(
					"SELECT * FROM TT_BALANCE_ACCOUNTS A WHERE A.DEALER_CODE= ? AND A.D_KEY = ? AND IS_RED <> ? AND A.SALES_PART_NO = ?",
					params);
			if (list.size() > 0) {
				throw new ServiceBizException("单据已结算，不能重复结算");
			}
		}

		// 如果是索赔工单，结算的时候生成GSC系统保修单号
		if (!StringUtils.isNullOrEmpty(roNo)) {
			List<Object> params = new ArrayList<Object>();
			params.add(FrameworkUtil.getLoginInfo().getDealerCode());
			params.add(roNo);
			params.add(CommonConstants.DICT_RPT_CLAIM);
			params.add(CommonConstants.D_KEY);
			List<Map> listgcsRepairOrder = DAOUtil.findAll(
					"SELECT * FROM TT_REPAIR_ORDER WHERE DEALER_CODE = ? AND RO_NO = ? AND RO_TYPE = ? AND D_KEY = ?",
					params);
			// 首先判断是否索赔工单
			if (listgcsRepairOrder != null && listgcsRepairOrder.size() > 0) {
				// 获取最后生产索赔单号的的工单
				List<Object> queryParam = new ArrayList<Object>();
				queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
				queryParam.add(CommonConstants.DICT_RPT_CLAIM);
				Map map = DAOUtil
						.findFirst("SELECT  a.GCS_SYS_REPAIR_NO FROM tt_balance_accounts a LEFT JOIN tt_repair_order b "
								+ " ON a.ro_no = b.ro_no AND a.dealer_code = b.dealer_code  WHERE a.dealer_code = ? AND b.RO_TYPE = ? "
								+ " AND ( a.GCS_SYS_REPAIR_NO IS NOT NULL  AND a.GCS_SYS_REPAIR_NO <> '' )  ORDER BY a.created_at "
								+ " DESC, A.BALANCE_NO DESC LIMIT 1 ", queryParam);
				if (map.size() > 0) {
					String lastGscNO = map.get(0).toString();
					if (lastGscNO != null && lastGscNO.length() == 6) {
						gcsSysRepairNo = Utility.getRealNumber(lastGscNO);
					} else {
						gcsSysRepairNo = Utility.getRealNumber("");
					}
					logger.debug("GCS_SYS_REPAIR_NO = " + gcsSysRepairNo);
				}
			}
		}

		// 新增结算单
		String balanceNo = commonNoService.GetBillNo(CommonConstants.SRV_JSD);
		boolean zeroBalance = false;
		logger.debug("BALANCE_NO = " + balanceNo);

		// 结算时使用会员卡积分
		// 集团模式(惠通陆华)需要进行改造
		String cardId = balanceDTO.getCardId();
		String useCredit = balanceDTO.getUseCredit();
		/**
		 * 积分先不做
		 */

		TtBalanceAccountsPO accountsPo = new TtBalanceAccountsPO();

		accountsPo.set("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		accountsPo.set("BALANCE_NO", balanceNo);
		accountsPo.set("RO_NO", balanceDTO.getRoNo());
		accountsPo.set("SALES_PART_NO", balanceDTO.getSalesPartNo());
		accountsPo.set("BALANCE_MODE_CODE", balanceDTO.getBalanceModeCode());
		accountsPo.set("DISCOUNT_MODE_CODE", balanceDTO.getDiscountModeCode());
		accountsPo.set("CONTRACT_NO", balanceDTO.getContractNo());
		accountsPo.set("CONTRACT_CARD", balanceDTO.getContractCard());
		accountsPo.set("INVOICE_NO", balanceDTO.getInvoiceNo());
		accountsPo.set("MEMBER_NO", balanceDTO.getMemberNo());
		accountsPo.set("INVOICE_TYPE_CODE", balanceDTO.getInvoiceTypeCode());
		accountsPo.set("PAY_TYPE_CODE", balanceDTO.getPayTypeCode());
		accountsPo.set("LABOUR_AMOUNT", balanceDTO.getLabourAmount());
		accountsPo.set("REPAIR_PART_AMOUNT", balanceDTO.getRepairPartAmount());
		accountsPo.set("SALES_PART_AMOUNT", balanceDTO.getSalesPartAmount());
		accountsPo.set("ADD_ITEM_AMOUNT", balanceDTO.getAddItemAmount());
		accountsPo.set("OVER_ITEM_AMOUNT", balanceDTO.getOverItemAmount());
		accountsPo.set("TAX", Utility.getDefaultValue("1003"));
		accountsPo.set("TAX_AMOUNT", balanceDTO.getTaxAmountBalance());
		accountsPo.set("NET_AMOUNT", balanceDTO.getNetAmountBalance());
		accountsPo.set("TOTAL_AMOUNT", balanceDTO.getTotalAmount());
		accountsPo.set("RECEIVE_AMOUNT", balanceDTO.getReceiveAmount());
		accountsPo.set("DERATE_AMOUNT", balanceDTO.getDerateAmount());
		accountsPo.set("LABOUR_COST", balanceDTO.getLabourCost());
		accountsPo.set("REPAIR_PART_COST", balanceDTO.getRepairPartCost());
		accountsPo.set("SALES_PART_COST", balanceDTO.getSalesPartCost());
		accountsPo.set("PAY_OFF", balanceDTO.getPayOff());
		accountsPo.set("SQUARE_DATE", new Timestamp(System.currentTimeMillis()));
		accountsPo.set("BALANCE_HANDLER", FrameworkUtil.getLoginInfo().getEmployeeNo());
		accountsPo.set("IS_RED", CommonConstants.DICT_IS_NO);
		accountsPo.set("BALANCE_TIME", new Timestamp(System.currentTimeMillis()));
		accountsPo.set("PRODUCTION_VALUE", balanceDTO.getProductionValue());
		accountsPo.set("SUB_OBB_AMOUNT", balanceDTO.getSubObbAmount());
		accountsPo.set("CARDS_AMOUNT", balanceDTO.getCardsAmount());
		accountsPo.set("GIFT_AMOUNT", balanceDTO.getGiftAmount());
		accountsPo.set("SUM_AMOUNT", balanceDTO.getSumAmount());
		accountsPo.set("ACC_ID", balanceDTO.getAccId());
		accountsPo.set("ARR_BALANCE", balanceDTO.getArrBalance());
		accountsPo.set("INSURATION_CODE", balanceDTO.getInsurationCode());
		accountsPo.set("INSURATION_NO", balanceDTO.getInsurationNo());
		accountsPo.set("REMARK", balanceDTO.getRemarkBalance());
		accountsPo.set("REMARK1", balanceDTO.getRemark1Balance());
		if (!StringUtils.isNullOrEmpty(useCredit)) {
			accountsPo.set("THIS_USE_CREDIT", useCredit);
		}
		if (!StringUtils.isNullOrEmpty(cardId)) {
			accountsPo.set("CARD_ID", cardId);
		}
		if (!StringUtils.isNullOrEmpty(balanceDTO.getCardTypeCode())) {
			accountsPo.set("CARD_TYPE_CODE", balanceDTO.getCardTypeCode());
		}
		if (!StringUtils.isNullOrEmpty(gcsSysRepairNo)) {
			accountsPo.set("GCS_SYS_REPAIR_NO", gcsSysRepairNo);
		}
		accountsPo.saveIt();

		/**
		 * 更新车辆表中该车辆的欠款,加上本次的(应)收款金额 if (vin != null) { TmVehiclePOFactory
		 * tmvehicle = new TmVehiclePOFactory();
		 * tmvehicle.updateArrearageAmount(entityCode, -receiveAmount, vin,
		 * conn, userId); }
		 */

		RepairOrderPO ttRepairOrderPO = null;
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		if (!StringUtils.isNullOrEmpty(balanceDTO.getRoNo())) {
			ttRepairOrderPO = RepairOrderPO.findByCompositeKeys(dealerCode, balanceDTO.getRoNo().toString());
		}

		// 结算时使用电子礼券，增加使用记录

		if (balanceDTO.getAccId() != null && balanceDTO.getGiftAmount() != null
				&& Double.parseDouble(balanceDTO.getGiftAmount()) > 0) {
			TmGiftCertificateItemPO itemPo = new TmGiftCertificateItemPO();
			itemPo.set("DEALER_CODE", dealerCode);
			itemPo.set("ACC_ID", balanceDTO.getAccId());
			itemPo.set("RO_NO", balanceDTO.getRoNo());
			itemPo.set("BALANCE_NO", balanceNo);
			itemPo.set("VIN", ttRepairOrderPO.getString("VIN"));
			itemPo.set("LICENSE", ttRepairOrderPO.getString("LICENSE"));
			itemPo.set("PAY_AMOUNT", balanceDTO.getGiftAmount());
			itemPo.set("SERVICE_ADVISOR", ttRepairOrderPO.getString("SERVICE_ADVISOR"));
			itemPo.set("D_KEY", CommonConstants.D_KEY);
			itemPo.saveIt();

			StringBuffer sql = new StringBuffer();
			String giftAmount = balanceDTO.getGiftAmount();
			String accId = balanceDTO.getAccId();
			sql.append(" UPDATE TM_GIFT_CERTIFICATE SET PAY_AMOUNT = COALESCE(PAY_AMOUNT,0) + " + giftAmount
					+ " , REST_AMOUNT = COALESCE(REST_AMOUNT,0) - " + giftAmount + " WHERE DEALER_CODE = '" + dealerCode
					+ "' AND ACC_ID = " + accId);
			Base.exec(sql.toString());

		}

		List<Map> list = balanceDTO.getbLDtoList();
		if (list.size() > 0) {
			List<Map> hiddenList1 = balanceDTO.getHiddenList1();
			TtBalanceLabourPO labourPo = null;
			TtBalanceLabourPayobjPO labourObjPo = null;
			for (int i = 0; i < list.size(); i++) {
				if (!StringUtils.isNullOrEmpty(list.get(i).get("LABOUR_CODE"))) {
					labourPo = new TtBalanceLabourPO();
					labourPo.set("RO_ITEM_ID", list.get(i).get("ITEM_ID"));
					labourPo.set("BALANCE_NO", balanceNo);
					labourPo.set("DEALER_CODE", dealerCode);
					labourPo.set("RO_NO", balanceDTO.getRoNo());
					labourPo.set("MANAGE_SORT_CODE", list.get(i).get("MANAGE_SORT_CODE"));
					labourPo.set("TROUBLE_DESC", list.get(i).get("TROUBLE_DESC"));
					labourPo.set("TROUBLE_CAUSE", list.get(i).get("TROUBLE_CAUSE"));
					labourPo.set("LOCAL_LABOUR_CODE", list.get(i).get("LOCAL_LABOUR_CODE"));
					labourPo.set("LABOUR_CODE", list.get(i).get("LABOUR_CODE"));
					labourPo.set("LABOUR_NAME", list.get(i).get("LABOUR_NAME"));
					labourPo.set("LOCAL_LABOUR_NAME", list.get(i).get("LOCAL_LABOUR_NAME"));
					if (!StringUtils.isNullOrEmpty(list.get(i).get("LABOUR_AMOUNT"))) {
						labourPo.set("LABOUR_AMOUNT", list.get(i).get("LABOUR_AMOUNT"));
					}
					if (!StringUtils.isNullOrEmpty(list.get(i).get("STD_LABOUR_HOUR"))) {
						labourPo.set("STD_LABOUR_HOUR", list.get(i).get("STD_LABOUR_HOUR"));
					}
					if (!StringUtils.isNullOrEmpty(list.get(i).get("ASSIGN_LABOUR_HOUR"))) {
						labourPo.set("ASSIGN_LABOUR_HOUR", list.get(i).get("ASSIGN_LABOUR_HOUR"));
					}
					if (!StringUtils.isNullOrEmpty(list.get(i).get("LABOUR_PRICE"))) {
						labourPo.set("LABOUR_PRICE", list.get(i).get("LABOUR_PRICE"));
					}
					labourPo.set("WORKER_TYPE_CODE", list.get(i).get("WORKER_TYPE_CODE"));
					if (!StringUtils.isNullOrEmpty(list.get(i).get("CALC_DISCOUNT_AMOUNT"))) {
						labourPo.set("DISCOUNT_AMOUNT", list.get(i).get("CALC_DISCOUNT_AMOUNT"));
					}
					if (!StringUtils.isNullOrEmpty(list.get(i).get("CALC_REAL_RECEIVE_AMOUNT"))) {
						labourPo.set("REAL_RECEIVE_AMOUNT", list.get(i).get("CALC_REAL_RECEIVE_AMOUNT"));
					}
					if (!StringUtils.isNullOrEmpty(list.get(i).get("DISCOUNT"))) {
						labourPo.set("DISCOUNT", list.get(i).get("DISCOUNT"));
					}
					labourPo.set("TECHNICIAN", list.get(i).get("TECHNICIAN"));
					labourPo.set("CHARGE_PARTITION_CODE", list.get(i).get("CHARGE_PARTITION_CODE"));
					labourPo.set("INTER_RETURN", list.get(i).get("INTER_RETURN"));
					labourPo.set("PRE_CHECK", list.get(i).get("PRE_CHECK"));
					labourPo.set("CONSIGN_EXTERIOR", list.get(i).get("CONSIGN_EXTERIOR"));
					labourPo.set("REMARK", list.get(i).get("REMARK"));
					labourPo.set("PACKAGE_CODE", list.get(i).get("PACKAGE_CODE"));
					labourPo.set("REPAIR_TYPE_CODE", list.get(i).get("REPAIR_TYPE_CODE"));
					labourPo.set("MODEL_LABOUR_CODE", list.get(i).get("MODEL_LABOUR_CODE"));
					labourPo.set("MAINTAIN_PACKAGE_CODE", list.get(i).get("MAINTAIN_PACKAGE_CODE"));
					labourPo.set("ACTIVITY_CODE", list.get(i).get("ACTIVITY_CODE"));
					if (!StringUtils.isNullOrEmpty(list.get(i).get("CARD_ID"))) {
						labourPo.set("ACTIVITY_CODE", list.get(i).get("CARD_ID"));
					}
					labourPo.saveIt();

					String itemId = labourPo.get("ITEM_ID").toString();
					for (int j = 0; j < hiddenList1.size(); j++) {
						if (list.get(i).get("LABOUR_CODE").equals(hiddenList1.get(j).get("LABOUR_CODE"))) {
							// 新增结算单维修项目收费对象
							labourObjPo = new TtBalanceLabourPayobjPO();
							Double receiveableAmount = Double.parseDouble(list.get(i).get("LABOUR_AMOUNT").toString());
							Long discount = Long.valueOf(hiddenList1.get(j).get("DISCOUNT").toString());
							Double discountAmount = receiveableAmount * (1 - discount);
							Double realReceiveAmount = receiveableAmount * discount;

							labourObjPo.set("ITEM_ID", itemId);
							labourObjPo.set("DEALER_CODE", dealerCode);
							labourObjPo.set("PAYMENT_OBJECT_CODE", hiddenList1.get(j).get("PAYMENT_OBJECT_CODE"));
							labourObjPo.set("PAYMENT_OBJECT_NAME", hiddenList1.get(j).get("PAYMENT_OBJECT_NAME"));
							labourObjPo.set("RECEIVEABLE_AMOUNT", list.get(i).get("LABOUR_AMOUNT"));
							labourObjPo.set("DISCOUNT", hiddenList1.get(j).get("DISCOUNT"));
							labourObjPo.set("DISCOUNT_AMOUNT", discountAmount);
							labourObjPo.set("REAL_RECEIVE_AMOUNT", realReceiveAmount);
							labourObjPo.saveIt();
						} else {
							continue;
						}
					}
				}
			}
		}
		// 新增结算单维修配件
		List<Map> list2 = balanceDTO.getbRPDtoList();
		List<Map> hiddenList2 = balanceDTO.getHiddenList2();
		if (list2.size() > 0) {
			TtBalanceRepairPartPO partPo = null;
			TtBalanceRepairPartPayobjPO partObjPo = null;
			for (Map map : list2) {
				partPo = new TtBalanceRepairPartPO();
				partPo.set("RO_ITEM_ID", map.get("ITEM_ID"));
				partPo.set("BALANCE_NO", balanceNo);
				partPo.set("DEALER_CODE", dealerCode);
				partPo.set("RO_NO", balanceDTO.getRoNo());
				partPo.set("CHARGE_PARTITION_CODE", map.get("CHARGE_PARTITION_CODE"));
				partPo.set("MANAGE_SORT_CODE", map.get("MANAGE_SORT_CODE"));
				partPo.set("STORAGE_CODE", map.get("STORAGE_CODE"));
				partPo.set("STORAGE_POSITION_CODE", map.get("STORAGE_POSITION_CODE"));
				if (!StringUtils.isNullOrEmpty(map.get("IS_MAIN_PART"))) {
					partPo.set("IS_MAIN_PART", map.get("IS_MAIN_PART"));
				}
				partPo.set("PART_NO", map.get("PART_NO"));
				partPo.set("PART_NAME", map.get("PART_NAME"));
				if (!StringUtils.isNullOrEmpty(map.get("PART_QUANTITY"))) {
					partPo.set("PART_QUANTITY", map.get("PART_QUANTITY"));
				}
				partPo.set("UNIT_CODE", map.get("UNIT_CODE"));
				if (!StringUtils.isNullOrEmpty(map.get("PRICE_TYPE"))) {
					partPo.set("PRICE_TYPE", map.get("PRICE_TYPE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("CALC_DISCOUNT_AMOUNT"))) {
					partPo.set("DISCOUNT_AMOUNT", map.get("CALC_DISCOUNT_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("CALC_REAL_RECEIVE_AMOUNT"))) {
					partPo.set("REAL_RECEIVE_AMOUNT", map.get("CALC_REAL_RECEIVE_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("OEM_LIMIT_PRICE"))) {
					partPo.set("OEM_LIMIT_PRICE", map.get("OEM_LIMIT_PRICE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("PART_COST_PRICE"))) {
					partPo.set("PART_COST_PRICE", map.get("PART_COST_PRICE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_PRICE"))) {
					partPo.set("PART_SALES_PRICE", map.get("PART_SALES_PRICE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("PART_COST_AMOUNT"))) {
					partPo.set("PART_COST_AMOUNT", map.get("PART_COST_AMOUNT"));
				}
				partPo.set("MAINTAIN_PACKAGE_CODE", map.get("MAINTAIN_PACKAGE_CODE"));
				if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT"))) {
					partPo.set("PART_SALES_AMOUNT", map.get("PART_SALES_AMOUNT"));
				}
				partPo.set("SENDER", map.get("SENDER"));
				partPo.set("RECEIVER", map.get("RECEIVER"));
				if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT"))) {
					partPo.set("PART_SALES_AMOUNT", map.get("PART_SALES_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("SEND_TIME"))) {
					partPo.set("SEND_TIME", map.get("SEND_TIME"));
				}
				partPo.set("IS_FINISHED", CommonConstants.DICT_IS_NO);
				if (!StringUtils.isNullOrEmpty(map.get("BATCH_NO"))) {
					partPo.set("BATCH_NO", map.get("BATCH_NO"));
				}
				partPo.set("PRE_CHECK", map.get("PRE_CHECK"));
				if (!StringUtils.isNullOrEmpty(map.get("DISCOUNT"))) {
					partPo.set("DISCOUNT", map.get("DISCOUNT"));
				}
				// 配件类别
				if (!StringUtils.isNullOrEmpty(map.get("PART_CATEGORY"))) {
					partPo.set("PART_CATEGORY", map.get("PART_CATEGORY"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("INTER_RETURN"))) {
					partPo.set("INTER_RETURN", map.get("INTER_RETURN"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("CONSIGN_EXTERIOR"))) {
					partPo.set("CONSIGN_EXTERIOR", map.get("CONSIGN_EXTERIOR"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("PACKAGE_CODE"))) {
					partPo.set("PACKAGE_CODE", map.get("PACKAGE_CODE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("REPAIR_TYPE_CODE"))) {
					partPo.set("REPAIR_TYPE_CODE", map.get("REPAIR_TYPE_CODE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("MODEL_LABOUR_CODE"))) {
					partPo.set("MODEL_LABOUR_CODE", map.get("MODEL_LABOUR_CODE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("LABOUR_CODE"))) {
					partPo.set("LABOUR_CODE", map.get("LABOUR_CODE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("ACTIVITY_CODE"))) {
					partPo.set("ACTIVITY_CODE", map.get("ACTIVITY_CODE"));
				}
				if (!StringUtils.isNullOrEmpty(map.get("CARD_ID"))) {
					partPo.set("CARD_ID", map.get("CARD_ID"));
				}
				partPo.saveIt();

				// 如果低于成本价,记日志
				if (partPo.get("PART_SALES_PRICE") != null && partPo.get("PART_COST_PRICE") != null) {
					if (Double.parseDouble(partPo.get("PART_SALES_PRICE").toString()) < Double
							.parseDouble(partPo.get("PART_COST_PRICE").toString())) {
						handleOperateLog(
								"结算时维修配件：" + partPo.get("PART_NAME") + "【" + partPo.get("PART_NO") + "】的销售单价小于成本单价", "",
								Integer.parseInt(DictCodeConstants.DICT_ASCLOG_BALANCE_MANAGE),
								FrameworkUtil.getLoginInfo().getEmployeeNo());
					}
				}
				// 新增维修材料收费对象
				for (Map mapHidden : hiddenList2) {
					if (map.get("PART_NO").equals(mapHidden.get("PART_NO"))) {
						partObjPo = new TtBalanceRepairPartPayobjPO();
						Double receiveableAmount = Double.parseDouble(map.get("LABOUR_AMOUNT").toString());
						Long discount = Long.valueOf(mapHidden.get("DISCOUNT").toString());
						Double discountAmount = receiveableAmount * (1 - discount);
						Double realReceiveAmount = receiveableAmount * discount;

						partObjPo.set("ITEM_ID", partPo.get("ITEM_ID"));
						partObjPo.set("DEALER_CODE", dealerCode);
						partObjPo.set("PAYMENT_OBJECT_CODE", mapHidden.get("PAYMENT_OBJECT_CODE"));
						partObjPo.set("PAYMENT_OBJECT_NAME", mapHidden.get("PAYMENT_OBJECT_NAME"));
						partObjPo.set("RECEIVEABLE_AMOUNT", receiveableAmount);
						partObjPo.set("DISCOUNT", mapHidden.get("DISCOUNT"));
						partObjPo.set("DISCOUNT_AMOUNT", discountAmount);
						partObjPo.set("REAL_RECEIVE_AMOUNT", realReceiveAmount);
						partObjPo.saveIt();

					} else {
						continue;
					}
				}
			}
		}

		// 新增结算单附加项目明细
		List<Map> list4 = balanceDTO.getbAIDtoList();
		List<Map> hiddenList4 = balanceDTO.getHiddenList4();
		if (list4.size() > 0) {
			TtBalanceAddItemPO addPo = null;
			TtBalanceAddItemPayobjPO addObjPo = null;
			for (Map map4 : list4) {
				addPo = new TtBalanceAddItemPO();
				if (!StringUtils.isNullOrEmpty(balanceNo)) {
					addPo.set("BALANCE_NO", balanceNo);
				}
				if (!StringUtils.isNullOrEmpty(dealerCode)) {
					addPo.set("DEALER_CODE", dealerCode);
				}
				if (!StringUtils.isNullOrEmpty(map4.get("RO_NO"))) {
					addPo.set("RO_NO", map4.get("RO_NO"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("MANAGE_SORT_CODE"))) {
					addPo.set("MANAGE_SORT_CODE", map4.get("MANAGE_SORT_CODE"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("CHARGE_PARTITION_CODE"))) {
					addPo.set("CHARGE_PARTITION_CODE", map4.get("CHARGE_PARTITION_CODE"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("ADD_ITEM_CODE"))) {
					addPo.set("ADD_ITEM_CODE", map4.get("ADD_ITEM_CODE"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("ADD_ITEM_NAME"))) {
					addPo.set("ADD_ITEM_NAME", map4.get("ADD_ITEM_NAME"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("ADD_ITEM_AMOUNT"))) {
					addPo.set("ADD_ITEM_AMOUNT", map4.get("ADD_ITEM_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("REMARK"))) {
					addPo.set("REMARK", map4.get("REMARK"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("DISCOUNT"))) {
					addPo.set("DISCOUNT", map4.get("DISCOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("DISCOUNT_AMOUNT"))) {
					addPo.set("DISCOUNT_AMOUNT", map4.get("CALC_DISCOUNT_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("REAL_RECEIVE_AMOUNT"))) {
					addPo.set("REAL_RECEIVE_AMOUNT", map4.get("CALC_REAL_RECEIVE_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(map4.get("ACTIVITY_CODE"))) {
					addPo.set("ACTIVITY_CODE", map4.get("ACTIVITY_CODE"));
				}
				addPo.saveIt();

				// 新增附加项目收费对象
				for (Map mapHidden4 : hiddenList4) {
					if (map4.get("ADD_ITEM_CODE").equals(mapHidden4.get("ADD_ITEM_CODE"))) {
						addObjPo = new TtBalanceAddItemPayobjPO();
						Double receiveableAmount = Double.parseDouble(map4.get("LABOUR_AMOUNT").toString());
						Long discount = Long.valueOf(mapHidden4.get("DISCOUNT").toString());
						Double discountAmount = receiveableAmount * (1 - discount);
						Double realReceiveAmount = receiveableAmount * discount;

						addObjPo.set("ITEM_ID", addPo.get("ITEM_ID"));
						addObjPo.set("DEALER_CODE", dealerCode);
						addObjPo.set("PAYMENT_OBJECT_CODE", mapHidden4.get("PAYMENT_OBJECT_CODE"));
						addObjPo.set("PAYMENT_OBJECT_NAME", mapHidden4.get("PAYMENT_OBJECT_NAME"));
						addObjPo.set("RECEIVEABLE_AMOUNT", receiveableAmount);
						addObjPo.set("DISCOUNT", mapHidden4.get("DISCOUNT"));
						addObjPo.set("DISCOUNT_AMOUNT", discountAmount);
						addObjPo.set("REAL_RECEIVE_AMOUNT", realReceiveAmount);
						addObjPo.saveIt();
					} else {
						continue;
					}
				}
			}
		}

		// 新增结算单其他成本
		List<Map> hiddenList5 = balanceDTO.getHiddenList5();
		if (hiddenList5.size() > 0) {
			TtBalanceOtherCostPO otherPo = null;
			for (Map mapHidden5 : hiddenList5) {
				otherPo = new TtBalanceOtherCostPO();
				otherPo.set("BALANCE_NO", balanceNo);
				otherPo.set("DEALER_CODE", dealerCode);
				otherPo.set("OTHER_COST_NAME", mapHidden5.get("OTHER_COST_NAME"));
				otherPo.set("OTHER_COST_CODE", mapHidden5.get("OTHER_COST_CODE"));
				if (!StringUtils.isNullOrEmpty(mapHidden5.get("OTHER_COST_AMOUNT"))) {
					otherPo.set("OTHER_COST_AMOUNT", mapHidden5.get("OTHER_COST_AMOUNT"));
				}
				otherPo.saveIt();
			}
		}

		// 新增结算单收费对象列表
		List<Map> receivableList = balanceDTO.getReceivableList();// 应收
		List<Map> receivedList = balanceDTO.getReceivedList();// 实收
		BalancePayobjPO payObjPo = null;
		if (receivableList.size() > 0) {
			for (int i = 0; i < receivableList.size(); i++) {
				payObjPo = new BalancePayobjPO();
				payObjPo.set("BALANCE_NO", balanceNo);
				payObjPo.set("DEALER_CODE", dealerCode);
				payObjPo.set("RO_NO", roNo);
				payObjPo.set("SALES_PART_NO", salesPartNo);
				payObjPo.set("PAYMENT_OBJECT_CODE", receivableList.get(i).get("PAYMENT_OBJECT_CODE"));
				payObjPo.set("PAYMENT_OBJECT_NAME", receivableList.get(i).get("PAYMENT_OBJECT_NAME"));
				if (!StringUtils.isNullOrEmpty(receivableList.get(i).get("RECEIVEABLE_LABOUR_FEE"))) {
					payObjPo.set("RECEIVEABLE_LABOUR_FEE", receivableList.get(i).get("RECEIVEABLE_LABOUR_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivableList.get(i).get("RECEIVEABLE_REPAIR_PART_FEE"))) {
					payObjPo.set("RECEIVEABLE_REPAIR_PART_FEE",
							receivableList.get(i).get("RECEIVEABLE_REPAIR_PART_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivableList.get(i).get("RECEIVEABLE_SALES_PART_FEE"))) {
					payObjPo.set("RECEIVEABLE_SALES_PART_FEE", receivableList.get(i).get("RECEIVEABLE_SALES_PART_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivableList.get(i).get("CALC_RECEIVEABLE_TOTAL_AMOUNT"))) {
					payObjPo.set("RECEIVEABLE_TOTAL_AMOUNT",
							receivableList.get(i).get("CALC_RECEIVEABLE_TOTAL_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(receivableList.get(i).get("RECEIVEABLE_ADD_ITEM_FEE"))) {
					payObjPo.set("RECEIVEABLE_ADD_ITEM_FEE", receivableList.get(i).get("RECEIVEABLE_ADD_ITEM_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivableList.get(i).get("RECEIVEABLE_OVER_ITEM_FEE"))) {
					payObjPo.set("RECEIVEABLE_OVER_ITEM_FEE", receivableList.get(i).get("RECEIVEABLE_OVER_ITEM_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT"))) {
					payObjPo.set("RECEIVABLE_AMOUNT", receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("REAL_LABOUR_FEE"))) {
					payObjPo.set("REAL_LABOUR_FEE", receivedList.get(i).get("REAL_LABOUR_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("REAL_REPAIR_PART_FEE"))) {
					payObjPo.set("REAL_REPAIR_PART_FEE", receivedList.get(i).get("REAL_REPAIR_PART_FEE"));
				}
				if (StringUtils.isNullOrEmpty(receivedList.get(i).get("REAL_LABOUR_FEE"))) {
					zeroBalance = true;
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("REAL_SALES_PART_FEE"))) {
					payObjPo.set("REAL_SALES_PART_FEE", receivedList.get(i).get("REAL_SALES_PART_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("REAL_ADD_ITEM_FEE"))) {
					payObjPo.set("REAL_ADD_ITEM_FEE", receivedList.get(i).get("REAL_ADD_ITEM_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("REAL_OVER_ITEM_FEE"))) {
					payObjPo.set("REAL_OVER_ITEM_FEE", receivedList.get(i).get("REAL_OVER_ITEM_FEE"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_REAL_TOTAL_AMOUNT"))) {
					payObjPo.set("REAL_TOTAL_AMOUNT", receivedList.get(i).get("CALC_REAL_TOTAL_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_NET_AMOUNT"))) {
					payObjPo.set("NET_AMOUNT", receivedList.get(i).get("CALC_NET_AMOUNT"));
				}
				String tax = Utility.getDefaultValue("1003");
				if (!StringUtils.isNullOrEmpty(tax)) {
					payObjPo.set("TAX", tax);
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_TAX_AMOUNT"))) {
					payObjPo.set("TAX_AMOUNT", receivedList.get(i).get("CALC_TAX_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT"))) {
					payObjPo.set("RECEIVABLE_AMOUNT", receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT"));
				}
				payObjPo.set("RECEIVED_AMOUNT", "0");
				payObjPo.set("DERATED_AMOUNT", "0");
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT"))) {
					payObjPo.set("NOT_RECEIVED_AMOUNT", receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT"));
				}
				String subObbAmount = "";
				if (StringUtils.isNullOrEmpty(receivedList.get(i).get("SUB_OBB_AMOUNT"))) {
					subObbAmount = "0";
				} else {
					subObbAmount = receivedList.get(i).get("SUB_OBB_AMOUNT").toString();
				}
				payObjPo.set("SUB_OBB_AMOUNT", subObbAmount);

				String cardsAmount = "";
				if (StringUtils.isNullOrEmpty(receivedList.get(i).get("CARDS_AMOUNT"))) {
					cardsAmount = "0";
				} else {
					cardsAmount = receivedList.get(i).get("CARDS_AMOUNT").toString();
				}
				payObjPo.set("CARDS_AMOUNT", cardsAmount);

				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("GIFT_AMOUNT"))) {
					payObjPo.set("GIFT_AMOUNT", receivedList.get(i).get("GIFT_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_SUM_AMOUNT"))) {
					payObjPo.set("SUM_AMOUNT", receivedList.get(i).get("CALC_SUM_AMOUNT"));
				}
				if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CUS_RECEIVE_SORT"))) {
					payObjPo.set("CUS_RECEIVE_SORT", receivedList.get(i).get("CUS_RECEIVE_SORT"));
				}

				if (Utility.getDefaultValue("1096").equals(CommonConstants.DICT_IS_YES)) {
					payObjPo.set("PAY_OFF", CommonConstants.DICT_IS_NO);
				} else if (StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT"))) {
					payObjPo.set("PAY_OFF", CommonConstants.DICT_IS_YES);
				} else {
					payObjPo.set("PAY_OFF", CommonConstants.DICT_IS_NO);
				}
				payObjPo.set("IS_RED", CommonConstants.DICT_IS_NO);
				payObjPo.saveIt();

				// 更新欠款
				if (!StringUtils.isNullOrEmpty(receivableList.get(i).get("PAYMENT_OBJECT_CODE"))) {
					// 更新收费对象的欠款,加上本次的未收金额
					double notReceivedAmount = 0;
					if (!StringUtils.isNullOrEmpty(receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT"))) {
						notReceivedAmount = Double
								.valueOf(receivedList.get(i).get("CALC_RECEIVABLE_AMOUNT").toString());
					}
					boolean isOwner = false;
					List<Object> queryParam = new ArrayList<Object>();
					queryParam.add(dealerCode);
					queryParam.add(receivableList.get(i).get("PAYMENT_OBJECT_CODE"));
					List<Map> ownList = DAOUtil.findAll("SELECT * FROM (" + CommonConstants.VM_OWNER
							+ ") AA  WHERE AA.DEALER_CODE = ? AND AA.OWNER_NO = ?", queryParam);
					if (ownList.size() > 0) {
						isOwner = true;
					}
					StringBuffer sql = new StringBuffer();
					if (isOwner) {
						sql.append("UPDATE TM_OWNER "
								+ " SET ARREARAGE_AMOUNT =  (case when ARREARAGE_AMOUNT is null then 0 else ARREARAGE_AMOUNT end)-(-"
								+ notReceivedAmount + ") " + "  WHERE OWNER_NO = '"
								+ receivableList.get(i).get("PAYMENT_OBJECT_CODE") + "'  AND DEALER_CODE = '"
								+ dealerCode + "' ");
					} else {
						sql.append("UPDATE TM_PART_CUSTOMER "
								+ " SET ARREARAGE_AMOUNT =  (case when ARREARAGE_AMOUNT is null then 0 else ARREARAGE_AMOUNT end)-(-"
								+ notReceivedAmount + ") " + "  WHERE CUSTOMER_CODE = '"
								+ receivableList.get(i).get("PAYMENT_OBJECT_CODE") + "'  AND DEALER_CODE = '"
								+ dealerCode + "' ");
					}
					Base.exec(sql.toString());
					StringBuffer sqlSub = new StringBuffer();
					if (isOwner) {
						sqlSub.append("UPDATE TM_OWNER_SUBCLASS "
								+ " SET ARREARAGE_AMOUNT =  (case when ARREARAGE_AMOUNT is null then 0 else ARREARAGE_AMOUNT end)-(-"
								+ notReceivedAmount + ") " + "  WHERE OWNER_NO =' "
								+ receivableList.get(i).get("PAYMENT_OBJECT_CODE") + "'  AND DEALER_CODE = '"
								+ dealerCode + "' ");
					} else {
						sqlSub.append("UPDATE TM_PART_CUSTOMER_SUBCLASS "
								+ " SET ARREARAGE_AMOUNT =  (case when ARREARAGE_AMOUNT is null then 0 else ARREARAGE_AMOUNT end)-(-"
								+ notReceivedAmount + ") " + "  WHERE CUSTOMER_CODE = '"
								+ receivableList.get(i).get("PAYMENT_OBJECT_CODE") + "'  AND DEALER_CODE =' "
								+ dealerCode + "' ");
					}
					Base.exec(sqlSub.toString());

					// 更新车辆表中该车辆的欠款,加上车主本次的未收金额
					if (!StringUtils.isNullOrEmpty(balanceDTO.getVin())
							&& !StringUtils.isNullOrEmpty(receivableList.get(i).get("PAYMENT_OBJECT_CODE"))
							&& receivableList.get(i).get("PAYMENT_OBJECT_CODE")
									.equals(ttRepairOrderPO.get("OWNER_NO"))) {
						String groupCode = Utility.getGroupEntity(dealerCode, "TM_VEHICLE");
						StringBuffer sqlVeh = new StringBuffer();
						sqlVeh.append(" UPDATE TM_VEHICLE   SET ARREARAGE_AMOUNT =  Coalesce(ARREARAGE_AMOUNT,0)+ ("
								+ notReceivedAmount + ") " + " WHERE VIN = '"
								+ Utility.fullSpaceBuffer2(balanceDTO.getVin(), 17) + "'  AND DEALER_CODE = '"
								+ groupCode + "'");
						Base.exec(sqlVeh.toString());
						StringBuffer sqlVehSub = new StringBuffer();
						sqlVehSub.append("  UPDATE TM_VEHICLE_SUBCLASS "
								+ " SET ARREARAGE_AMOUNT =  Coalesce(ARREARAGE_AMOUNT,0)+ ((" + notReceivedAmount
								+ ")) " + " WHERE VIN = '" + Utility.fullSpaceBuffer2(balanceDTO.getVin(), 17)
								+ "'  AND DEALER_CODE = '" + dealerCode + "'  ");
						Base.exec(sqlVehSub.toString());
					}
				}
			}
		}

		if (zeroBalance) {
			handleOperateLog("执行了实收工时费为0的结算：" + balanceNo, "",
					Integer.valueOf(DictCodeConstants.DICT_ASCLOG_BALANCE_MANAGE),
					FrameworkUtil.getLoginInfo().getEmployeeNo());
		}
		logger.debug("执行了实收工时费为0的结算：" + balanceNo);

		if (!StringUtils.isNullOrEmpty(balanceDTO.getSalesPartNo())) {
			TtSalesPartPO ttSalesPartPO = TtSalesPartPO.findByCompositeKeys(dealerCode, balanceDTO.getSalesPartNo());
			ttSalesPartPO.set("BALANCE_STATUS", CommonConstants.DICT_IS_YES);
			ttSalesPartPO.saveIt();
		}

		// 更新工单状态为结算
		if (!StringUtils.isNullOrEmpty(balanceDTO.getRoNo())) {
			RepairOrderPO orderPo = RepairOrderPO.findByCompositeKeys(dealerCode, balanceDTO.getRoNo());
			String isDelivery = DictCodeConstants.DICT_DELIVERY_STATUS_TYPE_NO;
			if (!StringUtils.isNullOrEmpty(orderPo)) {
				isDelivery = orderPo.getString("DELIVERY_TAG");
			}

			// 使用交车开关,增加索赔单和工单一起交车的功能
			if (!isDelivery.equals(DictCodeConstants.DICT_DELIVERY_STATUS_TYPE_YES)) {
				if (Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_SUBMIT_CAR))
						.equals(CommonConstants.DICT_IS_NO)) {
					// 自动更新索赔单
					if (roNo.indexOf("RO") != -1) {
						String roNoNew1 = roNo.replace(roNo.substring(0, 2), "RW");
						RepairOrderPO orderPO2 = RepairOrderPO.findByCompositeKeys(dealerCode, roNoNew1);
						if (!StringUtils.isNullOrEmpty(orderPO2)) {
							String aRoStatus1 = orderPO2.getString("RO_STATUS");
							orderPO2.set("RO_STATUS", aRoStatus1);
							setOrderPo(orderPO2, isDelivery);
							orderPO2.saveIt();
						}
					}
					// 自动更新工单
					else {
						String roNoNew2 = roNo.replace(roNo.substring(0, 2), "RO");
						RepairOrderPO orderPO2 = RepairOrderPO.findByCompositeKeys(dealerCode, roNoNew2);
						if (!StringUtils.isNullOrEmpty(orderPO2)) {
							String aRoStatus2 = orderPO2.getString("RO_STATUS");
							orderPO2.set("RO_STATUS", aRoStatus2);
							setOrderPo(orderPO2, isDelivery);
							orderPO2.saveIt();
						}
					}
				}
			}
		}

		// 如果车辆的第一次进厂日期为空，更新车辆第一次进厂时间，售前维修和新车装潢的工单不更新
		if (!StringUtils.isNullOrEmpty(roNo) && ttRepairOrderPO != null
				&& !ttRepairOrderPO.get("REPAIR_TYPE_CODE").equals(CommonConstants.REPAIR_TYPE_UPHOLSTER)
				&& !StringUtils.isNullOrEmpty(ttRepairOrderPO.get("VIN"))) {
			// 根据维修类型代码去维修类型表判断是否售前维修
			TmRepairTypePO RepairPO12 = TmRepairTypePO.findByCompositeKeys(dealerCode,
					ttRepairOrderPO.get("REPAIR_TYPE_CODE"));
			if (!StringUtils.isNullOrEmpty(RepairPO12)) {
				if (!RepairPO12.get("IS_PRE_SERVICE").equals(CommonConstants.DICT_IS_YES)) {
					VehiclePO tmVehiclePO = null;
					List<Object> relatList = new ArrayList<Object>();
					relatList.add(ttRepairOrderPO.get("VIN"));
					relatList.add(Utility.getGroupEntity(dealerCode, "TM_VEHICLE"));
					List<VehiclePO> listVehicle = VehiclePO.find("VIN = ? AND DEALER_CODE = ?", relatList.toArray());
					// .findAll("SELECT * FROM TM_VEHICLE WHERE VIN = ? AND
					// DEALER_CODE = ?", relatList);
					listVehicle = getVehicleSubclassList1(dealerCode, listVehicle);
					if (listVehicle != null && listVehicle.size() > 0) {
						tmVehiclePO = listVehicle.get(0);
						if (tmVehiclePO.get("FIRST_IN_DATE") == null) {
							// 二级网点业务-车辆子表更新
							String vin = "";
							if (!StringUtils.isNullOrEmpty(ttRepairOrderPO.get("VIN"))) {
								vin = ttRepairOrderPO.get("VIN").toString();
							}
							updateSubclassPO(vin, tmVehiclePO);
							tmVehiclePO.saveIt();
						}
					}
				}
			}
		}

		if (!StringUtils.isNullOrEmpty(roNo) && !StringUtils.isNull(balanceDTO.getVin())) {
			// 维修项目和配件不修移到维修建议
			TtRoLabourPO labourPO = new TtRoLabourPO();
			List<Object> queryParam = new ArrayList<Object>();
			queryParam.add(balanceDTO.getRoNo());
			queryParam.add(dealerCode);
			queryParam.add(CommonConstants.D_KEY);
			queryParam.add(CommonConstants.DICT_IS_YES);
			List labourList = DAOUtil.findAll(
					"SELECT * FROM TT_RO_LABOUR WHERE RO_NO = ? AND DEALER_CODE = ? AND D_KEY = ? AND NEEDLESS_REPAIR = ?",
					queryParam);
			if (labourList != null && labourList.size() > 0) {
				for (int i = 0; i < labourList.size(); i++) {
					labourPO = (TtRoLabourPO) labourList.get(i);
					if (labourPO != null) {
						// VIN+工时代码已经存在的话先删掉
						List<Object> paramList = new ArrayList<Object>();
						paramList.add(CommonConstants.D_KEY);
						paramList.add(dealerCode);
						paramList.add(balanceDTO.getVin());
						paramList.add(labourPO.get("LABOUR_CODE"));
						paramList.add(CommonConstants.DICT_IS_YES);

						List<Map> deleteList = DAOUtil.findAll(
								"SELECT * FROM TT_SUGGEST_MAINTAIN_LABOUR WHERE D_KEY = ? AND DEALER_CODE = ? AND VIN = ? AND LABOUR_CODE = ? AND IS_VALID = ?",
								paramList);
						if (deleteList.size() > 0) {
							for (int j = 0; j < deleteList.size(); j++) {
								TtSuggestMaintainLabourPO suggestLabourPODelete = (TtSuggestMaintainLabourPO) deleteList
										.get(j);
								suggestLabourPODelete.delete();
							}
						}

						TtSuggestMaintainLabourPO suggestLabourPO = new TtSuggestMaintainLabourPO();
						suggestLabourPO.set("DEALER_CODE", dealerCode);
						suggestLabourPO.set("LABOUR_AMOUNT", labourPO.get("LABOUR_AMOUNT"));
						suggestLabourPO.set("LABOUR_CODE", labourPO.get("LABOUR_CODE"));
						suggestLabourPO.set("LABOUR_NAME", labourPO.get("LABOUR_NAME"));
						suggestLabourPO.set("LABOUR_PRICE", labourPO.get("LABOUR_PRICE"));
						suggestLabourPO.set("RO_NO", labourPO.get("RO_NO"));
						suggestLabourPO.set("REMARK", labourPO.get("REMARK"));
						suggestLabourPO.set("REMARK", labourPO.get("REMARK"));
						suggestLabourPO.set("STD_LABOUR_HOUR", labourPO.get("STD_LABOUR_HOUR"));
						suggestLabourPO.set("SUGGEST_DATE", new Date());
						suggestLabourPO.set("VIN", balanceDTO.getVin());
						suggestLabourPO.set("REASON", labourPO.get("REASON"));
						suggestLabourPO.saveIt();

						// 先删派工子表
						List<Object> assignParam = new ArrayList<Object>();
						assignParam.add(CommonConstants.D_KEY);
						assignParam.add(dealerCode);
						assignParam.add(labourPO.get("ITEM_ID"));
						List<Map> assignDeList = DAOUtil.findAll(
								" SELECT * FROM  TT_RO_ASSIGN WHERE D_KEY = ? AND DEALER_CODE = ? AND ITEM_ID = ? ",
								assignParam);
						if (assignDeList.size() > 0) {
							for (int j = 0; j < assignDeList.size(); j++) {
								TtRoAssignPO ttRoAssignPODelete = (TtRoAssignPO) assignDeList.get(j);
								ttRoAssignPODelete.delete();
							}
						}

						// 在删除维修材料中关联维修项目中是不修的配件
						List<Object> repairParam = new ArrayList<Object>();
						repairParam.add(balanceDTO.getRoNo());
						repairParam.add(dealerCode);
						repairParam.add(CommonConstants.D_KEY);
						repairParam.add(CommonConstants.DICT_IS_YES);
						List<Map> repairLsit = DAOUtil.findAll(
								"SELECT * FROM TT_RO_REPAIR_PART WHERE RO_NO = ? AND DEALER_CODE = ? AND  D_KEY = ? AND  NEEDLESS_REPAIR = ? ",
								repairParam);
						if (repairLsit.size() > 0) {
							for (int j = 0; j < repairLsit.size(); j++) {
								TtRoRepairPartPO ttRoRepairPartPO = (TtRoRepairPartPO) repairLsit.get(j);
								ttRoRepairPartPO.delete();
							}
						}
					}
					TtRoLabourPO labourPO1 = (TtRoLabourPO) labourList.get(i);
					labourPO1.delete();
					// 修改主表update相关信息 2012-11-16
					RepairOrderPO roCon = RepairOrderPO.findByCompositeKeys(dealerCode, balanceDTO.getRoNo());
					roCon.saveIt();
				}

			}

			// 本次维修项目当中是否在维修建议中存在,如果存在的话从维修建议中逻辑删除(置为无效)
			List<Object> labourParam = new ArrayList<Object>();
			labourParam.add(balanceDTO.getRoNo());
			labourParam.add(dealerCode);
			labourParam.add(CommonConstants.D_KEY);
			labourParam.add(CommonConstants.DICT_IS_NO);
			labourList = DAOUtil.findAll(
					"SELECT * FROM TT_RO_LABOUR WHERE RO_NO = ? AND DEALER_CODE = ? AND D_KEY = ? AND NEEDLESS_REPAIR = ?",
					labourParam);
			if (labourList != null && labourList.size() > 0) {
				for (int i = 0; i < labourList.size(); i++) {
					labourPO = (TtRoLabourPO) labourList.get(i);
					if (labourPO != null && !StringUtils.isNullOrEmpty(balanceDTO.getVin())) {
						List<Object> suggestParam = new ArrayList<Object>();
						suggestParam.add(CommonConstants.D_KEY);
						suggestParam.add(dealerCode);
						suggestParam.add(balanceDTO.getVin());
						suggestParam.add(labourPO.get("LABOUR_CODE"));
						TtSuggestMaintainLabourPO suggestLabourPOCon = (TtSuggestMaintainLabourPO) DAOUtil.findFirst(
								" SELECT * FROM TT_SUGGEST_MAINTAIN_LABOUR WHERE D_KEY = ?  AND DEALER_CODE = ? AND VIN = ? "
										+ "AND LABOUR_CODE = ?  ",
								queryParam);
						suggestLabourPOCon.set("IS_VALID", CommonConstants.DICT_IS_NO);
					}
				}
			}

			TtRoRepairPartPO roRepairPartPO = new TtRoRepairPartPO();
			List<Object> roRepairParam = new ArrayList<Object>();
			roRepairParam.add(balanceDTO.getRoNo());
			roRepairParam.add(dealerCode);
			roRepairParam.add(CommonConstants.D_KEY);
			roRepairParam.add(CommonConstants.DICT_IS_YES);
			List<Map> roRepairPartList = DAOUtil.findAll(
					"SELECT * FROM TT_RO_REPAIR_PART WHERE RO_NO = ? AND DEALER_CODE = ? AND D_KEY = ? AND NEEDLESS_REPAIR = ? ",
					roRepairParam);
			if (roRepairPartList != null && roRepairPartList.size() > 0) {
				for (int i = 0; i < roRepairPartList.size(); i++) {
					roRepairPartPO = (TtRoRepairPartPO) roRepairPartList.get(i);
					if (roRepairPartPO != null) {
						// VIN+配件代码已经存在的话先删掉
						List<Object> suggestMainParam = new ArrayList<Object>();
						suggestMainParam.add(CommonConstants.D_KEY);
						suggestMainParam.add(dealerCode);
						suggestMainParam.add(balanceDTO.getVin());
						suggestMainParam.add(roRepairPartPO.get("PART_NO"));
						suggestMainParam.add(CommonConstants.DICT_IS_YES);
						TtSuggestMaintainPartPO.delete(
								"D_KEY = ?  AND DEALER_CODE = ? AND VIN = ? AND PART_NO = ? AND IS_VALID = ?",
								suggestMainParam);
						TtSuggestMaintainPartPO suggestPartPO = new TtSuggestMaintainPartPO();

						suggestPartPO.set("DEALER_CODE", roRepairPartPO.get("DEALER_CODE"));
						suggestPartPO.set("PART_NAME", roRepairPartPO.get("PART_NAME"));
						suggestPartPO.set("PART_NO", roRepairPartPO.get("PART_NO"));
						suggestPartPO.set("QUANTITY", new Double(roRepairPartPO.getString("QUANTITY")));
						suggestPartPO.set("RO_NO", roRepairPartPO.get("RO_NO"));
						suggestPartPO.set("SALES_PRICE", roRepairPartPO.get("PART_SALES_PRICE"));
						suggestPartPO.setDate("SUGGEST_DATE", new Date());
						suggestPartPO.set("VIN", balanceDTO.getVin());
						suggestPartPO.set("REASON", roRepairPartPO.get("REASON"));
						suggestPartPO.saveIt();
					}
				}

				TtRoRepairPartPO.delete("RO_NO = ? AND DEALER_CODE = ? AND D_KEY = ? AND NEEDLESS_REPAIR = ? ",
						roRepairParam);
				// 修改主表update相关信息
				RepairOrderPO roCon = RepairOrderPO.findByCompositeKeys(dealerCode, balanceDTO.getRoNo());
				roCon.saveIt();
			}

			// 本次维修的配件当中是否在维修建议中存在,如果存在的话从维修建议中逻辑删除(置为无效)
			List<Object> roRepairParam1 = new ArrayList<Object>();
			roRepairParam1.add(balanceDTO.getRoNo());
			roRepairParam1.add(dealerCode);
			roRepairParam1.add(CommonConstants.D_KEY);
			roRepairParam1.add(CommonConstants.DICT_IS_NO);
			roRepairPartList = DAOUtil.findAll(
					"SELECT * FROM TT_RO_REPAIR_PART WHERE RO_NO = ? AND DEALER_CODE = ? AND D_KEY = ? AND NEEDLESS_REPAIR = ? ",
					roRepairParam1);
			if (roRepairPartList != null && roRepairPartList.size() > 0) {
				for (int i = 0; i < roRepairPartList.size(); i++) {
					roRepairPartPO = (TtRoRepairPartPO) roRepairPartList.get(i);
					if (roRepairPartPO != null && !StringUtils.isNullOrEmpty(balanceDTO.getVin())) {
						List<Object> suggestParam1 = new ArrayList<Object>();
						suggestParam1.add(CommonConstants.D_KEY);
						suggestParam1.add(dealerCode);
						suggestParam1.add(balanceDTO.getVin());
						suggestParam1.add(roRepairPartPO.get("PART_NO"));
						TtSuggestMaintainPartPO suggestPartPOCon = TtSuggestMaintainPartPO
								.findFirst("D_KEY = ? AND DEALER_CODE = ? AND VIN = ? AND PART_NO = ? ", suggestParam1);
						suggestPartPOCon.set("IS_VALID", CommonConstants.DICT_IS_NO);
						suggestPartPOCon.saveIt();

					}
				}
			}
		}

		logger.debug("结算完成");
		/**
		 * 校验工单的车主所拆分的实收费用是否大于会员活动的面额
		 */
		// 判断是否包含会员活动项目
		List<Map> balanceList = balanceDTO.getbLDtoList();
		List<Map> repairPartList = balanceDTO.getbRPDtoList();

		if (balanceList.size() > 0) {
			if ((ckeckFieldNotNull2(balanceList, "ACTIVITY_CODE") && ckeckFieldNotNull2(balanceList, "CARD_ID"))
					|| (ckeckFieldNotNull2(repairPartList, "ACTIVITY_CODE")
							&& ckeckFieldNotNull2(repairPartList, "CARD_ID"))) {
				// 会员活动专项资金添加校验
				/**
				 * 步骤1：根据活动编号获取本次结算单中所有会员活动的总金额A 2：根据工单号获取工单车主收费对象的实收总金额B
				 * 3：比较这两个金额 如果A>B 则给出提示"工单车主的收费对象的实收金额不能少于会员活动的总金额";
				 */
				// 结算单中包含只有项目的会员活动，此时以项目活动编号为准
				double activityAmountSum = 0.00;
				List<Map> roActiList = queryRoMemberActivity(roNo);
				// 获取到最终的会员活动编号
				if (roActiList != null && roActiList.size() > 0) {
					for (int z = 0; z < roActiList.size(); z++) {
						String memActivityCode = roActiList.get(z).get("ACTIVITY_CODE").toString();
						String memCardId = roActiList.get(z).get("CARD_ID").toString();
						if (!StringUtils.isNullOrEmpty(memActivityCode)) {
							activityAmountSum += querySumMemberActivity(memActivityCode, memCardId);
						}
					}
					logger.debug("本次维修工单的会员活动总金额为： " + activityAmountSum);
				}

				if (activityAmountSum > 0.00) {
					RepairOrderPO orderPO = new RepairOrderPO();
					List<Object> repairOrParams = new ArrayList<Object>();
					repairOrParams.add(dealerCode);
					repairOrParams.add(balanceDTO.getRoNo());
					repairOrParams.add(CommonConstants.D_KEY);
					List<RepairOrderPO> repairOrderList = RepairOrderPO
							.find("DEALER_CODE = ? AND RO_NO = ?  AND D_KEY = ?", repairOrParams.toArray());
					if (repairOrderList != null && repairOrderList.size() > 0) {
						orderPO = repairOrderList.get(0);
						BalancePayobjPO payobjPO = new BalancePayobjPO();
						List<Object> objParams = new ArrayList<Object>();
						objParams.add(dealerCode);
						objParams.add(balanceDTO.getRoNo());
						objParams.add(orderPO.get("OWNER_NO"));
						objParams.add(CommonConstants.D_KEY);
						objParams.add(balanceNo);
						List<BalancePayobjPO> objList = BalancePayobjPO.find(
								"DEALER_CODE = ? AND RO_NO = ? AND PAYMENT_OBJECT_CODE = ? AND D_KEY = ? AND BALANCE_NO = ?",
								objParams);
						if (objList != null && objList.size() > 0) {
							payobjPO = objList.get(0);
							double realTotalAmount = 0.00;
							if (!StringUtils.isNullOrEmpty(payobjPO.get("RECEIVABLE_AMOUNT"))) {
								realTotalAmount = Double.parseDouble(payobjPO.get("RECEIVABLE_AMOUNT").toString());
								if (realTotalAmount < activityAmountSum) {
									throw new ServiceBizException("工单车主的收费对象的应收金额不能少于会员活动的总金额!");
								} else {
									// 拆分收费对象没问题，将会员活动总金额保存在工单中
									RepairOrderPO reOrPo = RepairOrderPO.findByCompositeKeys(dealerCode,
											balanceDTO.getRoNo());
									reOrPo.set("MEM_ACTI_TOTAL_AMOUNT", activityAmountSum);
									reOrPo.saveIt();
								}
							}
						}
					}
				}
			}
		}

		// 业务描述：维修结算生成凭证
		TtBalanceAccountsPO apo = null;
		if (balanceNo == null || balanceNo.trim().length() < 1) {
			logger.info("balanceNo is null ");
		} else {
			apo = TtBalanceAccountsPO.findByCompositeKeys(balanceNo, dealerCode);
		}

		TtAccountsTransFlowPO po = new TtAccountsTransFlowPO();
		po.set("ORG_CODE", dealerCode);
		po.set("BUSINESS_NO", balanceNo);
		po.set("DEALER_CODE", dealerCode);
		po.setDate("TRANS_DATE", new Date());
		po.set("TRANS_TYPE", CommonConstants.DICT_BUSINESS_TYPE_BALANCE_ACCOUNTS);
		if (apo != null && apo.get("RO_NO") != null && !apo.get("RO_NO").equals(""))
			po.set("SUB_BUSINESS_NO", apo.get("RO_NO"));
		else
			po.set("SUB_BUSINESS_NO", apo.get("SALES_PART_NO"));

		po.set("TAX_AMOUNT", apo.get("TOTAL_AMOUNT"));
		po.set("NET_AMOUNT", apo.get("NET_AMOUNT"));
		po.set("IS_VALID", CommonConstants.DICT_IS_YES);
		po.set("EXEC_NUM", 0);
		//po.set("EXEC_STATUS", CommonConstants.DICT_EXEC_STATUS_NOT_EXEC);
		if (StringUtils.isNullOrEmpty(apo.get("TOTAL_AMOUNT"))) {
			apo.set("TOTAL_AMOUNT",0);
		}
		if ((int) apo.get("TOTAL_AMOUNT") != 0) {
			po.saveIt();
		}
		/**
		 * 新增结算单更新相关数据金额
		 *
		 */
		logger.debug("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA   " + balanceNo);
		List<Object> accountsParams = new ArrayList<Object>();
		String sql = " SELECT * FROM TT_BALANCE_ACCOUNTS WHERE DEALER_CODE = ? AND D_KEY = ? ";
		accountsParams.add(dealerCode);
		accountsParams.add(CommonConstants.D_KEY);
		if (!StringUtils.isNullOrEmpty(balanceNo)) {
			sql += " AND BALANCE_NO = ? ";
			accountsParams.add(balanceNo);
		}
		if (!StringUtils.isNullOrEmpty(balanceDTO.getRoNo())) {
			sql += " AND RO_NO = ?";
			accountsParams.add(balanceDTO.getRoNo());
		}
		if (!StringUtils.isNullOrEmpty(balanceDTO.getSalesPartNo())) {
			sql += " AND SALES_PART_NO = ?";
			accountsParams.add(balanceDTO.getSalesPartNo());
		}
		List<TtBalanceAccountsPO> ttBalanceAccounts = TtBalanceAccountsPO.findBySQL(sql, accountsParams.toArray());
		TtBalanceAccountsPO ttBalanceAccountsPO = ttBalanceAccounts.get(0);
		// 是否含索赔
		String CLAIM_RECORD_COUNT = CommonConstants.DICT_IS_NO;
		boolean chargePartitionCode = false;
		List<Map> listLabour = balanceDTO.getbLDtoList();
		if (listLabour.size() > 0) {
			for (Map map : listLabour) {
				if (!StringUtils.isNullOrEmpty(map.get("CHARGE_PARTITION_CODE"))) {
					if ("S".equals(map.get("CHARGE_PARTITION_CODE"))) {
						CLAIM_RECORD_COUNT = CommonConstants.DICT_IS_YES;
						chargePartitionCode = true;
						break;
					}
				}
			}
		}

		List<Map> listRepair = balanceDTO.getbRPDtoList();
		if (listRepair.size() > 0) {
			for (Map map : listRepair) {
				if (!StringUtils.isNullOrEmpty(map.get("CHARGE_PARTITION_CODE"))) {
					if ("S".equals(map.get("CHARGE_PARTITION_CODE"))) {
						CLAIM_RECORD_COUNT = CommonConstants.DICT_IS_YES;
						chargePartitionCode = true;
						break;
					}
				}
			}
		}

		List<Map> listSales = balanceDTO.getbSPDtoList();
		if (listSales.size() > 0) {
			for (Map map : listSales) {
				if (!StringUtils.isNullOrEmpty(map.get("CHARGE_PARTITION_CODE"))) {
					if ("S".equals(map.get("CHARGE_PARTITION_CODE"))) {
						CLAIM_RECORD_COUNT = CommonConstants.DICT_IS_YES;
						chargePartitionCode = true;
						break;
					}
				}
			}
		}

		List<Map> listAdd = balanceDTO.getbAIDtoList();
		if (listAdd.size() > 0) {
			for (Map map : listAdd) {
				if (!StringUtils.isNullOrEmpty(map.get("CHARGE_PARTITION_CODE"))) {
					if ("S".equals(map.get("CHARGE_PARTITION_CODE"))) {
						CLAIM_RECORD_COUNT = CommonConstants.DICT_IS_YES;
						chargePartitionCode = true;
						break;
					}
				}
			}
		}

		// 工时费(索赔)
		double labourSalesSum = 0;
		// 实收工时费(索赔)
		double labourRealSum = 0;
		if (listLabour.size() > 0) {
			if (ckeckFieldNotNull2(listLabour, "LABOUR_AMOUNT")) {
				for (Map map : listLabour) {
					if (!StringUtils.isNullOrEmpty(map.get("CHARGE_PARTITION_CODE"))) {
						if ("S".equals(map.get("CHARGE_PARTITION_CODE"))) {
							if (!StringUtils.isNullOrEmpty(map.get("LABOUR_AMOUNT"))) {
								labourSalesSum = labourSalesSum + Double.valueOf(map.get("LABOUR_AMOUNT").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("CALC_REAL_RECEIVE_AMOUNT"))) {
								labourRealSum = labourRealSum
										+ Double.valueOf(map.get("CALC_REAL_RECEIVE_AMOUNT").toString());
							}
						}
					}
				}
			}
		}

		// 维修材料成本(索赔)
		double repairPartCostSum = 0;
		// 维修材料费(索赔)
		double repairPartSalesSum = 0;
		// (结算单维修配件)实收金额
		double repairPartRealSum = 0;
		if (listRepair.size() > 0) {
			if (ckeckFieldNotNull2(listRepair, "PART_COST_AMOUNT")) {
				for (Map map : listRepair) {
					if (!StringUtils.isNullOrEmpty(map.get("CHARGE_PARTITION_CODE"))) {
						if ("S".equals(map.get("CHARGE_PARTITION_CODE"))) {
							if (!StringUtils.isNullOrEmpty(map.get("PART_COST_AMOUNT"))) {
								repairPartCostSum = repairPartCostSum
										+ Double.valueOf(map.get("PART_COST_AMOUNT").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT"))) {
								repairPartSalesSum = repairPartSalesSum
										+ Double.valueOf(map.get("PART_SALES_AMOUNT").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("CALC_REAL_RECEIVE_AMOUNT"))) {
								repairPartRealSum = repairPartRealSum
										+ Double.valueOf(map.get("CALC_REAL_RECEIVE_AMOUNT").toString());
							}
						}
					}
				}
			}
		}

		// 销售材料成本(索赔)
		double salesPartCostSum = 0;
		// 销售材料费(索赔)
		double salesPartSalesSum = 0;
		// (结算单销售配件)实收金额
		double salesPartRealSum = 0;
		if (listSales.size() > 0) {
			if (ckeckFieldNotNull2(listSales, "PART_COST_AMOUNT")) {
				for (Map map : listSales) {
					if (!StringUtils.isNullOrEmpty(map.get("CHARGE_PARTITION_CODE"))) {
						if ("S".equals(map.get("CHARGE_PARTITION_CODE"))) {
							if (!StringUtils.isNullOrEmpty(map.get("PART_COST_AMOUNT"))) {
								salesPartCostSum = salesPartCostSum
										+ Double.valueOf(map.get("PART_COST_AMOUNT").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT"))) {
								salesPartSalesSum = salesPartSalesSum
										+ Double.valueOf(map.get("PART_SALES_AMOUNT").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("CALC_REAL_RECEIVE_AMOUNT"))) {
								salesPartRealSum = salesPartRealSum
										+ Double.valueOf(map.get("CALC_REAL_RECEIVE_AMOUNT").toString());
							}
						}
					}
				}
			}
		}

		// 附加项目费（材料）
		double addItemSalesSum = 0;
		// 附加项目费(实收金额)
		double addItemRealSum = 0;
		if (listAdd.size() > 0) {
			if (ckeckFieldNotNull2(listSales, "ADD_ITEM_AMOUNT")) {
				for (Map map : listAdd) {
					if (!StringUtils.isNullOrEmpty(map.get("CHARGE_PARTITION_CODE"))) {
						if ("S".equals(map.get("CHARGE_PARTITION_CODE"))) {
							if (!StringUtils.isNullOrEmpty(map.get("ADD_ITEM_AMOUNT"))) {
								addItemSalesSum = addItemSalesSum
										+ Double.valueOf(map.get("ADD_ITEM_AMOUNT").toString());
							}
							if (!StringUtils.isNullOrEmpty(map.get("CALC_REAL_RECEIVE_AMOUNT"))) {
								addItemRealSum = addItemRealSum
										+ Double.valueOf(map.get("CALC_REAL_RECEIVE_AMOUNT").toString());
							}
						}
					}
				}
			}
		}

		// 结算单辅料管理费(材料)
		double manageOverSalesSum = 0;
		// 结算单辅料管理费(实收金额)
		double manageOverRealSum = 0;

		// 工时费(索赔)
		double labSum = 0;
		// 实收工时费(索赔)
		double realLabSum = 0;
		if (listLabour.size() > 0) {
			if (ckeckFieldNotNull2(listLabour, "LABOUR_AMOUNT")) {
				for (Map map : listLabour) {
					if (!StringUtils.isNullOrEmpty(map.get("LABOUR_AMOUNT"))) {
						labSum = labSum + Double.valueOf(map.get("LABOUR_AMOUNT").toString());
					}
					if (!StringUtils.isNullOrEmpty(map.get("CALC_REAL_RECEIVE_AMOUNT"))) {
						realLabSum = realLabSum + Double.valueOf(map.get("CALC_REAL_RECEIVE_AMOUNT").toString());
					}
				}
			}
		}

		// 维修材料费(索赔)
		double saleParSum = 0;
		// (结算单维修配件)实收金额
		double parRealSum = 0;
		if (listRepair.size() > 0) {
			if (ckeckFieldNotNull2(listRepair, "PART_COST_AMOUNT")) {
				for (Map map : listRepair) {
					if (!StringUtils.isNullOrEmpty(map.get("PART_SALES_AMOUNT"))) {
						saleParSum = saleParSum + Double.valueOf(map.get("PART_SALES_AMOUNT").toString());
					}
					if (!StringUtils.isNullOrEmpty(map.get("CALC_REAL_RECEIVE_AMOUNT"))) {
						parRealSum = parRealSum + Double.valueOf(map.get("CALC_REAL_RECEIVE_AMOUNT").toString());
					}
				}
			}
		}
		
		ttBalanceAccountsPO.set("IS_HAS_CLAIM", CLAIM_RECORD_COUNT);
	    ttBalanceAccountsPO.set("LABOUR_SALES_SUM",labourSalesSum);
	    ttBalanceAccountsPO.set("LABOUR_REAL_SUM",labourRealSum);
	    ttBalanceAccountsPO.set("REPAIR_PART_COST_SUM",repairPartCostSum);
	    ttBalanceAccountsPO.set("REPAIR_PART_SALES_SUM",repairPartSalesSum);
	    ttBalanceAccountsPO.set("REPAIR_PART_REAL_SUM",repairPartRealSum);
	    ttBalanceAccountsPO.set("SALES_PART_COST_SUM",salesPartCostSum);
	    ttBalanceAccountsPO.set("SALES_PART_SALES_SUM",salesPartSalesSum);
	    ttBalanceAccountsPO.set("SALES_PART_REAL_SUM",salesPartRealSum);
	    ttBalanceAccountsPO.set("ADD_ITEM_SALES_SUM",addItemSalesSum);
	    ttBalanceAccountsPO.set("ADD_ITEM_REAL_SUM",addItemRealSum);
	    ttBalanceAccountsPO.set("MANAGE_OVER_SALES_SUM",manageOverSalesSum);
	    ttBalanceAccountsPO.set("MANAGE_OVER_REAL_SUM",manageOverRealSum);
	    ttBalanceAccountsPO.set("LAB_SUM",labSum);
	    ttBalanceAccountsPO.set("REAL_LAB_SUM",realLabSum);
	    ttBalanceAccountsPO.set("SALE_PAR_SUM",saleParSum);
	    ttBalanceAccountsPO.set("PAR_REAL_SUM",parRealSum);
	    ttBalanceAccountsPO.saveIt();
	    
	    
	    String [] tableName = {"TT_BALANCE_LABOUR","TT_BALANCE_REPAIR_PART","TT_BALANCE_SALES_PART","TT_BALANCE_ADD_ITEM"};
		String [] tablePayobj = {"TT_BALANCE_LABOUR_PAYOBJ","TT_BALANCE_REPAIR_PART_PAYOBJ","TT_BALANCE_SALES_PART_PAYOBJ","TT_BALANCE_ADD_ITEM_PAYOBJ"};
			
		
		
		//循环4张表8次(每张表都有索赔和非索赔)
		List<Map> listReceivable = balanceDTO.getReceivableList();
		for (Map map : listReceivable) {
			double receiveAmountLaClaim = 0; //维修项目应收金额（索赔）
			double realReceiveAmountLaClaim = 0; //维修项目实收金额（索赔）
			double discountAmountLaClaim = 0;  //维修项目折让金额（索赔）
			double recLaNotClaim = 0; //维修项目应收金额（非索赔）
			double realRecAmountLaNotClaim = 0 ;//维修项目实收金额（非索赔）
			double discountAmountLaNOtClaim = 0; //维修项目折让金额（非索赔）
			double receiveableAmountRpClaim = 0 ; //维修配件应收金额（索赔）
			double realReceiveAmountRpClaim = 0 ; //维修配件实收金额（索赔）
			double discountAmountRpClaim = 0; //维修配件折让金额（索赔）   
			double partCostAmountRpClaim = 0 ; //维修配件成本金额（索赔）  
			double recAmountRpNotClaim = 0; //  维修配件应收金额（非索赔）
			double realRecAmountRpNotClaim = 0 ; //  维修配件实收金额（非索赔）
			double discountAmounntRpNotClaim = 0; //  维修配件折让金额（非索赔）   
			double PartCostAmountRpNotClaim = 0;  //维修配件成本金额（非索赔）  
			double partCostAmountRp = 0 ; //维修配件成本金额
			double receiveableAmountSpClaim = 0 ; //  销售配件应收金额(索赔)
			double realReceiveAmountSpClaim = 0 ; //  销售配件实收金额(索赔)
			double discountAmountSpClaim = 0; //销售配件折让金额(索赔)
			double partCostAmountSpClaim = 0; // 销售配件成本金额(索赔)
			double recAmountSpNotClaim = 0; //销售配件应收金额(非索赔)
			double realRecAmountSpNotClaim = 0; //销售配件实收金额(非索赔)
			double discountAmountSpNotClaim = 0 ; // 销售配件折让金额(非索赔)
			double partCostAmountSpNotClaim = 0 ; //销售配件成本金额(非索赔)
			double partCostAmountSp = 0; //  销售配件成本金额
			double receiveableAmountAiClaim = 0 ; //附加项目应收金额(索赔)
			double realReceiveAmountAiClaim = 0; //附加项目实收金额(索赔)
			double discountAmountAiClaim = 0; // 附加项目折让金额(索赔)
			double recAmountAiNotClaim = 0; // 附加项目应收金额(非索赔)
			double realRecAmountAiNotClaim = 0 ; //   附加项目实收金额(非索赔)
			double discountAmountAiNotClaim = 0 ; // 附加项目折让金额(非索赔)
			
			for(int i = 0 ; i < tableName.length ; i++){
				//索赔
				List<Map> listClaim=queryPayobjAmountStatiscs(1, tablePayobj[i], tableName[i], balanceNo,map.get("PAYMENT_OBJECT_CODE").toString());
				if(listClaim != null && listClaim.size() > 0){
					for (Map claimMap : listClaim) {
						if(tableName[i].equals("TT_BALANCE_LABOUR")){
							receiveAmountLaClaim = receiveAmountLaClaim + Double.parseDouble(claimMap.get("RECEIVEABLE_AMOUNT").toString());
							realReceiveAmountLaClaim = realReceiveAmountLaClaim + Double.parseDouble(claimMap.get("REAL_RECEIVE_AMOUNT").toString());
							discountAmountLaClaim = discountAmountLaClaim + Double.parseDouble(claimMap.get("DISCOUNT_AMOUNT").toString());
						}
						if(tableName[i].equals("TT_BALANCE_REPAIR_PART")){
							receiveableAmountRpClaim = receiveableAmountRpClaim + Double.parseDouble(claimMap.get("RECEIVEABLE_AMOUNT").toString());
							realReceiveAmountRpClaim = realReceiveAmountRpClaim + Double.parseDouble(claimMap.get("REAL_RECEIVE_AMOUNT").toString());
							discountAmountRpClaim = discountAmountRpClaim + Double.parseDouble(claimMap.get("DISCOUNT_AMOUNT").toString());
							partCostAmountRpClaim = partCostAmountRpClaim + Double.parseDouble(claimMap.get("PART_COST_AMOUNT").toString());
						}
						if(tableName[i].equals("TT_BALANCE_SALES_PART")){
							receiveableAmountSpClaim = receiveableAmountSpClaim +Double.parseDouble(claimMap.get("RECEIVEABLE_AMOUNT").toString());
							realReceiveAmountSpClaim = realReceiveAmountSpClaim +  Double.parseDouble(claimMap.get("REAL_RECEIVE_AMOUNT").toString());
							discountAmountSpClaim = discountAmountSpClaim + Double.parseDouble(claimMap.get("DISCOUNT_AMOUNT").toString());
							partCostAmountSpClaim = partCostAmountSpClaim + Double.parseDouble(claimMap.get("PART_COST_AMOUNT").toString());
						}
						if(tableName[i].equals("TT_BALANCE_ADD_ITEM")){
							receiveableAmountAiClaim = receiveableAmountAiClaim + Double.parseDouble(claimMap.get("RECEIVEABLE_AMOUNT").toString());
							realReceiveAmountAiClaim = realReceiveAmountAiClaim + Double.parseDouble(claimMap.get("REAL_RECEIVE_AMOUNT").toString());
							discountAmountAiClaim = discountAmountAiClaim + Double.parseDouble(claimMap.get("DISCOUNT_AMOUNT").toString());
						}
					}
				}
				//非索赔
				List<Map> listNotClaim = queryPayobjAmountStatiscs(2, tablePayobj[i], tableName[i], balanceNo,map.get("PAYMENT_OBJECT_CODE").toString());
				if(listNotClaim != null && listNotClaim.size() > 0 ){
					for (Map notClaimMap : listNotClaim) {
						if(tableName[i].equals("TT_BALANCE_LABOUR")){
							recLaNotClaim = recLaNotClaim + Double.parseDouble(notClaimMap.get("RECEIVEABLE_AMOUNT").toString());
							realRecAmountLaNotClaim = realRecAmountLaNotClaim + Double.parseDouble(notClaimMap.get("REAL_RECEIVE_AMOUNT").toString());
							discountAmountLaNOtClaim = discountAmountLaNOtClaim + Double.parseDouble(notClaimMap.get("DISCOUNT_AMOUNT").toString());
						}
						if(tableName[i].equals("TT_BALANCE_REPAIR_PART")){
							recAmountRpNotClaim = recAmountRpNotClaim + Double.parseDouble(notClaimMap.get("RECEIVEABLE_AMOUNT").toString());
							realRecAmountRpNotClaim = realRecAmountRpNotClaim + Double.parseDouble(notClaimMap.get("REAL_RECEIVE_AMOUNT").toString());
							discountAmounntRpNotClaim = discountAmounntRpNotClaim + Double.parseDouble(notClaimMap.get("DISCOUNT_AMOUNT").toString());
							PartCostAmountRpNotClaim = PartCostAmountRpNotClaim + Double.parseDouble(notClaimMap.get("PART_COST_AMOUNT").toString());
						}
						if(tableName[i].equals("TT_BALANCE_SALES_PART")){
							recAmountSpNotClaim = recAmountSpNotClaim + Double.parseDouble(notClaimMap.get("RECEIVEABLE_AMOUNT").toString());
							realRecAmountSpNotClaim = realRecAmountSpNotClaim + Double.parseDouble(notClaimMap.get("REAL_RECEIVE_AMOUNT").toString());
							discountAmountSpNotClaim = discountAmountSpNotClaim + Double.parseDouble(notClaimMap.get("DISCOUNT_AMOUNT").toString());
							partCostAmountSpNotClaim = partCostAmountSpNotClaim + Double.parseDouble(notClaimMap.get("PART_COST_AMOUNT").toString());
						}
						if(tableName[i].equals("TT_BALANCE_ADD_ITEM")){
							recAmountAiNotClaim = recAmountAiNotClaim + Double.parseDouble(notClaimMap.get("RECEIVEABLE_AMOUNT").toString());
							realRecAmountAiNotClaim = realRecAmountAiNotClaim + Double.parseDouble(notClaimMap.get("REAL_RECEIVE_AMOUNT").toString());
							discountAmountAiNotClaim = discountAmountAiNotClaim + Double.parseDouble(notClaimMap.get("DISCOUNT_AMOUNT").toString());
						}
					}
				}
				if(tableName[i].equals("TT_BALANCE_REPAIR_PART") || tableName[i].equals("TT_BALANCE_SALES_PART")){
					partCostAmountRp = partCostAmountRpClaim + PartCostAmountRpNotClaim;
					partCostAmountSp = partCostAmountSpClaim + partCostAmountSpNotClaim;
				}			
			}
			
			TmPayobjAmountStatisticsPO amountStatisticsPO = new TmPayobjAmountStatisticsPO();
			amountStatisticsPO.set("DEALER_CODE",dealerCode);
			amountStatisticsPO.set("BALANCE_NO",balanceNo);
			amountStatisticsPO.set("PAYMENT_OBJECT_CODE",map.get("PAYMENT_OBJECT_CODE"));
			amountStatisticsPO.set("PAYMENT_OBJECT_NAME",map.get("PAYMENT_OBJECT_NAME"));
			amountStatisticsPO.set("RECEIVEABLE_AMOUNT_LA_CLAIM",receiveAmountLaClaim);
			amountStatisticsPO.set("REAL_RECEIVE_AMOUNT_LA_CLAIM",realReceiveAmountLaClaim);
			amountStatisticsPO.set("DISCOUNT_AMOUNT_LA_CLAIM",discountAmountLaClaim);
			amountStatisticsPO.set("REC_LA_NOT_CLAIM",recLaNotClaim);
			amountStatisticsPO.set("REAL_REC_AMOUNT_LA_NOT_CLAIM",realRecAmountLaNotClaim);
			amountStatisticsPO.set("DISCOUNT_AMOUNT_LA_NOT_CLAIM",discountAmountLaNOtClaim);
			amountStatisticsPO.set("RECEIVEABLE_AMOUNT_RP_CLAIM",receiveableAmountRpClaim);
			amountStatisticsPO.set("REAL_RECEIVE_AMOUNT_RP_CLAIM",realReceiveAmountRpClaim);
			amountStatisticsPO.set("DISCOUNT_AMOUNT_RP_CLAIM",discountAmountRpClaim);
			amountStatisticsPO.set("PART_COST_AMOUNT_RP_CLAIM",partCostAmountRpClaim);
			amountStatisticsPO.set("REC_AMOUNT_RP_NOT_CLAIM",recAmountRpNotClaim);
			amountStatisticsPO.set("REAL_REC_AMOUNT_RP_NOT_CLAIM",realRecAmountRpNotClaim);
			amountStatisticsPO.set("DISCOUNT_AMOUNT_RP_NOT_CLAIM",discountAmounntRpNotClaim);
			amountStatisticsPO.set("PART_COST_AMOUNT_RP_NOT_CLAIM",PartCostAmountRpNotClaim);
			amountStatisticsPO.set("PART_COST_AMOUNT_RP",partCostAmountRp);
			amountStatisticsPO.set("RECEIVEABLE_AMOUNT_SP_CLAIM",receiveableAmountSpClaim);
			amountStatisticsPO.set("REAL_RECEIVE_AMOUNT_SP_CLAIM",realReceiveAmountSpClaim);
			amountStatisticsPO.set("DISCOUNT_AMOUNT_SP_CLAIM",discountAmountSpClaim);
			amountStatisticsPO.set("PART_COST_AMOUNT_SP_CLAIM",partCostAmountSpClaim);
			amountStatisticsPO.set("REC_AMOUNT_SP_NOT_CLAIM",recAmountSpNotClaim);
			amountStatisticsPO.set("REAL_REC_AMOUNT_SP_NOT_CLAIM",realRecAmountSpNotClaim);
			amountStatisticsPO.set("DISCOUNT_AMOUNT_SP_NOT_CLAIM",discountAmountSpNotClaim);
			amountStatisticsPO.set("PART_COST_AMOUNT_SP_NOT_CLAIM",partCostAmountSpNotClaim);
			amountStatisticsPO.set("PART_COST_AMOUNT_SP",partCostAmountSp);
			amountStatisticsPO.set("RECEIVEABLE_AMOUNT_AI_CLAIM",receiveableAmountAiClaim);
			amountStatisticsPO.set("REAL_RECEIVE_AMOUNT_AI_CLAIM",realReceiveAmountAiClaim);
			amountStatisticsPO.set("DISCOUNT_AMOUNT_AI_CLAIM",discountAmountAiClaim);
			amountStatisticsPO.set("REC_AMOUNT_AI_NOT_CLAIM",recAmountAiNotClaim);
			amountStatisticsPO.set("REAL_REC_AMOUNT_AI_NOT_CLAIM",realRecAmountAiNotClaim);
			amountStatisticsPO.set("DISCOUNT_AMOUNT_AI_NOT_CLAIM",discountAmountAiNotClaim);
			amountStatisticsPO.set("D_KEY",CommonConstants.D_KEY);
			amountStatisticsPO.saveIt();
		}
		
	    
	    

	}

	/**
	 * 配件发料价格修改进行的日志操作
	 * 
	 * @param operateType
	 * @param operator
	 */
	public void handleOperateLog(String content, String remark, int operateType, String operator)
			throws ServiceBizException {
		OperateLogPO logPO = new OperateLogPO();
		logPO.setString("OPERATE_CONTENT", content.trim());
		logPO.setInteger("OPERATE_TYPE", operateType);
		logPO.setString("OPERATOR", operator);
		logPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
		logPO.setString("REMARK", remark);
		logPO.saveIt();
	}
	
	/**
	 * 
	 * @param orderPo
	 * @param isDelivery
	 * @throws ServiceBizException
	 */
	public void setOrderPo(RepairOrderPO orderPo, String isDelivery) throws ServiceBizException {
		orderPo.set("RO_STATUS", DictCodeConstants.DICT_RO_STATUS_TYPE_BALANCED);
		// 是否使用交车开关，如果不使用的话就是自动交车
		if (!isDelivery.equals(DictCodeConstants.DICT_DELIVERY_STATUS_TYPE_YES)) {// 已交车
			if (Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_SUBMIT_CAR))
					.equals(CommonConstants.DICT_IS_NO)) {
				orderPo.set("DELIVERY_USER", FrameworkUtil.getLoginInfo().getUserId());
				orderPo.set("DELIVERY_TAG", DictCodeConstants.DICT_DELIVERY_STATUS_TYPE_YES);
				orderPo.setDate("DELIVERY_DATE", new Date());
			}
		}
	}
	
	/**
	 * 
	 * @param vin
	 * @param tmVehiclePO
	 * @throws ServiceBizException
	 */
	public void updateSubclassPO(String vin, VehiclePO tmVehiclePO) throws ServiceBizException {
		TmVehicleSubclassPO poSub = TmVehicleSubclassPO.findFirst("VIN = ? AND DEALER_CODE = ?", vin,
				FrameworkUtil.getLoginInfo().getDealerCode());
		if (!StringUtils.isNullOrEmpty(poSub)) {
			poSub.setString("CONSULTANT", tmVehiclePO.getString("CONSULTANT"));
			poSub.setInteger("IS_SELF_COMPANY", tmVehiclePO.getInteger("IS_SELF_COMPANY"));
			poSub.setDate("FIRST_IN_DATE", tmVehiclePO.getDate("FIRST_IN_DATE"));
			poSub.setString("CHIEF_TECHNICIAN", tmVehiclePO.getString("CHIEF_TECHNICIAN"));
			poSub.setString("SERVICE_ADVISOR", tmVehiclePO.getString("SERVICE_ADVISOR"));
			poSub.setString("INSURANCE_ADVISOR", tmVehiclePO.getString("INSURANCE_ADVISOR"));
			poSub.setString("MAINTAIN_ADVISOR", tmVehiclePO.getString("MAINTAIN_ADVISOR"));
			poSub.setDate("LAST_MAINTAIN_DATE", tmVehiclePO.getDate("LAST_MAINTAIN_DATE"));
			poSub.setDouble("LAST_MAINTAIN_MILEAGE", tmVehiclePO.getDouble("LAST_MAINTAIN_MILEAGE"));
			poSub.setDate("LAST_MAINTENANCE_DATE", tmVehiclePO.getDate("LAST_MAINTENANCE_DATE"));
			poSub.setDouble("LAST_MAINTENANCE_MILEAGE", tmVehiclePO.getDouble("LAST_MAINTENANCE_MILEAGE"));
			poSub.setDouble("PRE_PAY", tmVehiclePO.getDouble("PRE_PAY"));
			poSub.setDouble("ARREARAGE_AMOUNT", tmVehiclePO.getDouble("ARREARAGE_AMOUNT"));
			poSub.setDate("DISCOUNT_EXPIRE_DATE", tmVehiclePO.getDate("DISCOUNT_EXPIRE_DATE"));
			poSub.setString("DISCOUNT_MODE_CODE", tmVehiclePO.getString("DISCOUNT_MODE_CODE"));
			poSub.setInteger("IS_SELF_COMPANY_INSURANCE", tmVehiclePO.getInteger("IS_SELF_COMPANY_INSURANCE"));
			poSub.setDate("ADJUST_DATE", tmVehiclePO.getDate("ADJUST_DATE"));
			poSub.setString("ADJUSTER", tmVehiclePO.getString("ADJUSTER"));
			poSub.setInteger("IS_VALID", tmVehiclePO.getInteger("IS_VALID"));
			poSub.setString("OWNER_NO", tmVehiclePO.getString("OWNER_NO"));
			poSub.setInteger("NO_VALID_REASON", tmVehiclePO.getInteger("NO_VALID_REASON"));
			poSub.saveIt();
		}
	}
	
	/**
	 * 
	 * @param list
	 * @param field
	 * @return
	 * @throws ServiceBizException
	 */
	public Boolean ckeckFieldNotNull2(List<Map> list, String field)  throws ServiceBizException {
		StringBuffer sb = new StringBuffer("");
		for (Map map : list) {
			if (!StringUtils.isNullOrEmpty(map.get(field))) {
				sb.append(map.get(field)).append(" ");
			}
		}
		if (!"".equals(sb.toString())) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * @param entityCode
	 * @param list
	 * @return
	 * @throws ServiceBizException
	 */
	public static List<VehiclePO> getVehicleSubclassList1(String entityCode, List<VehiclePO> list)  throws ServiceBizException {
		if (list == null)
			return null;

		VehiclePO po = null;
		for (int i = 0; i < list.size(); i++) {
			po = list.get(i);
			Map poSub = new HashMap();
			String sql = "SELECT * FROM TM_VEHICLE_SUBCLASS WHERE DEALER_CODE = '" + entityCode
					+ "' AND MAIN_ENTITY = '" + po.get("DEALER_CODE").toString() + "' AND VIN = '"
					+ po.get("VIN").toString() + "'";
			poSub = DAOUtil.findFirst(sql, null);

			if (poSub != null) {
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "CONSULTANT"))
					po.set("CONSULTANT", poSub.get("CONSULTANT"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "IS_SELF_COMPANY"))
					po.set("IS_SELF_COMPANY", poSub.get("IS_SELF_COMPANY"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "FIRST_IN_DATE"))
					po.set("FIRST_IN_DATE", poSub.get("FIRST_IN_DATE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "CHIEF_TECHNICIAN"))
					po.set("CHIEF_TECHNICIAN", poSub.get("CHIEF_TECHNICIAN"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "SERVICE_ADVISOR"))
					po.set("SERVICE_ADVISOR", poSub.get("SERVICE_ADVISOR"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "INSURANCE_ADVISOR"))
					po.set("INSURANCE_ADVISOR", poSub.get("INSURANCE_ADVISOR"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "MAINTAIN_ADVISOR"))
					po.set("MAINTAIN_ADVISOR", poSub.get("MAINTAIN_ADVISOR"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTAIN_DATE"))
					po.set("LAST_MAINTAIN_DATE", poSub.get("LAST_MAINTAIN_DATE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTAIN_MILEAGE"))
					po.set("LAST_MAINTAIN_MILEAGE", poSub.get("LAST_MAINTAIN_MILEAGE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTENANCE_DATE"))
					po.set("LAST_MAINTENANCE_DATE", poSub.get("LAST_MAINTENANCE_DATE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "LAST_MAINTENANCE_MILEAGE"))
					po.set("LAST_MAINTENANCE_MILEAGE", poSub.get("LAST_MAINTENANCE_MILEAGE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "PRE_PAY"))
					po.set("PRE_PAY", poSub.get("PRE_PAY"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "ARREARAGE_AMOUNT"))
					po.set("ARREARAGE_AMOUNT", poSub.get("ARREARAGE_AMOUNT"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "DISCOUNT_EXPIRE_DATE"))
					po.set("DISCOUNT_EXPIRE_DATE", poSub.get("DISCOUNT_EXPIRE_DATE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "DISCOUNT_MODE_CODE"))
					po.set("DISCOUNT_MODE_CODE", poSub.get("DISCOUNT_MODE_CODE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "IS_SELF_COMPANY_INSURANCE"))
					po.set("IS_SELF_COMPANY_INSURANCE", poSub.get("IS_SELF_COMPANY_INSURANCE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "ADJUST_DATE"))
					po.set("ADJUST_DATE", poSub.get("ADJUST_DATE"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "ADJUSTER"))
					po.set("ADJUSTER", poSub.get("ADJUSTER"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "IS_VALID"))
					po.set("IS_VALID", poSub.get("IS_VALID"));
				if (Utility.isPrivateField(entityCode, "TM_VEHICLE", "NO_VALID_REASON"))
					po.set("NO_VALID_REASON", poSub.get("NO_VALID_REASON"));
			}
		}
		return list;
	}
	
	/**
	 * 
	 * @param roNo
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> queryRoMemberActivity(String roNo)  throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT ACTIVITY_CODE,CARD_ID FROM ( ")
				.append(" SELECT A.ACTIVITY_CODE,A.CARD_ID FROM  TT_RO_REPAIR_PART A  WHERE  A.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND  A.RO_NO='").append(roNo)
				.append("' AND A.ACTIVITY_CODE IS NOT NULL AND A.ACTIVITY_CODE!='' AND A.CARD_ID IS NOT NULL AND A.CARD_ID!=0 ")
				.append(" UNION SELECT B.ACTIVITY_CODE,B.CARD_ID FROM  TT_RO_LABOUR B  WHERE B.DEALER_CODE='")
				.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND   B.RO_NO='").append(roNo)
				.append("' AND B.ACTIVITY_CODE IS NOT NULL AND B.ACTIVITY_CODE!='' AND B.CARD_ID IS NOT NULL AND B.CARD_ID!=0 ")
				.append(") ");
		return DAOUtil.findAll(sb.toString(), null);
	}
	
	/**
	 * 
	 * @param memberActivityCode
	 * @param cardId
	 * @return
	 * @throws ServiceBizException
	 */
	public double querySumMemberActivity(String memberActivityCode, String cardId) throws ServiceBizException  {
		double sumAmount = 0.00;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT (COALESCE(B.MEMBER_ACTIVITY_AMOUNT,0)) AS SUM_ACTIVITY_AMOUNT FROM ("
				+ CommonConstants.VM_MEMBER_CARD_ACTIVITY + ") A INNER JOIN (" + CommonConstants.VM_MEMBER_ACTIVITY
				+ ")  B" + " ON A.DEALER_CODE = B.DEALER_CODE AND A.MEMBER_ACTIVITY_CODE =B.MEMBER_ACTIVITY_CODE WHERE "
				+ "  A.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND A.IS_USE_SPECIAL_FUND = "
				+ CommonConstants.DICT_IS_YES + " " + "  AND A.MEMBER_ACTIVITY_CODE = '" + memberActivityCode
				+ "' AND A.CARD_ID=" + cardId + " " + " ");
		List<Map> list = DAOUtil.findAll(sql.toString(), null);
		if (list.size() > 0) {
			for (Map map : list) {
				if (!StringUtils.isNullOrEmpty(map.get("SUM_ACTIVITY_AMOUNT"))) {
					sumAmount = Double.parseDouble(map.get("SUM_ACTIVITY_AMOUNT").toString());
				}
			}
		}
		return sumAmount;
	}
	
	/**
	 * 
	 * @param tag
	 * @param tablePayobj
	 * @param tableMain
	 * @param balanceNo
	 * @param payobj
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> queryPayobjAmountStatiscs(int tag,String tablePayobj,String tableMain,String balanceNo,String payobj) throws ServiceBizException {
		StringBuffer sql = new StringBuffer("");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		logger.debug("****************************             "+tableMain + "       ***************************************");
		sql
		.append(" SELECT AAA.DEALER_CODE, AAA.BALANCE_NO, AAA.D_KEY, AAA.PAYMENT_OBJECT_CODE, AAA.PAYMENT_OBJECT_NAME, \n " +
				" SUM(AAA.RECEIVEABLE_AMOUNT) 		 AS RECEIVEABLE_AMOUNT,  \n " +
				" SUM(AAA.REAL_RECEIVE_AMOUNT_PAYOBJ) AS REAL_RECEIVE_AMOUNT,  \n " +
				" SUM(AAA.DISCOUNT_AMOUNT_PAYOBJ)     AS DISCOUNT_AMOUNT   \n " ); 
				if(tableMain.equals("TT_BALANCE_SALES_PART") || tableMain.equals("TT_BALANCE_REPAIR_PART")){
					sql.append(" , SUM(AAA.PART_COST_AMOUNT_PAYOBJ)    AS PART_COST_AMOUNT \n ");
				}
				sql.append(" FROM (SELECT   B.*, A.PAYMENT_OBJECT_CODE, A.PAYMENT_OBJECT_NAME, \n " +
				" A.RECEIVEABLE_AMOUNT,  \n " +
				" A.DISCOUNT_AMOUNT     AS DISCOUNT_AMOUNT_PAYOBJ,  \n " +
				" A.REAL_RECEIVE_AMOUNT AS REAL_RECEIVE_AMOUNT_PAYOBJ,  \n " +
				" A.DISCOUNT  AS DISCOUNT_PAYOBJ   \n" );
				if(tableMain.equals("TT_BALANCE_SALES_PART") || tableMain.equals("TT_BALANCE_REPAIR_PART")){
					sql.append(" ,B.PART_COST_AMOUNT*A.REAL_RECEIVE_AMOUNT/(  CASE  WHEN B.REAL_RECEIVE_AMOUNT = 0 THEN 1  ELSE B.REAL_RECEIVE_AMOUNT END) AS PART_COST_AMOUNT_PAYOBJ  \n ");
				}
				sql.append(" FROM "+tablePayobj+" A  \n " +
				" LEFT JOIN "+tableMain+" B \n " +
				" ON A.ITEM_ID = B.ITEM_ID   AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY = B.D_KEY  "); 
				if(tag == 1){
					sql.append(" WHERE  B.CHARGE_PARTITION_CODE = 'S'  ) AAA ");
				}
				if(tag == 2 ){
					sql.append(" WHERE  B.CHARGE_PARTITION_CODE <> 'S'  ) AAA ");
				}
				sql.append(" LEFT JOIN TT_BALANCE_PAYOBJ PAYOBJ ON  AAA.BALANCE_NO = PAYOBJ.BALANCE_NO AND AAA.PAYMENT_OBJECT_CODE = PAYOBJ.PAYMENT_OBJECT_CODE \n " +
				" AND AAA.DEALER_CODE = PAYOBJ.DEALER_CODE AND AAA.D_KEY = PAYOBJ.D_KEY  WHERE AAA.D_KEY = "+CommonConstants.D_KEY+" " );
				if(balanceNo != null && !balanceNo.equals("")){
					sql.append(" AND AAA.BALANCE_NO = '"+balanceNo+"' ");
				}
				if(dealerCode != null && !dealerCode.equals("")){
					sql.append(" AND AAA.DEALER_CODE = '"+dealerCode+"' ");
				}
				if(payobj != null && !payobj.equals("")){
					sql.append(" AND AAA.PAYMENT_OBJECT_CODE = '"+payobj+"' ");
				}
				sql.append(" GROUP BY AAA.DEALER_CODE,  AAA.BALANCE_NO, AAA.D_KEY,  AAA.PAYMENT_OBJECT_CODE,AAA.PAYMENT_OBJECT_NAME ");
		
		
		logger.debug(sql.toString());
		return DAOUtil.findAll(sql.toString(), null);
	}
	
	

}
