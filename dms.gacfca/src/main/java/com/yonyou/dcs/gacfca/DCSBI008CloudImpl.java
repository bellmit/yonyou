package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TtCustomerIntentDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: DCSBI002CloudImpl 
* @Description: 意向 数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午7:51:59 
*
 */
@Service
public class DCSBI008CloudImpl  extends BaseCloudImpl implements DCSBI008Cloud {

	private static final Logger logger = LoggerFactory.getLogger(DCSBI008CloudImpl.class);
	
	@Override
	public String receiveDate(List<TtCustomerIntentDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始获取意向 数据 ******************************");
		for (TtCustomerIntentDTO entry : dtoList) {
			try {
				TtCusIntentPO po = new TtCusIntentPO();
				BeanUtils.copyProperties(entry, po);
				po.saveIt();
			} catch (Exception e) {
				logger.error("数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("*************************** 成功获取意向 数据 ******************************");
		return msg;
	}

}
