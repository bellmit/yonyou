package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS032Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 大客户政策车系数据下发
 * 
 * @author Administrator
 *
 */
public interface SADCS032Cloud {
	public String execute() throws ServiceBizException;

	public List<SADCS032Dto> getDataList() throws ServiceBizException;
}
