package com.yonyou.dms.vehicle.dao.afterSales.preAuthorization;

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
 * 经销商端索赔预授权状态查询
 * @author Administrator
 *
 */
@Repository
public class PreclaimPreQueryDealerDao extends  OemBaseDAO{
	/**
	 * 经销商端索赔预授权状态查询
	 */
	public PageInfoDto PreclaimPreQueryDealerQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT a.ID,a.VIN,a.REPAIR_NO,a.FORE_NO,a.DEALER_CODE,a.APPLY_DATE,a.DEALER_NAME,a.STATUS \n" );
		sql.append("  FROM TT_WR_FOREAPPROVAL_dcs a \n" );
		sql.append(" WHERE a.IS_DEL = "+OemDictCodeConstants.IS_DEL_00+" \n");
		sql.append(" and  a.STATUS !="+OemDictCodeConstants.FORE_APPLOVAL_STATUS_01+" \n");
		sql.append(" and  a.DEALER_ID = "+loginInfo.getDealerId());
		//申请日期
		  if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
				sql.append("  AND  a.APPLY_DATE>= '"+queryParam.get("startdate")+"'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
				sql.append("   AND  a.APPLY_DATE<= '"+queryParam.get("enddate")+"'  \n");
			}
		  //预授权单号
		  if (!StringUtils.isNullOrEmpty(queryParam.get("foreNo"))) {
				sql.append("  AND  a.FORE_NO  like '%"+queryParam.get("foreNo")+"%'  \n");
			}
		  //VIN
		  if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sql.append("  AND   a.VIN  like '%"+queryParam.get("vin")+"%'  \n");
			}
		  
		  //工单号
		  if (!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))) {
				sql.append("  AND   a.REPAIR_NO  like '%"+queryParam.get("repairNo")+"%'  \n");
			}
		  
		  //处理状态
		  if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
				sql.append("  AND   a.STATUS = '"+queryParam.get("status")+"'  \n");
			}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	

}
