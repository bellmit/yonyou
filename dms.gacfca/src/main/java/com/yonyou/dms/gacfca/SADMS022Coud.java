package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS022Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：车型贴息信息下发
 */
public interface SADMS022Coud {

	public int getSADMS022(List<SADMS022Dto> voList, String dealerCode) throws ServiceBizException;

}
