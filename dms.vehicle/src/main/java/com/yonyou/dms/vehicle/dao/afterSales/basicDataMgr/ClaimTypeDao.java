package com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 索赔类型维护
 * @author Administrator
 *
 */
@Repository
public class ClaimTypeDao  extends OemBaseDAO{ 
	
	/**
	 * 索赔类型维护查询
	 */
	public PageInfoDto  ClaimTypeQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TWC.ID,TWC.CLAIM_CATEGORY,TWC.CLAIM_TYPE_CODE,TWC.CLAIM_TYPE \n");
		sql.append("FROM TT_WR_CLAIMTYPE_DCS TWC WHERE 1=1 ");
		  if (!StringUtils.isNullOrEmpty(queryParam.get("conType"))) {
				sql.append("AND claim_category = "+queryParam.get("conType")+"  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	

}
