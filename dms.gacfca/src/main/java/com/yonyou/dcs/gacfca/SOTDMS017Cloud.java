package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppUSalesQuotasDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS017Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppUSalesQuotasDto> getDataList() throws ServiceBizException;

}
