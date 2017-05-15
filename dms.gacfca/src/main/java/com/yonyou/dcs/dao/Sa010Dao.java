package com.yonyou.dcs.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 
* @ClassName: Sa010Dao 
* @Description: 展厅预测报告
* @author zhengzengliang 
* @date 2017年4月13日 下午2:49:30 
*
 */
@SuppressWarnings("rawtypes")
@Repository
public class Sa010Dao extends OemBaseDAO{
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 查询当前展厅预测报告周
	 * @param currentDate
	 * @return
	 * @throws Exception
	 */
	public Map getCurrentWeek(Date currentDate) throws Exception {
		String currDate = handleFormatDate(currentDate);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT WEEK_CODE  \n");
		sql.append(" From TM_WEEK_SET \n");
		sql.append(" WHERE 1=1 ");
		sql.append(" AND START_DATE <= '"+currDate+"' \n");
		sql.append(" AND END_DATE >= '"+currDate+"' \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	/**
	 * 处理返回显示的时间格式为yyyy-MM-dd HH:mm:ss
	 * 
	 * @param inTime
	 * @return
	 */
	public static String handleFormatDate(java.util.Date date) {
		if (date == null) {
			return "";
		} else {
			return sdf.format(date);
		}
	}

}
