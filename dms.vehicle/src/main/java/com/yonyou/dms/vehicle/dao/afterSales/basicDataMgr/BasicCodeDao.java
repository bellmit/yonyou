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
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 代码维护
 * @author Administrator
 *
 */
@Repository
public class BasicCodeDao extends OemBaseDAO{
	/**
	 * 代码维护查询
	 */
	public PageInfoDto  BasicCodeQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		
		  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("	 select twbc.BASE_ID,twbc.BASE_CODE,twbc.BASE_NAME,twbc.BASE_TYPE  \n");
		sql.append("     from TT_WR_BASE_CODE_DCS twbc \n");
		sql.append("     where  twbc.IS_DEL="+OemDictCodeConstants.IS_DEL_00+"      \n");
      if(!"".equals(loginInfo.getCompanyId())&&!(null==loginInfo.getCompanyId())){//公司ID不为空
			sql.append(" and twbc.OEM_COMPANY_ID = '"+loginInfo.getCompanyId()+"' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("BASE_TYPE"))) {
				sql.append("AND twbc.BASE_TYPE like '%"+queryParam.get("BASE_TYPE")+"%'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	

}
