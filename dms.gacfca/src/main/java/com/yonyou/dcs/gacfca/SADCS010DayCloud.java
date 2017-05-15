package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SA010DayDTO;

/**
 * 
* @ClassName: SADCS010DayCloud 
* @Description: 展厅预测报告(每天)
* @author zhengzengliang 
* @date 2017年4月13日 下午3:17:20 
*
 */
public interface SADCS010DayCloud {
	
	public String receiveDate(List<SA010DayDTO> dtoList) throws Exception;

}
