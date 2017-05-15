package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TtVisitingRecordDTO;
import com.yonyou.dms.common.domains.PO.basedata.VisitingRecordPO;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 
* @ClassName: DCSBI002CloudImpl 
* @Description: 展厅客户数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午7:51:59 
*
 */
@Service
public class DCSBI002CloudImpl  extends BaseCloudImpl implements DCSBI002Cloud {

	private static final Logger logger = LoggerFactory.getLogger(DCSBI002CloudImpl.class);
	
	@Override
	public String receiveDate(List<TtVisitingRecordDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("*************************** 开始获取展厅客户数据 ******************************");
		for (TtVisitingRecordDTO entry : dtoList) {
			try {
					VisitingRecordPO vrPO = new VisitingRecordPO();
					BeanUtils.copyProperties(entry, vrPO);
					vrPO.saveIt();
			} catch (Exception e) {
				logger.error("经销商车辆实销数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("*************************** 成功获取展厅客户数据 ******************************");
		return msg;
	}

}
