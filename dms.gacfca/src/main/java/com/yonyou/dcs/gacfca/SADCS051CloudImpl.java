package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.SADMS051DTO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsBusinessMonthlyPO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS051CloudImpl extends BaseCloudImpl implements SADCS051Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS051CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	public String handleExecutor(List<SADMS051DTO> dto) throws Exception {
		String msg = "1";
		logger.info("====经营月报数据上报接收开始====");
		for (SADMS051DTO vo : dto) {
			try {
				insertVo(vo);
			} catch (Exception e) {
				logger.error("经营月报数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====经营月报数据上报接收结束====");
		return msg;
	}

	private void insertVo(SADMS051DTO vo) {

		Map<String, Object> map = dao.getSeDcsDealerCode(vo.getEntityCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		String dealerName = String.valueOf(map.get("DEALER_NAME"));// 上报经销商信息
		TtVsBusinessMonthlyPO po1 = new TtVsBusinessMonthlyPO();

		LazyList<TtVsBusinessMonthlyPO> list = TtVsBusinessMonthlyPO
				.findBySQL(
						"select * from Tt_Vs_Business_Monthly_dcs where DEALER_CODE=? and BUSINESS_YEAR="
								+ vo.getBusinessYear() + ",and BUSINESS_MONTH=" + vo.getBusinessMonth() + "",
						dealerCode);

		if (null != list && list.size() > 0) {
			for (TtVsBusinessMonthlyPO po : list) {
				po.setString("DEALER_CODE", dealerCode);
				po.setString("DEALER_NAME", dealerName);
				po.setString("CHRYSLER_CAR_IN_NUM", vo.getChryslerCarInNum());
				po.setString("JEEP_CAR_IN_NUM", vo.getJeepCarInNum());
				po.setString("DODGE_CAR_IN_NUM", vo.getDodgeCarInNum());
				po.setString("BBDC_CAR_IN_NUM", vo.getBbdcCarInNum());
				po.setString("CUSTOMER_RETENTION", vo.getCustomerNum());
				po.setString("CUSTOMER_LOYALTY", vo.getLoyalCustomerNum());
				po.setString("WORK_NUMBER", vo.getServiceStationNum());
				po.setString("INTO_FACTORY_NUM", vo.getCarInNumMonth());
				po.setString("RESERVATION", vo.getAppointmentCarNum());
				po.setString("KEEP_FIT_NUM_MONTH", vo.getKeepFitNumMonth());
				po.setString("CLAIM_NUM_MONTH", vo.getClaimNumMonth());
				po.setString("EXPENSE_CUSTOMER_RATE", vo.getExpenseCustomerRate());
				po.setString("OUT_INSURANCE_NUM_MONTH", vo.getOutInsuranceNumMonth());
				po.setString("TARGET_VALUE_MONTH", vo.getTargetValueMonth());
				po.setString("ACTUAR_VALUE_MONTH", vo.getActuarValueMonth());
				po.setString("ACTUAR_VALUE_GROWTH_RATE_MONTH", vo.getActuarValueGrowthRateMonth());
				po.setString("ONCE_REPAIR_RATE", vo.getOnceRepairRate());
				po.setString("PART_SALES_AMOUNT", vo.getPartSalesAmount());
				po.setString("PART_SALES_AMOUNT_GROWTH_RATE", vo.getPartSalesAmountGrowthRate());
				po.setString("PART_STOCK_MONTH", vo.getPartStockMonth());
				po.setString("PART_SATISFY_RATE", vo.getPartSatisfyRate());
				po.setString("PART_TURNOVER_RATE", vo.getPartTurnoverRate());
				po.setString("CUS_COMPLAINT_RATE_MONTH", vo.getCusComplaintRateMonth());
				po.setString("INTERNAL_TRAIN_HOUR", vo.getInternalTrainHour());
				if (null != vo.getSalesCarNum()) {
					po.setString("SALES_CAR_NUM", Double.parseDouble(vo.getSalesCarNum() + ""));
				}
				po.setString("FIRST_INSURANCE_RATE", vo.getFirstInsuranceRate());
				po.setString("IN_REPAIR_INSURANCE_RATE", vo.getInRepairInsuranceRate());
				po.setString("OUT_REPAIR_INSURANCE_RATE", vo.getOutRepairInsuranceRate());
				po.setString("", vo.getCustomerManagerNum());
				po.setString("REPAIR_TECHNICIAN_NUM", vo.getRepairTechnicianNum());
				po.setString("CUSTOMER_MANAGER_NUM", vo.getCustomerKeepRate());
				po.setTimestamp("CREATE_DATE", vo.getCreateDate());
				po.setTimestamp("BUSINESS_MONTH", vo.getBusinessMonth());
				po.setTimestamp("BUSINESS_YEAR", vo.getBusinessYear());
				po.saveIt();

			}

		} else {
			// 插入展厅预测报告数据
			TtVsBusinessMonthlyPO po = new TtVsBusinessMonthlyPO();
			po.setString("DEALER_CODE", dealerCode);
			po.setString("DEALER_NAME", dealerName);
			po.setString("CHRYSLER_CAR_IN_NUM", vo.getChryslerCarInNum());
			po.setString("JEEP_CAR_IN_NUM", vo.getJeepCarInNum());
			po.setString("DODGE_CAR_IN_NUM", vo.getDodgeCarInNum());
			po.setString("BBDC_CAR_IN_NUM", vo.getBbdcCarInNum());
			po.setString("CUSTOMER_RETENTION", vo.getCustomerNum());
			po.setString("CUSTOMER_LOYALTY", vo.getLoyalCustomerNum());
			po.setString("WORK_NUMBER", vo.getServiceStationNum());
			po.setString("INTO_FACTORY_NUM", vo.getCarInNumMonth());
			po.setString("RESERVATION", vo.getAppointmentCarNum());
			po.setString("KEEP_FIT_NUM_MONTH", vo.getKeepFitNumMonth());
			po.setString("CLAIM_NUM_MONTH", vo.getClaimNumMonth());
			po.setString("EXPENSE_CUSTOMER_RATE", vo.getExpenseCustomerRate());
			po.setString("OUT_INSURANCE_NUM_MONTH", vo.getOutInsuranceNumMonth());
			po.setString("TARGET_VALUE_MONTH", vo.getTargetValueMonth());
			po.setString("ACTUAR_VALUE_MONTH", vo.getActuarValueMonth());
			po.setString("ACTUAR_VALUE_GROWTH_RATE_MONTH", vo.getActuarValueGrowthRateMonth());
			po.setString("ONCE_REPAIR_RATE", vo.getOnceRepairRate());
			po.setString("PART_SALES_AMOUNT", vo.getPartSalesAmount());
			po.setString("PART_SALES_AMOUNT_GROWTH_RATE", vo.getPartSalesAmountGrowthRate());
			po.setString("PART_STOCK_MONTH", vo.getPartStockMonth());
			po.setString("PART_SATISFY_RATE", vo.getPartSatisfyRate());
			po.setString("PART_TURNOVER_RATE", vo.getPartTurnoverRate());
			po.setString("CUS_COMPLAINT_RATE_MONTH", vo.getCusComplaintRateMonth());
			po.setString("INTERNAL_TRAIN_HOUR", vo.getInternalTrainHour());

			if (null != vo.getSalesCarNum()) {
				po.setString("SALES_CAR_NUM", Double.parseDouble(vo.getSalesCarNum() + ""));
			}
			po.setString("FIRST_INSURANCE_RATE", vo.getFirstInsuranceRate());
			po.setString("IN_REPAIR_INSURANCE_RATE", vo.getInRepairInsuranceRate());
			po.setString("OUT_REPAIR_INSURANCE_RATE", vo.getOutRepairInsuranceRate());
			po.setString("", vo.getCustomerManagerNum());
			po.setString("REPAIR_TECHNICIAN_NUM", vo.getRepairTechnicianNum());
			po.setString("CUSTOMER_MANAGER_NUM", vo.getCustomerKeepRate());
			po.setTimestamp("CREATE_DATE", vo.getCreateDate());
			po.setTimestamp("BUSINESS_MONTH", vo.getBusinessMonth());
			po.setTimestamp("BUSINESS_YEAR", vo.getBusinessYear());
			po.saveIt();

		}

	}

}
