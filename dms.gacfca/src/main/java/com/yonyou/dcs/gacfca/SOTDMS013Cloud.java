package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppUTestDriveDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS013Cloud {
	
	public String execute() throws ServiceBizException;
	
	public LinkedList<TiAppUTestDriveDto> getDataList() throws ServiceBizException;

}
