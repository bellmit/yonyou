package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.dccDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS003Cloud 
* @Description: DCC潜在客户信息下发
* @author zhengzengliang 
* @date 2017年4月12日 下午3:31:43 
*
 */
public interface SADCS003Cloud {
	
	public String execute() throws ServiceBizException;
	
	public List<dccDto> getDataList() throws ServiceBizException;

}
