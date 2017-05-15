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
* @date 2017年4月1日
*/
@Repository
public class ActivityPlanAnalysisDao extends OemBaseDAO{
	
	
	/**
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getPlanAnalysisInitQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWA.ACTIVITY_ID, TWA.ACTIVITY_CODE,  TWA.ACTIVITY_NAME, \n");
		sql.append("		DATE_FORMAT(TWA.START_DATE,'%Y-%c-%d') START_DATE, \n");
		sql.append("		DATE_FORMAT(TWA.END_DATE,'%Y-%c-%d') END_DATE, \n");
		sql.append("		TWA.STATUS, \n");
		sql.append("		NUM.TOLNUM,  NUM.COMNUM, \n");
		sql.append("        ROUND( NUM.COMNUM/NUM.TOLNUM, 2) WCL  \n");
		sql.append("	FROM TT_WR_ACTIVITY_DCS TWA, \n");
		sql.append("	     (SELECT  COUNT(T1.ID) TOLNUM, \n");
		sql.append("		     (SELECT COUNT(ID) FROM TT_WR_ACTIVITY_VEHICLE_DCS T2 WHERE VEHICLE_STATUS = '40331002' AND T2.ACTIVITY_ID=T1.ACTIVITY_ID) AS COMNUM, \n");
		sql.append("		     T1.ACTIVITY_ID \n");
		sql.append("		FROM TT_WR_ACTIVITY_VEHICLE_DCS T1 GROUP BY  ACTIVITY_ID) NUM  \n");
		sql.append("	WHERE TWA.ACTIVITY_ID= NUM.ACTIVITY_ID \n");
		sql.append("		AND TWA.STATUS<> 40171001 \n");
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityCode"))){
			sql.append("		AND TWA.ACTIVITY_CODE LIKE'%"+queryParam.get("activityCode")+"%' \n");	                      
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("startDate"))){
			sql.append("		AND TWA.START_DATE >= DATE_FORMAT('"+queryParam.get("startDate")+"','%Y-%c-%d')  \n");	                      
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("endDate"))){
			sql.append("		AND TWA.END_DATE <= DATE_FORMAT('"+queryParam.get("endDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");	                      
		}
		if(!StringUtils.isNullOrEmpty(queryParam.get("activityStatus"))){
			sql.append("		AND  TWA.STATUS ='"+queryParam.get("activityStatus")+"' \n");	                      
		}						
		sql.append(" \n");

		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getPlanAnalysisDetailQueryList(Map<String, String> queryParam, Long activityId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT tt.*, ROUND( tt.JHN/tt.JHS, 2) WCL FROM (   SELECT INFO.EXECUTE_DEALER_CODE,INFO.EXECUTE_DEALER_NAME,       \n");
		sql.append("		A.DEALER_CODE,       \n");
		sql.append("		A.JHS,	 IFNULL(B.JHN,0) JHN,	   \n");
		sql.append("		IFNULL(C.JHW,0) JHW  FROM (SELECT PO.EXECUTE_DEALER_CODE,       \n");
		sql.append("				    PO.EXECUTE_DEALER_NAME,       \n");
		sql.append("				    PO.ACTIVITY_ID  \n");
		sql.append("				FROM  TT_WR_ACTIVITY_VEHICLE_DCS PO   \n");
		sql.append("				WHERE PO.ACTIVITY_ID = '"+activityId+"' \n");
		sql.append("				AND PO.EXECUTE_DEALER_CODE IS NOT NULL  \n");
		//经销商代码
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			sql.append("				AND  PO.EXECUTE_DEALER_CODE LIKE '%"+queryParam.get("dealerCode")+"%'  \n");	                      
		}	
		//sql.append("				AND  PO.EXECUTE_DEALER_CODE LIKE '%10047%'  \n");
		
		sql.append("				GROUP BY  PO.EXECUTE_DEALER_CODE,PO.EXECUTE_DEALER_NAME,PO.ACTIVITY_ID) INFO         \n");
		sql.append("	LEFT JOIN (SELECT E.ACTIVITY_ID, E.DEALER_CODE, COUNT(E.VIN) AS JHS                    \n");
		sql.append("			FROM TT_WR_ACTIVITY_VEHICLE_DCS E                   \n");
		sql.append("			WHERE E.ACTIVITY_ID = '"+activityId+"'                  \n");
		sql.append("			GROUP BY E.ACTIVITY_ID, E.DEALER_CODE) A          \n");
		sql.append("		ON INFO.EXECUTE_DEALER_CODE = a.DEALER_CODE             \n");
		sql.append("			AND A.ACTIVITY_ID = INFO.ACTIVITY_ID			  \n");
		sql.append("	LEFT JOIN (SELECT E2.ACTIVITY_ID,E2.EXECUTE_DEALER_CODE, E2.EXECUTE_DEALER_NAME, COUNT(E2.VIN) AS JHN                    \n");
		sql.append("			FROM TT_WR_ACTIVITY_VEHICLE_DCS E2                  	 \n");
		sql.append("			WHERE     E2.ACTIVITY_ID = '"+activityId+"'                         \n");
		sql.append("			AND E2.VEHICLE_STATUS = '40331002'                         \n");
		sql.append("			AND (E2.DEALER_CODE = E2.EXECUTE_DEALER_CODE) \n");
		sql.append("			GROUP BY E2.ACTIVITY_ID, E2.EXECUTE_DEALER_CODE,E2.EXECUTE_DEALER_NAME) B         \n");
		sql.append("		 ON INFO.EXECUTE_DEALER_CODE = B.EXECUTE_DEALER_CODE             \n");
		sql.append("			AND B.ACTIVITY_ID = INFO.ACTIVITY_ID     \n");
		sql.append("	LEFT JOIN (SELECT E2.ACTIVITY_ID,E2.EXECUTE_DEALER_CODE, E2.EXECUTE_DEALER_NAME,COUNT(E2.VIN) AS JHW \n");
		sql.append("			FROM TT_WR_ACTIVITY_VEHICLE_DCS E2                  \n");
		sql.append("			WHERE E2.ACTIVITY_ID = '"+activityId+"'                         \n");
		sql.append("				AND (E2.DEALER_CODE <> E2.EXECUTE_DEALER_CODE OR E2.DEALER_CODE IS NULL)                  \n");
		sql.append("			GROUP BY E2.ACTIVITY_ID,E2.EXECUTE_DEALER_CODE, E2.EXECUTE_DEALER_NAME) C          \n");
		sql.append("		 ON INFO.EXECUTE_DEALER_CODE = C.EXECUTE_DEALER_CODE             \n");
		sql.append("			AND C.ACTIVITY_ID = INFO.ACTIVITY_ID  ) tt \n");

		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getPlanAnalysisDetailQueryListTwo(Map<String, String> queryParam, Long activityId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT tt.*, ROUND( tt.JHN/tt.JHS, 2) WCL FROM ( SELECT INFO.DEALER_CODE,INFO.DEALER_NAME, IFNULL(A.JHS,0) JHS,IFNULL(B.JHN  ,0) JHN  \n");
		sql.append("		FROM (SELECT PO.DEALER_CODE,PO.DEALER_NAME,PO.ACTIVITY_ID  \n");
		sql.append("			FROM  TT_WR_ACTIVITY_VEHICLE_DCS PO   \n");
		sql.append("			WHERE PO.ACTIVITY_ID = '"+activityId+"' \n");
		sql.append("				AND PO.DEALER_CODE IS NOT NULL  \n");
		//经销商代码
				if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
					sql.append("				AND  PO.DEALER_CODE LIKE '%"+queryParam.get("dealerCode")+"%'  \n");	                      
				}
		//sql.append("				AND  PO.DEALER_CODE LIKE '%10036%'  \n");
		
		sql.append("				GROUP BY  PO.DEALER_CODE,PO.DEALER_NAME,PO.ACTIVITY_ID) INFO         \n");
		sql.append("		LEFT JOIN (SELECT E.ACTIVITY_ID, E.DEALER_CODE, COUNT(E.VIN) AS JHS                    \n");
		sql.append("				FROM TT_WR_ACTIVITY_VEHICLE_DCS E                   \n");
		sql.append("				WHERE E.ACTIVITY_ID = '"+activityId+"'                 \n");
		sql.append("				GROUP BY E.ACTIVITY_ID, E.DEALER_CODE) A     \n");
		sql.append("			ON INFO.DEALER_CODE = a.DEALER_CODE        \n");
		sql.append("				AND A.ACTIVITY_ID = INFO.ACTIVITY_ID			  \n");
		sql.append("		LEFT JOIN (SELECT E2.ACTIVITY_ID,E2.DEALER_CODE, E2.DEALER_NAME, COUNT(E2.VIN) AS JHN      \n");
		sql.append("				FROM TT_WR_ACTIVITY_VEHICLE_DCS E2                   \n");
		sql.append("				WHERE  E2.ACTIVITY_ID = '"+activityId+"'                         \n");
		sql.append("					AND E2.VEHICLE_STATUS = '40331002' \n");
		sql.append("					AND E2.DEALER_CODE IS NOT NULL                   \n");
		sql.append("				GROUP BY E2.ACTIVITY_ID,E2.DEALER_CODE,E2.DEALER_NAME) B          \n");
		sql.append("			ON INFO.DEALER_CODE = B.DEALER_CODE          \n");
		sql.append("				AND B.ACTIVITY_ID = INFO.ACTIVITY_ID  ) tt \n");

		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}

}
