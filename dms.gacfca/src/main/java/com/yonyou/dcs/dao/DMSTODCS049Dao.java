package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiUsedCarReplacementIntentionDetailDTO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class DMSTODCS049Dao extends OemBaseDAO {

	public List<TiUsedCarReplacementIntentionDetailDTO> queryTiUsedData(String groupId) {
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ID, ITEM_ID, DEALER_CODE, CUSTOMER_NO, CUSTOMER_PROVINCE, CUSTOMER_CITY, CUSTOMER_TYPE,	\n" );
		sql.append("	INTENTION_DATE, INTENTION_BRAND_CODE, INTENTION_SERIES_CODE, INTENTION_MODEL_CODE,	\n" );
		sql.append("	USED_CAR_BRAND_CODE, USED_CAR_SERIES_CODE, USED_CAR_MODEL_CODE, USED_CAR_LICENSE,	\n");
		sql.append("	USED_CAR_VIN, USED_CAR_ASSESS_AMOUNT, USED_CAR_LICENSE_DATE, USED_CAR_MILEAGE,	\n" );
		sql.append("	USED_CAR_DESCRIBE, REPORT_TYPE, RESULT, MESSAGE, GROUP_ID, IS_DEL	\n");
		sql.append("		FROM TI_USED_CAR_REPLACEMENT_INTENTION_DETAIL_DCS	\n");
		sql.append("	 		WHERE GROUP_ID='"+groupId+"'	\n" );
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		return setTiUsedCarReplacementIntentionDetailDtoList(mapList);
	}

	private List<TiUsedCarReplacementIntentionDetailDTO> setTiUsedCarReplacementIntentionDetailDtoList(
			List<Map> mapList) {
		List<TiUsedCarReplacementIntentionDetailDTO> resultList = new ArrayList<>();
		if(mapList != null && !mapList.isEmpty()){
			for(Map map : mapList){
				TiUsedCarReplacementIntentionDetailDTO dto = new TiUsedCarReplacementIntentionDetailDTO();
				String usedCarAssessAmount = CommonUtils.checkNull(map.get("USED_CAR_ASSESS_AMOUNT"));
				String groupId = CommonUtils.checkNull(map.get("GROUP_ID"));
				String usedCarMileage = CommonUtils.checkNull(map.get("USED_CAR_MILEAGE"));
				String intentionModelCode = CommonUtils.checkNull(map.get("INTENTION_MODEL_CODE"));
				String usedCarLicense = CommonUtils.checkNull(map.get("USED_CAR_LICENSE_DATE"));
				String customerProvince = CommonUtils.checkNull(map.get("CUSTOMER_PROVINCE"));
				String dealerCode = CommonUtils.checkNull(map.get("DEALER_CODE"));
				String usedCarSeriesCode = CommonUtils.checkNull(map.get("USED_CAR_SERIES_CODE"));
				String usedCarBrandCode = CommonUtils.checkNull(map.get("USED_CAR_BRAND_CODE"));
				String usedCarDescribe = CommonUtils.checkNull(map.get("USED_CAR_DESCRIBE"));
				Integer isDel = CommonUtils.checkNull(map.get("IS_DEL")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("IS_DEL")));
				String customerCity = CommonUtils.checkNull(map.get("CUSTOMER_CITY"));
				String intentionBrandCode = CommonUtils.checkNull(map.get("INTENTION_BRAND_CODE"));
				String message = CommonUtils.checkNull(map.get("MESSAGE"));
				Integer result = CommonUtils.checkNull(map.get("RESULT")) == "" ? null : Integer.parseInt(CommonUtils.checkNull(map.get("RESULT")));
				String intentionSeriesCode = CommonUtils.checkNull(map.get("INTENTION_SERIES_CODE"));
				String usedCarModelCode = CommonUtils.checkNull(map.get("USED_CAR_MODEL_CODE"));
				String usedCarLicenseDate = CommonUtils.checkNull(map.get("USED_CAR_LICENSE_DATE"));
				Long id = CommonUtils.checkNull(map.get("ID")) == "" ? null : Long.parseLong(CommonUtils.checkNull(map.get("ID")));
				String usedCarVin = CommonUtils.checkNull(map.get("USED_CAR_VIN"));
				String reportType = CommonUtils.checkNull(map.get("REPORT_TYPE"));//上报类型:1：新增、2：更新、3：删除
				String customerType = CommonUtils.checkNull(map.get("CUSTOMER_TYPE"));
				String itemId = CommonUtils.checkNull(map.get("ITEM_ID"));//单条数据标识ID
				String intentionDate = CommonUtils.checkNull(map.get("INTENTION_DATE"));//置换意向时间
				String customerNo = CommonUtils.checkNull(map.get("CUSTOMER_NO"));//客户编号
				
				dto.setCustomerCity(customerCity);
				dto.setCustomerNo(customerNo);
				dto.setCustomerProvince(customerProvince);
				dto.setCustomerType(customerType);
				dto.setDealerCode(dealerCode);
				dto.setGroupId(groupId);
				dto.setId(id);
				dto.setIntentionBrandCode(intentionBrandCode);
				dto.setIntentionDate(intentionDate);
				dto.setIntentionModelCode(intentionModelCode);
				dto.setIntentionSeriesCode(intentionSeriesCode);
				dto.setIsDel(isDel);
				dto.setItemId(itemId);
				dto.setMessage(message);
				dto.setReportType(reportType);
				dto.setResult(result);
				dto.setUsedCarAssessAmount(usedCarAssessAmount);
				dto.setUsedCarBrandCode(usedCarBrandCode);
				dto.setUsedCarDescribe(usedCarDescribe);
				dto.setUsedCarLicense(usedCarLicense);
				dto.setUsedCarLicenseDate(usedCarLicenseDate);
				dto.setUsedCarMileage(usedCarMileage);
				dto.setUsedCarModelCode(usedCarModelCode);
				dto.setUsedCarSeriesCode(usedCarSeriesCode);
				dto.setUsedCarVin(usedCarVin);
				resultList.add(dto);
			}
		}
		return resultList;
	}

	public List<Map> selectMaterial(String brandCode, String seriesCode, String modelCode) {
		StringBuffer sql = new StringBuffer(getVwMaterialSql());
		List<Object> queryParam = new ArrayList<>();
		sql.append(" AND MG1.GROUP_CODE = ?  AND MG2.GROUP_CODE = ? AND MG3.GROUP_CODE = ?");
		queryParam.add(brandCode);
		queryParam.add(seriesCode);
		queryParam.add(modelCode);
		return OemDAOUtil.findAll(sql.toString(), queryParam);
	}

}
