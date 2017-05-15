package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TmPotentialCustomerDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: DCSBI002CloudImpl 
* @Description: 用户 数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午7:51:59 
*
 */
@Service
public class DCSBI004CloudImpl  extends BaseCloudImpl implements DCSBI004Cloud {

	private static final Logger logger = LoggerFactory.getLogger(DCSBI004CloudImpl.class);
	
	@Override
	public String receiveDate(List<TmPotentialCustomerDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始获取潜客数据 ******************************");
		for (TmPotentialCustomerDTO entry : dtoList) {
			try {
				PotentialCusPO pcPO = new PotentialCusPO();
				BeanUtils.copyProperties(entry, pcPO);
				pcPO.saveIt();
			} catch (Exception e) {
				logger.error("用户数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("*************************** 成功获取潜客数据 ******************************");
		return msg;
	}

}
