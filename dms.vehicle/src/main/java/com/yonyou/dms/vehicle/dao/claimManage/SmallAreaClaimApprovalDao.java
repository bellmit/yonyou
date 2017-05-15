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
import com.yonyou.dms.vehicle.domains.DTO.claimManage.ClaimManageDTO;

/**
* @author liujm
* @date 2017年5月3日
*/

@Repository
public class SmallAreaClaimApprovalDao extends OemBaseDAO{
	
	
	/**
	 * 
	 * @param queryParam
	 * @param type tab页标记
	 * @return
	 */
	public PageInfoDto getSmallAreaClaimApprovalQuery(Map<String, String> queryParam, Integer type) {
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
		sql.append("SELECT TWC.CLAIM_ID, TWC.CLAIM_NO, TWC.RO_NO, TWC.VIN, TWC.STATUS, TWC.PASS_DATE, COALESCE(TWC.SUBMIT_COUNT,0) SUBMIT_COUNT  , DATE_FORMAT(TWC.APPLY_DATE,'%Y-%c-%d %H:%i:%s') APPLY_DATE \n");
		sql.append("		,TWR.REPAIR_ID, TWR.REPAIR_NO, TWR.ORDER_TYPE \n");
		sql.append("		,COALESCE(DATE_FORMAT(TWR.REPAIR_DATE,'%Y-%c-%d'),'') REPAIR_DATE \n");
		sql.append("		,TWCT.CLAIM_TYPE_CODE, TWCT.CLAIM_TYPE  ,TVM.MATERIAL_NAME  , \n");
		sql.append("		TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME  ,TWC.SMALL_AREA_APPROVAL_STATUS, \n");
		sql.append("		TWC.BIG_AREA_APPROVAL_STATUS,TWC.CLAIM_GROUP_APPROVAL_STATUS  , \n");
		sql.append("		DATE_FORMAT(TWC.SMALL_AREA_APPROVAL_DATE,'%Y-%c-%d %H:%i:%s') SMALL_AREA_APPROVAL_DATE , DATE_FORMAT(TWC.BIG_AREA_APPROVAL_DATE,'%Y-%c-%d %H:%i:%s') BIG_AREA_APPROVAL_DATE, \n");
		sql.append("		DATE_FORMAT(TWC.CLAIM_GROUP_APPROVAL_DATE,'%Y-%c-%d %H:%i:%s') CLAIM_GROUP_APPROVAL_DATE  \n");
		sql.append("	FROM TT_WR_CLAIM_DCS TWC  \n");
		sql.append("	LEFT JOIN TT_WR_REPAIR_DCS TWR ON TWC.RO_NO=TWR.REPAIR_NO  \n");
		sql.append("	LEFT JOIN TM_VEHICLE_DEC TV ON TWC.VIN=TV.VIN  \n");
		sql.append("	LEFT JOIN TM_VHCL_MATERIAL TVM ON TV.MATERIAL_ID=TVM.MATERIAL_ID  \n");
		sql.append("	LEFT JOIN TM_DEALER TD ON TWC.DEALER_ID = TD.DEALER_ID  \n");
		sql.append("	LEFT JOIN TT_WR_CLAIMTYPE_DCS TWCT ON TWCT.CLAIM_TYPE_CODE=TWC.CLAIM_TYPE \n");
		sql.append("	INNER JOIN TM_DEALER_ORG_RELATION TDOR ON TDOR.DEALER_ID = TD.DEALER_ID  \n");
		sql.append("	INNER JOIN TM_ORG  TOR3 ON (TOR3.ORG_ID = TDOR.ORG_ID AND TOR3.ORG_LEVEL = 3 AND TOR3.BUSS_TYPE = 12351002) \n");
		sql.append("	WHERE  TWC.IS_DEL=0  \n");
		//		#条件
		//sql.append("		AND TOR3.org_id = "+loginInfo.getOrgId()+"  #ORG_ID \n");
		
		sql.append("		AND TWC.STATUS =  "+OemDictCodeConstants.CLAIM_STATUS_02+" \n");
		
		//查询条件
		//经销商
		if(!StringUtils.isNullOrEmpty(queryParam.get("dealerCode"))){
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
		
		
		//小区未审核 
		if(type == 1){
			sql.append(" and TWC.SMALL_AREA_APPROVAL_STATUS =  ? 	\n"); //小区未审批
			sql.append(" and TWC.BIG_AREA_APPROVAL_STATUS = ?  		\n"); //大区未审批
			sql.append(" and TWC.CLAIM_GROUP_APPROVAL_STATUS = ? 	\n"); //索赔组未审批
			params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_01); //未审批
			params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_01); //未审批
			params.add(OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_01); //未审批
		}
		//小区审批通过
		if(type == 2){
			sql.append(" and TWC.SMALL_AREA_APPROVAL_STATUS =  ? 	\n"); //小区已通过
			params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_02); //小区-已审批通过
		}
		
		//小区 - 审批驳回
		if(type == 3){
			sql.append(" and TWC.SMALL_AREA_APPROVAL_STATUS =  ? 	\n"); //小区驳回
			params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_03); // 小区 - 审批驳回
		}
		//大区-审批驳回
		if(type == 4){
			sql.append(" and  TWC.SMALL_AREA_APPROVAL_STATUS =  ? 	\n"); 
			sql.append(" and  TWC.BIG_AREA_APPROVAL_STATUS = ?  	\n"); 
			params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_02); // 小区 - 审批通过
			params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_03); // 大区- 审批驳回
		}		
		//索赔组-审批驳回
		if(type == 5){
			sql.append(" and TWC.SMALL_AREA_APPROVAL_STATUS =  ? 	\n"); 
			sql.append(" and TWC.BIG_AREA_APPROVAL_STATUS = ?  		\n");  
			sql.append(" and TWC.CLAIM_GROUP_APPROVAL_STATUS = ? 	\n");
			params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_02); // 小区 - 审批通过
			params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_02); // 大区- 审批通过
			params.add(OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_03); //索赔组- 审批驳回
		}


		//sql.append("	ORDER BY TWC.CLAIM_NO DESC \n");

		
		return sql.toString();
	}
	
	/**
	 * 小区 审核历史
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
	/**
	 * 校验 防止同时审批
	 * @param cmDto
	 * @param type
	 * @return
	 */
	public boolean checkData(ClaimManageDTO cmDto, Integer type){
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT * FROM TT_WR_CLAIM_DCS WHERE  DEALER_CODE =  ? AND CLAIM_ID = ? AND SMALL_AREA_APPROVAL_STATUS = ?  AND BIG_AREA_APPROVAL_STATUS = ? AND CLAIM_GROUP_APPROVAL_STATUS = ? AND IS_DEL = ?    \n");
		params.add(cmDto.getDealerCode()); //经销商代码
		params.add(Long.parseLong(cmDto.getClaimId().toString())); //索赔单ID
        if(type == 4){ //被大区驳回
        	params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_02); //审批通过
        	params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_03); //审批驳回
        	params.add(OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_01); //未审批
        }else if(type == 5){ //被索赔组驳回
        	params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_02); //审批通过
        	params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_02); //审批通过
        	params.add(OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_03); //审批驳回
        }else{
        	params.add(OemDictCodeConstants.SMALL_AREA_APPROVAL_STATUS_01); //未审批
        	params.add(OemDictCodeConstants.BIG_AREA_APPROVAL_STATUS_01); //审批通过
        	params.add(OemDictCodeConstants.CLAIM_GROUP_APPROVAL_STATUS_01); //未审批
        }
        params.add(OemDictCodeConstants.IS_DEL_00); //有效
        
        List<Map> resultList = OemDAOUtil.findAll(sql.toString(), params);
        
		boolean flag = false;
		
		if(resultList != null && resultList.size()>0){
			flag = true;
		}
		
		return flag;
	}
	
}
	
	
