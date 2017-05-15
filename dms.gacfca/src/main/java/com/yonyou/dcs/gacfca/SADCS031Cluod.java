package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.SADCS031Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADCS031Cluod {
	public void execute() throws ServiceBizException;

	public List<SADCS031Dto> getDataList() throws ServiceBizException;

}
