package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domains.PO.baseData.DictPO;

/**
 * 
* @ClassName: SalesLeadsFeedbackCreateDao 
* @Description: DCC建档客户信息反馈接收
* @author zhengzengliang 
* @date 2017年4月13日 上午10:26:22 
*
 */
@Repository
public class SalesLeadsFeedbackCreateDao extends OemBaseDAO{
	
	@SuppressWarnings("rawtypes")
	public String getRelationIdInfo(Integer id) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("");
		sql.append("   SELECT TCR.RELATION_EN \n");
		sql.append("    FROM TC_RELATION TCR, TC_CODE_DCS TCC  \n");
		sql.append("      WHERE TCR.CODE_ID = TCC.CODE_ID AND TCC.TYPE = ?\n");
		sql.append("     AND TCC.CODE_ID= ? \n");
		params.add(1310);
		params.add(id);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		if(list.size()>0){
			return list.get(0).get("RELATION_EN").toString();
		}else{
			return null;
		}
	}

}
