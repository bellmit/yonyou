package com.yonyou.dms.manage.dao.personInfoManager;


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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 零售开关设定
 * @author Administrator
 *
 */
@Repository
public class SalesSwitchChageDao extends OemBaseDAO{
	public PageInfoDto salesSwitchChangeQuery(Map<String, String> queryParam) {
		   List<Object> params = new ArrayList<>();
		    String sql = getQuerySql(queryParam,params);
		    PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql,params);
			return pageInfoDto;
	}
	private String getQuerySql(Map<String,String> queryParam,List<Object> params){
		StringBuilder sql = new StringBuilder();
		sql.append(" \n");
		sql.append("SELECT D.DEALER_CODE, -- 经销商 \n");
		sql.append("          D.DEALER_SHORTNAME,  --  \n");
		sql.append("          D.STATUS       --  \n");
		sql.append("  FROM TM_DEALER D \n");
		sql.append(" where 1=1");    
		  if (!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))) {
	            sql.append(" and D.DEALER_CODE=? ");
	            params.add( queryParam.get("dealerCode"));
	        }
	        if (!StringUtils.isNullOrEmpty(queryParam.get("groupCode"))) {
	            sql.append(" and d.dealer_shortname like ?");
	            params.add("%" + queryParam.get("groupCode") + "%");
	        }
		return sql.toString();	
	}
	
	/**
	 * 获取大区信息
	 */
	public List<Map> getBigOrg(Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ORG1.ORG_ID BIG_ORG,ORG1.ORG_NAME BIG_ORG_NAME FROM TM_ORG ORG1 , TM_ORG ORG2 ");
		sql.append(" WHERE  ORG1.ORG_ID = ORG2.PARENT_ORG_ID  ");
		sql.append(" AND ORG1.DUTY_TYPE = 10431003 AND ORG1.ORG_LEVEL = 2 ");
		if(loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)){
			sql.append(" AND ORG1.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_02+"'  ");
		}else{
			sql.append(" AND ORG1.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_01+"'  ");
		}
		
		
		
		
		// 判断是否是大区经理登录
		if (loginInfo.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_LARGEREGION.toString())) {
			sql.append("   AND ORG1.ORG_ID = '" + loginInfo.getOrgId() + "' \n");	
		}
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 获取小区信息
	 */
	public List<Map> getSmallOrg(String bigorgid,Map<String, String> queryParams) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  ORG2.ORG_ID SMALL_ORG,ORG2.ORG_NAME SMALL_ORG_NAME FROM TM_ORG ORG1 ,TM_ORG ORG2  ");
		sql.append(" WHERE  ORG1.ORG_ID = ORG2.PARENT_ORG_ID  ");
		sql.append(" AND ORG2.DUTY_TYPE = 10431004 AND ORG2.ORG_LEVEL = 3 ");
		if(loginInfo.getPoseBusType().equals(OemDictCodeConstants.POSE_BUS_TYPE_DWR)){
			sql.append(" AND ORG2.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_02+"'  ");
		}else{
			sql.append(" AND ORG2.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_01+"'  ");
		}
		// 判断是否是大区经理登录
		if (loginInfo.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_LARGEREGION.toString())) {
			sql.append("   AND ORG2.ORG_ID = '" + loginInfo.getOrgId() + "' \n");	
		}
		if(!StringUtils.isNullOrEmpty(bigorgid)){
			sql.append("   AND ORG1.ORG_ID in ( " + bigorgid + ") \n");	
		}
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	public List<Map> getOrgLeft(String dealerCode,String dealerName,String orgId1,String orgId2) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" select td.dealer_id as code,td.dealer_shortname as data FROM tm_dealer td ,TT_VS_BASIC_PARA tvbp  ,TM_DEALER_ORG_RELATION tdor,tm_org org,tm_org org1 \n");
		sql.append(" where td.dealer_id = tvbp.dealer_id ");
		sql.append(" and td.DEALER_ID = tdor.DEALER_ID \n");
		sql.append(" and org.ORG_ID = tdor.ORG_ID \n");
		sql.append(" and org.PARENT_ORG_ID = org1.ORG_ID \n");
		if(!StringUtils.isNullOrEmpty(orgId1)){//大区
			sql.append("and org1.ORG_ID = '").append(orgId1).append("'");
		}
		if(!StringUtils.isNullOrEmpty(orgId2)){//大区
			sql.append("and org.ORG_ID = '").append(orgId2).append("'");
		}
		if(!StringUtils.isNullOrEmpty(dealerCode)){
			dealerCode+=dealerCode.substring(dealerCode.length()-1).equals(",")?"0":"";
			dealerCode = dealerCode.replaceAll(",", "','");
			sql.append(" and td.dealer_code in('").append(dealerCode).append("')");
		}
		if(!StringUtils.isNullOrEmpty(dealerName)){
			sql.append(" and td.dealer_name like '%").append(dealerName).append("%'");
		}
		//增加车厂ID过滤
		sql.append(" and td.OEM_COMPANY_ID="+loginInfo.getCompanyId()+"\n");		
		sql.append(" and tvbp.FAST_RETAIL_STATUS = '").append(OemDictCodeConstants.FAST_RETAIL_STATUS_01).append("'");
		sql.append(" order by org.ORG_ID ");
		System.out.println(sql.toString() +"\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
	
	public List<Map> getOrgRight(String dealerCode,String dealerName,String orgId1,String orgId2) {
		StringBuffer sql = new StringBuffer();
		sql.append("select td.dealer_id as code,td.dealer_shortname as data FROM tm_dealer td,TM_DEALER_ORG_RELATION tdor,tm_org org,tm_org org1 \n");
		sql.append(" where 1=1 and td.dealer_type = '"+OemDictCodeConstants.DEALER_TYPE_DVS+"' \n");
		sql.append(" and td.DEALER_ID = tdor.DEALER_ID \n");
		sql.append(" and org.ORG_ID = tdor.ORG_ID \n");
		sql.append(" and org.PARENT_ORG_ID = org1.ORG_ID \n");
		sql.append(" and td.status ='"+OemDictCodeConstants.STATUS_ENABLE+"' and td.dealer_id not in  ");
		sql.append(" (select dealer_id from TT_VS_BASIC_PARA where FAST_RETAIL_STATUS='"+OemDictCodeConstants.FAST_RETAIL_STATUS_01+"' )  ");
		if(!StringUtils.isNullOrEmpty(orgId1)){//大区
			sql.append("and org1.ORG_ID = '").append(orgId1).append("'");
		}
		if(!StringUtils.isNullOrEmpty(orgId2)){//大区
			sql.append("and org.ORG_ID = '").append(orgId2).append("'");
		}
		if(!StringUtils.isNullOrEmpty(dealerCode)){
			dealerCode+=dealerCode.substring(dealerCode.length()-1).equals(",")?"0":"";
			dealerCode = dealerCode.replaceAll(",", "','");
			sql.append(" and td.dealer_code in('").append(dealerCode).append("')");
		}
		if(!StringUtils.isNullOrEmpty(dealerName)){
			sql.append(" and td.dealer_name like '%").append(dealerName).append("%'");
		}
		sql.append(" order by org.ORG_ID ");
		System.out.println(sql.toString()+"\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}
}
