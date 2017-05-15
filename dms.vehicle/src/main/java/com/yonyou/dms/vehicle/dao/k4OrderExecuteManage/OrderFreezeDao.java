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
* @ClassName: OrderFreezeDao 
* @Description: JV订单执行管理
* @author zhengzengliang 
* @date 2017年3月13日 下午3:42:05 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class OrderFreezeDao extends OemBaseDAO{

	/**
	 * 
	* @Title: orderFreezeQuery 
	* @Description: 订单截停查询
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	public PageInfoDto orderFreezeQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = orderFreezeQuerySQL(queryParam,params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}
	private String orderFreezeQuerySQL(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  TVO.ORDER_NO, CONCAT(TVO.ORDER_YEAR,'-',TVO.ORDER_MONTH) ORDER_YEAR_MONTH,TVO.ORDER_WEEK,ORDER_TYPE.CODE_DESC ORDER_TYPE,TVO.EC_ORDER_NO, \n");
		sql.append("         ORDER_STATUS.CODE_DESC ORDER_STATUS ,ORG2.ORG_NAME BIG_AREA,ORG3.ORG_NAME SMALL_AREA,TD.DEALER_CODE,TD.DEALER_SHORTNAME, \n");
		sql.append("         VM.BRAND_NAME,VM.SERIES_NAME,VM.MODEL_NAME,VM.GROUP_NAME,VM.MODEL_YEAR,VM.TRIM_NAME,VM.COLOR_NAME,VEHICLE_USE.CODE_DESC VEHICLE_USE, \n");
		sql.append("         PAYMENT_TYPE.CODE_DESC PAYMENT_TYPE,TVO.IS_FREEZE,TOR.FREEZE_REASON FREEZE_REASON,DATE_FORMAT(TVO.FREEZE_DATE,'%Y-%m-%d') FREEZE_DATE, \n");
		sql.append("         DATE_FORMAT(TVO.UNFREEZE_DATE,'%Y-%m-%d') UNFREEZE_DATE,DATE_FORMAT(TVO.ORDER_DATE,'%Y-%m-%d') ORDER_DATE, \n");
		sql.append("         DATE_FORMAT(TVO.DEAL_ORDER_AFFIRM_DATE,'%Y-%m-%d') DEAL_ORDER_AFFIRM_DATE \n");
		sql.append(" FROM TT_VS_ORDER TVO \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") VM ON TVO.MATERAIL_ID = VM.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_DEALER TD ON TVO.DEALER_ID = TD.DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TD.DEALER_ID = TDOR.DEALER_ID  \n");
		sql.append(" INNER JOIN TM_ORG ORG3 ON TDOR.ORG_ID = ORG3.ORG_ID  AND ORG3.ORG_LEVEL = 3  \n");
		sql.append(" INNER JOIN TM_ORG ORG2 ON ORG3.PARENT_ORG_ID = ORG2.ORG_ID AND ORG2.ORG_LEVEL = 2 \n");
		sql.append(" LEFT JOIN TC_CODE_DCS ORDER_TYPE ON ORDER_TYPE.CODE_ID = TVO.ORDER_TYPE \n");
		sql.append(" LEFT JOIN TC_CODE_DCS ORDER_STATUS ON ORDER_STATUS.CODE_ID = TVO.ORDER_STATUS \n");
		sql.append(" LEFT JOIN TC_CODE_DCS VEHICLE_USE ON VEHICLE_USE.CODE_ID = TVO.VEHICLE_USE \n");
		sql.append(" LEFT JOIN TC_CODE_DCS PAYMENT_TYPE ON PAYMENT_TYPE.CODE_ID = TVO.PAYMENT_TYPE \n");
		sql.append(" LEFT JOIN TM_ORDERFREEZEREASON TOR ON TOR.FREEZE_CODE = TVO.FREEZE_REASON \n");
		sql.append(" WHERE   ((ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01+" AND ( ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_05+" OR ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+"  ) ) OR \n");
		sql.append("         (ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02+" AND ( ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_04+" OR ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+"  ) ) OR  \n");
		sql.append("         (ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03+" AND ( ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_04+" OR ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+"  ) ) OR \n");
		sql.append("         (ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04+" AND ( ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_01+" OR ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_02+" OR ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+" )) OR \n");
		sql.append("         (ORDER_TYPE = "+OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05+" AND ( ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_05+" OR ORDER_STATUS = "+OemDictCodeConstants.SALE_ORDER_TYPE_06+"  ) ) ) \n");
		sql.append("         AND (IS_SEND <> "+OemDictCodeConstants.IF_TYPE_YES+" OR IS_SEND IS NULL ) \n");
		sql.append(" AND VM.GROUP_TYPE = "+OemDictCodeConstants.GROUP_TYPE_DOMESTIC+" \n");
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
			sql.append(" and VM.group_ID = ?");
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
			sql.append(" and tvo.ORDER_DATE = ?");
			params.add(queryParam.get(""));
		}*/
		//确认日期  结束
		/*if(!StringUtils.isNullOrEmpty(queryParam.get(""))){
			sql.append(" and tvo.ORDER_DATE = ?");
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
		}
		//是否截停
		String isFreeze = queryParam.get("isFreezeName") ;
		if(!StringUtils.isNullOrEmpty(isFreeze)){
			if (isFreeze.equals(OemDictCodeConstants.IF_TYPE_YES.toString())) {
				sql.append(" AND TVO.IS_FREEZE = "+OemDictCodeConstants.IF_TYPE_YES.toString()+"");
			}else {
				sql.append(" AND (TVO.IS_FREEZE <> "+OemDictCodeConstants.IF_TYPE_YES.toString()+" OR  TVO.IS_FREEZE IS NULL )");
			}
		}
	//	sql.append(" ORDER BY TVO.ORDER_WEEK DESC \n");
		System.out.println("*********************");
		System.out.println(sql.toString());
		System.out.println("*********************");
		return sql.toString();
	}
	
	/**
	 * 
	* @Title: getFreezeReason 
	* @Description: 获取冻结原因 
	* @param @param freezeReason
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> getFreezeReason(String freezeReason) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder  sql = new StringBuilder();
		sql.append("SELECT FREEZE_CODE, FREEZE_REASON, STATUS FROM TM_ORDERFREEZEREASON WHERE STATUS = 10011001 AND FREEZE_CODE = '"+freezeReason+"'  ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}
	
	/**
	 * 
	* @Title: findFreezeReason 
	* @Description: 初始化冻结原因 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	public List<Map> findFreezeReason() {
		List<Object> params = new ArrayList<Object>();
		StringBuilder  sql = new StringBuilder();
		sql.append("SELECT FREEZE_CODE, FREEZE_REASON, STATUS FROM TM_ORDERFREEZEREASON WHERE STATUS = 10011001  ");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		return list;
	}

}
