package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtSalesPromotionPlanDTO;

/**
 * 
* @ClassName: DCSBI006Cloud 
* @Description: 销售计划数据上报
* @author zhengzengliang 
* @date 2017年4月11日 下午4:07:20 
*
 */
public interface DCSBI006Cloud {
	public String receiveDate(List<TtSalesPromotionPlanDTO> dto) throws Exception;
}
