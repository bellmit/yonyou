package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TtSalesPromotionPlanDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanDPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: DCSBI002CloudImpl 
* @Description: 销售计划 数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午7:51:59 
*
 */
@Service
public class DCSBI006CloudImpl  extends BaseCloudImpl implements DCSBI006Cloud {

	private static final Logger logger = LoggerFactory.getLogger(DCSBI006CloudImpl.class);
	
	@Override
	public String receiveDate(List<TtSalesPromotionPlanDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始获取销售计划数据 ******************************");
		for (TtSalesPromotionPlanDTO entry : dtoList) {
			try {
				TtSalesPromotionPlanDPO po = new TtSalesPromotionPlanDPO();
				BeanUtils.copyProperties(entry, po);
				po.saveIt();
			} catch (Exception e) {
				logger.error("用户数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("*************************** 成功获取销售计划数据 ******************************");
		return msg;
	}

}
