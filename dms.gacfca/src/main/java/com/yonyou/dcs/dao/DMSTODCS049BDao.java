package com.yonyou.dcs.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiUsedCarReplacementIntentionDetailBDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class DMSTODCS049BDao extends OemBaseDAO {

	/**
	 * 
	* @Title:	queryTiUsedData
	* @Description: TODO(查询接口表上报的数据) 
	 */
	public List<TiUsedCarReplacementIntentionDetailBDTO> queryTiUsedData(String groupId){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ID, ITEM_ID, DEALER_CODE, CUSTOMER_NO, CUSTOMER_PROVINCE, CUSTOMER_CITY, CUSTOMER_TYPE,	\n" );
		sql.append("	INTENTION_DATE, INTENTION_BRAND_CODE, INTENTION_SERIES_CODE, INTENTION_MODEL_CODE,	\n" );
		sql.append("	USED_CAR_BRAND_CODE, USED_CAR_SERIES_CODE, USED_CAR_MODEL_CODE, USED_CAR_LICENSE,	\n");
		sql.append("	USED_CAR_VIN, USED_CAR_ASSESS_AMOUNT, USED_CAR_LICENSE_DATE, USED_CAR_MILEAGE,	\n" );
		sql.append("	USED_CAR_DESCRIBE, REdtoRT_TYPE, RESULT, MESSAGE, GROUP_ID, IS_DEL	\n");
		sql.append("		FROM TI_USED_CAR_REPLACEMENT_INTENTION_DETAIL_DCS	\n");
		sql.append("	 		WHERE 1=1	\n" );
		List<Object> params = new ArrayList<>();
		sql.append("AND GROUP_ID=?");
		params.add(groupId);
		List<Map> list = OemDAOUtil.findAll(sql.toString(), params);
		List<TiUsedCarReplacementIntentionDetailBDTO> dtoList=null;
		TiUsedCarReplacementIntentionDetailBDTO dto=null;
		if (list!=null && list.size()>0) {
			dtoList=new ArrayList<TiUsedCarReplacementIntentionDetailBDTO>();
			for(int i=0;i<list.size();i++){
				dto =new TiUsedCarReplacementIntentionDetailBDTO();
				dto.setId(Long.parseLong(CommonUtils.checkNull(list.get(i).get("ID")), 0));
	        	dto.setItemId(CommonUtils.checkNull(list.get(i).get("ITEM_ID")));
	        	dto.setDealerCode(CommonUtils.checkNull(list.get(i).get("DEALER_CODE")));
	        	dto.setCustomerNo(CommonUtils.checkNull(list.get(i).get("CUSTOMER_NO")));
	        	dto.setCustomerProvince(CommonUtils.checkNull(list.get(i).get("CUSTOMER_PROVINCE")));
	        	dto.setCustomerCity(CommonUtils.checkNull(list.get(i).get("CUSTOMER_CITY")));
	        	dto.setCustomerType(CommonUtils.checkNull(list.get(i).get("CUSTOMER_TYPE")));
	        	dto.setIntentionDate(CommonUtils.checkNull(list.get(i).get("INTENTION_DATE")));
	        	dto.setIntentionBrandCode(CommonUtils.checkNull(list.get(i).get("INTENTION_BRAND_CODE")));
	        	dto.setIntentionSeriesCode(CommonUtils.checkNull(list.get(i).get("INTENTION_SERIES_CODE")));
	        	dto.setIntentionModelCode(CommonUtils.checkNull(list.get(i).get("INTENTION_MODEL_CODE")));
	        	dto.setUsedCarBrandCode(CommonUtils.checkNull(list.get(i).get("USED_CAR_BRAND_CODE")));
	        	dto.setUsedCarSeriesCode(CommonUtils.checkNull(list.get(i).get("USED_CAR_SERIES_CODE")));
	        	dto.setUsedCarModelCode(CommonUtils.checkNull(list.get(i).get("USED_CAR_MODEL_CODE")));
	        	dto.setUsedCarLicense(CommonUtils.checkNull(list.get(i).get("USED_CAR_LICENSE")));
	        	dto.setUsedCarVin(CommonUtils.checkNull(list.get(i).get("USED_CAR_VIN")));
	        	dto.setUsedCarAssessAmount(CommonUtils.checkNull(list.get(i).get("USED_CAR_ASSESS_AMOUNT")));
	        	dto.setUsedCarLicenseDate(CommonUtils.checkNull(list.get(i).get("USED_CAR_LICENSE_DATE")));
	        	dto.setUsedCarMileage(CommonUtils.checkNull(list.get(i).get("USED_CAR_MILEAGE")));
	        	dto.setUsedCarDescribe(CommonUtils.checkNull(list.get(i).get("USED_CAR_DESCRIBE")));
	        	dto.setReportType(CommonUtils.checkNull(list.get(i).get("REdtoRT_TYPE")));
	        	dto.setResult(Integer.parseInt(CommonUtils.checkNull(list.get(i).get("RESULT")),0));
	        	dto.setMessage(CommonUtils.checkNull(list.get(i).get("MESSAGE")));
	        	dto.setGroupId(CommonUtils.checkNull(list.get(i).get("GROUP_ID")));
	        	dto.setIsDel(Integer.parseInt(CommonUtils.checkNull(list.get(i).get("IS_DEL")),0));
				dtoList.add(dto);
			}
			
		}
        return dtoList;
	}
	/**
	 * 查询物料是否有效
	 */
	public List<Map> getMaterialInfo(String groupCode,int groupLevel) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("  SELECT * from tm_vhcl_material_group where 1=1");
		List<Object> params = new ArrayList<>();
		sqlSb.append(" and  GROUP_CODE=?");
		params.add(groupCode);
		sqlSb.append(" and  GROUP_LEVEL=?");
		params.add(groupLevel);
		sqlSb.append(" and  STATUS=?");
		params.add(OemDictCodeConstants.STATUS_ENABLE);
		List<Map> applyList = OemDAOUtil.findAll(sqlSb.toString(), params);
		return applyList;
	}
	/**
	* @Description: TODO(根据下端entityCode查询上端dealerCode 区分销售和售后,此方法为销售) 
	* @param @param dealerCode 下端entityCode
	* @param @return 上端dealerCode
	 */
	public String getSaDcsDealerCode1(String entityCode) throws Exception {
		String dealerCode = "";
		if (Utility.testIsNotNull(entityCode)){
			StringBuffer sql= new StringBuffer();
			sql.append("SELECT DEALER_ID, DEALER_CODE, DEALER_NAME FROM TM_DEALER A \n");
			sql.append("	WHERE A.COMPANY_ID = \n");
			sql.append("		(SELECT B.COMPANY_ID FROM TM_COMPANY B \n");
			sql.append("			WHERE B.COMPANY_CODE = \n");
			sql.append("				(SELECT DCS_CODE from TI_DEALER_RELATION C \n");
			sql.append("					WHERE C.DMS_CODE = '").append(entityCode).append("') \n");
			sql.append("		)AND A.DEALER_TYPE = ").append(OemDictCodeConstants.DEALER_TYPE_DVS);
			List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
			if (list!=null && list.size()>0) {
				dealerCode = String.valueOf(list.get(0).get("DEALER_CODE"));
			}
		}
		return dealerCode;
	}
	
}
