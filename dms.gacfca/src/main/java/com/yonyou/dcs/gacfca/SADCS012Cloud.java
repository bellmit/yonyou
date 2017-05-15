package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;

/**
 * 
* @ClassName: SADCS012Cloud 
* @Description: 【异步】大客户报备数据上报
* @author zhengzengliang 
* @date 2017年4月13日 下午3:50:35 
*
 */
public interface SADCS012Cloud {
	
	public String receiveDate(List<PoCusWholeClryslerDto> dtoList) throws Exception;

}
