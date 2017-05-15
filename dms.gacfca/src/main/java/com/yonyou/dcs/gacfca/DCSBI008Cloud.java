package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtCustomerIntentDTO;


/**
 * 
* @ClassName: DCSBI008Cloud 
* @Description: 意向数据上报
* @author zhengzengliang 
* @date 2017年4月11日 下午4:20:57 
*
 */
public interface DCSBI008Cloud {
	public String receiveDate(List<TtCustomerIntentDTO> dto) throws Exception;
}
