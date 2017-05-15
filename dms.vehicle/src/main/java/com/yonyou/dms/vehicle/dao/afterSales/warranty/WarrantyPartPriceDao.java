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
public class WarrantyPartPriceDao extends OemBaseDAO{
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
		sql.append("select TWPP.ID,TWPP.MODE_CODE,TWPP.RATE,TVMG.GROUP_NAME,TWPP.MVS,TWPP.STATUS, \n");
		sql.append("TU1.NAME CREATE_BY,TU1.NAME UPDATE_BY,TWPP.CREATE_DATE,TWPP.UPDATE_DATE \n");
		sql.append("from TT_WR_PART_PRICE_DCS TWPP left OUTER JOIN tm_vhcl_material_group TVMG \n");
		sql.append("on TWPP.MODE_CODE=TVMG.GROUP_CODE LEFT OUTER JOIN Tc_user TU1 ON TU1.USER_ID=TWPP.CREATE_BY \n");
		sql.append("LEFT OUTER JOIN Tc_user TU2 ON TU2.USER_ID=TWPP.UPDATE_BY WHERE 1=1 \n");
        if(!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))){ 
        	sql.append(" 	AND TWPP.MODE_CODE = " + queryParam.get("seriesCode") + " \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("mvs"))){ 
        	sql.append(" 	AND TWPP.MVS = " + queryParam.get("mvs") + " \n");
        }
        if(operate.equals("down")){//下载排序
        	sql.append("ORDER BY TWPP.ID asc \n");
        }
		return sql.toString();
	}
	
	/**
	 * 查询MVS
	 */
	public List<Map> getMVS(Map<String, String> queryParams,String seriesCode) throws ServiceBizException {
		StringBuffer sql  = new StringBuffer("\n");
		sql.append("select MVS from tm_vhcl_material_group \n");
		sql.append("where MVS is not null and MVS<>'' \n");
		if(!StringUtils.isNullOrEmpty(seriesCode)){ 
	        sql.append("AND PARENT_GROUP_ID in (select GROUP_ID from tm_vhcl_material_group where GROUP_CODE='"+seriesCode+"') \n");
		}
		sql.append("group by MVS \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
}
