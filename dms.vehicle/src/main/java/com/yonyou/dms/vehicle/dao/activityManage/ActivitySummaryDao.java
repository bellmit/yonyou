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
* @date 2017年4月5日
*/
@Repository
public class ActivitySummaryDao extends OemBaseDAO{
	
	
	
	/**
	 * 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getActivitySummaryQuery(Map<String, String> queryParam) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//String dealer = loginInfo.getDealerId().toString();
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT * FROM ( SELECT   TWA.ACTIVITY_ID,TAAE.ID,TWA.ACTIVITY_CODE,TWA.ACTIVITY_NAME, TD.DEALER_CODE,TD.DEALER_SHORTNAME, \n");
		sql.append("		(SELECT T.REGION_NAME FROM  TM_REGION T WHERE T.REGION_CODE=TD.PROVINCE_ID) PROVINCE,  TAAE.IN_AMOUNT,  \n");
		sql.append("		DATE_FORMAT(TWA.START_DATE,'%Y-%c-%d') START_DATE , \n");
		sql.append("		DATE_FORMAT(TWA.END_DATE,'%Y-%c-%d') END_DATE,DATE_FORMAT(TWA.SUMM_CLOSEDATE,'%Y-%c-%d') SUMM_CLOSEDATE \n");
		sql.append("	     FROM TT_AS_ACTIVITY_EVALUATE_DCS TAAE, TT_WR_ACTIVITY_DCS TWA, TM_DEALER TD \n");
		sql.append("	     WHERE     TAAE.ACTIVITY_ID = TWA.ACTIVITY_ID   \n");
		
		if(loginInfo.getDealerId() !=null && !"".equals(loginInfo.getDealerId().toString())){
			sql.append("	AND TAAE.DEALER_ID = '").append(loginInfo.getDealerId().toString()).append("'");
		}
		sql.append("		   AND TAAE.STATUS = 10041001 \n");
		sql.append("		   AND  TD.DEALER_ID= TAAE.DEALER_ID \n");
	
		//活动系统编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("		AND TWA.ACTIVITY_CODE LIKE'%"+queryParam.get("activityCode")+"%'        \n"); 
		}
		//经销商代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("		AND TD.DEALER_CODE LIKE'%"+queryParam.get("dealerCode")+"%'       \n");  
		}		
		//开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("startDate"))){
			sql.append("		 AND TWA.START_DATE >= DATE_FORMAT('"+queryParam.get("startDate")+"','%Y-%c-%d')         \n"); 
		}
		//结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("		 AND TWA.END_DATE <= DATE_FORMAT('"+queryParam.get("endDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')      \n"); 
		}
		sql.append("   ) tt  \n");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}	
	
	
	/**
	 * 明细
	 * @param queryParam
	 * @return
	 */
	public List<Map> getActivitySummaryDetailQuery(Map<String, String> queryParam,Long id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT TD.DEALER_CODE, TD.DEALER_SHORTNAME, TAAE.ON_AMOUNT,TAAE.OUT_GUARANTEE,TAAE.IN_GUARANTEE, TAAE.MAINTAIN_AMOUNT, \n");
		sql.append("		TAAE.REPAIR_AMOUNT,TAAE.IN_AMOUNT,t.ACTIVITY_NAME, TAAE.REPAIR_AMOUNT,TAAE.ON_CAMOUNT,TAAE.EVALUATE, \n");
		sql.append("		TAAE.MEASURES, TAAE.IN_GUARANTEE, TAAE.OUT_GUARANTEE, TAAE.MAINTAIN_AMOUNT,r.REGION_NAME \n");
		sql.append("	FROM TT_AS_ACTIVITY_EVALUATE_DCS TAAE, \n");
		sql.append("		TM_REGION R, \n");
		sql.append("		TT_WR_ACTIVITY_DCS T, \n");
		sql.append("		TM_DEALER TD  \n");
		sql.append("	WHERE T.ACTIVITY_ID = TAAE.ACTIVITY_ID \n");
		sql.append("		AND R.REGION_CODE = TAAE.REGION \n");
		sql.append("		AND TAAE.DEALER_ID = TD.DEALER_ID \n");
		sql.append("		AND TAAE.ID = '"+id+"' \n");
		
		List<Map>  list = OemDAOUtil.findAll(sql.toString(), params);
		return list;

	}	

}










