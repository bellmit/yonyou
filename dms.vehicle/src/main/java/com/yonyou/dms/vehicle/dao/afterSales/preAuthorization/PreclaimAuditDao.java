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
 * 索赔预授权审核作业 
 * @author Administrator
 *
 */
@Repository
public class PreclaimAuditDao extends OemBaseDAO{
	/**
	 * 索赔预授权审核作业查询
	 */
	public PageInfoDto PreclaimAuditSearch(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
		    System.out.println(pageInfoDto);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		sql.append("	 select  a.ID, a.DEALER_CODE, a.DEALER_NAME, a.FORE_NO, a.VIN, a.REPAIR_NO, DATE_FORMAT(APPLY_DATE, '%Y-%m-%d') as APPLY_DATE  \n");
		sql.append("     FROM TT_WR_FOREAPPROVAL_dcs a \n");
		
		sql.append("     where a.OEM_COMPANY_ID = "+loginInfo.getCompanyId()+" \n");//添加oem_company_id条件
		//sql.append("     AND a.CURRENT_AUTH_CODE = "+queryParam.get("levelCode") +" \n");
		sql.append("     AND a.STATUS IN ("+OemDictCodeConstants.FORE_APPLOVAL_STATUS_02+")  \n");//已接收
	  if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {//经销商代码不为空
			String returnStr = "";
			String[] paramStrArr = queryParam.get("dealerCode").split(",");
			if(paramStrArr.length > 0){
				sql.append(" and (");
				for(int i=0;i<paramStrArr.length;i++){
					returnStr+=" a.dealer_code='"+paramStrArr[i]+"' or\n";
				}
				sql.append(returnStr.substring(0, returnStr.length()-3));
				sql.append(")\n");				
			}			
		}
		//经销商名称不为空
			 if (!StringUtils.isNullOrEmpty(queryParam.get("dealerName"))) {
			sql.append("		AND a.DEALER_NAME like ('%"+ queryParam.get("dealerName")+"%')\n");
		}
			 if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {    //申请开始日期不为空
			sql.append("		AND a.APPLY_DATE  >=DATE_FORMAT('"+queryParam.get("startdate")+" 00:00:00', '%Y-%m-%d %H:%i:%s') \n");
		}
			 if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {          //申请结束日期不为空
			sql.append("	    AND a.APPLY_DATE  <= DATE_FORMAT('"+queryParam.get("enddate")+" 23:59:59', '%Y-%m-%d %H:%i:%s') \n");
		}
	       if (!StringUtils.isNullOrEmpty(queryParam.get("foNo"))) {  //预授权单号不为空
			sql.append("		AND UPPER(a.FORE_NO) like UPPER('%"+queryParam.get("foNo")+"%')\n");
		}
	       if (!StringUtils.isNullOrEmpty(queryParam.get("roNo"))) {		//维修工单号不为空
			sql.append("		AND UPPER(a.REPAIR_NO) like UPPER('%"+queryParam.get("roNo")+"%')\n");
		}
		if(!loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !loginInfo.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())){
			sql.append("      AND  a.DEALER_ID in ("+OemBaseDAO.getDealersByArea(loginInfo.getOrgId().toString())+")\n");
		}
		 System.out.println(sql.toString());
			return sql.toString();
	}
	
	

}
