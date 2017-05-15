package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS022Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 贴息利率信息下发
 * 
 * @author Administrator
 *
 */
public interface SADCS022Cluod {
	// 多选下发
	public String handleExecute() throws ServiceBizException;

	// 多选下发
	public String esendDataAll(String array) throws ServiceBizException;

	// 单选下发
	public String sendDataEach(String array) throws ServiceBizException;

	public List<SADMS022Dto> getDataList(String param) throws ServiceBizException;
}
