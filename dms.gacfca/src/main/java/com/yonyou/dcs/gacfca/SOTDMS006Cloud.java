package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppNFinancialDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS006Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppNFinancialDto> getDataList() throws ServiceBizException;

}
