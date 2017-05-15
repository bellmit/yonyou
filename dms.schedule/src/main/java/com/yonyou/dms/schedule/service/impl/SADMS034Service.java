package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;

import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.SADMS034Dto;

/**
 * @author wangliang
 * @date 2017年3月2日
 */
public interface SADMS034Service {
	
	public LinkedList<SADMS034Dto> getSADMS034()  throws ServiceBizException;
	
}
