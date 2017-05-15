package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TmPotentialCustomerDTO;


/**
 * 
* @ClassName: DCSBI004Cloud 
* @Description: 潜客数据上报
* @author zhengzengliang 
* @date 2017年4月11日 下午3:28:16 
*
 */
public interface DCSBI004Cloud {
	public String receiveDate(List<TmPotentialCustomerDTO> dto) throws Exception;
}
