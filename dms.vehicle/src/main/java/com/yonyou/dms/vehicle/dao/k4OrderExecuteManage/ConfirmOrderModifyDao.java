package com.yonyou.dms.vehicle.dao.k4OrderExecuteManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 
 * @ClassName: ConfirmOrderModifyDao
 * @Description: JV订单执行管理
 * @author zhengzengliang
 * @date 2017年3月8日 下午5:18:10
 *
 */
@SuppressWarnings("rawtypes")
@Repository
public class ConfirmOrderModifyDao extends OemBaseDAO {

	/**
	 * 
	 * @Title: getConfirmOrderInfoQueryList @Description: 确认订单修改 @param @param
	 *         queryParam @param @return 设定文件 @return PageInfoDto 返回类型 @throws
	 */
	public PageInfoDto getConfirmOrderInfoQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getConfirmOrderInfoQueryListSql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	private String getConfirmOrderInfoQueryListSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"select tvo.ORDER_NO, tvo.ORDER_TYPE,tvo.VIN,tvo.ORDER_YEAR,tvo.ORDER_MONTH,tvo.ORDER_WEEK,DATE_FORMAT(tvo.ORDER_DATE, '%Y-%m-%d') ORDER_DATE,CONCAT(DATE_FORMAT (TVO.ORDER_DATE, '%Y'),'年',TVO.ORDER_MONTH,'月',TVO.ORDER_WEEK,'周') ORDER_DATE_CONVERT,tvo.EC_ORDER_NO,vm.BRAND_ID,vm.BRAND_CODE,vm.BRAND_NAME, \n");
		sql.append(
				"       vm.SERIES_CODE,vm.SERIES_ID,vm.SERIES_NAME,vm.MODEL_CODE,vm.MODEL_NAME,vm.MODEL_YEAR,vm.GROUP_CODE,vm.GROUP_CODE,vm.GROUP_ID,vm.GROUP_NAME,vm.COLOR_CODE,vm.COLOR_NAME, \n");
		sql.append(
				"       vm.TRIM_CODE,vm.TRIM_NAME,tvo.VEHICLE_USE,tvo.PAYMENT_TYPE,tvo.IS_REBATE,tvo.SO_CR_FAILURE_REASON \n");
		sql.append("from tt_vs_order tvo, (" + getVwMaterialSql() + ") vm, TM_DEALER td \n");
		sql.append("where tvo.MATERAIL_ID = vm.MATERIAL_ID \n");
		sql.append("       and vm.GROUP_TYPE=" + OemDictCodeConstants.GROUP_TYPE_DOMESTIC + " \n");
		sql.append("and   tvo.DEALER_ID = td.DEALER_ID \n");
		sql.append("       and tvo.IS_DEL =" + OemDictCodeConstants.IS_DEL_00 + " \n");
		sql.append("       and  tvo.IS_SEND  =" + OemDictCodeConstants.IF_TYPE_NO + " \n");
		sql.append("       and tvo.ORDER_STATUS =" + OemDictCodeConstants.SALE_ORDER_TYPE_06 + " \n");
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
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and tvo.ORDER_DATE >='" + queryParam.get("beginDate") + "'");
		}

		// 提报日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append(" and tvo.ORDER_DATE <='" + queryParam.get("endDate") + "'");
		}

		// 订单类型
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderTypeName"))) {
			sql.append(" and tvo.ORDER_TYPE = ?");
			params.add(queryParam.get("orderTypeName"));
		} else {
			// 排除直销订单
			sql.append(" and tvo.ORDER_TYPE <> " + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03 + "\n");
		}
		// 订单号
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderNoName"))) {
			sql.append(" and tvo.ORDER_NO = ?");
			params.add(queryParam.get("orderNoName"));
		}
		// 品牌
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
			sql.append(" and VM.BRAND_ID = ?");
			params.add(queryParam.get("brandCode"));
		}
		// 车系
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))) {
			sql.append(" and VM.SERIES_ID = ?");
			params.add(queryParam.get("seriesName"));
		}
		// 车款
		if (!StringUtils.isNullOrEmpty(queryParam.get("groupName"))) {
			sql.append(" and VM.GROUP_ID = ?");
			params.add(queryParam.get("groupName"));
		}
		// 年款
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))) {
			sql.append(" and VM.MODEL_YEAR = ?");
			params.add(queryParam.get("modelYear"));
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
	 * @Title: selectOrderId @Description: 确认订单修改 @param @param
	 *         orderNo @param @return 设定文件 @return List<Map> 返回类型 @throws
	 */
	public List<Map> selectOrderId(String orderNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tvo.* FROM tt_vs_order tvo WHERE 1=1 \n");
		if (!StringUtils.isNullOrEmpty(orderNo)) {
			sql.append("  AND tvo.ORDER_NO = '" + orderNo + "'");
		}
		if (!StringUtils.isNullOrEmpty(orderNo)) {
			sql.append("  AND tvo.IS_SEND = " + OemDictCodeConstants.IF_TYPE_NO);
		}
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

	/**
	 * 
	 * @Title: selectTiK4VsOrder @Description: @param @param
	 *         orderNo @param @return 设定文件 @return List<Map> 返回类型 @throws
	 */
	public List<Map> selectTiK4VsOrder(String orderNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT tkvo.* FROM ti_k4_vs_order tkvo WHERE 1=1 \n");
		if (!StringUtils.isNullOrEmpty(orderNo)) {
			sql.append("  AND tkvo.ORDER_NO = '" + orderNo + "'");
		}
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

	/**
	 * 
	 * @Title: queryWeek @Description: 确认订单修改（周列表） @param @param
	 *         queryParam @param @return 设定文件 @return List<Map> 返回类型 @throws
	 */
	public List<Map> queryWeek(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append("select WORK_WEEK  from TM_WORK_WEEK where 1=1 \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("orderYearId"))) {
			sql.append("  AND work_year = ? \n");
			params.add(queryParam.get("orderYearId"));
		}
		if (queryParam.get("orderMonthId") != null) {
			List<Map> monthList = OemDAOUtil
					.findAll("select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = "
							+ queryParam.get("orderMonthId"), null);
			if (monthList.size() > 0) {
				Integer month = Integer.valueOf(monthList.get(0).get("CODE_CN_DESC").toString());
				if (!StringUtils.isNullOrEmpty(month)) {
					sql.append("  AND work_month = ? \n");
					params.add((month));
				}
			}
		}
		sql.append("  order by WORK_WEEK \n");
		// 执行查询操作
		List<Map> result = OemDAOUtil.findAll(sql.toString(), params);
		return result;
	}

}
