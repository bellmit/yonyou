package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.VsStockEntryItemDto;

/**
 * 
* @ClassName: SADCS002Cloud 
* @Description: 车辆验收上报（DMS验收后，上报验收明细给上端）
* @author zhengzengliang 
* @date 2017年4月11日 下午6:10:01 
*
 */
public interface SADCS002Cloud {
	public String receiveDate(List<VsStockEntryItemDto> dto) throws Exception;
}
