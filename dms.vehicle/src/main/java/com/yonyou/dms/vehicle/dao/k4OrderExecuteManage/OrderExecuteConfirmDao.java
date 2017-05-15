package com.yonyou.dms.vehicle.dao.k4OrderExecuteManage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.Util.OemDictCodeConstantsUtils;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.common.domains.PO.salesPlanManage.TtVsOrderHistoryPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 
 * @ClassName: OrderExecuteConfirmDao
 * @Description: JV订单执行管理
 * @author zhengzengliang
 * @date 2017年3月3日 下午5:30:10
 *
 */
@SuppressWarnings("rawtypes")
@Repository
public class OrderExecuteConfirmDao extends OemBaseDAO {

	/**
	 * 
	 * @Title: queryWeek @Description: 订单执行确认 @param @return 设定文件 @return List
	 *         <Map> 返回类型 @throws
	 */
	public List<Map> queryWeek() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR); // 当前年
		int month = c.get(Calendar.MONTH); // 当前月
		StringBuilder sql = new StringBuilder();
		sql.append("select WORK_WEEK  from TM_WORK_WEEK where work_year = " + year + " and work_month = " + (month + 1)
				+ " \n");
		sql.append("  order by WORK_WEEK \n");
		List<Object> params = new ArrayList<Object>();
		// 执行查询操作
		List<Map> result = OemDAOUtil.findAll(sql.toString(), params);
		return result;
	}

	/**
	 * 
	 * @Title: getOrderExecuteConfirmInfoQueryList @Description:
	 *         订单执行确认（查询） @param @param queryParam @param @return 设定文件 @return
	 *         PageInfoDto 返回类型 @throws
	 */
	public PageInfoDto getOrderExecuteConfirmInfoQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getOrderExecuteConfirmInfoQueryListSql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	private String getOrderExecuteConfirmInfoQueryListSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TVO.ORDER_NO, \n");
		sql.append("       TVO.ORDER_TYPE, \n");
		sql.append("       TVO.VIN, \n");
		sql.append("       TVO.ORDER_YEAR, \n");
		sql.append("       TVO.ORDER_MONTH, \n");
		sql.append("       TVO.ORDER_WEEK, \n");
		sql.append("       DATE_FORMAT (TVO.ORDER_DATE, '%Y-%m-%d') ORDER_DATE, \n");
		sql.append(
				"       CONCAT(DATE_FORMAT (TVO.ORDER_DATE, '%Y'),'年',TVO.ORDER_MONTH,'月',TVO.ORDER_WEEK,'周') ORDER_DATE_CONVERT, \n");
		sql.append("       TVO.EC_ORDER_NO, \n");
		sql.append("       VM.BRAND_CODE, \n");
		sql.append("       VM.BRAND_NAME, \n");
		sql.append("       VM.SERIES_CODE, \n");
		sql.append("       VM.SERIES_NAME, \n");
		sql.append("       VM.MODEL_CODE, \n");
		sql.append("       VM.MODEL_NAME, \n");
		sql.append("       VM.MODEL_YEAR, \n");
		sql.append("       VM.GROUP_CODE, \n");
		sql.append("       VM.GROUP_NAME, \n");
		sql.append("       VM.COLOR_CODE, \n");
		sql.append("       VM.COLOR_NAME, \n");
		sql.append("       VM.TRIM_CODE, \n");
		sql.append("       VM.TRIM_NAME, \n");
		sql.append("       TVO.VEHICLE_USE \n");
		sql.append("  FROM TT_VS_ORDER TVO, (" + getVwMaterialSql() + ") VM, TM_DEALER TD \n");
		sql.append(" WHERE TVO.MATERAIL_ID = VM.MATERIAL_ID \n");
		sql.append("   AND TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append("   AND TVO.IS_DEL = '" + OemDictCodeConstants.IS_DEL_00 + "' \n");
		sql.append("   AND VM.GROUP_TYPE = '" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + "' \n");
		String orderType = queryParam.get("orderTypeName");
		if (!StringUtils.isNullOrEmpty(orderType)) {
			if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01.toString().equals(orderType)) {
				// 指派订单，已指派
				sql.append("   AND TVO.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_05 + " \n");
			} else if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02.toString().equals(orderType)) {// 紧急订单
				// 紧急订单，OTD审核通过
				sql.append("   AND TVO.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_04 + " \n");
			} else if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03.toString().equals(orderType)) {// 直销订单
				// 直销订单，已指派
				sql.append("   AND TVO.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_05 + " \n");
			} else if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04.toString().equals(orderType)) {// 常规订单
				// 已常规订单，分周
				sql.append("   AND TVO.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_02 + " \n");
			} else if (OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05.toString().equals(orderType)) {// 商城订单
				// 官网订单，已指派
				sql.append("   AND TVO.ORDER_STATUS = " + OemDictCodeConstants.SALE_ORDER_TYPE_05 + " \n");
			}
			sql.append("   AND TVO.ORDER_TYPE = " + orderType + " \n");
		} else {
			// 排除直销订单
			sql.append("   AND TVO.ORDER_TYPE <> '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03 + "' \n");
			// 指派订单，已指派
			sql.append("   AND (   (TVO.ORDER_TYPE = '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01
					+ "' AND TVO.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_05 + "') \n");
			// 紧急订单，OTD审核通过
			sql.append("        OR (TVO.ORDER_TYPE = '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02
					+ "' AND TVO.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_04 + "') \n");
			// 常规订单，已分周
			sql.append("        OR (TVO.ORDER_TYPE = '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04
					+ "' AND TVO.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_02 + "') \n");
			// 官网订单，已指派
			sql.append("        OR (TVO.ORDER_TYPE = '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05
					+ "' AND TVO.ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_05 + "')) \n");
		}
		// 年
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderYearName"))) {
			sql.append(" and tvo.ORDER_YEAR  = ?");
			params.add(queryParam.get("orderYearName"));
		}
		// 月
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderMonthName"))) {
			sql.append(" and tvo.ORDER_MONTH  = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?)");
			params.add(queryParam.get("orderMonthName"));
		}
		// 周
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderWeekName"))) {
			sql.append(" and tvo.ORDER_WEEK = ?");
			params.add(queryParam.get("orderWeekName"));
		}
		// 提报日期 开始
		if (!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateFrom"))) {
			sql.append(" and tvo.ORDER_DATE >='" + queryParam.get("lastStockInDateFrom") + "'");
		}

		// 提报日期 结束

		if (!StringUtils.isNullOrEmpty(queryParam.get("lastStockInDateTo"))) {
			sql.append(" and tvo.ORDER_DATE <='" + queryParam.get("lastStockInDateTo") + "'");
		}

		// 订单类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderTypeName"))) {
			sql.append(" and tvo.ORDER_TYPE = ?");
			params.add(queryParam.get("orderTypeName"));
		}
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNoName"))) {
			sql.append(" and tvo.ORDER_NO = ?");
			params.add(queryParam.get("orderNoName"));
		}
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandName"))) {
			sql.append(" and VM.BRAND_ID = ?");
			params.add(queryParam.get("brandName"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.SERIES_ID = ?");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelName"))) {
			sql.append(" and VM.MODEL_CODE = ?");
			params.add(queryParam.get("modelName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYearName"))) {
			sql.append(" and VM.MODEL_YEAR = ?");
			params.add(queryParam.get("modelYearName"));
		}
		// 颜色
		if (!StringUtils.isNullOrEmpty(queryParam.get("colorName"))) {
			sql.append(" and VM.COLOR_CODE = ?");
			params.add(queryParam.get("colorName"));
		}
		// 内饰
		if (!StringUtils.isNullOrEmpty(queryParam.get("trimName"))) {
			sql.append(" and VM.TRIM_CODE = ?");
			params.add(queryParam.get("trimName"));
		}
		// 获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!StringUtils.isNullOrEmpty(loginInfo.getDealerId())) {
			sql.append("   AND TD.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		}

		return sql.toString();
	}

	/**
	 * 
	 * @Title: getAddressAndDealerCode @Description:
	 *         订单执行确认（查询运达方式和收货地址） @param @return 设定文件 @return List
	 *         <Map> 返回类型 @throws
	 */
	public List<Map> getAddressAndDealerCode() {
		List<Object> params = new ArrayList<Object>();
		// 获取当前用户
		// LoginInfoDto loginInfo =
		// ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT td.DEALER_CODE, td.ADDRESS FROM tm_dealer td WHERE 1=1 AND td.DEALER_ID = 201308217685510");
		/*
		 * if(!StringUtils.isNullOrEmpty(BigDecimal.valueOf(loginInfo.
		 * getDealerId()))){ sql.append("  AND td.DEALER_ID = ? ");
		 * params.add(BigDecimal.valueOf(loginInfo.getDealerId())); }
		 */
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

	/**
	 * 
	 * @Title: getPaymentList @Description: 订单执行确认（订单附款方式） @param @return
	 *         设定文件 @return List<Map> 返回类型 @throws
	 */
	public List<Map> getPaymentList() {
		// 获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		// List<TmDealerPaymentPO> list = TmDealerPaymentPO
		// .findBySQL(
		// "SELECT tdp.PAYMENT_TYPE FROM tm_dealer_payment tdp WHERE tdp.STATUS
		// = "
		// + OemDictCodeConstants.STATUS_ENABLE + " AND tdp.DEALER_ID = ? ",
		// loginInfo.getDealerId());
		String sql = "SELECT tdp.PAYMENT_TYPE FROM tm_dealer_payment tdp WHERE tdp.STATUS = "
				+ OemDictCodeConstants.STATUS_ENABLE + " AND tdp.DEALER_ID = " + loginInfo.getDealerId() + " ";
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);

		List<Map> paymentList = new ArrayList<Map>();
		for (int z = 0; z < list.size(); z++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("key", OemDictCodeConstantsUtils.getDictDescById(OemDictCodeConstants.K4_PAYMENT.toString(),
					list.get(z).get("PAYMENT_TYPE").toString()));
			map.put("value", list.get(z).get("PAYMENT_TYPE").toString());
			paymentList.add(map);
		}
		return paymentList;
	}

	/**
	 * 
	 * @Title: selectVehicleUse @Description:订单执行确认验证（
	 *         车辆用途不是普通不能使用返利） @param @param ttVsOrderDTO @param @return
	 *         设定文件 @return List<TtVsOrderPO> 返回类型 @throws
	 */
	public List<Map> selectVehicleUse(String orderNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tvo.* FROM tt_vs_order tvo WHERE 1=1 \n");
		if (!StringUtils.isNullOrEmpty(orderNo)) {
			sql.append("  AND tvo.ORDER_NO = '" + orderNo + "'");
		}
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

	/**
	 * 
	 * @Title: checkOrderNoStatus @Description: 校验当前订单是否为可确认状态 @param @param
	 *         order_no @param @param type @param @return 设定文件 @return Integer
	 *         返回类型 @throws
	 */
	public Integer checkOrderNoStatus(String order_no, int type) {
		Integer documentId = 0;
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		if (type == 1) {
			sql.append("  SELECT MAX(FLAG) FLAG FROM ((SELECT COUNT(1) FLAG FROM TT_VS_ORDER WHERE ORDER_NO = '"
					+ order_no + "' \n");
			sql.append(" 	AND ORDER_TYPE IN ('" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01 + "','"
					+ OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03 + "','" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05
					+ "') AND ORDER_STATUS = '" + OemDictCodeConstants.SALE_ORDER_TYPE_05 + "' \n");// 增加
																									// Constant.ORDER_TYPE_DOMESTIC_05-已指派订单(官网订单状态)
			sql.append(" 	) \n");
			sql.append(" 	UNION \n");
			sql.append(" 	(SELECT COUNT(1) FLAG FROM TT_VS_ORDER WHERE ORDER_NO = '" + order_no + "'  \n");
			sql.append(" 	AND ORDER_TYPE = '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02 + "' AND ORDER_STATUS = '"
					+ OemDictCodeConstants.SALE_ORDER_TYPE_04 + "'  \n");
			sql.append(" 	) \n");
			sql.append(" 	UNION \n");
			sql.append(" 	(SELECT COUNT(1) FLAG FROM TT_VS_ORDER WHERE ORDER_NO = '" + order_no + "'  \n");
			sql.append(" 	AND ORDER_TYPE = '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04 + "' AND ORDER_STATUS = '"
					+ OemDictCodeConstants.SALE_ORDER_TYPE_02 + "' ) \n");
			sql.append(")AS t\n");
		} else {
			sql.append(" SELECT COUNT(1) FLAG FROM TT_VS_ORDER WHERE ORDER_NO = '" + order_no + "'  ");
			sql.append(" AND ORDER_TYPE IN (" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04 + ","
					+ OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01 + ")\n");
			sql.append(" AND ORDER_STATUS IN (" + OemDictCodeConstants.SALE_ORDER_TYPE_02 + ","
					+ OemDictCodeConstants.SALE_ORDER_TYPE_05 + "," + OemDictCodeConstants.SALE_ORDER_TYPE_06 + ")\n");
			sql.append(" AND (IS_SEND = " + OemDictCodeConstants.IF_TYPE_NO + " OR IS_SEND IS NULL) \n");
			sql.append(")AS t\n");
		}
		// 执行查询操作
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if (null != list && list.size() > 0) {
			documentId = Integer.valueOf(list.get(0).get("FLAG").toString());
			return documentId;
		}
		return null;
	}

	public List<TmDealerPO> selectTmDealerByDealerCode(String dealerCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT td.* FROM tm_dealer td WHERE 1=1 \n");
		if (!StringUtils.isNullOrEmpty(dealerCode)) {
			sql.append("  AND td.DEALER_CODE = ? ");
			params.add(dealerCode);
		}
		List<TmDealerPO> list = TmDealerPO.findBySQL(sql.toString(), params);
		return list;
	}

	public List<TmDealerPO> selectTmDealerByDealerId(BigDecimal dealerId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT td.* FROM tm_dealer td WHERE 1=1 \n");
		if (!StringUtils.isNullOrEmpty(dealerId)) {
			sql.append("  AND td.DEALER_ID = ? ");
			params.add(dealerId);
		}
		List<TmDealerPO> list = TmDealerPO.findBySQL(sql.toString(), params);
		return list;
	}

	public List<Map> selectVwMaterial(Long materialId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT MG1.GROUP_ID AS BRAND_ID,         MG1.GROUP_CODE AS BRAND_CODE,         MG1.GROUP_NAME AS BRAND_NAME,               MG1.STATUS AS BRAND_STATUS,         MG2.GROUP_ID AS SERIES_ID,         MG2.GROUP_CODE AS SERIES_CODE,               MG2.GROUP_NAME AS SERIES_NAME,         MG2.STATUS AS SERIES_STATUS,         MG2.GROUP_TYPE AS GROUP_TYPE,               MG3.GROUP_ID AS MODEL_ID,         MG3.GROUP_CODE AS MODEL_CODE,         MG3.GROUP_NAME AS MODEL_NAME,               MG3.STATUS AS MODEL_STATUS,         MG3.WX_ENGINE AS WX_ENGINE,           MG3.OILE_TYPE AS OILE_TYPE,                 MG4.GROUP_ID AS GROUP_ID,         MG4.GROUP_CODE AS GROUP_CODE,         MG4.GROUP_NAME AS GROUP_NAME,               MG4.STATUS AS GROUP_STATUS,         MG4.MODEL_YEAR AS MODEL_YEAR,         MG4.FACTORY_OPTIONS AS FACTORY_OPTIONS,               MG4.STANDARD_OPTION AS STANDARD_OPTION,         MG4.LOCAL_OPTION AS LOCAL_OPTION,               MG4.SPECIAL_SERIE_CODE AS SPECIAL_SERIE_CODE,         M.TRIM_CODE,         M.TRIM_NAME,         M.COLOR_CODE,               M.COLOR_NAME,         M.MATERIAL_ID,         M.MATERIAL_CODE,         M.MATERIAL_NAME,         M.IS_SALES,           MG3.MVS AS MVS                  \n");
		sql.append(
				"  FROM TM_VHCL_MATERIAL M,         TM_VHCL_MATERIAL_GROUP_R MGR,         TM_VHCL_MATERIAL_GROUP MG4,         TM_VHCL_MATERIAL_GROUP MG3,         TM_VHCL_MATERIAL_GROUP MG2,         TM_VHCL_MATERIAL_GROUP MG1                                        \n");
		sql.append(
				"  WHERE M.MATERIAL_ID = MGR.MATERIAL_ID     AND MGR.GROUP_ID = MG4.GROUP_ID    AND MG4.PARENT_GROUP_ID = MG3.GROUP_ID     AND MG3.PARENT_GROUP_ID = MG2.GROUP_ID             AND MG2.PARENT_GROUP_ID = MG1.GROUP_ID     AND M.COMPANY_ID = 2010010100070674 AND M.MATERIAL_ID = ?\n");
		params.add(materialId);
		Map map = OemDAOUtil.findFirst(sql.toString(), params);
		List<Map> list = new ArrayList<Map>();
		list.add(map);
		return list;
	}

	public String getEndDate(Integer orderYear, Integer orderWeek) {
		String sql = new String(
				" SELECT  DATE_FORMAT (END_DATE, '%Y-%m-%d') END_DATE FROM TM_WORK_WEEK  WHERE WORK_YEAR = '"
						+ orderYear + "' AND WORK_WEEK = '" + orderWeek + "' AND STATUS = "
						+ OemDictCodeConstants.STATUS_ENABLE + " ");
		Map map = OemDAOUtil.findFirst(sql, null);
		String endDate = map.get("END_DATE").toString();
		return endDate;
	}

	public void insertHistory(Long orderId, Integer saleOrderType06, String orderExecute, String string2, Long userId,
			String userName) {
		TtVsOrderHistoryPO historyPo = new TtVsOrderHistoryPO();
		historyPo.setString("ORDER_ID", orderId.toString());
		historyPo.setString("CHANGE_STATUS", saleOrderType06.toString());
		historyPo.setString("CHANGE_REMARK", orderExecute);
		historyPo.setString("REMARK", string2);
		historyPo.setBigDecimal("CREATE_BY", BigDecimal.valueOf(userId));
		historyPo.setString("CREATE_NAME", userName);
		historyPo.setDate("CREATE_DATE", new Date());
		historyPo.insert();
	}

	/**
	 * 
	 * @Title: updateTtVsOrder @Description: 根据订单号 更新付款方式 是否返利 经销商订单确认时间
	 *         已确认 @param @param paymentType @param @param
	 *         isRebate @param @param orderNo 设定文件 @return void 返回类型 @throws
	 */
	public void updateTtVsOrder(String paymentType, Integer isRebate, String orderNo) {
		TtVsOrderPO.update("PAYMENT_TYPE=? , IS_REBATE =? , DEAL_ORDER_AFFIRM_DATE=? , ORDER_STATUS=?", " ORDER_NO=? ",
				Integer.parseInt(CommonUtils.checkNull(paymentType)), Integer.parseInt(CommonUtils.checkNull(isRebate)),
				new Date(), OemDictCodeConstants.SALE_ORDER_TYPE_06, orderNo);
	}

}
