package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS003ForeReturnDTO;

/**
 * 
* @ClassName: SADCS003ForeReturnCloud 
* @Description: DCC建档客户信息反馈接收
* @author zhengzengliang 
* @date 2017年4月13日 上午9:39:19 
*
 */
public interface SADCS003ForeReturnCloud {
	
	public String receiveDate(List<SADMS003ForeReturnDTO> dto) throws Exception;

}
