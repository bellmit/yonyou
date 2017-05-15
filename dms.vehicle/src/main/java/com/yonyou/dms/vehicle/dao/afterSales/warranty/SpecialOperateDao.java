package com.yonyou.dms.vehicle.dao.afterSales.warranty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class SpecialOperateDao extends OemBaseDAO{
	/**
	 *查询
	 */
	public PageInfoDto query(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params,"query");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 下载
	 */
	public List<Map> getDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params,"down");
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}
	/**
	 * SQL组装   
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params,String operate) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWO.ID,TWO.OPT_CODE,TWO.OPT_NAME_EN,TWO.OPT_NAME_CN,TWO.INVOICE_CODE,TWO.STATUS, \n");
		sql.append("TU1.NAME CREATE_BY,TU2.NAME UPDATE_BY,TWO.CREATE_DATE,TWO.UPDATE_DATE \n");
		sql.append("FROM TT_WR_OPERATE_DCS TWO LEFT OUTER JOIN Tc_user TU1 ON TU1.USER_ID=TWO.CREATE_BY \n");
		sql.append("LEFT OUTER JOIN Tc_user TU2 ON TU2.USER_ID=TWO.UPDATE_BY WHERE 1=1 AND TWO.IS_SP=10041001 \n");
        if(!StringUtils.isNullOrEmpty(queryParam.get("optCode"))){ 
        	sql.append(" 	AND TWO.OPT_CODE like '%" + queryParam.get("optCode") + "%' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("invoiceCode"))){ 
        	sql.append(" 	AND TWO.INVOICE_CODE like '%" + queryParam.get("invoiceCode") + "%' \n");
        }
        if(operate.equals("down")){//下载排序
        	sql.append("ORDER BY TWO.ID asc \n");
        }
		return sql.toString();
	}
}
