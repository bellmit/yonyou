package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.SADMS052DTO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsAbsorptivityPO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS052CloudImpl extends BaseCloudImpl implements SADCS052Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS052CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	@Override
	public String handleExecutor(List<SADMS052DTO> dto) throws Exception {
		String msg = "1";
		logger.info("====吸收率数据上报接收开始====");
		for (SADMS052DTO vo : dto) {
			try {
				insertVo(vo);
			} catch (Exception e) {
				logger.error("吸收率数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====吸收率数据上报接收结束====");
		return msg;
	}

	private void insertVo(SADMS052DTO vo) {

		Map<String, Object> map = dao.getSeDcsDealerCode(vo.getEntityCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		String dealerName = String.valueOf(map.get("DEALER_NAME"));// 上报经销商信息

		LazyList<TtVsAbsorptivityPO> list = TtVsAbsorptivityPO
				.findBySQL("select * from TT_VS_ABSORPTIVITY_dcs where DEALER_CODE=? and ABSORB_YEAR="
						+ vo.getAbsorbYear() + " and ABSORB_MONTH=" + vo.getAbsorbMonth() + "", dealerCode);
		if (null != list && list.size() > 0) {
			for (TtVsAbsorptivityPO po : list) {
				po.setString("DEALER_CODE", dealerCode);
				po.setString("DEALER_NAME", dealerName);
				po.setString("ENTITY_AMOUNT", vo.getEntityAmount());
				po.setString("SERVICE_BUSINESS_AMOUNT", vo.getServiceBusinessAmount());
				po.setString("BP_VALUE_MONTH", vo.getBpValueMonth());
				po.setString("SERVICE_GROSS_PROFIT_RATE", vo.getServiceGrossProfitRate());
				po.setString("ABSORB_YEAR", vo.getAbsorbYear());
				po.setString("DEGREE_BP_VALUE_MONTH", vo.getDegreeBpValueMonth());
				po.setString("DEGREE_JP_VALUE_MONTH", vo.getDegreeJpValueMonth());
				po.setString("JP_VALUE_MONTH", vo.getJpValueMonth());
				po.setString("ABSORB_MONTH", vo.getAbsorbMonth());
				po.setString("ABSORB_RATE", vo.getAbsorbRate());
				po.setString("CUSTOMER_DEPOT_RATE", vo.getCustomerDepotRate());
				po.setString("SERVICE_GROSS_PROFIT", vo.getServiceGrossProfit());
				po.setTimestamp("CREATE_DATE", vo.getCreateDate());
				po.saveIt();
			}

		} else {
			TtVsAbsorptivityPO po = new TtVsAbsorptivityPO();
			po.setString("DEALER_CODE", dealerCode);
			po.setString("DEALER_NAME", dealerName);
			po.setString("ABSORB_YEAR", vo.getAbsorbYear());
			po.setString("ABSORB_MONTH", vo.getAbsorbMonth());
			po.setString("ENTITY_AMOUNT", vo.getEntityAmount());
			po.setString("SERVICE_BUSINESS_AMOUNT", vo.getServiceBusinessAmount());
			po.setString("BP_VALUE_MONTH", vo.getBpValueMonth());
			po.setString("SERVICE_GROSS_PROFIT_RATE", vo.getServiceGrossProfitRate());
			po.setString("ABSORB_YEAR", vo.getAbsorbYear());
			po.setString("DEGREE_BP_VALUE_MONTH", vo.getDegreeBpValueMonth());
			po.setString("DEGREE_JP_VALUE_MONTH", vo.getDegreeJpValueMonth());
			po.setString("JP_VALUE_MONTH", vo.getJpValueMonth());
			po.setString("ABSORB_MONTH", vo.getAbsorbMonth());
			po.setString("ABSORB_RATE", vo.getAbsorbRate());
			po.setString("CUSTOMER_DEPOT_RATE", vo.getCustomerDepotRate());
			po.setString("SERVICE_GROSS_PROFIT", vo.getServiceGrossProfit());
			po.setTimestamp("CREATE_DATE", vo.getCreateDate());
			po.saveIt();
		}

	}

}
