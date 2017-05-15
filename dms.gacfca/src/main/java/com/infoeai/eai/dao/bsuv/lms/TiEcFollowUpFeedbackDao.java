package com.infoeai.eai.dao.bsuv.lms; 

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;


/**
 * @author wjs
 * @date 2016年5月9日 下午4:22:26
 */
@Repository
@SuppressWarnings("rawtypes")
public class TiEcFollowUpFeedbackDao extends OemBaseDAO {

	/* (non-Javadoc)
	 * @see com.infoservice.dms.cgcsl.dao.AbstractIFDao#wrapperVO(java.sql.ResultSet, int, int)
	 */
	
	public List<Map> getEcFollowUpFeedbackInfo(String from,String to){
		StringBuffer sql=new StringBuffer();
		sql.append("select  T.EC_ORDER_NO, T.ID, T.DEALER_CODE, T.TEL, T.TRAIL_DATE \n");
		sql.append("  from TI_EC_FOLLOW_UP_FEEDBACK_DCS T  \n");
		sql.append(" where 1=1 \n");
		sql.append("  AND ((T.CREATE_DATE>='"+from+"' and CREATE_DATE<='"+to+"' ) ");
		sql.append("  OR  (T.UPDATE_DATE >= '"+from+"' AND UPDATE_DATE<='"+to+"')) ");
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		return list;
	}
	
	

}
 