package com.infoeai.eai.dao.salesOfTool;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * 金融报价(DMS新增)
 * @author luoyang
 *
 */
@Repository
public class TiDmsNFinancialDao extends OemBaseDAO {

	public Map<String, Object> beginAndEndDate() {
		StringBuffer sql=new StringBuffer();
		sql.append("select min(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as BEGIN_DATE,max(IFNULL(DATE_FORMAT(CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'')) as END_DATE  ");
		sql.append(" from TI_DMS_N_FINANCIAL_DCS WHERE 1=1 and is_send=0");
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		if(mapList != null && !mapList.isEmpty()){
			return mapList.get(0);
		}
		return null;
	}

	public List<Map> searchDataForJSON() {
		StringBuffer sql=new StringBuffer();
		sql.append("select UNIQUENESS_ID as UniquenessID, IFNULL(BUY_TYPE,0) as BuyType, CAR_PRICE as CarPrice, IFNULL(FIRST_PAYMENT,0) as FirstPayment, IFNULL(LOAN_SUM,0) as LoanSum, LOAN_YEAR as LoanYear, IFNULL(LOAN_RATE,0) as LoanRate, ");
		sql.append("REPAYMENT_MONTH as RepaymentMonth, IS_PRINT as IsPrint, IFNULL(ROAD_TOLL,0) as RoadToll, IFNULL(VEHICLE_PURCHASE_TAX,0) as VehiclePurchaseTax, IFNULL(VEHICLE_VESSEL_TAX,0) as VehicleVesselTax, IFNULL(LICENSE_PLATE_COST,0) as LicensePlateCost,");
		sql.append("IFNULL(EX_WAREHOUSE_COST,0) as EXWarehouseCost, IFNULL(BOUTIQUE,0) as Boutique, IFNULL(INSURANCE_SUM,0) as InsuranceSum, IFNULL(ESTIMATED_PRICE,0) as EstimatedPrice,Dealer_User_ID as DealerUserID, TC.LMS_CODE as DealerCode,");
		sql.append("IFNULL(DATE_FORMAT(TDNF.CREATE_DATE,'%Y-%m-%d %H:%i:%s'),'') as CreateDate ");
		//sql.append("IFNULL(DATE_FORMAT(UPDATE_DATE,'yyyy-mm-dd hh24:mi:ss'),'') as UPDATE_DATE ");
		sql.append("  FROM TI_DMS_N_FINANCIAL_DCS TDNF LEFT JOIN TI_DEALER_RELATION DR ON DR.dms_code = TDNF.dealer_code  LEFT JOIN TM_COMPANY TC ON TC.company_code = DR.dcs_code where 1=1 and is_send=0");
		return null;
	}

}
