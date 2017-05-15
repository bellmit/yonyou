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
public class OperateAndMainPartDao extends OemBaseDAO {

	/**
	 * 操作代码与主因件查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getOptCodeQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params,"query");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 操作代码与主因件下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getOptCodeDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params,"down");
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装操作代码与主因件
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params,String operate) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TWOO.ID,TWOO.OLDPT_CODE,TWOO.OPT_CODE,TWOO.PT_TAG,TC.CODE_CN_DESC AS STATUS,TU1.NAME AS CREATE_BY,TU2.NAME AS UPDATE_BY, \n  ");
		sql.append("  DATE_FORMAT(TWOO.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,DATE_FORMAT(TWOO.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE   \n");
		sql.append(" FROM TT_WR_OPT_OLDPT_DCS TWOO   \n");
		sql.append(" LEFT JOIN TC_CODE TC ON TC.CODE_ID =TWOO.STATUS  \n");
		sql.append(" LEFT OUTER JOIN TC_USER TU1 ON TU1.USER_ID=TWOO.CREATE_BY	 \n");
		sql.append(" LEFT OUTER JOIN TC_USER TU2 ON TU2.USER_ID=TWOO.UPDATE_BY  WHERE 1=1	\n  ");
		//操作代码
        if(!StringUtils.isNullOrEmpty(queryParam.get("optCode"))){ 
        	sql.append(" 	AND TWOO.OPT_CODE LIKE '%" + queryParam.get("optCode") + "%' \n");
        } 
		//配件标记
        if(!StringUtils.isNullOrEmpty(queryParam.get("ptTag"))){ 
        	sql.append(" 	AND TWOO.PT_TAG ='" + queryParam.get("ptTag") + "' \n");
        }
       //主因件代码
        if(!StringUtils.isNullOrEmpty(queryParam.get("oldptCode"))){ 
        	sql.append(" 	AND TWOO.OLDPT_CODE LIKE '%" + queryParam.get("oldptCode") + "%' \n");
        }
        if(operate.equals("down")){//下载排序
        	sql.append(" 	ORDER BY TWOO.ID DESC,TWOO.UPDATE_DATE DESC \n");
        }
		System.out.println(sql.toString());
		return sql.toString();
	}
	
}
