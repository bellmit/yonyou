package com.infoeai.eai.dao.bsuv.lms; 

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;


/**
 * @author wjs
 * @date 2016年5月9日 下午4:22:26
 */
@Repository
@SuppressWarnings("rawtypes")
public class TiEcHitSingleDao extends OemBaseDAO {
	
	
	
	public List<Map> getEcHitSingleFeedbackInfo(String from,String to){
		StringBuffer sql=new StringBuffer();
		sql.append("select  ID, DEALER_NAME, DEALER_CODE,  TEL, SOLD_BY, SOLD_MOBILE, IS_HIT_SINGLE  \n");
		sql.append("  from TI_EC_HIT_SINGLE_DCS T  \n");
		sql.append(" where 1=1 \n");
		//sql.append("  AND (");
		//sql.append("(T.CREATE_DATE>='"+from+"' and CREATE_DATE<='"+to+"' ) ");
		sql.append(" AND   (T.UPDATE_DATE >= '"+from+"' AND UPDATE_DATE<='"+to+"') ");
		sql.append(" AND T.IS_HIT_SINGLE is not null");
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		
		return list;
	}
	/**
	 * 校验是否是官网经销商
	 * @return
	 */
	public String checkIsEcDealer(String dealerCode){
		StringBuffer sql=new StringBuffer();
		sql.append("  select * from TM_DEALER ");
		sql.append("   where 1=1 ");
		sql.append("   AND DEALER_CODE='"+dealerCode+"' ");
		sql.append("   AND STATUS="+OemDictCodeConstants.STATUS_ENABLE);
		sql.append("   AND IS_EC="+OemDictCodeConstants.IF_TYPE_YES);
		List<Map> list= OemDAOUtil.findAll(sql.toString(), null);
		if (list.size()>0) {
			return "";
		}
		return "【经销商不存在，或者经销商不是官网经销商】";
	}
	
	

}
 