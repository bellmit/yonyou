package com.yonyou.dms.manage.dao.personInfoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 经销商权限切换
 * @author Administrator
 *
 */
@Repository
public class DealerSwitchDao extends OemBaseDAO {
	
	
	//查询所有经销商信息

	public PageInfoDto dealerSwitchQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	
	}

	//sql语句组装
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		String dealerCode = queryParam.get("dealerCode");
		StringBuilder sql = new StringBuilder();
		sql.append(" \n");
		sql.append("SELECT D.DEALER_SHORTNAME, -- 经销商 \n");
		sql.append("       U.ACNT, -- 登录账号 \n");
		sql.append("       U.PASSWORD -- 登录密码 \n");
		sql.append("  FROM TM_DEALER D \n");
		sql.append(" INNER JOIN TC_USER U ON U.COMPANY_ID = D.COMPANY_ID \n");
		sql.append(" WHERE 1=1");
		sql.append(" AND D.STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append(" AND D.DEALER_TYPE = '" + OemDictCodeConstants.DEALER_TYPE_DVS + "' \n");
		sql.append("   AND U.USER_STATUS = '" + OemDictCodeConstants.STATUS_ENABLE + "' \n");
		sql.append("   AND U.USER_TYPE = '" + OemDictCodeConstants.SYS_USER_DEALER + "' \n");
		sql.append("   AND U.ACNT LIKE 'S%" + CommonUtils.checkNull(dealerCode) + "' \n");
	    if (!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))) {
            sql.append(" AND D.DEALER_SHORTNAME LIKE ?");
            params.add("%" + queryParam.get("groupCode") + "%");
	    }
	    System.out.println(sql+"\n");
		return sql.toString();
	}
}