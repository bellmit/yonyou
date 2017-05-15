package com.yonyou.dms.vehicle.dao.afterSales.workWeek;

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
 * 工作周查询
 * @author Administrator
 *
 */
@Repository
public class WorkWeekManageDao extends OemBaseDAO{
	/**
	 * 查询
	 */
	public PageInfoDto WorkWeekManageQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT YEAR_CODE,\n");
	    sql.append("       MONTH_CODE,\n");  
	    sql.append("       WEEK_CODE,\n");  
	    sql.append("       START_DATE,\n");  
	    sql.append("       END_DATE\n"); 
	    sql.append("  FROM tm_week  \n");  
	    sql.append(" where  1=1   \n"); 
		 //年
		  if (!StringUtils.isNullOrEmpty(queryParam.get("workYear"))) {
				sql.append("and  YEAR_CODE ='"+queryParam.get("workYear")+"' \n");
			}
		  //月
		  if (!StringUtils.isNullOrEmpty(queryParam.get("workMonth"))) {
				sql.append("and  MONTH_CODE ='"+queryParam.get("workMonth")+"' \n");
			}
		  
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	//查询临时表里面的数据
	public List<Map> findWorkWeek() {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" SELECT * FROM  Tmp_Week_DCS   \n");
		sql.append(" WHERE  CREATE_BY =  "+loginInfo.getUserId()+"  \n");
		sql.append(" order by ID  \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), null);
		return resultList;
	}
	

}
