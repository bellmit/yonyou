package com.yonyou.dms.web.dao.inter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;
@Repository
public class InterfaceDao extends OemBaseDAO{
	/**
	 * 查询方法
	 * 
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getInterfaceMsgList(Map<String, String> queryParam) {
		System.out.println("进入查询方法");
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		System.out.println(sql+params.toString());
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}
	
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT MSG_ID,MSG_FROM,MSG_TO,BIZ_TYPE,CASE WHEN MSG_TYPE=2 THEN '接收' ELSE '未接收' END MSG_TYPE,MSG_FILE_ID,CASE WHEN PROCESS=0 THEN '完成' ELSE '未完成' END PROCESS,"
				+ "DATE_FORMAT(CREATE_DATE,'%Y-%m-%d ') AS CREATE_DATE,TRY_TIMES,DATE_FORMAT(LAST_TRY_TIME,'%Y-%m-%d ') AS LAST_TRY_TIME \n");
		sql.append(" FROM DE_MSG_INFO WHERE 1=1 \n");
		if (!StringUtils.isNullOrEmpty(queryParam.get("bizType"))) {
			sql.append(" and BIZ_TYPE = ?");
			params.add(queryParam.get("bizType"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("msgType"))) {
			sql.append(" and MSG_TYPE = ?");
			params.add(queryParam.get("msgType"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("process"))) {
			sql.append(" and PROCESS = ?");
			params.add(queryParam.get("process"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("beginDateTB"))) {
			sql.append(" and DATE(CREATE_DATE) >= ?");
			params.add(queryParam.get("beginDateTB"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDateTB"))) {
			sql.append(" and DATE(CREATE_DATE) <= ?");
			params.add(queryParam.get("endDateTB"));
		}


		System.out.println(sql.toString());
		return sql.toString();
		
	}

}
