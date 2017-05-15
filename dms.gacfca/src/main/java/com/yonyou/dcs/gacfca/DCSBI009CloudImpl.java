package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TmVsProductDTO;
import com.yonyou.dms.common.domains.PO.basedata.VsProductPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: DCSBI009CloudImpl 
* @Description: 产品 数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午7:51:59 
*
 */
@Service
public class DCSBI009CloudImpl  extends BaseCloudImpl implements DCSBI009Cloud {

	private static final Logger logger = LoggerFactory.getLogger(DCSBI009CloudImpl.class);
	
	@Override
	public String receiveDate(List<TmVsProductDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始获取产品 数据 ******************************");
		for (TmVsProductDTO entry : dtoList) {
			try {
				VsProductPO po = new VsProductPO();
				BeanUtils.copyProperties(entry, po);
				po.saveIt();
			} catch (Exception e) {
				logger.error("数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("*************************** 成功获取产品 数据 ******************************");
		return msg;
	}

}
