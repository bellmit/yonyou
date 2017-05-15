package com.yonyou.dms.vehicle.dao.k4OrderExecuteManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.GetVinUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
/**
 * 
* @ClassName: OrderTopMaintainDao 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月9日 下午2:49:56 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class OrderTopMaintainDao extends OemBaseDAO{

	/**
	 * 
	* @Title: queryYear 
	* @Description: 设置置顶维护（获取工作年）
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> queryYear() {
		StringBuilder sql = new StringBuilder();
		sql.append("select distinct  WORK_YEAR from TM_WORK_WEEK  \n");
		sql.append("  order by WORK_YEAR \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

	/**
	 * 
	* @Title: findAll 
	* @Description: 订单置顶维护查询(oem) 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto findAll(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = findAllSQL(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String findAllSQL(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TVO.ORDER_ID, --  订单ID \n");
		sql.append("       TVO.ORDER_NO, --  订单号 \n");
		sql.append("       CONCAT(CAST(TVO.ORDER_YEAR AS CHAR),'年',CAST(TVO.ORDER_MONTH AS CHAR)) ORDER_YEAR_M, -- 订单年月 \n");
		sql.append("       TVO.ORDER_WEEK, -- 执行周 \n");
		sql.append("       TVO.ORDER_TYPE, -- 订单类型常量 \n");
		sql.append("       TVO.EC_ORDER_NO, -- 官网订单号 \n");
		sql.append("       TVO.ORDER_STATUS, -- 订单状态常量 \n");
		sql.append("       TOR2.ORG_NAME ORG_NAME2, -- 大区 \n");
		sql.append("       TOR.ORG_NAME ORG_NAME, -- 小区 \n");
		sql.append("       TD.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       TD.DEALER_SHORTNAME, -- 经销商名称 \n");
		sql.append("       TVO.VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       TVO.IS_TOP, -- 是否置顶 \n");
		sql.append("       TC.CODE_ID, -- 车辆节点状态常量ID \n");
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
		sql.append("       TVO.PAYMENT_TYPE, -- 付款方式 \n");
		sql.append("       DATE_FORMAT(TVO.ORDER_DATE, '%Y-%m-%d') ORDER_DATE, -- 提报日期 \n");
		sql.append("       DATE_FORMAT(TVO.DEAL_ORDER_AFFIRM_DATE, '%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE, -- 确认日期 \n");
		sql.append("       TVO.ORDER_PRICE, -- 订单价格 \n");
		sql.append("       TU.NAME, -- 置顶操作人 \n");
		sql.append("       TVO.TOP_DATE, -- 置顶时间 \n");
		sql.append("       TVO.TOP_CANCEL_DATE -- 取消置顶时间 \n");
		sql.append("  FROM TT_VS_ORDER TVO \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") VM \n");
		sql.append("    ON TVO.MATERAIL_ID = VM.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_DEALER TD \n");
		sql.append("    ON TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR \n");
		sql.append("    ON TD.DEALER_ID = TDOR.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG TOR \n");
		sql.append("    ON TDOR.ORG_ID = TOR.ORG_ID \n");
		sql.append(" INNER JOIN TM_ORG TOR2 \n");
		sql.append("    ON TOR.PARENT_ORG_ID = TOR2.ORG_ID \n");
		sql.append(" LEFT JOIN TC_USER TU \n");
		sql.append("    ON TVO.TOP_BY = TU.USER_ID \n");
		sql.append(" LEFT JOIN TM_VEHICLE_DEC TV \n");
		sql.append("    ON TV.VIN = TVO.VIN \n");
		sql.append(" LEFT JOIN TC_CODE TC \n");
		sql.append("    ON TV.NODE_STATUS = TC.CODE_ID \n");
		
		sql.append(" WHERE VM.GROUP_TYPE = "+OemDictCodeConstants.GROUP_TYPE_DOMESTIC+" \n");
		sql.append("   AND (TVO.IS_SEND = "+OemDictCodeConstants.IF_TYPE_NO+" OR TVO.IS_SEND IS NULL) -- 未发送给SAP \n");
		sql.append("   AND TVO.SO_NO IS NULL  -- SO为空 \n");
		sql.append(" 	AND ((TVO.ORDER_TYPE IN ("+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01+","+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03+","+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05+ ") AND TVO.ORDER_STATUS IN( "+OemDictCodeConstants.SALE_ORDER_TYPE_05+","+OemDictCodeConstants.SALE_ORDER_TYPE_06+"))" +"or(TVO.ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02+" " +"AND TVO.ORDER_STATUS IN( "+OemDictCodeConstants.SALE_ORDER_TYPE_04+","+OemDictCodeConstants.SALE_ORDER_TYPE_06+"))" +"or(TVO.ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04+" AND TVO.ORDER_STATUS IN("+OemDictCodeConstants.SALE_ORDER_TYPE_02+","+OemDictCodeConstants.SALE_ORDER_TYPE_06+"))) \n");
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandName"))){
			sql.append(" and VM.BRAND_ID = ?");
			params.add(queryParam.get("brandName"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and VM.SERIES_ID = ?");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and VM.GROUP_ID = ?");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYearName"))){
			sql.append(" and VM.MODEL_YEAR = ?");
			params.add(queryParam.get("modelYearName"));
		}
		//颜色
		if(!StringUtils.isNullOrEmpty(queryParam.get("colorName"))){
			sql.append(" and VM.COLOR_CODE = ?");
			params.add(queryParam.get("colorName"));
		}
		//内饰
		if(!StringUtils.isNullOrEmpty(queryParam.get("trimName"))){
			sql.append(" and VM.TRIM_CODE = ?");
			params.add(queryParam.get("trimName"));
		}
		//订单年
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderYearName"))){
			sql.append(" and TVO.ORDER_YEAR  = ?");
			params.add(queryParam.get("orderYearName"));
		}
		//订单月
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderMonthName"))){
			sql.append(" and TVO.ORDER_MONTH  = (select tc.CODE_CN_DESC from TC_CODE tc where 1=1 and tc.CODE_ID = ?)");
			params.add(queryParam.get("orderMonthName"));
		}
		//订单周
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderWeekName"))){
			sql.append(" and TVO.ORDER_WEEK = ?");
			params.add(queryParam.get("orderWeekName"));
		}
		//订单号
		String orderNo = queryParam.get("orderNoName");
		if(!StringUtils.isNullOrEmpty(orderNo)){
			orderNo = orderNo.replaceAll("\\^", "\n");
			orderNo = orderNo.replaceAll("\\,", "\n");
			sql.append("  "+GetVinUtil.getOrderNOsAuto(orderNo, "TVO"));
		}
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigOrgName"))){
			sql.append(" and TOR.PARENT_ORG_ID  = ?");
			params.add(queryParam.get("bigOrgName"));
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallOrgName"))){
			sql.append(" and TOR.ORG_ID = ?");
			params.add(queryParam.get("smallOrgName"));
		}
		//付款方式
		if(!StringUtils.isNullOrEmpty(queryParam.get("paymentTypeName"))){
			sql.append(" and TVO.PAYMENT_TYPE = ?");
			params.add(queryParam.get("paymentTypeName"));
		}
		//车辆用途
		if(!StringUtils.isNullOrEmpty(queryParam.get("vehicleUseName"))){
			sql.append(" and TVO.VEHICLE_USE = ?");
			params.add(queryParam.get("vehicleUseName"));
		}
		//提报日期  开始
		if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.ORDER_DATE = ?");
			params.add(queryParam.get(""));
		}
		//提报日期  结束
		if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.ORDER_DATE = ?");
			params.add(queryParam.get(""));
		}
		// 提报日期 开始
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and tvo.ORDER_DATE >='" + queryParam.get("beginDate") + "'");
		}
		// 提报日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append(" and tvo.ORDER_DATE <='" + queryParam.get("endDate") + "'");
		}
		
		//确认日期  开始
		if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.ORDER_DATE = ?");
			params.add(queryParam.get(""));
		}
		//确认日期  结束
		if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.ORDER_DATE = ?");
			params.add(queryParam.get(""));
		}
		// 确认日期 开始
		if (!StringUtils.isNullOrEmpty(queryParam.get("arStartDate"))) {
			sql.append(" and tvo.DEAL_ORDER_AFFIRM_DATE >='" + queryParam.get("arStartDate") + "'");
		}
		// 确认日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("arEndDate"))) {
			sql.append(" and tvo.DEAL_ORDER_AFFIRM_DATE <='" + queryParam.get("arEndDate") + "'");
		}
		
		//选择经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" and td.DEALER_CODE = ?");
			params.add(queryParam.get("dealerCode"));
		}
		//订单类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderTypeName"))){
			sql.append(" and tvo.ORDER_TYPE = ?");
			params.add(queryParam.get("orderTypeName"));
		}
		//是否置顶
		String isTop = queryParam.get("isTopName") ;
		if(!StringUtils.isNullOrEmpty(isTop)){
			if (isTop.equals(OemDictCodeConstants.IF_TYPE_YES.toString())) {
				sql.append("   AND TVO.IS_TOP = "+OemDictCodeConstants.IF_TYPE_YES+"   -- 已置顶\n");
			}else {
				sql.append("   AND( TVO.IS_TOP IS NULL  OR TVO.IS_TOP = "+OemDictCodeConstants.IF_TYPE_NO+" )  -- 未置顶\n");
			}
		}
		System.out.println("**********************");
		System.out.println(sql.toString());
		System.out.println("**********************");
		return sql.toString();
	}

	/**
	 * 
	* @Title: selectTtVsOrderAll 
	* @Description: 设置订单置顶维护 
	* @param @param orderNo
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> selectTtVsOrderAll(String orderNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder  sql = new StringBuilder();
		sql.append("SELECT tvo.*FROM tt_vs_order tvo WHERE 1=1 \n");
		if(!StringUtils.isNullOrEmpty(orderNo)){
			sql.append("  AND tvo.ORDER_NO = '" + orderNo + "'");
		}
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

	/**
	 * 
	* @Title: getFreezeReason 
	* @Description: 
	* @param @param cancelReason
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getFreezeReason(String cancelReason) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder  sql = new StringBuilder();
		sql.append("SELECT CANCEL_REASON_TEXT FROM TM_ORDER_CANCEL_REMARKS WHERE 1=1 AND CANCEL_REMARKS_STATUS = 10011001 and CANCEL_REMARKS_NO = '"+cancelReason+"' ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

}
