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
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Repository
public class DealerTransferConfirmDao extends OemBaseDAO {

	public PageInfoDto search(Map<String, String> param) {
		String vin = param.get("vin");
		List<Object> queryParam = new ArrayList<Object>();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT VT.TRANSFER_ID, -- 调拨ID \n");
		sql.append("       VT.OUT_DEALER_ID, -- 调出经销商ID \n");
		sql.append("       VT.IN_DEALER_ID, -- 调入经销商ID \n");
		sql.append("       V.VIN, -- 车架号 \n");
		sql.append("       M.BRAND_NAME, -- 品牌 \n");
		sql.append("       M.SERIES_NAME, -- 车系 \n");
		sql.append("       M.MODEL_CODE, -- CPOS \n");
		sql.append("       M.GROUP_NAME, -- 车款 \n");
		sql.append("       M.MODEL_YEAR, -- 年款 \n");
		sql.append("       M.COLOR_NAME, -- 颜色 \n");
		sql.append("       M.TRIM_NAME -- 内饰 \n");
		sql.append("  FROM TT_VS_VEHICLE_TRANSFER VT \n");
		sql.append(" INNER JOIN TM_VEHICLE_DEC V ON V.VEHICLE_ID = VT.VEHICLE_ID \n");
		sql.append(" INNER JOIN ("+OemBaseDAO.getVwMaterialSql()+") M ON M.MATERIAL_ID = V.MATERIAL_ID \n");
		sql.append(" WHERE VT.CHECK_STATUS = '"+OemDictCodeConstants.TRANSFER_CHECK_STATUS_02+"' \n");
		//以下四行测试时关闭
		sql.append("   AND M.GROUP_TYPE = ? \n");
		//老代码
//		queryParam.add(OemDictCodeConstants.GROUP_TYPE_DOMESTIC);			//国产车
		queryParam.add(OemDictCodeConstants.GROUP_TYPE_IMPORT);			    //进口车
		sql.append("   AND VT.IN_DEALER_ID = ? \n");
		queryParam.add(loginUser.getDealerId());
		
		// 车架号
		if (StringUtils.isNotBlank(vin)) {
			vin = vin.replace("，", ",");
			vin = vin.replaceAll("\\^", " \n");
			vin = vin.replaceAll("\\,", " \n");
			sql.append("   " + OemBaseDAO.getVinsAuto(vin, "V") + " \n");
		}
		System.out.println("SQL \n ================== \n"+sql+"\n====================");
		System.out.println("Params:"+queryParam);
		return OemDAOUtil.pageQuery(sql.toString(), queryParam);
	}

	public List<Map> getTransferInDealerIds(String transferId) {
		transferId = transferId.replaceAll(",", "','");
		List<Object> queryParam = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT VT.TRANSFER_ID, -- 调拨ID \n");
		sql.append("       VT.VEHICLE_ID, -- 车辆ID \n");
		sql.append("       VT.OUT_DEALER_ID, -- 调出经销商ID \n");
		sql.append("       VT.IN_DEALER_ID -- 调入经销商ID \n");
		sql.append("  FROM TT_VS_VEHICLE_TRANSFER VT \n");
		sql.append(" WHERE VT.CHECK_STATUS = ? \n");
		queryParam.add(OemDictCodeConstants.TRANSFER_CHECK_STATUS_02);
		sql.append("   AND VT.TRANSFER_ID IN ('"+transferId+"') \n");
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

	public Map getMaterial(String materialId){
		List<Object> queryParam = new ArrayList<Object>();
		String sql = "select * from ("+getVwMaterialSql()+") M where material_id = "+materialId;
		return OemDAOUtil.findFirst(sql, queryParam);
	}

}
