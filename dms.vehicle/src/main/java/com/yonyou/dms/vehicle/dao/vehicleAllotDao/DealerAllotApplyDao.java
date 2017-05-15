package com.yonyou.dms.vehicle.dao.vehicleAllotDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVehicleTransferPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class DealerAllotApplyDao extends OemBaseDAO {

	public PageInfoDto search(Map<String, String> param) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Object> queryParam = new ArrayList<Object>();
		String vin = param.get("vin");
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT D.DEALER_ID, -- 经销商ID \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME, -- 内饰 \n");
		sql.append("       V.VEHICLE_ID -- 车辆ID \n");
		sql.append("  FROM TM_VEHICLE_DEC V \n");
		sql.append(" INNER JOIN TM_DEALER D ON D.DEALER_ID = V.DEALER_ID \n");
		sql.append(" INNER JOIN ("+OemBaseDAO.getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		//在库状态（接车入库）
		sql.append(" WHERE (V.NODE_STATUS IN ('" //+ Constant.VEHICLE_NODE_10 + "', '"		// ZDRL-中进车款确认
                								+ OemDictCodeConstants.VEHICLE_NODE_11 + "', '"			// ZDRQ-换车入库
                								+ OemDictCodeConstants.VEHICLE_NODE_14 +"', '"			// ZTPR-退车入库
                								+ OemDictCodeConstants.VEHICLE_NODE_15 +"') \n");		// ZDLD-经销商验收
		
		sql.append("    OR V.NODE_STATUS IN ('" + OemDictCodeConstants.K4_VEHICLE_NODE_12 + "', '"		// 退换车入库
												//+ Constant.K4_VEHICLE_NODE_14 + "', '"		// 已开票
												+ OemDictCodeConstants.K4_VEHICLE_NODE_19 +"')) \n");	// 已验收
		
		sql.append("   AND V.DEALER_ID = '" + loginInfo.getDealerId() + "' \n");
		sql.append("   AND NOT EXISTS (SELECT 1 FROM TT_VS_NVDR N WHERE N.VIN = V.VIN) \n");
		sql.append("   AND NOT EXISTS (SELECT 1 FROM TT_VS_VEHICLE_TRANSFER VT \n");
		sql.append("                    WHERE VT.VEHICLE_ID = V.VEHICLE_ID \n");
		sql.append("                      AND (VT.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_01 + "' OR \n");
		sql.append("                           VT.CHECK_STATUS = '" + OemDictCodeConstants.TRANSFER_CHECK_STATUS_05 + "')) \n");

		// VIN号
		if (StringUtils.isNotBlank(vin)) {
			vin = vin.replace("，", ",");
			vin = vin.replaceAll("\\^", "\n");
			vin = vin.replaceAll("\\,", "\n");
			sql.append("  " + OemBaseDAO.getVinsAuto(vin, "V") + " \n");
		}
		
		System.out.println("SQL \n ================== \n"+sql+"\n====================");
		System.out.println("Params:"+queryParam);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public List<Map> getBusinessScope(String vehicleId, String inDealerCode) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT V.VIN, -- 车架号 \n");
		sql.append("       CASE V.VIN WHEN T.VIN THEN 'TRUE' ELSE 'FALSE'END AS BUSINESS_SCOPE \n");
		sql.append("  FROM (SELECT V.VEHICLE_ID, -- 车辆ID \n");
		sql.append("               V.VIN, -- 车架号 \n");
		sql.append("               D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("               D.DEALER_SHORTNAME -- 经销商名称 \n");
		sql.append("          FROM TM_VEHICLE_DEC V \n");
		sql.append("               INNER JOIN ("+OemBaseDAO.getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append("               INNER JOIN TM_DEALER_BUSS DB ON DB.GROUP_ID = M.SERIES_ID \n");
		sql.append("               INNER JOIN TM_DEALER D ON D.DEALER_ID = DB.DEALER_ID \n");
		sql.append("         WHERE D.DEALER_CODE = '" + inDealerCode + "'  \n");
		sql.append("           AND V.VEHICLE_ID IN (" + vehicleId + ")) T \n");
		sql.append("       RIGHT JOIN TM_VEHICLE_DEC V ON V.VEHICLE_ID = T.VEHICLE_ID \n");
		sql.append(" WHERE CASE V.VIN WHEN T.VIN THEN 'TRUE' ELSE 'FALSE'END <> 'TRUE' \n");
		sql.append("   AND V.VEHICLE_ID IN (" + vehicleId + ")  \n");
		System.out.println("SQL \n===================\n"+sql+"\n===================");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public List<TmDealerPO> getByCode(String inDealerCode, int dealerTypeDvs) {
		String sql = "select * from tm_dealer where DEALER_CODE = ? and DEALER_TYPE = ?";
		List<TmDealerPO> list = TmDealerPO.findBySQL(sql, inDealerCode,dealerTypeDvs);
		return list;
	}

	public List<TmDealerPO> getByCode(String inDealerCode) {
		String sql = "select * from tm_dealer t where t.DEALER_CODE = ? ";
		List<TmDealerPO> list = TmDealerPO.findBySQL(sql, inDealerCode);
		return list;
	}

	public List<Map> regionalInfoQuery(String dealerCode) {
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT D.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       O3.ORG_CODE AS S_ORG_CODE, -- 小区代码 \n");
		sql.append("       O3.ORG_NAME AS S_ORG_NAME, -- 小区名称 \n");
		sql.append("       O2.ORG_CODE AS B_ORG_CODE, -- 大区代码 \n");
		sql.append("       O2.ORG_NAME AS B_ORG_NAME -- 大区名称 \n");
		sql.append("  FROM TM_DEALER D \n");
		sql.append(" INNER JOIN TM_DEALER_ORG_RELATION DOR ON DOR.DEALER_ID = D.DEALER_ID \n");
		sql.append(" INNER JOIN TM_ORG O3 ON O3.ORG_ID = DOR.ORG_ID AND O3.ORG_LEVEL = '3' \n");
		sql.append(" INNER JOIN TM_ORG O2 ON O2.ORG_ID = O3.PARENT_ORG_ID AND O2.ORG_LEVEL = '2' \n");
		sql.append(" WHERE D.DEALER_CODE = '" + dealerCode + "' \n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public List<TtVsVehicleTransferPO> getTransferByIds(Long dealerId, long vehicleId) {
		String sql = "select * from tt_vs_vehicle_transfer where OUT_DEALER_ID = ? and VEHICLE_ID = ?";
		List<TtVsVehicleTransferPO> list = TtVsVehicleTransferPO.findBySQL(sql, dealerId,vehicleId);
		return list;
	}

	public void delTransferChk(Long transferId) {
		String sql = "delete from tt_vs_vehicle_transfer_chk where TRANSFER_ID = ? ";
		List<Object> queryParam = new ArrayList<Object>();
		queryParam.add(transferId);
		OemDAOUtil.execBatchPreparement(sql, queryParam);;
	}

}
