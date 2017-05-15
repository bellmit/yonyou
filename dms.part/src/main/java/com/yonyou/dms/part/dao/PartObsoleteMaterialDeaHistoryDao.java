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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 呆滞品交易历史查询Dao
 * @author ZhaoZ
 * @date 2017年4月13日
 */
@Repository
public class PartObsoleteMaterialDeaHistoryDao extends OemBaseDAO{

	/**
	 * 呆滞品交易历史查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto allList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getAllListSql(queryParams, params);
		return  OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 呆滞品交易历史查询sql
	 * @param queryParams
	 * @return
	 */
	private String getAllListSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dutytupeint = logonUser.getDutyType();
		String dealercode = logonUser.getDealerCode();
		StringBuffer sql=new StringBuffer();
		sql.append("   SELECT TOR2.ORG_NAME ORG_NAME2,		 \n");
		sql.append("   TOR.ORG_NAME ORG_NAME, 		\n");
		sql.append("   TD.DEALER_CODE DDEALER_CODE,		\n");
		sql.append("   TD.DEALER_SHORTNAME DDEALER_SHORTNAME, 		\n");
		sql.append("   TDD.DEALER_CODE ADEALER_CODE,		 \n");
		sql.append("   TDD.DEALER_SHORTNAME ADEALER_SHORTNAME, 		\n");
		sql.append("   TOMA.PART_CODE,		\n");
		sql.append("   IFNULL(TOMA.PART_NAME, TP.PART_NAME )PART_NAME, 		 \n");
		sql.append("   TOMA.APPLY_NUMBER,		\n");
		sql.append("   TOMA.SALES_PRICE, 		\n");
		sql.append("   TOMA.SALES_PRICE*TOMA.APPLY_NUMBER  TOTALS, 	\n");
		sql.append("   DATE_FORMAT(TOMA.APPLY_DATE,'%Y-%m-%d %h:%i:%s')  APPLY_DATE,     	\n");
		sql.append("   DATE_FORMAT(TOMA.AFFIRM_DATE,'%Y-%m-%d %h:%i:%s') AFFIRM_DATE,     		\n");
		sql.append("   DATE_FORMAT(OUT_WAREHOUS_DATE,'%Y-%m-%d %h:%i:%s') OUT_WAREHOUS_DATE,   	\n");
		sql.append("   DATE_FORMAT(TOMA.PUT_WAREHOUS_DATE,'%Y-%m-%d %h:%i:%s') PUT_WAREHOUS_DATE,   		\n");
		sql.append("   CASE WHEN TOMA.STATUS LIKE 70351001 || TOMA.STATUS LIKE 70351002 || TOMA.STATUS LIKE 70351003 THEN '进行中'   		\n");
		sql.append("   WHEN TOMA.STATUS LIKE 70351004  THEN '已完成' ELSE '已取消' END STATUS_NAME     \n"); 
		if(!dutytupeint.equals(OemDictCodeConstants.DUTY_TYPE_DEALER.toString())){
			sql.append("  ,TOMR.WAREHOUSE     \n"); 
		}
		sql.append(" from TT_OBSOLETE_MATERIAL_APPLY_DCS TOMA		\n");
		sql.append("   LEFT JOIN TM_DEALER TD		\n");
		sql.append("   ON TOMA.RELEASE_DEALER_CODE = TD.DEALER_CODE 		\n");
		sql.append("   LEFT JOIN TM_DEALER TDD		\n");
		sql.append("   ON TOMA.APPLY_DEALER_CODE = TDD.DEALER_CODE		\n");
		sql.append("   LEFT JOIN TM_DEALER_ORG_RELATION TDOR 		\n");
		sql.append("   ON TD.DEALER_ID = TDOR.DEALER_ID			\n");
		sql.append("   LEFT JOIN TM_ORG TOR 		\n");
		sql.append("   ON TDOR.ORG_ID = TOR.ORG_ID		\n");
		sql.append("   LEFT JOIN TM_ORG TOR2   \n");
		sql.append("   ON TOR.PARENT_ORG_ID = TOR2.ORG_ID    \n");
		sql.append("   LEFT JOIN TT_PT_PART_BASE_DCS TP    \n");
		sql.append("   ON TP.PART_CODE = TOMA.PART_CODE    \n");
		if(!dutytupeint.equals(OemDictCodeConstants.DUTY_TYPE_DEALER.toString())){
			sql.append("   LEFT JOIN TT_OBSOLETE_MATERIAL_RELEASE_DCS TOMR    \n"); 
			sql.append("   ON TOMR.RELEASE_ID=TOMA.RELEASE_ID    \n"); 
		}
		sql.append("   WHERE 1=1    \n");
		//大区
		if(!StringUtils.isNullOrEmpty(queryParams.get("bigArea"))){ 
			sql.append(" 					AND TOR2.ORG_ID = ? \n");
	        params.add(queryParams.get("bigArea"));
	    }
		//小区
		if(!StringUtils.isNullOrEmpty(queryParams.get("smallArea"))){ 
			sql.append(" 					AND TOR.ORG_ID = ? \n");
	        params.add(queryParams.get("smallArea"));
	    }
		//调入经销商
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){ 
			String[] dealerCodes=queryParams.get("dealerCode").split(",");
	   		String code = "";
   			for(int i=0;i<dealerCodes.length;i++){
   				code+=""+dealerCodes[i]+",";
   				code=code.substring(0,code.length()-1);		
   			}
			sql.append(" 				AND	TDD.DEALER_CODE IN (?)  \n");
	        params.add(code);
	    }
		//配件代码
		if(!StringUtils.isNullOrEmpty(queryParams.get("partsCode"))){ 
			sql.append(" 				AND	TOMA.PART_CODE LIKE ? \n");
	        params.add("%"+queryParams.get("partsCode")+"%");
	    }
		//配件名称
		if(!StringUtils.isNullOrEmpty(queryParams.get("partsname"))){ 
			sql.append(" 				AND	TOMA.PART_NAME Like ? \n");
	        params.add("%"+queryParams.get("partsname")+"%");
	    }
		//状态
		if(!StringUtils.isNullOrEmpty(queryParams.get("status"))){ 
			String status = queryParams.get("status");
			if("70351001".equals(status)){
				sql.append(" 				AND	TOMA.STATUS IN ('70351001','70351002','70351003') \n");
			}else if("70351006".equals(status)){
				sql.append(" 				AND	TOMA.STATUS IN ('70351005','70351006') \n");
			}else{
				sql.append(" 				AND	TOMA.STATUS IN ('70351004') \n");
			}
			
	    }
		//调出经销商
		if(!StringUtils.isNullOrEmpty(queryParams.get("TDdealerCode"))){ 
			String[] dealerCodes=queryParams.get("TDdealerCode").split(",");
	   		String code = "";
   			for(int i=0;i<dealerCodes.length;i++){
   				code+=""+dealerCodes[i]+",";
   				code=code.substring(0,code.length()-1);		
   			}
			sql.append(" 				AND	TD.DEALER_CODE IN (?)  \n");
	        params.add(code);
	    }
		//完成时间
		if(!StringUtils.isNullOrEmpty(queryParams.get("beginDate"))){ 
		     
			sql.append(" AND  DATE_FORMAT(TOMA.PUT_WAREHOUS_DATE,'%Y-%m-%d') >='" + queryParams.get("beginDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endDate"))){ 
        	
        	sql.append(" AND  DATE_FORMAT(TOMA.PUT_WAREHOUS_DATE,'%Y-%m-%d') <='" + queryParams.get("endDate") +"' \n");
        }
		if(dutytupeint.equals(OemDictCodeConstants.DUTY_TYPE_DEALER.toString())){
			sql.append("   AND ((TOMA.RELEASE_DEALER_CODE = '"+dealercode+"' )or (TOMA.APPLY_DEALER_CODE = '"+dealercode+"'))   \n"); 
		}
		return sql.toString();
	}

	/**
	 * 呆滞品交易历史下载查询
	 * @param queryParams
	 * @return
	 */
	public List<Map> queryDownLoadList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getAllListSql(queryParams, params);
		return  OemDAOUtil.findAll(sql, params);
	}

	/**
	 * 售后大区
	 * @return
	 */
	public List<Map> bigAreaList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select BIG_AREA_ID,  \n");
		sql.append("       BIG_AREA_NAME,  \n");
		sql.append("       substring(SMALL_AREA_ID,1,char_length(SMALL_AREA_ID)-1) as SMALL_AREA_ID,  \n");
		sql.append("       SUBSTRING(SMALL_AREA_NAME,1,CHAR_LENGTH(SMALL_AREA_NAME)-1) AS SMALL_AREA_NAME  \n");
		sql.append("FROM  (SELECT A.ORG_ID AS BIG_AREA_ID,  \n");
		sql.append("  A.ORG_NAME AS BIG_AREA_NAME, \n");
		sql.append("     REPLACE(GROUP_CONCAT(B.ORG_ID, ','),',,',',')  SMALL_AREA_ID, \n");
		sql.append("     REPLACE(GROUP_CONCAT(B.ORG_NAME, ','),',,',',')   SMALL_AREA_NAME \n");
		sql.append("     FROM TM_ORG A  \n");
		sql.append("     INNER JOIN TM_ORG B  \n");
		sql.append("      ON B.PARENT_ORG_ID = A.ORG_ID  \n");
		sql.append("      AND B.ORG_LEVEL = '3'   \n");
		sql.append(" WHERE A.ORG_LEVEL = '2' \n");
		sql.append("   AND A.DUTY_TYPE = '"+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"'  \n");
		sql.append("   AND A.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_02+"'\n");
		sql.append(" GROUP BY A.ORG_ID, A.ORG_NAME ) as A \n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> smallAreaList(Long bigArea) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  \n");
		sql.append("       substring(SMALL_AREA_ID,1,char_length(SMALL_AREA_ID)-1) as SMALL_AREA_ID,  \n");
		sql.append("       SUBSTRING(SMALL_AREA_NAME,1,CHAR_LENGTH(SMALL_AREA_NAME)-1) AS SMALL_AREA_NAME  \n");
		sql.append("FROM  (SELECT A.ORG_ID AS BIG_AREA_ID,  \n");
		sql.append("  A.ORG_NAME AS BIG_AREA_NAME, \n");
		sql.append("     REPLACE(GROUP_CONCAT(B.ORG_ID, ','),',,',',')  SMALL_AREA_ID, \n");
		sql.append("     REPLACE(GROUP_CONCAT(B.ORG_NAME, ','),',,',',')   SMALL_AREA_NAME \n");
		sql.append("     FROM TM_ORG A  \n");
		sql.append("     INNER JOIN TM_ORG B  \n");
		sql.append("      ON B.PARENT_ORG_ID = A.ORG_ID  \n");
		sql.append("      AND B.ORG_LEVEL = '3'   \n");
		sql.append(" WHERE A.ORG_LEVEL = '2' \n");
		sql.append("   AND A.DUTY_TYPE = '"+OemDictCodeConstants.DUTY_TYPE_LARGEREGION+"'  \n");
		sql.append("   AND A.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_02+"'\n");
		sql.append("   AND A.ORG_ID = '"+bigArea+"'\n");
		sql.append("  GROUP BY A.ORG_ID, A.ORG_NAME ) as a \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

}
