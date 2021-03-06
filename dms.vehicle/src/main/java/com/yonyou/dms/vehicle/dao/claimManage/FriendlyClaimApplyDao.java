package com.yonyou.dms.vehicle.dao.claimManage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
* @author liujm
* @date 2017年4月25日
*/


@Repository
public class FriendlyClaimApplyDao extends OemBaseDAO{
	
	
	/**
	 * 召回车辆查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getFriendlyClaimApplyQuery(Map<String, String> queryParam) {
		List<Object> params = new ArrayList<Object>();
		String sql = getQuerySql(queryParam, params);
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql, params);
		return pageInfoDto;

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
		sql.append("SELECT TWC.SMALL_AREA_APPROVAL_STATUS, TWC.CLAIM_ID,TWC.CLAIM_NO,TWC.CLAIM_TYPE CLAIM_TYPE_CODE, \n");
		sql.append("		TWCT.CLAIM_TYPE,TWC.RO_NO,TWC.SUBMIT_COUNT,TWC.VIN,DATE_FORMAT(TWC.APPLY_DATE,'%Y-%c-%d') APPLY_DATE,TWC.STATUS,TWC.MODEL,TWC.MILLEAGE, \n");
		sql.append("		TWC.Warranty_DATE,TWC.FOREAPPROVAL_NO,TWC.SERIES \n");
		sql.append("	FROM TT_WR_CLAIM_DCS TWC LEFT JOIN TT_WR_CLAIMTYPE_DCS TWCT ON TWCT.CLAIM_TYPE_CODE=TWC.CLAIM_TYPE  \n");
		sql.append("	WHERE  TWC.IS_DEL=0  \n");
		sql.append("			AND TWC.CLAIM_TYPE = 'G' AND TWC.IS_DEL=0 \n");
		//条件
		//索赔单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimNo"))){
			sql.append("			AND TWC.CLAIM_NO LIKE'%"+queryParam.get("claimNo")+"%' \n");
		}
		//工单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))){
			sql.append("			AND TWC.RO_NO LIKE'%"+queryParam.get("repairNo")+"%' \n");
		}
		//索赔状态
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStartDate"))){
			sql.append("			AND TWC.APPLY_DATE >= DATE_FORMAT('"+queryParam.get("claimStartDate")+"','%Y-%c-%d')  \n");
		}
		//索赔开始时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimEndDate"))){
			sql.append("			AND TWC.APPLY_DATE <= DATE_FORMAT('"+queryParam.get("claimEndDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//索赔结束时间
		if(!StringUtils.isNullOrEmpty(queryParam.get("claimStatus"))){
			sql.append("			AND  TWC.STATUS ='"+queryParam.get("claimStatus")+"' \n");
		}
		//经销商CODE、公司ID				
		sql.append("	AND TWC.OEM_COMPANY_ID="+loginInfo.getOemCompanyId()+" AND TWC.DEALER_ID="+loginInfo.getDealerId()+" \n");
		sql.append("	ORDER BY TWC.CLAIM_ID DESC \n");

		return sql.toString(); 	
	}
	
	
	
	/**
	 * 工单明细 零部件清单
	 * @param repairNo
	 * @return
	 */
	public PageInfoDto getRepairQueryPartMsgDetail(String repairNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" 	SELECT TWRP.DETAIL_ID,TWRP.IS_MAIN,TWRP.PART_CODE, \n");
		sql.append("		TWRP.PART_NAME,COALESCE(TWRP.PRICE,0) PRICE, \n");
		sql.append("		TWRP.REPAIR_METHOD,TWRP.REMARK,COALESCE(TWRP.QUANTITY,0) QUANTITY,0 AS PACKAGE_QUANTITY, TWRP.ACTIVITY_CODE \n");
		sql.append("	FROM TT_WR_REPAIR_PART_DCS TWRP \n");
		sql.append("	LEFT JOIN TT_WR_REPAIR_DCS twr ON  TWRP.REPAIR_ID=twr.REPAIR_ID WHERE twr.REPAIR_NO = '"+repairNo+"' \n");
		sql.append("	  \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 工单明细 维修工时清单
	 * @param repairNo
	 * @return
	 */
	public PageInfoDto getRepairQueryItemMsgDetail(String repairNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("  SELECT TWRI.ITEM_ID, \n");
		sql.append("		IFNULL(TWRI.LABOUR_CODE,'') LON,TWRI.LABOUR_NAME,COALESCE(TWRI.LABOUR_NUM,0) FRT, TWRI.ACTIVITY_CODE \n");
		sql.append("	FROM TT_WR_REPAIR_ITEM_DCS TWRI \n");
		sql.append("	LEFT JOIN TT_WR_REPAIR_DCS twr ON  TWRI.REPAIR_ID=twr.REPAIR_ID WHERE twr.REPAIR_NO = '"+repairNo+"' \n");
		sql.append("	  \n");
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 工单明细 其他项目清单
	 * @param repairNo
	 * @return
	 */
	public PageInfoDto getRepairQueryOtherItemMsgDetail(String repairNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" 	SELECT TWRO.DETAIL_ID,TWRO.REPAIR_ID,TWRO.ITEM_CODE,TWRO.ITEM_NAME, \n");
		sql.append("		COALESCE(TWRO.ITEM_FEE,0) ITEM_FEE,TWRO.REMARK ITEM_REMARK, TWRO.ACTIVITY_CODE \n");
		sql.append("	FROM TT_WR_REPAIR_OTHERITEM_DCS TWRO \n");
		sql.append("	LEFT JOIN TT_WR_REPAIR_DCS twr ON  TWRO.REPAIR_ID=twr.REPAIR_ID WHERE twr.REPAIR_NO =  '"+repairNo+"' \n");
		sql.append("	  \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 工单明细 车辆信息
	 * @param vin
	 * @return
	 */
	public Map getRepairQueryVehicleMsgDetail(String vin) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT TV.VEHICLE_ID,TV.VIN,COALESCE(TV.MILEAGE,0) MILLEAGE, \n");
		sql.append("		TT.MATERIAL_ID,TT.GROUP_ID,COALESCE(TT.GROUP_NAME,'') MATERIAL_CODE, \n");
		sql.append("		TT.MODEL_CODE, \n");
		sql.append("		TV.MODEL_YEAR, \n");
		sql.append("		TT.COLOR_CODE,TT.COLOR_NAME, \n");
		sql.append("		TV.ENGINE_NO, \n");
		sql.append("		TV.LICENSE_NO,TV.MILEAGE, \n");
		sql.append("		DATE_FORMAT(TV.PURCHASE_DATE,'%Y-%c-%d') PURCHASE_DATE \n");
		sql.append("	FROM TM_VEHICLE_DEC TV LEFT JOIN \n");
		sql.append("			("+getVwMaterialSql()+") TT \n");
		sql.append("		ON TV.MATERIAL_ID=TT.MATERIAL_ID WHERE VIN='"+vin+"' \n");

		Map map = OemDAOUtil.findFirst(sql.toString(), params);		
		return map;

	}
	/**
	 * 工单明细 基本信息
	 * @param repairNo
	 * @return
	 */
	public Map getRepairQueryMsgDetail(String repairNo) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("	SELECT AAA.*,TU.NAME CREATOR_NAME FROM (SELECT TWR.CREATE_BY,TWR.REPAIR_ID,TWR.REPAIR_NO,TWR.CLAIM_NUMBER,TWR.OEM_COMPANY_ID,TWR.DEALER_ID,TD.DEALER_CODE,TD.DEALER_SHORTNAME DEALER_NAME,TWR.REPAIR_TYPE,TV.VIN,TWR.PACKAGE_CODE,TWR.PACKAGE_NAME, \n");
		sql.append("		TC.CODE_DESC REPAIR_DESC,DATE_FORMAT(TWR.REPAIR_DATE,'%Y-%c-%d') REPAIR_DATE,TWR.LAB_PAY,TWR.PARA_FLAG, \n");
		sql.append("		TWR.MILLEAGE,TWR.REPORT_MAN,DATE_FORMAT(TWR.MAKE_DATE,'%Y-%c-%d') MAKE_DATE, \n");
		sql.append("		TWR.EMAIL,TWR.TEL,DATE_FORMAT(TWR.FAULT_DATE,'%Y-%c-%d') FAULT_DATE,TWR.BETWEEN_DAYS, \n");
		sql.append("		TWR.ORDER_TYPE,TWR.CUSTOMER_NAME,TWR.CUSTOMER_TEL,TWR.CUSTOMER_ADDR,TWR.RO_TROUBLE_DESC,TWR.CUSTOMER_DESC,REPLACE(TWR.REMARK,'<br/>',' \n");
		sql.append("		') REMARK,DATE_FORMAT(TWR.BALANCE_DATE,'%Y-%c-%d') BALANCE_DATE,DATE_FORMAT(TWR.FINISH_DATE,'%Y-%c-%d') FINISH_DATE, \n");
		sql.append("		COALESCE(TWR.PART_FEE,0) PART_FEE,COALESCE(TWR.LABOR_FEE,0) LABOR_FEE,COALESCE(TWR.OTHER_FEE,0) OTHER_FEE,	 \n");
		sql.append("		COALESCE(TWR.PART_FEE,0)+COALESCE(TWR.LABOR_FEE,0)+COALESCE(TWR.OTHER_FEE,0) TOTAL_FEE,TWR.STATUS,TWR.CLAIM_STATUS,TWR.DOWN_STATUS,TWR.MAIN_PART \n");
		sql.append("	FROM TT_WR_REPAIR_DCS TWR,TC_CODE_DCS TC,TM_VEHICLE_DEC TV,TM_DEALER TD \n");
		sql.append("	WHERE TWR.REPAIR_TYPE=TC.CODE_ID  AND TWR.DEALER_ID=TD.DEALER_ID \n");
		sql.append("		AND TWR.VIN=TV.VIN \n");
		sql.append("		AND TWR.REPAIR_NO='"+repairNo+"' \n");
		sql.append("	) AAA LEFT JOIN  TC_USER TU ON AAA.CREATE_BY = TU.USER_ID \n");

		Map map = OemDAOUtil.findFirst(sql.toString(), params);		
		return map;

	}
	
	
	/**
	 * 索赔明细  申请信息
	 * @param vin
	 * @return
	 */
	public Map getClaimQueryDetail(Long  claimId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT C.RO_NO,	  #维修工单号 \n");
		sql.append("	C.CUSTOMER_COMPLAIN, C.CHECK_REPAIR_PROCEDURES, C.DEAL_SCHEME, C.APPLY_REASONS, \n");
		sql.append("	C.CUSTOMER_BEAR_FEE_PART, C.DEALER_BEAR_FEE_PART,  C.OEM_BEAR_FEE_PART, \n");
		sql.append("	C.CUSTOMER_BEAR_FEE_LABOR,C.DEALER_BEAR_FEE_LABOR, C.OEM_BEAR_FEE_LABOR, \n");
		sql.append("	C.CUSTOMER_BEAR_FEE_OTHER,C.DEALER_BEAR_FEE_OTHER, C.OEM_BEAR_FEE_OTHER, \n");
		sql.append("	C.SELECT_CATEGORY, C.SELECT_MODE,  A.ACTIVITY_CODE,	  #服务活动代码 \n");
		sql.append("	A.ACTIVITY_NAME,	  #服务活动名称 \n");
		sql.append("	R.REPAIR_ID,	  #维修工单ID \n");
		sql.append("	C.PLATE_NO,	  #车牌号 \n");
		sql.append("	C.WARRANTY_DATE,	  #保修开始日期 \n");
		sql.append("	C.BRAND,	  #品牌 \n");
		sql.append("	C.SERIES,	  #车系 \n");
		sql.append("	C.MODEL,	  #车型 \n");
		sql.append("	C.VIN,	  #VIN \n");
		sql.append("	C.RO_STARTDATE,	  #工单开始日期 \n");
		sql.append("	C.RO_ENDDATE,	  #工单结束日期 \n");
		sql.append("	C.MILLEAGE,	  #行驶里程 \n");
		sql.append("	C.SUBMIT_COUNT,	  #提报次数 \n");
		sql.append("	C.LABOUR_PICE,	  #工时单价 \n");
		sql.append("	C.DEDUCT_FEE,	  #抵扣金额 \n");
		sql.append("	C.DEDUCT_REMARK,	  #抵扣意见 \n");
		sql.append("	C.CLAIM_NO,	  #索赔单号 \n");
		sql.append("	TWCT.CLAIM_TYPE,	  #索赔类型 \n");
		sql.append("	TWCT.CLAIM_TYPE_CODE,	  #索赔类型code \n");
		sql.append("	REPLACE(C.REMARK,'<br/>','') REMARK,	  #备注 \n");
		sql.append("	C.LABOUR_FEE,	  #工时金额 \n");
		sql.append("	C.PART_FEE,	  #配件金额 \n");
		sql.append("	C.OTHER_AMOUNT,	  #其他费用金额 \n");
		sql.append("	C.ALL_AMOUNT,	  #总金额 \n");
		sql.append("	C.TAX_RATE,	  #税率 \n");
		sql.append("	C.PART_PAY,	  #管理税率 \n");
		sql.append("	DATE_FORMAT(C.APPLY_DATE,'%Y-%c-%d') AS APPLY_DATE_CHAR, #索赔申请日期 \n");
		sql.append("	C.APPLY_DATE,	  #索赔申请日期 \n");
		sql.append("	D.DEALER_CODE,	  #特约店代码 \n");
		sql.append("	D.DEALER_SHORTNAME, #特约店简称 \n");
		sql.append("	U.ACNT,	  #用户acnt \n");
		sql.append("	U.NAME,	  #申请人 \n");
		sql.append("	C.CLAIM_ID \n");
		sql.append("FROM TT_WR_CLAIM_DCS C LEFT JOIN TM_DEALER D ON (C.DEALER_ID = D.DEALER_ID) \n");
		sql.append("                   LEFT JOIN TC_USER U ON (C.CREATE_BY=U.USER_ID) \n");
		sql.append("                   LEFT JOIN TT_WR_REPAIR_DCS R ON (C.RO_NO = R.REPAIR_NO) \n");
		sql.append("                   LEFT JOIN TT_WR_ACTIVITY_DCS A ON (C.ACTIVITY_CODE = A.ACTIVITY_CODE) \n");
		sql.append("                   LEFT JOIN TT_WR_CLAIMTYPE_DCS TWCT ON TWCT.CLAIM_TYPE_CODE=C.CLAIM_TYPE \n");
		sql.append("WHERE 1=1 \n");		
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		//经销商OEM_COMPANY_ID       公司COMPANY_ID
		if(loginInfo.getPoseType()!=null && loginInfo.getPoseType() == 10021002){
			sql.append("		AND C.OEM_COMPANY_ID = "+loginInfo.getOemCompanyId()+" \n");
		}else{
			sql.append("		AND C.OEM_COMPANY_ID = "+loginInfo.getCompanyId()+" \n");
		}		
		//sql.append("		AND C.OEM_COMPANY_ID = 2010010100070674 \n");
		sql.append("		AND C.CLAIM_ID = "+claimId+" \n");

		Map map = OemDAOUtil.findFirst(sql.toString(), params);		
		return map;
	}
	/**
	 * 索赔明细  情形
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getClaimCaseQueryDetail(Long  claimId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT O.CASE_CODE,O.ID, \n");
		sql.append("       O.FAULT_CODE, \n");
		sql.append("       O.FAULT_NAME, \n");
		sql.append("       O.FAULT_CAUSE, \n");
		sql.append("       O.APPLY_REMARK, \n");
		sql.append("       O.REPAIR_MEASURE, \n");
		sql.append("       O.TECHPERSON_NUM, \n");
		sql.append("       O.TECHPERSON \n");
		sql.append("  FROM TT_WR_CLAIM_CASE_DCS O \n");
		sql.append(" WHERE 1 = 1 \n");
		sql.append("		AND O.CLAIM_ID = "+claimId+" \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 索赔明细  工时
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getClaimLabourQueryDetail(Long  claimId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT O.LABOUR_CODE, \n");
		sql.append("       O.IS_MAIN, \n");
		sql.append("       O.LABOUR_NAME, \n");
		sql.append("       O.LABOUR_NUM, \n");
		sql.append("       O.PRICE, \n");
		sql.append("       O.FEE , \n");
		sql.append(" o.ACTIVITY_CODE \n");
		sql.append("  FROM TT_WR_CLAIMLABOUR_REL_DCS O \n");
		sql.append(" WHERE 1 = 1 \n");
		sql.append("		AND O.CLAIM_ID = "+claimId+" \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 索赔明细 零部件
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getClaimPartQueryDetail(Long  claimId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT P.IS_MAIN, \n");
		sql.append("       P.PART_CODE, \n");
		sql.append("       P.PART_NAME, \n");
		sql.append("       P.QUANTITY, \n");
		sql.append("       p.AMOUNT,p.PRICE, \n");
		sql.append(" p.ACTIVITY_CODE \n");
		sql.append("  FROM TT_WR_CLAIMPART_REL_DCS P \n");
		sql.append("		WHERE P.CLAIM_ID = "+claimId+" \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 索赔明细 其他项目
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getClaimOtherQueryDetail(Long  claimId) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT O.OTHER_REL_ID, \n");
		sql.append("       O.OTHER_FEE_CODE, \n");
		sql.append("       O.OTHER_FEE_NAME, \n");
		sql.append("       O.AMOUNT, \n");
		sql.append("       O.REMARK, \n");
		sql.append("       O.ACTIVITY_CODE \n");
		sql.append("  FROM TT_WR_CLAIMOTHER_REL_DCS O \n");
		sql.append(" WHERE 1 = 1 \n");
		sql.append("		AND O.CLAIM_ID = "+claimId+"	 \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 索赔明细 审核历史
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
		sql.append("		AND (TWCAS.APPROVAL_STATUS = 90011002 OR TWCAS.APPROVAL_STATUS = 90011003) \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	
	public PageInfoDto getQueryRepairOrderList(Map<String, String> queryParam) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWR.REPAIR_ID, TWR.REPAIR_NO, TWR.CLAIM_NUMBER, TV.VIN, TV.ENGINE_NO,TV.LICENSE_NO, DATE_FORMAT(TV.PURCHASE_DATE,'%Y-%c-%d') PURCHASE_DATE, \n");
		sql.append("		TWR.REPAIR_TYPE, TWR.STATUS, TWR.MAIN_PART,TWR.BETWEEN_DAYS,TWR.PACKAGE_CODE,TWR.PACKAGE_NAME, \n");
		sql.append("		TWR.CUSTOMER_NAME,TWR.CUSTOMER_TEL,TWR.CUSTOMER_ADDR,TWR.TEL,TWR.REPORT_MAN,TWR.MILLEAGE, \n");
		sql.append("		TWR.DEALER_CODE,  TWR.DEALER_NAME, \n");
		sql.append("		COALESCE (TWR.MAINTAIN_HOUR, 0) MAINTAIN_HOUR,COALESCE (TWR.PART_FEE, 0) PART_FEE, \n");
		sql.append("		COALESCE (TWR.LABOR_FEE, 0) LABOR_FEE,COALESCE (TWR.OTHER_FEE, 0) OTHER_FEE,COALESCE (TWR.LAB_PAY, 0) LAB_PAY, \n");
		sql.append("		DATE_FORMAT (TWR.MAKE_DATE, '%Y-%c-%d') MAKE_DATE,MAKE_MAN, \n");
		sql.append("		DATE_FORMAT (TWR.REPAIR_DATE, '%Y-%c-%d') REPAIR_DATE, \n");
		sql.append("		DATE_FORMAT (TWR.BALANCE_DATE, '%Y-%c-%d') BALANCE_DATE, \n");
		//sql.append("		DATE_FORMAT (TWR.BALANCE_DATE, '%Y-%c-%d') BALANCE_DATE, \n");
		sql.append("		DATE_FORMAT (TWR.FAULT_DATE, '%Y-%c-%d') FAULT_DATE, \n");
		sql.append("		TVM.BRAND_CODE,TVM.BRAND_NAME,TVM.SERIES_CODE,TVM.SERIES_NAME,TVM.MODEL_CODE,TVM.MODEL_NAME,TV.MODEL_YEAR \n");
		sql.append(" FROM TT_WR_REPAIR_DCS TWR, TM_VEHICLE_DEC TV \n");
		sql.append("	left join  ("+getVwMaterialSql()+") TVM  on TV.MATERIAL_ID = TVM.MATERIAL_ID \n");
		sql.append(" WHERE  TWR.VIN = TV.VIN  \n");
		sql.append("        AND TWR.IS_DEL = 0 \n");
		sql.append("        AND TWR.IS_CLAIM = 10041001 \n");
		sql.append("        AND TWR.STATUS = 40021002 			 \n");
		//	#条件 
		//工单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))){
			sql.append("		AND TWR.REPAIR_NO like'%"+queryParam.get("repairNo")+"%' \n");
		}
		//工单日期开始
		if(!StringUtils.isNullOrEmpty(queryParam.get("roStartDate"))){
			sql.append("		AND TWR.REPAIR_DATE >= DATE_FORMAT('"+queryParam.get("roStartDate")+"','%Y-%c-%d')  \n");
		}
		//工单日期结束
		if(!StringUtils.isNullOrEmpty(queryParam.get("roEndtDate"))){
			sql.append("		AND TWR.BALANCE_DATE <= DATE_FORMAT('"+queryParam.get("roEndtDate")+" 23:59:59','%Y-%c-%d %H:%i:%s')  \n");
		}
		//购车日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("purchase"))){
			sql.append("		AND TV.PURCHASE_DATE = DATE_FORMAT('"+queryParam.get("purchase")+"','%Y-%c-%d')  \n");
		}
		//vin
		if(!StringUtils.isNullOrEmpty(queryParam.get("VIN"))){
			sql.append("		AND TWR.VIN like'%"+queryParam.get("VIN")+"%' \n");
		}
		//里程
		if(!StringUtils.isNullOrEmpty(queryParam.get("inMilleage"))){
			sql.append("		AND  TWR.MILLEAGE ='"+queryParam.get("inMilleage")+"' \n");
		}
		
		sql.append("		AND TWR.OEM_COMPANY_ID = "+loginInfo.getOemCompanyId()+" \n");
		sql.append("        AND TWR.DEALER_ID = "+loginInfo.getDealerId()+" \n");

		sql.append(" AND  TWR.CLAIM_NUMBER ='5AS0J2'  ");  //限制减少数据
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 查询预授权单信息
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto getQueryforeOrderList(Map<String, String> queryParam) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT FORE.ID,FORE.FORE_NO,FORE.REPAIR_NO,DATE_FORMAT(FORE.START_DATE,'%Y-%c-%d') START_DATE, \n");
		sql.append("    DATE_FORMAT(FORE.INTO_DATE,'%Y-%c-%d') INTO_DATE,FORE.INTO_MILEAGE,FORE.VIN,FORE.BRAND, \n");
		sql.append("    FORE.SERIES,FORE.MODEL,FORE.PLATE_NO,FORE.ENGINE_NO, \n");
		sql.append("    FORE.LINK_TEL,DATE_FORMAT(FORE.APPLY_DATE,'%Y-%c-%d') APPLY_DATE,FORE.APPLY_MAN \n");
		sql.append("     FROM TT_WR_FOREAPPROVAL_DCS FORE \n");
		sql.append("     WHERE    FORE.IS_DEL = 0 \n");
		sql.append("       AND FORE.STATUS =  11691004 \n");
		//	   #条件 
		//预授权单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("foreNo"))){
			sql.append("	   AND FORE.FORE_NO LIKE'%"+queryParam.get("foreNo")+"%' \n");
		}
		//维修单号
		if(!StringUtils.isNullOrEmpty(queryParam.get("repairNo"))){
			sql.append("	   AND FORE.REPAIR_NO LIKE'%"+queryParam.get("repairNo")+"%' \n");
		}
		//申请日期
		if(!StringUtils.isNullOrEmpty(queryParam.get("applyDate"))){
			sql.append("	   AND FORE.APPLY_DATE = DATE_FORMAT('"+queryParam.get("applyDate")+"','Y-%c-%d')  \n");
		}
		//VIN
		if(!StringUtils.isNullOrEmpty(queryParam.get("vin"))){
			sql.append("	   AND FORE.VIN LIKE'%"+queryParam.get("vin")+"%' \n");
		}
		
		sql.append("      AND FORE.OEM_COMPANY_ID  = "+loginInfo.getOemCompanyId()+" \n");
		sql.append("      AND FORE.DEALER_ID = "+loginInfo.getDealerId()+" \n");
		//sql.append(" ORDER BY FORE.ID DESC \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 查询工单下的其他项目列表:
	 * @param repairId
	 * @return
	 */
	public PageInfoDto getQueryRepairOrderClaimOther(Map<String, String> queryParam) {
		//校验repairId是否存在
		if(StringUtils.isNullOrEmpty(queryParam.get("repairId"))){
			throw new ServiceBizException("无法获取维修工单号,请联系管理员!");
		}		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWRO.DETAIL_ID,TWRO.REPAIR_ID,TWRO.ITEM_CODE OTHER_FEE_CODE,TWRO.ITEM_NAME OTHER_FEE_NAME, \n");
		sql.append("		COALESCE(TWRO.ITEM_FEE,0) AMOUNT,TWRO.REMARK,twro.ACTIVITY_CODE  \n");
		sql.append("	FROM TT_WR_REPAIR_DCS TWR,TT_WR_REPAIR_OTHERITEM_DCS TWRO , TT_WR_OTHERFEE_DCS TWO \n");
		sql.append("	WHERE  TWR.REPAIR_ID = TWRO.REPAIR_ID  AND TWRO.REPAIR_ID="+queryParam.get("repairId")+"  AND TWO.OTHER_FEE_CODE = TWRO.ITEM_CODE AND TWO.IS_DEL =0 \n");
		//sql.append("	ORDER BY TWRO.DETAIL_ID \n");
		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 查询工单下的维修零部件列表:
	 * @param repairId
	 * @return
	 */
	public PageInfoDto getQueryRepairOrderClaimPart(Map<String, String> queryParam) {

		//校验repairId是否存在
		if(StringUtils.isNullOrEmpty(queryParam.get("repairId"))){
			throw new ServiceBizException("无法获取维修工单号,请联系管理员!");
		}		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append(" select * from ( SELECT TWRP.IS_MAIN,TWRP.PART_CODE,TWRP.PART_NAME, \n");
		sql.append("		COALESCE (tppb.dnp_price, 0) PRICE,SUM(COALESCE(TWRP.QUANTITY,0)) QUANTITY,TWRP.ACTIVITY_CODE,TWRP.REMARK \n");
		sql.append("		,(SELECT COUNT(*) FROM tt_pt_part_relation_DCS WHERE PARTNOOLD = twrp.part_code ) NEWPART_CODE  \n");
		sql.append("	FROM TT_WR_REPAIR_DCS TWR, TT_WR_REPAIR_PART_DCS TWRP , tt_pt_part_base_DCS tppb \n");
		sql.append("	WHERE  TWR.REPAIR_ID = TWRP.REPAIR_ID  AND TWR.REPAIR_ID = "+queryParam.get("repairId")+" AND tppb.part_code = twrp.part_code AND tppb.is_del = 0 \n");
		sql.append("	GROUP BY TWRP.PART_CODE,TWRP.IS_MAIN,TWRP.PART_NAME,DNP_PRICE,TWRP.ACTIVITY_CODE,TWRP.REMARK \n");
		sql.append("	) tt \n");

		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	/**
	 * 查询工单下的维修项目列表:
	 * @param repairId
	 * @return
	 */
	public PageInfoDto getQueryRepairOrderClaimLabour(Map<String, String> queryParam) {
		//校验repairId是否存在
		if(StringUtils.isNullOrEmpty(queryParam.get("repairId"))){
			throw new ServiceBizException("无法获取维修工单号,请联系管理员!");
		}
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT * FROM (SELECT t1.LABOUR_CODE,t1.LABOUR_NAME,t1.ACTIVITY_CODE,t1.REMARK,IFNULL(twl.LABOUR_NUM,0) LABOUR_NUM FROM ( SELECT TWRI.LABOUR_CODE,TWRI.LABOUR_NAME,COALESCE(TWRI.LABOUR_NUM,0) LABOUR_NUM,TWRI.ACTIVITY_CODE,TWRI.REMARK \n");
		sql.append("	     FROM TT_WR_REPAIR_DCS TWR,TT_WR_REPAIR_ITEM_DCS TWRI  \n");
		sql.append("	     WHERE  TWR.REPAIR_ID = TWRI.REPAIR_ID  AND TWR.REPAIR_ID = "+queryParam.get("repairId")+" ) t1 \n");
		sql.append("	     LEFT JOIN TT_WR_LABOUR_DCS TWL ON TWL.LABOUR_CODE = CONCAT('2013',SUBSTR(t1.LABOUR_CODE,5)) AND TWL.IS_DEL = 0 AND GROUP_CODE = 'COMPASS 2.4' ) a \n");
		sql.append(" \n");		
		
		PageInfoDto pageInfoDto = OemDAOUtil.pageQuery(sql.toString(), params);
		return pageInfoDto;

	}
	
	/**
	 * 修改页面数据回显
	 * @param claimId
	 * @param queryParam
	 * @return
	 */
	public Map getQueryEditMessage(Long claimId,Map<String, String> queryParam) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWC.SMALL_AREA_APPROVAL_STATUS,TWR.REPAIR_ID,TWC.CLAIM_ID,TWC.CLAIM_NO,TWC.CLAIM_TYPE,TWC.RO_NO REPAIR_NO, TWR.REPAIR_ID, TWC.DEALER_ID,TWC.VIN,TWC.ACTIVITY_CODE,TWC.STATUS, \n");
		sql.append("		TWC.CUSTOMER_COMPLAIN, TWC.CHECK_REPAIR_PROCEDURES, TWC.DEAL_SCHEME, TWC.APPLY_REASONS, \n");
		sql.append("		TWC.CUSTOMER_BEAR_FEE_PART, TWC.DEALER_BEAR_FEE_PART, TWC.OEM_BEAR_FEE_PART, \n");
		sql.append("		TWC.CUSTOMER_BEAR_FEE_LABOR, TWC.DEALER_BEAR_FEE_LABOR, TWC.OEM_BEAR_FEE_LABOR, \n");
		sql.append("		TWC.CUSTOMER_BEAR_FEE_OTHER, TWC.DEALER_BEAR_FEE_OTHER, TWC.OEM_BEAR_FEE_OTHER, \n");
		sql.append("		REPLACE(TWC.REMARK,'<br/>','') REMARK, \n");
		sql.append("		COALESCE(TWC.LABOUR_PICE,0) LABOUR_PICE, \n");
		sql.append("		COALESCE(TWC.OTHER_AMOUNT,0) OTHER_AMOUNT, \n");
		sql.append("		COALESCE(TWC.ALL_AMOUNT,0) ALL_AMOUNT,COALESCE(TWC.PART_FEE,0) PART_FEE,COALESCE(TWC.MILLEAGE,0) MILLEAGE,  \n");
		sql.append("		COALESCE(TWC.LABOUR_FEE,0) LABOUR_FEE, \n");
		sql.append("		DATE_FORMAT(TWR.BALANCE_DATE,'%Y-%c-%d') BALANCE_DATE,DATE_FORMAT(TWC.APPLY_DATE,'%Y-%c-%d') APPLY_DATE, \n");
		sql.append("		TWC.DEALER_CODE,(SELECT TD.DEALER_NAME FROM TM_DEALER TD WHERE TD.DEALER_CODE=TWC.DEALER_CODE) DEALER_NAME, \n");
		sql.append("		TWC.RO_STARTDATE,TWC.RO_ENDDATE,TWC.FOREAPPROVAL_NO,TWC.SERVE_ADVISOR, \n");
		sql.append("		TWC.VIN,TWC.PLATE_NO,TWC.ENGINE_NO,TWC.BRAND,TWC.SERIES,TWC.MODEL,TWC.PRE_CLAIM_NO,TWC.CONTINUE_FLAG, \n");
		sql.append("		TWC.LASTRO_NO,DATE_FORMAT(TWC.LASTRO_DATE,'%Y-%c-%d') LASTRO_DATE,TWC.LASTRO_MILLEAGE,TWC.SERVICE_CONTRACT_NO, \n");
		sql.append("		DATE_FORMAT(TWC.ARRIVAL_TIME,'%Y-%c-%d') ARRIVAL_TIME,TWC.SHIP_NO,TWC.IS_MAIN_FAULT,TWC.DAMAGE_TYPE,TWC.DAMAGE_AREA,TWC.DAMAGE_DEGREE \n");
		sql.append("		,TWC.TAX_RATE,TWC.PART_PAY,TWC.WARRANTY_DATE \n");
		sql.append("		FROM TT_WR_CLAIM_DCS TWC,TT_WR_REPAIR_DCS TWR \n");
		sql.append("		WHERE TWC.RO_NO=TWR.REPAIR_NO \n");
		sql.append("		AND TWC.CLAIM_ID = "+claimId+" \n");

		Map map = OemDAOUtil.findFirst(sql.toString(), params);				
		return map;

	}
	/**
	 * 新增页面基础数据加载
	 * @return
	 */
	public Map getClaimBaseParamByDealer() {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("SELECT TWBP.LABOUR_PRICE,TWBP.TAX_RATE,TWBP.PART_MANGEFEE,TWBP.VALID_DAYS,TD.DEALER_CODE,TD.DEALER_SHORTNAME  DEALER_NAME \n");
		sql.append("		FROM TT_WR_BASIC_PARA_DCS TWBP \n");
		sql.append("		LEFT JOIN TM_DEALER TD ON  TWBP.DEALER_ID=TD.DEALER_ID \n");
		sql.append("		WHERE TWBP.IS_DEL=0 \n");
		sql.append("		AND TWBP.DEALER_ID="+loginInfo.getDealerId()+" AND TWBP.OEM_COMPANY_ID="+loginInfo.getOemCompanyId()+" \n");
	
		Map map = OemDAOUtil.findFirst(sql.toString(), params);				
		return map;

	}
	
	/**
	 * 新增页面基础数据加载
	 * @return
	 */
	public Map queryShowVehicleInfoMap(String vin) {
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select vhcl.VIN,vhcl.LICENSE_NO,vhcl.ENGINE_NO,DATE_FORMAT(vhcl.PURCHASE_DATE,'%Y-%c-%d') PURCHASE_DATE,  \n");
		sql.append("		 vhcl.MILEAGE,vhcl.MODEL_YEAR,vwm.COLOR_CODE,vwm.COLOR_NAME,vwm.SERIES_CODE, \n");
		sql.append("		 vhcl.MATERIAL_ID,vwm.BRAND_CODE,vwm.BRAND_NAME,vwm.SERIES_CODE,vwm.SERIES_NAME,vwm.MODEL_CODE,vwm.MODEL_NAME \n");
		sql.append("		 from TM_VEHICLE_DEC vhcl,("+getVwMaterialSql()+") vwm \n");
		sql.append("		 where vhcl.MATERIAL_ID = vwm.MATERIAL_ID \n");
		sql.append("		 and vhcl.VIN = '"+vin+"' \n");
		
		Map map = OemDAOUtil.findFirst(sql.toString(), params);				
		return map;

	}
	
}




















