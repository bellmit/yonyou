package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS003ForeDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：LMS建档校验 DCS转发过来下端判断下是否撞单给出反馈
 * @author Benzc
 * @date 2017年5月8日
 */
public interface SADMS003ForeCoud {
	
	public int getSADMS003Fore(List<SADMS003ForeDTO> voList,String dealerCode)throws ServiceBizException; 

}
