package com.yonyou.dms.vehicle.dao.afterSales.warranty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author zhanghongyi
 */
@Repository
public class WarrantyMainPartAndLocDao extends OemBaseDAO {

	/**
	 * 主因件与位置代码查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getMainPartQuerySql(queryParam, params,"query");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 主因件与位置代码下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getMainPartQuerySql(queryParam, params,"down");
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装主因件
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getMainPartQuerySql(Map<String, String> queryParam, List<Object> params,String operate) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TWOL.ID,TWOL.OLDPT_CODE,TWOL.LOC_CODE,tc.CODE_DESC STATUS, \n");
		sql.append("TU2.NAME UPDATE_BY,TU1.NAME CREATE_BY,TWOL.CREATE_DATE,TWOL.UPDATE_DATE \n");
		sql.append("FROM TT_WR_OLDPT_LOC_DCS TWOL left join tc_code_dcs tc on tc.CODE_ID=TWOL.STATUS\n");
		sql.append("LEFT OUTER JOIN Tc_user TU1 ON TU1.USER_ID=TWOL.CREATE_BY \n");
		sql.append("LEFT OUTER JOIN Tc_user TU2 ON TU2.USER_ID=TWOL.UPDATE_BY WHERE 1=1 \n");
		//主因件
        if(!StringUtils.isNullOrEmpty(queryParam.get("oldptCode"))){ 
        	sql.append("AND TWOL.OLDPT_CODE LIKE '%" + queryParam.get("oldptCode") + "%' \n");
        } 
		//位置代码
        if(!StringUtils.isNullOrEmpty(queryParam.get("locCode"))){ 
        	sql.append("AND TWOL.LOC_CODE LIKE '%" + queryParam.get("locCode") + "%' \n");
        } 
        if(operate.equals("down")){//下载排序
        	sql.append("ORDER BY TWOL.ID DESC,TWOL.UPDATE_DATE DESC \n");
        }
		System.out.println(sql.toString());
		return sql.toString();
	}
	
}
