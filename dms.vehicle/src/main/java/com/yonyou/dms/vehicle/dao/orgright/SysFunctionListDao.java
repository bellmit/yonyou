package com.yonyou.dms.vehicle.dao.orgright;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

@Repository
public class SysFunctionListDao {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	/**
	 * 查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto queryList(Map<String, String> queryParam, LoginInfoDto loginInfo) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,params,loginInfo);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * SQL组装
	 * @param queryParam
	 * @param params
	 * @param loginInfo
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params, LoginInfoDto loginInfo) {
		StringBuffer sql=new StringBuffer();
	    sql.append("SELECT TC.FUNC_ID,\n");
	    sql.append("       TC.PAR_FUNC_ID,\n");  
	    sql.append("       TC.FUNC_CODE,\n");  
	    sql.append("       TC.FUNC_NAME,\n");  
	    sql.append("       TC.FUNC_TYPE,\n");  
	    sql.append("       TC.CREATE_BY,\n");  
	    sql.append("       TC.CREATE_DATE,\n");  
	    sql.append("       TC.UPDATE_BY,\n");
	    sql.append("       TC.UPDATE_DATE,\n");
	    sql.append("       TC.CHECK_FLAG\n");
	    sql.append("  FROM TC_FUNC TC\n");  
	    sql.append(" where  1=1   \n"); 
	    if(!StringUtils.isNullOrEmpty(queryParam.get("funcCode"))){
	    	sql.append("   AND TC.FUNC_CODE LIKE ?\n");  
	    	params.add("%" + queryParam.get("funcCode") + "%");
	    }
	    if(!StringUtils.isNullOrEmpty(queryParam.get("groupType"))){
	    	sql.append("   AND TC.FUNC_TYPE = ?\n");
	    	params.add(queryParam.get("groupType"));
	    }
		logger.debug("SQL-------: " + sql.toString() +"  "+params.toString());
		return sql.toString();
	}

}
