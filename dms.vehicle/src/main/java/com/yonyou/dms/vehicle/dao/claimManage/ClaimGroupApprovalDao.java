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
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujm
* @date 2017年5月4日
*/

@Repository
public class ClaimGroupApprovalDao extends OemBaseDAO{
	
	
	
	/**
	 * 查询  索赔组列表
	 * @param queryParam
	 * @param type tab页标记
	 * @return
	 */
	public PageInfoDto getClaimGroupApprovalQuery(Map<String, String> queryParam, Integer type) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam,  type, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

	}
	
	/**
	 * SQL组装   查询
	 * @param queryParam
	 * @param params
	 * @return
	 */
	private String getQuerySql(Map<String, String> queryParam, Integer type, List<Object> params) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWC.CLAIM_ID, TWC.CLAIM_NO, TWC.RO_NO, TWC.VIN, TWC.STATUS, TWC.PASS_DATE, \n");
		sql.append("		COALESCE(TWC.SUBMIT_COUNT,0) SUBMIT_COUNT  , DATE_FORMAT(TWC.APPLY_DATE,'%Y-%c-%d %H:%i:%s') APPLY_DATE \n");
		sql.append("		,TWR.REPAIR_ID, TWR.REPAIR_NO, TWR.ORDER_TYPE \n");
		sql.append("		,COALESCE(DATE_FORMAT(TWR.REPAIR_DATE,'%Y-%c-%d'),'') REPAIR_DATE \n");
		sql.append("		,TWCT.CLAIM_TYPE_CODE, TWCT.CLAIM_TYPE  ,TVM.MATERIAL_NAME  , \n");
		sql.append("		TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME  , \n");
		sql.append("		TWC.SMALL_AREA_APPROVAL_STATUS, TWC.BIG_AREA_APPROVAL_STATUS, \n");
		sql.append("		TWC.CLAIM_GROUP_APPROVAL_STATUS  , DATE_FORMAT(TWC.SMALL_AREA_APPROVAL_DATE,'%Y-%c-%d %H:%i:%s') SMALL_AREA_APPROVAL_DATE , \n");
		sql.append("		DATE_FORMAT(TWC.BIG_AREA_APPROVAL_DATE,'%Y-%c-%d %H:%i:%s') BIG_AREA_APPROVAL_DATE, DATE_FORMAT(TWC.CLAIM_GROUP_APPROVAL_DATE,'%Y-%c-%d %H:%i:%s') CLAIM_GROUP_APPROVAL_DATE  \n");
		sql.append("	FROM TT_WR_CLAIM_DCS TWC  \n");
		sql.append("	  LEFT JOIN TT_WR_REPAIR_DCS TWR ON TWC.RO_NO=TWR.REPAIR_NO  \n");
		sql.append("	  LEFT JOIN TM_VEHICLE_DEC TV ON TWC.VIN=TV.VIN  \n");
		sql.append("	  LEFT JOIN TM_VHCL_MATERIAL TVM ON TV.MATERIAL_ID=TVM.MATERIAL_ID  \n");
		sql.append("	  LEFT JOIN TM_DEALER TD ON TWC.DEALER_ID = TD.DEALER_ID  \n");
		sql.append("	  LEFT JOIN TT_WR_CLAIMTYPE_DCS TWCT ON TWCT.CLAIM_TYPE_CODE=TWC.CLAIM_TYPE \n");
		sql.append("	WHERE  TWC.IS_DEL= 0      \n");
		sql.append("			AND TWC.STATUS =  40081002 \n");

		//	条件 		
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
			//sql.append("		and TD.DEALER_CODE in("+queryParam.get("dealerCode")+") \n");
			sql.append("	AND TD.DEALER_CODE IN ( ? ) \n");
			params.add(queryParam.get("dealerCode"));
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sql.append("		and TWC.vin LIKE '%"+queryParam.get("vin")+"%' \n");
		}
		//
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStartDate"))){
			sql.append("		and TBCRA.REPORT_DATE >= DATE_FORMAT('"+queryParam.get("claimStartDate")+" 00:00:00','%Y-%c-%d %H:%i:%s')  \n");
		}
		//索赔申请日期起始
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimEndDate"))){
			sql.append("		and TBCRA.REPORT_DATE <= DATE_FORMAT('"+queryParam.get("claimEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//索赔申请日期结束
		if(!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))){
			sql.append("		AND TWC.RO_NO LIKE '%"+queryParam.get("repairNo")+"%' \n");
		}
		//索赔单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimNo"))){
			sql.append("		and TWC.CLAIM_NO =  '"+queryParam.get("claimNo")+"'  \n");
		}
		
		//索赔组未审核
		if(type == 1){
			sql.append(" and TWC.SMALL_AREA_APPROVAL_STATUS =  ? "); 
			sql.append(" and TWC.BIG_AREA_APPROVAL_STATUS = ?  "); 
			sql.append(" and TWC.CLAIM_GROUP_APPROVAL_STATUS = ? ");
			params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_02); //小区审批通过
			params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_02); //大区审批通过
			params.add(OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_01); //索赔组未审批
		} 
		//索赔组审核通过
		if(type == 2){
			sql.append(" and TWC.SMALL_AREA_APPROVAL_STATUS =  ? ");
			sql.append(" and TWC.BIG_AREA_APPROVAL_STATUS = ?  "); 
			sql.append(" and TWC.CLAIM_GROUP_APPROVAL_STATUS = ?  ");
			params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_02); //小区-审批通过
			params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_02); //大区-审批通过
			params.add(OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_02); //索赔组-审批通过
		} 
		//索赔组审核驳回
		if(type == 3){
			sql.append(" and TWC.SMALL_AREA_APPROVAL_STATUS =  ? "); 
			sql.append(" and TWC.BIG_AREA_APPROVAL_STATUS = ?  "); 
			sql.append(" and TWC.CLAIM_GROUP_APPROVAL_STATUS = ? ");
			params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_02); //小区-审批通过
			params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_02); //大区-审批通过
			params.add(OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_03); //索赔组驳回
		} 
		//sql.append("ORDER BY TWC.CLAIM_NO DESC		"); 
		return sql.toString();
	}
	
	
	/**
	 * 索赔组 审核历史
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getClaimAuditQueryDetail(Long  claimId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TU.NAME,DATE_FORMAT(TWCAS.APPROVAL_DATE,'%Y-%c-%d')  AS AP_DATE, \n");
		sql.append("		TWCAS.APPROVAL_REMARK, TWCAS.APPROVAL_STATUS  \n");
		sql.append("	FROM TT_WR_CLAIM_APPROVAL_HIS_DCS TWCAS , TC_USER TU  \n");
		sql.append("	WHERE TWCAS.APPROVAL_USER = TU.USER_ID   \n");
		sql.append("		AND TWCAS.CLAIM_ID = "+claimId+"   \n");	
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	

}
