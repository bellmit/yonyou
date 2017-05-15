package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppSendVerificationDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS002Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppSendVerificationDto> getDataList() throws ServiceBizException;

}
