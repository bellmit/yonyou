package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.po.TiLcreplaceOrderPO;
import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.DTO.basedata.SADMS063Dto;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SADCS063CloudImpl extends BaseCloudImpl implements SADCS063Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS063CloudImpl.class);
	
	@Autowired
	SaDcs056Dao dao;
	
	@Override
	public String handleExecutor(List<SADMS063Dto> dtos) throws Exception {
		String msg = "1";
		beginDbService();
		try {
			logger.info("*************** SADCS063Cloud 留存订单上报接收开始 *******************");
			for (SADMS063Dto dto : dtos) {
				insertData(dto);
			}
			logger.info("*************** SADCS063Cloud 留存订单上报完成 ********************");
			dbService.endTxn(true);
		} catch (Exception e) {
			logger.error("*************** SADCS063Cloud 留存订单上报异常 *****************", e);
			msg = "0";
			dbService.endTxn(false);
		} finally{
			Base.detach();
			dbService.clean();
		}
		return msg;
	}
	/**
	 * 接收上报上来的留存订单信息
	 * @param dto
	 * @throws Exception
	 */
	private void insertData(SADMS063Dto dto) {
			
		TiLcreplaceOrderPO tlc = new TiLcreplaceOrderPO();

		if (Utility.testIsNotNull(dto.getDealerCode())) {
			Map<String, Object> dcsInfoMap = dao.getSaDcsDealerCode(dto.getDealerCode());
			String dealerCode = CommonUtils.checkNull(dcsInfoMap.get("DEALER_CODE"));
			tlc.setString("DEALER_CODE", dealerCode);
		}
		tlc.setString("SERIES_CODE", dto.getSeriesCode());// 车系代码
		tlc.setString("BRAND_CODE", dto.getBrandCode());// 品牌代码
		if (Utility.testIsNotNull(CommonUtils.checkNull(dto.getSoStatus()))) {
			tlc.setInteger("SO_STATUS", dto.getSoStatus());// 订单状态
		}
		if (Utility.testIsNotNull(CommonUtils.checkNull(dto.getSalesLcreplace()))) {
			tlc.setInteger("SALES_LCREPLACE", dto.getSalesLcreplace());// 留存数量
		}
		tlc.setDate("VIN", dto.getSubmitTime());// 上报时间

		tlc.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
		tlc.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
		tlc.insert();
	}
}
