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
 * @author xuqinqin
 * @date 2017年04月25日
 */
@Repository
public class WarrantyMainPartDao extends OemBaseDAO {

	/**
	 * 主因件查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getMainPartQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getMainPartQuerySql(queryParam, params,"query");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 主因件下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getMainPartDownloadList(Map<String, String> queryParam) {
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
		sql.append("SELECT TWMO.OLDPT_CODE,TWMO.OLDPT_NAME,TWMO.ID,TC.CODE_CN_DESC AS STATUS,TU1.NAME AS CREATE_BY,TU2.NAME AS UPDATE_BY, \n  ");
		sql.append("  DATE_FORMAT(TWMO.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,DATE_FORMAT(TWMO.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE   \n");
		sql.append(" FROM TT_WR_MAIN_OLDPART_DCS TWMO   \n");
		sql.append(" LEFT JOIN TC_CODE TC ON TC.CODE_ID =TWMO.STATUS  \n");
		sql.append(" LEFT OUTER JOIN TC_USER TU1 ON TU1.USER_ID=TWMO.CREATE_BY	 \n");
		sql.append(" LEFT OUTER JOIN TC_USER TU2 ON TU2.USER_ID=TWMO.UPDATE_BY  WHERE 1=1	\n  ");
		//主因件代码
        if(!StringUtils.isNullOrEmpty(queryParam.get("oldptCode"))){ 
        	sql.append(" 	AND TWMO.OLDPT_CODE LIKE '%" + queryParam.get("oldptCode") + "%' \n");
        } 
		//主因件描述
        if(!StringUtils.isNullOrEmpty(queryParam.get("oldptName"))){ 
        	sql.append(" 	AND TWMO.OLDPT_NAME LIKE '%" + queryParam.get("oldptName") + "%' \n");
        } 
        if(operate.equals("down")){//下载排序
        	sql.append(" 	ORDER BY TWMO.ID DESC,TWMO.UPDATE_DATE DESC \n");
        }
		System.out.println(sql.toString());
		return sql.toString();
	}
	
}
