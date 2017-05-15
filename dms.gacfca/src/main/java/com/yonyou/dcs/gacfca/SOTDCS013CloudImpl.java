package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsUSalesQuotasDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsUSalesQuotasPO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SOTDCS013CloudImpl extends BaseCloudImpl implements SOTDCS013Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsUSalesQuotasDto> dto) throws Exception {
		String msg = "1";
		logger.info("====销售人员分配信息接收开始===="); 
		for (TiDmsUSalesQuotasDto entry : dto) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("销售人员分配信息数据接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====销售人员分配信息接收结束===="); 
		return msg;
	}

	public void insertData(TiDmsUSalesQuotasDto vo) throws Exception {
		/*
		 * Map<String, Object> map =
		 * deCommonDao.getSaDcsDealerCode(vo.getEntityCode()); String dealerCode
		 * = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		 */
		TiDmsUSalesQuotasPO po = new TiDmsUSalesQuotasPO();
		if (null != vo.getFCAID()) {
			po.setLong("FCA_ID", new Long(vo.getFCAID()));
		}
		po.setString("UNIQUENESS_ID", vo.getUniquenessID());
		po.setString("OLD_DEALER_USER_ID", vo.getOldDealerUserID());
		po.setString("DEALER_USER_ID", vo.getDealerUserID());
		po.setString("DEALER_CODE", vo.getDealerCode());
		po.setString("IS_SEND", "0");
		po.setTimestamp("UPDATE_DATE", vo.getUpdateDate());
		po.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);
		po.insert();
	}
	
	

}
