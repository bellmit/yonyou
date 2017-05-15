package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.SEDMS066Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SEDMS066Cloud {
	
	public String execute(String outWarehousNos) throws ServiceBizException;
	
	public LinkedList<SEDMS066Dto> getDataList(String outWarehousNos) throws ServiceBizException;

}
