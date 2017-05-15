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
* @ClassName: OrderCancelDao 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月10日 下午4:06:20 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class OrderCancelDao extends OemBaseDAO{

	/**
	 * 
	* @Title: findAll 
	* @Description: 订单取消
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto findAll(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getfindAllSql(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String getfindAllSql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TVO.ORDER_ID, -- 订单ID \n");
		sql.append("       TVO.ORDER_NO, -- 订单号 \n");
		sql.append("       CONCAT(CAST(TVO.ORDER_YEAR AS CHAR),'-',CAST(TVO.ORDER_MONTH AS CHAR)) ORDER_YEAR_M , -- 订单年月 \n");
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
		sql.append("       TVO.VIN, -- VIN \n");
		sql.append("       TC.CODE_ID, -- 车辆节点状态常量ID \n");
		sql.append("       VM.BRAND_CODE, -- 品牌代码\n");
		sql.append("       VM.BRAND_NAME, -- 品牌名称\n");
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
		sql.append("       TVO.ORDER_PRICE -- 订单价格 \n");
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
		sql.append(" LEFT JOIN TM_VEHICLE_DEC TV \n");
		sql.append("    ON TV.VIN = TVO.VIN \n");
		sql.append(" LEFT JOIN TC_CODE TC \n");
		sql.append("    ON TV.NODE_STATUS = TC.CODE_ID \n");
		sql.append(" WHERE VM.GROUP_TYPE = "+OemDictCodeConstants.GROUP_TYPE_DOMESTIC+" \n");
		sql.append("   AND ((TVO.ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01+" AND \n");
		sql.append("       (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_05+" OR (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+" AND (TVO.IS_SEND <> "+OemDictCodeConstants.IF_TYPE_YES+" OR TVO.IS_SEND IS NULL)))) -- 指派订单   70031005:已指派  70031006:已确认 \n");
		sql.append("    OR (TVO.ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02+" AND \n");
		sql.append("       (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_04+" OR (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+" AND (TVO.IS_SEND <> "+OemDictCodeConstants.IF_TYPE_YES+" OR TVO.IS_SEND IS NULL)))) -- 紧急订单 70031004:OTD审核通过   70031006:已确认 \n");
		sql.append("    OR (TVO.ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03+"  AND \n");
		sql.append("       (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_05+"  OR (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+" AND (TVO.IS_SEND <> "+OemDictCodeConstants.IF_TYPE_YES+" OR TVO.IS_SEND IS NULL)))) -- 直销订单 70031005:已指派  70031006:已确认 \n");
		sql.append("    OR (TVO.ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04+"  AND \n");
		sql.append("       (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_01+" OR TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_02+" OR \n");
		sql.append("       (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+" AND (TVO.IS_SEND <> "+OemDictCodeConstants.IF_TYPE_YES+" OR TVO.IS_SEND IS NULL)))) -- 常规订单70031001:已提交  70031002:已分周   70031006:已确认并且未发送给SAP \n");
		sql.append("    OR (TVO.ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05+"  AND \n");
		sql.append("       (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_05+" OR \n");
		sql.append("       (TVO.ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+" AND (TVO.IS_SEND <> "+OemDictCodeConstants.IF_TYPE_YES+" OR TVO.IS_SEND IS NULL))))) -- 官网订单 70031005:已指派  70031006:已确认并且未发送给SAP \n");
		//品牌
		if(!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))){
			sql.append(" and VM.BRAND_ID = ?");
			params.add(queryParam.get("brandCode"));
		}
		//车系
		if(!StringUtils.isNullOrEmpty(queryParam.get("seriesName"))){
			sql.append(" and VM.SERIES_ID = ?");
			params.add(queryParam.get("seriesName"));
		}
		//车款
		if(!StringUtils.isNullOrEmpty(queryParam.get("groupName"))){
			sql.append(" and VM.group_ID =  ?");
			params.add(queryParam.get("groupName"));
		}
		//年款
		if(!StringUtils.isNullOrEmpty(queryParam.get("modelYear"))){
			sql.append(" and VM.MODEL_YEAR = ?");
			params.add(queryParam.get("modelYear"));
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
		if(!StringUtils.isNullOrEmpty(queryParam.get("workYearName"))){
			sql.append(" and TVO.ORDER_YEAR  = ?");
			params.add(queryParam.get("workYearName"));
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
		//VIN
		String vinNo = queryParam.get("vinName");
		if(!StringUtils.isNullOrEmpty(vinNo)){
			vinNo = vinNo.replaceAll("\\^", "\n");
			vinNo = vinNo.replaceAll("\\,", "\n");
			sql.append("  "+GetVinUtil.getVins(vinNo, "TVO"));
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
		/*if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.ORDER_DATE = ?");
			sql.append(" and tvo.ORDER_DATE >='" + queryParam.get("lastStockInDateFrom") + "'");
			params.add(queryParam.get(""));
		}*/
		//提报日期  结束
		/*if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.ORDER_DATE = ?");
			params.add(queryParam.get(""));
		}*/
		
		// 提报日期 开始
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
			sql.append(" and tvo.ORDER_DATE >='" + queryParam.get("beginDate") + "'");
		}
		// 提报日期 结束
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sql.append(" and tvo.ORDER_DATE <='" + queryParam.get("endDate") + "'");
		}
		
		//确认日期  开始
		/*if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.DEAL_ORDER_AFFIRM_DATE = ?");
			params.add(queryParam.get(""));
		}*/
		//确认日期  结束
		/*if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.DEAL_ORDER_AFFIRM_DATE = ?");
			params.add(queryParam.get(""));
		}*/
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
			System.err.println(queryParam.get("orderTypeName"));
			
		}
		//订单状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("orderStatusName"))){
			sql.append(" and TVO.ORDER_STATUS = ?");
			params.add(queryParam.get("orderStatusName"));
		}
		sql.append(" ORDER BY TVO.ORDER_WEEK DESC \n");
		
		System.out.println("*******************");
		System.out.println(sql.toString());
		System.out.println("*******************");
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: orderCancelService 
	* @Description: 获取订单取消备注 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> orderCancelService() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder  sql = new StringBuilder();
		sql.append("SELECT CANCEL_REMARKS_NO,CANCEL_REASON_TEXT FROM TM_ORDER_CANCEL_REMARKS WHERE CANCEL_REMARKS_STATUS = 10011001 \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

	
	
}
