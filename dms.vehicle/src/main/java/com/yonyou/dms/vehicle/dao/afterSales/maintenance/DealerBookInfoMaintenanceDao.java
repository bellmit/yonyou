package com.yonyou.dms.vehicle.dao.afterSales.maintenance;

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
 * 经销商微信预约专员维护
 * @author Administrator
 *
 */
@Repository
public class DealerBookInfoMaintenanceDao extends OemBaseDAO{
	
	/**
	 * 经销商微信预约专员维护查询
	 */
	public PageInfoDto DealerBookInfoMaintenanceQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		  LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String tmpt=loginInfo.getDealerCode();
		//String dealer_code=tmpt.endsWith("A")?tmpt.substring(0,tmpt.length()-1):tmpt;
		sql.append("	select twrs.DEALER_CODE,twrs.ID,twrs.NAME,twrs.TELEPHONE from TM_WX_RESERVE_SPECIALIST_dcs twrs where twrs.status="+OemDictCodeConstants.STATUS_ENABLE+" and twrs.is_del='0'\n ");//有效未删除
		sql.append(" and twrs.DEALER_CODE ='"+tmpt+"'\n") ; 
		  if (!StringUtils.isNullOrEmpty(queryParam.get("name"))) {
				sql.append("AND NAME like '%"+queryParam.get("name")+"%'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("telephone"))) {
				sql.append("AND TELEPHONE like '%"+queryParam.get("telephone")+"%'  \n");
		  }
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	
	
	

}
