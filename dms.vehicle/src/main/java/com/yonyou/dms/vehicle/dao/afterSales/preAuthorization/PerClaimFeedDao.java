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
 * 索陪预授权反馈查询
 * @author Administrator
 *
 */
@Repository
public class PerClaimFeedDao extends OemBaseDAO{
	/**
	 * 索陪预授权反馈信息查询
	 */
	public PageInfoDto PerClaimFeedQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("SELECT a.ID,a.DEALER_CODE,a.DEALER_NAME,a.VIN,a.FORE_NO,a.REPAIR_NO,a.APPLY_DATE,a.APPLY_MAN \n" );
		sql.append("  FROM TT_WR_FOREAPPROVAL_dcs a,TT_WR_FOREAPPROVAL_FEEDBACK_dcs b\n" );
		sql.append(" WHERE a.IS_DEL = "+OemDictCodeConstants.IS_DEL_00+" \n" );
		sql.append(" and  b.FORE_ID = a.ID \n" );
		//经销商代码
		  if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
				sql.append("AND a.DEALER_CODE = '"+queryParam.get("dealerCode")+"'  \n");
			}
		  //工单号
		  if (!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))) {
				sql.append("AND a.REPAIR_NO like '%"+queryParam.get("repairNo")+"%'  \n");
			}
		//申请日期
		  if (!StringUtils.isNullOrEmpty(queryParam.get("startDate"))) {
				sql.append("AND a.APPLY_DATE>= '"+queryParam.get("startDate")+"'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
				sql.append("AND a.APPLY_DATE<= '"+queryParam.get("endDate")+"'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("foreNo"))) {
				sql.append("AND a.FORE_NO  like '%"+queryParam.get("foreNo")+"%'  \n");
			}
		  if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
				sql.append("AND a.VIN  like '%"+queryParam.get("vin")+"%'  \n");
			}
		
		if(!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())){
			sql.append("      AND  a.DEALER_ID in ("+OemBaseDAO.getDealersByArea(loginInfo.getOrgId().toString())+")\n");	
	}
		 System.out.println(sql.toString());
			return sql.toString();
	}
		
	

}
