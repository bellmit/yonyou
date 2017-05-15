package com.yonyou.dms.vehicle.dao.vehicleAllotDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TtVsVehicleTransferPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.PO.vehicleAllot.TtVsVehicleTransferChkPO;

@Repository
public class VehicleAllotApproveDao extends OemBaseDAO{

	public PageInfoDto searchVehicleAllotApprove(Map<String, String> params, LoginInfoDto loginUser) {
		List<Object> queryParam = new ArrayList<Object>();
		String outDealerCode = params.get("outDealerCode");
		String inDealerCode = params.get("inDealerCode");
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT T.TRANSFER_ID, -- 主键ID \n");
		sql.append("       O12.ORG_ID, -- 调出大区ID \n");
		sql.append("       O12.ORG_NAME, -- 调出大区 \n");
		sql.append("       D1.DEALER_SHORTNAME AS OUT_DEALER_NAME, -- 调出经销商 \n");
		sql.append("       O22.ORG_ID, -- 调出大区ID \n");
		sql.append("       O22.ORG_NAME, -- 调出大区 \n");
		sql.append("       D2.DEALER_SHORTNAME AS IN_DEALER_NAME, -- 调入经销商 \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       T.CREATE_DATE, -- 创建日期 \n");
		sql.append("       T.TRANSFER_REASON, -- 调拨原因 \n");
		sql.append("       T.TRANS_REGIONAL_FLAG -- 跨区标识 \n");
		sql.append("  FROM TT_VS_VEHICLE_TRANSFER T \n");
		sql.append("  LEFT JOIN TT_VS_VEHICLE_TRANSFER_CHK CHK ON CHK.TRANSFER_ID = T.TRANSFER_ID \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.VEHICLE_ID = T.VEHICLE_ID \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" INNER JOIN TM_DEALER D1 ON D1.DEALER_ID = T.OUT_DEALER_ID \n");
		sql.append(" INNER JOIN TM_DEALER D2 ON D2.DEALER_ID = T.IN_DEALER_ID \n");
		
		sql.append(" /* 调出大区 */ \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION DOR1 ON DOR1.DEALER_ID = T.OUT_DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG O11 ON O11.ORG_ID = DOR1.ORG_ID AND O11.ORG_LEVEL = 3 \n");
		sql.append(" INNER JOIN TM_ORG O12 ON O12.ORG_ID = O11.PARENT_ORG_ID AND O12.ORG_LEVEL = 2 \n");
		
		sql.append(" /* 调入大区 */ \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION DOR2 ON DOR2.DEALER_ID = T.IN_DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG O21 ON O21.ORG_ID = DOR2.ORG_ID AND O21.ORG_LEVEL = 3 \n");
		sql.append(" INNER JOIN TM_ORG O22 ON O22.ORG_ID = O21.PARENT_ORG_ID AND O22.ORG_LEVEL = 2 \n");
		//测试时关闭 12行
		sql.append(" WHERE ((T.TRANS_REGIONAL_FLAG = 0 AND T.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_01 + "' AND O11.ORG_ID = '" + loginUser.getOrgId() + "') \n");
		sql.append("    OR  (T.TRANS_REGIONAL_FLAG = 1 AND ((T.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_01 + "' AND O11.ORG_ID = '" + loginUser.getOrgId() + "') \n");
		sql.append("                                    OR (T.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_05 + "' AND CHK.APPROVAL_RECORD = 1 AND O21.ORG_ID = '" + loginUser.getOrgId() + "'))) \n");
		sql.append("    OR  (T.TRANS_REGIONAL_FLAG = 2 AND ((T.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_01 + "' AND O11.ORG_ID = '" + loginUser.getOrgId() + "') \n");
		sql.append("                                    OR (T.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_05 + "' AND CHK.APPROVAL_RECORD = 1 AND O12.ORG_ID = '" + loginUser.getOrgId() + "') \n");
		sql.append("                                    OR (T.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_05 + "' AND CHK.APPROVAL_RECORD = 2 AND O21.ORG_ID = '" + loginUser.getOrgId() + "') \n");
		sql.append("                                    OR (T.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_05 + "' AND CHK.APPROVAL_RECORD = 3 AND O22.ORG_ID = '" + loginUser.getOrgId() + "')))) \n");
		
		/*
		 * 业务范围 Begin
		 */
		sql.append("  " + control("M.SERIES_ID", loginUser.getDealerSeriesIDs(), loginUser.getPoseSeriesIDs()) + " \n");
		/*
		 * 业务范围 End..
		 */
		
		// 品牌
		if (StringUtils.isNotBlank(params.get("brandId"))) {
			sql.append("   AND M.BRAND_ID = ? \n");
			queryParam.add(params.get("brandId"));
		}
		
		// 车系
		if (StringUtils.isNotBlank(params.get("seriesId"))) {
			sql.append("   AND M.SERIES_ID = ? \n");
			queryParam.add(params.get("seriesId"));
		}
		
		// 车款
		if (StringUtils.isNotBlank(params.get("groupId"))) {
			sql.append("   AND M.GROUP_ID = ? \n");
			queryParam.add(params.get("groupId"));
		}
		
		// 年款
		if (StringUtils.isNotBlank(params.get("modelYear"))) {
			sql.append("   AND M.MODEL_YEAR = ? \n");
			queryParam.add(params.get("modelYear"));
		}
		
		// 颜色
		if (StringUtils.isNotBlank(params.get("colorCode"))) {
			sql.append("   AND M.COLOR_CODE = ? \n");
			queryParam.add(params.get("colorCode"));
		}
		
		// 内饰
		if (StringUtils.isNotBlank(params.get("trimCode"))) {
			sql.append("   AND M.TRIM_CODE = ? \n");
			queryParam.add(params.get("trimCode"));
		}
		
		// 调出经销商
		if (StringUtils.isNotBlank(outDealerCode)) {
			outDealerCode = outDealerCode.replaceAll(",", "','");
			sql.append("   AND D1.DEALER_CODE IN ('"+outDealerCode+"')");
		}
		
		// 调入经销商
		if (StringUtils.isNotBlank(inDealerCode)) {
			inDealerCode = inDealerCode.replaceAll(",", "','");
			sql.append("   AND D2.DEALER_CODE IN ('"+inDealerCode+"')");
		}
		
		if (StringUtils.isNotBlank(params.get("groupCode"))) {
			String groupCode = params.get("groupCode").replaceAll(",", "','");
			sql.append("   AND M1.GROUP_CODE in ? ");
			queryParam.add("('"+ groupCode +"')");
		}
		
		// VIN号
		if (StringUtils.isNotBlank(params.get("vin"))) {
			sql.append("   AND V.VIN = ? \n");
			queryParam.add(params.get("vin"));
		}

//		sql.append(" ORDER BY T.CREATE_DATE DESC \n");		
		System.out.println("SQL \n================"+sql+"================");
		System.out.println("Params"+queryParam);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public List<Map> selectSeriesName(Map<String, String> params) {
		List<Object> queryParam = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("       S.GROUP_ID AS SERIES_ID, -- 车系ID \n");
		sql.append("       S.GROUP_CODE AS SERIES_CODE, -- 车系代码 \n");
		sql.append("       S.GROUP_NAME AS SERIES_NAME, -- 车系名称 \n");
		sql.append("       S.PARENT_GROUP_ID -- 父级ID \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP S \n");
		sql.append(" WHERE S.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR );
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	public List<Map> selectBrandName(Map<String, String> params) {
		List<Object> queryParam = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("       B.GROUP_ID AS BRAND_ID, -- 品牌ID \n");
		sql.append("       B.GROUP_CODE AS BRAND_CODE, -- 品牌代码 \n");
		sql.append("       B.GROUP_NAME AS BRAND_NAME -- 品牌名称 \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP B \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP S ON S.PARENT_GROUP_ID = B.GROUP_ID \n");
		sql.append(" WHERE B.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_BRAND);
		sql.append("   AND S.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR);
		
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	public List<Map> selectGroupName(Map<String, String> params) {
		List<Object> queryParam = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("       G.GROUP_ID, -- 车款ID \n");
		sql.append("       G.GROUP_CODE, -- 车款代码 \n");
		sql.append("       G.GROUP_NAME -- 车款名称 \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP G \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP C ON C.GROUP_ID = G.PARENT_GROUP_ID \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP S ON S.GROUP_ID = C.PARENT_GROUP_ID \n");
		sql.append(" WHERE G.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_TYPE);
		sql.append("   AND C.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_MODEL);
		sql.append("   AND S.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR);
		
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
		
	}

	public List<Map> selectModelYear(Map<String, String> params) {
		List<Object> queryParam = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("       G.MODEL_YEAR -- 年款 \n");
		sql.append("  FROM TM_VHCL_MATERIAL_GROUP G \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP C ON C.GROUP_ID = G.PARENT_GROUP_ID \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP S ON S.GROUP_ID = C.PARENT_GROUP_ID \n");
		sql.append(" WHERE G.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_TYPE);
		sql.append("   AND C.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_MODEL);
		sql.append("   AND S.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR);
		
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	public List<Map> selectColorName(Map<String, String> params) {
		List<Object> queryParam = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("       M.COLOR_NAME -- 颜色 \n");
		sql.append("  FROM TM_VHCL_MATERIAL M \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP_R R ON R.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP G ON G.GROUP_ID = R.GROUP_ID \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP C ON C.GROUP_ID = G.PARENT_GROUP_ID \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP S ON S.GROUP_ID = C.PARENT_GROUP_ID \n");
		sql.append(" WHERE G.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_TYPE);
		sql.append("   AND C.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_MODEL);
		sql.append("   AND S.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR);
		
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	public List<Map> selectTrimName(Map<String, String> params) {
		List<Object> queryParam = new ArrayList<Object>();
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT DISTINCT \n");
		sql.append("       M.TRIM_NAME -- 内饰 \n");
		sql.append("  FROM TM_VHCL_MATERIAL M \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP_R R ON R.MATERIAL_ID = M.MATERIAL_ID \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP G ON G.GROUP_ID = R.GROUP_ID \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP C ON C.GROUP_ID = G.PARENT_GROUP_ID \n");
		sql.append("  LEFT JOIN TM_VHCL_MATERIAL_GROUP S ON S.GROUP_ID = C.PARENT_GROUP_ID \n");
		sql.append(" WHERE G.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_TYPE);
		sql.append("   AND C.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_MODEL);
		sql.append("   AND S.GROUP_LEVEL = ? \n");
		queryParam.add(OemDictCodeConstants.TM_VHCL_MATERIAL_GROUP_CAR);
		
		List<Map> resultList = OemDAOUtil.findAll(sql.toString(), queryParam);
		return resultList;
	}

	public List<Map> findMaterial(Long materialId) {
		List<Object> queryParam = new ArrayList<Object>();		
		String sql="select * from ("+getVwMaterialSql()+") M where M.MATERIAL_ID = ? ";
		queryParam.add(materialId);
		List<Map> resultList = OemDAOUtil.findAll(sql, queryParam);
		return resultList;
	}

	public List<TtVsVehicleTransferChkPO> findChkList(String id) {
		String sql = "SELECT * FROM TT_VS_VEHICLE_TRANSFER_CHK T WHERE T.TRANSFER_ID = '"+id+"'";
		System.out.println("SQL \n================"+sql+"================");
		List<TtVsVehicleTransferChkPO> chkList = TtVsVehicleTransferPO.findBySQL(sql);
		return chkList;
	}
	
	/**
	 * 是否为进口车辆
	 * @param vehicleId
	 * @return
	 */
	public boolean isImportVehicle(Long vehicleId){
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VIN \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN ("+getVwMaterialSql()+") M \n");
		sql.append("    ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE M.GROUP_TYPE = ? \n");
		queryParam.add(OemDictCodeConstants.GROUP_TYPE_IMPORT);
		sql.append("   AND V.VEHICLE_ID = ? \n");
		queryParam.add(vehicleId);
		List<Map> result = OemDAOUtil.findAll(sql.toString(), queryParam);
		if(result != null && result.size() > 0){
			return true;
		}else{
			return false;
		}
	}

}
