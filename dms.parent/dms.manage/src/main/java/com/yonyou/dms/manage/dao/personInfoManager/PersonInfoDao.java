package com.yonyou.dms.manage.dao.personInfoManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
 * 个人信息维护
 * @author Administrator
 *
 */
@Repository
public class PersonInfoDao extends OemBaseDAO {
	/**
	 * 个人信息维护查询
	 * @param queryParam
	 * @param loginInfo
	 * @return
	 */
	public PageInfoDto personInfoQuery(Map<String, String> queryParam,LoginInfoDto loginInfo) {
		
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,loginInfo, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		return pageInfoDto;
	}

	/**
	 * 组装sql语句
	 * @param queryParam
	 * @param loginInfo
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, LoginInfoDto loginInfo, List<Object> params) {
	       StringBuffer sb = new StringBuffer();
	       sb.append("SELECT O.ORG_NAME,P.POSE_NAME ");
			sb.append(" FROM TR_USER_POSE U LEFT JOIN TC_POSE P ON U.POSE_ID = P.POSE_ID ");
			sb.append(" LEFT JOIN TM_ORG O ON P.ORG_ID = O.ORG_ID AND P.COMPANY_ID = O.COMPANY_ID ");   
			sb.append(" WHERE 1=1 ");
			if (loginInfo.getCompanyId() != null) {
				sb.append(" AND P.COMPANY_ID = ? ");
				params.add(loginInfo.getCompanyId());
			}
			
			if (loginInfo.getOrgId() != null) {
				sb.append(" AND P.ORG_ID = ? ");
				params.add(loginInfo.getOrgId());
			}
			
			if (loginInfo.getUserId() != null) {
				sb.append(" AND U.USER_ID = ? ");
				params.add(loginInfo.getUserId());
			}
			System.out.println("sql:"+sb+"\n"+params);
	        return sb.toString() ;
	}
	
	
}
