package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;

import com.yonyou.dms.common.domains.DTO.basedata.SA013Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 
 * @author wangliang
 * @date 2017年2月28日
 */
public interface SADMS013Service {
	
	public LinkedList<SA013Dto> getSADMS013(String entityCode) throws ServiceBizException;
	
}
