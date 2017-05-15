package com.yonyou.dms.part.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
 * 呆滞品发布信息查询Dao
 * @author ZhaoZ
 * @date 2017年3月24日
 */
@Repository
public class PartObsoleteMaterialReleseDao extends OemBaseDAO{

	/**
	 * 呆滞品发布信息查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getPartObsoleteMaterialList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getPartObsoleteMaterialSql(queryParams, params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 呆滞品发布信息查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getPartObsoleteMaterialSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TOMR.RELEASE_ID,");
		sql.append("       TOR3.ORG_NAME ORGMIN,\n");
		sql.append("       TOR2.ORG_NAME ORGMAX, \n");
		sql.append("       "+loginInfo.getUserId()+" AS CODE, \n");
		sql.append("       TD.DEALER_CODE, \n");
		sql.append("       TD.DEALER_SHORTNAME,\n");
		sql.append("       TOMR.LINKMAN_NAME, \n");
		sql.append("       TOMR.LINKMAN_TEL,\n");
		sql.append("       TOMR.WAREHOUSE,\n");
		sql.append("       TOMR.PART_CODE, \n");
		sql.append("       CASE WHEN TOMR.PART_NAME IS NULL OR TOMR.PART_NAME = '' \n");
		sql.append("          THEN TPPB.PART_NAME \n");
		sql.append("          ELSE TOMR.PART_NAME \n");
		sql.append("        END \n");
		sql.append("       AS PART_NAME, \n");
		sql.append("       TOMR.RELEASE_NUMBER, \n");
		sql.append("       TOMR.APPLY_NUMBER, \n");
		sql.append("       TOMR.SALES_PRICE,\n");
		sql.append("       DATE_FORMAT(TOMR.RELEASE_DATE,'%Y-%m-%d') RELEASE_DATE, \n");
		sql.append("       DATE_FORMAT(TOMR.END_DATE,'%Y-%m-%d') END_DATE \n");
		sql.append("  FROM TT_OBSOLETE_MATERIAL_RELEASE_dcs TOMR \n");
		sql.append("  INNER JOIN TM_DEALER TD ON TOMR.DEALER_CODE = TD.DEALER_CODE \n");
		sql.append("  INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append("  INNER JOIN TM_ORG TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 ) \n");
		sql.append("  INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 AND TOR2.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_02+"' ) \n");
		sql.append("  LEFT JOIN TT_PT_PART_BASE_dcs TPPB ON TOMR.PART_CODE = TPPB.PART_CODE \n");
		sql.append("  WHERE TOMR.APPLY_NUMBER > 0 \n");
		sql.append("  AND TOMR.STATUS = '"+OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_01+"' \n");
		if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
			sql.append("  AND TOMR.PART_CODE LIKE ?");
	        params.add("%"+queryParams.get("partCode")+"%");
	    }
		if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
			sql.append("  AND TOMR.PART_NAME LIKE ?");
	        params.add("%"+queryParams.get("partName")+"%");
	    }
		if (!StringUtils.isNullOrEmpty(queryParams.get("applyNum"))) {
			Pattern pattern = Pattern.compile("[0-9]*"); 
			boolean flag = pattern.matcher(queryParams.get("applyNum")).matches();    
			if(!flag){
				sql.append("  AND TOMR.APPLY_NUMBER <= -1");
			}else{
				sql.append("  AND TOMR.APPLY_NUMBER >= ?");
				params.add(queryParams.get("applyNum"));
			}
			
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
			sql.append("  AND TOMR.DEALER_CODE IN (?)");
			params.add(queryParams.get("dealerCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("bigArea"))) {
			sql.append("  AND TOR2.ORG_ID = ?");
			params.add(queryParams.get("bigArea"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("smallArea"))) {
			sql.append("  AND TOR3.ORG_ID = ?");
			params.add(queryParams.get("smallArea"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("province"))) {
			sql.append("  AND TD.PROVINCE_ID = ?");
			params.add(queryParams.get("province"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("cityId"))) {
			sql.append("  AND TD.CITY_ID = ?");
			params.add(queryParams.get("cityId"));
		}
		return sql.toString();
	}

	
	/**
	 * 呆滞品发布信息下载查询
	 * @param queryParams
	 * @return
	 */
	public List<Map> queryDownLoadList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getDownLoadListSql(queryParams, params);
		return OemDAOUtil.findAll(sql, params);
	}
	/**
	 * 呆滞品发布信息下载查询SQL
	 * @param queryParams
	 * @return
	 */
	private String getDownLoadListSql(Map<String, String> queryParams, List<Object> params) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TOMR.RELEASE_ID,");
		sql.append("       TOR3.ORG_NAME ORGMIN,\n");
		sql.append("       TOR2.ORG_NAME ORGMAX, \n");
		sql.append("       TD.DEALER_CODE, \n");
		sql.append("       TD.DEALER_SHORTNAME,\n");
		sql.append("       TOMR.LINKMAN_NAME, \n");
		sql.append("       TOMR.LINKMAN_TEL,\n");
		sql.append("       TOMR.WAREHOUSE,\n");
		sql.append("       TOMR.PART_CODE, \n");
		sql.append("       CASE WHEN TOMR.PART_NAME IS NULL OR TOMR.PART_NAME = '' \n");
		sql.append("          THEN TPPB.PART_NAME \n");
		sql.append("          ELSE TOMR.PART_NAME \n");
		sql.append("        END \n");
		sql.append("       AS PART_NAME, \n");
		sql.append("       TOMR.RELEASE_NUMBER, \n");
		sql.append("       TOMR.APPLY_NUMBER, \n");
		sql.append("       TOMR.SALES_PRICE,\n");
		sql.append("       DATE_FORMAT(TOMR.RELEASE_DATE,'%Y-%m-%d') RELEASE_DATE, \n");
		sql.append("       DATE_FORMAT(TOMR.END_DATE,'%Y-%m-%d') END_DATE \n");
		sql.append("  FROM TT_OBSOLETE_MATERIAL_RELEASE_dcs TOMR \n");
		sql.append("  INNER JOIN TM_DEALER TD ON TOMR.DEALER_CODE = TD.DEALER_CODE \n");
		sql.append("  INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TD.DEALER_ID \n");
		sql.append("  INNER JOIN TM_ORG TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 ) \n");
		sql.append("  INNER JOIN TM_ORG TOR2 ON (TOR3.PARENT_ORG_ID = TOR2.ORG_ID AND TOR2.ORG_LEVEL = 2 AND TOR2.BUSS_TYPE = '"+OemDictCodeConstants.ORG_BUSS_TYPE_02+"' ) \n");
		sql.append("  LEFT JOIN TT_PT_PART_BASE_dcs TPPB ON TOMR.PART_CODE = TPPB.PART_CODE \n");
		sql.append("  WHERE TOMR.APPLY_NUMBER > 0 \n");
		sql.append("  AND TOMR.STATUS = '"+OemDictCodeConstants.PART_OBSOLETE_RELESE_STATUS_01+"' \n");
		if(!StringUtils.isNullOrEmpty(queryParams.get("partCode"))){ 
			sql.append("  AND TOMR.PART_CODE LIKE ?");
	        params.add("%"+queryParams.get("partCode")+"%");
	    }
		if(!StringUtils.isNullOrEmpty(queryParams.get("partName"))){ 
			sql.append("  AND TOMR.PART_NAME LIKE ?");
	        params.add("%"+queryParams.get("partName")+"%");
	    }
		if (!StringUtils.isNullOrEmpty(queryParams.get("applyNum"))) {
			sql.append("  AND TOMR.APPLY_NUMBER >= ?");
			params.add(queryParams.get("applyNum"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))) {
			sql.append("  AND TOMR.DEALER_CODE IN (?)");
			params.add(queryParams.get("dealerCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("bigArea"))) {
			sql.append("  AND TOR2.ORG_ID = ?");
			params.add(queryParams.get("bigArea"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("smallArea"))) {
			sql.append("  AND TOR3.ORG_ID = ?");
			params.add(queryParams.get("smallArea"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("province"))) {
			sql.append("  AND TD.PROVINCE_ID = ?");
			params.add(queryParams.get("province"));
		}
		if (!StringUtils.isNullOrEmpty(queryParams.get("cityId"))) {
			sql.append("  AND TOR3.TD.CITY_ID = ?");
			params.add(queryParams.get("cityId"));
		}
		return sql.toString();
	}

	/**
	 * 查询指定编号的呆滞品
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> queryReleaseList(Long id) {
		StringBuffer sql= new StringBuffer();
		sql.append(" SELECT TD.DEALER_CODE, \n");
		sql.append("        TD.DEALER_SHORTNAME, \n");
		sql.append("        TOMR.RELEASE_ID,\n");
		sql.append("        TOMR.PART_CODE, \n");
		sql.append("        CASE \n");
		sql.append("          WHEN TOMR.PART_NAME IS NULL OR TOMR.PART_NAME = '' THEN TPPB.PART_NAME \n");
		sql.append("		      ELSE TOMR.PART_NAME \n");
		sql.append("		    END AS PART_NAME, \n");
		sql.append("        TOMR.SALES_PRICE, \n");
		sql.append("        TOMR.APPLY_NUMBER, \n");
		sql.append("        TOMR.COST_PRICE, \n");
		sql.append("        TOMR.MEASURE_UNITS \n");
		sql.append(" FROM tt_obsolete_material_release_dcs TOMR \n");
		sql.append(" INNER JOIN TM_DEALER TD ON TOMR.DEALER_CODE = TD.DEALER_CODE \n");
		sql.append(" LEFT JOIN TT_PT_PART_BASE_dcs TPPB ON TOMR.PART_CODE = TPPB.PART_CODE \n");
		sql.append(" WHERE TOMR.RELEASE_ID = "+id+" \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

}
