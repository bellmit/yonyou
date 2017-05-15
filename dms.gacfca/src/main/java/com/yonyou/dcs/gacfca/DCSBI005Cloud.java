package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtCustomerIntentDetailDTO;


/**
 * 
* @ClassName: DCSBI005Cloud 
* @Description: 潜客意向
* @author zhengzengliang 
* @date 2017年4月11日 下午3:40:41 
*
 */
public interface DCSBI005Cloud {
	public String receiveDate(List<TtCustomerIntentDetailDTO> dto) throws Exception;
}
