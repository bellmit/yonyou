package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADMS020Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADCS020Cloud {
	public String execute() throws ServiceBizException, Exception;

	public List<SADMS020Dto> getDataList(String param) throws ServiceBizException;

}
