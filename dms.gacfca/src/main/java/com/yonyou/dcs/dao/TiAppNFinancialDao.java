package com.yonyou.dcs.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.DTO.gacfca.TiAppNFinancialDto;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Repository
public class TiAppNFinancialDao extends OemBaseDAO {
	
	/**
	 * 获取经销商业务范围
	 * @param dealerCode
	 * @return
	 */
	public String[] getDealerByGroupId(String[] groupID,String dealerCode) {
		String[] groupIds = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT DISTINCT T1.GROUP_ID FROM TM_DEALER_BUSS t1 , TM_DEALER t2 WHERE  t1.DEALER_ID = t2.DEALER_ID AND T2.DEALER_CODE = '" + dealerCode + "' ");
		if(null!= groupID && groupID.length>0){
			sql.append(" AND T1.GROUP_ID IN( ");
			for (int i = 0; i < groupID.length; i++) {
				if (i == groupID.length - 1) {
					sql.append(" '" + groupID[i] + "'");
				} else {
					sql.append(" '" + groupID[i] + "',");
				}
			}
			sql.append(" ) ");
		}
		System.out.println(sql.toString());
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if(null!= list && list.size()>0){
			groupIds = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				groupIds[i] = CommonUtils.checkNull(list.get(i).get("GROUP_ID"));
			}
		}
		return groupIds;
	}

	/**
	 * 包含groupId
	 * @param groupId
	 * @return
	 * @throws ParseException
	 */
	public LinkedList<TiAppNFinancialDto> queryAppNFinancial(String[] groupId) throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNFinancialDto> list = this.setTiAppNFinancialDtoList(mapList);
		return list;
	}

	/**
	 * 不包含groupId
	 * @return
	 * @throws ParseException
	 */
	public LinkedList<TiAppNFinancialDto> queryAppNFinancial() throws ParseException {
		StringBuffer sql = this.getSql();
		List<Map> mapList = OemDAOUtil.findAll(sql.toString(), null);
		LinkedList<TiAppNFinancialDto> list = this.setTiAppNFinancialDtoList(mapList);
		return list;
	}
	
	private LinkedList<TiAppNFinancialDto> setTiAppNFinancialDtoList(List<Map> list) throws ParseException {
		LinkedList<TiAppNFinancialDto> resultList = new LinkedList<TiAppNFinancialDto>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(list != null && !list.isEmpty()){
			for(Map map : list){
				TiAppNFinancialDto dto = new TiAppNFinancialDto();
				Long financialOfferId = map.get("FINANCIAL_OFFER_ID") == null ? null : Long.parseLong(String.valueOf(map.get("FINANCIAL_OFFER_ID")));
				Long fcaId = map.get("FCA_ID") == null ? null : Long.parseLong(String.valueOf(map.get("FCA_ID")));
				Double carPrice = map.get("CAR_PRICE") == null ? null : Double.parseDouble(String.valueOf(map.get("CAR_PRICE")));
				Double firstPayment = map.get("FIRST_PAYMENT") == null ? null : Double.parseDouble(String.valueOf(map.get("FIRST_PAYMENT")));
				Double exWarehouseCost = map.get("EX_WAREHOUSE_COST") == null ? null : Double.parseDouble(String.valueOf(map.get("EX_WAREHOUSE_COST")));
				Double licensePlateCost = map.get("LICENSE_PLATE_COST") == null ? null : Double.parseDouble(String.valueOf(map.get("LICENSE_PLATE_COST")));
				Double repaymentMonth = map.get("REPAYMENT_MONTH") == null ? null : Double.parseDouble(String.valueOf(map.get("REPAYMENT_MONTH")));
				String dealerCode = map.get("DEALER_CODE") == null ? null : String.valueOf(map.get("DEALER_CODE"));
				Double loanRate = map.get("LOAN_RATE") == null ? null : Double.parseDouble(String.valueOf(map.get("LOAN_RATE")));
				Double insuranceSum = map.get("INSURANCE_SUM") == null ? null : Double.parseDouble(String.valueOf(map.get("INSURANCE_SUM")));
				Double loanSum = map.get("LOAN_SUM") == null ? null : Double.parseDouble(String.valueOf(map.get("LOAN_SUM")));
				Double vehiclePurchaseTax = map.get("VEHICLE_PURCHASE_TAX") == null ? null : Double.parseDouble(String.valueOf(map.get("VEHICLE_PURCHASE_TAX")));
				Integer boutique = map.get("BOUTIQUE") == null ? null : Integer.parseInt(String.valueOf(map.get("BOUTIQUE")));
				Integer loanYear = map.get("LOAN_YEAR") == null ? null : Integer.parseInt(String.valueOf(map.get("LOAN_YEAR")));
				Double estimatedPrice = map.get("ESTIMATED_PRICE") == null ? null : Double.parseDouble(String.valueOf(map.get("ESTIMATED_PRICE")));
				Double roadToll = map.get("ROAD_TOLL") == null ? null : Double.parseDouble(String.valueOf(map.get("ROAD_TOLL")));
				Double isPrint = map.get("IS_PRINT") == null ? null : Double.parseDouble(String.valueOf(map.get("IS_PRINT")));
				Date createDate = map.get("CREATE_DATE") == null ? null : sdf.parse(String.valueOf(map.get("CREATE_DATE")));
				Integer buyType = map.get("BUY_TYPE") == null ? null : Integer.parseInt(String.valueOf(map.get("BUY_TYPE")));
				Double vehicleVesselTax = map.get("VEHICLE_VESSEL_TAX") == null ? null : Double.parseDouble(String.valueOf(map.get("VEHICLE_VESSEL_TAX")));
				String dealerUserId = map.get("DEALER_USER_ID") == null ? null : String.valueOf(map.get("DEALER_USER_ID"));
				String uniquenessID = map.get("UNIQUENESS_ID") == null ? null : String.valueOf(map.get("UNIQUENESS_ID"));
				
				dto.setFinancialOfferId(financialOfferId);
				dto.setBoutique(boutique);
				dto.setBuyType(buyType);
				dto.setCarPrice(carPrice);
				dto.setCreateDate(createDate);
				dto.setDealerCode(dealerCode);
				dto.setDealerUserId(dealerUserId);
				dto.setEstimatedPrice(estimatedPrice);
				dto.setExWarehouseCost(exWarehouseCost);
				dto.setFcaId(fcaId);
				dto.setFirstPayment(firstPayment);
				dto.setInsuranceSum(insuranceSum);
				dto.setIsPrint(isPrint);
				dto.setLicensePlateCost(licensePlateCost);
				dto.setLoanRate(loanRate);
				dto.setLoanSum(loanSum);
				dto.setLoanYear(loanYear);
				dto.setRepaymentMonth(repaymentMonth);
				dto.setRoadToll(roadToll);
				dto.setUniquenessID(uniquenessID);
				dto.setVehiclePurchaseTax(vehiclePurchaseTax);
				dto.setVehicleVesselTax(vehicleVesselTax);
				resultList.add(dto);
			}
		}
		return resultList;
	}
	
	private StringBuffer getSql(){
		StringBuffer sql= new StringBuffer();
		sql.append("SELECT ANFC.FINANCIAL_OFFER_ID, \n" );
		sql.append("       ANFC.FCA_ID, \n" );
		sql.append("       ANFC.BUY_TYPE, \n" );
		sql.append("       ANFC.CAR_PRICE, \n" );
		sql.append("       ANFC.FIRST_PAYMENT, \n" );
		sql.append("       ANFC.LOAN_SUM, \n" );
		sql.append("       ANFC.LOAN_YEAR, \n" );
		sql.append("       ANFC.LOAN_RATE, \n" );
		sql.append("       ANFC.REPAYMENT_MONTH, \n" );
		sql.append("       ANFC.IS_PRINT, \n" );
		sql.append("       ANFC.ROAD_TOLL, \n" );
		sql.append("       ANFC.VEHICLE_PURCHASE_TAX, \n" );
		sql.append("       ANFC.VEHICLE_VESSEL_TAX, \n" );
		sql.append("       ANFC.LICENSE_PLATE_COST, \n" );
		sql.append("       ANFC.EX_WAREHOUSE_COST, \n" );
		sql.append("       ANFC.BOUTIQUE, \n" );
		sql.append("       ANFC.INSURANCE_SUM, \n" );
		sql.append("       ANFC.ESTIMATED_PRICE, \n" );
		sql.append("       ANFC.DEALER_USER_ID, \n" );
		sql.append("       ANFC.UNIQUENESS_ID, \n" );
		sql.append("       DR.DMS_CODE AS DEALER_CODE, \n" );
		sql.append("       DATE_FORMAT(ANFC.CREATE_DATE,'%Y-%m-%d %H:%i:%S') AS CREATE_DATE \n" );
		sql.append("   FROM TI_APP_N_FINANCIAL ANFC \n" );
		sql.append("       LEFT JOIN TI_DEALER_RELATION DR ON ANFC.DEALER_CODE = DR.DCS_CODE \n" );
		sql.append("   WHERE ANFC.IS_SEND = 0 OR ANFC.IS_SEND IS NULL \n" );
		return sql;
	}

}
