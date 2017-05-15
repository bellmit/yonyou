package com.yonyou.dcs.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;

@SuppressWarnings("unchecked")
@Repository
public class RetailInformationChangeDao extends OemBaseDAO {

	/**
	 * 车辆ID信息查询
	 * @param 
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getVehicleIdByVin(String vin) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT VEHICLE_ID,VIN  \n");
		sql.append(" FROM TM_VEHICLE_DEC \n");
		sql.append(" WHERE 1=1 ");
		if (!StringUtils.isNullOrEmpty(vin)){
			sql.append(" AND VIN = '"+vin+"' \n");
			sql.append(" AND LIFE_CYCLE = '"+OemDictCodeConstants.LIF_CYCLE_05+"' " ) ;
		}
		return OemDAOUtil.findFirst(sql.toString(), null);
	}

	public void insertSalesReport(String vehicleId) throws Exception {
		StringBuffer sqlStr= new StringBuffer();
		List<Object> params = new ArrayList<Object>();	
		sqlStr.append(" INSERT INTO TT_VS_SALES_REPORT_HISTORY_DCS \n" );
		sqlStr.append("		SELECT TVSR.REPORT_ID, TVSR.OEM_COMPANY_ID, TVSR.DLR_COMPANY_ID, \n" );
		sqlStr.append("				TVSR.DEALER_ID, TVSR.VEHICLE_ID, TVSR.VEHICLE_NO, TVSR.CONTRACT_NO, \n" );
		sqlStr.append("				TVSR.INVOICE_DATE, TVSR.INVOICE_NO, TVSR.INSURANCE_COMPANY, TVSR.INSURANCE_DATE, \n" );
		sqlStr.append("				TVSR.CONSIGNATION_DATE, TVSR.MILES, TVSR.PAYMENT, TVSR.VEHICLE_TYPE, TVSR.SALES_MAN, \n" );
		sqlStr.append("				TVSR.PRICE, TVSR.MEMO, TVSR.CTM_ID, TVSR.STATUS, TVSR.SALES_DATE, TVSR.IS_OLD_CTM, \n" );
		sqlStr.append("				TVSR.CREATE_DATE, TVSR.CREATE_BY, TVSR.UPDATE_DATE, TVSR.UPDATE_BY, TVSR.VER, \n" );
		sqlStr.append("				TVSR.IS_ARC, TVSR.IS_DEL, TVSR.LOAN_ORG, TVSR.INSTALLMENT_NUMBER, TVSR.INSTALLMENT_AMOUNT, \n" );
		sqlStr.append("				TVSR.IS_OCR, TVSR.FIRST_PERMENT_RATIO, TVSR.LOAN_RATE, TVSR.IS_MODIFY, TVSR.IS_SCAN, \n" );
		sqlStr.append("				TVSR.RESULT_VALUE, TVSR.INVOICE_TYPE_CODE, TVSR.INVOICE_AMOUNT, TVSR.INVOICE_CHARGE_TYPE, \n" );
		sqlStr.append("				TVSR.INVOICE_CUSTOMER, TVSR.INVOICE_WRITER, TVSR.TRANSACTOR, TVSR.BUSINESS_ID \n" );
		sqlStr.append("					FROM TT_VS_SALES_REPORT TVSR where TVSR.VEHICLE_ID= '"+vehicleId+"' AND TVSR.STATUS=? \n" );
		params.add(OemDictCodeConstants.STATUS_ENABLE);
		OemDAOUtil.execBatchPreparement(sqlStr.toString(), params);
	}
	
	/**
	 * 车辆实销客户信息查询
	 * @param vehicleId
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getCustomInfo(String vehicleId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  CTM_ID  \n");
		sql.append(" FROM TT_VS_SALES_REPORT  ");
		sql.append(" WHERE 1=1 ");
		if (!StringUtils.isNullOrEmpty(vehicleId)){
			sql.append(" AND VEHICLE_ID = '"+vehicleId+"' \n");
		}
		sql.append(" AND STATUS = "+OemDictCodeConstants.STATUS_ENABLE+" \n");
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	
	/**
	 * 将变更前的客户信息保存到历史表
	 * @param ctmId
	 */
	public void insertCustomInfo(String ctmId) {
		StringBuffer sqlStr= new StringBuffer();
		List<Object> params = new ArrayList<Object>();	
		sqlStr.append(" INSERT INTO TT_VS_CUSTOMER_HISTORY_DCS \n" );
		sqlStr.append("		SELECT TVC.CTM_ID, TVC.DLR_COMPANY_ID, TVC.OEM_COMPANY_ID, TVC.CTM_TYPE, \n" );
		sqlStr.append("			TVC.CTM_NAME, TVC.SEX, TVC.CARD_TYPE, TVC.CARD_NUM, TVC.MAIN_PHONE, TVC.OTHER_PHONE, \n" );
		sqlStr.append("			TVC.EMAIL, TVC.POST_CODE, TVC.BIRTHDAY, TVC.CTM_FORM, TVC.INCOME, TVC.EDUCATION, \n" );
		sqlStr.append("			TVC.INDUSTRY, TVC.IS_MARRIED, TVC.PROFESSION, TVC.JOB, TVC.COMMUNICATE_TYPE, \n" );
		sqlStr.append("			TVC.IS_OWNER, TVC.COUNTRY, TVC.PROVINCE, TVC.CITY, TVC.TOWN, TVC.ADDRESS, TVC.STATUS, \n" );
		sqlStr.append("			TVC.COMPANY_NAME, TVC.COMPANY_S_NAME, TVC.COMPANY_PHONE, TVC.LEVEL_ID, TVC.KIND, \n" );
		sqlStr.append("			TVC.VEHICLE_NUM, TVC.WEDDING_DAY, TVC.REFERENCE, TVC.REFERENCE_TEL, TVC.SALES_ADVISER, \n" );
		sqlStr.append("			TVC.FAX, TVC.BEST_CONTACT_TYPE, TVC.BEST_CONTACT_TIME, TVC.HOBBY, TVC.INDUSTRY_FIRST, \n" );
		sqlStr.append("			TVC.INDUSTRY_SECOND, TVC.VOCATION_TYPE, TVC.POSITION_NAME, TVC.IS_FIRST_BUY, \n" );
		sqlStr.append("			TVC.IS_PERSON_DRIVE_CAR, TVC.BUY_PURPOSE, TVC.CHOICE_REASON, TVC.BUY_REASON, \n" );
		sqlStr.append("			TVC.MEDIA_TYPE, TVC.MEDIA_DETAIL, TVC.FAMILY_MEMBER, TVC.IM, TVC.EXIST_BRAND, \n" );
		sqlStr.append("			TVC.EXIST_SERIES, TVC.EXIST_MODEL, TVC.PURCHASE_YEAR, TVC.MILEAGE, TVC.PURCHASE_DIFFER, \n" );
		sqlStr.append("			TVC.EDITOR, TVC.INTENTION_MODEL, TVC.INTENTION_COLOR, TVC.INTENTION_REMARK, TVC.OTHER_NEED, \n" );
		sqlStr.append("			TVC.BUDGET_AMOUNT, TVC.INTENTION_BRAND, TVC.INTENTION_SERIES, TVC.CREATE_DATE, TVC.CREATE_BY, \n" );
		sqlStr.append("			TVC.UPDATE_DATE, TVC.UPDATE_BY, TVC.VER, TVC.IS_ARC, TVC.IS_DEL, TVC.TMP_VIN, TVC.TMP_NO, \n" );
		sqlStr.append("			TVC.OWNER_NO, TVC.IS_DCC_OFFER, TVC.IS_ORDER, TVC.CTM_NO \n" );
		sqlStr.append("				FROM TT_VS_CUSTOMER TVC WHERE TVC.CTM_ID=? AND TVC.STATUS=? \n" );
		params.add(ctmId);
		params.add(OemDictCodeConstants.STATUS_ENABLE);
		OemDAOUtil.execBatchPreparement(sqlStr.toString(), params);
	}
	
	/**
	 * 二手车信息查询
	 * @param  vin
	 * @return 
	 * @throws Exception
	 */
	public Map<String, Object> getSecondCarById(String permutedVin) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT LM_ID  \n");
		sql.append(" FROM TT_VS_SECOND_CAR \n");
		sql.append(" WHERE 1=1 ");
		if (!StringUtils.isNullOrEmpty(permutedVin)){
			sql.append(" AND VIN = '"+permutedVin+"' \n");
		}
		return OemDAOUtil.findFirst(sql.toString(), null);
	}
	
	/**
	 * 将变更前的二手车信息保存到历史表
	 * @param vin
	 */
	public void copySecondCarToHistory(String vin){
		List<Object> params = new ArrayList<Object>();	
		StringBuffer sql =new StringBuffer();
		sql.append("INSERT INTO TT_VS_SECOND_CAR_HISTORY_DCS \n" );
		sql.append("	SELECT TVSC.LM_ID, TVSC.CTM_ID, TVSC.SERIES_NAME, TVSC.MODEL_NAME, TVSC.CUSTOMER_TYPE, \n" );
		sql.append("		TVSC.EMISSIONS, TVSC.LICENSE, TVSC.PRODUCTION_DATE, TVSC.BUY_DATE, TVSC.VIN, \n" );
		sql.append("		TVSC.ENGINE_NUM, TVSC.COLOR_NAME, TVSC.GEAR_FORM, TVSC.MILEAGE, TVSC.FUEL_TYPE, \n" );
		sql.append("		TVSC.HBBJ, TVSC.VEHICLE_ALLOCATION, TVSC.USE_TYPE, TVSC.TRAFFIC_INSURE_INFO, \n" );
		sql.append("		TVSC.DRIVING_LICENSE, TVSC.BUSINESS, TVSC.REGISTRY, TVSC.ANNUAL_INSPECTION_DATE, \n" );
		sql.append("		TVSC.ORIGIN_CERTIFICATE, TVSC.SCRAP_DATE, TVSC.PURCHASE_TAX, TVSC.TRAFFIC_INSURE_DATA, \n" );
		sql.append("		TVSC.VEHICLE_AND_VESSEL_TAX, TVSC.REMARK, TVSC.CREATE_DATE, TVSC.CREATE_BY, TVSC.UPDATE_DATE, \n" );
		sql.append("		TVSC.UPDATE_BY, TVSC.VER, TVSC.IS_ARC, TVSC.IS_DEL, TVSC.IS_ASSESSED, TVSC.ASSESSED_PRICE, \n" );
		sql.append("		TVSC.OLD_CAR_PURCHASE, TVSC.OLD_BRAND_CODE, TVSC.OLD_SERIES_CODE, TVSC.IS_PERMUTED, \n" );
		sql.append("		TVSC.FILE_OLD_A, TVSC.FILE_URLOLD_A, TVSC.OLD_MODEL_CODE FROM TT_VS_SECOND_CAR TVSC WHERE \n" );
		sql.append("			TVSC.VIN= ? \n");
		params.add(vin);
		OemDAOUtil.execBatchPreparement(sql.toString(), params);
	}

}
