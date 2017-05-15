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
public class WarrantyMainPartAndDefectDao extends OemBaseDAO {

	/**
	 * 主因件与故障码查询
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
	 * 主因件与故障码下载
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
		sql.append("SELECT TWOD.ID,TWOD.OLDPT_CODE,TWOD.DEF_CODE,tc.CODE_DESC STATUS, \n");
		sql.append("TU2.NAME UPDATE_BY,TU1.NAME CREATE_BY,TWOD.CREATE_DATE,TWOD.UPDATE_DATE \n");
		sql.append("FROM TT_WR_OLDPT_DEF_DCS TWOD left join tc_code_dcs tc on tc.CODE_ID=TWOD.STATUS \n");
		sql.append("LEFT OUTER JOIN Tc_user TU1 ON TU1.USER_ID=TWOD.CREATE_BY \n");
		sql.append("LEFT OUTER JOIN Tc_user TU2 ON TU2.USER_ID=TWOD.UPDATE_BY WHERE 1=1 \n");
		//主因件
        if(!StringUtils.isNullOrEmpty(queryParam.get("oldptCode"))){ 
        	sql.append("AND TWOD.OLDPT_CODE LIKE '%" + queryParam.get("oldptCode") + "%' \n");
        } 
		//故障代码
        if(!StringUtils.isNullOrEmpty(queryParam.get("defCode"))){ 
        	sql.append("AND TWOD.DEF_CODE LIKE '%" + queryParam.get("defCode") + "%' \n");
        } 
        if(operate.equals("down")){//下载排序
        	sql.append("ORDER BY TWOD.ID DESC,TWOD.UPDATE_DATE DESC \n");
        }
		System.out.println(sql.toString());
		return sql.toString();
	}
	
}
