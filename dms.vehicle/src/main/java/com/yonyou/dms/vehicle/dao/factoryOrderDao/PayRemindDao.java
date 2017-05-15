package com.yonyou.dms.vehicle.dao.factoryOrderDao;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;

/**
 * 
 * @author lianxinglu 2017-02-13
 */
@Repository

public class PayRemindDao extends OemBaseDAO {

	public PageInfoDto QueryPayRemind(Map<String, String> queryParam) {
		String sql = "select REMIND_ID,RTYPE,DAY_NUM from TT_PAY_REMIND";
		PageInfoDto pageQuery = OemDAOUtil.pageQuery(sql, null);

		return pageQuery;
	}

}
