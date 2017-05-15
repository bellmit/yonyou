package com.yonyou.dms.manage.dao.workWeek;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

@Repository
public class WorkWeekDefinitionDao extends OemBaseDAO {

	public List<Map> getYear() {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select distinct work_year from TM_WORK_WEEK  \n");
		sql.append("  order by work_year \n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
		
	}

	public List<Map> getWeek(String workYear, String workMonth) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select work_week from TM_WORK_WEEK where work_year = " + workYear+" and work_month = "+ workMonth+" \n");
		sql.append("  order by work_week \n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public PageInfoDto search(Map<String, String> param) {
		String workYear = param.get("workYear");
		String workMonth = param.get("workMonth");
		String workWeek = param.get("workWeek");
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT WEEK_ID, \n");
		sql.append("       WORK_YEAR, \n");
		sql.append("       WORK_MONTH, \n");
		sql.append("       WORK_WEEK, \n");
		sql.append("       DATE_FORMAT(START_DATE, '%Y-%m-%d') AS START_DATE, \n");
		sql.append("       DATE_FORMAT(END_DATE, '%Y-%m-%d') AS END_DATE, \n");
		sql.append("       STATUS, \n");
		sql.append("       CREATE_DATE, \n");
		sql.append("       CREATE_BY, \n");
		sql.append("       DATE_FORMAT(UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE, \n");
		sql.append("       UPDATE_BY, \n");
		sql.append("       VER, \n");
		sql.append("       IS_ARC, \n");
		sql.append("       IS_DEL \n");
		sql.append("  FROM TM_WORK_WEEK TVM \n");
		sql.append(" WHERE 1 = 1 \n");
		if(StringUtils.isNotBlank(workYear)){
			sql.append("  AND TVM.WORK_YEAR = ? ");
			queryParam.add(workYear);
		}
		if(StringUtils.isNotBlank(workMonth)){
			sql.append("  AND TVM.WORK_MONTH = ? ");
			queryParam.add(workMonth);
		}
		if(StringUtils.isNotBlank(workWeek)){
			sql.append("  AND TVM.WORK_WEEK = ? ");
			queryParam.add(workWeek);
		}
		sql.append(" ORDER BY WORK_YEAR,WORK_MONTH,WORK_WEEK\n");
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public boolean checkDate(Date startDate, Date endDate, Long weekId) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select work_year ,work_month,work_week  from TM_WORK_WEEK w where \n");
		sql.append(" DATE(w.START_DATE) <=  ? and DATE(w.END_DATE)  >= ?   \n");
		queryParam.add(startDate);
		queryParam.add(endDate);
		if(weekId != null){
			sql.append(" and week_id <> '"+weekId+"' \n");
		}
		System.out.println("SQL\n===============\n"+sql+"\n=================");
		System.out.println("Params："+queryParam);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}

	public boolean checkDate2(Integer workYear, Integer workMonth, Integer workWeek, Long weekId) {
		List<Object> queryParam = new ArrayList<Object>();
		if(workYear == null || workMonth == null ||workWeek == null ){
			return true ;
		}
		
		String week = null;
		if(weekId == null){			
			String year = String.valueOf(workYear); 
			String month = String.valueOf(workMonth);
			week = String.valueOf(workWeek);
			year = year.substring(2,4);
			if(month.length()<2){
				month = "0"+month; 
			}
			if(week.length()<2){
				week = "0"+week; 
			}
			week = year + month+week;
		}else{
			week = String.valueOf(workWeek);
		}
		
		StringBuffer sql = new StringBuffer();
		sql.append(" select work_year ,work_month,work_week  from TM_WORK_WEEK w where \n");
		sql.append(" work_Year = '"+workYear+"' and work_Month= '"+workMonth+"' and work_Week = '"+week+"'  \n");
		if(weekId != null){
			sql.append(" and week_id <> '"+weekId+"' \n");
		}
		System.out.println("SQL\n===============\n"+sql+"\n=================");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		if(list != null && list.size() > 0){
			return false;
		}
		return true;
	}

	public List<Map> queryExport(Map<String, String> param) {
		List<Object> queryParam = new ArrayList<Object>();
		String workYear = param.get("workYear");
		String workMonth = param.get("workMonth");
		String workWeek = param.get("workWeek");
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT WEEK_ID, \n");
		sql.append("       WORK_YEAR, \n");
		sql.append("       WORK_MONTH, \n");
		sql.append("       WORK_WEEK, \n");
		sql.append("       DATE_FORMAT(START_DATE, '%Y-%m-%d') AS START_DATE, \n");
		sql.append("       DATE_FORMAT(END_DATE, '%Y-%m-%d') AS END_DATE, \n");
		sql.append("       STATUS, \n");
		sql.append("       CREATE_DATE, \n");
		sql.append("       CREATE_BY, \n");
		sql.append("       DATE_FORMAT(UPDATE_DATE, '%Y-%m-%d') AS UPDATE_DATE, \n");
		sql.append("       UPDATE_BY, \n");
		sql.append("       VER, \n");
		sql.append("       IS_ARC, \n");
		sql.append("       IS_DEL \n");
		sql.append("  FROM TM_WORK_WEEK TVM \n");
		sql.append(" WHERE 1 = 1 \n");
		if(StringUtils.isNotBlank(workYear)){
			sql.append("  AND TVM.WORK_YEAR = ? ");
			queryParam.add(workYear);
		}
		if(StringUtils.isNotBlank(workMonth)){
			sql.append("  AND TVM.WORK_MONTH = ? ");
			queryParam.add(workMonth);
		}
		if(StringUtils.isNotBlank(workWeek)){
			sql.append("  AND TVM.WORK_WEEK = ? ");
			queryParam.add(workWeek);
		}
		sql.append(" ORDER BY WORK_YEAR,WORK_MONTH,WORK_WEEK\n");
		return OemDAOUtil.downloadPageQuery(sql.toString(), queryParam);
	}

}
