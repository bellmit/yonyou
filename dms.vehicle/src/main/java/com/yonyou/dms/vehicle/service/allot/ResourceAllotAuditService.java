package com.yonyou.dms.vehicle.service.allot;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
@SuppressWarnings("rawtypes")
public interface ResourceAllotAuditService {

	public List<Map> getArea() throws ServiceBizException;

	public List<Map> getSeries() throws ServiceBizException;

	public List<Map> getAllotDate(String string) throws ServiceBizException;

	public Map<String, Object> findAll(Map<String, String> queryParam) throws ServiceBizException;

}
