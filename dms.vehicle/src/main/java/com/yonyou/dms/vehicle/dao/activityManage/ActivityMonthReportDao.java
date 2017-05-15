package com.yonyou.dms.vehicle.dao.activityManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujiming
* @date 2017年4月7日
*/
@Repository
public class ActivityMonthReportDao extends OemBaseDAO{
	
	public PageInfoDto getActivityReportQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 服务月活动报表 下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getActivityReportDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	
	
	/**
	 * SQL组装 服务月活动报表 查询
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TAVC.ACTIVITY_CODE, \n");
		sql.append("       TAVC.VIN, \n");
		sql.append("       TAVC.DEALER_CODE, \n");
		sql.append("       DATE_FORMAT(TAVC.EXECUTE_DATE,'%Y-%c-%d') EXECUTE_DATE, \n");
		sql.append("       TAVC.DEALER_NAME, \n");
		sql.append("       VM.MODEL_NAME, \n");
		sql.append("       TVC.CTM_NAME, \n");
		sql.append("       TVC.MAIN_PHONE, \n");
		sql.append("       TVC.ADDRESS, \n");
		sql.append("       TVC.EMAIL, \n");
		sql.append("       IFNULL(FEE.AMOUNT,0) AS AMOUNT \n");
		sql.append("  FROM TT_WR_ACTIVITY_VEHICLE_COMPLETE_dcs TAVC LEFT JOIN TM_VEHICLE_dec TV ON TAVC.VIN = TV.VIN \n");
		sql.append("       LEFT JOIN ("+getVwMaterialSql()+") VM ON TV.MATERIAL_ID = VM.MATERIAL_ID \n");
		sql.append("       LEFT JOIN TT_VS_SALES_REPORT TVSR ON TV.VEHICLE_ID = TVSR.VEHICLE_ID \n");
		sql.append("       LEFT JOIN TT_VS_CUSTOMER TVC ON TVSR.CTM_ID = TVC.CTM_ID \n");
		sql.append("       LEFT JOIN  (SELECT T2.ACTIVITY_CODE,T1.VIN,T1.DEALER_CODE,SUM(T1.PART_FEE + T1.LABOR_FEE + T1.OTHER_FEE) AS amount \n");
		sql.append("					FROM TT_WR_REPAIR_DCS T1, \n");
		sql.append("						(SELECT repair_id, activity_code \n");
		sql.append("							FROM TT_WR_REPAIR_ITEM_DCS \n");
		sql.append("							WHERE activity_code IS NOT NULL \n");
		sql.append("						UNION \n");
		sql.append("						SELECT repair_id, activity_code \n");
		sql.append("							FROM TT_WR_REPAIR_OTHERITEM_DCS \n");
		sql.append("							WHERE activity_code IS NOT NULL \n");
		sql.append("						UNION \n");
		sql.append("						SELECT repair_id, activity_code \n");
		sql.append("							FROM TT_WR_REPAIR_PART_DCS \n");
		sql.append("						WHERE activity_code IS NOT NULL) T2 \n");
		sql.append("					WHERE T1.REPAIR_ID = T2.REPAIR_ID GROUP BY T2.ACTIVITY_CODE,T1.VIN,T1.DEALER_CODE )   FEE \n");
		sql.append("				ON  TAVC.ACTIVITY_CODE =  FEE.ACTIVITY_CODE AND TAVC.VIN = Fee.vin AND TAVC.DEALER_CODE = FEE.DEALER_CODE \n");
		sql.append("	WHERE 1 = 1 \n");		
		//活动系统编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("		AND TAVC.ACTIVITY_CODE like'%"+queryParam.get("activityCode")+"%' \n");	                      
		}
		//经销商代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("		AND TAVC.DEALER_CODE like'%"+queryParam.get("dealerCode")+"%' \n");			                      
		}
		//开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("startDate"))){
			sql.append("		AND TAVC.EXECUTE_DATE >= DATE_FORMAT('"+queryParam.get("startDate")+"','%Y-%c-%d ')  \n");
		}
		//结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("		AND TAVC.EXECUTE_DATE <= DATE_FORMAT('"+queryParam.get("endDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')		 \n");
		}
	
		
		return sql.toString();
	}

}
