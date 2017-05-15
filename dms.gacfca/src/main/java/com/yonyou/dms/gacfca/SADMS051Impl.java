package com.yonyou.dms.gacfca;

import java.util.Calendar;
import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.SADMS051DTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtBusinessMonthPO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description  经营月报表上报
 * @author Administrator
 *
 */
@Service
public class SADMS051Impl implements SADMS051{

	final Logger logger = Logger.getLogger(SADMS051Impl.class);

	/**
	 * @description 经营月报表上报
	 * @param dealerCode
	 * @param year
	 * @param month
	 */
	@Override
	public LinkedList<SADMS051DTO> getSADMS051(String dealerCode, String year, String month) {
		logger.info("==========SADMS051Impl执行===========");
		try{
			if(!Utility.testString(year) || !Utility.testString(month)){
				Calendar calm = Calendar.getInstance();		    
				if (calm.get(Calendar.DATE) != 5){
					logger.debug("当前日期，不允许上报");
					return null;
				}	
				//		    //获取上月上报
				calm.add(Calendar.MONTH,-1);
				year = String.valueOf(calm.get(Calendar.YEAR));
				month = String.valueOf(calm.get(Calendar.MONTH) + 1);
				if (month.length() == 1){
					month = "0"+month;	
				}
			}
			//定义VO
			LinkedList<SADMS051DTO> resultList = new LinkedList<SADMS051DTO>();
			//查询当月或则手工填写后的月份数据 （仅限本月月尾 或则补录上月数据）
			logger.debug("from TtBusinessMonthPO DEALER_CODE = "+dealerCode+" and BUSINESS_YEAR = "+year+" and BUSINESS_MONTH = "+month+" and D_KEY = "+CommonConstants.D_KEY);
			LazyList<TtBusinessMonthPO> ttBusinessMonthPOs = TtBusinessMonthPO.findBySQL("DEALER_CODE = ? and BUSINESS_YEAR = ? and BUSINESS_MONTH = ? and D_KEY = ?",
					dealerCode,year,month,CommonConstants.D_KEY);
			if (ttBusinessMonthPOs !=null && !ttBusinessMonthPOs.isEmpty()) {
				TtBusinessMonthPO ttBusinessMonthPO = ttBusinessMonthPOs.get(0);
				SADMS051DTO SADMS051Dto = new SADMS051DTO();
				SADMS051Dto.setEntityCode(dealerCode);
				SADMS051Dto.setEntityName(ttBusinessMonthPO.getString("ENTITY_NAME"));
				SADMS051Dto.setBusinessYear(year);
				SADMS051Dto.setBusinessMonth(month);
				SADMS051Dto.setChryslerCarInNum(ttBusinessMonthPO.getLong("NT_CHRYSLER_CAR_IN_NUM"));
				SADMS051Dto.setJeepCarInNum(ttBusinessMonthPO.getLong("JEEP_CAR_IN_NUM"));
				SADMS051Dto.setDodgeCarInNum(ttBusinessMonthPO.getLong("DODGE_CAR_IN_NUM"));
				SADMS051Dto.setBbdcCarInNum(ttBusinessMonthPO.getLong("BBDC_CAR_IN_NUM"));
				SADMS051Dto.setCustomerNum(ttBusinessMonthPO.getLong("CUSTOMER_NUM"));
				SADMS051Dto.setLoyalCustomerNum(ttBusinessMonthPO.getLong("LOYAL_CUSTOMER_NUM"));
				SADMS051Dto.setCustomerKeepRate(ttBusinessMonthPO.getDouble("CUSTOMER_KEEP_RATE"));
				SADMS051Dto.setServiceStationNum(ttBusinessMonthPO.getLong("SERVICE_STATION_NUM"));
				SADMS051Dto.setCarInNumMonth(ttBusinessMonthPO.getLong("CAR_IN_NUM_MONTH"));
				SADMS051Dto.setAppointmentCarNum(ttBusinessMonthPO.getLong("APPOINTMENT_CAR_NUM"));
				SADMS051Dto.setAppointmentCarRate(ttBusinessMonthPO.getDouble("APPOINTMENT_CAR_RATE"));
				SADMS051Dto.setKeepFitNumMonth(ttBusinessMonthPO.getLong("KEEP_FIT_NUM_MONTH"));
				SADMS051Dto.setKeepFitRateMonth(ttBusinessMonthPO.getDouble("KEEP_FIT_RATE_MONTH"));
				SADMS051Dto.setClaimNumMonth(ttBusinessMonthPO.getLong("CLAIM_NUM_MONTH"));
				SADMS051Dto.setExpenseCustomerRate(ttBusinessMonthPO.getDouble("EXPENSE_CUSTOMER_RATE"));
				SADMS051Dto.setOutInsuranceNumMonth(ttBusinessMonthPO.getLong("OUT_INSURANCE_NUM_MONTH"));
				SADMS051Dto.setBenefitCustomerRate(ttBusinessMonthPO.getDouble("BENEFIT_CUSTOMER_RATE"));
				SADMS051Dto.setTargetValueMonth(ttBusinessMonthPO.getDouble("TARGET_VALUE_MONTH"));
				SADMS051Dto.setActuarValueMonth(ttBusinessMonthPO.getDouble("ACTUAR_VALUE_MONTH"));
				SADMS051Dto.setActuarValueGrowthRateMonth(ttBusinessMonthPO.getDouble("ACTUAR_VALUE_GROWTH_RATE_MONTH"));
				SADMS051Dto.setOnceRepairRate(ttBusinessMonthPO.getDouble("ONCE_REPAIR_RATE"));
				SADMS051Dto.setActuarCusPirce(ttBusinessMonthPO.getDouble("ACTUAR_CUS_PIRCE"));
				SADMS051Dto.setPartSalesAmount(ttBusinessMonthPO.getDouble("PART_SALES_AMOUNT"));
				SADMS051Dto.setPartSalesAmountGrowthRate(ttBusinessMonthPO.getDouble("PART_SALES_AMOUNT_GROWTH_RATE"));
				SADMS051Dto.setPartStockMonth(ttBusinessMonthPO.getDouble("PART_STOCK_MONTH"));
				SADMS051Dto.setPartSatisfyRate(ttBusinessMonthPO.getDouble("PART_SATISFY_RATE"));
				SADMS051Dto.setPartTurnoverRate(ttBusinessMonthPO.getDouble("PART_TURNOVER_RATE"));
				SADMS051Dto.setCusComplaintRateMonth(ttBusinessMonthPO.getDouble("CUS_COMPLAINT_RATE_MONTH"));
				SADMS051Dto.setInternalTrainHour(ttBusinessMonthPO.getDouble("INTERNAL_TRAIN_HOUR"));
				SADMS051Dto.setSalesCarNum(ttBusinessMonthPO.getLong("SALES_CAR_NUM"));
				SADMS051Dto.setFirstInsuranceRate(ttBusinessMonthPO.getDouble("FIRST_INSURANCE_RATE"));
				SADMS051Dto.setInRepairInsuranceRate(ttBusinessMonthPO.getDouble("IN_REPAIR_INSURANCE_RATE"));
				SADMS051Dto.setOutRepairInsuranceRate(ttBusinessMonthPO.getDouble("OUT_REPAIR_INSURANCE_RATE"));
				SADMS051Dto.setRepairTechnicianNum(ttBusinessMonthPO.getLong("REPAIR_TECHNICIAN_NUM"));
				SADMS051Dto.setCustomerManagerNum(ttBusinessMonthPO.getLong("CUSTOMER_MANAGER_NUM"));
				SADMS051Dto.setCreateBy(ttBusinessMonthPO.getLong("CREATE_BY"));
				SADMS051Dto.setCreateDate(ttBusinessMonthPO.getDate("CREATE_AT"));
				SADMS051Dto.setUpdateBy(ttBusinessMonthPO.getLong("UPDATE_BY"));
				SADMS051Dto.setUpdateDate(ttBusinessMonthPO.getDate("UPDATE_AT"));
				resultList.add(SADMS051Dto);
			}
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SADMS051Impl结束===========");
		}
	}
}
