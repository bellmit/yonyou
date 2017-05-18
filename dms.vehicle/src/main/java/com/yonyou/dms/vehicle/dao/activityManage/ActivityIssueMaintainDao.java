package com.yonyou.dms.vehicle.dao.activityManage;

import java.util.ArrayList;
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
* @author liujiming
* @date 2017年3月31日
*/
@Repository
public class ActivityIssueMaintainDao extends OemBaseDAO{

	
	/**
	 * 车辆活动状态查询
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getActivityIssueQueryList(Map<String, String> queryParam) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT ACT.ACTIVITY_ID, \n");
		sql.append("       ACT.ACTIVITY_CODE, \n");
		sql.append("       ACT.ACTIVITY_NAME,    \n");
		sql.append("       ACT.ACTIVITY_TYPE,	  \n");
		sql.append("	  ACT.CLAIM_TYPE, \n");
		sql.append("       DATE_FORMAT(ACT.START_DATE,'%Y-%c-%d') START_DATE, \n");
		sql.append("       DATE_FORMAT(ACT.END_DATE,'%Y-%c-%d') END_DATE, \n");
		sql.append("       ACT.STATUS \n");
		sql.append("	FROM TT_WR_ACTIVITY_DCS ACT \n");
		sql.append("	WHERE ACT.IS_DEL = 0 \n");
		sql.append("		AND ACT.STATUS IN( 40171001,40171002) \n");
		
		//公司ID
		sql.append("		AND ACT.OEM_COMPANY_ID = '"+loginInfo.getCompanyId()+"' \n");
		//sql.append("		AND ACT.OEM_COMPANY_ID = '2010010100070674'     \n");		
		//活动系统编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("		AND ACT.ACTIVITY_CODE like'%"+queryParam.get("activityCode")+"%' \n");                      
		}
		//经销商名称
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityName"))){
			sql.append("		AND ACT.ACTIVITY_NAME like'%"+queryParam.get("activityName")+"%' \n");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityStatus"))){
			sql.append("		AND  ACT.STATUS ='"+queryParam.get("activityStatus")+"' \n");
		}
		//车辆状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityType"))){
			sql.append("		AND  ACT.ACTIVITY_TYPE ='"+queryParam.get("activityType")+"' \n");
		}
				
		sql.append("		 \n");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 活动经销商维护  查询  	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerQuery(Map<String, String> queryParam,Long activityId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWAD.ID, TWAD.ACTIVITY_ID, TWAD.DEALER_ID, TWAD.DEALER_CODE,TWAD.DEALER_NAME, D.DEALER_SHORTNAME \n");
		sql.append("   FROM TT_WR_ACTIVITY_DEALER_DCS TWAD, TM_DEALER D \n");
		sql.append("	WHERE 1 = 1 \n");
		sql.append("		AND TWAD.DEALER_ID = D.DEALER_ID \n");
		sql.append("		AND D.DEALER_TYPE = 10771002 \n");
		//活动Id
		sql.append("		AND  TWAD.ACTIVITY_ID ='"+activityId+"' \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append(" and D.DEALER_CODE in ( ? ) \n");
			params.add(queryParam.get("dealerCode"));
		 }
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerName"))){
			sql.append("		AND D.DEALER_NAME like'%"+queryParam.get("dealerName")+"%' \n");         
		 }
		
		sql.append("		 \n");

		
		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	
}





