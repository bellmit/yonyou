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
* @date 2017年3月30日
*/
@Repository
public class ActivityDealerMaintainDao extends OemBaseDAO{
	
	/**
	 * 活动经销商 查询	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerManageQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDealerQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * SQL组装 活动经销商
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getDealerQuerySql(Map<String, String> queryParam, List<Object> params) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		StringBuffer conditionWhere = new StringBuffer("\n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			conditionWhere.append("		AND ACT.ACTIVITY_CODE LIKE'%"+queryParam.get("activityCode")+"%' \n");            
		 }
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityName"))){
			conditionWhere.append("		AND ACT.ACTIVITY_NAME LIKE'%"+queryParam.get("activityName")+"%' \n");
		 }
		
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityType"))){
			conditionWhere.append("		AND  ACT.ACTIVITY_TYPE ='"+queryParam.get("activityType")+"' \n");            
		 }
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityStatus"))){
			conditionWhere.append("		AND  ACT.STATUS ='"+queryParam.get("activityStatus")+"' \n");           
		 }
		
				
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT ACT.ACTIVITY_ID, \n");
		sql.append("       ACT.ACTIVITY_CODE, \n");
		sql.append("       ACT.ACTIVITY_NAME,      \n");
		sql.append("       ACT.ACTIVITY_TYPE, \n");
		sql.append("       ACT.CLAIM_TYPE, \n");
		sql.append("       DATE_FORMAT(ACT.START_DATE,'%Y-%c-%d') START_DATE, \n");
		sql.append("       DATE_FORMAT(ACT.END_DATE,'%Y-%c-%d') END_DATE, \n");
		sql.append("        IFNULL(D.COUNT, 0)  COUNT, \n");
		sql.append("    ACT.STATUS \n");
		sql.append("  FROM TT_WR_ACTIVITY_DCS ACT LEFT JOIN \n");
		sql.append("       (SELECT ACT.ACTIVITY_ID, COUNT(DD.DEALER_ID) COUNT \n");
		sql.append("          FROM TT_WR_ACTIVITY_DCS ACT, TT_WR_ACTIVITY_DEALER_DCS DD \n");
		sql.append("	     WHERE 1 = 1 \n");
		sql.append("		AND ACT.ACTIVITY_ID = DD.ACTIVITY_ID \n");
		sql.append("		AND ACT.IS_DEL = 0 \n");
		
		//查询条件
		if(conditionWhere!=null&&!conditionWhere.equals("")){
			sql.append("                             "+conditionWhere+"\n");	
		}
		
		sql.append("	     GROUP BY ACT.ACTIVITY_ID) D \n");
		sql.append("	ON ACT.ACTIVITY_ID = D.ACTIVITY_ID \n");
		sql.append("	WHERE 1 = 1 \n");
		sql.append("		AND (ACT.STATUS=40171001 OR ACT.STATUS=40171003) \n");
		sql.append("		AND ACT.IS_DEL = 0 \n");
		
		//公司ID
		//sql.append("		AND ACT.OEM_COMPANY_ID = '"+loginInfo.getOemCompanyId()+"' \n");
		sql.append("		AND ACT.OEM_COMPANY_ID = '2010010100070674' \n");
		//查询条件
		if(conditionWhere!=null&&!conditionWhere.equals("")){
			sql.append("                             "+conditionWhere+"\n");	
		}
		//sql.append("		ORDER BY ACT.ACTIVITY_ID DESC \n");
		sql.append(" \n");

		
		return sql.toString();
	}
	
	/**
	 * 活动经销商维护  查询  	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerEditQuery(Map<String, String> queryParam,Long activityId) {
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
	
	
	/**
	 * 新增页面查询  查询  	
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDealerAddQuery(Map<String, String> queryParam,Long acvtivityIdTyd) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT (D.DEALER_ID || ',' || D.DEALER_CODE || ',' || D.DEALER_NAME) DEALER_ID_CODE_NAME, \n");
		sql.append("	D.DEALER_ID, \n");
		sql.append("	D.DEALER_CODE, \n");
		sql.append("	D.DEALER_NAME, \n");
		sql.append("	D.DEALER_SHORTNAME \n");
		sql.append("	FROM TM_DEALER D \n");
		sql.append("	WHERE 1 = 1 AND D.IS_DEL = 0 \n");
		sql.append("		AND D.DEALER_TYPE = 10771002  \n");
		sql.append("		AND D.STATUS = 10011001  \n");
		sql.append("		AND D.DEALER_LEVEL = 10851001  \n");
		sql.append("        AND D.DEALER_CODE NOT IN (SELECT TWAD.DEALER_CODE FROM tt_wr_activity_dealer_dcs TWAD WHERE TWAD.ACTIVITY_ID = '"+acvtivityIdTyd+"' ) \n");
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













