/**
 * @Title: TtVsNvdrDao.java 
 * @Description:
 * @Date: 2013-6-20
 * @author baojie
 * @version 1.0
 * @remark
 */

package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;


@Repository
public class TtVsNvdrDao extends OemBaseDAO {

	
	/**
	 * 根据vin判断是否存在VsNvdrId
	 * 
	 * @param vin
	 *            车辆唯一标识
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String queryVsNvdrIdByVin(String vin) throws Exception {
		List paramList = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT VN.NVDR_ID \n");
		sql.append("FROM TT_VS_NVDR VN \n");
		sql.append("WHERE IS_DEL<>1 and VN.VIN = ? \n");
		paramList.add(vin);
		Map<String, Object> map = OemDAOUtil.findFirst(sql.toString(), paramList);
		if (map != null && map.size() > 0 && map.get("NVDR_ID") != null) {
			return String.valueOf(map.get("NVDR_ID"));
		}
		return null;
	}

}
