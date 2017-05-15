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
 * 索赔其他费用设定
 * @author Administrator
 *
 */
@Repository
public class OtherFeeDao extends OemBaseDAO{
	/**
	 * 索赔其他费用设定查询
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
		  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("	 select two.OTHER_FEE_ID,two.OTHER_FEE_CODE,two.OTHER_FEE_NAME,two.IS_DOWN,two.IS_DEL  \n");
		sql.append("     from TT_WR_OTHERFEE_dcs two \n");
		sql.append("     where  two.IS_DEL="+OemDictCodeConstants.IS_DEL_00+"      \n");
		if(!"".equals(loginInfo.getOemCompanyId())&&!(null==loginInfo.getOemCompanyId())){//公司ID不为空
			sql.append(" and two.OEM_COMPANY_ID = '"+loginInfo.getOemCompanyId()+"' \n");
		}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("otherFeeCode"))) {
				sql.append("AND  two.OTHER_FEE_CODE like '%"+queryParam.get("otherFeeCode")+"%'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("otherFeeName"))) {
				sql.append("AND  two.OTHER_FEE_NAME like '%"+queryParam.get("otherFeeName")+"%'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	

}
