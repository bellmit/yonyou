package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SA010DTO;

/**
 * 
* @ClassName: SADCS010Cloud 
* @Description: 展厅预测报告
* @author zhengzengliang 
* @date 2017年4月13日 下午2:38:12 
*
 */
public interface SADCS010Cloud {
	
	public String receiveDate(List<SA010DTO> dtoList) throws Exception;

}
