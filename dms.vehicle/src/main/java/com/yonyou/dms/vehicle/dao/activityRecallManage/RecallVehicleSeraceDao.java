package com.yonyou.dms.vehicle.dao.activityRecallManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujm
* @date 2017年4月24日
*/
@SuppressWarnings("all")

@Repository
public class RecallVehicleSeraceDao extends OemBaseDAO{
	
	
	
	
	/**
	 * 召回车辆查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getRecallVehicleSeraceQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	
	
	
	
	/**
	 * 召回车辆下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getRecallVehicleSeraceDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	
	
	
	
	
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT \n");
		sql.append("  trv.VIN,                #vin \n");
		sql.append("  trs.RECALL_ID,          #召回编号 \n");
		sql.append("  trs.RECALL_NO,          #召回编号 \n");
		sql.append("  trs.RECALL_NAME,        #召回名称 \n");
		sql.append("  DATE_FORMAT(trs.RECALL_START_DATE,'%Y-%c-%d') RECALL_START_DATE,    #召回开始日期 \n");
		sql.append("  DATE_FORMAT(trs.RECALL_END_DATE,'%Y-%c-%d') RECALL_END_DATE,        #召回结束日期 \n");
		sql.append("  tor2.ORG_NAME BIG_AREA,     #大区 \n");
		sql.append("  tor.ORG_NAME SMALL_AREA,    #小区 \n");
		sql.append("  REPLACE(td.DEALER_CODE,'A','')||'A' DEALER_CODE,    #责任经销商code \n");
		sql.append("  td.DEALER_SHORTNAME,        #责任经销商名称 \n");
		sql.append("  tvc.CTM_NAME,               #车主 \n");
		sql.append("  tvc.MAIN_PHONE,             #车主联系方式 \n");
		sql.append("  trv.RECALL_STATUS,          #完成状态 \n");
		sql.append("  DATE_FORMAT(trv.EXPECT_RECALL_DATE,'%Y-%c-%d') EXPECT_RECALL_DATE,    #预计召回时间 \n");
		sql.append("  DATE_FORMAT(trv.EXPECT_RECALL_DATE_TMP,'%Y-%c-%d') EXPECT_RECALL_DATE_TMP,    #预计召回时间 \n");
		sql.append("  trv.UPDATE_COUNT,            #修改次数 \n");
		sql.append("  trv.UPLOAD_STATUS         #是否上报 \n");
		sql.append("FROM  TT_RECALL_VEHICLE_DCS trv \n");
		sql.append("LEFT JOIN TT_RECALL_SERVICE_DCS trs ON trs.RECALL_ID = trv.RECALL_ID \n");
		sql.append("LEFT JOIN TM_VEHICLE_DEC tv ON tv.VIN = trv.VIN \n");
		sql.append("LEFT JOIN TT_VS_SALES_REPORT tvsr ON tv.VEHICLE_ID = tvsr.VEHICLE_ID \n");
		sql.append("LEFT JOIN TT_VS_CUSTOMER tvc ON tvc.CTM_ID = tvsr.CTM_ID \n");
		sql.append("LEFT JOIN TM_DEALER td ON td.DEALER_ID = trv.DUTY_DEALER \n");
		sql.append("LEFT JOIN TM_DEALER_ORG_RELATION tdor ON td.DEALER_ID = tdor.DEALER_ID \n");
		sql.append("LEFT JOIN TM_ORG tor ON tdor.ORG_ID = tor.ORG_ID \n");
		sql.append("LEFT JOIN TM_ORG tor2 ON tor.PARENT_ORG_ID = tor2.ORG_ID \n");
		sql.append("WHERE 1=1 \n");
		//-----条件 -----
		//经销商CODE
		sql.append("	AND  TD.DEALER_CODE='"+loginInfo.getDealerCode()+"'  \n");
		//召回编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallNo"))){
			sql.append("	and TRS.RECALL_NO like'%"+queryParam.get("recallNo")+"%' \n");
		}
		//召回名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallName"))){
			sql.append("	AND TRS.RECALL_NAME like'%"+queryParam.get("recallName")+"%' \n");
		}	
		//召回开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))){
			sql.append("	AND TRS.RECALL_START_DATE >=DATE_FORMAT('"+queryParam.get("beginDate")+"', '%Y-%c-%d') \n");
		}	
		//召回结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("	AND TRS.RECALL_END_DATE >= DATE_FORMAT('"+queryParam.get("endDate")+"', '%Y-%c-%d') \n");
		}	
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sql.append("	AND TRV.VIN like'%"+queryParam.get("vin")+"%' \n");
		}	
		//召回完成状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("recallStatus"))){
			sql.append("	AND TRV.RECALL_STATUS ='"+queryParam.get("recallStatus")+"' \n");
		}	
		//大区
		if(!StringUtils.isNullOrEmpty(queryParam.get("bigArea"))){
			sql.append("	AND TOR2.ORG_ID ='"+queryParam.get("bigArea")+"' \n");
		}
		//小区
		if(!StringUtils.isNullOrEmpty(queryParam.get("smallArea"))){
			sql.append("	AND TOR.ORG_ID ='"+queryParam.get("smallArea")+"' \n");
		}				
		return sql.toString();
	}
	/**
	 * 查询召回活动信息
	 * @param vin
	 * @param recallId
	 * @return
	 */
	public Map queryRecallDateilMap(String vin,String recallId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT  s.RECALL_NO,s.RECALL_NAME,s.RECALL_THEME,s.RECALL_TYPE, \n");
		sql.append("	DATE_FORMAT(s.RECALL_START_DATE,'%Y-%c-%d') RECALL_START_DATE, \n");
		sql.append("	DATE_FORMAT(s.RECALL_END_DATE,'%Y-%c-%d') RECALL_END_DATE, \n");
		sql.append("	s.IS_FIXED_COST,s.MAN_HOUR_COST,s.PART_COST,s.OTHER_COST, \n");
		sql.append("	s.RECALL_EXPLAIN,s.CLAIM_APPLY_GUIDANCE,s.RECALL_STATUS, \n");
		sql.append("	DATE_FORMAT(s.RELEASE_DATE,'%Y-%c-%d') RELEASE_DATE, \n");
		sql.append("	r.DEALER_NAME, \n");
		sql.append("	r.REPAIR_NO,	#工单号 \n");
		sql.append("	DATE_FORMAT(r.FINISH_DATE,'%Y-%c-%d') FINISH_DATE \n");
		sql.append("FROM TT_RECALL_VEHICLE_DCS v \n");
		sql.append("LEFT JOIN TT_RECALL_SERVICE_DCS s ON v.RECALL_ID = s.RECALL_ID \n");
		sql.append("LEFT JOIN TT_WR_REPAIR_DCS r ON r.VIN = v.VIN AND r.PACKAGE_CODE LIKE  '%s.RECALL_NO%' AND r.REFUND<>12781001 AND r.IS_DEL=0 \n");
		sql.append("WHERE s.RECALL_ID = "+recallId+" AND v.VIN ='"+vin+"' \n");
		sql.append(" \n");

		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);		
		Map map = new HashMap();
		if(list !=null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}
	/**
	 * 查询客户信息
	 * @param vin
	 * @param recallId
	 * @return
	 */
	public Map queryCustomerDateilMap(String vin,String recallId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT c.CTM_NAME, c.SEX, c.CARD_TYPE, c.MAIN_PHONE, c.EMAIL,  c.ADDRESS FROM TT_RECALL_VEHICLE_DCS rv \n");
		sql.append("LEFT JOIN TM_VEHICLE_DEC v ON rv.VIN = v.VIN \n");
		sql.append("LEFT JOIN TT_VS_SALES_REPORT vc ON vc.VEHICLE_ID = v.VEHICLE_ID \n");
		sql.append("LEFT JOIN TT_VS_CUSTOMER c ON c.CTM_ID = vc.CTM_ID \n");
		sql.append("WHERE rv.RECALL_ID = "+recallId+" AND v.VIN ='"+vin+"'  \n");
		sql.append(" \n");

		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);		
		Map map = new HashMap();
		if(list !=null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}
	/**
	 * 查询车辆信息
	 * @param vin
	 * @param recallId
	 * @return
	 */
	public Map queryVehicleDateilMap(String vin,String recallId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT rv.VIN,v.LICENSE_NO, \n");
		sql.append("w.BRAND_NAME,#品牌 \n");
		sql.append("w.SERIES_NAME, #车系 \n");
		sql.append("w.MODEL_NAME, #车型 \n");
		sql.append("w.GROUP_NAME, #年款 \n");
		sql.append("w.MODEL_YEAR, #车款 \n");
		sql.append("w.TRIM_NAME, #内饰 \n");
		sql.append("w.COLOR_NAME, #颜色 \n");
		sql.append("rv.RECALL_STATUS #状态 \n");
		sql.append("FROM TT_RECALL_VEHICLE_DCS  rv \n");
		sql.append("LEFT JOIN TM_VEHICLE_DEC v ON v.VIN = rv.VIN \n");
		sql.append("LEFT JOIN  ("+getVwMaterialSql()+") w ON w.MATERIAL_ID = v.MATERIAL_ID \n");
		sql.append("where rv.RECALL_ID = "+recallId+" and v.VIN ='"+vin+"'  \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);		
		Map map = new HashMap();	
		if(list !=null && list.size()>0){
			map = list.get(0);
		}
		return map;
	}

}












