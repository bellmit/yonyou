package com.yonyou.dms.gacfca;

import com.yonyou.dms.DTO.gacfca.dccDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：DCC潜在客户信息下发
 * @author Benzc
 * @date 2017年2月8日
 *
 */
public interface SADMS003 {
	
	public int getSADMS003(dccDto dtoList) throws ServiceBizException;

}
