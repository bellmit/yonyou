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
* @date 2017年4月5日
*/
@Repository
public class ActivityReportDao extends OemBaseDAO{
	
	/**
	 * 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getActivityReportQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select * from ( SELECT y.ACTIVITY_CODE,y.ACTIVITY_ID,y.ACTIVITY_NAME, \n");
		sql.append("				DATE_FORMAT(y.START_DATE,'%Y-%c-%d') START_DATE,       \n");
		sql.append("				DATE_FORMAT(y.END_DATE,'%Y-%c-%d') END_DATE,       \n");
		sql.append("				COUNT(ce.VIN) COUNT  \n");
		sql.append("			FROM  tt_wr_activity_DCS Y, \n");
		sql.append("			      TT_WR_ACTIVITY_VEHICLE_COMPLETE_DCS ce \n");
		sql.append("			WHERE y.ACTIVITY_CODE = ce.ACTIVITY_CODE AND y.IS_DEL = 0 \n");
		//活动系统编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("	AND Y.ACTIVITY_CODE LIKE'%"+queryParam.get("activityCode")+"%' \n");		                      
		}
		//开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("startDate"))){
			sql.append("	AND Y.START_DATE >= DATE_FORMAT('"+queryParam.get("startDate")+"','%Y-%c-%d')  \n");			                      
		}
		//结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("	AND Y.END_DATE <= DATE_FORMAT('"+queryParam.get("endDate")+" 23:59:59','%Y-%c-%d %H:%i:%s') \n");			                      
		}
		sql.append("				GROUP BY y.ACTIVITY_CODE,  y.ACTIVITY_NAME,y.ACTIVITY_ID, y.START_DATE, y.END_DATE ) tt \n");
		
		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 明细查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getActivityReportDetailQuery(Map<String, String> queryParam,String activityCode) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" select * from ( SELECT COUNT(R.VIN) COUNT, R.DEALER_CODE, R.DEALER_NAME  \n");
		sql.append("		FROM TT_WR_ACTIVITY_VEHICLE_COMPLETE_DCS R  \n");
		sql.append("		WHERE 1=1  \n");
		//活动系统编号
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("			AND  ACTIVITY_CODE='"+queryParam.get("activityCode")+"' \n");                    
		}		
		
		//经销商代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("			AND R.DEALER_CODE like'%"+queryParam.get("dealerCode")+"%' \n");                      
		}		

		sql.append("			GROUP BY R.DEALER_CODE,R.DEALER_NAME ) tt\n");
	
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}

}
