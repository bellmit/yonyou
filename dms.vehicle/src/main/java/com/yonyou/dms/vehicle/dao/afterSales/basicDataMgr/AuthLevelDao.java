package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

/**
 * 授权级别维护
 * @author Administrator
 *
 */
@Repository
public class AuthLevelDao extends OemBaseDAO{
	
	/**
	 * 授权级别维护查询
	 */
	public PageInfoDto  AuthLevelQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		
		  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		  StringBuffer sb = new StringBuffer();
		   sb.append(" select twa.LEVEL_CODE,twa.LEVEL_NAME,twa.LEVEL_TIER from TT_WR_AUTHLEVEL_dcs twa  ");
			sb.append(" where twa.LEVEL_CODE <> 100 \n");
			sb.append(" and twa.IS_DEL = 0 \n");
			sb.append(" and twa.OEM_COMPANY_ID = ").append(loginInfo.getCompanyId()).append("\n");
		    System.out.println(sb.toString());
			return sb.toString();
	}
	
	

}
