package com.yonyou.dms.vehicle.dao.claimManage;

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
* @author liujm
* @date 2017年4月28日
*/


@Repository
public class ClaimStatusSearchDealerDao extends OemBaseDAO{
	
	
	/**
	 * 查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getClaimStatusSearchDealerQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	/**
	 * 下载
	 * @param queryParam
	 * @return
	 */
	public List<Map> getClaimStatusSearchDealerDownload(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		List<Map> resultList = OemDAOUtil.downloadPageQuery(sql, params);
		return resultList;

	}
	
	/**
	 * SQL组装   查询（主页面） 
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, List<Object> params) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWC.CLAIM_ID,TWC.CLAIM_NO,TWR.REPAIR_ID,TWC.RO_NO,TWR.REPAIR_NO,TWR.ORDER_TYPE, \n");
		sql.append("	TVM.MATERIAL_NAME,TWC.VIN,TWC.STATUS,TWCT.CLAIM_TYPE_CODE,TWC.CLAIM_CATEGORY,TWCT.CLAIM_TYPE, DATE_FORMAT(TWC.PASS_DATE,'%Y-%c-%d') PASS_DATE,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME, \n");
		sql.append("	COALESCE(TWC.MAIN_PART,'') MAIN_PART, \n");
		sql.append("	COALESCE(TWC.ACTIVITY_CODE,'') ACTIVITY_CODE, CONCAT(COALESCE(TWC.SUBMIT_COUNT,0))  SUBMIT_COUNT, \n");
		sql.append("	COALESCE(DATE_FORMAT(TWC.APPLY_DATE,'%Y-%c-%d'),'') APPLY_DATE, \n");
		sql.append("	COALESCE(DATE_FORMAT(TWR.REPAIR_DATE,'%Y-%c-%d'),'') REPAIR_DATE, \n");
		sql.append("	COALESCE(TWC.PART_FEE,0) PART_FEE,COALESCE(TWC.LABOUR_FEE,0) LABOUR_FEE,COALESCE(TWC.OTHER_AMOUNT,0) OTHER_AMOUNT, \n");
		sql.append("	COALESCE(TWC.PART_FEE,0)+COALESCE(TWC.LABOUR_FEE,0)+COALESCE(TWC.OTHER_AMOUNT,0) TOTAL_AMOUNT \n");
		sql.append("FROM TT_WR_CLAIM_DCS TWC  \n");
		sql.append("	LEFT JOIN TT_WR_REPAIR_DCS TWR ON TWC.RO_NO=TWR.REPAIR_NO  \n");
		sql.append("	LEFT JOIN TM_VEHICLE_DEC TV ON TWC.VIN=TV.VIN  \n");
		sql.append("	LEFT JOIN TM_VHCL_MATERIAL TVM ON TV.MATERIAL_ID=TVM.MATERIAL_ID  \n");
		sql.append("	LEFT JOIN TM_DEALER TD ON TWC.DEALER_ID = TD.DEALER_ID  \n");
		sql.append("	LEFT JOIN TT_WR_CLAIMTYPE_DCS TWCT ON TWCT.CLAIM_TYPE_CODE=TWC.CLAIM_TYPE \n");
		sql.append("WHERE  TWC.IS_DEL=0      \n");
		
		
		//索赔单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimNo"))){
			sql.append("	AND TWC.CLAIM_NO LIKE'%"+queryParam.get("claimNo")+"%' \n");
		}
		//索赔申请日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStartDate"))){
			sql.append("	AND TWC.APPLY_DATE >= DATE_FORMAT('"+queryParam.get("claimStartDate")+"','%Y-%c-%d')  \n");
		}
		//索赔申请日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimEndDate"))){
			sql.append("	AND TWC.APPLY_DATE <= DATE_FORMAT('"+queryParam.get("claimEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s') \n");
		}
		//审核通过日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("passStartDate"))){
			sql.append("	AND TWC.PASS_DATE >= DATE_FORMAT('"+queryParam.get("passStartDate")+"','%Y-%c-%d')  \n");
		}
		//审核通过日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("passEndDate"))){
			sql.append("	AND TWC.PASS_DATE <= DATE_FORMAT('"+queryParam.get("passEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sql.append("	AND  TV.VIN LIKE '%"+queryParam.get("vin")+"%'  \n");
		}
		//索赔状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStatus"))){
			sql.append(" 	AND TWC.STATUS=	"+queryParam.get("claimStatus")+"		\n");
		}
		//索赔类型
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimType"))){
			sql.append("	AND  TWC.CLAIM_TYPE ='"+queryParam.get("claimType")+"' \n");
		}
		//维修工单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))){
			sql.append("	AND TWC.RO_NO LIKE '%"+queryParam.get("repairNo")+"%' \n");
		}
		
		sql.append("	AND TWC.STATUS <> 40081001 \n");
		//公司ID、经销商ID
		sql.append("	AND TWC.OEM_COMPANY_ID= "+loginInfo.getOemCompanyId()+" \n");
		sql.append("	AND TWC.DEALER_ID="+loginInfo.getDealerId()+" \n");
		
		return sql.toString();
	}
	
	
	/**
	 * 索赔申请单状态跟踪明细 审核历史
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getClaimAuditQueryDetail(Long  claimId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT L.LOG_ID,	#表ID \n");
		sql.append("       DATE_FORMAT(L.CHECK_DATE,'%Y-%c-%d %H:%i:%s') AS CHECK_DATE_CHAR, #审核日期 \n");
		sql.append("       L.CHECK_DATE,	#审核日期 \n");
		sql.append("       L.CLAIM_ID,	#索赔单ID \n");
		sql.append("       COALESCE(U.NAME,'自动审核') AS NAME, 	#审核人 \n");
		sql.append("       L.CHECK_DESC,	#审核意见 \n");
		sql.append("       COALESCE(L.CHECK_CODE,'') AS CHECK_CODE,	#拒绝驳回代码 \n");
		sql.append("       COALESCE(L.CODE_DESC,'') AS CODE_DESC,	#拒绝驳回代码描述 \n");
		sql.append("       L.STATUS 	#申请单状态 \n");
		sql.append("  FROM TT_WR_CLAIMCHECK_LOG_DCS L \n");
		sql.append("  LEFT JOIN TC_USER U ON L.CHECK_USER=U.USER_ID \n");
		sql.append("  WHERE 1=1 \n");
		sql.append("  AND L.CHECK_TYPE = 40251002 \n");
		sql.append(" AND L.CLAIM_ID = '"+claimId+"' \n");
		sql.append(" \n");	
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 索赔申请单状态跟踪明细 附件列表
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getAccessoryQueryList(Long  claimId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT * FROM FS_FILEUPLOAD WHERE STATUS=10011001 AND  YWZJ ='"+claimId+"'  \n");
		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 
	 * @param queryParam
	 * @return
	 */
	public List<Map> getQueryClaimTypeList() {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT * FROM TT_WR_CLAIMTYPE_DCS   \n");
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
		return resultList;

	}
	

}
