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
public class WarrantyDefectCodeDao extends OemBaseDAO {

	/**
	 * 缺陷代码查询
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public PageInfoDto getDefectCodeQueryList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDefectCodeQuerySql(queryParam, params,"query");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}

	/**
	 * 缺陷代码下载
	 * 
	 * @param queryParam
	 * @return pageInfoDto
	 */
	public List<Map> getDefectCodeDownloadList(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDefectCodeQuerySql(queryParam, params,"down");
		List<Map> orderList = OemDAOUtil.downloadPageQuery(sql, params);
		return orderList;
	}

	/**
	 * SQL组装缺陷代码
	 * 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getDefectCodeQuerySql(Map<String, String> queryParam, List<Object> params,String operate) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT TWD.DEF_CODE,TWD.DEF_NAME,TWD.ID,TC.CODE_CN_DESC AS STATUS,TU1.NAME AS CREATE_BY,TU2.NAME AS UPDATE_BY, \n  ");
		sql.append("  DATE_FORMAT(TWD.CREATE_DATE,'%Y-%m-%d') CREATE_DATE,DATE_FORMAT(TWD.UPDATE_DATE,'%Y-%m-%d') UPDATE_DATE   \n");
		sql.append(" FROM TT_WR_DEFECT_DCS TWD   \n");
		sql.append(" LEFT JOIN TC_CODE TC ON TC.CODE_ID =TWD.STATUS  \n");
		sql.append(" LEFT OUTER JOIN TC_USER TU1 ON TU1.USER_ID=TWD.CREATE_BY	 \n");
		sql.append(" LEFT OUTER JOIN TC_USER TU2 ON TU2.USER_ID=TWD.UPDATE_BY  WHERE 1=1	\n  ");
		//缺陷代码
        if(!StringUtils.isNullOrEmpty(queryParam.get("defCode"))){ 
        	sql.append(" 	AND TWD.DEF_CODE LIKE '%" + queryParam.get("defCode") + "%' \n");
        } 
		//缺陷代码描述
        if(!StringUtils.isNullOrEmpty(queryParam.get("defName"))){ 
        	sql.append(" 	AND TWD.DEF_NAME LIKE '%" + queryParam.get("defName") + "%' \n");
        }
        if(operate.equals("down")){//下载排序
        	sql.append(" 	ORDER BY TWD.ID DESC,TWD.UPDATE_DATE DESC \n");
        }
		System.out.println(sql.toString());
		return sql.toString();
	}
	
}
