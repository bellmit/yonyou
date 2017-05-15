package com.yonyou.dms.part.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 呆滞品交易确认
 * @author ZhaoZ
 *@date 2017年4月12日
 */
@Repository
public class PartObsoleteMaterialDealDao extends OemBaseDAO{

	/**
	 * 呆滞品交易查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getPartObsoleteMaterial(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getPartObsoleteMaterialSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 呆滞品交易查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getPartObsoleteMaterialSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql=new StringBuffer();
		sql.append("\n SELECT TOMA.APPLY_ID,\n");
		sql.append("          TOMA.LINKMAN_NAME,\n");
		sql.append("          TOMA.LINKMAN_TEL,\n");
		sql.append("          TOMA.ADDRESS,\n");
		sql.append("          TOMA.RELEASE_ID,\n");
		sql.append("          TOMA.POST_CODE,\n");
		sql.append("          TOMR.WAREHOUSE,\n");
		sql.append("          TOMA.PART_CODE,\n");
		sql.append("          TOMA.PART_NAME,\n");
		sql.append("          TOMR.RELEASE_NUMBER,\n");
		sql.append("          TOMR.APPLY_NUMBER CAN_APPLY_NUMBER,\n");
		sql.append("          TOMA.APPLY_NUMBER,\n");
		sql.append("          TOMA.SALES_PRICE,\n");
		sql.append("          (TOMA.APPLY_NUMBER* TOMA.SALES_PRICE) AS AMOUNT,\n");
		sql.append("          DATE_FORMAT(TOMA.APPLY_DATE,'%Y-%m-%d') APPLY_DATE,\n");
		sql.append("          DATE_FORMAT(TOMA.AFFIRM_DATE,'%Y-%m-%d') AFFIRM_DATE,\n");
		sql.append("          O2.ORG_NAME AS BIG_AREA,\n");
		sql.append("          O3.ORG_NAME AS SMALL_AREA,\n");
		sql.append("          TD.DEALER_SHORTNAME AS DEALER_NAME,\n");
		sql.append("          TD.DEALER_CODE,\n");
		sql.append("          TOMA.STATUS \n");
		sql.append("  FROM TT_OBSOLETE_MATERIAL_APPLY_dcs TOMA \n");
		sql.append("  INNER JOIN  TT_OBSOLETE_MATERIAL_RELEASE_dcs TOMR ON TOMA.RELEASE_ID = TOMR.RELEASE_ID \n");
		sql.append("  INNER JOIN TM_DEALER TD ON TD.DEALER_CODE = TOMA.APPLY_DEALER_CODE  \n");
		sql.append("  INNER JOIN TM_DEALER_ORG_RELATION DOR ON DOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append("  INNER JOIN TM_ORG O3 ON O3.ORG_ID = DOR.ORG_ID AND O3.ORG_LEVEL = '3' \n");
		sql.append("  INNER JOIN TM_ORG O2 ON O2.ORG_ID = O3.PARENT_ORG_ID AND O2.ORG_LEVEL = '2' \n");
		sql.append("  WHERE  TD.DEALER_TYPE = '10771002' \n");
		sql.append("  AND TOMA.RELEASE_DEALER_CODE='"+loginUser.getDealerCode()+"'\n");
		//申请时间
		 if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
		     
			 sql.append(" AND  DATE_FORMAT(TOMA.APPLY_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
	     }
	     if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
	            
	    	 sql.append(" AND  DATE_FORMAT(TOMA.APPLY_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
	     }
		
        //经销商
        if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
        	String[] dealerCodes=queryParams.get("dealerCode").split(",");
    		String code="";
    		for(int i=0;i<dealerCodes.length;i++){
    			code+=""+dealerCodes[i]+",";
    		}
    		code=code.substring(0,code.length()-1);	
        	sql.append(" 					AND TD.DEALER_CODE IN (?) \n");
        	params.add(code);
        }
        //配件编号
        if (!StringUtils.isNullOrEmpty(queryParams.get("partCode"))) {
        	sql.append(" 					AND TOMA.PART_CODE LIKE ? \n");
        	params.add("%"+queryParams.get("partCode")+"%");
		}
        //配件名称
        if (!StringUtils.isNullOrEmpty(queryParams.get("partName"))) {
        	sql.append(" 					AND TOMA.PART_NAME LIKE ? \n");
        	params.add("%"+queryParams.get("partName")+"%");
		}
        //状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("claimStatus"))){
        	if("70351002".equals(queryParams.get("claimStatus"))){
        		sql.append(" 					AND TOMA.STATUS in  ('70351002', '70351003','70351004') \n");
        	}else{
        		sql.append(" 					AND TOMA.STATUS = '" + queryParams.get("claimStatus") + "' \n");
        	}
        }
		return sql.toString();
	}

	/**
	 * 下载查询
	 * @param queryParams
	 * @return
	 */
	public List<Map> getdownloadList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getdownloadLSql(queryParams, params);
		return OemDAOUtil.findAll(sql, params);
	}

	/**
	 * 下载查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getdownloadLSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql=new StringBuffer();
		sql.append("\n SELECT TOMA.RELEASE_ID,\n");
		sql.append("          TOMA.LINKMAN_NAME,\n");
		sql.append("          TOMA.LINKMAN_TEL,\n");
		sql.append("          TOMA.ADDRESS,\n");
		sql.append("          TOMA.POST_CODE,\n");
		sql.append("          TOMR.WAREHOUSE,\n");
		sql.append("          TOMA.PART_CODE,\n");
		sql.append("          TOMA.PART_NAME,\n");
		sql.append("          TOMR.RELEASE_NUMBER,\n");
		sql.append("          TOMA.APPLY_NUMBER,\n");
		sql.append("          TOMA.SALES_PRICE,\n");
		sql.append("          (TOMA.APPLY_NUMBER* TOMA.SALES_PRICE) AS AMOUNT,\n");
		sql.append("          TOMA.APPLY_DATE,\n");
		sql.append("          TOMA.AFFIRM_DATE,\n");
		sql.append("          O2.ORG_NAME AS BIG_AREA,\n");
		sql.append("          O3.ORG_NAME AS SMALL_AREA,\n");
		sql.append("          TD.DEALER_SHORTNAME AS DEALER_NAME,\n");
		sql.append("          TD.DEALER_CODE,\n");
		sql.append("          TOMA.STATUS \n");
		sql.append("  FROM TT_OBSOLETE_MATERIAL_APPLY_dcs TOMA \n");
		sql.append("  INNER JOIN  TT_OBSOLETE_MATERIAL_RELEASE_dcs TOMR ON TOMA.RELEASE_ID = TOMR.RELEASE_ID \n");
		sql.append("  INNER JOIN TM_DEALER TD ON TD.DEALER_CODE = TOMA.APPLY_DEALER_CODE  \n");
		sql.append("  INNER JOIN TM_DEALER_ORG_RELATION DOR ON DOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append("  INNER JOIN TM_ORG O3 ON O3.ORG_ID = DOR.ORG_ID AND O3.ORG_LEVEL = '3' \n");
		sql.append("  INNER JOIN TM_ORG O2 ON O2.ORG_ID = O3.PARENT_ORG_ID AND O2.ORG_LEVEL = '2' \n");
		sql.append("  WHERE  TD.DEALER_TYPE = '10771002' \n");
		sql.append("  AND TOMA.RELEASE_DEALER_CODE='"+loginUser.getDealerCode()+"'\n");
		//申请时间
		 if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
		     
			 sql.append(" AND  DATE_FORMAT(TOMA.APPLY_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
	     }
	     if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
	            
	    	 sql.append(" AND  DATE_FORMAT(TOMA.APPLY_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
	     }
		
        //经销商
        if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
        	String[] dealerCodes=queryParams.get("dealerCode").split(",");
    		String code="";
    		for(int i=0;i<dealerCodes.length;i++){
    			code+=""+dealerCodes[i]+",";
    		}
    		code=code.substring(0,code.length()-1);	
        	sql.append(" 					AND TD.DEALER_CODE IN (?) \n");
        	params.add(code);
        }
        //配件编号
        if (!StringUtils.isNullOrEmpty(queryParams.get("partCode"))) {
        	sql.append(" 					AND TOMA.PART_CODE LIKE ? \n");
        	params.add("%"+queryParams.get("partCode")+"%");
		}
        //配件名称
        if (!StringUtils.isNullOrEmpty(queryParams.get("partName"))) {
        	sql.append(" 					AND TOMA.PART_NAME LIKE ? \n");
        	params.add("%"+queryParams.get("partName")+"%");
		}
        //状态
        if(!StringUtils.isNullOrEmpty(queryParams.get("claimStatus"))){
        	if("70351002".equals(queryParams.get("claimStatus"))){
        		sql.append(" 					AND TOMA.STATUS in  ('70351002', '70351003','70351004') \n");
        	}else{
        		sql.append(" 					AND TOMA.STATUS = '" + queryParams.get("claimStatus") + "' \n");
        	}
        }
		return sql.toString();
	}

}
