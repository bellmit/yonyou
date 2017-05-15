package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtSalesOrderDTO;

/**
 * 
* @ClassName: DCSBI001Cloud 
* @Description: 销售订单数据上报
* @author zhengzengliang 
* @date 2017年4月6日 下午3:43:17 
*
 */
public interface DCSBI001Cloud {
	public String receiveDate(List<TtSalesOrderDTO> dto) throws Exception;
}
