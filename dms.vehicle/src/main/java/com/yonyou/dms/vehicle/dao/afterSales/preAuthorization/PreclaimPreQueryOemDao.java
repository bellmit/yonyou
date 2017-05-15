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
 * 索赔预授权状态查询
 * @author Administrator
 *
 */
@Repository
public class PreclaimPreQueryOemDao extends OemBaseDAO{
	
	/**
	 * 查询
	 * @param queryParam
	 * @return
	 */
	
	public PageInfoDto PreclaimPreQueryOemQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT a.ID,a.VIN,a.FORE_NO,a.REPAIR_NO,a.DEALER_CODE,DATE_FORMAT(APPLY_DATE, '%Y-%m-%d')  as APPLY_DATE,a.DEALER_NAME,a.STATUS \n" );		
		sql.append(" FROM TT_WR_FOREAPPROVAL_dcs a \n" );
		sql.append(" WHERE a.IS_DEL = "+OemDictCodeConstants.IS_DEL_00+" \n" );
		sql.append(" and a.STATUS != "+OemDictCodeConstants.FORE_APPLOVAL_STATUS_01+" \n" );
		/*sql.append(" and a.DEALER_CODE in(select dea.DEALER_CODE from TM_DEALER dea ,tm_org org,TM_DEALER_ORG_RELATION tdo where dea.DEALER_ID = tdo.DEALER_ID \n");
		sql.append(" and tdo.ORG_ID = org.ORG_ID and org.ORG_ID = "+loginInfo.getOrgId()+")");*/
		//申请日期
		  if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
				sql.append("AND a.APPLY_DATE>= '"+queryParam.get("startdate")+"'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
				sql.append("AND a.APPLY_DATE<= '"+queryParam.get("enddate")+"'  \n");
			}
		  //预授权单号
		  if (!StringUtils.isNullOrEmpty(queryParam.get("foreNo"))) {
				sql.append("AND a.FORE_NO  like '%"+queryParam.get("foreNo")+"%'  \n");
			}
		  //VIN
		  if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sql.append("AND a.VIN  like '%"+queryParam.get("vin")+"%'  \n");
			}
		  
		  //工单号
		  if (!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))) {
				sql.append("AND a.REPAIR_NO  like '%"+queryParam.get("repairNo")+"%'  \n");
			}
		  
		  //处理状态
		  if (!StringUtils.isNullOrEmpty(queryParam.get("status"))) {
				sql.append("AND a.STATUS = '"+queryParam.get("status")+"'  \n");
			}
		  //经销商代码
		  if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
				sql.append("AND a.DEALER_CODE  = '"+queryParam.get("dealerCode")+"'  \n");
			}
		  
	if(!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())){
			sql.append("      AND  a.DEALER_ID in ("+OemBaseDAO.getDealersByArea(loginInfo.getOrgId().toString())+")\n");	
	}
		 System.out.println(sql.toString());
			return sql.toString();
	}

}
