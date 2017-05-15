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
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

@Repository
public class DealerAllotQueryDao extends OemBaseDAO {

	public PageInfoDto search(Map<String, String> param) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode = loginInfo.getDealerCode();
		String brandId = param.get("brandId");
		String seriesId = param.get("seriesId");
		String groupId = param.get("groupId");
		String modelYear = param.get("modelYear");
		String colorCode = param.get("colorCode");
		String trimCode = param.get("trimCode");
		String beginDate = param.get("beginDate");
		String endDate = param.get("endDate");
		String vin = param.get("vin");
		String checkStatus = param.get("checkStatus");
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
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       DATE_FORMAT(VT.CREATE_DATE, '%Y-%m-%d') AS CREATE_DATE, -- 申请日期 \n");
		sql.append("       VT.CHECK_STATUS, -- 审批状态 \n");
		sql.append("       VT.TRANSFER_ID -- 调拨ID \n");
		sql.append("  FROM TT_VS_VEHICLE_TRANSFER VT \n");
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
		sql.append(" WHERE (D1.DEALER_CODE = '" + dealerCode + "' OR D2.DEALER_CODE = '" + dealerCode + "') \n");
		
		// 品牌
		if (StringUtils.isNotBlank(brandId)) {
			sql.append("   AND M.BRAND_ID = ? \n");
			queryParam.add(brandId);
		}
		
		// 车系
		if (StringUtils.isNotBlank(seriesId)) {
			sql.append("   AND M.SERIES_ID = ? \n");
			queryParam.add(seriesId);
		}
		
		// 车款
		if (StringUtils.isNotBlank(groupId)) {
			sql.append("   AND M.GROUP_ID = ? \n");
			queryParam.add(groupId);
		}
		
		// 年款
		if (StringUtils.isNotBlank(modelYear)) {
			sql.append("   AND M.MODEL_YEAR = ? \n");
			queryParam.add(modelYear);
		}
		
		// 颜色
		if (StringUtils.isNotBlank(colorCode)) {
			sql.append("   AND M.COLOR_CODE = ? \n");
			queryParam.add(colorCode);
		}
		
		// 内饰
		if (StringUtils.isNotBlank(trimCode)) {
			sql.append("   AND M.TRIM_CODE = ? \n");
			queryParam.add(trimCode);
		}
		
		// 调拨申请日期 Begin
		if (StringUtils.isNotBlank(beginDate)) {
			sql.append("   AND DATE(VT.CREATE_DATE) >= ? \n");
			queryParam.add(beginDate);
		}
		
		// 调拨申请日期 End
		if (StringUtils.isNotBlank(endDate)) {
			sql.append("   AND DATE(VT.CREATE_DATE) <= ? \n");
			queryParam.add(endDate);
		}
		
		// VIN号
		if (StringUtils.isNotBlank(vin)) {
			sql.append("   AND V.VIN LIKE UPPER(?) \n");
			queryParam.add("%"+vin+"%");
		}
		
		// 审批状态
		if (StringUtils.isNotBlank(checkStatus)) {
			sql.append("   AND VT.CHECK_STATUS = ? \n");
			queryParam.add(checkStatus);
		}
		
		System.out.println("SQL \n ====================\n"+sql+"\n =======================");
		System.out.println("Params:"+queryParam);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}


}
