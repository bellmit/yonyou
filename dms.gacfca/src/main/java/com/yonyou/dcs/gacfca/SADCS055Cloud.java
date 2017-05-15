package com.yonyou.dcs.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TtActivityResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 服务活动下发
 * 
 * @author Administrator
 *
 */
public interface SADCS055Cloud {
	public String execute() throws ServiceBizException;

	public List<TtActivityResultDto> getDataList() throws ServiceBizException;

}
