package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SA010DTO;

/**
 * 
* @ClassName: SADCS011Cloud 
* @Description: 展厅预测报告(每天)
* @author zhengzengliang 
* @date 2017年4月13日 下午3:40:52 
*
 */
public interface SADCS011Cloud {
	
	public String receiveDate(List<SA010DTO> dtoList) throws Exception;

}
