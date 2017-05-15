package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppUCustomerInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS012Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppUCustomerInfoDto> getDataList() throws ServiceBizException;

}
