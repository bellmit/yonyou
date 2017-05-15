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
* @date 2017年3月31日
*/
@Repository
public class ActivityStatusMaintainDao extends OemBaseDAO{
	/**
	 * 车辆活动状态查询
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityStatusQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT * FROM (		SELECT TWA.ACTIVITY_ID, TVC.CTM_ID, (SELECT TVL.LM_ID FROM TT_VS_LINKMAN TVL WHERE TVL.CTM_ID=TVC.CTM_ID) LM_ID, \n");
		sql.append("	TWA.ACTIVITY_CODE,TWAV.VIN, TV.LICENSE_NO, TVC.CTM_NAME, \n");
		sql.append("	DATE_FORMAT(TWA.START_DATE,'%Y-%c-%d') START_DATE, \n");
		sql.append("	DATE_FORMAT(TWA.END_DATE,'%Y-%c-%d') END_DATE, (SELECT TVL.NAME FROM TT_VS_LINKMAN TVL WHERE TVL.CTM_ID=TVC.CTM_ID) NAME, \n");
		sql.append("	TWAV.DEALER_NAME,TWAV.EXECUTE_DEALER_NAME,  \n");
		sql.append("	#DECODE(TWAV.VEHICLE_STATUS,40331001,'未完成',40331002,'已完成') VEHICLE_STATUS \n");
		sql.append("	TWAV.VEHICLE_STATUS	 \n");
		sql.append("     FROM  TT_WR_ACTIVITY_DCS TWA, \n");
		sql.append("	   TT_WR_ACTIVITY_VEHICLE_DCS TWAV, \n");
		sql.append("	   TM_VEHICLE_DEC TV, \n");
		sql.append("	   TT_VS_SALES_REPORT TVSR, \n");
		sql.append("	   TT_VS_CUSTOMER TVC \n");
		sql.append("     WHERE TWA.ACTIVITY_ID =TWAV.ACTIVITY_ID \n");
		sql.append("	AND TV.VIN = TWAV.VIN \n");
		sql.append("	AND TVSR.VEHICLE_ID=TV.VEHICLE_ID \n");
		sql.append("	AND TVSR.CTM_ID=TVC.CTM_ID \n");
		//活动系统编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("	AND TWA.ACTIVITY_CODE LIKE'%"+queryParam.get("activityCode")+"%' \n");	                      
		}
		//经销商名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerName"))){
			sql.append("	AND  TWAV.DEALER_NAME LIKE'%"+queryParam.get("dealerName")+"%' \n");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sql.append("	AND TWAV.VIN LIKE'%"+queryParam.get("vin")+"%' \n");
		}
		//车辆状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("vehicleStatus"))){
			sql.append("	AND  TWAV.VEHICLE_STATUS ='"+queryParam.get("vehicleStatus")+"' \n");
		}
		sql.append("      )  t   \n");	
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 车辆信息
	 * @param VIN
	 * @param activityId
	 * @return
	 */
	public List<Map> getDetailCarMsgQuery(String VIN, Long activityId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT  TV.LICENSE_NO, TV.VIN, TWAV.DEALER_CODE  \n");
		sql.append("	FROM TM_VEHICLE_DEC TV,TT_WR_ACTIVITY_VEHICLE_DCS  TWAV \n");
		sql.append("	WHERE  1=1 \n");
		sql.append("		AND tv.vin = twav.vin \n");
		sql.append("		AND TV.VIN = '"+VIN+"'  \n");
		sql.append("		AND TWAV.ACTIVITY_ID = "+activityId+" \n");

		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}
	/**
	 * 车主信息
	 * @param ctmId
	 * @return
	 */
	public List<Map> getDetailGTMMsgQuery(Long ctmId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TVC.CTM_NAME,  (SELECT  REGION_NAME FROM TM_REGION WHERE REGION_CODE=TVC.PROVINCE) AS PROVINCE, \n");
		sql.append("	(SELECT  REGION_NAME FROM TM_REGION WHERE REGION_CODE=TVC.CITY) AS CITY, \n");
		sql.append("	(SELECT  REGION_NAME FROM TM_REGION WHERE REGION_CODE=TVC.TOWN) AS TOWN, \n");
		sql.append("	TVC.POST_CODE,TVC.ADDRESS,TVC.EMAIL  \n");
		sql.append("    FROM  TT_VS_CUSTOMER TVC \n");
		sql.append("    WHERE 1=1  \n");
		sql.append("    AND TVC.CTM_ID = "+ctmId+" \n");

		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}
	/**
	 * 联系人信息
	 * @param ctmId
	 * @return
	 */
	public List<Map> getDetailLinkManMsgQuery(Long lmId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TVL.MAIN_PHONE,TVL.OTHER_PHONE,TVL.NAME \n");
		sql.append("  FROM TT_VS_LINKMAN TVL \n");
		sql.append("  WHERE   1=1  AND TVL.LM_ID = "+lmId+" \n");
		List<Map> orderList = OemDAOUtil.findAll(sql.toString(), params);
		return orderList;
	}
	

}
