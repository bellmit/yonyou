package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsNFinancialDTO;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNFinancialPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SOTDCS006Cloud
 * Description: 创建客户信息（金融报价）(DMS新增)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午5:03:37
 * result msg 1：成功 0：失败
 */
@Service
public class SOTDCS006CloudImpl implements SOTDCS006Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsNFinancialDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====创建客户信息（金融报价）(DMS新增)接收开始===="); 
		for (TiDmsNFinancialDTO entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("创建客户信息（金融报价）(DMS新增)数据接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====创建客户信息（金融报价）(DMS新增)接收结束===="); 
		return msg;
	}
	
	public void insertData(TiDmsNFinancialDTO vo) throws Exception {
		// Map<String, Object> map =
		// deCommonDao.getSaDcsDealerCode(vo.getEntityCode());
		// String dealerCode = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		TiDmsNFinancialPO insertPO = new TiDmsNFinancialPO();
		insertPO.setString("UNIQUENESS_ID", vo.getUniquenessId());// DMS客户唯一ID
		insertPO.setInteger("BUY_TYPE", vo.getBuyType());// 购买方式
		insertPO.setDouble("CAR_PRICE", vo.getCarPrice());// 车辆价格
		insertPO.setDouble("FIRST_PAYMENT", vo.getFirstPayment());// 首付款
		insertPO.setDouble("LOAN_SUM", vo.getLoanSum());// 贷款额
		insertPO.setInteger("LOAN_YEAR", vo.getLoanYear());// 贷款年限
		insertPO.setDouble("LOAN_RATE", vo.getLoanRate());// 利率
		insertPO.setDouble("REPAYMENT_MONTH", vo.getRepaymentMonth());// 月还款
		insertPO.setDouble("IS_PRINT", vo.getIsPrint());// 提供报价单
		insertPO.setDouble("ROAD_TOLL", vo.getRoadToll());// 养路费
		insertPO.setDouble("VEHICLE_PURCHASE_TAX", vo.getVehiclePurchaseTax());// 车辆购置附加税
		insertPO.setDouble("CEHICLE_VESSEL_TAX", vo.getVehicleVesselTax());// 车船税
		insertPO.setDouble("LICENSE_PLATE_COST", vo.getLicensePlateCost());// 代办上牌费
		insertPO.setDouble("EX_WAREHOUSE_COST", vo.getExWarehouseCost());// 出库费
		insertPO.setInteger("BOUTIQUE", vo.getBoutique());// 精品
		insertPO.setDouble("INSURANCE_SUM", vo.getInsuranceSum());// 保险总计
		insertPO.setDouble("ESTIMATED_PRICE", vo.getEstimatedPrice());// 置换抵扣金额
		insertPO.setString("SEALER_CODE", vo.getDealerCode());// 经销商代码
		insertPO.setString("DEALER_USER_ID", vo.getDealerUserId());// 销售人员ID
		insertPO.setDate("CREATE_DATE", vo.getCreateDate());// 创建日期
		insertPO.setString("IS_SEND", "0");// 同步标志
		insertPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
		// 插入数据
		insertPO.insert();
	}

}
