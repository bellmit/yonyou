package com.yonyou.dms.vehicle.dao.afterSales.workWeek;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 假期维护
 * @author Administrator
 *
 */
@Repository
public class HolidayManageDao extends OemBaseDAO{
	/**
	 * 假期维护查询
	 */
	public PageInfoDto holidayManageQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		Calendar now = Calendar.getInstance();
		sql.append("select HOLIDAY_ID,HOLIDAY_YEAR,HOLIDAY_NAME,DATE_FORMAT(START_DATE,'%Y-%m-%d') START_DATE,DATE_FORMAT(END_DATE,'%Y-%m-%d') END_DATE,STATUS,\n");
		sql.append("       DATE_FORMAT(ADJUST_DATE,'%Y-%m-%d') , CONCAT(DATE_FORMAT(ADJUST_DATE,'%Y-%m-%d') ,'---', DATE_FORMAT(ADJUST_DATE2,'%Y-%m-%d'))  AS WEEK,  DATE_FORMAT(ADJUST_DATE2,'%Y-%m-%d'),DATEDIFF(END_DATE,START_DATE)+1 as HDAYS\n");
		sql.append("  from TM_HOLIDAY_DCS  \n");
		sql.append("  where 1=1 \n");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("holidayYear"))) {
				sql.append("AND HOLIDAY_YEAR like '%"+queryParam.get("holidayYear")+"%'  \n");
			}else{
				sql.append("AND HOLIDAY_YEAR ='"+now.get(Calendar.YEAR)+"'  \n");
			}
		  
		 System.out.println(sql.toString());
			return sql.toString();
	}
	/**
	 * 查询所有年份信息
	 */
	public List<Map> getYear() throws ServiceBizException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct HOLIDAY_YEAR_DCS from TM_HOLIDAY ");
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
