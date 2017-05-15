package com.yonyou.dms.vehicle.dao.k4OrderQueryManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.PO.k4OrderQueryManage.TtVsOrderCheckEPO;

@Repository
public class SalesOrderQueryDao extends OemBaseDAO {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 查询方法
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params, loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * SQL组装
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT DISTINCT TVO.ORDER_ID, --   订单ID \n");
		sql.append("       TVO.ORDER_NO AS ORDER_NO1, --   订单号 \n");
		sql.append("       TVO.ORDER_NO, --   订单号 \n");
		sql.append("      concat  ( TVO.ORDER_YEAR ,'-', TVO.ORDER_MONTH) ORDER_YEAR_M, --  订单年月 \n");
		sql.append("       TVO.ORDER_WEEK, --  执行周 \n");
		sql.append("       TVO.ORDER_TYPE, --  订单类型常量 \n");
		sql.append("       TVO.EC_ORDER_NO, --  官网订单号 \n");
		sql.append("       TVO.ORDER_STATUS, --  订单状态常量 \n");
		sql.append("       TVOA.STATUS  AS OAPLLAYSTATUS,--  订单申请状态常量 \n");
		sql.append("       TOR2.ORG_NAME ORG_NAME2, --  大区 \n");
		sql.append("       TOR.ORG_NAME ORG_NAME, --  小区 \n");
		sql.append("       TD.DEALER_CODE, --  经销商代码 \n");
		sql.append("       TD.DEALER_SHORTNAME, --  经销商名称 \n");
		sql.append("       TVO.VIN, --  VIN \n");
		sql.append("       TVO.VEHICLE_USE, --  车辆用途 \n");
		sql.append("       CASE WHEN TVO.IS_TOP = '10041001' THEN '是' ELSE '否' END AS IS_TOP , --  是否置顶 \n");
		// sql.append(" TC.CODE_ID, -- 车辆节点状态常量ID \n");
		// sql.append(" TC.CODE_DESC, -- 车辆节点状态 \n");
		sql.append("       TV.NODE_STATUS, --  车辆节点状态 \n");
		sql.append("       VM.BRAND_CODE, --  品牌 \n");
		sql.append("       VM.SERIES_CODE, --  车系代码 \n");
		sql.append("       VM.SERIES_NAME, --  车系名称 \n");
		sql.append("       VM.MODEL_CODE, --  CPOS代码 \n");
		sql.append("       VM.MODEL_NAME, --  CPOS名称 \n");
		sql.append("       VM.GROUP_CODE, --  车款代码 \n");
		sql.append("       VM.GROUP_NAME, --  车款名称 \n");
		sql.append("       VM.MODEL_YEAR, --  年款 \n");
		sql.append("       VM.COLOR_CODE, --  颜色代码\n");
		sql.append("       VM.COLOR_NAME, --  颜色 \n");
		sql.append("       VM.TRIM_CODE, --  内饰代码 \n");
		sql.append("       VM.TRIM_NAME, --  内饰 \n");
		sql.append("       TVO.WHOLESALE_PRICE, --  工厂批发价 \n");
		sql.append("       TVO.MSRP_PRICE, --  MSRP价格 \n");
		sql.append("       TVO.PAYMENT_TYPE, --  付款方式 \n");
		sql.append("	   TVO.SO_CR_FAILURE_REASON, --  添加配车失败原因(SO备注)\n");
		sql.append("	   TOCR.CANCEL_REASON_TEXT CANCEL_RESON, --  订单取消备注\n");
		sql.append("       date_format(TVO.ORDER_DATE,'%Y-%m-%d') ORDER_DATE, --  提报日期 \n");
		sql.append("       date_format(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE, --  确认日期 \n");
		sql.append("       date_format(TVO.INVOICE_DATE,'%Y-%m-%d') INVOICE_DATE, --  开票日期 \n");
		sql.append("       date_format(TVOT.DEPARTURE_DATE,'%Y-%m-%d') STOCKOUT_DEALER_DATE, --  发运时间 \n");
		sql.append("       date_format(TVNH.ZPD2_DATE,'%Y-%m-%d') INSPECTION_DATE, --  物流到店日期 \n");
		sql.append("       date_format(TVO.SO_CREATE_DATE,'%Y-%m-%d') SO_CREATE_DATE, --  SO创建时间(扣款时间) \n");
		sql.append(
				"       date_format(TVO.SO_CR_FAILURE_DATE,'%Y-%m-%d  %H:%i:%s') SO_CR_FAILURE_DATE, --  SO创建失败时间 \n");
		sql.append("       date_format(TV.DEALER_STORAGE_DATE,'%Y-%m-%d') ARRIVE_DATE, --  收车时间 \n");
		sql.append("       TVO.SO_NO, --  SO号 \n");
		sql.append("       TVO.INVOICE_NO, --  发票号 \n");
		sql.append("       TVO.ORDER_AMOUNT, --  最终付款金额 \n");
		sql.append("       TVO.IS_SEND,--  订单发送状态 \n");
		sql.append("       date_format(TVO.ORDER_CANCEL_DATE,'%Y-%m-%d %H:%i:%s') ORDER_CANCEL_DATE, --  取消订单时间 \n");
		sql.append("       TVO.IS_FREEZE,--  订单冻结状态 \n");
		sql.append("       TOR3.FREEZE_REASON FREEZE_REASON,--  订单冻结原因 \n");
		sql.append("       date_format(TVO.FREEZE_DATE,'%Y-%m-%d') FREEZE_DATE,--  订单冻结时间 \n");
		sql.append("       date_format(TVO.UNFREEZE_DATE,'%Y-%m-%d') UNFREEZE_DATE,--  订单解除截停时间 \n");
		sql.append("       date_format(TVO.SEND_DATE,'%Y-%m-%d %H:%i:%s') SEND_DATE,--  订单发送时间 \n");
		sql.append("       date_format(TVO.ETA_DATE,'%Y-%m-%d') ETA_DATE,  --  预计到达时间 \n");
		sql.append("       TVO.ASSIGNMENT_REMARK, --  指派订单备注\n");
		sql.append("       TVO.FINANCING_STATUS, --  融资状态\n");
		sql.append("       IFNULL(TD2.DEALER_CODE,TD.DEALER_CODE) R_DEALER_CODE, --  交车经销商代码\n");
		sql.append("       IFNULL(TD2.DEALER_SHORTNAME,TD.DEALER_SHORTNAME) R_DEALER_SHORTNAME --  交车经销商名称\n");
		sql.append("  FROM TT_VS_ORDER TVO \n");
		sql.append(" LEFT JOIN TT_VS_ORDER_ADJUST TVOA \n");
		sql.append("	ON TVOA.ORDER_ID = TVO.ORDER_ID  \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") VM \n");
		sql.append("    ON TVO.MATERAIL_ID = VM.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_DEALER TD \n");
		sql.append("    ON TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR \n");
		sql.append("    ON TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG TOR \n");
		sql.append("    ON TDOR.ORG_ID = TOR.ORG_ID \n");
		sql.append(" INNER JOIN TM_ORG TOR2 \n");
		sql.append("    ON TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append(" LEFT JOIN TM_VEHICLE_DEC TV \n");
		sql.append("    ON TV.VIN = TVO.VIN \n");
		// sql.append(" LEFT JOIN TC_CODE_DCS TC \n");
		// sql.append(" ON TV.NODE_STATUS = TC.CODE_ID \n");
		// sql.append(" LEFT JOIN TT_VS_INSPECTION TVI \n");
		// sql.append(" ON TVI.VEHICLE_ID = TV.VEHICLE_ID \n");
		sql.append(" LEFT JOIN TT_VS_ORDER_TRANS TVOT  \n");
		sql.append("    ON TVO.TRANS_NO  = TVOT.TRANS_NO AND TVO.VIN = TVOT.VIN \n");
		sql.append(" LEFT JOIN TT_VEHICLE_NODE_HISTORY TVNH  \n");
		sql.append("    ON TV.VEHICLE_ID  = TVNH.VEHICLE_ID \n");
		sql.append(" LEFT JOIN TM_ORDERFREEZEREASON TOR3 ON TOR3.FREEZE_CODE = TVO.FREEZE_REASON  \n");

		/* 关联取消备注信息 新增 start xiawei 2016-02-16 */
		sql.append(" LEFT JOIN TM_ORDER_CANCEL_REMARKS TOCR \n");
		sql.append("    ON TOCR.CANCEL_REMARKS_NO = CANCEL_RESON \n");
		/* 关联取消备注信息 新增 end xiawei 2016-02-16 */
		sql.append(" LEFT JOIN TM_DEALER TD2 ON TVO.RECEIVES_DEALER_ID = TD2.DEALER_ID \n");
		sql.append("    where vm.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "\n");
		sql.append("    AND TVO.ORDER_STATUS <> " + OemDictCodeConstants.SALE_ORDER_TYPE_00
				+ " --  紧急订单,常规订单 70031000:已保存\n");
		// 年
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderYear"))) {
			sql.append(" AND TVO.ORDER_YEAR  = ? \n");
			params.add(queryParam.get("orderYear"));
		}
		// 月
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderMonth"))) {
			sql.append(" AND TVO.ORDER_MONTH  = ? \n");
			params.add(queryParam.get("orderMonth"));
		}
		// 周
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderWeek"))) {
			sql.append(" AND TVO.ORDER_WEEK  = ? \n");
			params.add(queryParam.get("orderWeek"));
		}
		// 订单类型 orderType
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderType"))) {
			sql.append(" AND TVO.ORDER_TYPE  = ? \n");
			params.add(queryParam.get("orderType"));
		}
		// 订单状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderStatus1"))
				&& StringUtils.isNullOrEmpty(queryParam.get("orderStatus2"))) {
			sql.append(" AND TVO.ORDER_STATUS  = ? \n");
			params.add(queryParam.get("orderStatus1"));
		}
		if (StringUtils.isNullOrEmpty(queryParam.get("orderStatus1"))
				&& !StringUtils.isNullOrEmpty(queryParam.get("orderStatus2"))) {
			sql.append(" AND TVO.ORDER_STATUS  = ? \n");
			params.add(queryParam.get("orderStatus2"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderStatus1"))
				&& !StringUtils.isNullOrEmpty(queryParam.get("orderStatus2"))) {
			sql.append(" AND TVO.ORDER_STATUS  between ? \n");
			params.add(queryParam.get("orderStatus1"));
			sql.append("AND ? \n");
			params.add(queryParam.get("orderStatus2"));
		}
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" AND VM.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" AND VM.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" AND VM.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" AND VM.MODEL_YEAR  = '" + queryParam.get("modelYear") + "' \n");
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" AND VM.COLOR_CODE  = '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" AND VM.TRIM_CODE  = '" + queryParam.get("trimName") + "' \n");
		}
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			String orderNo = queryParam.get("orderNo");
			orderNo = orderNo.replaceAll("\\^", "\n");
			orderNo = orderNo.replaceAll("\\,", "\n");
			sql.append("  " + getOrderNOsAuto(orderNo, "TVO"));
		}
		// SO号
		if (!StringUtils.isNullOrEmpty(queryParam.get("soNo"))) {
			String soNo = queryParam.get("soNo");
			soNo = soNo.replaceAll("\\^", "\n");
			soNo = soNo.replaceAll("\\,", "\n");
			sql.append("  " + getSoNOsAuto(soNo, "TVO"));
		}
		// VIN号
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vinNo = queryParam.get("vin");
			vinNo = vinNo.replaceAll("\\^", "\n");
			vinNo = vinNo.replaceAll("\\,", "\n");
			sql.append("  " + getVins(vinNo, "TVO"));
		}
		// 销售大区
		if (!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))) {
			sql.append(" AND TOR2.ORG_ID  = ? \n");
			params.add(queryParam.get("bigOrgName"));
		}
		// 销售小区
		if (!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))) {
			sql.append(" AND TOR.ORG_ID  = ? \n");
			params.add(queryParam.get("smallOrgName"));
		}
		// 经销商代码
		if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
			sql.append(" AND TD.DEALER_CODE  = ? \n");
			params.add(queryParam.get("dealerCode"));
		}
		// 节点状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("nodeStatus1"))
				&& StringUtils.isNullOrEmpty(queryParam.get("nodeStatus2"))) {
			sql.append(" AND TV.NODE_STATUS  = ? \n");
			params.add(queryParam.get("nodeStatus1"));
		}
		if (StringUtils.isNullOrEmpty(queryParam.get("nodeStatus1"))
				&& !StringUtils.isNullOrEmpty(queryParam.get("nodeStatus2"))) {
			sql.append(" AND TV.NODE_STATUS  = ? \n");
			params.add(queryParam.get("nodeStatus2"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("nodeStatus1"))
				&& !StringUtils.isNullOrEmpty(queryParam.get("nodeStatus2"))) {
			sql.append(" AND TV.NODE_STATUS  between ? \n");
			params.add(queryParam.get("nodeStatus1"));
			sql.append("AND ? \n");
			params.add(queryParam.get("nodeStatus2"));
		}
		// 付款方式
		if (!StringUtils.isNullOrEmpty(queryParam.get("paymentType"))) {
			sql.append(" AND TVO.PAYMENT_TYPE  = " + queryParam.get("paymentType") + " \n");
			params.add(queryParam.get("paymentType"));
		}
		// 车辆用途
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleUse"))) {
			sql.append(" AND TVO.VEHICLE_USE  = " + queryParam.get("vehicleUse") + " \n");
			// params.add(queryParam.get("vehicleUse"));
		}
		// 提报日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" AND DATE_FORMAT(TVO.ORDER_DATE,'%Y-%m-%d')  >= '" + queryParam.get("beginDate") + "' \n");
			sql.append(" AND DATE_FORMAT(TVO.ORDER_DATE,'%Y-%m-%d')  <= '" + queryParam.get("endDate") + "' \n");
		}
		// 确认日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("arStartDate"))) {
			sql.append("  AND DATE_FORMAT(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d')  >= '" + queryParam.get("arStartDate")
					+ "' \n");
			sql.append("  AND DATE_FORMAT(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d')  <= '" + queryParam.get("arEndDate")
					+ "' \n");
		}
		// ifType 是否置顶
		if (!StringUtils.isNullOrEmpty(queryParam.get("isTop"))) {
			sql.append(" AND TVO.IS_TOP  = " + queryParam.get("isTop") + " \n");
			// params.add(queryParam.get("isTop"));
		}
		// 开票日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("invoiceBeginDate"))) {
			sql.append(" AND DATE_FORMAT(TVO.INVOICE_DATE,'%Y-%m-%d')  >= '" + queryParam.get("invoiceBeginDate")
					+ "' \n");
			sql.append(
					" AND DATE_FORMAT(TVO.INVOICE_DATE,'%Y-%m-%d')  <= '" + queryParam.get("invoiceEndDate") + "' \n");
		}
		// 是否发送isSend
		if (!StringUtils.isNullOrEmpty(queryParam.get("isSend"))) {
			sql.append(" AND TVO.IS_SEND  = " + queryParam.get("isSend") + " \n");
			// params.add(queryParam.get("isSend"));
		}
		// 是否截停 TVO.IS_FREEZE
		if (!StringUtils.isNullOrEmpty(queryParam.get("isfreeze"))) {
			sql.append(" AND TVO.IS_FREEZE  = " + queryParam.get("isfreeze") + " \n");
			// params.add(queryParam.get("isfreeze"));
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * 销售订单信息下载查询
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> querySalesOrderForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getQuerySql(queryParam, params, loginInfo);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;
	}

	/**
	 * 根据ID 获取详细信息
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public Map<String, Object> queryDetail(String id, LoginInfoDto loginInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = getDetailQuerySql(id);
		map = OemDAOUtil.findFirst(sql, null);
		Long orderId = Long.parseLong(map.get("ORDER_ID").toString());
		// OTD审核意见
		TtVsOrderCheckEPO otdOpinionPo = new TtVsOrderCheckEPO();
		otdOpinionPo = otdOpinionPo.findFirst(
				" CHECK_TYPE = '" + OemDictCodeConstants.ORDER_ADJUST_TYPE_01 + "' AND ORDER_ID = ?", orderId);
		if (null != otdOpinionPo) {
			map.put("checkOpinion", otdOpinionPo.getString("CHECK_OPINION"));
		}
		// 小区审核意见
		TtVsOrderCheckEPO districtOpinionPo = new TtVsOrderCheckEPO();
		districtOpinionPo = otdOpinionPo.findFirst(
				" CHECK_TYPE = '" + OemDictCodeConstants.ORDER_ADJUST_TYPE_03 + "' AND ORDER_ID = ?", orderId);
		if (null != districtOpinionPo) {
			map.put("checkOpinion", districtOpinionPo.getString(""));
		}

		return map;
	}

	/**
	 * 获取详细SQL组装
	 * 
	 * @param id
	 * @return
	 */
	private String getDetailQuerySql(Object id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TVO.ORDER_ID, --  订单ID \n");
		sql.append("       TVO.ORDER_NO, --  订单号 \n");
		sql.append("   concat(    TVO.ORDER_YEAR , '-',  TVO.ORDER_MONTH ) ORDER_YEAR_M, --  订单年月 \n");
		sql.append("       TVO.ORDER_WEEK, --  执行周 \n");
		sql.append("       TVO.ORDER_TYPE, --  订单类型常量 \n");
		sql.append("       TVO.ORDER_STATUS, --  订单状态常量 \n");
		sql.append("       TOR2.ORG_NAME ORG_NAME2, --  大区 \n");
		sql.append("       TOR.ORG_NAME ORG_NAME, --  小区 \n");
		sql.append("       TD.DEALER_CODE, --  经销商代码 \n");
		sql.append("       TD.DEALER_SHORTNAME, --  经销商名称 \n");
		sql.append("       RTD.DEALER_CODE R_DEALER_CODE, --  交车经销商代码 \n");
		sql.append("       RTD.DEALER_SHORTNAME R_DEALER_SHORTNAME, --  交车经销商名称 \n");
		sql.append("       TVO.VIN, --  VIN \n");
		sql.append("       TVO.VEHICLE_USE, --  车辆用途 \n");
		sql.append("       TVO.IS_TOP, --  是否置顶 \n");
		sql.append("       TC.CODE_ID, \n");
		sql.append("       TC.CODE_DESC, --  车辆节点状态 \n");
		sql.append("       VM.BRAND_CODE, --  品牌 \n");
		sql.append("       VM.SERIES_CODE, --  车系代码 \n");
		sql.append("       VM.SERIES_NAME, --  车系名称 \n");
		sql.append("       VM.MODEL_CODE, --  CPOS代码 \n");
		sql.append("       VM.MODEL_NAME, --  CPOS名称 \n");
		sql.append("       VM.GROUP_CODE, --  车款代码 \n");
		sql.append("       VM.GROUP_NAME, --  车款名称 \n");
		sql.append("       VM.MODEL_YEAR, --  年款 \n");
		sql.append("       VM.COLOR_CODE, \n");
		sql.append("       VM.COLOR_NAME, --  颜色 \n");
		sql.append("       VM.TRIM_CODE, \n");
		sql.append("       VM.TRIM_NAME, --  内饰 \n");
		sql.append("       TVO.WHOLESALE_PRICE, --  工厂批发价 \n");
		sql.append("       TVO.MSRP_PRICE, --  MSRP价格 \n");
		sql.append("       TVO.PAYMENT_TYPE, --  付款方式 \n");
		// date_format(TVO.DEAL_BOOK_DATE,'%Y-%m-%d')
		sql.append("       date_format(TVO.ORDER_DATE,'%Y-%m-%d') ORDER_DATE, --  提报日期 \n");
		sql.append("       date_format(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE, --  确认日期 \n");
		sql.append("       date_format(TVO.SO_CREATE_DATE,'%Y-%m-%d') SO_CREATE_DATE, --  SO创建时间(扣款时间) \n");
		sql.append("       TVO.SO_NO, --  SO号 \n");
		sql.append("       TVO.DN_NO, --  DN号 \n");
		sql.append("       TVO.ORDER_AMOUNT, --  最终付款金额 \n");
		sql.append("       TVO.IS_SEND, --  订单发送状态 \n");
		sql.append("       TVO.DISCOUNT_CODE, --  折扣代码 \n");
		sql.append("       TVO.DISCOUNT_AMOUNT, --  折扣金额 \n");
		sql.append("       TVO.IS_REBATE, --  是否使用返利 \n");
		sql.append("       date_format(TVO.INVOICE_DATE,'%Y-%m-%d') INVOICE_DATE, --  开票时间 \n");
		sql.append("       date_format(TVO.DELIVER_ORDER_DATE,'%Y-%m-%d') DELIVER_ORDER_DATE, --  发运时间 \n");
		sql.append("       date_format(TVO.ALLOT_WEEK_DATE,'%Y-%m-%d') ALLOT_WEEK_DATE, --  分周时间 \n");
		sql.append("       date_format(TVO.ALLOT_VEHICLE_DATE,'%Y-%m-%d') ALLOT_VEHICLE_DATE, --  配车时间 \n");
		sql.append("       TVO.ADDRESS, --  发运地址 \n");
		sql.append("       TVO.REMARK, --  备注 \n");
		sql.append("       date_format(TVO.SEND_DATE,'%Y-%m-%d') SO_CREATE_DATE, --  订单发送时间 \n");
		sql.append("       date_format(TVO.DEAL_BOOK_DATE,'%Y-%m-%d') DEAL_BOOK_DATE, --  验收入库日期 \n");
		sql.append("	   TVO.SO_CR_FAILURE_REASON, --  添加配车失败原因(SO备注)\n");
		sql.append("	   TVO.CANCEL_RESON, --  订单取消原因\n");
		sql.append("	   TVO.INVOICE_TYPE, --  开票类型\n");
		sql.append("	   TVO.ASSIGNMENT_REMARK --  指派订单备注\n");
		sql.append("  FROM TT_VS_ORDER TVO \n");
		sql.append(" LEFT JOIN (" + getVwMaterialSql() + ") VM \n");
		sql.append("    ON TVO.MATERAIL_ID = VM.MATERIAL_ID \n");
		sql.append(" LEFT JOIN TM_DEALER TD \n");
		sql.append("    ON TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" LEFT JOIN TM_DEALER RTD \n");
		sql.append("    ON TVO.RECEIVES_DEALER_ID = RTD.DEALER_ID \n");
		sql.append(" LEFT JOIN TM_DEALER_ORG_RELATION TDOR \n");
		sql.append("    ON TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append(" LEFT JOIN TM_ORG TOR \n");
		sql.append("    ON TDOR.ORG_ID = TOR.ORG_ID \n");
		sql.append(" LEFT JOIN TM_ORG TOR2 \n");
		sql.append("    ON TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append(" LEFT JOIN TM_VEHICLE_DEC TV \n");
		sql.append("    ON TV.VIN = TVO.VIN \n");
		sql.append(" LEFT JOIN TC_CODE_DCS TC \n");
		sql.append("    ON TV.NODE_STATUS = TC.CODE_ID \n");
		sql.append(" WHERE vm.GROUP_TYPE ='" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		sql.append("   AND TVO.ORDER_NO = '" + id + "' \n");

		logger.debug("厂端/经销商端详细ID查询生成SQL为:" + sql.toString());
		System.out.println(sql.toString());
		return sql.toString();
	}

	/**
	 * ------------------------------------经销商端功能代码-----------------------------
	 * -----------------------------
	 **/

	/**
	 * 销售订单查询(经销商端)
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryDealerList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQueryDealerSql(queryParam, params, loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 订单查询SQL组装(经销商端)
	 * 
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getQueryDealerSql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT TVO.ORDER_ID, -- 订单ID \n");
		sql.append("       TVO.ORDER_NO, -- 订单号 \n");
		sql.append("       TVO.ORDER_NO AS ORDER_NO1, -- 订单号 \n");
		sql.append("       concat(    TVO.ORDER_YEAR , '-',  TVO.ORDER_MONTH ) ORDER_YEAR_M, -- 订单年月 \n");
		sql.append("       TVO.ORDER_WEEK, -- 执行周 \n");
		sql.append("       TVO.ORDER_TYPE, -- 订单类型常量 \n");
		sql.append("       TVO.EC_ORDER_NO, -- 官网订单号 \n");
		sql.append("       TVO.ORDER_STATUS, -- 订单状态常量 \n");
		sql.append("       TVOA.STATUS  AS OAPLLAYSTATUS,-- 订单申请状态常量 \n");
		sql.append("	   TVO.SO_CR_FAILURE_REASON, -- 添加配车失败原因(SO备注)\n");
		sql.append("       TVO.VIN, -- VIN \n");
		sql.append("       TVO.VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       TC.CODE_ID, -- 车辆节点状态常量ID \n");
		sql.append("       TC.CODE_DESC, -- 车辆节点状态 \n");
		sql.append("       VM.BRAND_CODE, -- 品牌 \n");
		sql.append("       VM.SERIES_CODE, -- 车系代码 \n");
		sql.append("       VM.SERIES_NAME, -- 车系名称 \n");
		sql.append("       VM.MODEL_CODE, -- CPOS代码 \n");
		sql.append("       VM.MODEL_NAME, -- CPOS名称 \n");
		sql.append("       VM.GROUP_CODE, -- 车款代码 \n");
		sql.append("       VM.GROUP_NAME, -- 车款名称 \n");
		sql.append("       VM.MODEL_YEAR, -- 年款 \n");
		sql.append("       VM.COLOR_CODE, \n");
		sql.append("       VM.COLOR_NAME, -- 颜色 \n");
		sql.append("       VM.TRIM_CODE, \n");
		sql.append("       VM.TRIM_NAME, -- 内饰 \n");
		sql.append("       TVO.WHOLESALE_PRICE, -- 工厂批发价 \n");
		sql.append("       TVO.MSRP_PRICE, -- MSRP价格 \n");
		sql.append("       TVO.PAYMENT_TYPE, -- 付款方式 \n");
		sql.append("       date_format(TVO.ORDER_DATE,'%Y-%m-%d') ORDER_DATE, --  提报日期 \n");
		sql.append("       date_format(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE, --  确认日期 \n");
		sql.append(
				"       date_format(TVO.SO_CR_FAILURE_DATE,'%Y-%m-%d %H:%i:%s') SO_CR_FAILURE_DATE, --  SO创建失败时间 \n");
		sql.append("       TVO.IS_SEND, -- 订单发送状态 \n");
		sql.append("       TVO.IS_FREEZE,-- 订单冻结状态 \n");
		sql.append("       TVO.FINANCING_STATUS,-- 财务状态 \n");
		sql.append("       TOR.FREEZE_REASON FREEZE_REASON,-- 订单冻结原因 \n");
		sql.append("       date_format(TVO.FREEZE_DATE,'%Y-%m-%d') FREEZE_DATE,--  订单冻结时间 \n");
		sql.append("       date_format(TVO.UNFREEZE_DATE,'%Y-%m-%d') UNFREEZE_DATE,--  订单解除截停时间 \n");
		sql.append("       date_format(TVO.SEND_DATE,'%Y-%m-%d %H:%i:%s') SEND_DATE,--  订单发送时间 \n");
		sql.append("       date_format(TVO.ETA_DATE,'%Y-%m-%d') ETA_DATE  --  预计到达时间 \n");
		sql.append(" FROM TT_VS_ORDER TVO \n");
		sql.append(" LEFT JOIN TT_VS_ORDER_ADJUST TVOA \n");
		sql.append("	ON TVOA.ORDER_ID = TVO.ORDER_ID  \n");
		sql.append(" INNER JOIN (" + getVwMaterialSql() + ") VM \n");
		sql.append("    ON TVO.MATERAIL_ID = VM.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_DEALER TD \n");
		sql.append("    ON TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR \n");
		sql.append("    ON TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append(" LEFT JOIN TM_VEHICLE_DEC TV \n");
		sql.append("    ON TV.VIN = TVO.VIN \n");
		sql.append(" LEFT JOIN TC_CODE_DCS TC \n");
		sql.append("    ON TV.NODE_STATUS = TC.CODE_ID \n");
		sql.append(" LEFT JOIN TM_ORDERFREEZEREASON TOR ON TOR.FREEZE_CODE = TVO.FREEZE_REASON  \n");
		sql.append(" WHERE 1=1 "); // 临时修改
		sql.append(" 	AND vm.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "\n");
		sql.append("    AND TVO.ORDER_STATUS <> " + OemDictCodeConstants.SALE_ORDER_TYPE_00
				+ " -- 紧急订单,常规订单 70031000:已保存\n");
		sql.append("    AND TVO.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		sql.append("	AND TVO.ORDER_TYPE <> " + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03 + " -- 不查询直销订单\n");
		// 年
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderYear"))) {
			sql.append(" and TVO.ORDER_YEAR  = ? \n");
			params.add(queryParam.get("orderYear"));
		}
		// 月
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderMonth"))) {
			sql.append(" and TVO.ORDER_MONTH  = ? \n");
			params.add(queryParam.get("orderMonth"));
		}
		// 周
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderWeek"))) {
			sql.append(" and TVO.ORDER_WEEK  = ? \n");
			params.add(queryParam.get("orderWeek"));
		}
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNo"))) {
			String orderNo = queryParam.get("orderNo");
			orderNo = orderNo.replaceAll("\\^", "\n");
			orderNo = orderNo.replaceAll("\\,", "\n");
			sql.append("  " + getOrderNOsAuto(orderNo, "TVO"));
		}
		// VIN
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			String vinNo = queryParam.get("vin");
			vinNo = vinNo.replaceAll("\\^", "\n");
			vinNo = vinNo.replaceAll("\\,", "\n");
			sql.append("  " + getVins(vinNo, "TVO"));
		}
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and VM.BRAND_ID  = ? \n");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.SERIES_ID  = ? \n");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and VM.GROUP_ID  = ? \n");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and VM.MODEL_YEAR  = '" + queryParam.get("modelYear") + "' \n");
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and VM.COLOR_CODE  = '" + queryParam.get("colorName") + "' \n");
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and VM.TRIM_CODE  = '" + queryParam.get("trimName") + "' \n");
		}
		// 订单状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderStatus1"))
				&& StringUtils.isNullOrEmpty(queryParam.get("orderStatus2"))) {
			sql.append(" and TVO.ORDER_STATUS between ? \n");
			params.add(queryParam.get("orderStatus1"));
			sql.append("and " + OemDictCodeConstants.SALE_ORDER_TYPE_16 + " \n");
		}
		// 订单状态
		if (StringUtils.isNullOrEmpty(queryParam.get("orderStatus1"))
				&& !StringUtils.isNullOrEmpty(queryParam.get("orderStatus2"))) {
			sql.append(" and TVO.ORDER_STATUS  between " + OemDictCodeConstants.SALE_ORDER_TYPE_01 + " \n");
			sql.append(" and  ? \n");
			params.add(queryParam.get("orderStatus2"));
		}
		// 订单状态
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderStatus1"))
				&& !StringUtils.isNullOrEmpty(queryParam.get("orderStatus2"))) {
			sql.append(" and TVO.ORDER_STATUS  between ? \n");
			params.add(queryParam.get("orderStatus1"));
			sql.append(" and ? \n");
			params.add(queryParam.get("orderStatus2"));
		}
		// 订单类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderType"))) {
			sql.append(" and TVO.ORDER_TYPE  = ? \n");
			params.add(queryParam.get("orderType"));
		}
		// 付款方式
		if (!StringUtils.isNullOrEmpty(queryParam.get("payment"))) {
			sql.append(" and TVO.PAYMENT_TYPE  = ? \n");
			params.add(queryParam.get("payment"));
		}
		// 车辆用途
		if (!StringUtils.isNullOrEmpty(queryParam.get("vehicleUse"))) {
			sql.append(" and TVO.VEHICLE_USE  = ? \n");
			params.add(queryParam.get("vehicleUse"));
		}
		// 提报日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append("  AND DATE_FORMAT(TVO.ORDER_DATE,'%Y-%m-%d')  >= '" + queryParam.get("beginDate") + "' \n");
			sql.append("  AND DATE_FORMAT(TVO.ORDER_DATE,'%Y-%m-%d')  <= '" + queryParam.get("endDate") + "' \n");
		}
		// 确认日期
		if (!StringUtils.isNullOrEmpty(queryParam.get("arStartDate"))) {
			sql.append("  AND DATE_FORMAT(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d')  >= '" + queryParam.get("arStartDate")
					+ "' \n");
			sql.append("  AND DATE_FORMAT(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d')  <= '" + queryParam.get("arEndDate")
					+ "' \n");
		}

		logger.debug("经销商端查询生成SQL为:" + sql.toString() + " " + params.toString());

		return sql.toString();
	}

	/**
	 * 根据ID获取详细信息(经销商端)
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public Map<String, Object> queryDealerDetail(String id, LoginInfoDto loginInfo) {
		Map<String, Object> map = new HashMap<String, Object>();
		String sql = getDetailQueryDetailSql(id);
		map = OemDAOUtil.findFirst(sql, null);
		Long orderId = Long.parseLong(map.get("ORDER_ID").toString());
		// OTD审核意见
		TtVsOrderCheckEPO otdOpinionPo = new TtVsOrderCheckEPO();
		otdOpinionPo = otdOpinionPo.findFirst(
				" CHECK_TYPE = '" + OemDictCodeConstants.ORDER_ADJUST_TYPE_01 + "' AND ORDER_ID = ?", orderId);
		if (null != otdOpinionPo) {
			map.put("checkOpinion", otdOpinionPo.getString("CHECK_OPINION"));
		}
		// 小区审核意见
		TtVsOrderCheckEPO districtOpinionPo = new TtVsOrderCheckEPO();
		districtOpinionPo = otdOpinionPo.findFirst(
				" CHECK_TYPE = '" + OemDictCodeConstants.ORDER_ADJUST_TYPE_03 + "' AND ORDER_ID = ?", orderId);
		if (null != districtOpinionPo) {
			map.put("checkOpinion", districtOpinionPo.getString(""));
		}

		return map;
	}

	/**
	 * 临时经销商详细查询SQL组装
	 * 
	 * @param id
	 * @return
	 */
	private String getDetailQueryDetailSql(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TVO.ORDER_ID, --  订单ID \n");
		sql.append("       TVO.ORDER_NO, --  订单号 \n");
		sql.append("      concat(    TVO.ORDER_YEAR , '-',  TVO.ORDER_MONTH ) ORDER_YEAR_M, --  订单年月 \n");
		sql.append("       TVO.ORDER_WEEK, --  执行周 \n");
		sql.append("       TVO.ORDER_TYPE, --  订单类型常量 \n");
		sql.append("       TVO.ORDER_STATUS, --  订单状态常量 \n");
		sql.append("       TOR2.ORG_NAME ORG_NAME2, --  大区 \n");
		sql.append("       TOR.ORG_NAME ORG_NAME, --  小区 \n");
		sql.append("       TD.DEALER_CODE, --  经销商代码 \n");
		sql.append("       TD.DEALER_SHORTNAME, --  经销商名称 \n");
		sql.append("       RTD.DEALER_CODE R_DEALER_CODE, --  交车经销商代码 \n");
		sql.append("       RTD.DEALER_SHORTNAME R_DEALER_SHORTNAME, --  交车经销商名称 \n");
		sql.append("       TVO.VIN, --  VIN \n");
		sql.append("       TVO.VEHICLE_USE, --  车辆用途 \n");
		sql.append("       TVO.IS_TOP, --  是否置顶 \n");
		sql.append("       TC.CODE_ID, \n");
		sql.append("       TC.CODE_DESC, --  车辆节点状态 \n");
		sql.append("       VM.BRAND_CODE, --  品牌 \n");
		sql.append("       VM.SERIES_CODE, --  车系代码 \n");
		sql.append("       VM.SERIES_NAME, --  车系名称 \n");
		sql.append("       VM.MODEL_CODE, --  CPOS代码 \n");
		sql.append("       VM.MODEL_NAME, --  CPOS名称 \n");
		sql.append("       VM.GROUP_CODE, --  车款代码 \n");
		sql.append("       VM.GROUP_NAME, --  车款名称 \n");
		sql.append("       VM.MODEL_YEAR, --  年款 \n");
		sql.append("       VM.COLOR_CODE, \n");
		sql.append("       VM.COLOR_NAME, --  颜色 \n");
		sql.append("       VM.TRIM_CODE, \n");
		sql.append("       VM.TRIM_NAME, --  内饰 \n");
		sql.append("       TVO.WHOLESALE_PRICE, --  工厂批发价 \n");
		sql.append("       TVO.MSRP_PRICE, --  MSRP价格 \n");
		sql.append("       TVO.PAYMENT_TYPE, --  付款方式 \n");
		// date_format(TVO.DEAL_BOOK_DATE,'%Y-%m-%d')
		sql.append("       date_format(TVO.ORDER_DATE,'%Y-%m-%d') ORDER_DATE, --  提报日期 \n");
		sql.append("       date_format(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE, --  确认日期 \n");
		sql.append("       date_format(TVO.SO_CREATE_DATE,'%Y-%m-%d') SO_CREATE_DATE, --  SO创建时间(扣款时间) \n");
		sql.append("       TVO.SO_NO, --  SO号 \n");
		sql.append("       TVO.DN_NO, --  DN号 \n");
		sql.append("       TVO.ORDER_AMOUNT, --  最终付款金额 \n");
		sql.append("       TVO.IS_SEND, --  订单发送状态 \n");
		sql.append("       TVO.DISCOUNT_CODE, --  折扣代码 \n");
		sql.append("       TVO.DISCOUNT_AMOUNT, --  折扣金额 \n");
		sql.append("       TVO.IS_REBATE, --  是否使用返利 \n");
		sql.append("       date_format(TVO.INVOICE_DATE,'%Y-%m-%d') INVOICE_DATE, --  开票时间 \n");
		sql.append("       date_format(TVO.DELIVER_ORDER_DATE,'%Y-%m-%d') DELIVER_ORDER_DATE, --  发运时间 \n");
		sql.append("       date_format(TVO.ALLOT_WEEK_DATE,'%Y-%m-%d') ALLOT_WEEK_DATE, --  分周时间 \n");
		sql.append("       date_format(TVO.ALLOT_VEHICLE_DATE,'%Y-%m-%d') ALLOT_VEHICLE_DATE, --  配车时间 \n");
		sql.append("       TVO.ADDRESS, --  发运地址 \n");
		sql.append("       TVO.REMARK, --  备注 \n");
		sql.append("       date_format(TVO.SEND_DATE,'%Y-%m-%d') SO_CREATE_DATE, --  订单发送时间 \n");
		sql.append("       date_format(TVO.DEAL_BOOK_DATE,'%Y-%m-%d') DEAL_BOOK_DATE, --  验收入库日期 \n");
		sql.append("	   TVO.SO_CR_FAILURE_REASON, --  添加配车失败原因(SO备注)\n");
		sql.append("	   TVO.CANCEL_RESON, --  订单取消原因\n");
		sql.append("	   TVO.INVOICE_TYPE, --  开票类型\n");
		sql.append("	   TVO.ASSIGNMENT_REMARK --  指派订单备注\n");
		sql.append("  FROM TT_VS_ORDER TVO \n");
		sql.append(" LEFT JOIN (" + getVwMaterialSql() + ") VM \n");
		sql.append("    ON TVO.MATERAIL_ID = VM.MATERIAL_ID \n");
		sql.append(" LEFT JOIN TM_DEALER TD \n");
		sql.append("    ON TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" LEFT JOIN TM_DEALER RTD \n");
		sql.append("    ON TVO.RECEIVES_DEALER_ID = RTD.DEALER_ID \n");
		sql.append(" LEFT JOIN TM_DEALER_ORG_RELATION TDOR \n");
		sql.append("    ON TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append(" LEFT JOIN TM_ORG TOR \n");
		sql.append("    ON TDOR.ORG_ID = TOR.ORG_ID \n");
		sql.append(" LEFT JOIN TM_ORG TOR2 \n");
		sql.append("    ON TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append(" LEFT JOIN TM_VEHICLE_DEC TV \n");
		sql.append("    ON TV.VIN = TVO.VIN \n");
		sql.append(" LEFT JOIN TC_CODE_DCS TC \n");
		sql.append("    ON TV.NODE_STATUS = TC.CODE_ID \n");
		sql.append(" WHERE 1=1 ");
		/**
		 * 
		 * sql.append(" WHERE vm.GROUP_TYPE ='"
		 * +OemDictCodeConstants.GROUP_TYPE_DOMESTIC+"' \n");
		 */
		sql.append("   AND TVO.ORDER_NO = '" + id + "' \n");

		logger.debug("经销商端ID明细查询生成SQL为:" + sql.toString());

		return sql.toString();
	}

	/**
	 * 经销商下载查询(经销商端)
	 * 
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map> queryDealerSalesOrderForExport(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<>();
		String sql = getQueryDealerSql(queryParam, params, loginInfo);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;
	}

	public PageInfoDto orderRecords(String orderId, LoginInfoDto loginInfo) {
		// 历史订单记录查询
		String sql = new String();
		sql = " SELECT TC.CODE_DESC ,HIS.CHANGE_REMARK,HIS.REMARK,HIS.CREATE_NAME NAME,DATE_FORMAT(HIS.CREATE_DATE,'%Y-%m-%d %H:%i:%s') CREATE_DATE "
				+ " FROM TT_VS_ORDER_HISTORY HIS " + " LEFT JOIN TC_CODE_DCS TC ON  HIS.CHANGE_STATUS = TC.CODE_ID "
				+ " WHERE HIS.ORDER_ID = " + orderId;
		String dutyType = loginInfo.getDutyType();
		if (dutyType.equals(OemDictCodeConstants.DUTY_TYPE_DEALER.toString())
				|| dutyType.equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
			sql += " AND CHANGE_REMARK NOT IN('订单置顶','订单解除置顶') ";
		}
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), null);
		return pageInfoDto;
	}

}
