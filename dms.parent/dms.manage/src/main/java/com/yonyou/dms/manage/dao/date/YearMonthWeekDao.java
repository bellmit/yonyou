package com.yonyou.dms.manage.dao.date;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class YearMonthWeekDao extends OemBaseDAO{

	public List<Map> getYearList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select DISTINCT WORK_YEAR AS ORDER_YEAR from TM_WORK_WEEK  \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> getMonthList() {
		List<Map> monthList = new ArrayList<>();
		for (int i = 1; i < 13; i++) { // 初始化当前年包含当前月以及以后月信息
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("ORDER_MONTH", i);
			monthList.add(map);
		} 
		return monthList;
	}

	public List<Map> getWeekList(Integer year,Integer month) {
		StringBuffer sql = new StringBuffer();
		List<Object> params = new ArrayList<Object>();
		sql.append("select WORK_WEEK as ORDER_WEEK from TM_WORK_WEEK WHERE 1=1  \n");
		if(!StringUtils.isNullOrEmpty(year)){
			sql.append(" AND work_year = ? ");
			params.add(year);
		}
		if(!StringUtils.isNullOrEmpty(month)){
			sql.append(" AND work_month = ? ");
			params.add(month);
		}
		return OemDAOUtil.findAll(sql.toString(), params);
	}

	public List<Map> getHourList() {
		List<Map> hourList = new ArrayList<>();
		for (int i = 1; i < 25; i++) { 
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("HOUR_LIST", i);
			hourList.add(map);
		} 
		return hourList;
	}

}
