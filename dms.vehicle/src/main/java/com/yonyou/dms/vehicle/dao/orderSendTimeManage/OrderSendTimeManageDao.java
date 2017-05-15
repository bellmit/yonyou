package com.yonyou.dms.vehicle.dao.orderSendTimeManage;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 订单发送时间维护
 * @author Administrator
 *
 */
@Repository
public class OrderSendTimeManageDao extends OemBaseDAO{
	
	/**
	 * 订单发送时间查询
	 */
	public PageInfoDto  orderSendTimeQuery() {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql();
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql() {
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT CASE WEEK \n");
		sql.append("       WHEN '0' THEN '星期日' \n");
		sql.append("       WHEN '1' THEN '星期一' \n");
		sql.append("       WHEN '2' THEN '星期二' \n");
		sql.append("       WHEN '3' THEN '星期三' \n");
		sql.append("       WHEN '4' THEN '星期四' \n");
		sql.append("       WHEN '5' THEN '星期五' \n");
		sql.append("       WHEN '6' THEN '星期六' \n");
		sql.append("       ELSE '' END AS WEEK, -- 周 \n");
		sql.append("       START_TIME, -- 开始时间 \n");
		sql.append("       STOP_TIME, -- 截停时间 \n");
		sql.append("       STATUS, -- 状态 \n");
		sql.append("       DATE_FORMAT(UPDATE_DATE, '%Y-%m-%d %H:%i:%s') AS UPDATE_DATE, -- 更新时间 \n");
		sql.append("       ID -- 主键ID \n");
		sql.append("  FROM TT_ORDER_SEND_TIME_MANAGE T \n");
		 System.out.println(sql.toString());
			return sql.toString();
	}
}
