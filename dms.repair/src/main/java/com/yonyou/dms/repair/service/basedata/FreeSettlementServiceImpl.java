package com.yonyou.dms.repair.service.basedata;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TmGiftCertificateItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAccountsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAddItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceAddItemPayobjPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceLabourPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceLabourPayobjPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceRepairPartPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBalanceRepairPartPayobjPO;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
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
		accountsPo.set("TAX_AMOUNT_BALANCE", balanceDTO.getTaxAmountBalance());
		accountsPo.set("NET_AMOUNT_BALANCE", balanceDTO.getNetAmountBalance());
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
		accountsPo.set("REMARK_BALANCE", balanceDTO.getRemarkBalance());
		accountsPo.set("REMARK1_BALANCE", balanceDTO.getRemark1Balance());
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
		if (list4.size()>0) {
			TtBalanceAddItemPO addPo = null;
			TtBalanceAddItemPayobjPO addObjPo = null;
			for (Map map4 : list4) {
				addPo = new TtBalanceAddItemPO();
				if(!StringUtils.isNullOrEmpty(balanceNo)){
					addPo.set("BALANCE_NO", balanceNo);
				}
				if(!StringUtils.isNullOrEmpty(dealerCode)){
					addPo.set("DEALER_CODE", dealerCode);
				}
				if(!StringUtils.isNullOrEmpty(map4.get("RO_NO"))){
					addPo.set("RO_NO", map4.get("RO_NO"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("MANAGE_SORT_CODE"))){
					addPo.set("MANAGE_SORT_CODE", map4.get("MANAGE_SORT_CODE"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("CHARGE_PARTITION_CODE"))){
					addPo.set("CHARGE_PARTITION_CODE", map4.get("CHARGE_PARTITION_CODE"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("ADD_ITEM_CODE"))){
					addPo.set("ADD_ITEM_CODE", map4.get("ADD_ITEM_CODE"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("ADD_ITEM_NAME"))){
					addPo.set("ADD_ITEM_NAME", map4.get("ADD_ITEM_NAME"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("ADD_ITEM_AMOUNT"))){
					addPo.set("ADD_ITEM_AMOUNT", map4.get("ADD_ITEM_AMOUNT"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("REMARK"))){
					addPo.set("REMARK", map4.get("REMARK"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("DISCOUNT"))){
					addPo.set("DISCOUNT", map4.get("DISCOUNT"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("DISCOUNT_AMOUNT"))){
					addPo.set("DISCOUNT_AMOUNT", map4.get("CALC_DISCOUNT_AMOUNT"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("REAL_RECEIVE_AMOUNT"))){
					addPo.set("REAL_RECEIVE_AMOUNT", map4.get("CALC_REAL_RECEIVE_AMOUNT"));
				}
				if(!StringUtils.isNullOrEmpty(map4.get("ACTIVITY_CODE"))){
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
					}else {
						continue;
					}
				}
			}
		}
		
		// 新增结算单其他成本
		List<Map> hiddenList5 = balanceDTO.getHiddenList5();
		if (hiddenList5.size()>0) {
		//	TtBalanceOtherCostPO otherPo = null;
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

}
