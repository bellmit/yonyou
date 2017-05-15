package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS003ForeDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
* @ClassName: SADCS003ForeCloud 
* @Description: DCC建档客户信息下发
* @author zhengzengliang 
* @date 2017年4月12日 下午5:28:12 
*
 */
public interface SADCS003ForeCloud {
	
	public String execute() throws ServiceBizException;
	
	public List<SADMS003ForeDTO> getDataList() throws ServiceBizException;

}
