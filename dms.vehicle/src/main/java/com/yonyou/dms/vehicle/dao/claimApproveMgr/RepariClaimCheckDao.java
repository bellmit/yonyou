package com.yonyou.dms.vehicle.dao.claimApproveMgr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 保修索赔
 * @author ZhaoZ
 * @date 2017年4月25日
 */
@Repository
public class RepariClaimCheckDao extends OemBaseDAO{

	/**
	 * 保修索赔申请审核查询
	 * @param authCode
	 * @param oemCompanyId
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto repairClaimList(String authCode, Long oemCompanyId, Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRepairClaimListSql(queryParams,params,authCode,oemCompanyId);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 保修索赔申请审核查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getRepairClaimListSql(Map<String, String> queryParams, List<Object> params,String authCode,Long oemCompanyId) {
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT * FROM (\n" );
		sqlStr.append("SELECT D.DEALER_CODE,\n" );
		sqlStr.append("       D.DEALER_SHORTNAME,\n" );
		sqlStr.append("       DATE_FORMAT(R.REPAIR_DATE,'%Y-%m-%d') AS REPAIR_DATE_CHAR, \n" );
		sqlStr.append("       R.REPAIR_DATE,\n" );
		sqlStr.append("       R.REPAIR_ID,\n" );
		sqlStr.append("       C.CLAIM_ID, \n" );
		sqlStr.append("       C.CLAIM_NO, \n" );
		sqlStr.append("       C.RO_NO, \n" );
		sqlStr.append("       C.MODEL, \n" );
		sqlStr.append("       C.VIN, \n" );
		sqlStr.append("       TWCT.CLAIM_TYPE_CODE,\n" );
		sqlStr.append("       TWCT.CLAIM_TYPE,\n" );
		sqlStr.append("       C.MAIN_PART,\n" );
		sqlStr.append("       DATE_FORMAT(C.APPLY_DATE,'%Y-%m-%d') AS APPLY_DATE_CHAR,\n" );
		sqlStr.append("       C.APPLY_DATE,\n" );	
		sqlStr.append("       C.STATUS \n" );
		sqlStr.append("  FROM TT_WR_CLAIM_dcs C\n" );
		sqlStr.append("  JOIN TM_DEALER D ON C.DEALER_ID = D.DEALER_ID\n" );//
		sqlStr.append("  LEFT JOIN TT_WR_REPAIR_dcs R ON C.RO_NO = R.REPAIR_NO\n");
		sqlStr.append("  left join TT_WR_CLAIMTYPE_dcs TWCT on TWCT.CLAIM_TYPE_CODE=C.CLAIM_TYPE    \n" );
		sqlStr.append("  WHERE C.IS_DEL = "+OemDictCodeConstants.IS_DEL_00+" \n");
		sqlStr.append("  AND C.STATUS = "+OemDictCodeConstants.CLAIM_STATUS_03+"\n");//审核中
		//sqlStr.append("  AND R.ORDER_TYPE = "+Constant.REPAIR_ORD_TYPE_02+"\n");//维修工单
		//sqlStr.append("  AND C.CLAIM_TYPE IN ("+Constant.CLAIM_TYPE_01+","+Constant.CLAIM_TYPE_03+","+Constant.CLAIM_TYPE_04+","+Constant.CLAIM_TYPE_05+")\n");//维修工单
		if(!StringUtils.isNullOrEmpty(oemCompanyId)){//公司ID不为空
			sqlStr.append(" and C.OEM_COMPANY_ID = '"+oemCompanyId+"' \n");
		}
		//授权代码
		if (!StringUtils.isNullOrEmpty(authCode)) {
			sqlStr.append(" and C.AUTH_CODE =  '"+authCode+"'\n");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("MAIN_PART"))){
			sqlStr.append("AND C.MAIN_PART LIKE ? \n");
			params.add("%"+queryParams.get("MAIN_PART")+"%");
		}
		if(!loginUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !loginUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())){
			sqlStr.append("      AND  C.DEALER_ID in ("+getDealersByArea(loginUser.getOrgId().toString())+")\n");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("CLAIM_NO"))){
			sqlStr.append("AND C.CLAIM_NO like ? \n");
			params.add("%"+queryParams.get("CLAIM_NO")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("MODEL"))){
			sqlStr.append("AND C.MODEL like ? \n");
			params.add("%"+queryParams.get("MODEL")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("RO_NO"))){
			sqlStr.append("AND C.RO_NO like ? \n");
			params.add("%"+queryParams.get("RO_NO")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("beginApplyDate"))){ 
		     
			sqlStr.append(" AND  DATE_FORMAT(C.APPLY_DATE,'%Y-%m-%d') >='" + queryParams.get("beginApplyDate") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("endApplyDate"))){ 
            
        	sqlStr.append(" AND  DATE_FORMAT(C.APPLY_DATE,'%Y-%m-%d') <='" + queryParams.get("endApplyDate") +"' \n");
        }
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			sqlStr.append("AND D.DEALER_CODE like ? \n");
			params.add("%"+queryParams.get("dealerCode")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerName"))){
			sqlStr.append("AND D.DEALER_SHORTNAME like ? \n");
			params.add("%"+queryParams.get("dealerName")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("CLAIM_TYPE"))){
			sqlStr.append("AND C.CLAIM_TYPE = ? \n");
			params.add(queryParams.get("CLAIM_TYPE"));
		}
		String vin = queryParams.get("vin");
		if (!StringUtils.isNullOrEmpty(vin)) {
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sqlStr.append(getVinsAuto(vin, "C"));
		}
		sqlStr.append(") DCS\n" );
		return sqlStr.toString();
	}

	/**
	 * 获得保修索赔申请审核信息
	 * @param claimId
	 * @param oemCompanyId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> claimInfoMap(Long claimId, Long oemCompanyId) {
		String sql = getclaimInfoMapSql(claimId,oemCompanyId);
		return OemDAOUtil.findFirst(sql, null);
	}

	/**
	 * 获得保修索赔申请审核信息SQL
	 * @param claimId
	 * @param oemCompanyId
	 * @return
	 */
	private String getclaimInfoMapSql(Long claimId, Long oemCompanyId) {
		StringBuffer sqlStr= new StringBuffer();
		//sqlStr.append("SELECT C.NWSCLAIM_NO,--NWS单号\n" );
		sqlStr.append("SELECT C.RO_NO,\n" );
		//获取维修方案
		sqlStr.append(" C.CUSTOMER_COMPLAIN, C.CHECK_REPAIR_PROCEDURES, C.DEAL_SCHEME, C.APPLY_REASONS, \n" );
		//获取各个分担费用
		sqlStr.append(" C.CUSTOMER_BEAR_FEE_PART, C.DEALER_BEAR_FEE_PART,  C.OEM_BEAR_FEE_PART, \n" );
		sqlStr.append(" C.CUSTOMER_BEAR_FEE_LABOR,C.DEALER_BEAR_FEE_LABOR, C.OEM_BEAR_FEE_LABOR, \n" );
		sqlStr.append(" C.CUSTOMER_BEAR_FEE_OTHER,C.DEALER_BEAR_FEE_OTHER, C.OEM_BEAR_FEE_OTHER, \n" );
		sqlStr.append(" C.SELECT_CATEGORY, C.SELECT_MODE, "); //选择分类，方式
		sqlStr.append(" A.ACTIVITY_CODE,\n" );
		sqlStr.append(" A.ACTIVITY_NAME,\n" );
		sqlStr.append(" R.REPAIR_ID,\n" );
		sqlStr.append(" C.PLATE_NO,\n" );
		sqlStr.append(" C.WARRANTY_DATE,\n" );
		sqlStr.append(" C.BRAND,\n" );
		sqlStr.append(" C.SERIES,\n" );
		sqlStr.append(" C.MODEL,\n" );
		sqlStr.append(" C.VIN,\n" );
		sqlStr.append(" C.RO_STARTDATE,\n" );
		sqlStr.append(" C.RO_ENDDATE,\n" );
		sqlStr.append(" C.MILLEAGE,\n" );
		sqlStr.append(" C.SUBMIT_COUNT,\n" );
		sqlStr.append(" C.LABOUR_PICE,\n" );//
		sqlStr.append(" C.DEDUCT_FEE,\n" );
		sqlStr.append(" C.DEDUCT_REMARK,\n" );
		sqlStr.append("C.CLAIM_NO,\n" );
		sqlStr.append("TWCT.CLAIM_TYPE,\n" );
		sqlStr.append("TWCT.CLAIM_TYPE_CODE,\n" );
		sqlStr.append("REPLACE(C.REMARK,'<br/>','\r\n') REMARK,\n" );
		sqlStr.append("C.LABOUR_FEE,\n" );
		sqlStr.append("C.PART_FEE,\n" );
		sqlStr.append("C.OTHER_AMOUNT,\n" );
		sqlStr.append("C.ALL_AMOUNT,\n" );//
		sqlStr.append("C.TAX_RATE,\n" );
		sqlStr.append("C.PART_PAY,\n" );
		sqlStr.append("DATE_FORMAT(C.APPLY_DATE,'%Y-%m-%d') AS APPLY_DATE_CHAR,-\n" );
		sqlStr.append("C.APPLY_DATE,\n" );
		sqlStr.append("D.DEALER_CODE,\n");
		sqlStr.append("D.DEALER_SHORTNAME,\n");
		sqlStr.append("U.ACNT,\n");
		sqlStr.append("U.NAME,\n");
		sqlStr.append("C.CLAIM_ID\n" );
		sqlStr.append("FROM TT_WR_CLAIM_DCS C LEFT JOIN TM_DEALER D ON (C.DEALER_ID = D.DEALER_ID)\n");
		sqlStr.append("                   LEFT JOIN TC_USER U ON (C.CREATE_BY=U.USER_ID)\n");
		sqlStr.append("                   LEFT JOIN TT_WR_REPAIR_DCS R ON (C.RO_NO = R.REPAIR_NO)\n");
		sqlStr.append("                   LEFT JOIN TT_WR_ACTIVITY_DCS A ON (C.ACTIVITY_CODE = A.ACTIVITY_CODE) \n");
		sqlStr.append("                   left join TT_WR_CLAIMTYPE_DCS TWCT on TWCT.CLAIM_TYPE_CODE=C.CLAIM_TYPE \n");
		sqlStr.append("WHERE 1=1 \n");
		if(!"".equals(oemCompanyId)&&!(null==oemCompanyId)){                 //公司ID不为空
			sqlStr.append("		AND C.OEM_COMPANY_ID = "+oemCompanyId+" \n");
		}
		if (Utility.testString(claimId)) {     //参数ID不为空
			sqlStr.append("		AND C.CLAIM_ID = "+claimId+" \n");
		}
		return sqlStr.toString();
	}

	/**
	 * 查询索赔单下的其他项目列表
	 * @param claimId
	 * @return
	 */
	public PageInfoDto otherItems(Long claimId) {
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT O.OTHER_REL_ID,\n" );
		sqlStr.append("       O.OTHER_FEE_CODE,\n" );
		sqlStr.append("       O.OTHER_FEE_NAME,\n" );
		sqlStr.append("       O.AMOUNT,\n" );
		sqlStr.append("       O.REMARK,\n" );
		sqlStr.append(" o.ACTIVITY_CODE \n");
		sqlStr.append("  FROM TT_WR_CLAIMOTHER_REL_dcs O\n" );
		sqlStr.append(" WHERE 1 = 1\n");
		if (Utility.testString(claimId)) {     //参数ID不为空
			sqlStr.append("		AND O.CLAIM_ID = "+claimId+" \n");
		}
		return OemDAOUtil.pageQuery(sqlStr.toString(), null);
	}

	/**
	 * 获得索赔单的零部件信息列表
	 * @param claimId
	 * @return
	 */
	public PageInfoDto repairParts(Long claimId) {
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT case when p.IS_MAIN = 10041001 then 10571001 else 10571002 end as IS_MAIN,\n" );
		sqlStr.append("       P.PART_CODE,\n" );
		sqlStr.append("       P.PART_NAME,\n" );
		sqlStr.append("       P.QUANTITY,\n" );
		sqlStr.append("       p.AMOUNT,p.PRICE,\n");
		sqlStr.append(" p.ACTIVITY_CODE \n");
		sqlStr.append("  FROM TT_WR_CLAIMPART_REL_dcs P\n" );
		if (Utility.testString(claimId)) {     //参数ID不为空
			sqlStr.append("		WHERE P.CLAIM_ID = "+claimId+" \n");
		}		
		return OemDAOUtil.pageQuery(sqlStr.toString(), null);
	}

	/**
	 * 查询索赔单与工时关系
	 * @param claimId
	 * @return
	 */
	public PageInfoDto clainLabours(Long claimId) {
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT O.LABOUR_CODE,\n" );
		sqlStr.append("       case when O.IS_MAIN = 10041001 then 10571001 else 10571002 end as IS_MAIN,\n" );
		sqlStr.append("       O.LABOUR_NAME,\n" );
		sqlStr.append("       O.LABOUR_NUM,\n" );
		sqlStr.append("       O.PRICE,\n" );
		sqlStr.append("       O.FEE ,\n" );
		sqlStr.append(" o.ACTIVITY_CODE \n");
		sqlStr.append("  FROM TT_WR_CLAIMLABOUR_REL_dcs O\n" );
		sqlStr.append(" WHERE 1 = 1\n");
		if (Utility.testString(claimId)) {     //参数ID不为空
			sqlStr.append("		AND O.CLAIM_ID = "+claimId+" \n");
		}
		return OemDAOUtil.pageQuery(sqlStr.toString(), null);
	}

	/**
	 * 查询索赔单审批历史信息
	 * @param claim
	 * @return
	 */
	public PageInfoDto historyList(Long claim) {
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT L.LOG_ID,\n" );
		sqlStr.append("       date_format(L.CHECK_DATE,'%Y-%m-%d  %h:%i:%s') AS CHECK_DATE_CHAR, \n" );
		sqlStr.append("       L.CHECK_DATE,\n" );
		sqlStr.append("       L.CLAIM_ID,\n" );
		sqlStr.append("       COALESCE(U.NAME,'自动审核') AS NAME, \n" );
		sqlStr.append("       L.CHECK_DESC,\n" );
		sqlStr.append("       COALESCE(L.CHECK_CODE,'') AS CHECK_CODE,\n");
		sqlStr.append("       COALESCE(L.CODE_DESC,'') AS CODE_DESC,\n");		
		sqlStr.append("       L.STATUS \n" );
		sqlStr.append("  FROM TT_WR_CLAIMCHECK_LOG_dcs L\n" );
		sqlStr.append("  LEFT JOIN TC_USER U ON L.CHECK_USER=U.USER_ID\n" );
		sqlStr.append("  WHERE 1=1 \n");
		sqlStr.append("  AND L.CHECK_TYPE = "+OemDictCodeConstants.CHECK_TYPE_02+" \n");
		if(Utility.testString(claim)){
			sqlStr.append(" AND L.CLAIM_ID = '"+claim+"' \n");
		}
		return OemDAOUtil.pageQuery(sqlStr.toString(), null);
	}

	/**
	 * 查询附件信息
	 * @param claim
	 * @return
	 */
	public PageInfoDto fujianList(Long claim) {
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT * FROM FS_FILEUPLOAD F WHERE STATUS="+OemDictCodeConstants.STATUS_ENABLE+"\n" );
		sqlStr.append("    AND F.FJID = "+claim+" \n" );
		return OemDAOUtil.pageQuery(sqlStr.toString(), null);
	}

	/**
	 * 索赔单状态跟踪查询
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto getOrderInfoList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRepairClaimListSql(queryParams,params);
		return OemDAOUtil.pageQuery(sql, params);
	}

	/**
	 * 索赔单状态跟踪查询SQL
	 * @param queryParams
	 * @param params
	 * @return
	 */
	private String getRepairClaimListSql(Map<String, String> queryParams, List<Object> params) {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT TWC.CLAIM_ID,TWC.CLAIM_NO,TWR.REPAIR_ID,TWC.RO_NO,TWR.ORDER_TYPE,\n" );
		sqlStr.append("TVM.MATERIAL_NAME,TWC.VIN,TWC.STATUS,TWCT.CLAIM_TYPE_CODE,TWCT.CLAIM_TYPE,TWC.CLAIM_CATEGORY,date_format(TWC.PASS_DATE,'%Y-%m-%d') PASS_DATE,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,\n" );
		sqlStr.append("COALESCE(TWC.MAIN_PART,'') MAIN_PART,TWC.MODEL,TWC.ALL_AMOUNT,\n" );
		sqlStr.append("COALESCE(TWC.ACTIVITY_CODE,'') ACTIVITY_CODE,COALESCE(TWC.SUBMIT_COUNT,0) SUBMIT_COUNT,\n" );
		sqlStr.append("COALESCE(date_format(TWC.APPLY_DATE,'%Y-%m-%d'),'') APPLY_DATE,\n" );
		sqlStr.append("COALESCE(date_format(TWR.REPAIR_DATE,'%Y-%m-%d'),'') REPAIR_DATE,\n" );
		sqlStr.append("COALESCE(TWC.PART_FEE,0) PART_FEE,COALESCE(TWC.LABOUR_FEE,0) LABOUR_FEE,COALESCE(TWC.OTHER_AMOUNT,0) OTHER_AMOUNT,\n" );
		sqlStr.append("COALESCE(TWC.PART_FEE,0)+COALESCE(TWC.LABOUR_FEE,0)+COALESCE(TWC.OTHER_AMOUNT,0) TOTAL_AMOUNT \n" );
		sqlStr.append("FROM TT_WR_CLAIM_dcs TWC " +
				" left join TT_WR_REPAIR_dcs TWR on TWC.RO_NO=TWR.REPAIR_NO " +
				" left join TM_VEHICLE_dec TV on TWC.VIN=TV.VIN " +
				" left join TM_VHCL_MATERIAL TVM on TV.MATERIAL_ID=TVM.MATERIAL_ID " +
				" left join TM_DEALER TD on TWC.DEALER_ID = TD.DEALER_ID " +
				" left join TT_WR_CLAIMTYPE_dcs TWCT on TWCT.CLAIM_TYPE_CODE=TWC.CLAIM_TYPE \n" );
				
		sqlStr.append(" WHERE  TWC.IS_DEL="+OemDictCodeConstants.IS_DEL_00+"      \n");
		if(!StringUtils.isNullOrEmpty(queryParams.get("CLAIM_NO"))){
			sqlStr.append("AND TWC.CLAIM_NO LIKE ? \n");
			params.add("%"+queryParams.get("CLAIM_NO")+"%");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("REPAIR_NO"))){
			sqlStr.append("AND TWC.RO_NO LIKE ? \n");
			params.add("%"+queryParams.get("REPAIR_NO")+"%");
		}
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("CLAIM_START_DATE"))){ 
		     
			sqlStr.append(" AND  DATE_FORMAT(TWC.APPLY_DATE,'%Y-%m-%d') >='" + queryParams.get("CLAIM_START_DATE") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("CLAIM_END_DATE"))){ 
            
        	sqlStr.append(" AND  DATE_FORMAT(TWC.APPLY_DATE,'%Y-%m-%d') <='" + queryParams.get("CLAIM_END_DATE") +"' \n");
        }
		
		if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){
			sqlStr.append("AND TD.DEALER_CODE IN(?) \n");
			params.add(queryParams.get("dealerCode"));
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("STATUS"))){
			sqlStr.append("AND TWC.STATUS= ? \n");
			params.add(queryParams.get("STATUS"));
		}
		sqlStr.append("AND TWC.STATUS <> "+OemDictCodeConstants.CLAIM_STATUS_01+"\n");
		if(!StringUtils.isNullOrEmpty(queryParams.get("CLAIM_TYPE"))){
			sqlStr.append("AND TWC.CLAIM_TYPE = ? \n");
			params.add(queryParams.get("CLAIM_TYPE"));
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("CLAIM_TYPE"))){
			sqlStr.append("AND TWC.CLAIM_TYPE = ? \n");
			params.add(queryParams.get("CLAIM_TYPE"));
		}
		String vin = queryParams.get("vin");
		if (!StringUtils.isNullOrEmpty(vin)) {
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sqlStr.append(getVinsAuto(vin, "TV"));
		}
		if(!logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString()) && !logonUser.getDutyType().toString().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())){
			sqlStr.append("      AND  TWC.DEALER_ID in logonUser("+getDealersByArea(logonUser.getOrgId().toString())+")\n");
		}
		if(!StringUtils.isNullOrEmpty(queryParams.get("PASS_START_DATE"))){ 
		     
			sqlStr.append(" AND  DATE_FORMAT(TWC.PASS_DATE,'%Y-%m-%d') >='" + queryParams.get("PASS_START_DATE") +"' \n");
        }
        if(!StringUtils.isNullOrEmpty(queryParams.get("PASS_END_DATE"))){ 
            
        	sqlStr.append(" AND  DATE_FORMAT(TWC.PASS_DATE,'%Y-%m-%d') <='" + queryParams.get("PASS_END_DATE") +"' \n");
        }
		return sqlStr.toString();
	}

	/**
	 * 索赔单状态跟踪--下载查询操作
	 * @param queryParams
	 * @return
	 */
	public List<Map> getClaimOrderInfoList(Map<String, String> queryParams) {
		List<Object> params = new ArrayList<Object>();
		String sql = getRepairClaimListSql(queryParams,params);
		return OemDAOUtil.findAll(sql, params);
	}

	/**
	 * 查询索赔单的索赔情形
	 * @param claimId
	 * @return
	 */
	public PageInfoDto clainCaseList(Long claimId) {
		StringBuffer sqlStr= new StringBuffer();
		sqlStr.append("SELECT O.CASE_CODE,O.ID,\n" );
		sqlStr.append("       O.FAULT_CODE,\n" );
		sqlStr.append("       O.FAULT_NAME,\n" );
		sqlStr.append("       O.FAULT_CAUSE,\n" );
		sqlStr.append("       O.APPLY_REMARK,\n" );
		sqlStr.append("       O.REPAIR_MEASURE,\n" );
		sqlStr.append("       O.TECHPERSON_NUM,\n" );
		sqlStr.append("       O.TECHPERSON\n" );
		sqlStr.append("  FROM TT_WR_CLAIM_CASE_dcs O\n" );
		sqlStr.append(" WHERE 1 = 1\n");
		if (Utility.testString(claimId)) {     //参数ID不为空
			sqlStr.append("		AND O.CLAIM_ID = "+claimId+" \n");
		}
		return OemDAOUtil.pageQuery(sqlStr.toString(), null);
	}

	/**
	 * 进入保修申请审核页面初始化
	 * @param oemCompanyId
	 * @param claimNo
	 * @param dealerCode
	 * @return
	 */
	public Map claimMap(Long oemCompanyId, String claimNo, String dealerCode) {
		String sql = getclaimMapSql(oemCompanyId,claimNo,dealerCode);
		return OemDAOUtil.findFirst(sql, null);
	}

	/**
	 * 进入保修申请审核页面初始化SQL
	 * @param oemCompanyId
	 * @param claimNo
	 * @param dealerCode
	 * @return
	 */
	private String getclaimMapSql(Long oemCompanyId, String claimNo, String dealerCode) {
		StringBuffer sqlStr= new StringBuffer();
		//sqlStr.append("SELECT C.NWSCLAIM_NO,--NWS单号\n" );
		sqlStr.append("SELECT C.RO_NO,\n" );
		sqlStr.append("A.ACTIVITY_CODE,\n" );
		sqlStr.append("A.ACTIVITY_NAME,\n" );
		sqlStr.append("R.REPAIR_ID,\n" );
		sqlStr.append(" C.CLAIM_ID,\n" );
		sqlStr.append(" C.PLATE_NO,\n" );
		sqlStr.append(" C.WARRANTY_DATE,\n" );
		sqlStr.append(" C.BRAND,\n" );
		sqlStr.append(" C.SERIES,\n" );
		sqlStr.append(" C.MODEL,\n" );//
		sqlStr.append(" C.STATUS,\n" );
		sqlStr.append(" C.VIN,\n" );
		sqlStr.append(" C.RO_STARTDATE,\n" );
		sqlStr.append(" C.RO_ENDDATE,\n" );//DEDUCT_REMARK
		sqlStr.append(" C.DEDUCT_REMARK,\n" );
		sqlStr.append(" C.MILLEAGE,\n" );
		sqlStr.append(" C.SUBMIT_COUNT,\n" );
		sqlStr.append(" C.LABOUR_PICE,\n" );
		sqlStr.append("C.CLAIM_NO,\n" );
		sqlStr.append("TWCT.CLAIM_TYPE,\n" );
		sqlStr.append("TWCT.CLAIM_TYPE_CODE,\n" );
		sqlStr.append("REPLACE(C.REMARK,'<br/>','\r\n') REMARK,\n" );
		sqlStr.append("C.LABOUR_FEE,\n" );
		sqlStr.append("C.PART_FEE,\n" );
		sqlStr.append("C.OTHER_AMOUNT,\n" );
		sqlStr.append("C.ALL_AMOUNT,\n" );
		sqlStr.append(" DATE_FORMAT(C.APPLY_DATE,'%Y-%m-%d') AS APPLY_DATE_CHAR,\n" );
		sqlStr.append("C.APPLY_DATE,\n" );
		sqlStr.append("D.DEALER_CODE,\n");
		sqlStr.append("D.DEALER_SHORTNAME,\n");
		sqlStr.append("U.ACNT,\n");
		sqlStr.append("U.NAME,\n");
		sqlStr.append("C.CLAIM_ID,\n" );
		sqlStr.append("C.DEDUCT_FEE,\n" );
		sqlStr.append("C.REMARK\n" );
		sqlStr.append("FROM TT_WR_CLAIM_dcs C LEFT JOIN TM_DEALER D ON (C.DEALER_ID = D.DEALER_ID)\n");
		sqlStr.append("                   LEFT JOIN TC_USER U ON (C.CREATE_BY=U.USER_ID)\n");
		sqlStr.append("                   LEFT JOIN TT_WR_REPAIR_dcs R ON (C.RO_NO = R.REPAIR_NO)\n");
		sqlStr.append("                   LEFT JOIN TT_WR_ACTIVITY_dcs A ON (C.ACTIVITY_CODE = A.ACTIVITY_CODE) \n");
		sqlStr.append("                   left join TT_WR_CLAIMTYPE_dcs TWCT on TWCT.CLAIM_TYPE_CODE=C.CLAIM_TYPE \n");
		sqlStr.append(" WHERE C.IS_DEL=0 \n");
		if(!"".equals(oemCompanyId)&&!(null==oemCompanyId)){                 //公司ID不为空
			sqlStr.append("		AND C.OEM_COMPANY_ID = "+oemCompanyId+" \n");
		}
		
		sqlStr.append("		AND C.CLAIM_NO = '"+claimNo+"' \n");
	   if(!StringUtils.isNullOrEmpty(dealerCode)){      //参数ID不为空
			sqlStr.append("		AND D.DEALER_CODE = '"+dealerCode+"' \n");
		}
	   return sqlStr.toString();
	}

	
	/**
	 * 
	 * @param oemCompanyId
	 * @param queryParams
	 * @return
	 */
	public PageInfoDto claimList(Long oemCompanyId, Map<String, String> queryParams) {
		StringBuffer sqlStr= new StringBuffer();
		//sqlStr.append("SELECT C.NWSCLAIM_NO,--NWS单号\n" );
		sqlStr.append("SELECT C.RO_NO,\n" );
		sqlStr.append("A.ACTIVITY_CODE,\n" );
		sqlStr.append("A.ACTIVITY_NAME,\n" );
		sqlStr.append("R.REPAIR_ID,\n" );
		sqlStr.append(" C.CLAIM_ID,\n" );
		sqlStr.append(" C.PLATE_NO,\n" );
		sqlStr.append(" C.WARRANTY_DATE,\n" );
		sqlStr.append(" C.BRAND,\n" );
		sqlStr.append(" C.SERIES,\n" );
		sqlStr.append(" C.MODEL,\n" );//
		sqlStr.append(" C.STATUS,\n" );
		sqlStr.append(" C.VIN,\n" );
		sqlStr.append(" C.RO_STARTDATE,\n" );
		sqlStr.append(" C.RO_ENDDATE,\n" );//DEDUCT_REMARK
		sqlStr.append(" C.DEDUCT_REMARK,\n" );
		sqlStr.append(" C.MILLEAGE,\n" );
		sqlStr.append(" C.SUBMIT_COUNT,\n" );
		sqlStr.append(" C.LABOUR_PICE,\n" );
		sqlStr.append("C.CLAIM_NO,\n" );
		sqlStr.append("TWCT.CLAIM_TYPE,\n" );
		sqlStr.append("TWCT.CLAIM_TYPE_CODE,\n" );
		sqlStr.append("REPLACE(C.REMARK,'<br/>','\r\n') REMARK,\n" );
		sqlStr.append("C.LABOUR_FEE,\n" );
		sqlStr.append("C.PART_FEE,\n" );
		sqlStr.append("C.OTHER_AMOUNT,\n" );
		sqlStr.append("C.ALL_AMOUNT,\n" );
		sqlStr.append(" DATE_FORMAT(C.APPLY_DATE,'%Y-%m-%d') AS APPLY_DATE_CHAR,\n" );
		sqlStr.append("C.APPLY_DATE,\n" );
		sqlStr.append("D.DEALER_CODE,\n");
		sqlStr.append("D.DEALER_SHORTNAME,\n");
		sqlStr.append("U.ACNT,\n");
		sqlStr.append("U.NAME,\n");
		sqlStr.append("C.CLAIM_ID,\n" );
		sqlStr.append("C.DEDUCT_FEE,\n" );
		sqlStr.append("C.REMARK\n" );
		sqlStr.append("FROM TT_WR_CLAIM_dcs C LEFT JOIN TM_DEALER D ON (C.DEALER_ID = D.DEALER_ID)\n");
		sqlStr.append("                   LEFT JOIN TC_USER U ON (C.CREATE_BY=U.USER_ID)\n");
		sqlStr.append("                   LEFT JOIN TT_WR_REPAIR_dcs R ON (C.RO_NO = R.REPAIR_NO)\n");
		sqlStr.append("                   LEFT JOIN TT_WR_ACTIVITY_dcs A ON (C.ACTIVITY_CODE = A.ACTIVITY_CODE) \n");
		sqlStr.append("                   left join TT_WR_CLAIMTYPE_dcs TWCT on TWCT.CLAIM_TYPE_CODE=C.CLAIM_TYPE \n");
		sqlStr.append(" WHERE C.IS_DEL=0 \n");
		if(!"".equals(oemCompanyId)&&!(null==oemCompanyId)){                 //公司ID不为空
			sqlStr.append("		AND C.OEM_COMPANY_ID = "+oemCompanyId+" \n");
		}
		
		sqlStr.append("		AND C.CLAIM_NO = '"+queryParams.get("CLAIM_NO")+"' \n");
	   if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){      //参数ID不为空
			sqlStr.append("		AND D.DEALER_CODE = '"+queryParams.get("dealerCode")+"' \n");
		}
	return OemDAOUtil.pageQuery(sqlStr.toString(), null);
	}

	public Map claimMap(Long oemCompanyId, Map<String, String> queryParams) {
		StringBuffer sqlStr= new StringBuffer();
		//sqlStr.append("SELECT C.NWSCLAIM_NO,--NWS单号\n" );
		sqlStr.append("SELECT C.RO_NO,\n" );
		sqlStr.append("A.ACTIVITY_CODE,\n" );
		sqlStr.append("A.ACTIVITY_NAME,\n" );
		sqlStr.append("R.REPAIR_ID,\n" );
		sqlStr.append(" C.CLAIM_ID,\n" );
		sqlStr.append(" C.PLATE_NO,\n" );
		sqlStr.append(" C.WARRANTY_DATE,\n" );
		sqlStr.append(" C.BRAND,\n" );
		sqlStr.append(" C.SERIES,\n" );
		sqlStr.append(" C.MODEL,\n" );//
		sqlStr.append(" C.STATUS,\n" );
		sqlStr.append(" C.VIN,\n" );
		sqlStr.append(" C.RO_STARTDATE,\n" );
		sqlStr.append(" C.RO_ENDDATE,\n" );//DEDUCT_REMARK
		sqlStr.append(" C.DEDUCT_REMARK,\n" );
		sqlStr.append(" C.MILLEAGE,\n" );
		sqlStr.append(" C.SUBMIT_COUNT,\n" );
		sqlStr.append(" C.LABOUR_PICE,\n" );
		sqlStr.append("C.CLAIM_NO,\n" );
		sqlStr.append("TWCT.CLAIM_TYPE,\n" );
		sqlStr.append("TWCT.CLAIM_TYPE_CODE,\n" );
		sqlStr.append("REPLACE(C.REMARK,'<br/>','\r\n') REMARK,\n" );
		sqlStr.append("C.LABOUR_FEE,\n" );
		sqlStr.append("C.PART_FEE,\n" );
		sqlStr.append("C.OTHER_AMOUNT,\n" );
		sqlStr.append("C.ALL_AMOUNT,\n" );
		sqlStr.append(" DATE_FORMAT(C.APPLY_DATE,'%Y-%m-%d') AS APPLY_DATE_CHAR,\n" );
		sqlStr.append("C.APPLY_DATE,\n" );
		sqlStr.append("D.DEALER_CODE,\n");
		sqlStr.append("D.DEALER_SHORTNAME,\n");
		sqlStr.append("U.ACNT,\n");
		sqlStr.append("U.NAME,\n");
		sqlStr.append("C.CLAIM_ID,\n" );
		sqlStr.append("C.DEDUCT_FEE,\n" );
		sqlStr.append("C.REMARK\n" );
		sqlStr.append("FROM TT_WR_CLAIM_dcs C LEFT JOIN TM_DEALER D ON (C.DEALER_ID = D.DEALER_ID)\n");
		sqlStr.append("                   LEFT JOIN TC_USER U ON (C.CREATE_BY=U.USER_ID)\n");
		sqlStr.append("                   LEFT JOIN TT_WR_REPAIR_dcs R ON (C.RO_NO = R.REPAIR_NO)\n");
		sqlStr.append("                   LEFT JOIN TT_WR_ACTIVITY_dcs A ON (C.ACTIVITY_CODE = A.ACTIVITY_CODE) \n");
		sqlStr.append("                   left join TT_WR_CLAIMTYPE_dcs TWCT on TWCT.CLAIM_TYPE_CODE=C.CLAIM_TYPE \n");
		sqlStr.append(" WHERE C.IS_DEL=0 \n");
		if(!"".equals(oemCompanyId)&&!(null==oemCompanyId)){                 //公司ID不为空
			sqlStr.append("		AND C.OEM_COMPANY_ID = "+oemCompanyId+" \n");
		}
		sqlStr.append("		AND C.CLAIM_NO = '"+queryParams.get("CLAIM_NO")+"' \n");
	   if(!StringUtils.isNullOrEmpty(queryParams.get("dealerCode"))){      //参数ID不为空
			sqlStr.append("		AND D.DEALER_CODE = '"+queryParams.get("dealerCode")+"' \n");
		}
	   return OemDAOUtil.findFirst(sqlStr.toString(), null);
	}

}
