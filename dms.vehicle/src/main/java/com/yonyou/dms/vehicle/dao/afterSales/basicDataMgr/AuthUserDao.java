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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 授权人员管理
 * @author Administrator
 *
 */
@Repository
public class AuthUserDao extends OemBaseDAO{
	/**
	 * 查询
	 */
	public PageInfoDto  AuthUserQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT DISTINCT TU.USER_ID,\n" );
		sql.append("                TU.ACNT,\n" );
		sql.append("                TU.EMP_NUM,\n" );
		sql.append("                TU.NAME,\n" );
		sql.append("                TU.USER_STATUS,\n" );
		sql.append("                TU.COMPANY_ID,\n" );
		sql.append("                TU.PHONE,\n" );
		sql.append("                TU.EMAIL,\n" );
		sql.append("                TU.APPROVAL_LEVEL_CODE,\n" );
		sql.append("                TAWA.LEVEL_NAME,\n" );
		sql.append("                TU.PERSON_CODE,\n" );
		sql.append("                TU.UPDATE_DATE,\n" );
		sql.append("                TP.POSE_NAME,\n" );
		sql.append("                TU.CREATE_DATE\n" );
		sql.append("  FROM TC_POSE TP, TR_USER_POSE TUP, TC_USER TU\n" );
		sql.append("  LEFT JOIN (SELECT *\n" );
		sql.append("               FROM TT_WR_AUTHLEVEL_dcs TT\n" );
		sql.append("              WHERE 1 = 1\n" );
		if (!StringUtils.isNullOrEmpty(loginInfo.getCompanyId())) {
			sql.append(" AND TT.OEM_COMPANY_ID = '"+loginInfo.getCompanyId()+"'  ");
		}
		sql.append(" ) TAWA\n" );
		sql.append("    ON TU.APPROVAL_LEVEL_CODE = TAWA.LEVEL_CODE\n" );
		sql.append(" WHERE TU.USER_ID = TUP.USER_ID\n" );
		sql.append("   AND TP.POSE_ID = TUP.POSE_ID\n" );
		sql.append(" AND TU.USER_STATUS = '"+OemDictCodeConstants.STATUS_ENABLE+"' AND TP.POSE_BUS_TYPE='"+OemDictCodeConstants.POSE_BUS_TYPE_WR+"' AND TP.POSE_CODE LIKE 'WR%' \n");//有用的状态
		if (!StringUtils.isNullOrEmpty(loginInfo.getCompanyId())) {
			sql.append(" AND TU.COMPANY_ID = '"+loginInfo.getCompanyId()+"'  ");
		}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("name"))) {
				sql.append("AND   upper(tu.name) like '%"+queryParam.get("name")+"%'  \n");
			}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("code"))) {
				sql.append("AND    upper(tu.ACNT) like '%"+queryParam.get("code")+"%'  \n");
			}
		 if (!StringUtils.isNullOrEmpty(queryParam.get("level"))) {
				sql.append("AND   tu.approval_level_code= '"+queryParam.get("level")+"'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 查询所有授权顺序
	 * @param queryParams
	 * @return
	 * @throws ServiceBizException
	 */
	public List<Map> getAuthLevel(Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT LEVEL_TIER,LEVEL_CODE,LEVEL_NAME  from  TT_WR_AUTHLEVEL_dcs where OEM_COMPANY_ID= '"+loginInfo.getCompanyId()+"' ");

		return OemDAOUtil.findAll(sql.toString(), null);
	}
	

}
