package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

@SuppressWarnings("all")
@Repository
public class SADCS030Dao extends OemBaseDAO {

	public List<Map> isExistReserveIdQuery(String reserveId) {

		StringBuffer sql = new StringBuffer();
		StringBuffer sql1 = new StringBuffer();
		// case when A.RESERVE_ID=null then '0' else '1' end
		// sql.append("SELECT (case when A.RESERVE_ID=null then '0' else '1'
		// end) AS A, \n");
		// sql.append(" (case when B.RESERVE_ID=null then '0' else '1' end) AS B
		// \n");
		// sql.append(" FROM TM_WX_RESERVER_INFO_dcs A \n");
		// sql.append(" LEFT JOIN TM_WX_RESERVER_INFO_REPORT_dcs B \n");
		// sql.append(" ON B.RESERVE_ID = A.RESERVE_ID \n");
		// sql.append(" WHERE A.RESERVE_ID IN ('" + reserveId + "') OR \n");
		// sql.append(" B.RESERVE_ID IN ('" + reserveId + "') \n");
		sql.append("SELECT '1' A  from TM_WX_RESERVER_INFO_dcs a WHERE a.RESERVE_ID ='" + reserveId + "'");
		sql1.append("SELECT '1' B  from TM_WX_RESERVER_INFO_REPORT_dcs B WHERE B.RESERVE_ID ='" + reserveId + "'");

		List<Map> m = new ArrayList<>();
		Map mm = new HashMap<>();
		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		Map map1 = OemDAOUtil.findFirst(sql1.toString(), null);
		if (map == null || map.get("A") == null) {
			mm.put("A", "0");
			m.add(mm);
		} else {
			m.add(map);
		}
		if (map1 == null || map1.get("B") == null) {
			mm.put("B", "0");
			m.add(mm);
		} else {
			m.add(map1);
		}

		return m;
	}

}
