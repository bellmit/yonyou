package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS071Dao;
import com.yonyou.dms.DTO.gacfca.DiscountCouponDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SEDCS071CloudImpl  extends BaseCloudImpl implements SEDCS071Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SEDCS071CloudImpl.class);
	
	@Autowired
	SEDCS071Dao dao ;
	
	@Override
	public List<DiscountCouponDTO> receiveData(List<DiscountCouponDTO> dtos) throws Exception {
		logger.info("*************************** 开始获取可用卡券信息******************************");
		List<DiscountCouponDTO> retdtos=new ArrayList<DiscountCouponDTO>();
		try {
			for (DiscountCouponDTO dto : dtos) {
				// 查询DCS端经销商是否存在
				Map<String, Object> dcsDealer = null;
				String dealerCode = "";
				if (Utility.testIsNotNull(dto.getEntityCode())) {
					dcsDealer = dao.getSaDcsDealerCode(dto.getEntityCode());
					dealerCode = dcsDealer.get("DEALER_CODE").toString();
				}
				String vin = dto.getVin();

				logger.info("SEDCS071Cloud 同步接口接收到VIN：" + vin + "和经销商：" + dealerCode);

				// 查询卡券使用状态为使用中跟未使用的个数
				List<Map<String, Object>> listCount = dao.countVehicle(vin);
				int countNum = listCount.size();

				// 当countNum大于1时，下发使用中的卡券，否则下发未使用的卡券
				List<DiscountCouponDTO> list = dao.queryVehicleDto(vin, countNum);
				if (list == null || list.size() <= 0) {
					logger.info("没有卡券信息");
					return null;
				}else{
					logger.info("存在卡券信息，下发");
					retdtos.addAll(list);
				}
			}
		} catch (Exception e) {
			logger.error("获取可用卡券信息失败", e);
			throw new ServiceBizException(e);
		}

		logger.info("*************************** 成功获取获取可用卡券信息 ******************************");
		return retdtos;
	}

}
