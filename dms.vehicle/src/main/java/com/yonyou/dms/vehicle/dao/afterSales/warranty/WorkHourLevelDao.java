package com.yonyou.dms.vehicle.dao.afterSales.warranty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class WorkHourLevelDao extends OemBaseDAO{
	/**
	 *查询
	 */
	public PageInfoDto query(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select TWWL.ID,TWWL.DER_LEVEL,TWWL.WT_CODE,TWWT.WT_NAME,TWWL.WORK_PRICE, \n");
		sql.append("TWWL.STATUS,TWWL.REMARK,TU.NAME UPDATE_BY,TWWL.UPDATE_DATE \n");
		sql.append("from TT_WR_WORRKHOUR_LEVEL_DCS TWWL LEFT OUTER JOIN  TT_WR_WARRANTY_TYPE_DCS TWWT on TWWL.WT_ID=TWWT.ID	\n");
		sql.append("LEFT OUTER JOIN Tc_user TU ON TU.USER_ID=TWWL.UPDATE_BY	WHERE 1=1 \n");
        if(!StringUtils.isNullOrEmpty(queryParam.get("derLevel"))){ 
        	sql.append(" 	AND TWWL.DER_LEVEL = " + queryParam.get("derLevel") + " \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("wtId"))){ 
        	sql.append(" 	AND TWWL.WT_ID = " + queryParam.get("wtId") + " \n");
        }
		return sql.toString();
	}
	
	/**
	 * 查询保修类型
	 */
	public List<Map> getWarrantyType(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql  = new StringBuffer("\n");
		sql.append("select ID WT_ID,WT_CODE,WT_NAME from tt_wr_warranty_type_dcs \n");
		sql.append("where STATUS=10011001 \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
}
