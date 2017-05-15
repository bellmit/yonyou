package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.dccDto;

/**
 * 
* @ClassName: SADCS005Cloud 
* @Description: 战败DCC潜在客户信息上报接收
* @author zhengzengliang 
* @date 2017年4月13日 上午11:41:35 
*
 */
public interface SADCS005Cloud {
	
	public String receiveDate(List<dccDto> dto) throws Exception;

}
