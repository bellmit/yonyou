package com.yonyou.dcs.dao;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 出库信息or作废信息上报接口
 * @author luoyang
 *
 */
@Repository
public class SEDMS067Dao extends OemBaseDAO {
	
	public void updateApplyNumber(Long releaseId,Integer applyNumber){
		StringBuffer sql = new StringBuffer();
		sql.append("update TT_OBSOLETE_MATERIAL_RELEASE set APPLY_NUMBER=(APPLY_NUMBER+"+applyNumber+")	\n");
		sql.append("	where RELEASE_ID="+releaseId+"	\n");
		OemDAOUtil.execBatchPreparement(sql.toString(), null);
	}

}
