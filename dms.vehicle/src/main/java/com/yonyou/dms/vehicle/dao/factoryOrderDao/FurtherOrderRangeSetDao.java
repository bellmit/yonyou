package com.yonyou.dms.vehicle.dao.factoryOrderDao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 
 * @author lianxinglu
 */
@Repository
public class FurtherOrderRangeSetDao {

	public List<Map> queryInit() {
		StringBuffer sql = new StringBuffer();
		// case when TRR.NODE_STATUS=null then 0 else 1
		sql.append(
				"SELECT TC.CODE_ID,TC.CODE_DESC,TC.NUM,(case when IFNULL(TRR.NODE_STATUS,0)=0  then 0 else 1 end) AS IS_CHECK FROM TC_CODE_dcs TC LEFT JOIN TT_VS_RESOURCE_RANGE TRR ON TC.CODE_ID = TRR.NODE_STATUS WHERE TC.TYPE = 1151 AND TC.CODE_ID NOT IN (11511010,11511012,11511015,11511016,11511020,11511019,11511018,11511011,11511008) ORDER BY NUM ASC \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}

}
