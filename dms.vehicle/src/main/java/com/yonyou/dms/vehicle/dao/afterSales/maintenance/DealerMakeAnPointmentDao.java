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
/**
 * 微信预约时段空闲度设定
 * @author Administrator
 *
 */
@Repository
public class DealerMakeAnPointmentDao extends OemBaseDAO{
	/**
	 * 微信预约时段空闲度设定查询
	 */
	public PageInfoDto DealerMakeAnPointmentQuery(Map<String, String> queryParam) {
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
		String dealer_code=tmpt.endsWith("A")?tmpt.substring(0,tmpt.length()-1):tmpt;
		sql.append("	select ID,RESERVE_DESC,RESERVE_NUM_LIMIT from TM_WX_RESERVER_ITEM_DEF_dcs where DEALER_CODE='"+dealer_code+"' \n ");
		 System.out.println(sql.toString());
			return sql.toString();
	}
	

}
