package com.yonyou.dms.vehicle.dao.afterSales.authorizeMgr;

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
 * 索赔自动审核规则管理 
 * @author Administrator
 *
 */
@Repository
public class AutohCheckRuleDao extends OemBaseDAO{
	/**
	 * 索赔自动审核规则管理查询
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
		  StringBuffer sql = new StringBuffer();
		    sql.append("select tawca.AUTO_ID,\n" );
			sql.append("       tawca.AUTO_TYPE,\n" );
			
			sql.append("      CASE WHEN tawca.STATUS =10011001  THEN '启动' ELSE  '停止' END  STATUS, \n" );
			
			sql.append("       tawca.REMARK,\n" );
			sql.append("       tawca.LEVEL_CODE,\n" );
			sql.append("       tawca.LEVEL_DESC  \n" );	
			sql.append("  from TT_WR_AUTO_RULE_dcs tawca \n" );
			sql.append(" where tawca.is_del=0 and  tawca.oem_company_id= ").append(loginInfo.getCompanyId()).append("\n");
		    System.out.println(sql.toString());
			return sql.toString();
	}
	
	/**
	 * 查询所有授权顺序
	 */
	 public List<Map> getAllLevel(Map<String, String> queryParam,Long id){
	        StringBuffer sqlStr = new StringBuffer();
	        sqlStr.append("SELECT distinct L.LEVEL_NAME,L.LEVEL_CODE \n" );
			sqlStr.append("FROM TT_WR_AUTHLEVEL_dcs L LEFT JOIN TT_WR_AUTO_RULE_dcs M\n" );
			sqlStr.append("ON L.LEVEL_CODE=M.LEVEL_CODE AND L.IS_DEL=0\n" );
			sqlStr.append("AND M.IS_DEL=0 AND M.AUTO_ID="+id+" \n" );
			  System.out.println(sqlStr.toString());
	    return OemDAOUtil.findAll(sqlStr.toString(),null); 
	}
	
	

}
