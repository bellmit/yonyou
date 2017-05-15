package com.yonyou.dms.vehicle.dao.vehicleAllotDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class VehicleAllotQueryDao extends OemBaseDAO {

	public PageInfoDto searchVehicleAllotQuery(Map<String, String> params, LoginInfoDto loginUser) {
		List<Object> queryParam = new ArrayList<Object>();	
		String sql = this.getSqlForQuery(params,loginUser,queryParam);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public List<Map> findDetailById(String transferId) {
		List<Object> queryParam = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT O12.ORG_NAME AS OUT_BIG_AREA, -- 调出大区 \n");
		sql.append("       O11.ORG_NAME AS OUT_SMALL_AREA, -- 调出小区 \n");
		
		sql.append("       D1.DEALER_CODE AS OUT_DEALER_CODE, -- 调出经销商代码 \n");
		sql.append("       D1.DEALER_SHORTNAME AS OUT_DEALER_NAME, -- 调出经销商 \n");
		
		sql.append("       O22.ORG_NAME AS IN_BIG_AREA, -- 调入大区 \n");
		sql.append("       O21.ORG_NAME AS IN_SMALL_AREA, -- 调入小区 \n");
		
		sql.append("       D2.DEALER_CODE AS IN_DEALER_CODE, -- 调入经销商代码 \n");
		sql.append("       D2.DEALER_SHORTNAME AS IN_DEALER_NAME, -- 调入经销商名称 \n");
		
		sql.append("       V.VIN, -- VIN码 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_NAME, -- 车型 \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		
		sql.append("       DATE_FORMAT(VT.CREATE_DATE,'%Y-%m-%d') AS CREATE_DATE, -- 申请日期 \n");
		sql.append("       VT.CHECK_STATUS, -- 审批状态 \n");
		
		sql.append("       VT.TRANSFER_ID, -- 调拨ID \n");
		
		sql.append("       VT.TRANS_REGIONAL_FLAG, -- 跨地区标识 \n");
		
		sql.append("       VTC.CHECK_ORG_ID, -- 调出小区审批部门 \n");
		sql.append("       VTC.CHECK_POSITION_ID, -- 调出小区审批职位 \n");
		sql.append("       (SELECT U.NAME FROM TC_USER U WHERE U.USER_ID = VTC.CHECK_USER_ID) AS CHECK_USER, -- 调出小区审批人 \n");
		sql.append("       DATE_FORMAT(VTC.CHECK_DATE,'%Y-%m-%d') AS CHECK_DATE, -- 调出小区审批日期 \n");
		sql.append("       VTC.CHECK_DESC, -- 调出小区审批描述 \n");
		
		sql.append("       VTC.OUT_BIG_ORG_ID, -- 调出大区审批部门 \n");
		sql.append("       VTC.OUT_BIG_POSITION_ID, -- 调出大区审批职位 \n");
		sql.append("       (SELECT U.NAME FROM TC_USER U WHERE U.USER_ID = VTC.OUT_BIG_USER_ID) AS OUT_BIG_USER, -- 调出大区审批人 \n");
		sql.append("       DATE_FORMAT(VTC.OUT_BIG_CHK_DATE,'%Y-%m-%d') AS OUT_BIG_CHK_DATE, -- 调出大区审批日期 \n");
		sql.append("       VTC.OUT_BIG_CHK_DESC, -- 调出大区审批描述 \n");
		
		sql.append("       VTC.IN_SMALL_ORG_ID, -- 调入小区审批部门 \n");
		sql.append("       VTC.IN_SMALL_POSITION_ID, -- 调入小区审批职位 \n");
		sql.append("       (SELECT U.NAME FROM TC_USER U WHERE U.USER_ID = VTC.IN_SMALL_USER_ID) AS IN_SMALL_USER, -- 调入小区审批人 \n");
		sql.append("       DATE_FORMAT(VTC.IN_SMALL_CHK_DATE,'%Y-%m-%d') AS IN_SMALL_CHK_DATE, -- 调入小区审批日期 \n");
		sql.append("       VTC.IN_SMALL_CHK_DESC, -- 调入小区审批描述 \n");
		
		sql.append("       VTC.IN_BIG_ORG_ID, -- 调入大区审批部门 \n");
		sql.append("       VTC.IN_BIG_POSITION_ID, -- 调入大区审批职位 \n");
		sql.append("       (SELECT U.NAME FROM TC_USER U WHERE U.USER_ID = VTC.IN_BIG_USER_ID) AS IN_BIG_USER, -- 调入大区审批人 \n");
		sql.append("       DATE_FORMAT(VTC.IN_BIG_CHK_DATE,'%Y-%m-%d') AS IN_BIG_CHK_DATE, -- 调入大区审批日期 \n");
		sql.append("       VTC.IN_BIG_CHK_DESC, -- 调入大区审批描述 \n");
		
		sql.append("       VTC.APPROVAL_RECORD -- 审批记录 \n");
		
		sql.append("  FROM TT_VS_VEHICLE_TRANSFER VT \n");
		sql.append("  LEFT JOIN TT_VS_VEHICLE_TRANSFER_CHK VTC ON VTC.TRANSFER_ID = VT.TRANSFER_ID \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.VEHICLE_ID = VT.VEHICLE_ID \n");
		sql.append(" INNER JOIN ("+OemBaseDAO.getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		
		sql.append(" /* 调出经销商 */ \n");
		sql.append(" INNER JOIN TM_DEALER D1 ON D1.DEALER_ID = VT.OUT_DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION DOR1 ON DOR1.DEALER_ID = D1.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG O11 ON O11.ORG_ID = DOR1.ORG_ID AND O11.ORG_LEVEL = 3 \n");
		sql.append(" INNER JOIN TM_ORG O12 ON O12.ORG_ID = O11.PARENT_ORG_ID AND O12.ORG_LEVEL = 2 \n");
		
		sql.append(" /* 调入经销商 */ \n");
		sql.append(" INNER JOIN TM_DEALER D2 ON D2.DEALER_ID = VT.IN_DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION DOR2 ON DOR2.DEALER_ID = D2.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG O21 ON O21.ORG_ID = DOR2.ORG_ID AND O21.ORG_LEVEL = 3 \n");
		sql.append(" INNER JOIN TM_ORG O22 ON O22.ORG_ID = O21.PARENT_ORG_ID AND O22.ORG_LEVEL = 2 \n");
		
		sql.append(" WHERE VT.TRANSFER_ID = ? \n");
		queryParam.add(transferId);
		System.out.println("SQL==================\n" + sql + "\n==================");
		System.out.println("Param:"+queryParam);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), queryParam);
		return list;
	}

	public List<Map> exportVehicleAllotQuery(Map<String, String> params, LoginInfoDto loginInfo) {
		List<Object> queryParam = new ArrayList<Object>();	
		String sql = this.getSqlForQuery(params,loginInfo,queryParam);
		return OemDAOUtil.downloadPageQuery(sql, queryParam);
	}
	
	/**
	 * 获取查询页面的sql
	 * @param loginUser 
	 * @param params 
	 * @param queryParam 
	 * @return
	 */
	private String getSqlForQuery(Map<String, String> params, LoginInfoDto loginUser, List<Object> queryParam){
		StringBuffer sql = new StringBuffer();
		String outDealerCode = params.get("outDealerCode");
		String inDealerCode = params.get("inDealerCode");
		sql.append("\n");
		sql.append("( \n");
		
		sql.append("SELECT O12.ORG_NAME AS OUT_BIG_AREA, -- 调出大区 \n");
		sql.append("       O11.ORG_NAME AS OUT_SMALL_AREA, -- 调出小区 \n");
		sql.append("       VT.OUT_DEALER_ID, -- 调出经销商ID \n");
		sql.append("       D1.DEALER_CODE AS OUT_DEALER_CODE, -- 调出经销商代码 \n");
		sql.append("       D1.DEALER_SHORTNAME AS OUT_DEALER_NAME, -- 调出经销商 \n");
		sql.append("       VTC.CHECK_DATE, -- 调出小区审批日期 \n");
		sql.append("       VTC.OUT_BIG_CHK_DATE, -- 调出大区审批日期 \n");
		
		sql.append("       O22.ORG_NAME AS IN_BIG_AREA, -- 调入大区 \n");
		sql.append("       O21.ORG_NAME AS IN_SMALL_AREA, -- 调入小区 \n");
		sql.append("       VT.IN_DEALER_ID, -- 调入经销商ID \n");
		sql.append("       D2.DEALER_CODE AS IN_DEALER_CODE, -- 调入经销商代码 \n");
		sql.append("       D2.DEALER_SHORTNAME AS IN_DEALER_NAME, -- 调入经销商名称 \n");
		sql.append("       VTC.IN_SMALL_CHK_DATE, -- 调入小区审批日期 \n");
		sql.append("       VTC.IN_BIG_CHK_DATE, -- 调入大区审批日期 \n");
		
		sql.append("       V.VIN, -- VIN码 \n");
		sql.append("       M.SERIES_ID, -- 车系ID \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_NAME, -- 车型 \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       VT.CREATE_DATE AS APPLY_DATE, -- 申请日期 \n");
		sql.append("       VT.CHECK_STATUS, -- 审批状态 \n");
		
		sql.append("       (CASE WHEN VT.TRANS_REGIONAL_FLAG = 0 THEN (SELECT MAX(VTC.CHECK_DATE) FROM TT_VS_VEHICLE_TRANSFER_CHK VTC WHERE VTC.TRANSFER_ID = VT.TRANSFER_ID AND VTC.CHECK_STATUS = '20201002') \n");
		sql.append("             WHEN VT.TRANS_REGIONAL_FLAG = 1 THEN (SELECT MAX(VTC.IN_SMALL_CHK_DATE) FROM TT_VS_VEHICLE_TRANSFER_CHK VTC WHERE VTC.TRANSFER_ID = VT.TRANSFER_ID AND VTC.CHECK_STATUS = '20201002') \n");
		sql.append("             WHEN VT.TRANS_REGIONAL_FLAG = 2 THEN (SELECT MAX(VTC.IN_BIG_CHK_DATE) FROM TT_VS_VEHICLE_TRANSFER_CHK VTC WHERE VTC.TRANSFER_ID = VT.TRANSFER_ID AND VTC.CHECK_STATUS = '20201002') \n");
		sql.append("             ELSE NULL END) AS THROUGH_DATE, -- 审批通过日期 \n");
		
		sql.append("       VT.TRANSFER_ID -- 调拨ID \n");
		sql.append("  FROM TT_VS_VEHICLE_TRANSFER VT \n");
		sql.append("  LEFT JOIN TT_VS_VEHICLE_TRANSFER_CHK VTC ON VTC.TRANSFER_ID = VT.TRANSFER_ID \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.VEHICLE_ID = VT.VEHICLE_ID \n");
		sql.append(" INNER JOIN ("+OemBaseDAO.getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" /* 调出经销商 */ \n");
		sql.append(" INNER JOIN TM_DEALER D1 ON D1.DEALER_ID = VT.OUT_DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION DOR1 ON DOR1.DEALER_ID = D1.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG O11 ON O11.ORG_ID = DOR1.ORG_ID AND O11.ORG_LEVEL = 3 \n");
		sql.append(" INNER JOIN TM_ORG O12 ON O12.ORG_ID = O11.PARENT_ORG_ID AND O12.ORG_LEVEL = 2 \n");
		sql.append(" /* 调入经销商 */ \n");
		sql.append(" INNER JOIN TM_DEALER D2 ON D2.DEALER_ID = VT.IN_DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION DOR2 ON DOR2.DEALER_ID = D2.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG O21 ON O21.ORG_ID = DOR2.ORG_ID AND O21.ORG_LEVEL = 3 \n");
		sql.append(" INNER JOIN TM_ORG O22 ON O22.ORG_ID = O21.PARENT_ORG_ID AND O22.ORG_LEVEL = 2 \n");
		
		sql.append(") \n");
		
		StringBuffer sql2 = new StringBuffer();
		
		sql2.append("SELECT T.OUT_BIG_AREA, -- 调出大区 \n");
		sql2.append("       T.OUT_SMALL_AREA, -- 调出小区 \n");
		sql2.append("       T.OUT_DEALER_ID, -- 调出经销商ID \n");
		sql2.append("       T.OUT_DEALER_CODE, -- 调出经销商代码 \n");
		sql2.append("       T.OUT_DEALER_NAME, -- 调出经销商 \n");
		sql2.append("       T.CHECK_DATE AS OUT_SMALL_CHK_DATE, -- 调出小区审批日期 \n");
		sql2.append("       T.OUT_BIG_CHK_DATE AS OUT_BIG_CHK_DATE, -- 调出大区审批日期 \n");
		sql2.append("       T.IN_BIG_AREA, -- 调入大区 \n");
		sql2.append("       T.IN_SMALL_AREA, -- 调入小区 \n");
		sql2.append("       T.IN_DEALER_ID, -- 调入经销商ID \n");
		sql2.append("       T.IN_DEALER_CODE, -- 调入经销商代码 \n");
		sql2.append("       T.IN_DEALER_NAME, -- 调入经销商名称 \n");
		sql2.append("       T.IN_SMALL_CHK_DATE AS IN_SMALL_CHK_DATE, -- 调入小区审批日期 \n");
		sql2.append("       T.IN_BIG_CHK_DATE AS IN_BIG_CHK_DATE, -- 调入大区审批日期 \n");
		sql2.append("       T.VIN, -- VIN号 \n");
		sql2.append("       T.SERIES_ID, -- 车系ID \n");
		sql2.append("       T.SERIES_NAME, -- 车系 \n");
		sql2.append("       T.MODEL_NAME, -- 车型 \n");
		sql2.append("       T.GROUP_NAME, -- 车款 \n");
		sql2.append("       T.COLOR_NAME, -- 颜色 \n");
		sql2.append("       T.APPLY_DATE AS APPLY_DATE, -- 申请日期 \n");
		sql2.append("       T.CHECK_STATUS, -- 审批状态 \n");
		sql2.append("       T.THROUGH_DATE AS THROUGH_DATE, -- 审批通过日期 \n");
		sql2.append("       T.TRANSFER_ID -- 调拨ID \n");
		sql2.append("  FROM "+ sql + " T \n");
		sql2.append(" WHERE 1 = 1 \n");
				
		if (loginUser.getDutyType() != null && !loginUser.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_DEPT.toString())
				&& !loginUser.getDutyType().equals(OemDictCodeConstants.DUTY_TYPE_COMPANY.toString())) {
			
			sql2.append("   AND (T.OUT_DEALER_ID IN (" + getDealersByArea(loginUser.getOrgId().toString()) + ") \n");
			sql2.append("    OR  T.IN_DEALER_ID IN (" + getDealersByArea(loginUser.getOrgId().toString()) + ")) \n");
		}
		
		/*
		 * 业务范围 Begin
		 */
		sql2.append("  " + control("T.SERIES_ID", loginUser.getDealerSeriesIDs(), loginUser.getPoseSeriesIDs()) + " \n");
		/*
		 * 业务范围 End..
		 */

		
		// 调拨申请日期（开始）
		if (StringUtils.isNotBlank(params.get("beginDate"))) {
			sql2.append("   AND DATE(T.THROUGH_DATE) >= ? \n");
			queryParam.add(params.get("beginDate"));
			
		}

		// 调拨申请日期（结束）
		if (StringUtils.isNotBlank(params.get("endDate"))) {
			sql2.append("   AND DATE(T.THROUGH_DATE) <= ? \n");
			queryParam.add(params.get("endDate"));
		}
		
		// VIN码
		if (StringUtils.isNotBlank(params.get("vin"))) {
			String vin = params.get("vin");
			vin = vin.replace("，", ",");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql2.append("  " + OemBaseDAO.getVinsAuto(vin, "T") + " \n");
		}
		
		// 审批状态
		if (StringUtils.isNotBlank(params.get("checkStatus"))) {
			sql2.append("   AND T.CHECK_STATUS = ? \n");
			queryParam.add(params.get("checkStatus"));
		}
		
		// 调出经销商代码
		if (StringUtils.isNotBlank(outDealerCode)) {
			outDealerCode = outDealerCode.replaceAll(",", "','");
			sql2.append("   AND T.OUT_DEALER_CODE IN ('"+outDealerCode+"') \n");
		}
		
		// 调入经销商代码
		if (StringUtils.isNotBlank(inDealerCode)) {
			inDealerCode = inDealerCode.replaceAll(",", "','");
			sql2.append("   AND T.IN_DEALER_CODE IN ('"+inDealerCode+"') \n");
		}
		
		System.out.println("SQl======================\n"+sql2+"\n========================");
		System.out.println("Param:"+queryParam);
		return sql2.toString();
	}
	
	

}
