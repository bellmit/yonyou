package com.yonyou.dms.part.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 配件订单管理Dao
 * @author ZhaoZ
 * @date 2017年3月24日
 */
@SuppressWarnings("unchecked")
@Repository
public class PartClaimManageDao extends OemBaseDAO{

	/**
	 * 到货索赔审核查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto QuerycheckClaim(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerycheckClaimSql(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 到货索赔审核查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getQuerycheckClaimSql(Map<String, String> queryParams, List<Object> params) {
		   StringBuffer pasql = new StringBuffer();
		   pasql.append("SELECT * from ( \n");
		   pasql.append("SELECT  \n");
		   pasql.append("TD.DEALER_ID,\n");
		   pasql.append("TD.DEALER_CODE,\n");
           pasql.append("(select FCA_CODE FROM TM_COMPANY where COMPANY_ID =TD.COMPANY_ID ) FCA_CODE,\n");
		   pasql.append("TD.DEALER_SHORTNAME,\n");
		   pasql.append("TPC.CLAIM_ID,\n");
		   pasql.append("TPC.CLAIM_NO,\n");
		   pasql.append("TPC.PART_CODE,\n");
		   pasql.append("TPC.PART_NAME,\n");
		   pasql.append("TPC.CLAIM_NUM,\n");
		   pasql.append("TPC.CLAIM_REQUIRE,\n");
		   pasql.append("TPC.CLAIM_PROPERTY,\n");
		   pasql.append("TPC.DELIVER_NO,\n");
		   pasql.append("TPC.PART_MDOEL,\n");
		   pasql.append("date_format(TPC.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,\n");
		   pasql.append("TPC.CLAIM_STATUS,\n");
		   pasql.append("TPC.TRANS_STOCK,\n");
		   pasql.append("(select DISTINCT ORDER_TYPE from TT_PT_ORDER_dcs where SAP_ORDER_NO =TPC.ORDER_NO ) ORDER_TYPE,\n");
		   pasql.append("TPC.AMOUNT,\n");
		   pasql.append("TPC.TRANS_COMPANY\n");
		   pasql.append("FROM TT_PT_CLAIM_dcs TPC,TM_DEALER TD\n");
		   pasql.append("WHERE 1=1\n");
		   pasql.append("AND TD.DEALER_ID = TPC.DEALER_ID\n");
     	   pasql.append("AND TPC.CLAIM_STATUS in('"+ OemDictCodeConstants.PART_CLAIM_STATUS_03+"','"+OemDictCodeConstants.PART_CLAIM_STATUS_04+ "')\n");
		    //经销商
     	    if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
          	  String[] dealerCodes=queryParams.get("dealerCode").split(",");
      		  String code="";
      		  for(int i=0;i<dealerCodes.length;i++){
      			  code+=""+dealerCodes[i]+",";
      		  }
      		  code=code.substring(0,code.length()-1);
      		  pasql.append(" 					AND TD.DEALER_CODE IN (?) \n");
          	  params.add(code);
            }
		    //零件分类
     	    if(!StringUtils.isNullOrEmpty(queryParams.get("partReportMethod"))){ 
     	    	pasql.append(" 					AND TPC.PART_MDOEL = ? \n");
           	  params.add(queryParams.get("partReportMethod"));
             }
	        //索赔要求
     	    if(!StringUtils.isNullOrEmpty(queryParams.get("partClaimRequirement"))){ 
     	    	pasql.append(" 					AND TPC.CLAIM_REQUIRE = ? \n");
          	    params.add(queryParams.get("partClaimRequirement"));
     	    }
	        //索赔性质
     	    if(!StringUtils.isNullOrEmpty(queryParams.get("partClaimNature"))){ 
     	    	pasql.append(" 					AND TPC.CLAIM_PROPERTY = ? \n");
         	    params.add(queryParams.get("partClaimNature"));
    	    }
	        //配件编号
     	   	if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
     		    pasql.append(" 					AND TPC.PART_CODE LIKE ? \n");
         	    params.add("%"+queryParams.get("partCode")+"%");
    	    }
	        //配件名称
     	   	if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
     		   pasql.append(" 					AND TPC.PART_NAME LIKE ? \n");
     		   params.add("%"+queryParams.get("partName")+"%");
     	   	}
	        //交货单号
     	   	if(!StringUtils.isNullOrEmpty(queryParams.get("deliverNo"))){ 
     		   pasql.append(" 					AND TPC.DELIVER_NO LIKE ? \n");
     		   params.add("%"+queryParams.get("deliverNo")+"%");
     	   	}
     	   	//状态
     	   	if(!StringUtils.isNullOrEmpty(queryParams.get("claimStatus"))){ 
     		   pasql.append(" 					AND TPC.CLAIM_STATUS = ? \n");
    		   params.add(queryParams.get("claimStatus"));
     	   	}
	        //索赔编号
     	  	if(!StringUtils.isNullOrEmpty(queryParams.get("claimNo"))){ 
     	  		pasql.append(" 					AND TPC.CLAIM_NO LIKE ? \n");
     	  		params.add("%"+queryParams.get("claimNo")+"%");
     	  	}
	        //提报时间
     	  	if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
     	  		pasql.append(" AND  DATE_FORMAT(TPC.REPORT_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
     	 	}
     	 
     	 	if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
     	 		pasql.append(" AND  DATE_FORMAT(TPC.REPORT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
     	 	}
     	 	pasql.append(") dcs \n");
			return pasql.toString();
	     
	}

	/**
	 * 索赔基本信息
	 * @param id
	 * @return
	 */

	public Map<String, Object> getCheckDetail(BigDecimal claimId) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TD.DEALER_CODE, \n");
		sql.append("	(select FCA_CODE FROM TM_COMPANY where COMPANY_ID =TD.COMPANY_ID ) FCA_CODE,\n");
		sql.append("    TD.LINK_MAN, \n");
		sql.append("    TD.FAX_NO,\n");
		sql.append("    TD.LINK_MAN_MOBILE, \n");
		sql.append("    TD.DEALER_SHORTNAME,\n");
		sql.append("    TD.DEALER_NAME, \n");
		sql.append("    TPC.CLAIM_ID,\n");
		sql.append("    TPC.CLAIM_NO, \n");
		sql.append("    date_format(TPC.CLAIM_DATE,'%Y-%m-%d') CLAIM_DATE,\n");
		sql.append("    TPC.CLAIM_PROPERTY,\n");
		sql.append("    TPC.CLAIM_REQUIRE\n");
		sql.append("    FROM TT_PT_CLAIM_dcs TPC \n");
		sql.append("    INNER JOIN TM_DEALER TD ON TPC.DEALER_ID = TD.DEALER_ID \n");
		sql.append("    WHERE 1 = 1 \n");
		sql.append("    AND TPC.CLAIM_ID = " + claimId + "\n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	/**
	 * 索赔订单详细信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> getClaimDetailInfo(BigDecimal claimId) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TPO.ORDER_TYPE,\n");
		sql.append("    TPC.ORDER_NO, \n");
		sql.append("    TPC.DELIVER_NO, \n");
		sql.append("    TPC.TRANS_COMPANY,\n");
		sql.append("    TPC.TRANS_TYPE, \n");
		sql.append("    TPC.TRANS_NO, \n");
		sql.append("    TPC.BOX_NO,\n");
		sql.append("    date_format(TPC.ARRIVED_DATE,'%Y-%m-%d') ARRIVED_DATE,\n");
		sql.append("    date_format(TPC.CHECKED_DATE,'%Y-%m-%d') CHECKED_DATE, \n");
		sql.append("    TPC.CHECKED_BY,\n");
		sql.append("    TPC.CHECKED_PHONE,\n");
		sql.append("    TPC.PART_CODE, \n");
		sql.append("    TPC.PART_NAME, \n");
		sql.append("    TRANS_NUM, \n");
		sql.append("    TPC.CLAIM_NUM, \n");;
		sql.append("    TPC.PART_PRICE, \n");
		sql.append("    TPC.AMOUNT,  \n");
		sql.append("    TPC.PART_MDOEL,\n");
		sql.append("    TPC.REPORT_TIMES, \n");
		sql.append("    date_format(TPC.TRANS_DATE,'%Y-%m-%d') TRANS_DATE,\n");
		sql.append("    TPC.LEAVE_TRANS_DAYS, \n");
		sql.append("    TPC.DUTY_BY,\n");
		sql.append("    TPC.AUDIT_OPTIONS,\n");
		sql.append("    TPC.CLAIM_OPTIONS , \n");
		sql.append("    TPC.CLAIM_STATUS ,\n");
		sql.append("    TPC.AUDIT_REMARK , \n");
		sql.append("    TPC.REISSUE_TRANS_NO, \n");	
		sql.append("    TPC.DELIVERY_ERROR_PART, \n");
		sql.append("    date_format(TPC.TRANS_SYSTEM_DATE,'%Y-%m-%d') TRANS_SYSTEM_DATE \n");
		sql.append("    FROM TT_PT_CLAIM_dcs TPC \n");
		sql.append("    LEFT JOIN TT_PT_ORDER_dcs TPO ON TPC.ORDER_NO=TPO.SAP_ORDER_NO \n");
		sql.append("    WHERE 1 = 1 \n");
		sql.append("    AND TPC.CLAIM_ID = " + claimId + "\n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	/**
	 * 审核历史
	 * @param claimId
	 * @return
	 */
	public PageInfoDto checkInfoByClaimId(BigDecimal claimId) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TPCH.CHECK_ID, \n");
		sql.append("    TC.NAME,  \n");
		sql.append("    DATE_FORMAT(TPCH.CHECK_DATE,'%Y-%m-%d') CHECK_DATE, \n");
		sql.append("    TPCH.CHECK_STATUS, \n");
		sql.append("    TPCH.CHECK_OPINION \n");
		sql.append("    FROM TT_PT_CLAIM_HISTORY_dcs TPCH \n");
		sql.append("    INNER JOIN TC_USER TC ON TC.USER_ID = TPCH.CREATE_BY \n");
		sql.append("    INNER JOIN TT_PT_CLAIM_dcs TPC ON TPC.CLAIM_ID=TPCH.CLAIM_ID \n");
		sql.append("    WHERE 1 = 1 \n");
		sql.append("    AND TPC.CLAIM_ID = " + claimId + "\n");
		return OemDAOUtil.pageQuery(sql.toString(), null);
	}

	/**
	 * 到货索赔查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto QueryqueryClaim(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQueryqueryClaimSql(queryParams, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;
	}

	/**
	 * 到货索赔查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getQueryqueryClaimSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
		   pasql.append("SELECT * from ( \n");   
		   pasql.append("SELECT  \n");
		   pasql.append("TD.DEALER_ID,\n");
		   pasql.append("TD.DEALER_CODE,\n");
		   pasql.append("(select FCA_CODE FROM TM_COMPANY where COMPANY_ID =TD.COMPANY_ID ) FCA_CODE,\n");
		   pasql.append("TD.DEALER_SHORTNAME,\n");
		   pasql.append("TPC.CLAIM_ID,\n");
		   pasql.append("TPC.CLAIM_NO,\n");
		   pasql.append("TPC.PART_CODE,\n");
		   pasql.append("TPC.PART_NAME,\n");
		   pasql.append("TPC.CLAIM_NUM,\n");
		   pasql.append("TPC.CLAIM_REQUIRE,\n");
		   pasql.append("TPC.CLAIM_PROPERTY,\n");
		   pasql.append("TPC.DELIVER_NO,\n");
		   pasql.append("TPC.PART_MDOEL,\n");
		   pasql.append("DATE_FORMAT(TPC.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,\n");
		   pasql.append("TPC.CLAIM_STATUS,\n");
		   pasql.append("TPC.TRANS_STOCK,\n");
		   pasql.append("DATE_FORMAT((select max(TPCH.CHECK_DATE) FROM TT_PT_CLAIM_HISTORY_dcs TPCH where TPCH.CLAIM_ID=TPC.CLAIM_ID ),'%Y-%m-%d') CHECK_DATE,\n");
		   pasql.append("TPC.AMOUNT,\n");
		   pasql.append("TPC.TRANS_COMPANY\n");
		   pasql.append("FROM TT_PT_CLAIM_dcs TPC,TM_DEALER TD\n");
		   pasql.append("WHERE 1=1\n");
		   pasql.append("AND TD.DEALER_ID = TPC.DEALER_ID\n");
		   //经销商
    	    if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
         	  String[] dealerCodes=queryParams.get("dealerCode").split(",");
     		  String code="";
     		  for(int i=0;i<dealerCodes.length;i++){
     			  code+=""+dealerCodes[i]+",";
     		  }
     		  code=code.substring(0,code.length()-1);
     		  pasql.append(" 					AND TD.DEALER_CODE IN (?) \n");
         	  params.add(code);
           }
		    //零件分类
    	    if(!StringUtils.isNullOrEmpty(queryParams.get("partReportMethod"))){ 
    	    	pasql.append(" 					AND TPC.PART_MDOEL = ? \n");
          	    params.add(queryParams.get("partReportMethod"));
            }
	        //索赔要求
    	    if(!StringUtils.isNullOrEmpty(queryParams.get("partClaimRequirement"))){ 
    	    	pasql.append(" 					AND TPC.CLAIM_REQUIRE = ? \n");
         	    params.add(queryParams.get("partClaimRequirement"));
    	    }
	        //索赔性质
    	    if(!StringUtils.isNullOrEmpty(queryParams.get("partClaimNature"))){ 
    	    	pasql.append(" 					AND TPC.CLAIM_PROPERTY = ? \n");
        	    params.add(queryParams.get("partClaimNature"));
    	    }
	        //配件编号
    	   	if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
    		    pasql.append(" 					AND TPC.PART_CODE LIKE ? \n");
        	    params.add("%"+queryParams.get("partCode")+"%");
    	   	}
	        //配件名称
    	   	if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
    		   pasql.append(" 					AND TPC.PART_NAME LIKE ? \n");
    		   params.add("%"+queryParams.get("partName")+"%");
    	   	}
	        //交货单号
    	   	if(!StringUtils.isNullOrEmpty(queryParams.get("deliverNo"))){ 
    		   pasql.append(" 					AND TPC.DELIVER_NO LIKE ? \n");
    		   params.add("%"+queryParams.get("deliverNo")+"%");
    	   	}
    	   	//状态
    	   	if(!StringUtils.isNullOrEmpty(queryParams.get("claimStatus"))){ 
    		   pasql.append(" 					AND TPC.CLAIM_STATUS = ? \n");
    		   params.add(queryParams.get("claimStatus"));
    	   	}
	        //索赔编号
    	  	if(!StringUtils.isNullOrEmpty(queryParams.get("claimNo"))){ 
    	  		pasql.append(" 					AND TPC.CLAIM_NO LIKE ? \n");
    	  		params.add("%"+queryParams.get("claimNo")+"%");
    	  	}
	        //提报时间
    	  	if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
    	  		pasql.append(" AND  DATE_FORMAT(TPC.REPORT_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
    	 	}
    	 
    	 	if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
    	 		pasql.append(" AND  DATE_FORMAT(TPC.REPORT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
    	 	}
    	 	pasql.append(") dcs \n");
			return pasql.toString(); 
	      
	}

	/**
	 * 导出查询
	 * @param queryParams
	 * @return
	 */
	public List<Map> downLoad(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getdownLoadSql(queryParams, params);
		return OemDAOUtil.findAll(sql,params );
	}
	/**
	 * 导出查询
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getdownLoadSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer pasql = new StringBuffer();
		   pasql.append("SELECT  \n");
		   pasql.append("TD.DEALER_ID,\n");
		   pasql.append("TD.DEALER_CODE,\n");
		   pasql.append("(select FCA_CODE FROM TM_COMPANY where COMPANY_ID =TD.COMPANY_ID ) FCA_CODE,\n");
		   pasql.append("TD.DEALER_SHORTNAME,\n");
		   pasql.append("TPC.CLAIM_ID,\n");
		   pasql.append("TPC.CLAIM_NO,\n");
		   pasql.append("TPC.PART_CODE,\n");
		   pasql.append("TPC.PART_NAME,\n");
		   pasql.append("TPC.CLAIM_NUM,\n");
		   pasql.append("TPC.CLAIM_REQUIRE,\n");
		   pasql.append("TPC.CLAIM_PROPERTY,\n");
		   pasql.append("TPC.DELIVER_NO,\n");
		   pasql.append("TPC.PART_MDOEL,\n");
		   pasql.append("DATE_FORMAT(TPC.REPORT_DATE,'%Y-%m-%d') REPORT_DATE,\n");
		   pasql.append("TPC.CLAIM_STATUS,\n");
		   pasql.append("TPC.DUTY_BY,\n");
		   pasql.append("TPC.REISSUE_TRANS_NO,\n");
		   pasql.append("TPC.TRANS_STOCK,\n");
		   pasql.append("DATE_FORMAT((select max(TPCH.CHECK_DATE) FROM TT_PT_CLAIM_HISTORY_dcs TPCH where TPCH.CLAIM_ID=TPC.CLAIM_ID ),'%Y-%m-%d') CHECK_DATE,\n");
		   pasql.append("TPC.AMOUNT,\n");
		   pasql.append("TPC.TRANS_COMPANY\n");
		   pasql.append("FROM TT_PT_CLAIM_dcs TPC,TM_DEALER TD\n");
		   pasql.append("WHERE 1=1\n");
		   pasql.append("AND TD.DEALER_ID = TPC.DEALER_ID\n");
		  
		    //经销商
		   if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
	         	  String[] dealerCodes=queryParams.get("dealerCode").split(",");
	     		  String code="";
	     		  for(int i=0;i<dealerCodes.length;i++){
	     			  code+=""+dealerCodes[i]+",";
	     		  }
	     		  code=code.substring(0,code.length()-1);
	     		  pasql.append(" 					AND TD.DEALER_CODE IN (?) \n");
	         	  params.add(code);
	           }
	        
		   
		   //零件分类
   	    	if(!StringUtils.isNullOrEmpty(queryParams.get("partReportMethod"))){ 
   	    		pasql.append(" 					AND TPC.PART_MDOEL = ? \n");
         	    params.add(queryParams.get("partReportMethod"));
   	    	}
   	    	//索赔要求
    	    if(!StringUtils.isNullOrEmpty(queryParams.get("partClaimRequirement"))){ 
    	    	pasql.append(" 					AND TPC.CLAIM_REQUIRE = ? \n");
         	    params.add(queryParams.get("partClaimRequirement"));
    	    }
	       
    	    //索赔性质
     	    if(!StringUtils.isNullOrEmpty(queryParams.get("partClaimNature"))){ 
     	    	pasql.append(" 					AND TPC.CLAIM_PROPERTY = ? \n");
         	    params.add(queryParams.get("partClaimNature"));
    	    }
	        //配件编号
     	    if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
     		    pasql.append(" 					AND TPC.PART_CODE LIKE ? \n");
     		    params.add("%"+queryParams.get("partCode")+"%");
   	   		}
     	    //配件名称
    	   	if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
    		    pasql.append(" 					AND TPC.PART_NAME LIKE ? \n");
    		    params.add("%"+queryParams.get("partName")+"%");
    	   	}
    	    //交货单号
    	   	if(!StringUtils.isNullOrEmpty(queryParams.get("deliverNo"))){ 
    		    pasql.append(" 					AND TPC.DELIVER_NO LIKE ? \n");
    		    params.add("%"+queryParams.get("deliverNo")+"%");
    	   	}
    	   	//状态
    	   	if(!StringUtils.isNullOrEmpty(queryParams.get("claimStatus"))){ 
    		    pasql.append(" 					AND TPC.CLAIM_STATUS = ? \n");
    		    params.add(queryParams.get("claimStatus"));
    	   	}
    	   	//索赔编号
    	  	if(!StringUtils.isNullOrEmpty(queryParams.get("claimNo"))){ 
    	  		pasql.append(" 					AND TPC.CLAIM_NO LIKE ? \n");
    	  		params.add("%"+queryParams.get("claimNo")+"%");
    	  	}
    	  	 //提报时间
    	  	if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
    	  		pasql.append(" AND  DATE_FORMAT(TPC.REPORT_DATE,'%Y-%m-%d') >='" + queryParams.get("startDate") +"' \n");
    	 	}
    	 
    	 	if(!StringUtils.isNullOrEmpty(queryParams.get("startDate"))){ 
    	 		pasql.append(" AND  DATE_FORMAT(TPC.REPORT_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
    	 	}
			return pasql.toString();
	}

	/**
	 * 索赔订单详细信息
	 * @param id
	 * @return
	 */
	public Map<String, Object> findClaimInfoByClaim(BigDecimal id) {
		StringBuilder sql = new StringBuilder();
		sql.append("    SELECT \n");
		sql.append("    TPO.ORDER_TYPE, \n");
		sql.append("    TPC.ORDER_NO, \n");
		sql.append("    TPC.DELIVER_NO, \n");
		sql.append("    TPC.TRANS_COMPANY, \n");
		sql.append("    TPC.TRANS_TYPE, \n");
		sql.append("    TPC.TRANS_NO, \n");
		sql.append("    TPC.BOX_NO, \n");
		sql.append("    DATE_FORMAT(TPC.ARRIVED_DATE,'%Y-%m-%d') ARRIVED_DATE,\n");
		sql.append("    DATE_FORMAT(TPC.CHECKED_DATE,'%Y-%m-%d') CHECKED_DATE, \n");
		sql.append("    TPC.CHECKED_BY, \n");
		sql.append("    TPC.CHECKED_PHONE, \n");
		sql.append("    TPC.PART_CODE, \n");
		sql.append("    TPC.PART_NAME, \n");
		sql.append("    TRANS_NUM, \n");
		sql.append("    TPC.CLAIM_NUM, \n");;
		sql.append("    TPC.PART_PRICE, \n");
		sql.append("    TPC.AMOUNT,  \n");
		sql.append("    TPC.PART_MDOEL,\n");
		sql.append("    TPC.REPORT_TIMES, \n");
		sql.append("    DATE_FORMAT(TPC.TRANS_DATE,'%Y-%m-%d') TRANS_DATE, \n");
		sql.append("    TPC.LEAVE_TRANS_DAYS, \n");
		sql.append("    TPC.DUTY_BY,\n");
		sql.append("    TPC.AUDIT_OPTIONS, \n");
		sql.append("    TPC.CLAIM_OPTIONS , \n");
		sql.append("    TPC.CLAIM_STATUS , \n");
		sql.append("    TPC.AUDIT_REMARK , \n");
		sql.append("    TPC.REISSUE_TRANS_NO, \n");	
		sql.append("    TPC.DELIVERY_ERROR_PART, \n");
		sql.append("    DATE_FORMAT(TPC.TRANS_SYSTEM_DATE ,'%Y-%m-%d') TRANS_SYSTEM_DATE\n");
		sql.append("    FROM TT_PT_CLAIM_dcs TPC \n");
		sql.append("    LEFT JOIN TT_PT_ORDER_dcs TPO ON TPC.ORDER_NO=TPO.SAP_ORDER_NO \n");
		sql.append("    WHERE 1 = 1 \n");
		sql.append("    AND TPC.CLAIM_ID = " + id + "\n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

}
