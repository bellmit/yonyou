package com.infoeai.eai.dao.ws.technicalsupport.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.infoeai.eai.wsServer.vehicle.TsVehicleVO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TS04Dao extends OemBaseDAO {

	public List<TsVehicleVO> getTS04VO(String updateDate) throws ParseException {
		StringBuffer sql = new StringBuffer();
		sql.append("select TV.VEHICLE_ID,TV.VIN,VM.BRAND_CODE,VM.SERIES_CODE,VM.MODEL_CODE,VM.MODEL_YEAR,TV.PRODUCT_DATE,TV.ENGINE_NO,TV.LICENSE_NO,TV.PURCHASE_DATE,TVC.CTM_NAME,TVC.MAIN_PHONE,TD.DEALER_ID,TD.DEALER_CODE,TV.REMARK,'10011001' STATUS  \n");
		sql.append("	from TM_VEHICLE_DEC TV left join ("+getVwMaterialSql()+") VM on TV.MATERIAL_ID = VM.MATERIAL_ID\n");
		sql.append("		left join TT_VS_SALES_REPORT TVS on TVS.VEHICLE_ID = TV.VEHICLE_ID\n");
		sql.append("		left join TT_VS_CUSTOMER TVC on TVC.CTM_ID = TVS.CTM_ID\n");
		sql.append("		left join TM_DEALER TD on TVS.DEALER_ID = TD.DEALER_ID\n");
		sql.append("			WHERE 1=1\n");
		if (updateDate!=null&&!"".equals(updateDate)){
			sql.append("				AND TV.CREATE_DATE BETWEEN '"+updateDate+"' AND NOW() AND TV.UPDATE_DATE is null\n");
			sql.append("				OR TV.UPDATE_DATE BETWEEN '"+updateDate+"' AND NOW() \n");
		}
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setTsVehicleVOList(mapList);
	}

	private List<TsVehicleVO> setTsVehicleVOList(List<Map> mapList) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TsVehicleVO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TsVehicleVO vo = new TsVehicleVO();
			    Long vehicleId = CommonUtils.checkNull(map.get("VEHICLE_ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("VEHICLE_ID")));
			    String vin = CommonUtils.checkNull(map.get("VIN"));
			    String brand = CommonUtils.checkNull(map.get("BRAND_CODE"));
			    String seriesCode = CommonUtils.checkNull(map.get("SERIES_CODE"));
			    String modelCode = CommonUtils.checkNull(map.get("MODEL_CODE"));
			    String packageCode = CommonUtils.checkNull(map.get("MODEL_YEAR"));
			    Date productDate = CommonUtils.checkNull(map.get("PRODUCT_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("PRODUCT_DATE")));
			    String engineNo = CommonUtils.checkNull(map.get("ENGINE_NO"));
			    String licenseNo = CommonUtils.checkNull(map.get("LICENSE_NO"));
			    Date purchaseDate = CommonUtils.checkNull(map.get("PURCHASE_DATE")) == "" ? null : sdf.parse(CommonUtils.checkNull(map.get("PURCHASE_DATE")));
			    String customerName = CommonUtils.checkNull(map.get("CTM_NAME"));
			    String customerPhone = CommonUtils.checkNull(map.get("MAIN_PHONE"));
			    String remark = CommonUtils.checkNull(map.get("REMARK"));
			    String status = CommonUtils.checkNull(map.get("STATUS"));
				    
				vo.setBrand(brand);
				vo.setCustomerName(customerName);
				vo.setCustomerPhone(customerPhone);
				vo.setEngineNo(engineNo);
				vo.setLicenseNo(licenseNo);
				vo.setModelCode(modelCode);
				vo.setPackageCode(packageCode);
				vo.setProductDate(productDate);
				vo.setPurchaseDate(purchaseDate);
				vo.setRemark(remark);
				vo.setSeriesCode(seriesCode);
				vo.setStatus(status);
				vo.setVehicleId(vehicleId);
				vo.setVin(vin);
				resultList.add(vo);
			}
		}
		return resultList;
	}

}
