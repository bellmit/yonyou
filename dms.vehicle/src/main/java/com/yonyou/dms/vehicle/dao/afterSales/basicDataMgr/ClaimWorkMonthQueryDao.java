package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 索赔工作月查询
 * @author Administrator
 *
 */
@Repository
public class ClaimWorkMonthQueryDao extends OemBaseDAO{
	
	public PageInfoDto  ClaimTypeQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT a.ID,a.WORK_YEAR,a.WORK_NONTH,a.WORK_WEEK,DATE_FORMAT(a.START_DATE,'%Y-%m-%d') START_DATE,DATE_FORMAT(a.END_DATE,'%Y-%m-%d') END_DATE\n" );
		sql.append("  FROM TT_WR_CLAIMMONTH_dcs a \n" );
		sql.append("  	where  a.is_del = 0 ");
		 //年
		  if (!StringUtils.isNullOrEmpty(queryParam.get("workYear"))) {
				sql.append("and  WORK_YEAR ='"+queryParam.get("workYear")+"' \n");
			}
		  //月
		  if (!StringUtils.isNullOrEmpty(queryParam.get("workMonth"))) {
				sql.append("and  WORK_NONTH ='"+queryParam.get("workMonth")+"' \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	

}
