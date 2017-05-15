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
public class SpecialPartDao extends OemBaseDAO{
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
		sql.append("select TWSP.ID,TWSP.MVS,TWSP.PT_CODE,TWSP.PT_NAME,TWSP.STATUS,TWSP.WR_PRICE, \n");
		sql.append("TU1.NAME CREATE_BY,TU1.NAME UPDATE_BY,TWSP.CREATE_DATE,TWSP.UPDATE_DATE \n");
		sql.append("FROM TT_WR_SPECIAL_PART_DCS TWSP \n");
		sql.append("LEFT OUTER JOIN Tc_user TU1 ON TU1.USER_ID=TWSP.CREATE_BY \n");
		sql.append("LEFT OUTER JOIN Tc_user TU2 ON TU2.USER_ID=TWSP.UPDATE_BY WHERE 1=1 \n");
        if(!StringUtils.isNullOrEmpty(queryParam.get("mvs"))){ 
        	sql.append(" 	AND TWSP.MVS = '" + queryParam.get("mvs") + "' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("ptCode"))){ 
        	sql.append(" 	AND TWSP.PT_CODE like '%" + queryParam.get("ptCode") + "%' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("ptName"))){ 
        	sql.append(" 	AND TWSP.PT_NAME like '%" + queryParam.get("ptName") + "%' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("status"))){ 
        	sql.append(" 	AND TWSP.STATUS = " + queryParam.get("status") + " \n");
        }
        if(operate.equals("down")){//下载排序
        	sql.append("ORDER BY TWSP.ID asc \n");
        }
		return sql.toString();
	}
	
	/**
	 * 查询MVS
	 */
	public List<Map> getMVS(Map<String, String> queryParams) throws ServiceBizException {
		StringBuffer sql  = new StringBuffer("\n");
		sql.append("select MVS from tm_vhcl_material_group \n");
		sql.append("where MVS is not null and MVS<>'' \n");
		sql.append("group by MVS \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
}
