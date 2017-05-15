package com.yonyou.dms.vehicle.dao.afterSales.warranty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class DealerLevelMaintainDao extends OemBaseDAO{
	/**
	 *查询
	 */
	public PageInfoDto DealerLevelQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("       SELECT  \n");
		sql.append("		T.BIG_ORG_NAME, \n");
		sql.append("		T.SAMLL_ORG_NAME, \n");
		sql.append("		T.DER_LEVEL, \n");
		sql.append("		T.DEALER_ID,T.ID, \n");
		sql.append("		T.FIAT_WEBDAC_CODE, \n");
		sql.append("		T.STATUS, \n");
		sql.append("		TU.NAME UPDATE_BY, \n");
		sql.append("		T.UPDATE_DATE, \n");
		sql.append("		TD.DEALER_CODE, \n");
		sql.append("		TD.DEALER_SHORTNAME \n");
		sql.append("	FROM \n");
		sql.append("	(  	 \n");
		sql.append("		 SELECT  \n");
		sql.append("			( \n");
		sql.append("				 select ORG_NAME From TM_ORG TO2  \n");
		sql.append("				 where TO2.ORG_ID=TO1.PARENT_ORG_ID \n");
		sql.append("			) BIG_ORG_NAME,	 \n");
		sql.append("			 TO1.ORG_NAME SAMLL_ORG_NAME, \n");
		sql.append("			 TWDL.DER_LEVEL  DER_LEVEL, \n");
		sql.append("			 TWDL.STATUS STATUS,   \n");
		sql.append("			 TWDL.UPDATE_BY UPDATE_BY, \n");
		sql.append("			 TWDL.UPDATE_DATE UPDATE_DATE, \n");
		sql.append("			 TWDL.DEALER_ID DEALER_ID, \n");
		sql.append("			 TWDL.ID ID, \n");
		sql.append("			 TWDL.FIAT_WEBDAC_CODE FIAT_WEBDAC_CODE \n");
		sql.append("		 from TM_DEALER_ORG_RELATION  TDOR \n");
		sql.append("		 right OUTER JOIN tt_wr_dealer_level_dcs TWDL ON TWDL.DEALER_ID=TDOR.DEALER_ID   \n");
		sql.append("		 LEFT OUTER JOIN  TM_ORG TO1 ON  TDOR.ORG_ID=TO1.ORG_ID \n");
		sql.append("	) T \n");
		sql.append("	LEFT OUTER JOIN TM_DEALER TD ON T.DEALER_ID=TD.DEALER_ID  \n");
		sql.append("	LEFT OUTER JOIN Tc_user TU ON TU.USER_ID=T.UPDATE_BY	 WHERE 1=1  \n");
        if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){ 
        	sql.append(" 	AND TD.DEALER_CODE = '" + queryParam.get("dealerCode") + "' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("derLevel"))){ 
        	sql.append(" 	AND T.DER_LEVEL = " + queryParam.get("derLevel") + " \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){ 
        	sql.append(" 	AND T.STATUS = " + queryParam.get("status") + " \n");
        }
		return sql.toString();
	}
}
