package com.yonyou.dcs.dao;

import java.util.Date;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.DateUtil;

/**
 * 
 * Title:SaDcs057Dao
 * Description: 试乘试驾统计报表上报
 * @author DC
 * @date 2017年4月7日 下午8:05:07
 */
@Repository
public class SaDcs057Dao extends OemBaseDAO{

	@SuppressWarnings("unchecked")
	public Map<String, Object> getCurrentWeek(Date currentDate) {
		String currDate = DateUtil.formatDefaultDateTimes(currentDate);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT WEEK_CODE  \n");
		sql.append(" From TM_WEEK_SET \n");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND START_DATE <= '"+currDate+"' \n");
		sql.append(" AND END_DATE >= '"+currDate+"' \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

}
