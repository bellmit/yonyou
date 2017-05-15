package com.yonyou.dcs.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class AssignmentDao extends OemBaseDAO {

	public List<Map> selectCurrentWeek() {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("select work_year,work_month,work_week from TM_WORK_WEEK where start_date < now() and end_date > now() and STATUS = "+OemDictCodeConstants.STATUS_ENABLE +"\n");
		sql.append(" ORDER BY WORK_WEEK");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

}
